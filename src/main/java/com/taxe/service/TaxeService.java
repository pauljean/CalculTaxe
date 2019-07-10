package com.taxe.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taxe.model.Commande;
import com.taxe.model.Facture;
import com.taxe.model.OrigineEnum;
import com.taxe.model.Produit;

@Service
public class TaxeService {

	private Logger LOGGER = LoggerFactory.getLogger(TaxeService.class);

	private static final BigDecimal PLAGE_MAX = new BigDecimal("0.05");
	private static final BigDecimal PLAGE_MIN = new BigDecimal("0.00");

	public Facture calculFacture(Commande commande) {

		Facture facture = new Facture();

		BigDecimal montantTTC;

		BigDecimal totalTaxe;

		List<Produit> produits = commande.getProduits();

		montantTTC = produits.stream().map(this::calculPrixTTC).reduce(BigDecimal::add).get();

		totalTaxe = produits.stream().map(this::calculTaxe).reduce(BigDecimal::add).get();

		facture.setCommande(commande);
		facture.setMontantTTC(montantTTC);
		facture.setTotalTaxe(totalTaxe);
		facture.setTaxeService(this);

		return facture;

	}

	/**
	 * permet de calculer le prix ttc d'un produit
	 * @param produit
	 * @return
	 */	
	public BigDecimal calculPrixTTC(Produit produit) {

		BigDecimal prixTTC;
		BigDecimal taxeBrute;

		BigDecimal prixHT = produit.getPrixHT();
		OrigineEnum origineEnum = produit.getOrigineEnum();
		BigDecimal taxeValue = produit.getTaxeEnum().getValue();

		taxeBrute = prixHT.multiply(taxeValue).add(prixHT.multiply(origineEnum.getValue()));

		prixTTC = prixHT.add(arrondi(taxeBrute));

		return prixTTC;

	}

	/**
	 * permet de calculer le montant de la taxe d'un produit
	 * @param produit
	 * @return
	 */
	public BigDecimal calculTaxe(Produit produit) {

		BigDecimal taxe;
		BigDecimal taxeBrute;

		BigDecimal prixHT = produit.getPrixHT();
		OrigineEnum origineEnum = produit.getOrigineEnum();
		BigDecimal taxeValue = produit.getTaxeEnum().getValue();

		taxeBrute = prixHT.multiply(taxeValue).add(prixHT.multiply(origineEnum.getValue()));

		taxe = arrondi(taxeBrute);

		return taxe;

	}

	
	/***
	 * permet d'arrondi
	 * @param amount
	 * @return
	 */
	public BigDecimal arrondi(BigDecimal amount) {

		BigDecimal value = amount.setScale(2, RoundingMode.CEILING);

		BigDecimal reminder = value.remainder(PLAGE_MAX);

		BigDecimal sub = PLAGE_MAX.subtract(reminder);

		BigDecimal result = reminder.equals(PLAGE_MIN) ? value : value.add(sub);

		return result;
	}

}
