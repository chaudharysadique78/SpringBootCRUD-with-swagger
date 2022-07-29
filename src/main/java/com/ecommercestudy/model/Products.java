package com.ecommercestudy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false)
	private String productName;
	@Column(nullable = false)
	private double produtPrice;
	@Column(nullable = false)
	private String brand;

	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Products(long id, String productName, double produtPrice, String brand) {
		super();
		this.id = id;
		this.productName = productName;
		this.produtPrice = produtPrice;
		this.brand = brand;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProdutPrice() {
		return produtPrice;
	}

	public void setProdutPrice(double produtPrice) {
		this.produtPrice = produtPrice;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Products [id=" + id + ", productName=" + productName + ", produtPrice=" + produtPrice + ", brand="
				+ brand + "]";
	}

}
