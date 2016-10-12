package org.fundacionjala.bankocr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Account number that manages the parse, line of ocr digit, the account number.
 */
class AccountNumber {

    private static final int NUMBER_OF_DIGITS = 9;
    private static final int WIDTH_OF_OCR_NUMERAL = 3;
    private static final int FACTOR = 11;
    private static final Map<String, String> DIGIT_MAPPED_TO_NUMERIC = new HashMap<>();
    private final String ocrNumberConverted;

    private static final String CHARACTER_NO_LLEGIBLE = "?";
    private static final String ILL = " ILL";
    private static final String ERR = " ERR";

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


    static {
        DIGIT_MAPPED_TO_NUMERIC.put(ZERO, "0");
        DIGIT_MAPPED_TO_NUMERIC.put(ONE, "1");
        DIGIT_MAPPED_TO_NUMERIC.put(TWO, "2");
        DIGIT_MAPPED_TO_NUMERIC.put(THREE, "3");
        DIGIT_MAPPED_TO_NUMERIC.put(FOUR, "4");
        DIGIT_MAPPED_TO_NUMERIC.put(FIVE, "5");
        DIGIT_MAPPED_TO_NUMERIC.put(SIX, "6");
        DIGIT_MAPPED_TO_NUMERIC.put(SEVEN, "7");
        DIGIT_MAPPED_TO_NUMERIC.put(EIGHT, "8");
        DIGIT_MAPPED_TO_NUMERIC.put(NINE, "9");
    }

    /**
     * Constructor method of account number.
     *
     * @param ocrAccountNumber The OCR account number.
     */
    public AccountNumber(final String ocrAccountNumber) {
        this.ocrNumberConverted = createAccountNumberFromOcr(ocrAccountNumber);
    }

    /**
     * Method to checksum.
     *
     * @return The checksum calculation.
     */
    public boolean isValidCheckSum() {
        int checkSumCalculation = 0;
        for (int digit = 0; digit < NUMBER_OF_DIGITS; digit++) {
            String thisCharacter = ocrNumberConverted.substring(digit, digit + 1);
            checkSumCalculation += (NUMBER_OF_DIGITS - digit) * Integer.parseInt(thisCharacter);
        }
        return checkSumCalculation % FACTOR == 0;
    }

    /**
     * Method to parse the digit.
     *
     * @param ocrAccountNumber The OCR account number.
     * @param digit            The digit.
     * @return The lines of the digit.
     */
    public static String parseDigit(final String ocrAccountNumber, final int digit) {
        int startOfFirstLine = digit * WIDTH_OF_OCR_NUMERAL;
        int startOfSecondLine = startOfFirstLine + (WIDTH_OF_OCR_NUMERAL * NUMBER_OF_DIGITS);
        int startOfThirdLine = startOfSecondLine + (WIDTH_OF_OCR_NUMERAL * NUMBER_OF_DIGITS);

        String firstLineOfOcrDigit = lineOfOcrDigit(ocrAccountNumber, startOfFirstLine);
        String secondLineOfOcrDigit = lineOfOcrDigit(ocrAccountNumber, startOfSecondLine);
        String thirdLineOfOcrDigit = lineOfOcrDigit(ocrAccountNumber, startOfThirdLine);

        return firstLineOfOcrDigit + secondLineOfOcrDigit + thirdLineOfOcrDigit;
    }

    /**
     * Method for the line of the digit.
     *
     * @param ocrAccountNumber The account number.
     * @param startOfLine      The start of the line.
     * @return The line of the digit.
     */
    private static String lineOfOcrDigit(final String ocrAccountNumber, final int startOfLine) {
        return ocrAccountNumber.substring(startOfLine, (startOfLine + WIDTH_OF_OCR_NUMERAL));
    }

    /**
     * Method to create an account number.
     *
     * @param accountNumberAsOcrDigits The account number as digits.
     * @param accountNumberLength      The length of the account number.
     * @return The account number.
     */
    private static String createAccountNumber(final List<String> accountNumberAsOcrDigits,
                                              final int accountNumberLength) {
        StringBuilder accountNumber = new StringBuilder();
        for (int digit = 0; digit < accountNumberLength; digit++) {
            accountNumber.append(DIGIT_MAPPED_TO_NUMERIC
                    .get(accountNumberAsOcrDigits.get(digit)));
        }
        return accountNumber.toString();
    }

    /**
     * Method to create an account number from OCR.
     *
     * @param ocrToInterpret The ocr number to interpret.
     * @return The creation of an account number.
     */
    public static String createAccountNumberFromOcr(final String ocrToInterpret) {

        List<String> accountNumberAsOcrDigits = divideOcrAccountNumberIntoOcrDigits(ocrToInterpret);
        final int accountNumberLength = accountNumberAsOcrDigits.size();
        return createAccountNumber(accountNumberAsOcrDigits, accountNumberLength);
    }

    /**
     * Method to divide the ocr account number into ocr digits.
     *
     * @param ocrAccountNumber The ocr account number.
     * @return The account number as ocr digits.
     */
    public static List<String> divideOcrAccountNumberIntoOcrDigits(final String ocrAccountNumber) {

        List<String> accountNumberAsOcrDigits = new ArrayList<>();

        for (int digit = 0; digit < NUMBER_OF_DIGITS; digit++) {
            accountNumberAsOcrDigits.add(parseDigit(ocrAccountNumber, digit));
        }
        return accountNumberAsOcrDigits;
    }

    /**
     * Get method to obtain the ocr number converted.
     *
     * @return The ocrNumberConverted.
     */
    String convertOCRcodeToNumber() {
        return ocrNumberConverted;
    }
}
