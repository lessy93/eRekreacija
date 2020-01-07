package rek.vao;

import rek.dao.DvoranaDAO;
import rek.dao.UporabnikDAO;
import rek.sql.BazenPovezav;

public class Test {


	public static void main(String[] args) {
		
		
//		Uporabnik up = new Uporabnik();
//		TipUporabnika tip= new TipUporabnika();
//		tip.setId(1);
//		up.setIme("Miha");
//		up.setPriimek("Leskovar");
//		up.setEmail("leskovar.miha@gmail.com");
//		up.setGeslo("miha1234");
//		up.setTip(tip);
//		
//		TipUporabnika testtip= new TipUporabnika();
//		testtip.setNaziv("test");
		
//		UporabnikDAO updao = new UporabnikDAO();
//		TipUporabnikaDAO tipDao= new TipUporabnikaDAO();
		try {
			
		DvoranaDAO dao = new DvoranaDAO();
		
		for(Dvorana d : dao.getDvoranas())
		{
			System.out.println(d.getNazivDvorane());
		}
		//	tipDao.getTipUporabnikas();
			//tipDao.shraniTipUporabnika(testtip);
		
			
		//	updao.shraniUporabnik(up);
		//	updao.getUporabnikByEmailGeslo("leskovar.miha@gmail.com", "miha1234");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
