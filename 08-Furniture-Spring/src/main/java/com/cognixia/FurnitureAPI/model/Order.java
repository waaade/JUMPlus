package com.cognixia.FurnitureAPI.model;

import java.io.Serializable;
import java.util.List;

import com.cognixia.FurnitureAPI.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
	
	@ManyToOne
	@JoinColumn(name = "itemId", referencedColumnName = "id")
	private Item item;

	/**
	 * @param id
	 * @param user
	 * @param items
	 * @param total
	 */
	public Order(Integer id, User user, Item item) {
		this.id = id;
		this.user = user;
		this.item = item;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Item getItems() {
		return item;
	}

	public void setItems(Item item) {
		this.item = item;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
