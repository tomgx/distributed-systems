import java.io.Serializable;

public class AuctionItem implements java.io.Serializable {
    int itemID;
    String name;
    String description;
    int highestBid;

    // Constructor
    public AuctionItem(int itemID, String name, String description, int highestBid) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.highestBid = highestBid;
    }

    // toString for easy display in the client
    @Override
    public String toString() {
        return "ItemID: " + itemID + ", Name: " + name + ", Description: " + description + ", HighestBid: " + highestBid;
    }
}
