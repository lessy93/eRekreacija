package rek.vao;

public class RezervacijaDvorane {

	private int idRezervacijeDvorane;
	private Termini termini;
	private Uporabnik uporabnik;
	
	public int getIdRezervacijeDvorane() {
		return idRezervacijeDvorane;
	}
	public void setIdRezervacijeDvorane(int idRezervacijeDvorane) {
		this.idRezervacijeDvorane = idRezervacijeDvorane;
	}
	public Termini getTermini() {
		return termini;
	}
	public void setTermini(Termini termini) {
		this.termini = termini;
	}

	public Uporabnik getUporabnik() {
		return uporabnik;
	}
	public void setUporabnik(Uporabnik uporabnik) {
		this.uporabnik = uporabnik;
	}

	@Override
	public String toString() {
		return idRezervacijeDvorane + "" + uporabnik + "";
	}
	
}
