import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class AuctionServer implements Auction {
    private Map<Integer, AuctionItem> items;
    private SecretKey secretKey;

    public AuctionServer() {
        // Initialize items
        items = new HashMap<>();
        AuctionItem item1 = new AuctionItem();
        item1.itemID = 1;
        item1.name = "Vintage Watch";
        item1.description = "A rare vintage watch from 1960.";
        item1.highestBid = 500;

        AuctionItem item2 = new AuctionItem();
        item2.itemID = 2;
        item2.name = "Painting";
        item2.description = "Original oil painting by a famous artist.";
        item2.highestBid = 1200;

        AuctionItem item3 = new AuctionItem();
        item3.itemID = 3;
        item3.name = "Antique Vase";
        item3.description = "A beautiful vase from ancient China.";
        item3.highestBid = 3000;

        items.put(item1.itemID, item1);
        items.put(item2.itemID, item2);
        items.put(item3.itemID, item3);

        // Load AES key from file
        try (FileInputStream fis = new FileInputStream("keys/testKey.aes");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            secretKey = (SecretKey) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SealedObject getSpec(int itemID) throws RemoteException {
        try {
            AuctionItem item = items.get(itemID);
            if (item == null) return null;

            // Encrypt AuctionItem
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return new SealedObject(item, cipher);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error encrypting item", e);
        }
    }

    public static void main(String[] args) {
        try {
            AuctionServer server = new AuctionServer();
            Auction stub = (Auction) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Auction", stub);

            System.out.println("Auction server ready with encryption");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
