import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {

        //Start your main program by reading the words from words.txt
        //and storing them in a HashSet<String>.
        HashSet<String> dictionary = getWords("C:/UoPeople/Lab9/words.txt");
        HashSet<String> wordsToCheck = new HashSet<>();
        wordsToCheck.add("html");
        wordsToCheck.add("cpsc");
        wordsToCheck.add("hashset");
        wordsToCheck.add("treeset");
        wordsToCheck.add("cvs");
        wordsToCheck.add("isempty");
        wordsToCheck.add("href");
        wordsToCheck.add("txt");
        wordsToCheck.add("filein");
        wordsToCheck.add("pre");
        wordsToCheck.add("hasnext");
        wordsToCheck.add("wordlist");
        wordsToCheck.add("getinputfilenamefromuser");
        wordsToCheck.add("jfilechooser");
        wordsToCheck.add("filedialog");
        wordsToCheck.add("setdialogtitle");
        wordsToCheck.add("int");
        wordsToCheck.add("hello");
        wordsToCheck.add("world");
        wordsToCheck.add("computer");


        //To make sure that you've read all the words,
        //check the size of the set.
        //(It should be 72875.)
        System.out.println("dictionary size: " + dictionary.size());

        printCorrections(wordsToCheck, dictionary);
    }

    //Can be used to get the words to check as well
    public static HashSet<String> getWords(String path) {
        HashSet<String> dictionary = new HashSet<>();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                dictionary.add(scanner.nextLine().toLowerCase());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return dictionary;
    }

    //this method returns a set of misspelled words from a set with words
    //we can create our own set and use it or we can read the file in with help
    //of the method getWords(String path)
    public static HashSet<String> misspelledWords(HashSet<String> words, HashSet<String> dictionary) {
        HashSet<String> misspelledWords = new HashSet<>();
        for (String word : words) {
            if (!dictionary.contains(word.toLowerCase())) {
                misspelledWords.add(word.toLowerCase());
            }
        }
        return misspelledWords;
    }

    //this method prints out all the misspelled words that were found in a set that we have gotten from
    //after using the method misspelledWords();
    public static void printCorrections(HashSet<String> words, HashSet<String> dictionary) {
        HashSet<String> misspelledWords = misspelledWords(words, dictionary);
        for (String word : misspelledWords) {
            System.out.print(word + ": ");
            TreeSet<String> corrections = corrections(word, dictionary);
            for (String suggest : corrections) {
                System.out.print(" " + suggest);
            }
            System.out.println();
        }
    }

    //this method calls all the methods that check spelling and returns a treeset of suggestions
    public static TreeSet<String> corrections(String badWord, HashSet dictionary) {
        TreeSet<String> suggestions = new TreeSet<>();
        if (!dictionary.contains(badWord)) {
            if (!checkWordChangeLetter(badWord, dictionary).isEmpty()) {
                suggestions.addAll(checkWordChangeLetter(badWord, dictionary));
            }
            if (!checkWordInsertLetter(badWord, dictionary).isEmpty()) {
                suggestions.addAll(checkWordInsertLetter(badWord, dictionary));
            }

            if (!checkWordInsertSpace(badWord, dictionary).isEmpty()) {
                suggestions.addAll(checkWordInsertSpace(badWord, dictionary));
            }

            if (!checkWordSwapLetter(badWord, dictionary).isEmpty()) {
                suggestions.addAll(checkWordSwapLetter(badWord, dictionary));
            }

            if (!checkWordDelete(badWord, dictionary).isEmpty()) {
                suggestions.addAll(checkWordDelete(badWord, dictionary));
            }
        }
        if (suggestions.isEmpty()) {
            System.out.print("No suggestions");
        }
        return suggestions;
    }


    //• Delete any one of the letters from the misspelled word.
    public static ArrayList<String> checkWordDelete(String word, HashSet<String> dictionary) {
        ArrayList<String> suggestions = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            String temp = word.substring(0, i) + word.substring(i + 1, word.length());
            if (dictionary.contains(temp)) {
                suggestions.add(temp);
            }
        }
        return suggestions;
    }

    //Change any letter in the misspelled word to any other letter.
    public static ArrayList<String> checkWordChangeLetter(String word, HashSet<String> dictionary) {
        ArrayList<String> suggestions = new ArrayList<>();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < word.length(); i++) {
            StringBuilder temp = new StringBuilder();
            temp.append(word);
            for (int j = 0; j < alphabet.length(); j++) {
                temp.replace(i, i + 1, Character.toString(alphabet.charAt(j)));
                if (dictionary.contains(temp.toString())) {
                    suggestions.add(temp.toString());
                }
            }
        }
        return suggestions;
    }

    // Insert any letter at any point in the misspelled word.

    public static ArrayList<String> checkWordInsertLetter(String word, HashSet<String> dictionary) {
        ArrayList<String> suggestions = new ArrayList<>();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i <= word.length(); i++) {
            StringBuilder temp = new StringBuilder();
            temp.append(word);
            for (int j = 0; j < alphabet.length(); j++) {
                temp.insert(i, alphabet.charAt(j));
                if (dictionary.contains(temp.toString())) {
                    suggestions.add(temp.toString());
                }
                temp.delete(i, i + 1);
            }
        }
        return suggestions;
    }

    //Swap any two neighboring characters in the misspelled word.
    public static ArrayList<String> checkWordSwapLetter(String word, HashSet<String> dictionary) {
        ArrayList<String> suggestions = new ArrayList<>();
        for (int i = 0; i < word.length() - 1; i++) {
            StringBuilder temp = new StringBuilder();
            temp.append(word);
            char next = word.charAt(i + 1);
            temp.insert(i, next);
            // System.out.println(temp.toString());
            temp.deleteCharAt(i + 2);
            if (dictionary.contains(temp.toString())) {
                suggestions.add(temp.toString());
            }
        }
        return suggestions;
    }

    // Insert a space at any point in the misspelled word (and check that both of the words that are produced are in the dictionary)

    public static ArrayList<String> checkWordInsertSpace(String word, HashSet<String> dictionary) {
        ArrayList<String> suggestions = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            if (dictionary.contains(word.substring(0, i))) {
                String substring = word.substring(i, word.length());
                if (dictionary.contains(substring)) {
                    String temp = word.substring(0, i) + " " + substring;
                    suggestions.add(temp);
                }
            }
        }
        return suggestions;
    }

}


