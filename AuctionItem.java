import java.io.Serializable;

public class AuctionItem implements Serializable {
    int itemID;
    String name;
    String description;
    int highestBid;

    public AuctionItem(int itemID, String name, String description, int highestBid) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.highestBid = highestBid;
    }
}
