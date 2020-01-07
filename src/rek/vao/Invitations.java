package rek.vao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Random;

import rek.dao.PovabiloDAO;
import rek.dao.RezervacijaDvoraneDAO;
import rek.dao.UporabnikDAO;


public class Invitations {
	Random r;
	private RezervacijaDvorane rezervacijaDvorane;

	public Invitations(RezervacijaDvorane rezervacijaDvorane) {
		this.rezervacijaDvorane = rezervacijaDvorane;
	}

	public void sendInvitations(String emailList) {
		String[] emails = emailList.split(",");
		for (String email : emails) {
			//za preverjanje pravilnosti e-postnega naslova
			if (!emailList.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
					 )) {
				continue;
				
			}
			String text;
			 SimpleDateFormat sdf=  new SimpleDateFormat("HH:mm");
			 SimpleDateFormat datum= new SimpleDateFormat("dd-MM-yyyy");
			 RezervacijaDvoraneDAO rDAO = new RezervacijaDvoraneDAO();
			 rezervacijaDvorane = rDAO.getRezervacijeById(rezervacijaDvorane.getIdRezervacijeDvorane());
			 System.out.println("POTATOOOOOOOOOOOOOOOOOOOOOOOO " + rezervacijaDvorane);
			 
			 
			String unique = generateUniqueId(email);
			if (isRegistriran(email)) {
				text =  "Pozdravljeni,\n"
						+ "poslano vam je bilo povabilo na dogodek dne: " + datum.format(rezervacijaDvorane.getTermini().getDatum().getTime())
						+ " ob " + sdf.format(rezervacijaDvorane.getTermini().getZacetniCas().getTime()) + " do " + sdf.format(rezervacijaDvorane.getTermini().getKoncniCas().getTime())
						+ ". " + rezervacijaDvorane.getTermini().getDvorana().getSportniobjekt().getNaziv() + ", " + rezervacijaDvorane.getTermini().getDvorana().getSportniobjekt().getLokacija()
						+ " na igro " + rezervacijaDvorane.getTermini().getDvorana().getTipSporta().getNaziv() + "a."
						+ "\n Kliknite na  spodnjo povezavo, da potrdite udele�bo (obvezna registracija).\n"
						+ "http://localhost:8080/eRekreacija/povabilo.jsp?IdPovabila=" + unique
						+ "\n\nLep pozdrav,\n " + rezervacijaDvorane.getUporabnik().getIme() + " " + rezervacijaDvorane.getUporabnik().getPriimek();
			} else {
				text =  "Pozdravljeni,\n"
						+ "poslano vam je bilo povabilo na dogodek dne: " + datum.format(rezervacijaDvorane.getTermini().getDatum().getTime())
						+ " ob " + sdf.format(rezervacijaDvorane.getTermini().getZacetniCas().getTime()) + " do " + sdf.format(rezervacijaDvorane.getTermini().getKoncniCas().getTime())
						+ ". " + rezervacijaDvorane.getTermini().getDvorana().getSportniobjekt().getNaziv() + ", " + rezervacijaDvorane.getTermini().getDvorana().getSportniobjekt().getLokacija()
						+ " na igro " + rezervacijaDvorane.getTermini().getDvorana().getTipSporta().getNaziv() + "a."
						+ "\n Kliknite na  spodnjo povezavo, da potrdite udele�bo (obvezna registracija).\n"
						+ "http://localhost:8080/eRekreacija/povabilo.jsp?IdPovabila=" + unique
						+ "\n\nLep pozdrav,\n " + rezervacijaDvorane.getUporabnik().getIme() + " " + rezervacijaDvorane.getUporabnik().getPriimek();
			}
			SendMail.poslji("Povabilo", text, email);
			
			PovabiloDAO povabiloDAO = new PovabiloDAO();
			Povabilo povabilo = new Povabilo();
			
			povabilo.setUniqueId(unique);
			povabilo.setEmail(email);
			povabilo.setPotrjeno(0);
			povabilo.setRezervacija(rezervacijaDvorane);
			povabiloDAO.shraniPovabilo(povabilo);
			povabiloDAO.close();
		}
	}

	private boolean isRegistriran(String email) {
		try{
			return new UporabnikDAO().getCheckEmail(email);
		}catch (Exception e){
			return false;
		}
		
	}

	private String generateUniqueId(String email) {
		if (r == null) {
			r = new Random(System.currentTimeMillis());
		}
		
		String uniqueId = new BigInteger(130, r).toString(32);
		
		return uniqueId;
	}

}
