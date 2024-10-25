import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class KeyGeneratorUtil {
    public static void main(String[] args) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // AES-128
        SecretKey secretKey = keyGen.generateKey();

        try (ObjectOutputStream keyOut = new ObjectOutputStream(new FileOutputStream("keys/testKey.aes"))) {
            keyOut.writeObject(secretKey);
        }
    }
}
