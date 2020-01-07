package rek.vao;

public class TipSporta {

	private String naziv;
	private int idTipSporta;

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getIdTipSporta() {
		return idTipSporta;
	}

	public void setIdTipSporta(int idTipSporta) {
		this.idTipSporta = idTipSporta;
	}

	@Override
	public String toString() {
		return  idTipSporta + naziv;
	}
	
	
}
