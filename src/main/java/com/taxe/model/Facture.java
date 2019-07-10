package com.taxe.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.taxe.service.TaxeService;

public class Facture {

	private TaxeService taxeService;

	private Commande commande;

	private List<Produit> produits;

	private BigDecimal montantTTC;

	private BigDecimal totalTaxe;

	public Facture() {

	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduit(List<Produit> produits) {
		this.produits = produits;
	}

	public BigDecimal getMontantTTC() {
		return montantTTC;
	}

	public void setMontantTTC(BigDecimal montantTTC) {
		this.montantTTC = montantTTC;
	}

	public BigDecimal getTotalTaxe() {
		return totalTaxe;
	}

	public void setTotalTaxe(BigDecimal totalTaxe) {
		this.totalTaxe = totalTaxe;
	}

	public TaxeService getTaxeService() {
		return taxeService;
	}

	public void setTaxeService(TaxeService taxeService) {
		this.taxeService = taxeService;
	}

	public String printProduit(Produit p) {

		BigDecimal prixTTc = taxeService.calculPrixTTC(p);

		return "1 " + p.getNom() + " : " + prixTTc;
	}

	public String editerFacture() {

		produits = commande.getProduits();

		return new StringJoiner("\n").add(produits.stream().map(this::printProduit).collect(Collectors.joining("\n")))
				.add("Montant des taxes : " + getTotalTaxe()).add("Total : " + getMontantTTC()).toString();

	}

}
