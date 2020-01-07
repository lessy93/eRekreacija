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
import rek.vao.Povabilo;
import rek.vao.RezervacijaDvorane;
import rek.vao.SportniObjekt;
import rek.vao.Termini;
import rek.vao.Uporabnik;

public class PovabiloDAO {
	private BazenPovezav bp;
	
	public PovabiloDAO()
	{
	this.bp=new BazenPovezav();
	}
	
	public void close () {
		bp.izprazniBazen();
	}
	
	public void shraniPovabilo(Povabilo povabilo) {
		Connection c= bp.dobiPovezavo();	
		try{			
			PreparedStatement st=c.prepareStatement("INSERT INTO povabilo (uniqueId, eposta, rezervacijadvorane_idRezervacijaDvorane, potrjeno) VALUES (?,?,?,?)");						
			st.setString(1, povabilo.getUniqueId());		
			st.setString(2, povabilo.getEmail());	
			st.setInt(3, povabilo.getRezervacija().getIdRezervacijeDvorane());
			st.setInt(4, povabilo.getPotrjeno());
			
			st.executeUpdate();
				
		}catch(SQLException e){
			e.printStackTrace();
			
		}
	}
	
	public List<Povabilo> getPovabilos(int idUporabnik){
		List<Povabilo> seznamPovabil = new ArrayList<Povabilo>();
		
		try{
			Connection c = bp.dobiPovezavo();
			String sql ="SELECT * FROM povabilo, rezervacijadvorane, uporabnik, termini, dvorana d, sportniobjekt s WHERE rezervacijadvorane_idRezervacijaDvorane = idRezervacijaDvorane AND uporabnik_idUporabnik = idUporabnik AND termini_idTermini = idTermini AND dvorana_idDvorana = idDvorana AND d.sportniobjekt_idSportniObjekt = idSportniObjekt AND idUporabnik = ?";
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, idUporabnik);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				Povabilo povabilo = new Povabilo();		
				RezervacijaDvorane rezervacija = new RezervacijaDvorane();
				Uporabnik uporabnik = new Uporabnik();
				Termini termin = new Termini();
				Dvorana dvorana = new Dvorana();
				SportniObjekt sportniobjekt = new SportniObjekt();
				
				sportniobjekt.setIdSportniObjekt(rs.getInt("sportniobjekt_idSportniObjekt"));
				sportniobjekt.setNaziv(rs.getString("s.naziv"));
				
				dvorana.setIdDvorana(rs.getInt("dvorana_idDvorana"));
				dvorana.setNazivDvorane(rs.getString("naziv"));
				dvorana.setSportniobjekt(sportniobjekt);
				
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
				
				termin.setZacetniCas(zacetniC);
				
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
				
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
				
				termin.setKoncniCas(koncniC);				
				termin.setIdTermini(rs.getInt("idTermini"));
				termin.setDatum(dat);
				termin.setZasedenost(rs.getBoolean("zasedenost"));
				termin.setDvorana(dvorana); 
				
				uporabnik.setId(rs.getInt("uporabnik_idUporabnik"));
				uporabnik.setIme(rs.getString("ime"));
				uporabnik.setPriimek(rs.getString("priimek"));
				
				rezervacija.setIdRezervacijeDvorane(rs.getInt("rezervacijadvorane_idRezervacijaDvorane"));
				rezervacija.setUporabnik(uporabnik);
				rezervacija.setTermini(termin);
				
				povabilo. setIdPovabilo(rs.getInt("idpovabilo"));
				povabilo.setPotrjeno(rs.getInt("potrjeno"));
				povabilo.setEmail(rs.getString("eposta"));
				povabilo.setRezervacija(rezervacija);

				seznamPovabil.add(povabilo);
				
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
	
	return seznamPovabil;
}
	
	 public boolean updatePovabilo (int odgovor, int idPovabila) throws Exception{
			boolean updated = true;
			Connection c = bp.dobiPovezavo();
			try{
				String sql = "UPDATE povabilo SET potrjeno =? WHERE idPovabilo = ?";
				PreparedStatement st = c.prepareStatement(sql);
				
				st.setInt(1, odgovor);
				st.setInt(2, idPovabila);
				
				st.executeUpdate();
				
			}catch (SQLException e){
				updated = false;
			}
			return updated;
		}
	 
	 public Povabilo getPovabiloById(String uniqueId) throws Exception {
			Povabilo povabilo = new Povabilo();
			Connection c = bp.dobiPovezavo();
	        try {
	           
	            String sql = "SELECT * FROM povabilo WHERE uniqueId = ? ";
	            PreparedStatement prst = c.prepareStatement(sql);
	            prst.setString(1, uniqueId);

	            ResultSet rs = prst.executeQuery();
	            while (rs.next()) {
       	
	            	povabilo.setIdPovabilo(rs.getInt("idPovabilo"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return povabilo;
	    }	
}
