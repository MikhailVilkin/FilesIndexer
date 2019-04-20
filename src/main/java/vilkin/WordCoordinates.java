package vilkin;

public class WordCoordinates {

    private final int paragraph;
    private final int index;
    private final int fileID;

    WordCoordinates(int paragraph, int index) {
        this.paragraph = paragraph;
        this.index = index;
        this.fileID = -1;
    }
    WordCoordinates(int fileID, int paragraph, int index) {
        this.paragraph = paragraph;
        this.index = index;
        this.fileID = fileID;
    }

    int getParagraph() {
        return paragraph;
    }

    int getIndex() {
        return index;
    }

    int getFileID() throws Exception{
        if(fileID == -1)
            throw (new Exception("File is not mentioned"));
        return fileID;
    }

}
