package com.ecommercestudy.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommercestudy.exception.ResourceNotFoundException;
import com.ecommercestudy.model.Products;
import com.ecommercestudy.repository.ProductsRepository;

@RestController
@RequestMapping("/api/v1")
public class ProductsController {

	@Autowired
	private ProductsRepository productsRepository;

	@GetMapping("/products")
	public List<Products> getAllProducts() {
		return productsRepository.findAll();
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<Products> getProductsById(@PathVariable(value = "id") Long productId)
			throws ResourceNotFoundException {
		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
		return ResponseEntity.ok().body(product);
	}

	@PostMapping("/products")
	public EntityModel<Products> createProduct(@RequestBody Products product) {

		Products savedProduct = productsRepository.save(product);
		EntityModel<Products> resource = EntityModel.of(savedProduct);

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllProducts());

		resource.add(linkTo.withRel("all-products"));

		// HATEOAS

		return resource;
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Products> updateProducts(@PathVariable(value = "id") Long productId,
			@RequestBody Products productDetails) throws ResourceNotFoundException {
		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));

//		product.setEmailId(employeeDetails.getEmailId());
//		product.setLastName(employeeDetails.getLastName());
//		product.setFirstName(employeeDetails.getFirstName());
		productDetails.setId(product.getId());
		final Products updatedProducts = productsRepository.save(productDetails);
		return ResponseEntity.ok().body(updatedProducts);
	}

	@DeleteMapping("/products/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long productId)
			throws ResourceNotFoundException {
		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
		productsRepository.delete(product);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
