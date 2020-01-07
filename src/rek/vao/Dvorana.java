package rek.vao;

public class Dvorana {

	private int idDvorana;
	private String nazivDvorane;
	private String tipIgrisca;
	private String opis;
	private SportniObjekt sportniobjekt;
	private TipSporta tipSporta;

	
	public int getIdDvorana() {
		return idDvorana;
	}

	public void setIdDvorana(int idDvorana) {
		this.idDvorana = idDvorana;
	}

	public String getNazivDvorane() {
		return nazivDvorane;
	}

	public void setNazivDvorane(String nazivDvorane) {
		this.nazivDvorane = nazivDvorane;
	}

	public String getTipIgrisca() {
		return tipIgrisca;
	}

	public void setTipIgrisca(String tipIgrisca) {
		this.tipIgrisca = tipIgrisca;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public SportniObjekt getSportniobjekt() {
		return sportniobjekt;
	}

	public void setSportniobjekt(SportniObjekt sportniobjekt) {
		this.sportniobjekt = sportniobjekt;
	}
	
	

	public TipSporta getTipSporta() {
		return tipSporta;
	}

	public void setTipSporta(TipSporta tipSporta) {
		this.tipSporta = tipSporta;
	}

	@Override
	public String toString() {
		return idDvorana + nazivDvorane + opis + tipIgrisca + tipSporta + sportniobjekt;
	}
	
	
}
