package rek.vao;

public class Povabilo {

	private int idPovabilo, potrjeno;
	private String uniqueId, email;
	private RezervacijaDvorane rezervacijaDvorane;
	
	public int getIdPovabilo() {
		return idPovabilo;
	}
	public void setIdPovabilo(int idPovabilo) {
		this.idPovabilo = idPovabilo;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getPotrjeno() {
		return potrjeno;
	}
	public void setPotrjeno(int potrjeno) {
		this.potrjeno = potrjeno;
	}
	
	public RezervacijaDvorane getRezervacija() {
		return rezervacijaDvorane;
	}
	public void setRezervacija(RezervacijaDvorane rezervacija) {
		this.rezervacijaDvorane = rezervacija;
	}
	@Override
	public String toString() {
		return idPovabilo + " " + uniqueId + " " + email + " " + potrjeno + " " + rezervacijaDvorane;
	}
	
	
	
}
