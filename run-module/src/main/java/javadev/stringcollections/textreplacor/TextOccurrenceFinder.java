package javadev.stringcollections.textreplacor;

import javadev.stringcollections.textreplacor.console.ColoredConsoleOutput;
import javadev.stringcollections.textreplacor.io.TextSearchResultSaver;
import javadev.stringcollections.textreplacor.io.json.JSONObjectUtility;
import javadev.stringcollections.textreplacor.object.TextSearchResult;
import javadev.stringcollections.textreplacor.search.FindOccurrencesInAString;
import javadev.stringcollections.textreplacor.search.StringMatcherInFiles;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Class to find occurrences of a text in a given text file or in a given directory.
 * @author nurujjamanpollob
 * @version 1.0
 * @since 1.0
 */
public class TextOccurrenceFinder {
    
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        // show message that, they want to search in path or in a file
        ColoredConsoleOutput.printGreenText("Do you want to search in a file or in a directory? (file/directory)");
        String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("file")) {
            // ask for file path
            ColoredConsoleOutput.printBlueText("Enter the file path:");
            String filePath = scanner.nextLine().trim();
            
            // ask for text to search
            ColoredConsoleOutput.printBlueText("Enter the text to search:");
            String textToSearch = scanner.nextLine().trim();
            
            // call method to find occurrences in file
            findOccurrencesInFile(filePath, textToSearch);
        } else if (choice.equals("directory")) {
            // ask for directory path
            ColoredConsoleOutput.printBlueText("Enter the directory path:");
            String directoryPath = scanner.nextLine().trim();
            
            // ask for text to search
            ColoredConsoleOutput.printBlueText("Enter the text to search:");
            String textToSearch = scanner.nextLine().trim();
            
            // call method to find occurrences in directory
            findOccurrencesInDirectory(directoryPath, textToSearch);
        } else {
            ColoredConsoleOutput.printRedText("Invalid choice. Please enter 'file' or 'directory'.");
            
            // close the scanner
            scanner.close();
            
            // exit the program
            System.exit(1);
        }
    }

    private static void findOccurrencesInDirectory(String directoryPath, String textToSearch) throws IOException {

        // check if directory exists
        if (!fileOrDirectoryExists(directoryPath)) {
            ColoredConsoleOutput.printRedText("Directory does not exist: " + directoryPath);
            return;
        }

        // show start message
        ColoredConsoleOutput.printGreenText("Searching for occurrences of '" + textToSearch + "' in directory: " + directoryPath);
        ColoredConsoleOutput.printGreenText("\n\n");

        StringMatcherInFiles stringMatcherInFiles = new StringMatcherInFiles(directoryPath, textToSearch); // initialize the search with the directory and the string to find

        // include the occurrences where the text is matched
        stringMatcherInFiles.setIncludeTextWhereMatched(true);
        stringMatcherInFiles.setUseLogging(false);

        // find occurrences in the files
        List<TextSearchResult> results = stringMatcherInFiles.search();

        // if results are null, then no files found or no occurrences found
        if (results == null || results.isEmpty()) {
            ColoredConsoleOutput.printRedText("No occurrences found in the directory: " + directoryPath);
            return;
        }

        // ask the user if they want to save the results to a file, JSON formatted
        ColoredConsoleOutput.printBlueText("Do you want to save the results to a JSON file? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String saveChoice = scanner.nextLine().trim().toLowerCase();

        // pass this processing to a method
        processSaveChoice(saveChoice, results, directoryPath, textToSearch);


    }

    private static void processSaveChoice(String saveChoice, List<TextSearchResult> results, String directoryPath, String textToSearch) {

        boolean isSave = saveChoice.equals("yes") || saveChoice.equals("y");
        if (isSave) {
            // ask for save location
            ColoredConsoleOutput.printBlueText("Enter the save location (file path):");
            Scanner scanner = new Scanner(System.in);
            String saveLocation = scanner.nextLine().trim();

            // create a new instance of TextSearchResultSaver
            TextSearchResultSaver textSearchResultSaver = new TextSearchResultSaver();
            try {
                // save the results to a JSON file
                textSearchResultSaver.saveResultsToJsonFile(results, saveLocation, directoryPath);
                ColoredConsoleOutput.printGreenText("Results saved successfully to: " + saveLocation);
            } catch (IOException e) {
                ColoredConsoleOutput.printRedText("Error saving results: " + e.getMessage());
            }
        } else {
            ColoredConsoleOutput.printGreenText("Results not saved. Result is available in the console output below: ");

            ColoredConsoleOutput.printGreenText(JSONObjectUtility.formatJson(TextSearchResultSaver.getJsonStructure(results, directoryPath)));
        }

    }

    private static void findOccurrencesInFile(String filePath, String textToSearch) throws IOException {

        FindOccurrencesInAString findOccurrencesInAString = new FindOccurrencesInAString(new File(filePath), textToSearch);
        findOccurrencesInAString.setIncludeTextWhereMatched(true); // include the occurrences where the text is matched
        TextSearchResult textSearchResult = findOccurrencesInAString.findOccurrences();
        if (textSearchResult == null || textSearchResult.lines().length == 0) {
            ColoredConsoleOutput.printRedText("No occurrences found in the file: " + filePath);
            return;
        }
        // show find occurrence test is successful
        ColoredConsoleOutput.printGreenText("Find Occurrence Test is Successful!");
        // ask the user if they want to save the results to a file, JSON formatted
        ColoredConsoleOutput.printBlueText("Do you want to save the results to a JSON file? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String saveChoice = scanner.nextLine().trim().toLowerCase();
        // pass this processing to a method
        processSaveChoice(saveChoice, List.of(textSearchResult), new File(filePath).getParent(), textToSearch);
    }

    // check if the directory or file exists
    private static boolean fileOrDirectoryExists(String path) {
        return new java.io.File(path).exists();
    }
}
