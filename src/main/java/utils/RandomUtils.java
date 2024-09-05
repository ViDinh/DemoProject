package utils;

import java.util.Random;
import java.util.stream.Collectors;

public class RandomUtils {

    private static final int MIN = 10000;
    private static final int MAX = 99999;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    public static int generateFiveDigitNumber() {
        return random.nextInt((MAX - MIN) + 1) + MIN;
    }

    public static int generateRandomNumber(int maxNumber) {
        return random.nextInt(maxNumber);
    }

    public static String generateRandomString(int maxChars) {
        return random.ints(maxChars, 0, CHARACTERS.length())
                .mapToObj(i -> String.valueOf(CHARACTERS.charAt(i)))
                .collect(Collectors.joining());
    }
}
