package com.taxe.model;

import java.math.BigDecimal;

public enum TaxeEnum {
	
	courant(new BigDecimal("0.1")),
	Menager(new BigDecimal("0.00"));
	
	
	private BigDecimal taxeValue;
	
	TaxeEnum(BigDecimal taxeValue) {
		this.taxeValue=taxeValue;
	}
	
	public BigDecimal getValue(){
		return taxeValue;
	}

}
