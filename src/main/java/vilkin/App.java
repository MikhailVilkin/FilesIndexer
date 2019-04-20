package vilkin;

import jdk.nashorn.internal.objects.MapIterator;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class App {

    public static void main( String[] args ) {

        LinkedHashMap<String, Word> words;

        FileScanner f = null;
        try {
            f = new FileScanner("src/main/resources/input.txt");
            words = f.getWordsMap();

            try {
                DbHandler database = new DbHandler("./sample");
                Iterator<Word> itr = words.values().iterator();
                while (itr.hasNext()) {
                    database.addWord(itr.next(), 1);
                }

                Scanner inp = new Scanner(System.in);
                String str = inp.nextLine();

                ArrayList<Word> result = database.searchWord(str);
                for(int i = 0; i < result.size(); i++) {
                    System.out.println(result.get(i).toString());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
