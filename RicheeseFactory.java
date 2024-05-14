public class RicheeseFactory
{  
    //attribute
    private String custName;
    private String phoneNum;
    private char paymentMethod;
    private char menuType;
    private String menuId;
    private int quantity;
    
    //constructor
    public RicheeseFactory(){}
    
    public RicheeseFactory (String name, String number, char type, String id, int qty, char pmethod)
    {
        custName = name;
        phoneNum = number;
        paymentMethod = pmethod;
        menuType = type;
        menuId = id;
        quantity = qty;
    }
    
    //mutator
    public void setCustName(String name) {custName = name;}
    public void setPhoneNum(String number) {phoneNum = number;}
    public void setPaymentMethod(char pmethod) {paymentMethod = pmethod;}
    public void setMenuType(char type) {menuType = type;}
    public void setMenuId(String id) {menuId = id;}
    public void setQuantity(int qty) {quantity = qty;}
    
    //accessor
    public String getCustName() {return custName;}
    public String getPhoneNum() {return phoneNum;}
    public char getPaymentMethod() {return paymentMethod;}
    public char getMenuType() {return menuType;}
    public String getMenuId() {return menuId;}
    public int getQuantity() {return quantity;}
    
    public double MenuPrice() {
        double price = 0.0;
        if (getMenuType() == 'F') {
            if (getMenuId().equalsIgnoreCase("F1") || getMenuId().equalsIgnoreCase("F2") || getMenuId().equalsIgnoreCase("F3")){
                price = 12.0;
            }else if (getMenuId().equalsIgnoreCase("F5") || getMenuId().equalsIgnoreCase("F6") || getMenuId().equalsIgnoreCase("F7")){
                price = 25.00;
            }else{
                price = 18.00;
            }
        }else if (getMenuType() == 'S'){
            if (getMenuId().equalsIgnoreCase("S1") || getMenuId().equalsIgnoreCase("S6")){
                price = 5.0;
            }else if (getMenuId().equalsIgnoreCase("S3") || getMenuId().equalsIgnoreCase("S4") || getMenuId().equalsIgnoreCase("S5")){
                price = 7.0;
            }else{
                price = 8.0;
            }
        }else{
            if (getMenuId().equalsIgnoreCase("D1") || getMenuId().equalsIgnoreCase("D2")){
                price = 4.0;
            }else if (getMenuId().equalsIgnoreCase("D3")){
                price = 3.0;
            }else{
                price = 2.0;
            }
        }
        return price;
    }
    
    //calculate total price per customer
    public double calculateTotal() {
        double total = getQuantity()*MenuPrice();
        return total;
    }
    
    //printer
    public String toString() {
    return  "\n+-----------------------------------+" +
            "\n          CUSTOMER DETAILS           " +
            "\n+-----------------------------------+" +
            "\nCustomer name        : " + custName + 
            "\nCustomer phone number: " + phoneNum + 
            "\nCustomer menu type   : " + menuType +
            "\nCustomer menu ID     : " + menuId +
            "\nQuantity             : " + quantity +
            "\nThe total price      : " + calculateTotal() +
            "\nPayment method       : " + paymentMethod;
    }
}