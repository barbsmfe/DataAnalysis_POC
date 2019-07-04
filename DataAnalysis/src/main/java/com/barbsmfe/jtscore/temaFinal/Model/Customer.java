package com.barbsmfe.jtscore.temaFinal.Model;

public class Customer {
	
	private Long cnpj;
	private String name;
	private String businessArea;
	
	public Customer(Long cnpj, String name, String businessArea) {
		this.cnpj = cnpj;
		this.name = name;
		this.businessArea = businessArea;
	}

	public Long getCnpj() {
		return cnpj;
	}
	
	public String getBusinessArea() {
		return businessArea;
	}

	public String getName() {
		return name;
	}

	public String getIdCode() {
		return IdTypes.ID_CODE_CUSTOMER;
	}
}
