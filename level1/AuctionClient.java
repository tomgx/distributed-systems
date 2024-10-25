import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AuctionClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Auction auction = (Auction) registry.lookup("Auction");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the item ID to retrieve details: ");
            int itemID = scanner.nextInt();

            AuctionItem item = auction.getSpec(itemID);
            if (item != null) {
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
