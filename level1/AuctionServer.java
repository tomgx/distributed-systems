import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class AuctionServer implements Auction {
    private Map<Integer, AuctionItem> items;

    public AuctionServer() {
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
    }

    @Override
    public AuctionItem getSpec(int itemID) throws RemoteException {
        return items.get(itemID);
    }

    public static void main(String[] args) {
        try {
            AuctionServer server = new AuctionServer();
            Auction stub = (Auction) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Auction", stub);

            System.out.println("Auction server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
