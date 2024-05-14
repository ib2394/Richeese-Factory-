import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class RicheeseFactoryAppLL {
    public static void main(String[] args) {
        LinkedList RicheeseList = new LinkedList();
        readDataFromFile(RicheeseList);
        
        Scanner in = new Scanner(System.in);
        String answer = "yes";
        while(answer.equalsIgnoreCase("yes"))
        {   
            System.out.print("\n+-----------------------------------+");
            System.out.print("\n           CUSTOMER DETAIL           ");
            System.out.print("\n+-----------------------------------+");
            
            System.out.print("\nEnter Name        : ");
            String custName = in.next();
            
            System.out.print("\nEnter phone number: ");
            String phoneNum = in.next();
            
            displayMenu();
            System.out.print("\nEnter menu type   : ");
            char menuType = in.next().charAt(0);
            
            System.out.print("\nEnter menu id     : ");
            String menuId = in.next();
            
            System.out.print("\nEnter quantity    : ");
            int quantity = in.nextInt();
            
            paymentMethod();
            System.out.print("\nEnter payment method : ");
            char paymentMethod = in.next().charAt(0);
            
            RicheeseFactory customer = new RicheeseFactory (custName, phoneNum, menuType, menuId, quantity, paymentMethod);
            RicheeseList.addLast(customer);
            
            System.out.print("\nNext customer (yes/no)?: ");
            answer=in.next();
        }
        //update data in txt file
        writeDataToFile(RicheeseList);
        
        //Searching and update 
        System.out.print("\n+-----------------------------------+");
        System.out.print("\n         SEARCHING CUSTOMER          ");
        System.out.print("\n+-----------------------------------+");
        System.out.print("\nEnter customer phone number to update: ");
        String phoneNoToSearch = in.next();
        
        RicheeseFactory change;
        RicheeseFactory customer3 = (RicheeseFactory) RicheeseList.getFirst();
        boolean customerFound = false;
        
        while (customer3 != null) {
            if (customer3.getPhoneNum().equalsIgnoreCase(phoneNoToSearch))  {
                System.out.print(customer3.toString());
                System.out.print("\n\n+-----------------------------------+");
                System.out.print("\n        UPDATE MENU SELECTION        ");
                System.out.print("\n+-----------------------------------+");
                
                System.out.print("\nEnter New Menu Type: ");
                char updateMenuType = in.next().charAt(0);
                customer3.setMenuType(updateMenuType);
                
                System.out.print("\nEnter New Menu ID: ");
                String updateMenuId = in.next();
                customer3.setMenuId(updateMenuId);
                
                System.out.print("\nEnter New Quantity: ");
                int updateQty = in.nextInt();
                customer3.setQuantity(updateQty);
                
                change = customer3;
                System.out.print(change.toString());
                customerFound = true;
                break; // Stop searching once found
            }
            customer3 = (RicheeseFactory) RicheeseList.getNext();
        }
        if (!customerFound) {
            System.out.println("Customer not found with the specified phone number.");
        }
        
        //Process of counting the total number of customers who visit the Richeese factory
        int totalCustomer = 0;
        RicheeseFactory customer = (RicheeseFactory) RicheeseList.getFirst();
        while (customer != null) 
        {
            if(customer.getCustName() != null){ 
                totalCustomer++;
            }
            customer = (RicheeseFactory) RicheeseList.getNext();
        }
        System.out.print("\n\n+-----------------------------------+");
        System.out.print("\n          TOTAL OF CUSTOMER          ");
        System.out.print("\n+-----------------------------------+");
        System.out.println("\nTotal of customer that visit Richeese Factory : " + totalCustomer);
        
        //higher customer price (maximum)
        RicheeseFactory highest = null;
        double maxTotal = 0;
        RicheeseFactory c = (RicheeseFactory) RicheeseList.getFirst();  
        while (c != null){ 
            if(c.calculateTotal()>maxTotal){     
                highest = c;
                maxTotal = c.calculateTotal();
            }
            c = (RicheeseFactory) RicheeseList.getNext(); 
        }
        System.out.print("\n\n+-----------------------------------+");
        System.out.print("\n         MAXIMUM TOTAL PRICE         ");
        System.out.print("\n+-----------------------------------+");
        System.out.println("\n--Details of customer with the most total price-- " +"\n" + highest.toString());
        
        //Process to calculate the total revenue
        double totalRevenue = 0;
        int count = 0;
        RicheeseFactory cust = (RicheeseFactory) RicheeseList.getFirst();
        while (cust != null)
        {
            totalRevenue += cust.calculateTotal();
            cust = (RicheeseFactory) RicheeseList.getNext();
        }
        System.out.print("\n\n+-----------------------------------+");
        System.out.print("\n           TOTAL REVENUE             ");
        System.out.print("\n+-----------------------------------+");
        System.out.println("\nThe Total Revenue: RM" +totalRevenue);
        
        //Removal
        RicheeseFactory customers = (RicheeseFactory) RicheeseList.removeFirst();
        LinkedList paymentCash = new LinkedList();
        LinkedList paymentCard = new LinkedList();
        
        while (customers != null){
            if (customers.getPaymentMethod() == '1'){
                paymentCash.addLast (customers);
            }else{
                paymentCard.addLast (customers);
            }
            customers = (RicheeseFactory) RicheeseList.removeFirst();
        }
        
        int Cashnumber = 0;
        System.out.print("\n+-----------------------------------+");
        System.out.print("\n             CASH LIST               ");
        System.out.print("\n+-----------------------------------+");
        RicheeseFactory Customer = (RicheeseFactory) paymentCash.getFirst();
        while (Customer != null){
            Cashnumber++;
            System.out.println("\n" +Cashnumber+ ". " +Customer.getCustName());
            Customer = (RicheeseFactory) paymentCash.getNext();
        }
        
        int CardNumber = 0;
        System.out.print("\n+-----------------------------------+");
        System.out.print("\n             CARD LIST               ");
        System.out.print("\n+-----------------------------------+");
        RicheeseFactory Customers = (RicheeseFactory) paymentCard.getFirst();
        while (Customers != null){
            CardNumber++;
            System.out.println("\n" +CardNumber+ ". " +Customers.getCustName());
            Customers = (RicheeseFactory) paymentCard.getNext();
        } 
    }
    
     private static void writeDataToFile(LinkedList RicheeseList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("RicheeseList.txt"))) {
            RicheeseFactory customer = (RicheeseFactory) RicheeseList.getFirst();

            while (customer != null) {
                // Format the reservation data for writing to the file
                String data = String.format("%s;%s;%c;%s;%d;%c",
                        customer.getCustName(),
                        customer.getPhoneNum(),
                        customer.getMenuType(),
                        customer.getMenuId(),
                        customer.getQuantity(),
                        customer.getPaymentMethod());

                bw.write(data);
                bw.newLine();

                // Move to the next reservation
                customer = (RicheeseFactory) RicheeseList.getNext();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void readDataFromFile(LinkedList RicheeseList) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("RicheeseList.txt"));
            String str = br.readLine();

            while (str != null) {
                StringTokenizer token = new StringTokenizer(str, ";");

                String custName = token.nextToken();
                String phoneNum = token.nextToken();
                char menuType = token.nextToken().charAt(0);
                String menuId = token.nextToken();
                int quantity = Integer.parseInt(token.nextToken());
                char paymentMethod;
                
                if (token.hasMoreTokens()) {
                    paymentMethod = token.nextToken().charAt(0);
                } else {
                    paymentMethod = ' '; // Set a default value or handle the absence of paymentMethod
                }

                // Create instances 
                RicheeseFactory customer = new RicheeseFactory(custName, phoneNum, menuType, menuId, quantity, paymentMethod);

                // Add the reservation to the list
                RicheeseList.addLast(customer);

                // Read the next line
                str = br.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private static void displayMenu()
    {
        System.out.println(" ____________________________________________________________________");
        System.out.println("|  Menu Type  |  Menu Id  |         Menu Name          |    Price    |");    
        System.out.println("|-------------| ----------|----------------------------|-------------|"); 
        System.out.println("|             |     F1    | DS Fire Chicken            |    RM12     |");
        System.out.println("|             |     F2    | DS Richiken                |    RM12     |");
        System.out.println("|             |     F3    | Flying Chicken             |    RM12     |");
        System.out.println("|      F      |     F4    | Fire Wings                 |    RM18     |");
        System.out.println("|             |     F5    | Rich Burger Chicken        |    RM25     |");
        System.out.println("|             |     F6    | Fire Burger Chicken        |    RM25     |");
        System.out.println("|             |     F7    | Rich Burger Beef           |    RM25     |");
        System.out.println("|------------------------------------------------------|-------------|");
        System.out.println("|             |     S1    | Richeese Ice Cream Cup     |    RM5      |");
        System.out.println("|             |     S2    | Fire French Fries          |    RM8      |");
        System.out.println("|             |     S3    | Cheesy Cream Soup          |    RM7      |");
        System.out.println("|      S      |     S4    | Cheese Crackling           |    RM7      |");
        System.out.println("|             |     S5    | Potato Pompom              |    RM7      |");
        System.out.println("|             |     S6    | Richoco Ice Cream Cup      |    RM5      |");
        System.out.println("|             |     S7    | French Fries               |    RM8      |");
        System.out.println("|             |     S8    | Fire Wedges                |    RM8      |");
        System.out.println("|------------------------------------------------------|-------------|");
        System.out.println("|             |     D1    | Pink Lava                  |    RM4      |");
        System.out.println("|      D      |     D2    | Fruitarian Tea             |    RM4      |");
        System.out.println("|             |     D3    | Soft Drink                 |    RM3      |");
        System.out.println("|             |     D4    | Mineral Water              |    RM2      |");
        System.out.println(" --------------------------------------------------------------------");
    }
    
    private static void paymentMethod()
    {
        System.out.println(" ________________________");
        System.out.println("|  Code   |    Method    |");
        System.out.println("|---------|--------------|");
        System.out.println("|    1    |    Cash      |");
        System.out.println("|_________|______________|");
        System.out.println("|    2    |    Card      |");
        System.out.println(" ------------------------");
    }
}