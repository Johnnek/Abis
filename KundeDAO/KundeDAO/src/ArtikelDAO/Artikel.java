package ArtikelDAO;

import java.sql.SQLException;
import unserFramework.T;

public class Artikel extends T {
	private Long artikelNummer;
	private String bezeichnung;
	private Double preis;
	
	private ArtikelDAO artikelDAO = ArtikelDAO.getInstance();
	
	public Artikel(Long a, String b, Double d) throws SQLException{
		this.artikelNummer = a;
		this.bezeichnung = b;
		this.preis = d;
		artikelDAO.create(this);
	}
	
	@Override
	protected long getKey() {
		return this.artikelNummer;
	}
	
	public Long getArtikelNummer() {
		return this.artikelNummer;
	}
	
	public void setArtikelNummer(Long a) throws SQLException {
        this.artikelNummer = a;
        artikelDAO.update(this);
    }

    public String getBezeichnung() {
        return this.bezeichnung;
    }

    public void setBezeichnung(String b) throws SQLException {
        this.bezeichnung = b;
        artikelDAO.update(this);
    }

    public Double getPreis() {
        return this.preis;
    }

    public void setPreis(double p) throws SQLException {
        this.preis = p;
        artikelDAO.update(this);
    }
	
	/** Holen eines vorhandenen Kunden aus der Datenhaltungsschicht
	*   �ber die Kundennummer
	 * @throws SQLException 
	**/
	public static Object read(Long a) throws SQLException {
		return ArtikelDAO.getInstance().read(a);
	}
}
