package com.cognixia.FurnitureAPI.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private Integer stock;
	
	@NotBlank
	private Float price;
	
	@NotBlank
	private String imageURL;
	
	/**
	 * @param id
	 * @param name
	 * @param stock
	 * @param price
	 * @param imageURL
	 */
	public Item(Integer id, @NotBlank String name, @NotBlank Integer stock, @NotBlank Float price,
			@NotBlank String imageURL) {
		this.id = id;
		this.name = name;
		this.stock = stock;
		this.price = price;
		this.imageURL = imageURL;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	
}
