public class GenerateKey {
    public static void main(String[] args) {
        try {
            KeyUtil.generateAndStoreKey("keys/testKey.aes");
            System.out.println("AES key generated and stored.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
