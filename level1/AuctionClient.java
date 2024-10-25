import java.rmi.Naming;
import java.util.Scanner;

public class AuctionClient {
    public static void main(String[] args) {
        try {
            Auction auction = (Auction) Naming.lookup("rmi://localhost/Auction");
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter item ID: ");
            int itemID = scanner.nextInt();

            AuctionItem item = auction.getSpec(itemID);
            if (item != null) {
                System.out.println(item);
            } else {
                System.out.println("Item not found.");
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
