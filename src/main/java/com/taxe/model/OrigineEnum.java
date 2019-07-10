package com.taxe.model;

import java.math.BigDecimal;

public enum OrigineEnum {
	
	Locale(new BigDecimal("0.00")),
	Importer(new BigDecimal("0.05"));
	
	private BigDecimal taxeValue;
	
	OrigineEnum(BigDecimal taxeValue) {
		this.taxeValue=taxeValue;
	}
	
	public BigDecimal getValue(){
		return taxeValue;
	}

}
