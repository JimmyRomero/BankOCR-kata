package org.fundacionjala.bankOcr;

import java.util.ArrayList;

/**
 * Class Account number that manages the parse, line of ocr digit, the account number.
 */
public class AccountNumber {

    private boolean isValid;
    private final String textVersion;
    static final int NUMBER_OF_DIGITS = 9;
    static final int WIDTH_OF_OCR_NUMERAL = 3;

    /**
     * Constructor method of account number.
     *
     * @param ocrAccountNumber The OCR account number.
     */
    public AccountNumber(final String ocrAccountNumber) {
        this.textVersion = createAccountNumberFromOcr(ocrAccountNumber);
        isValid = checkSum();
    }

    /**
     * Method to checksum.
     *
     * @return The checksum calculation.
     */
    private boolean checkSum() {
        int checkSumCalculation = 0;
        int currentDigit;

        for (int digit = 0; digit < NUMBER_OF_DIGITS; digit++) {

            String thisCharacter = textVersion.substring(digit, digit + 1);
            currentDigit = Integer.parseInt(thisCharacter);
            checkSumCalculation = checkSumCalculation + ((NUMBER_OF_DIGITS - digit) * currentDigit);
        }
        final int i = 11;
        return ((checkSumCalculation % i) == 0);
    }

    /**
     * Method to parse the digit.
     *
     * @param ocrAccountNumber         The OCR account number.
     * @param accountNumberAsOcrDigits The account number as digits.
     * @param digit                    The digit.
     * @return The lines of the digit.
     */
    public static String parseDigit(final String ocrAccountNumber,
                                    final ArrayList<String> accountNumberAsOcrDigits, final int digit) {
        int startOfFirstLine = (digit * WIDTH_OF_OCR_NUMERAL);
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
     * @param accountNumberAsOcrDigits The aacount number as digits.
     * @param accountNumberLength      The length of the account number.
     * @return The aacount number.
     */
    private static String createAccountNumber(final ArrayList<String> accountNumberAsOcrDigits,
                                              final int accountNumberLength) {
        ConvertDigitsToNumeric converter = new ConvertDigitsToNumeric();
        String accountNumber = "";
        for (int digit = 0; digit < accountNumberLength; digit++) {
            accountNumber = accountNumber + (converter.makeNumeric(accountNumberAsOcrDigits.get(digit)));
        }
        return accountNumber;
    }

    /**
     * Method to create an account number from OCR.
     *
     * @param ocrToInterpret The ocr number to interpret.
     * @return The creation of an account number.
     */
    public static String createAccountNumberFromOcr(final String ocrToInterpret) {

        ArrayList<String> accountNumberAsOcrDigits = divideOcrAccountNumberIntoOcrDigits(ocrToInterpret);
        final int accountNumberLength = accountNumberAsOcrDigits.size();
        return createAccountNumber(accountNumberAsOcrDigits, accountNumberLength);
    }

    /**
     * Method to divide the ocr account number into ocr digits.
     *
     * @param ocrAccountNumber The ocr account number.
     * @return The account number as ocr digits.
     */
    public static ArrayList<String> divideOcrAccountNumberIntoOcrDigits(final String ocrAccountNumber) {

        ArrayList<String> accountNumberAsOcrDigits = new ArrayList<String>();

        for (int digit = 0; digit < NUMBER_OF_DIGITS; digit++) {
            accountNumberAsOcrDigits.add(parseDigit(ocrAccountNumber, accountNumberAsOcrDigits, digit));
        }
        return accountNumberAsOcrDigits;
    }

    /**
     * Get method to obtain if is valid.
     *
     * @return Get is valid.
     */
    boolean getIsValid() {
        return isValid;
    }

    /**
     * Get method to obtain the text version.
     *
     * @return The textversion.
     */
    String getTextVersion() {
        return textVersion;
    }
}
