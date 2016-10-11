package org.fundacionjala.bankOcr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test the numbers and digits of ocr bank.
 */
public class FileNumbersTest {

    private Map<String, String> useCase = new HashMap<String, String>();

    /**
     * Method to assign the data for perform the test.
     */
    @Before
    public void assignDataForTesting() {

        useCase.put("000000000",
                " _  _  _  _  _  _  _  _  _ "
                        + "| || || || || || || || || |"
                        + "|_||_||_||_||_||_||_||_||_|"
                        + "                           ");

        useCase.put("111111111",
                "                           "
                        + "  |  |  |  |  |  |  |  |  |"
                        + "  |  |  |  |  |  |  |  |  |"
                        + "                           ");

        useCase.put("123456789",
                "    _  _     _  _  _  _  _ "
                        + "  | _| _||_||_ |_   ||_||_|"
                        + "  ||_  _|  | _||_|  ||_| _|"
                        + "                           ");

        useCase.put("000000051",
                " _  _  _  _  _  _  _  _    "
                        + "| || || || || || || ||_   |"
                        + "|_||_||_||_||_||_||_| _|  |"
                        + "                           ");
    }

    /**
     * Test to check if the account number is array of nine digits.
     */
    @Test
    public void testAccountNumberIsArrayOfNineDigits() {
        ArrayList<String> arrayToCheck = AccountNumber.divideOcrAccountNumberIntoOcrDigits(useCase.get("000000000"));
        final int expected = 9;
        Assert.assertEquals(expected, arrayToCheck.size());
    }

    /**
     * Test to check the account number is valid.
     */
    @Test
    public void testCheckSumShowsValidAccountNumberIsValid() {
        final String numberToCheck = "123456789";
        AccountNumber accountNumber = new AccountNumber(useCase.get(numberToCheck));
        Assert.assertTrue(accountNumber.getIsValid());
    }

    /**
     * Test to check the account number is invalid.
     */
    @Test
    public void testCheckSumShowsInvalidAccountNumberIsInvalid() {
        final String numberToCheck = "111111111";
        AccountNumber accountNumber = new AccountNumber(useCase.get(numberToCheck));
        System.out.println(accountNumber);
        Assert.assertFalse(accountNumber.getIsValid());
    }


    /**
     * Test to verify if the stick is recognized.
     */
    @Test
    public void testStick1234567879IsRecognized() {
        String numberToCheck = "123456789";
        AccountNumber accountNumber = new AccountNumber(useCase.get(numberToCheck));
        Assert.assertEquals(numberToCheck, accountNumber.getTextVersion());
    }
}
