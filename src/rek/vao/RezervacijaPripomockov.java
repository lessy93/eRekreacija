package rek.vao;

public class RezervacijaPripomockov {
	private int idRezervacijaPripomockov;
	private String naziv;
	private Termini termin;
	private Uporabnik uporabnik;
	private int stevilo;

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Termini getTermin() {
		return termin;
	}

	public void setTerminiPripomockiRez(Termini termin) {
		this.termin = termin;
	}

	public int getIdRezervacijaPripomockov() {
		return idRezervacijaPripomockov;
	}

	public void setIdRezervacijaPripomockov(int idRezervacijaPripomockov) {
		this.idRezervacijaPripomockov = idRezervacijaPripomockov;
	}

	public int getStevilo() {
		return stevilo;
	}

	public void setStevilo(int stevilo) {
		this.stevilo = stevilo;
	}

	public Uporabnik getUporabnik() {
		return uporabnik;
	}

	public void setUporabnik(Uporabnik uporabnik) {
		this.uporabnik = uporabnik;
	}
	
	

}
