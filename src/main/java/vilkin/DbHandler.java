package vilkin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DbHandler {

    private final String DATABASE_PATH;
    private Connection connection;
    Statement statement;

    public DbHandler(String path) throws SQLException {
        DATABASE_PATH = "jdbc:h2:" + path;
        this.connection = DriverManager.getConnection(DATABASE_PATH);
        statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.

        statement.executeUpdate("drop table if exists indexation");
        statement.executeUpdate("create table indexation (word varchar, indexes array)");
    }

    public String getDBPath() {
        return DATABASE_PATH.substring(DATABASE_PATH.lastIndexOf(':') + 1);
    }

    public void addWord(Word word, int fileID) throws SQLException{

        int[] arrayOfIndexes = new int[word.getQuantity() * 3];
        ArrayList<WordCoordinates> coordinates = word.getListOfCoordinates();
        int j=-1;
        for(int i = 0; i < word.getQuantity() * 3; i++) {
            if(i%3 == 2)
                arrayOfIndexes[i] = coordinates.get(j).getIndex();
            if(i%3 == 1)
                arrayOfIndexes[i] = coordinates.get(j).getParagraph();
            if(i%3 == 0) {
                arrayOfIndexes[i] = fileID;
                j++;
            }
        }
        Array array = connection.createArrayOf("indexes",
                Arrays.stream( arrayOfIndexes ).boxed().toArray( Integer[]::new ));

        String sql = "INSERT INTO indexation VALUES (?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setString(1, word.getWord());
        pstmt.setArray(2, array);
        pstmt.executeUpdate();
    }

    public void printDB() throws SQLException{
        ResultSet rs = statement.executeQuery("select * from indexation");
        while(rs.next())
        {
            System.out.println("word " + rs.getString("word"));
            System.out.println(rs.getArray(2));
        }
    }

    public ArrayList<Word> searchWord(String word) throws SQLException{

        ArrayList<Integer> integerArray = new ArrayList<>();
        ArrayList<Word> resultArray = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery(
                "select * from indexation where Word like '%" + word + "%'");

        while (resultSet.next()) {

            String returnedString = resultSet.getArray(2).toString();
            returnedString = returnedString.substring(returnedString.lastIndexOf(":"));
            returnedString = returnedString.replaceAll("[^0-9,]", "");

            for(String i: returnedString.split(",")) {
                integerArray.add(Integer.parseInt(i));
            }

            Word wordObject = new Word(resultSet.getString(1));
            for(int i = 0; i < integerArray.size(); i+=3) {
                wordObject.setNewCoordinates(
                        new WordCoordinates(integerArray.get(i), integerArray.get(i + 1), integerArray.get(i + 2)));
            }
            resultArray.add(wordObject);

            integerArray.clear();
        }
        return resultArray;
    }

    private void addNewCoordinates(/*/,*/ WordCoordinates coordinates) {

        //
    }

}
