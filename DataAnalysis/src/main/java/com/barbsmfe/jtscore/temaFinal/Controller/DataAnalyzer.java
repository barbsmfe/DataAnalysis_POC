package com.barbsmfe.jtscore.temaFinal.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.barbsmfe.jtscore.temaFinal.Model.*;

public class DataAnalyzer {

	@Autowired
	private FileManager fileManager;

	private List<String> dataList;
	private List<Salesman> salesmanList;
	private List<Customer> customerList;
	private List<Sale> salesList;
	private ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	private final static int DELIMITER_POSITION = 4;

	public DataAnalyzer() throws IOException {
		this.dataList = new ArrayList<String>();
		this.salesmanList = new ArrayList<Salesman>();
		this.customerList = new ArrayList<Customer>();
		this.salesList = new ArrayList<Sale>();
	}

	public List<String> retrieveDataFromFile() throws IOException {
		return fileManager.readAnalysisFiles();
	}

	public void analyzeData() throws IOException {
		dataList = retrieveDataFromFile();
		if (dataList.size() > 0) {
			dataList.forEach(objectInformation -> {
				createObjectsFromListOfData(objectInformation);
			});
			fileManager.updateFinalAnalysisFile(returnFinalInformations());
		}
	}

	public Optional<Integer> getIdOfMostExpensiveSale() {
		Optional<Sale> saleOfWorstSalesman = salesList.stream()
				.max(Comparator.comparing((Sale sale) -> sale.getSumOfSalePrice()));

		if (saleOfWorstSalesman.isPresent()) {
			return Optional.of(saleOfWorstSalesman.get().getSaleIdCode());
		} else {
			return Optional.empty();
		}
	}

	public Optional<String> getNameOfWorstSalesman() {
		Optional<Sale> saleOfWorstSalesman = salesList.stream()
				.min(Comparator.comparing((Sale sale) -> sale.getSumOfSalePrice()));

		if (saleOfWorstSalesman.isPresent()) {
			return Optional.of(saleOfWorstSalesman.get().getName());
		} else {
			return Optional.empty();
		}
	}

	private Character findSeparatorInData(String data) {
		return data.charAt(3);
	}

	private void createObjectsFromListOfData(String data) {
		Character separator = findSeparatorInData(data);
		List<String> foundInformationForObjectCreation = separateData(separator, data);
		if (foundInformationForObjectCreation.size() > 0) {
			if (foundInformationForObjectCreation.get(0).equals(IdTypes.ID_CODE_SALESMAN)) {
				createSalesman(foundInformationForObjectCreation);
			} else if (foundInformationForObjectCreation.get(0).equals(IdTypes.ID_CODE_CUSTOMER)) {
				createCustomer(foundInformationForObjectCreation);
			} else if (foundInformationForObjectCreation.get(0).equals(IdTypes.ID_CODE_SALE)) {
				createSale(foundInformationForObjectCreation);
			}
		}
	}

	private void createCustomer(List<String> customerInformation) {
		Long cnpj = Long.parseLong(customerInformation.get(1));
		String name = customerInformation.get(2);
		String businessArea = customerInformation.get(3);

		Customer customer = applicationContext.getBean(Customer.class, cnpj, name, businessArea);
		customerList.add(customer);
	}

	private void createSalesman(List<String> salesmanInformation) {
		String cpf = salesmanInformation.get(1);
		String name = salesmanInformation.get(2);
		String salary = salesmanInformation.get(3);

		Salesman salesman = applicationContext.getBean(Salesman.class, name, Long.parseLong(cpf),
				Double.parseDouble(salary));
		salesmanList.add(salesman);
	}

	private void createSale(List<String> saleInformation) {
		int saleIdCode = Integer.parseInt(saleInformation.get(1));
		List<Item> listOfSoldItems = createListOfItems(saleInformation.get(2));
		String salesmanName = saleInformation.get(3);

		Sale sale = applicationContext.getBean(Sale.class, saleIdCode, listOfSoldItems, salesmanName);
		salesList.add(sale);
	}

	private Optional<Item> createItem(String itemInformation) {
		List<String> items = separateData("-", itemInformation);
		Optional<Item> item = Optional.empty();
		if (items.size() > 0) {
			int itemId = Integer.parseInt(items.get(0));
			int itemQuantity = Integer.parseInt(items.get(1));
			double itemPrice = Double.parseDouble(items.get(2));

			item = Optional.of(applicationContext.getBean(Item.class, itemId, itemQuantity, itemPrice));
		}
		return item;
	}

	private List<Item> createListOfItems(String listOfItems) {
		listOfItems = listOfItems.replace("[", "").replace("]", "");
		List<Item> itemsListProcessed = new ArrayList<Item>();
		List<String> itemsListSeparated = separateData(",", listOfItems);
		if (itemsListSeparated.size() > 0) {
			itemsListSeparated.forEach(item -> {
				itemsListProcessed.add(createItem(item).get());
			});
		}
		return itemsListProcessed;
	}

	private List<String> separateData(String separator, String data) {
		List<String> foundInformation = Arrays.stream(data.split(separator)).collect(Collectors.toList());
		return foundInformation;
	}

	private List<String> separateData(Character separator, String data) {
		List<String> listOfDataInformation = new ArrayList<String>();
		int firstDelimiter = DELIMITER_POSITION;
		int secondDelimiter = data.length();

		listOfDataInformation.add(data.substring(0, firstDelimiter - 1));
		
		for (int i = 4; i < data.length(); i++) {
			if (separator.equals(data.charAt(i))) {
				String informationFound = data.substring(firstDelimiter, i - 1);
				listOfDataInformation.add(informationFound);
				firstDelimiter = i + 1;
				break;
			}
		}

		for (int i = data.length() - 1; i >= 0; i--) {
			if (separator.equals(data.charAt(i))) {
				String informationFound = data.substring(firstDelimiter, i);
				listOfDataInformation.add(informationFound);
				firstDelimiter = i + 1;
				break;
			}
		}

		listOfDataInformation.add(data.substring(firstDelimiter, secondDelimiter));

		return listOfDataInformation;
	}

	public Optional<List<String>> returnFinalInformations() {
		Optional<List<String>> finalInformations = Optional.of(new ArrayList<String>());
		finalInformations.get().add("Amount of clients in input file: " + this.getAmountOfCustomers());
		finalInformations.get().add("Amount of salesmen in the input file: " + this.getAmountOfSalesmen());
		finalInformations.get().add("ID of the most expensive sale: " + this.getIdOfMostExpensiveSale().get());
		finalInformations.get().add("Worst salesman ever: " + this.getNameOfWorstSalesman().get());

		return finalInformations;
	}

	public int getAmountOfCustomers() {
		return customerList.size();
	}

	public int getAmountOfSalesmen() {
		return salesmanList.size();
	}
}
