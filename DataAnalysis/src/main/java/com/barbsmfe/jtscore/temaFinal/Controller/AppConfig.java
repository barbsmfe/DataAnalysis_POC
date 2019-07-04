package com.barbsmfe.jtscore.temaFinal.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.barbsmfe.jtscore.temaFinal.Main.DataAnalyzerInitializer;
import com.barbsmfe.jtscore.temaFinal.Model.Customer;
import com.barbsmfe.jtscore.temaFinal.Model.Item;
import com.barbsmfe.jtscore.temaFinal.Model.Sale;
import com.barbsmfe.jtscore.temaFinal.Model.Salesman;

@Configuration
@ComponentScan(basePackages = "com.barbsmfe.jtscore.temaFinal")
public class AppConfig {

	@Bean
	@Scope("prototype")
	public FileManager fileManager() throws IOException {
		return new FileManager();
	}
	
	@Bean
	@Scope("prototype")
	public DataAnalyzerInitializer dataAnalyzerInitializer() {
		return new DataAnalyzerInitializer();
	}
	
	@Bean
	@Scope("prototype")
	public DataAnalyzer dataAnalyzer() throws IOException {
		return new DataAnalyzer();
	}

	@Bean
	@Scope("prototype")
	public Customer customer(@Value("#{customer.cnpj}") final Long cnpj, @Value("#{customer.name}") final String name,
			@Value("#{customer.businessArea}") final String businessArea) {
		return new Customer(cnpj, name, businessArea);
	}

	@Bean
	@Scope("prototype")
	public Sale sale(@Value("#{sale.saleIdCode}") final int saleIdCode,
			@Value("#{sale.listOfSoldItems}") final Optional<List<Item>> listOfSoldItems,
			@Value("#{sale.businessArea}") final String salesmanName) {
		return new Sale(saleIdCode, listOfSoldItems, salesmanName);
	}

	@Bean
	@Scope("prototype")
	public Salesman salesman(@Value("#{salesman.name}") final String name, @Value("#{salesman.cpf}") final Long cpf,
			@Value("#{salesman.salary}") final double salary) {
		return new Salesman(name, cpf, salary);
	}

	@Bean
	@Scope("prototype")
	public Item item(@Value("#{item.itemId}") final int itemId, @Value("#{item.quantity}") final int quantity,
			@Value("#{item.price}") final double price) {
		return new Item(itemId, quantity, price);
	}
}
