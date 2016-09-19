package org.fundacionjala.bankOcr;

import java.util.ArrayList;

public class AccountNumber {

    public boolean isValid;
    public final String textVersion;
    final static int NUMBER_OF_DIGITS = 9;
    final static int WIDTH_OF_OCR_NUMERAL = 3;
    
    public AccountNumber(String ocrAccountNumber) {
        this.textVersion = createAccountNumberFromOcr(ocrAccountNumber);
        isValid = checkSum();
    }

    private boolean checkSum() {
        int checkSumCalculation = 0;
        int currentDigit;

        for (int digit = 0; digit < NUMBER_OF_DIGITS; digit++) {

            String thisCharacter = textVersion.substring(digit, digit+1);
            currentDigit = Integer.parseInt(thisCharacter);
            checkSumCalculation = checkSumCalculation + ((NUMBER_OF_DIGITS-digit) * currentDigit);
        }
        return ((checkSumCalculation % 11) == 0);
    }

    private static String parseDigit(String ocrAccountNumber, ArrayList<String> accountNumberAsOcrDigits, int digit) {
        int startOfFirstLine = (digit * WIDTH_OF_OCR_NUMERAL);
        int startOfSecondLine = startOfFirstLine + (WIDTH_OF_OCR_NUMERAL * NUMBER_OF_DIGITS);
        int startOfThirdLine = startOfSecondLine + (WIDTH_OF_OCR_NUMERAL * NUMBER_OF_DIGITS);

        String firstLineOfOcrDigit = lineOfOcrDigit(ocrAccountNumber, startOfFirstLine);
        String secondLineOfOcrDigit = lineOfOcrDigit(ocrAccountNumber, startOfSecondLine);
        String thirdLineOfOcrDigit = lineOfOcrDigit(ocrAccountNumber, startOfThirdLine);

        return firstLineOfOcrDigit + secondLineOfOcrDigit + thirdLineOfOcrDigit;
    }

    private static String lineOfOcrDigit(String ocrAccountNumber, int startOfLine) {
        return ocrAccountNumber.substring(startOfLine,(startOfLine + WIDTH_OF_OCR_NUMERAL));
    }

    private static String createAccountNumber(ArrayList<String> accountNumberAsOcrDigits, final int ACCOUNT_NUMBER_LENGTH) {
        ConvertDigitsToNumeric converter = new ConvertDigitsToNumeric();
        String accountNumber = "";
        for (int digit = 0; digit < ACCOUNT_NUMBER_LENGTH; digit++) {
            accountNumber = accountNumber + (converter.makeNumeric(accountNumberAsOcrDigits.get(digit)));
        }
        return accountNumber;
    }

    public static String createAccountNumberFromOcr(String ocrToInterpret) {

        ArrayList<String> accountNumberAsOcrDigits = divideOcrAccountNumberIntoOcrDigits(ocrToInterpret);
        final int ACCOUNT_NUMBER_LENGTH = accountNumberAsOcrDigits.size();
        return createAccountNumber(accountNumberAsOcrDigits, ACCOUNT_NUMBER_LENGTH);
    }

    public static ArrayList<String> divideOcrAccountNumberIntoOcrDigits(String ocrAccountNumber) {

        ArrayList<String> accountNumberAsOcrDigits = new ArrayList<String>();

        for (int digit = 0; digit < NUMBER_OF_DIGITS; digit++) {
            accountNumberAsOcrDigits.add(parseDigit(ocrAccountNumber, accountNumberAsOcrDigits, digit));
        }
        return accountNumberAsOcrDigits;
    }
}
