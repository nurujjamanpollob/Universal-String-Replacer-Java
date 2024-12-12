package javadev.stringcollections.textreplacor;


import javadev.stringcollections.textreplacor.exception.TextReplacerError;

import java.io.File;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to bulk String replacement tool.");
        System.out.println("This tool will help you to replace a text in all files under a directory with another text.");
        System.out.println("Please Provide input directory path, text to replace, and text to replace with.");
        System.out.println("Enter Input Directory Path: \n");
        String inputDirectoryPath = new Scanner(System.in).nextLine();
        System.out.println("Enter Text to Replace: \n");
        String textToReplace = new Scanner(System.in).nextLine();
        System.out.println("Enter Text to Replace With: \n");
        String textToReplaceWith = new Scanner(System.in).nextLine();

        // First Test check if directory is exist or not
        if (isDirectoryExist(inputDirectoryPath)) {
            System.out.println("Directory Exists, Now doing the replacement...");
            try {
                ReplaceStringInFiles replaceStringInFiles = new ReplaceStringInFiles(inputDirectoryPath, textToReplace, textToReplaceWith, 1024);
                replaceStringInFiles.setUseLogging(false);
                replaceStringInFiles.replaceStringInFiles();
            } catch (TextReplacerError e) {

                // handle the exception
                e.printStackTrace();

            }
        } else {
            System.out.println("Directory Not Exist, Exiting...");
            System.exit(0);
        }
    }

    private static boolean isDirectoryExist(String inputDirectoryPath) {

        return new File(inputDirectoryPath).exists();
    }
}