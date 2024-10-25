import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SealedObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class AuctionClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            Auction auction = (Auction) registry.lookup("Auction");

            SecretKey aesKey = loadKey();
            int itemID = 1;  // For example, retrieve item with ID 1
            SealedObject sealedItem = auction.getSpec(itemID);

            AuctionItem item = decryptItem(sealedItem, aesKey);
            if (item != null) {
                System.out.println("Item ID: " + item.itemID);
                System.out.println("Name: " + item.name);
                System.out.println("Description: " + item.description);
                System.out.println("Highest Bid: " + item.highestBid);
            } else {
                System.out.println("Item not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SecretKey loadKey() throws Exception {
        try (ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream("keys/testKey.aes"))) {
            return (SecretKey) keyIn.readObject();
        }
    }

    private static AuctionItem decryptItem(SealedObject sealedObject, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return (AuctionItem) sealedObject.getObject(cipher);
    }
}
