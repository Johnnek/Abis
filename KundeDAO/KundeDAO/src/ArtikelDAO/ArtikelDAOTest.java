package ArtikelDAO;

import java.sql.SQLException;

import ArtikelDAO.Artikel;
import ArtikelDAO.ArtikelDAO;

public class ArtikelDAOTest {
	 public static void main(String[] args) throws SQLException {
	    	//Zugriff auf DAO-Objekt bekommen
	    	ArtikelDAO artikelDAO = ArtikelDAO.getInstance();
	    	
	        System.out.println("Erzeuge einen Kunden");
	        Artikel derArtikel = new Artikel((long) 4711, "Priemer", 1.0);
	        System.out.println("Setze lokale Variable auf NULL und hole Kunden zur�ck");
	        derArtikel = null;
	        derArtikel = (Artikel) Artikel.read((long)4711);
	        System.out.println("Kunde ist " + derArtikel.getArtikelNummer() + " "
	                + derArtikel.getBezeichnung()+", "+derArtikel.getPreis());
	        System.out.println("Aktualisiere den Kunden. Setze Gruppe auf 2");
	        derArtikel.setPreis(2);
	        derArtikel = null;
	        derArtikel = (Artikel) Artikel.read((long)4711);
	        System.out.println("Kunde hat jetzt Gruppe " + derArtikel.getPreis());

	        // Jetzt wird der Kunde gel�scht
	 
	        artikelDAO.delete(derArtikel);
	        derArtikel = null;
	        System.out.println("Versuche den Kunden nach L�schung erneut zu lesen:");
	        derArtikel = (Artikel) Artikel.read((long)4711);
	        System.out.println(derArtikel);
}
}