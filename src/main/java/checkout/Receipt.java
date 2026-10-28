package checkout;

import java.util.HashMap;

public class Receipt {

    private HashMap<Product, Integer> productList;
    private HashMap<Product, Integer> priceList;
    private int totalPrice;

    Receipt(ShoppingCart s){
        productList = s.getCart();
        priceList = s.getPriceList();
        totalPrice = s.getPrice().getAmountInOre();
    }

    public String getReceipt(){


        String receipt = "";

        for(Product p : productList.keySet()){

            receipt = receipt + p.getName() + " " + productList.get(p) + " " + priceList.get(p) + "\n";
         }
        receipt = receipt + "Total price: " + totalPrice;

        return receipt;
    }


}
