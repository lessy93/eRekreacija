package rek.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rek.sql.BazenPovezav;
import rek.vao.Dvorana;
import rek.vao.RezervacijaDvorane;
import rek.vao.SportniObjekt;
import rek.vao.Termini;
import rek.vao.TipSporta;
import rek.vao.Uporabnik;

import java.sql.Statement;

public class RezervacijaDvoraneDAO {

	private BazenPovezav bp;
	public RezervacijaDvoraneDAO()
	{
	this.bp = new BazenPovezav();
	}
	
	public void close () {
		bp.izprazniBazen();
	}
	
	public void shraniRezervacijoDvorane(RezervacijaDvorane dvorana, Uporabnik uporabnik) throws Exception {
		Connection c = bp.dobiPovezavo();
		
		try{
			String sql = ("INSERT INTO rezervacijadvorane (termini_idTermini, uporabnik_idUporabnik) VALUES(?,?)");
			PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, dvorana.getTermini().getIdTermini());
			st.setInt(2, uporabnik.getId() );


			ResultSet rs = st.getGeneratedKeys();
			if (rs != null && rs.next()) {
				System.out.println("Id vnosa: " + rs.getLong(1));
			}
			st.executeUpdate();
			
		}catch (SQLException e){
			e.printStackTrace();
		}	
	}
	
	public List<RezervacijaDvorane> getRezervacijeByIdUpo(int id){
		List<RezervacijaDvorane> seznamRezervacij = new ArrayList<RezervacijaDvorane>();
		
		try{
			Connection c = bp.dobiPovezavo();
			String sql ="SELECT * FROM rezervacijaDvorane r, uporabnik u, termini t, dvorana d, sportniobjekt s WHERE r.uporabnik_idUporabnik=u.idUporabnik AND r.termini_idTermini=t.idTermini AND d.idDvorana=t.dvorana_idDvorana AND d.sportniobjekt_idSportniObjekt=s.idSportniObjekt AND r.uporabnik_idUporabnik=?";
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				
				RezervacijaDvorane rezervacija = new RezervacijaDvorane();
				SportniObjekt objekt = new SportniObjekt();
				Termini ter = new Termini();
				Uporabnik up = new Uporabnik();
				Dvorana tempD =  new Dvorana();
				
				up.setId(rs.getInt("idUporabnik"));
				up.setIme(rs.getString("ime"));
				up.setPriimek(rs.getString("priimek"));
				up.setEmail(rs.getString("ePosta"));
				up.setStatus(rs.getString("status"));
				up.setAktiven(rs.getString("aktiven"));
				
				objekt.setIdSportniObjekt(rs.getInt("idSportniObjekt"));
				objekt.setLokacija(rs.getString("lokacija"));
				objekt.setNaziv(rs.getString("s.naziv"));
				
						
				tempD.setIdDvorana(rs.getInt("dvorana_idDvorana"));
				tempD.setNazivDvorane(rs.getString("d.naziv"));
				tempD.setTipIgrisca(rs.getString("tipIgrisca"));
				tempD.setSportniobjekt(objekt);
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
				
				ter.setZacetniCas(zacetniC);
				
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
				
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
				
				ter.setKoncniCas(koncniC);				
				ter.setIdTermini(rs.getInt("idTermini"));
				ter.setDatum(dat);
				ter.setZasedenost(rs.getBoolean("zasedenost"));
				ter.setDvorana(tempD); 
					
				rezervacija.setIdRezervacijeDvorane(rs.getInt("idRezervacijaDvorane"));
				rezervacija.setTermini(ter);
				rezervacija.setUporabnik(up);
				
				seznamRezervacij.add(rezervacija);
			}
		
		}catch (Exception e){
			e.printStackTrace();
		}
	
		return seznamRezervacij;
	}
	
	public List<RezervacijaDvorane> getRezervacijeByIdObjekt(int idObjekt){
		List<RezervacijaDvorane> seznamRezervacij = new ArrayList<RezervacijaDvorane>();
		
		try{
			Connection c = bp.dobiPovezavo();
			String sql ="select * from rezervacijadvorane r, termini t, dvorana d, sportniobjekt s, uporabnik u WHERE r.termini_idTermini = t.idTermini AND r.uporabnik_idUporabnik = u.idUporabnik AND t.dvorana_idDvorana = d.idDvorana AND d.sportniobjekt_idSportniObjekt = s.idSportniObjekt AND s.idSportniObjekt = ?";
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, idObjekt);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				
				RezervacijaDvorane rezervacija = new RezervacijaDvorane();
				SportniObjekt objekt = new SportniObjekt();
				Termini ter = new Termini();
				Uporabnik up = new Uporabnik();
				Dvorana tempD =  new Dvorana();
				
				up.setId(rs.getInt("idUporabnik"));
				up.setIme(rs.getString("ime"));
				up.setPriimek(rs.getString("priimek"));
				up.setEmail(rs.getString("ePosta"));
				up.setStatus(rs.getString("status"));
				up.setAktiven(rs.getString("aktiven"));
				
				objekt.setIdSportniObjekt(rs.getInt("idSportniObjekt"));
				objekt.setLokacija(rs.getString("lokacija"));
				objekt.setNaziv(rs.getString("s.naziv"));
				
						
				tempD.setIdDvorana(rs.getInt("dvorana_idDvorana"));
				tempD.setNazivDvorane(rs.getString("d.naziv"));
				tempD.setTipIgrisca(rs.getString("tipIgrisca"));
				tempD.setSportniobjekt(objekt);
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
				
				ter.setZacetniCas(zacetniC);
				
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
				
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
				
				ter.setKoncniCas(koncniC);				
				ter.setIdTermini(rs.getInt("idTermini"));
				ter.setDatum(dat);
				ter.setZasedenost(rs.getBoolean("zasedenost"));
				ter.setDvorana(tempD); 
				
				
				
				rezervacija.setIdRezervacijeDvorane(rs.getInt("idRezervacijaDvorane"));
				rezervacija.setTermini(ter);
				rezervacija.setUporabnik(up);
				
				seznamRezervacij.add(rezervacija);
			}
		
		}catch (Exception e){
			e.printStackTrace();
		}
	
		return seznamRezervacij;
	}	 
	
	 public RezervacijaDvorane getRezervacijaDvoraneById(int idDvorane) throws Exception {
			RezervacijaDvorane dvorana = new RezervacijaDvorane();
			Connection c = bp.dobiPovezavo();
	        try {
	           
	            String sql = "SELECT * FROM rezervacijaDvorane WHERE idRezervacijaDvorane = ? ";
	            PreparedStatement prst = c.prepareStatement(sql);
	            prst.setInt(1, idDvorane);

	            ResultSet rs = prst.executeQuery();
	            while (rs.next()) {
	            	
	            	dvorana.setIdRezervacijeDvorane(rs.getInt("idRezervacijaDvorane"));
	  
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	    	System.out.print(dvorana.toString());
	        return dvorana;
	    }	
	 public RezervacijaDvorane getRezervacijeById(int id){
		 RezervacijaDvorane rezervacija = new RezervacijaDvorane();
			try{
				Connection c = bp.dobiPovezavo();
				String sql ="SELECT * FROM rezervacijaDvorane r, uporabnik u, termini t, dvorana d, sportniobjekt s, tipsporta tps WHERE r.uporabnik_idUporabnik=u.idUporabnik AND r.termini_idTermini=t.idTermini AND d.idDvorana=t.dvorana_idDvorana AND d.sportniobjekt_idSportniObjekt=s.idSportniObjekt AND d.tipsporta_idTipSporta = tps.idTipSporta AND r.idRezervacijaDvorane =?";
				PreparedStatement st = c.prepareStatement(sql);
				st.setInt(1, id);
				ResultSet rs = st.executeQuery();
				while (rs.next()){
					
					
					SportniObjekt objekt = new SportniObjekt();
					Termini ter = new Termini();
					Uporabnik up = new Uporabnik();
					Dvorana tempD =  new Dvorana();
					TipSporta tip = new TipSporta();
					
					up.setId(rs.getInt("idUporabnik"));
					up.setIme(rs.getString("ime"));
					up.setPriimek(rs.getString("priimek"));
					up.setEmail(rs.getString("ePosta"));
					up.setStatus(rs.getString("status"));
					up.setAktiven(rs.getString("aktiven"));
					
					objekt.setIdSportniObjekt(rs.getInt("idSportniObjekt"));
					objekt.setLokacija(rs.getString("lokacija"));
					objekt.setNaziv(rs.getString("s.naziv"));
					
					tip.setIdTipSporta(rs.getInt("d.tipsporta_idTipSporta"));
					tip.setNaziv(rs.getString("tps.naziv"));
							
					tempD.setIdDvorana(rs.getInt("dvorana_idDvorana"));
					tempD.setNazivDvorane(rs.getString("d.naziv"));
					tempD.setTipIgrisca(rs.getString("tipIgrisca"));
					tempD.setTipSporta(tip);
					tempD.setSportniobjekt(objekt);
					java.sql.Time zacetniCas =rs.getTime("zacetniCas");
					Calendar zacetniC = Calendar.getInstance();  
					zacetniC.setTime(zacetniCas);
					
					ter.setZacetniCas(zacetniC);
					
					java.sql.Time koncniCas =rs.getTime("koncniCas");
					Calendar   koncniC = Calendar.getInstance();  
					koncniC.setTime(koncniCas);
					
					java.sql.Date datum =rs.getDate("datum");
					Calendar   dat = Calendar.getInstance();  
					dat.setTime(datum);
					
					ter.setKoncniCas(koncniC);				
					ter.setIdTermini(rs.getInt("idTermini"));
					ter.setDatum(dat);
					ter.setZasedenost(rs.getBoolean("zasedenost"));
					ter.setDvorana(tempD); 
						
					rezervacija.setIdRezervacijeDvorane(rs.getInt("idRezervacijaDvorane"));
					rezervacija.setTermini(ter);
					rezervacija.setUporabnik(up);

				}
			
			}catch (Exception e){
				e.printStackTrace();
			}
		
			return rezervacija;
		}
}