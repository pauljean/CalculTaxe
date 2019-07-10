package com.taxe.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.taxe.model.Commande;
import com.taxe.model.Facture;
import com.taxe.model.OrigineEnum;
import com.taxe.model.Produit;
import com.taxe.model.TaxeEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaxeServiceTest {

	@Autowired
	TaxeService taxeService;

	private Commande commande1;
	private Commande commande2;
	private Commande commande3;


	@Before
	public void init() {

		commande1 = new Commande();
		List<Produit> produits1 = new ArrayList<>();
		Produit p11 = new Produit();
		Produit p12 = new Produit();
		Produit p13 = new Produit();

		p11.setNom("Livre");
		p11.setPrixHT(new BigDecimal("12.49"));
		p11.setTaxeEnum(TaxeEnum.Menager);
		p11.setOrigineEnum(OrigineEnum.Locale);

		p12.setNom("CD musical");
		p12.setPrixHT(new BigDecimal("14.99"));
		p12.setTaxeEnum(TaxeEnum.courant);
		p12.setOrigineEnum(OrigineEnum.Locale);

		p13.setNom("barre de chocolat");
		p13.setPrixHT(new BigDecimal("0.85"));
		p13.setTaxeEnum(TaxeEnum.Menager);
		p13.setOrigineEnum(OrigineEnum.Locale);

		produits1.add(p11);
		produits1.add(p12);
		produits1.add(p13);

		commande1.setProduits(produits1);

		///////////////// Commande2//////////////////////////

		commande2 = new Commande();
		List<Produit> produits2 = new ArrayList<>();
		Produit p21 = new Produit();
		Produit p22 = new Produit();

		p21.setNom("boîte de chocolats importée");
		p21.setPrixHT(new BigDecimal("10.00"));
		p21.setTaxeEnum(TaxeEnum.Menager);
		p21.setOrigineEnum(OrigineEnum.Importer);

		p22.setNom("flacon de parfum importé");
		p22.setPrixHT(new BigDecimal("47.50"));
		p22.setTaxeEnum(TaxeEnum.courant);
		p22.setOrigineEnum(OrigineEnum.Importer);

		produits2.add(p21);
		produits2.add(p22);

		commande2.setProduits(produits2);

		//////////////// Commande3///////////////////////////

		commande3 = new Commande();
		List<Produit> produits3 = new ArrayList<>();
		Produit p31 = new Produit();
		Produit p32 = new Produit();
		Produit p33 = new Produit();
		Produit p34 = new Produit();

		p31.setNom("flacon de parfum importé ");
		p31.setPrixHT(new BigDecimal("27.99"));
		p31.setTaxeEnum(TaxeEnum.courant);
		p31.setOrigineEnum(OrigineEnum.Importer);

		p32.setNom("flacon de parfum");
		p32.setPrixHT(new BigDecimal("18.99"));
		p32.setTaxeEnum(TaxeEnum.courant);
		p32.setOrigineEnum(OrigineEnum.Locale);

		p33.setNom("boîte de pilules contre la migraine");
		p33.setPrixHT(new BigDecimal("9.75"));
		p33.setTaxeEnum(TaxeEnum.Menager);
		p33.setOrigineEnum(OrigineEnum.Locale);

		p34.setNom("boîte de chocolats importés");
		p34.setPrixHT(new BigDecimal("11.25"));
		p34.setTaxeEnum(TaxeEnum.Menager);
		p34.setOrigineEnum(OrigineEnum.Importer);

		produits3.add(p31);
		produits3.add(p32);
		produits3.add(p33);
		produits3.add(p34);

		commande3.setProduits(produits3);

	}

	@Test
	public void calculFactureTest() {
		
		Facture facture1=taxeService.calculFacture(commande1);
		
		assertEquals(new BigDecimal("29.83"), facture1.getMontantTTC());
		assertEquals(new BigDecimal("1.50"), facture1.getTotalTaxe());
		
		System.out.println("########### Output 1 ###########");
		System.out.println(facture1.editerFacture());
		
		Facture facture2=taxeService.calculFacture(commande2);
		
		assertEquals(new BigDecimal("65.15"), facture2.getMontantTTC());
		assertEquals(new BigDecimal("7.65"), facture2.getTotalTaxe());
		
		System.out.println("########### Output 2 ###########");
		System.out.println(facture2.editerFacture());
		
		Facture facture3=taxeService.calculFacture(commande3);
		
		assertEquals(new BigDecimal("74.68"), facture3.getMontantTTC());
		assertEquals(new BigDecimal("6.70"), facture3.getTotalTaxe());
		
		System.out.println("########### Output 3 ###########");
		System.out.println(facture3.editerFacture());
	}
	
	@Test
	public void calculPrixTTC(){
		
		Produit p = new Produit();

		p.setNom("flacon de parfum importé ");
		p.setPrixHT(new BigDecimal("27.99"));
		p.setTaxeEnum(TaxeEnum.courant);
		p.setOrigineEnum(OrigineEnum.Importer);
		
		assertEquals(new BigDecimal("32.19"), taxeService.calculPrixTTC(p));
		
	}
	
	@Test
	public void calculTaxeTest(){
		
		Produit p = new Produit();

		p.setNom("flacon de parfum importé ");
		p.setPrixHT(new BigDecimal("27.99"));
		p.setTaxeEnum(TaxeEnum.courant);
		p.setOrigineEnum(OrigineEnum.Importer);
		
		assertEquals(new BigDecimal("4.20"), taxeService.calculTaxe(p));
	}


	@Test
	public void arrondiTest() {

		assertEquals(new BigDecimal("1.00"), taxeService.arrondi(new BigDecimal(0.99)));
		assertEquals(new BigDecimal("1.00"), taxeService.arrondi(new BigDecimal(1.00)));
		assertEquals(new BigDecimal("1.05"), taxeService.arrondi(new BigDecimal(1.01)));
		assertEquals(new BigDecimal("1.05"), taxeService.arrondi(new BigDecimal(1.02)));
		assertEquals(new BigDecimal("0.85"), taxeService.arrondi(new BigDecimal(0.85)));
	}

}
