import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class HashCracker {

    private static String hashString(String input, String algorithm)
            throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder hex = new StringBuilder();
        for (byte b : hashBytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    private static String selectAlgorithm(Scanner scanner) {
        while (true) {
            System.out.print("Enter a value (1-5): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": return "MD5";
                case "2": return "SHA-1";
                case "3": return "SHA-256";
                case "4": return "SHA-384";
                case "5": return "SHA-512";
                default:
                    System.out.println("Invalid selection. Try again.");
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== HASH CRACKER CLI ===");
        System.out.println("""
                1: MD5
                2: SHA-1
                3: SHA-256
                4: SHA-384
                5: SHA-512
                """);

        String algorithm = selectAlgorithm(scanner);
        System.out.println("Selected algorithm: " + algorithm);

        System.out.print("Enter hash value: ");
        String targetHash;
        while ((targetHash = scanner.nextLine().trim()).isEmpty()) {
            System.out.print("Hash cannot be empty. Enter hash value: ");
        }

        File wordlist;
        while (true) {
            System.out.print("Enter wordlist file path: ");
            wordlist = new File(scanner.nextLine());

            if (!wordlist.exists()) {
                System.out.println("File does not exist.");
            } else if (!wordlist.canRead()) {
                System.out.println("File cannot be read.");
            } else {
                break;
            }
        }

        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(wordlist))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (targetHash.equalsIgnoreCase(hashString(line, algorithm))) {
                    System.out.println("Password found: " + line);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Password not found in wordlist.");
            }

        } catch (IOException | NoSuchAlgorithmException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
