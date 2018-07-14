package ArtikelDAO;

import java.sql.SQLException;

import ArtikelDAO.Artikel;
import ArtikelDAO.ArtikelDAO;

public class ArtikelDAOTest {
	 public static void main(String[] args) throws SQLException {
	    	//Zugriff auf DAO-Objekt bekommen
	    	ArtikelDAO artikelDAO = ArtikelDAO.getInstance();
	    	artikelDAO.getConnection();
	    	
	        System.out.println("Erzeuge einen Artikel");
	        Artikel derArtikel = new Artikel((long) 4711, "Priemer", 1.0);
	        System.out.println("Setze lokale Variable auf NULL und hole Artikel zur�ck");
	        derArtikel = null;
	        derArtikel = (Artikel) Artikel.read((long)4711);
	        System.out.println("Artikel ist " + derArtikel.getArtikelNummer() + " "
	                + derArtikel.getBezeichnung()+", "+derArtikel.getPreis());
	        System.out.println("Aktualisiere den Artikel. Setze Preis auf 2");
	        derArtikel.setPreis(2);
	        derArtikel = null;
	        derArtikel = (Artikel) Artikel.read((long)4711);
	        System.out.println("Artikel hat jetzt den Preis " + derArtikel.getPreis());

	        // Jetzt wird der Kunde gel�scht
	        
	        artikelDAO.delete(derArtikel);
	        derArtikel = null;
	        System.out.println("Versuche den Artikel nach L�schung erneut zu lesen:");
	        //derArtikel = (Artikel) Artikel.read((long)4711);
	        System.out.println(derArtikel);
	        
}
}