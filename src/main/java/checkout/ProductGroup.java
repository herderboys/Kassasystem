package checkout;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class ProductGroup {
	
	private String name;
	//<Produkt>
	private List<Object> products = new ArrayList<>();
	
	public ProductGroup(String name) {
		
		this.name = name;
	}
	
	public String getName() {
		return name;	
	}
	//<Produkt>
	public List<Object> getProducts() {
	    return new ArrayList<>(products);
	}
	
	public void AddProductToProductGroup(Object product){
		
		this.products.add(product); 
		
	}
}
