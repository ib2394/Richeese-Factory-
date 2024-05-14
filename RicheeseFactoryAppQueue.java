import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Scanner;

public class RicheeseFactoryAppQueue {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Queue RicheeseQueue = new Queue();
        readDataFromFile(RicheeseQueue);
        
        String answer = "yes";
        while(answer.equalsIgnoreCase("yes"))
        {   
            System.out.print("\n+-----------------------------------+");
            System.out.print("\n           CUSTOMER DETAIL           ");
            System.out.print("\n+-----------------------------------+");
            
            System.out.print("\nEnter Name        : ");
            String custName = in.next();
            
            System.out.print("Enter phone number: ");
            String phoneNum = in.next();
            
            displayMenu();
            System.out.print("Enter menu type   : ");
            char menuType = in.next().charAt(0);
            
            System.out.print("Enter menu id     : ");
            String menuId = in.next();
            
            System.out.print("Enter quantity    : ");
            int quantity = in.nextInt();
    
            paymentMethod();
            System.out.print("Enter payment method : ");
            char paymentMethod = in.next().charAt(0);
            
            RicheeseFactory customer = new RicheeseFactory (custName, phoneNum, menuType, menuId, quantity, paymentMethod);
            RicheeseQueue.enqueue(customer);
            
            System.out.print("\nOrder Again (yes/no)?: ");
            answer=in.next();
        }
        //update data in txt file
        writeDataToFile(RicheeseQueue);
        
        //Searching and update 
        System.out.print("\n+-----------------------------------+");
        System.out.print("\n         SEARCHING CUSTOMER          ");
        System.out.print("\n+-----------------------------------+");
        System.out.print("\nEnter customer phone number to update: ");
        String phoneNoToSearch = in.next();
        
        RicheeseFactory change;
        RicheeseFactory customer3 = (RicheeseFactory) RicheeseQueue.getFront();
        Queue tempQ = new Queue();
        boolean customerFound = false;
        
        while (!RicheeseQueue.isEmpty()) {
            customer3 = (RicheeseFactory) RicheeseQueue.dequeue();
            tempQ.enqueue(customer3);
            if (customer3.getPhoneNum().equalsIgnoreCase(phoneNoToSearch))  {
                System.out.print(customer3.toString());
                System.out.print("\n\n+-----------------------------------+");
                System.out.print("\n        UPDATE MENU SELECTION        ");
                System.out.print("\n+-----------------------------------+");
                System.out.print("\nEnter New Menu Type  : ");
                char updateMenuType = in.next().charAt(0);
                customer3.setMenuType(updateMenuType);
                
                System.out.print("\nEnter New Menu ID    : ");
                String updateMenuId = in.next();
                customer3.setMenuId(updateMenuId);
                
                System.out.print("\nEnter New Quantity   : ");
                int updateQty = in.nextInt();
                customer3.setQuantity(updateQty);
                
                change = customer3;
                System.out.print(change.toString());
                customerFound = true;
                break;//Stop searching once found
            }
        }
        if (!customerFound) {
            System.out.println("Customer not found with the specified phone number.");
        }
        while(!tempQ.isEmpty()){
            customer3 = (RicheeseFactory) tempQ.dequeue();
            RicheeseQueue.enqueue(customer3);
        }
        
        //counting
        //count and display the total number of customers who visit Richeese Factory//
        int specCust=0;
        RicheeseFactory RF = null;
        
        while (!RicheeseQueue.isEmpty()){
            RF = (RicheeseFactory) RicheeseQueue.dequeue();
            tempQ.enqueue(RF);
            
            if (RF.getCustName() != null){
                specCust++;
            }
        }
        System.out.print("\n\n+-----------------------------------+");
        System.out.print("\n          TOTAL OF CUSTOMER          ");
        System.out.print("\n+-----------------------------------+");
        System.out.println("\nTotal of customer that visit Richeese Factory : "+ specCust);
        
        while(!tempQ.isEmpty()){
            RF = (RicheeseFactory) tempQ.dequeue();
            RicheeseQueue.enqueue(RF);
        }
    
        //Higher customer price(Maximum)
        RicheeseFactory highest = null;
        double maxTotal =  0;
        tempQ = new Queue();
        
        while(!RicheeseQueue.isEmpty())
        {
            RicheeseFactory cMAX = (RicheeseFactory) RicheeseQueue.dequeue();  
            
            double currentTotal = cMAX.calculateTotal();
            if(currentTotal > maxTotal)
            {
                highest = cMAX;
                maxTotal  = currentTotal;
            }
            tempQ.enqueue(cMAX);
        }
        System.out.print("\n\n+-----------------------------------+");
        System.out.print("\n         MAXIMUM TOTAL PRICE         ");
        System.out.print("\n+-----------------------------------+");
        System.out.println("\n--Details of customer with the most total price-- " +"\n" + highest.toString());
        while(!tempQ.isEmpty()){
            RicheeseFactory customer = (RicheeseFactory) tempQ.dequeue();
            RicheeseQueue.enqueue(customer);
        }
        
        //Process to calculate the total revenue(Summation)
        double totalRevenue = 0;
        int count = 0;
        RicheeseFactory custSum;
        while(!RicheeseQueue.isEmpty()){
            custSum= (RicheeseFactory) RicheeseQueue.dequeue();
            tempQ.enqueue(custSum);
            totalRevenue += custSum.calculateTotal();
        }
        while(!tempQ.isEmpty()){
            custSum = (RicheeseFactory) tempQ.dequeue();
            RicheeseQueue.enqueue(custSum);
        }
        
        System.out.print("\n\n+-----------------------------------+");
        System.out.print("\n           TOTAL REVENUE             ");
        System.out.print("\n+-----------------------------------+");
        System.out.println("\nThe Total Revenue: RM" + totalRevenue);
        
        //Removal
        RicheeseFactory cust;
        Queue cashQ = new Queue();
        Queue cardQ = new Queue();
        while(!RicheeseQueue.isEmpty()){
            cust=(RicheeseFactory) RicheeseQueue.dequeue();
            if(cust.getPaymentMethod()=='1'){
                cashQ.enqueue(cust);
            }
            else{
                cardQ.enqueue(cust);
            }
        }
        
        int cashNum = 1;
        RicheeseFactory custRF = null;
        Queue tempQueue = new Queue();
        System.out.print("\n+-----------------------------------+");
        System.out.print("\n             CASH LIST               ");
        System.out.print("\n+-----------------------------------+");
        while (!cashQ.isEmpty()){
            custRF = (RicheeseFactory) cashQ.dequeue();
            tempQueue.enqueue(custRF);
            System.out.println("\n" + cashNum++ + ". " + custRF.getCustName());
        }
        while(!tempQueue.isEmpty()){
            custRF = (RicheeseFactory) tempQueue.dequeue();
            cashQ.enqueue(custRF);
        }
        
        int cardNum = 1;
        System.out.print("\n+-----------------------------------+");
        System.out.print("\n             CARD LIST               ");
        System.out.print("\n+-----------------------------------+");
        while (!cardQ.isEmpty()){
            custRF = (RicheeseFactory) cardQ.dequeue();
            tempQueue.enqueue(custRF);
            System.out.println("\n" + cardNum++ + ". " + custRF.getCustName());
        }
        while(!tempQueue.isEmpty()){
            custRF = (RicheeseFactory) tempQueue.dequeue();
            cardQ.enqueue(custRF);
        }
    }
    
    private static void writeDataToFile(Queue RicheeseQueue) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("RicheeseQueue.txt"))) {
            RicheeseFactory customer = (RicheeseFactory) RicheeseQueue.getFront();

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
                customer = (RicheeseFactory) RicheeseQueue.getNext();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void readDataFromFile(Queue RicheeseQueue) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("RicheeseQueue.txt"));
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
                RicheeseQueue.enqueue(customer);

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