# Library Information

This Java library contains a list of classes that can be used to perform various file-system-based operations, such as:

<ul>
<li>Reading files and subdirectories</li>
<li> Find and replace a string occurrence in the files under a directory depending on provided parameters.</li>
<li>Find a string occurrence in a file or files under a directory.</li>
</ul>

I've created this library to help developers to perform file-system-based operations in a simple and easy way.

This library's primary goal is providing a flexible java library to bulk find & replace a string occurrence in the files under
a directory.

Reading text file for occurrence is introduced recently with version 2.0.0, with similar functionality to the previous version, bulk processing in its core always.

All APIs are well-tested, and it reliably tests and read and replaces the string in only text files.
This library does not read or replace the string in binary files,
and does not need any extension hints, however, you can set this library parameter to ignore or work with specific file
extensions, which I refer to as "filtering.".

However, files may be skipped even if you use filtering, if the file is not a text file.

The documentation will be updated soon.

Last updated: 2025-07-15

# Building the project

Use this following commands to build the jar file and install the jar file as a project dependency.

This project usages **Gradle**, so run this command to build the jar file:

`gradle build`

If you need to include this project as a dependency in your project, run this command:

`gradle shadowJar`

A fat jar file will be created in the `build/libs` directory. Include this jar file in your project.

# Using the library

To replace a string in all/selected files under a directory, use this class:

`javadev.stringcollections.textreplacor.ReplaceStringInFiles`

Code example:

      
                                
        try {
                ReplaceStringInFiles replaceStringInFiles = new ReplaceStringInFiles("src/main/resources/test-files", "me", "I Have Replaced Already!");
                replaceStringInFiles.replaceStringInFiles();
        } catch (TextReplacerError e) {
            
            // handle the exception
            e.printStackTrace();
            
        }

To replace a string in a single file, use this class:

`javadev.stringcollections.textreplacor.writer.ReplaceStringInAFile`

Code example:


        try {
                ReplaceStringInAFile replaceStringInAFile = new ReplaceStringInAFile(file, "me", "I've Replaced Already!");
                String replacedFilePath = replaceStringInAFile.replaceString();
        } catch (TextReplacerError e) {
                // handle the exception
                e.printStackTrace();
        }

To find a string in a file, use this class:

`javadev.stringcollections.textreplacor.search.FindOccurrencesInAString`

Code example:

```java
        FindOccurrencesInAString findOccurrencesInAString = new FindOccurrencesInAString(new File("test.txt"), "test"); // initialize the search with the file and the string to find
        // or you can initilize the search with a string and a string to find
        FindOccurrencesInAString findOccurrencesInAString = new FindOccurrencesInAString("This is a test string", "test"); // initialize the search with a string and the string to find
        // find occurrences in the string
        TextSearchResult resultObj = findOccurrencesInAString.findOccurrences();
        // from the result object, you can now access the occurrences and their positions by accessing the Line objects from the result object
        List<Line> lines = resultObj.lines();
        // play with the lines or do whatever you want with the lines
        for (Line line : lines) {
            System.out.println(line);
        }

```

If you need to find a string in all files under a directory, use this class:

`javadev.stringcollections.textreplacor.search.StringMatcherInFiles`

Code example:

```java
        StringMatcherInFiles stringMatcherInFiles = new StringMatcherInFiles("src/main/resources/test-files", "test"); // initialize the search with the directory and the string to find
        // or you can initialize the search with a directory and a string to find
        StringMatcherInFiles stringMatcherInFiles = new StringMatcherInFiles(new File("src/main/resources/test-files"), "test"); // initialize the search with a directory and the string to find
        // find occurrences in the files
        List<TextSearchResult> results = stringMatcherInFiles.search();
        // from the result object, you can now access the occurrences and their positions by accessing the Line objects from the result object
        for (TextSearchResult result : results) {
            List<Line> lines = result.lines();
            // play with the lines or do whatever you want with the lines
            for (Line line : lines) {
                System.out.println(line);
            }
        }
```

# Performance

This library was tested with a 70MB+ text file with a 10MB buffer size,
and it took less than 1 second to replace the string in the file.

By default, the buffer size is 1024 bytes, you can set the buffer size as you want.
Specially, in the case of processing large files, you can set the buffer size to a higher value,
to make sure your file is processed correctly and faster.

# Constructors

`**Class javadev.stringcollections.textreplacor.ReplaceStringInFiles**`

<ul>
<li>public ReplaceStringInFiles(String[] ignoreFileExtensions, String initialDirectoryPath, String oldString, String newString) - 
Initialize the String file replacer with specific file extensions ignore option.
For example, exclude .css files while bulk replace text files.</li> <br>

<li>public ReplaceStringInFiles(String[] ignoreFileExtensions, String initialDirectoryPath, String oldString, String newString, int bufferSize) - 
Initialize the String file replacer with specific file extensions ignore option with file read and write buffer size.
For example, exclude .css files while bulk replace text files.</li> <br>

<li>public ReplaceStringInFiles(String initialDirectoryPath, String oldString, String newString, String[] onlyFileExtensions) - 
Initialize the String file replacer with process only specific types of text file extensions. 
For example, only replace .css files while bulk replace text files.
File extension other than.css files will be ignored.</li> <br>

<li>public ReplaceStringInFiles(String initialDirectoryPath, String oldString, String newString, String[] onlyFileExtensions, int bufferSize) - 
Initialize the String file replacer with a process only specific types of text file extensions with file read and write buffer size. 
For example, only replace .css files while bulk replace text files.
File extension other than .css files will be ignored.</li> <br>

<li>public ReplaceStringInFiles(String initialDirectoryPath, String oldString, String newString) - 
Initialize the String file replacer, which will process and replace string occurrences in all text files, regardless of their extension.</li> <br>

<li>ReplaceStringInFiles(String initialDirectoryPath, String oldString, String newString, int bufferSize) - 
Initialize the String file replacer with a buffer size for file read and write operation,
which will process and replace string occurrences in all text files, regardless of their extension.</li>
</ul>

Class `javadev.stringcollections.textreplacor.writer.ReplaceStringInAFile`

<ul>
<li>public ReplaceStringInAFile(File file, String oldString, String newString) - 
Intialize Single Text File Replacer to replace a String occurrence in the passed file</li> <br>
<li>public ReplaceStringInAFile(File file, String oldString, String newString, int bufferSize)  - 
Intialize Single Text File Replacer with buffer size for read and write file
to replace a String occurrence in the passed file</li>
</ul>

Class `javadev.stringcollections.textreplacor.search.FindOccurrencesInAString`
<ul>
<li>public FindOccurrencesInAString(File file, String stringToFind) - 
Initialize the search with a file and the string to find.</li> <br>
<li>public FindOccurrencesInAString(String string, String stringToFind) -
Initialize the search with a string and the string to find.</li> <br>
</ul>

Class `javadev.stringcollections.textreplacor.search.StringMatcherInFiles`
<ul>
<li>public StringMatcherInFiles(String initialDirectoryPath, String stringToFind) -
Initialize the search with a directory and the string to find.</li> <br>
<li>public StringMatcherInFiles(File initialDirectory, String stringToFind) -
Initialize the search with a directory and the string to find.</li> <br>
</ul>

# Methods

It Will be updated soon.

# Fields

It Will be updated soon.

# Exceptions

It Will be updated soon.

# License

This library is licensed under the MIT License. Please see the LICENSE file for more information.





