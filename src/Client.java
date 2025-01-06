import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            Auction auction = (Auction) Naming.lookup("rmi://localhost/Auction");
            SecretKey secretKey = KeyUtil.loadKey("keys/testKey.aes");


            int itemID = (args.length > 0) ? Integer.parseInt(args[0]) : 2;

            SealedObject sealedItem = auction.getSpec(itemID);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            AuctionItem auctionItem = (AuctionItem) sealedItem.getObject(cipher);

            System.out.println("Item ID: " + auctionItem.itemID);
            System.out.println("Name: " + auctionItem.name);
            System.out.println("Description: " + auctionItem.description);
            System.out.println("Highest Bid: " + auctionItem.highestBid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
