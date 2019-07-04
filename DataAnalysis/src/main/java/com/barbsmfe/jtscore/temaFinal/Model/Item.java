package com.barbsmfe.jtscore.temaFinal.Model;

public class Item {
	
	private int itemId;
	private int quantity;
	private double price;
	
	public Item(int itemId, int quantity, double price) {
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
	}
	
	public int getItemId() {
		return itemId;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getPrice() {
		return price;
	}
}
