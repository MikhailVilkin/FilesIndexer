package vilkin;

import java.util.ArrayList;

public class Word implements Comparable<Word> {

    static String makeWord(String word) {
        word = word.replaceAll("[^A-Za-zА-Яа-я]", "");
        return word.toLowerCase();
    }

    final private String word;
    private ArrayList<WordCoordinates> coordinates;
    private int count;

    Word(String word) {

        this.word = word;
        coordinates = new ArrayList<>();
        count = 0;
    }

    String getWord() {
        return word;
    }

    void printCoordinates() {
        for(WordCoordinates coordinates: this.coordinates) {
            System.out.println(coordinates.getParagraph() + " " + coordinates.getIndex());
        }
    }

    public int compareTo(Word wordToCompare) {

        //This is for adding to a Collection
        if(word.equals(wordToCompare.getWord())) {
            wordToCompare.setNewCoordinates(this.getFirstCoordinates());
        }

        return word.compareTo(wordToCompare.getWord());
    }

    @Override
    public String toString() {
        return "word " + word;
    }

    public void setNewCoordinates(WordCoordinates coordinates) {
        this.coordinates.add(coordinates);
        count++;
    }

    public WordCoordinates getFirstCoordinates() {
        return coordinates.get(0);
    }

    public ArrayList<WordCoordinates> getListOfCoordinates() {
        return coordinates;
    }

    public int getQuantity() {

        return count;
    }


}
