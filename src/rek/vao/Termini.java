package rek.vao;


import java.sql.Time;
import java.util.Calendar;


public class Termini {
	
	private int idTermini;
	private Calendar datum;
	private Calendar zacetniCas, koncniCas;
	private Boolean zasedenost;
	Dvorana dvorana;
	
	public int getIdTermini() {
		return idTermini;
	}
	public void setIdTermini(int idTermini) {
		this.idTermini = idTermini;
	}
	public Calendar getDatum() {
		return datum;
	}
	public void setDatum(Calendar datum) {
		this.datum = datum;
	}
	public Calendar getZacetniCas() {
		return zacetniCas;
	}
	public void setZacetniCas(Calendar zacetniCas) {
		this.zacetniCas = zacetniCas;
	}
	public Calendar getKoncniCas() {
		return koncniCas;
	}
	public void setKoncniCas(Calendar koncniCas) {
		this.koncniCas = koncniCas;
	}
	public boolean getZasedenost() {
		return zasedenost;
	}
	public void setZasedenost(Boolean zasedenost) {
		this.zasedenost = zasedenost;
	}
	public Dvorana getDvorana() {
		return dvorana;
	}
	public void setDvorana(Dvorana dvorana) {
		this.dvorana = dvorana;
	}

}
