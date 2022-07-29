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
import com.ecommercestudy.model.Customer;
import com.ecommercestudy.repository.CustomerRepository;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customer")
	public List<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId)
			throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
		return ResponseEntity.ok().body(customer);
	}

	@PostMapping("/customer")
	public EntityModel<Customer> createCustomer(@RequestBody Customer customer) {

		Customer savedCustomer = customerRepository.save(customer);
		EntityModel<Customer> resource = EntityModel.of(savedCustomer);

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllCustomer());

		resource.add(linkTo.withRel("all-customers"));

		// HATEOAS

		return resource;
	}

	@PutMapping("/customer/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerId,
			@RequestBody Customer customerDetails) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));

//		product.setEmailId(employeeDetails.getEmailId());
//		product.setLastName(employeeDetails.getLastName());
//		product.setFirstName(employeeDetails.getFirstName());
		customerDetails.setId(customer.getId());
		final Customer updatedCustomer = customerRepository.save(customerDetails);
		return ResponseEntity.ok().body(updatedCustomer);
	}

	@DeleteMapping("/customer/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId)
			throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
		customerRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
