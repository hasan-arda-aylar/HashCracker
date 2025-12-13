import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


class HashCracker{
    public static String algorythm, hashvalue, path;
    public static boolean password_found;
    public static byte algo;
    public static String Hashstring (String line) throws NoSuchAlgorithmException {


        MessageDigest algo = MessageDigest.getInstance(algorythm);
        byte[] digest = algo.digest(line.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("""
                 _   _           _      ____                _            \s
                | | | | __ _ ___| |__  / ___|_ __ __ _  ___| | _____ _ __\s
                | |_| |/ _` / __| '_ \\| |   | '__/ _` |/ __| |/ / _ \\ '__|
                |  _  | (_| \\__ \\ | | | |___| | | (_| | (__|   <  __/ |  \s
                |_| |_|\\__,_|___/_| |_|\\____|_|  \\__,_|\\___|_|\\_\\___|_|
                  \
                                                                  alpha-1.0 CLI version\s
                =============================================================== \
                
                 Please select the desired algorithm\
                
                ====================Supported Algorithms=======================\
                
                 1:MD5\
                
                 2:SHA-1\
                
                 3:SHA-256\
                
                 4:SHA-384\
                
                 5:SHA-512\
                
                ===============================================================\
                
                Enter a value between (1-5):""");
        while (true){
            algo = input.nextByte();
            if( algo < 1 || algo > 5){
                System.out.println("Please enter a valid value");
            }else {
                switch (algo){
                    case 1:
                        algorythm = "MD5";
                        break;
                    case 2:
                        algorythm = "SHA-1";
                        break;
                    case 3:
                        algorythm = "SHA-256";
                        break;
                    case 4:
                        algorythm = "SHA-384";
                        break;
                    case 5:
                        algorythm = "SHA-512";
                        break;
                }
                break;

            }

        }
        System.out.println("===============================================================\n" +
                "Selected algorithm: " + algorythm );
        System.out.println("===============================================================" +
                "\nPlease enter the hash that you want to bruteforce:");
        hashvalue = input.nextLine();
        if(hashvalue.trim().isEmpty() ){
            hashvalue = input.nextLine();
            while (true){
                if (hashvalue.trim().isEmpty()){
                    System.out.println("Please enter an  hash value: ");
                    hashvalue = input.nextLine();
                }else {
                    break;
                }
            }
        }
        System.out.println("===============================================================\n" +
                "Please enter the file path of the wordlist:" +
                "\n===============================================================");

        try {
            while (true) {
                path = input.nextLine();
                File f = new File(path);
                if(!f.exists()){
                    System.out.println("File does not exist please make sure that you have entered correct path.");
                } else if (!f.canRead()) {
                    System.out.println("File cannot be read please check the permissions and try again.");
                } else if (!f.getName().toLowerCase().endsWith(".txt")) {
                    System.out.println("File must be in txt format, please try agian.");
                }else{
                    break;
                }

            }
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while((line = reader.readLine())!= null){
                if (hashvalue.equals(Hashstring(line))) {
                    System.out.println("the text value of the hash is: " + line);
                    password_found = true;
                }else {
                    password_found = false;
                }
            }
            if (password_found == false){
                System.out.println("Worldist file does not contain the text value of the hash.");
            }
        }
        catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }



    }
}