package checkout;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class ProductGrupp {
	
	private String name;
	//<Produkt>
	private List<Object> products = new ArrayList<>();
	
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
	
	public void AddProductToProductGrupp(Object product){
		
		this.products.add(product); 
		
	}
}
