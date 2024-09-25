# Library Information

This Java library contains a list of classes that can be used to perform various file-system-based operations, such as:

<ul>
<li>Reading files and subdirectories</li>
<li> Find and replace a string occurrence in the files under a directory depending on provided parameters.</li>
</ul>

I've created this library to help developers to perform file-system-based operations in a simple and easy way.

This library's primary goal is providing a flexible java library to bulk replace a string occurrence in the files under
a directory.

All APIs are well-tested, and it reliably tests and read and replaces the string in only text files.
This library does not read or replace the string in binary files,
and does not need any extension hints, however, you can set this library parameter to ignore or work with specific file
extensions, which I refer to as "filtering.".

However, files may be skipped even if you use filtering, if the file is not a text file.

The documentation will be updated soon.

# Building the project

Use this following commands to build the jar file and install the jar file as a project dependency.

This project usages **Gradle**, so run this command to build the jar file:

`gradle build`

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

# Constructors

It Will be updated soon.

# Methods

It Will be updated soon.

# Fields

It Will be updated soon.

# Exceptions

It Will be updated soon.

# License

This library is licensed under the MIT License. Please see the LICENSE file for more information.





