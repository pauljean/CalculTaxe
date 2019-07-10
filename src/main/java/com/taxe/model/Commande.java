package com.taxe.model;

import java.util.List;

public class Commande {
	
	private List<Produit> produits;
	
	public Commande() {
		
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}
	
	

}
