package rek.vao;

public class Uporabnik {
	
	private int  id;
	private String ime;
	private String priimek;
	private String email;
	private String status;
	private String geslo;
	private String aktiven;
	private SportniObjekt sportniObjekt;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPriimek() {
		return priimek;
	}
	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGeslo() {
		return geslo;
	}
	public void setGeslo(String geslo) {
		this.geslo = geslo;
	}
	
	public SportniObjekt getSportniObjekt() {
		return sportniObjekt;
	}
	public void setSportniObjekt(SportniObjekt sportniObjekt) {
		this.sportniObjekt = sportniObjekt;
	}
	public String getAktiven() {
		return aktiven;
	}
	public void setAktiven(String aktiven) {
		this.aktiven = aktiven;
	}
	@Override
	public String toString() {
		return "Uporabnik [id=" + id + ", ime=" + ime + ", priimek=" + priimek
				+ ", email=" + email + ", status=" + status + ", geslo="
				+ geslo + ", aktiven=" + aktiven + ", sportniObjekt="
				+ sportniObjekt + "]";
	}

}
