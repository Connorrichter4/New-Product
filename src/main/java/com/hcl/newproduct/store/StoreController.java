package com.hcl.newproduct.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {

	@Autowired
	ProductEntityCrudRepository productEntityCrudRepository;
	
	@GetMapping(path = "/createProduct")
	String showProductForm() {
		
		
		String output = "<form action='' method='POST'>";
		output += "Product Name: <input name='name' type='text' /><br />";
		output += "Product Price: <input name='price' type='text' /><br />";
		output += "<input type='submit' value='submit' /><br />";
		output += "</form>";
		
				
		return output;
	}
	
	@PostMapping(path = "/createProduct")
	void createProduct(@ModelAttribute ProductEntity product) {
		if(product == null || product.getName() == null) {
			throw new RuntimeException("Product name is required");
		}
		if (product.getPrice() < 0) {
			throw new RuntimeException("Price cannot be negative");
		}
		productEntityCrudRepository.save(product);
	}
	
	@GetMapping(path= "/")
	String home() {
		
		Iterable<ProductEntity> products = productEntityCrudRepository.findAll();
		
		String showProducts = "<h2>Showing All Products</h2>";
		for(ProductEntity p: products) {
			showProducts = showProducts + "<p>" + p.getName() + " - $" + p.getPrice() + "</p>";
		}
		
		return showProducts;
		
	}
	
}
