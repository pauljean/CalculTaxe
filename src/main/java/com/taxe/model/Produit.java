package com.taxe.model;

import java.math.BigDecimal;

public class Produit {

	private String nom;
	private BigDecimal prixHT;
	private TaxeEnum taxeEnum;
	private OrigineEnum origineEnum;

	public Produit() {

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public BigDecimal getPrixHT() {
		return prixHT;
	}

	public void setPrixHT(BigDecimal prixHT) {
		this.prixHT = prixHT;
	}

	public TaxeEnum getTaxeEnum() {
		return taxeEnum;
	}

	public void setTaxeEnum(TaxeEnum taxeEnum) {
		this.taxeEnum = taxeEnum;
	}

	public OrigineEnum getOrigineEnum() {
		return origineEnum;
	}

	public void setOrigineEnum(OrigineEnum origineEnum) {
		this.origineEnum = origineEnum;
	}

}
