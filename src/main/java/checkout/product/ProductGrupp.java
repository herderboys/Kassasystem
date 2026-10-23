package checkout.product;

import java.util.ArrayList;
import java.util.List;

import checkout.discount.Discount;

public class ProductGrupp {
	
	private String name;
	//<Produkt>
	private List<Object> products = new ArrayList<>();
	private List<Discount> discounts = new ArrayList<>();
	
	public ProductGrupp(String name) {
		
		this.name = name;
	}
	
	public String getName() {
		return name;	
	}
	//<Produkt>
	public List<Object> getProducts() {
	    return new ArrayList<>(products);
	}
	
	public void addProduct(Object product){
		this.products.add(product); 
		
	}
	
	 public boolean removeProduct(Object product) {
	    return products.remove(product);
	}
	
	public void addDiscount(Discount discount) {
	   
		this.discounts.add(discount);
	}
	
	public List<Discount> getDiscounts() {
	    return new ArrayList<>(discounts);
	}
}
