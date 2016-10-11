package org.fundacionjala.bankOcr;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to convert digits to numeric.
 */
public class ConvertDigitsToNumeric {

    private Map<String, String> digitMappedToNumeric = new HashMap<String, String>();

    private static final String ZERO = " _ | ||_|";
    private static final String ONE = "     |  |";
    private static final String TWO = " _  _||_ ";
    private static final String THREE = " _  _| _|";
    private static final String FOUR = "   |_|  |";
    private static final String FIVE = " _ |_  _|";
    private static final String SIX = " _ |_ |_|";
    private static final String SEVEN = " _   |  |";
    private static final String EIGHT = " _ |_||_|";
    private static final String NINE = " _ |_| _|";

    /**
     * Constructor method to convert digits to numeric.
     */
    public ConvertDigitsToNumeric() {
        digitMappedToNumeric.put(ZERO, "0");
        digitMappedToNumeric.put(ONE, "1");
        digitMappedToNumeric.put(TWO, "2");
        digitMappedToNumeric.put(THREE, "3");
        digitMappedToNumeric.put(FOUR, "4");
        digitMappedToNumeric.put(FIVE, "5");
        digitMappedToNumeric.put(SIX, "6");
        digitMappedToNumeric.put(SEVEN, "7");
        digitMappedToNumeric.put(EIGHT, "8");
        digitMappedToNumeric.put(NINE, "9");
    }

    /**
     * Method to convert the digit to numeric.
     *
     * @param numToConvert The String number to convert.
     * @return The number converted.
     */
    public String makeNumeric(final String numToConvert) {
        String num = digitMappedToNumeric.get(numToConvert);
        return num;
    }
}
