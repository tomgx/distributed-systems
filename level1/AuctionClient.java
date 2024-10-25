import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AuctionClient {
    public static void main(String[] args) {
        try {
            // Load AES key from file
            SecretKey secretKey;
            try (FileInputStream fis = new FileInputStream("keys/testKey.aes");
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                secretKey = (SecretKey) ois.readObject();
            }

            // Connect to RMI server
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Auction auction = (Auction) registry.lookup("Auction");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the item ID to retrieve details: ");
            int itemID = scanner.nextInt();

            // Retrieve and decrypt item details
            SealedObject sealedItem = auction.getSpec(itemID);
            if (sealedItem != null) {
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                AuctionItem item = (AuctionItem) sealedItem.getObject(cipher);

                System.out.println("\nItem Details:");
                System.out.println("Item ID: " + item.itemID);
                System.out.println("Name: " + item.name);
                System.out.println("Description: " + item.description);
                System.out.println("Highest Bid: " + item.highestBid);
            } else {
                System.out.println("Item not found.");
            }

            scanner.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
