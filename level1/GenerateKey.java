import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GenerateKey {
    public static void main(String[] args) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            // Save the key to a file
            try (FileOutputStream fos = new FileOutputStream("keys/testKey.aes");
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(secretKey);
            }

            System.out.println("AES key generated and saved to keys/testKey.aes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
