import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Server extends UnicastRemoteObject implements Auction {
    private final Map<Integer, AuctionItem> auctionItems;
    private SecretKey secretKey;

    protected Server() throws RemoteException {
        super();
        auctionItems = new HashMap<>();


        AuctionItem item1 = new AuctionItem();
        item1.itemID = 1;
        item1.name = "Painting";
        item1.description = "A painting from 1800";
        item1.highestBid = 10000;

        AuctionItem item2 = new AuctionItem();
        item2.itemID = 2;
        item2.name = "Camera";
        item2.description = "A rare camera";
        item2.highestBid = 1500;

        auctionItems.put(1, item1);
        auctionItems.put(2, item2);

        try {
            secretKey = KeyUtil.loadKey("keys/testKey.aes");
        } catch (Exception e) {
            throw new RemoteException("Failed to load AES key.", e);
        }
    }

    @Override
    public SealedObject getSpec(int itemID) throws RemoteException {
        try {
            AuctionItem item = auctionItems.get(itemID);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return new SealedObject(item, cipher);
        } catch (Exception e) {
            throw new RemoteException("Failed to encrypt auction item.", e);
        }
    }

    public static void main(String[] args) {
        try {

            Registry registry = LocateRegistry.getRegistry();
            Server server = new Server();
            registry.rebind("Auction", server);
            System.out.println("Auction Server is running.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
