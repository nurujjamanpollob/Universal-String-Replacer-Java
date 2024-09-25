package javadev.stringcollections.textreplacor.detector;

/**
 * @apiNote Class that detect text bound.
 * @since 1.0
 * @version 1.0
 * @author nurujjamanpollob
 */
public class TextBoundDetector {

    /**
     * Method that tests if a given index is not out of bound in a given input string.
     * It returns true that case. If the index is not bound, it will return false.
     *
     * @param inputString input string
     * @param index       index to test
     */
    public static boolean isIndexNotBound(String inputString, int index) {

        return index >= 0 && index < inputString.length();
    }

    /**
     * Method that tests if a given index is out of bound in a given input string.
     * It accept an index, along with another number to increase the index. then it tests if the index is out of bound.
     * If the index is out of bound, it will return true. Otherwise, it will return false.
     * @param inputString input string
     * @param index index to test
     * @param increaseBy number to increase the index
     */
    public static boolean isIndexOutOfBound(String inputString, int index, int increaseBy) {

        return index + increaseBy >= inputString.length();
    }

    /**
     * Method that tests if a given index is out of bound in a given input string.
     * It accept an index, along with another number to decrease the index. then it tests if the index is out of bound.
     * If the index is out of bound, it will return true. Otherwise, it will return false.
     * @param index index to test
     * @param decreaseBy number to decrease the index
     */
    public static boolean isIndexOutOfBoundDecrease(int index, int decreaseBy) {

        return index - decreaseBy < 0;

    }
}
