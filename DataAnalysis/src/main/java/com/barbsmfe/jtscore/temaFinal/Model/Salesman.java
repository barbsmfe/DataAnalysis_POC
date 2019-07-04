package com.barbsmfe.jtscore.temaFinal.Model;

public class Salesman {

	private String name;
	private Long cpf;
	private double salary;
	private int numberOfSales;
		
	public Salesman(String name, Long cpf, double salary) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.salary = salary;
		this.numberOfSales = 0;
	}

	public Long getCpf() {
		return cpf;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public int getNumberOfSales() {
		return numberOfSales;
	}

	public String getName() {
		return name;
	}

	public String getIdCode() {
		return IdTypes.ID_CODE_SALESMAN;
	}
	
	public void setNumberOfSales() {
		this.numberOfSales += 1;
	}
}
