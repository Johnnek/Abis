package kundeDAO;

public class Kunde {
    
    private Long kundennummer;
    private String name;
    private int kundengruppe;
    //für Zugriff KundeDAO
    private KundeDAO kundeDAO = KundeDAO.getInstance();
    
    public Kunde(Long kdnr, String nme, int kndngrpp) {
        this.kundennummer = kdnr;
        this.name = nme;
        this.kundengruppe = kndngrpp;
        kundeDAO.create(this);
    }

    public Long getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(Long kundennummer) {
        this.kundennummer = kundennummer;
        kundeDAO.update(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        kundeDAO.update(this);
    }

    public int getKundengruppe() {
        return kundengruppe;
    }

    public void setKundengruppe(int kundengruppe) {
        this.kundengruppe = kundengruppe;
        kundeDAO.update(this);
    }
	
	/** Holen eines vorhandenen Kunden aus der Datenhaltungsschicht
	*   über die Kundennummer
	**/
	public static Kunde read(Long kundennummer) {
		return KundeDAO.getInstance().read(kundennummer);
	}
}
