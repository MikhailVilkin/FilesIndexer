package vilkin;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileScanner {

    private final Path filePath;
    private List<String> buffer;
    private ArrayList<String> bufferListOfWords;
    private int indexInLine;
    private int lineSequenceNumber;

    private TreeSet<Word> wordsList;

    FileScanner(String filePath) throws IOException{

        lineSequenceNumber = -1;
        indexInLine = 0;
        try {
            this.filePath = Paths.get(filePath);
            buffer = Files.readAllLines(this.filePath);
        }
        catch (IOException exception){
            throw exception;
        }
        bufferListOfWords = new ArrayList<>();
        createListOfWords();
    }

    public Word getNextWord() {

        if(bufferListOfWords.isEmpty() ||
           indexInLine == bufferListOfWords.size()) {

            if(lineSequenceNumber == buffer.size() - 1)
                return null;

            bufferListOfWords.clear();
            lineSequenceNumber++;
            for(String words: buffer.get(lineSequenceNumber).split(" ")) {
                if(!Word.makeWord(words).isEmpty())
                    bufferListOfWords.add(Word.makeWord(words));
            }
            indexInLine = 0;

        }

        if(bufferListOfWords.isEmpty()) {
            return getNextWord();
        }
        if(bufferListOfWords.get(indexInLine).isEmpty()) {
            indexInLine++;
            return getNextWord();
        }

        Word word = new Word(bufferListOfWords.get(indexInLine));
        WordCoordinates coordinates = new WordCoordinates(lineSequenceNumber + 1, indexInLine + 1);
        word.setNewCoordinates(coordinates);
        indexInLine++;

        return word;
    }

    private void createListOfWords() {

        wordsList = new TreeSet<>();

        Word word;
        while((word = this.getNextWord()) != null) {
            wordsList.add(word);
        }
    }

    public LinkedHashMap<String, Word> getWordsMap() {

        LinkedHashMap<String, Word> hashMapOfWords = new LinkedHashMap<>();
        for(Word words: wordsList) {
            hashMapOfWords.put(words.getWord(), words);
        }

        return hashMapOfWords;
    }

}
