package rek.vao;

import java.util.List;

public class SportniObjekt {

	private int idSportniObjekt;
	private String naziv;
	private String opis;
	private String lokacija;
	private float lat;
	private float lng;
	private List<Dvorana> listDvoran;
	private String aktiven;

	public int getIdSportniObjekt() {
		return idSportniObjekt;
	}
	public void setIdSportniObjekt(int idSportniObjekt) {
		this.idSportniObjekt = idSportniObjekt;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getLokacija() {
		return lokacija;
	}
	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}
	
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLng() {
		return lng;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	
	public List<Dvorana> getListDvoran() {
		return listDvoran;
	}
	public void setListDvoran(List<Dvorana> listDvoran) {
		this.listDvoran = listDvoran;
	}
	
	public String getAktiven() {
		return aktiven;
	}
	public void setAktiven(String aktiven) {
		this.aktiven = aktiven;
	}
	@Override
	public String toString() {
		return "SportniObjekt [idSportniObjekt=" + idSportniObjekt + ", naziv="
				+ naziv + ", opis=" + opis + ", lokacija=" + lokacija
				+ ", lat=" + lat + ", lng=" + lng + ", aktiven=" + aktiven + "]";
	}	
}
