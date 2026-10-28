package checkout;

import java.util.ArrayList;
import java.util.Collections;
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

    private ArrayList<String> sortProducts(){

        ArrayList<String> productArray = new ArrayList<String>();

        for(Product p : productList.keySet()){

            productArray.add(p.getName() + " " + productList.get(p) + " " + priceList.get(p));

        }
        Collections.sort(productArray);
        return productArray;
    }

    public String getReceipt(){

        ArrayList<String> productList = sortProducts();
        String receipt = "";

        for(String s : productList){

            receipt = receipt + s + "\n";
         }
        receipt = receipt + "Total price: " + totalPrice;

        return receipt;
    }


}
