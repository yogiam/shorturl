import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ShortUrlGeneratorFinal {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE62_LENGTH = BASE62.length();
    private static long sequence = 0; // Incremental sequence
    private static Random random = new Random();

    private static Map<String, String> urlMap = new HashMap<>();

    private static String base62Encode(long num) {
        StringBuilder encoded = new StringBuilder();
        while (num > 0) {
            encoded.append(BASE62.charAt((int) (num % BASE62_LENGTH)));
            num /= BASE62_LENGTH;
        }
        return encoded.reverse().toString();
    }

    private static String generateRandomSuffix(int length) {
        StringBuilder suffix = new StringBuilder();
        for (int i = 0; i < length; i++) {
            suffix.append(BASE62.charAt(random.nextInt(BASE62_LENGTH)));
        }
        return suffix.toString();
    }

    public static String getShortKey() {
        String shortKey;

        String base62Key = base62Encode(sequence++);
        String randomSuffix = generateRandomSuffix(3);

        shortKey = base62Key + randomSuffix;

        return shortKey;
    }

    public static void main(String[] args) {
        String longURL1 = "https://www.example.com/first-url";
        String longURL2 = "https://www.example.com/second-url";

        String shortURL1 = getShortKey();
        String shortURL2 = getShortKey();

        System.out.println("Long URL: " + longURL1 + " -> Short URL: " + shortURL1);
        System.out.println("Long URL: " + longURL2 + " -> Short URL: " + shortURL2);

        int uniqueCount = 1000000;
        int collisionCount = 0;

        for (int i = 0; i < uniqueCount; i++) {
            String longUrl = "https://example.com/url" + i;
            String shortUrl = getShortKey();

            if (!urlMap.containsKey(shortUrl)) {
                urlMap.put(shortUrl, longUrl);
            } else {
                collisionCount++;
            }
        }

        System.out.println("Tested " + uniqueCount + " URLs with " + collisionCount + " collisions.");
    }
}
