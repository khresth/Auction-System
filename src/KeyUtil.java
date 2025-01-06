import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class KeyUtil {
    public static void generateAndStoreKey(String filePath) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); 
        SecretKey secretKey = keyGen.generateKey();
        try (FileOutputStream keyFile = new FileOutputStream(filePath)) {
            keyFile.write(secretKey.getEncoded());
        }
    }

    public static SecretKey loadKey(String filePath) throws IOException {
        byte[] keyBytes = Files.readAllBytes(Path.of(filePath));
        return new javax.crypto.spec.SecretKeySpec(keyBytes, "AES");
    }
}
