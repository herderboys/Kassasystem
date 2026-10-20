package checkout.product;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class ProductGrupp {
	
	private String namn;
	//<Produkt>
	private List<Object> products = new ArrayList<>();
	
	public ProductGrupp(String namn) {
		
		this.namn = namn;
	}
	
	public String getName() {
		return namn;	
	}
	//<Produkt>
	public List<Object> getProducts() {
	    return new ArrayList<>(products);
	}
	
	public void AddProductToProductGrupp(Object product){
		
		this.products.add(product); 
		
	}
}
