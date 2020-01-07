package rek.vao;

/**
 * 
 * @author tamara lorber
 *	get in set metode za športno opremo
 */
public class SportniPripomocki {
	
	private int idPripomocki, stevilo;
	private String naziv;
	private float cena;
	private Dvorana dvorana;
	
	
	
	public int getIdPripomocki() {
		return idPripomocki;
	}
	public void setIdPripomocki(int idPripomocki) {
		this.idPripomocki = idPripomocki;
	}
	public int getStevilo() {
		return stevilo;
	}
	public void setStevilo(int stevilo) {
		this.stevilo = stevilo;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public float getCena() {
		return cena;
	}
	public void setCena(float cena) {
		this.cena = cena;
	}

	public Dvorana getDvorana() {
		return dvorana;
	}
	public void setDvorana(Dvorana dvorana) {
		this.dvorana = dvorana;
	}
	@Override
	public String toString() {
		return naziv + idPripomocki + naziv + dvorana;
	}
	
	
	
}
