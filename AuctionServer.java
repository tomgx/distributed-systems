import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

public class AuctionServer extends UnicastRemoteObject implements Auction {
    private Map<Integer, AuctionItem> items;
    private SecretKey aesKey;

    protected AuctionServer() throws RemoteException {
        super();
        loadKey();
        loadItems();
    }

    public static void main(String[] args) {
        try {
            AuctionServer server = new AuctionServer();
            Registry registry = LocateRegistry.createRegistry(1099); // Use default port 1099
            registry.rebind("Auction", server);  // Bind the service with the name "Auction"
            System.out.println("Auction RMI Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void loadKey() {
        try (ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream("keys/testKey.aes"))) {
            aesKey = (SecretKey) keyIn.readObject();
        } catch (Exception e) {
            System.err.println("Error loading AES key: " + e.getMessage());
        }
    }

    private void loadItems() {
        items = new HashMap<>();
        items.put(1, new AuctionItem(1, "Laptop", "A powerful laptop", 300));
        items.put(2, new AuctionItem(2, "Smartphone", "A new smartphone", 200));
    }

    @Override
    public SealedObject getSpec(int itemID) throws RemoteException {
        AuctionItem item = items.get(itemID);
        if (item == null) return null;

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            return new SealedObject(item, cipher);
        } catch (Exception e) {
            throw new RemoteException("Error encrypting item", e);
        }
    }
}
}
