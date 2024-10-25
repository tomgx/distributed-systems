import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class AuctionServer extends UnicastRemoteObject implements Auction {
    private Map<Integer, AuctionItem> auctionItems;

    public AuctionServer() throws RemoteException {
        super();
        auctionItems = new HashMap<>();
        auctionItems.put(1, new AuctionItem(1, "Laptop", "High-end gaming laptop", 1000));
        auctionItems.put(2, new AuctionItem(2, "Phone", "Latest smartphone", 700));
        auctionItems.put(3, new AuctionItem(3, "Watch", "Smartwatch", 300));
    }

    @Override
    public AuctionItem getSpec(int itemID) throws RemoteException {
        return auctionItems.get(itemID);
    }
}
