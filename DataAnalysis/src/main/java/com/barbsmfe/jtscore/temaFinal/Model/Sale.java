package com.barbsmfe.jtscore.temaFinal.Model;

import java.util.List;
import java.util.Optional;

public class Sale {
	
	private int saleIdCode;
	private String salesmanName;
	private Optional<List<Item>> listOfSoldItems;
	
	public Sale(int saleIdCode, Optional<List<Item>> listOfSoldItems, String salesmanName) {
		this.saleIdCode = saleIdCode;
		this.listOfSoldItems = listOfSoldItems;
		this.salesmanName = salesmanName;
	}
	
	public int getSaleIdCode() {
		return saleIdCode;
	}
	
	public Optional<List<Item>> getListOfSoldItems(){
		return listOfSoldItems;
	}
	
	public Optional<Item> getItem(int itemId) {
		Optional<Item> itemFound = Optional.empty();
		if(listOfSoldItems.isPresent()) {
			itemFound = listOfSoldItems.get().stream().filter((item) -> item.getItemId()==itemId).findAny();	
		}
		return itemFound;
	}
	
	public boolean addItemToTheList(Item item) {
		if(listOfSoldItems.isPresent()) {
			listOfSoldItems.get().add(item);
			return true;
		}
		return false;
	}

	public String getName() {
		return salesmanName;
	}

	public String getIdCode() {
		return IdTypes.ID_CODE_SALE;
	}
	
	public double getSumOfSalePrice() {
		double[] sum = {0};
		if(listOfSoldItems.isPresent()) {
			listOfSoldItems.get().forEach(item -> {
				sum[0] = sum[0] + item.getPrice();
			});
		}
		return sum[0];
	}
}
