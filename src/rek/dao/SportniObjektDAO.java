package rek.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rek.sql.BazenPovezav;
import rek.vao.Dvorana;
import rek.vao.SportniObjekt;
import rek.vao.TipSporta;

import java.sql.Statement;

public class SportniObjektDAO {

	private BazenPovezav bp;
	public SportniObjektDAO()
	{
	this.bp=new BazenPovezav();
	}
	public void close () {
		bp.izprazniBazen();
	}
	
	public void shraniSportniObjekt(SportniObjekt objekt) throws Exception {
		Connection c = bp.dobiPovezavo();
		
		try{
			String sql = ("INSERT INTO sportniobjekt (naziv, opis, lokacija, Lat, Lng, aktiven) VALUES(?, ?, ?, ?, ?, ?)");
			PreparedStatement st=c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, objekt.getNaziv());
			st.setString(2, objekt.getOpis());
			st.setString(3, objekt.getLokacija());
			st.setFloat(4, objekt.getLat());
			st.setFloat(5, objekt.getLng());
			st.setString(6, "da");
			
			ResultSet rs = st.getGeneratedKeys();
			if (rs != null && rs.next()) {
				System.out.println("Id vnosa: " + rs.getLong(1));
			}

			st.executeUpdate();
			
		}catch (SecurityException e){
			e.printStackTrace();
		}
		
	}
	
	// za vnos idja v uporabnika
	public SportniObjekt getSportniObjekt(String naziv) throws Exception {
		SportniObjekt o = new SportniObjekt();
		Connection c = null;
        try {  
        	c = bp.dobiPovezavo();
            String sql = "SELECT * FROM sportniObjekt WHERE naziv LIKE ? ";
            PreparedStatement prst = c.prepareStatement(sql);
            prst.setString(1, naziv);
            
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
             
            	o.setIdSportniObjekt(rs.getInt("idSportniObjekt"));
            	o.setNaziv(rs.getString("naziv"));
            	o.setOpis(rs.getString("opis"));
            	o.setLokacija(rs.getString("lokacija"));
            	o.setLat(rs.getFloat("Lat"));
            	o.setLng(rs.getFloat("Lng"));
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        } 
        return o;
    }	
	
	public List<SportniObjekt> getSportniObjekts() throws Exception{
		List<SportniObjekt> seznamSportniObjekt = new ArrayList<SportniObjekt>();
		Connection c=null;
		try{
			c = bp.dobiPovezavo();
			String sql ="SELECT * FROM sportniobjekt where aktiven= 'da';";
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				SportniObjekt temp = new SportniObjekt();
				
				List<Dvorana> listdvo = new ArrayList<>();
				
				temp.setIdSportniObjekt(rs.getInt("idSportniObjekt"));
				temp.setLokacija(rs.getString("lokacija"));
				temp.setNaziv(rs.getString("naziv"));
				temp.setOpis(rs.getString("opis"));
				temp.setLat(rs.getFloat("Lat"));
				temp.setLng(rs.getFloat("Lng"));
				
				String sql1 ="SELECT * FROM  dvorana  WHERE sportniobjekt_idSportniObjekt = " + temp.getIdSportniObjekt();
				PreparedStatement st1 = c.prepareStatement(sql1);
				ResultSet rs1 = st1.executeQuery();
				while (rs1.next()){
					Dvorana d = new Dvorana();
					d.setIdDvorana(rs1.getInt("idDvorana"));
					d.setNazivDvorane(rs1.getString("naziv"));
					d.setOpis(rs1.getString("opis"));
					d.setTipIgrisca(rs1.getString("tipIgrisca"));
					listdvo.add(d);
				}

				temp.setListDvoran(listdvo);
				seznamSportniObjekt.add(temp);
			}
			
		}catch (Exception e){ 
			e.printStackTrace();
		}
		return seznamSportniObjekt;
	}
	//ZA ISKANJE BY NAZIV
	public List<SportniObjekt> getSportniObjektByNaziv(SportniObjekt o) throws Exception{
		List<SportniObjekt> seznamSportniObjekt = new ArrayList<SportniObjekt>();
		Connection c=null;
		try{
			c = bp.dobiPovezavo();
			String sql ="SELECT DISTINCT o.IdSportniObjekt, o.naziv, o.opis, o.lokacija, o.Lat, o.Lng FROM sportniobjekt o WHERE naziv LIKE ?";
			PreparedStatement st = c.prepareStatement(sql);
			st.setString(1, o.getNaziv());
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				SportniObjekt temp = new SportniObjekt();		
				temp.setIdSportniObjekt(rs.getInt("idSportniObjekt"));
				temp.setLokacija(rs.getString("lokacija"));
				temp.setNaziv(rs.getString("naziv"));
				temp.setOpis(rs.getString("opis"));
				temp.setLat(rs.getFloat("Lat"));
				temp.setLng(rs.getFloat("Lng"));			
				seznamSportniObjekt.add(temp);
			}
			if(!rs.first()){        
				System.out.println("NI ZADETKOV!");
			}
			
		}catch (Exception e){ 
			e.printStackTrace();
		}
		return seznamSportniObjekt;
	}
	// ZA ISKANJE BY TIP SPORTA
	public List<SportniObjekt> getSportniObjektsByTipSporta(TipSporta sport) throws Exception{
		List<SportniObjekt> seznamSportniObjekt = new ArrayList<SportniObjekt>();
		Connection c=null;
		try{
			c = bp.dobiPovezavo();
			String sql ="SELECT DISTINCT o.idSportniObjekt, o.naziv, o.opis, o.lokacija, o.Lat, o.Lng FROM sportniObjekt o, dvorana d, tipSporta s WHERE s.naziv LIKE ? AND d.sportniObjekt_idSportniObjekt=o.idSportniObjekt  AND d.tipSporta_idTipSporta= s.idTipSporta; ";
			PreparedStatement st = c.prepareStatement(sql);
			st.setString(1, sport.getNaziv());
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				SportniObjekt temp = new SportniObjekt();
				
				List<Dvorana> listdvo = new ArrayList<>();
				
				temp.setIdSportniObjekt(rs.getInt("idSportniObjekt"));
				temp.setLokacija(rs.getString("lokacija"));
				temp.setNaziv(rs.getString("naziv"));
				temp.setOpis(rs.getString("opis"));
				temp.setLat(rs.getFloat("Lat"));
				temp.setLng(rs.getFloat("Lng"));
				
				String sql1 ="SELECT * FROM  dvorana  WHERE sportniobjekt_idSportniObjekt = " + temp.getIdSportniObjekt();
				PreparedStatement st1 = c.prepareStatement(sql1);
				ResultSet rs1 = st1.executeQuery();
				while (rs1.next()){
					Dvorana d = new Dvorana();
					d.setIdDvorana(rs1.getInt("idDvorana"));
					d.setNazivDvorane(rs1.getString("naziv"));
					d.setOpis(rs1.getString("opis"));
					d.setTipIgrisca(rs1.getString("tipIgrisca"));
					listdvo.add(d);
				}
				
				temp.setListDvoran(listdvo);
				seznamSportniObjekt.add(temp);				
			}
			if(!rs.first()){        
				System.out.println("NI ZADETKOV!");
			}
			
		}catch (Exception e){ 
			e.printStackTrace();
		} 
		return seznamSportniObjekt;
	}
	
	
	public SportniObjekt getSportniObjektRezervacija(int id) throws Exception {
		SportniObjekt objekt = new SportniObjekt();
		Connection c = null;
        try {  
        	c = bp.dobiPovezavo();
            String sql = "SELECT * FROM sportniObjekt WHERE idSportniObjekt =? ";
            PreparedStatement prst = c.prepareStatement(sql);
            prst.setInt(1, id);
            
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
             
            	objekt.setIdSportniObjekt(rs.getInt("idSportniObjekt"));
            	objekt.setNaziv(rs.getString("naziv"));
            	objekt.setOpis(rs.getString("opis"));
            	objekt.setLokacija(rs.getString("lokacija"));
            	objekt.setLat(rs.getFloat("Lat"));
            	objekt.setLng(rs.getFloat("Lng"));
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }    
        return objekt;
    }	
	 public boolean deleteObjektById(int idObjekt) throws Exception {
	        boolean deleted = true;
	        Connection c = bp.dobiPovezavo();
	        try {
//	            String sql = "DELETE FROM sportniobjekt WHERE idSportniObjekt = ?";
	            String sql = "UPDATE sportniobjekt SET aktiven='ne' WHERE idSportniObjekt = ?";
	            PreparedStatement st = c.prepareStatement(sql);
	            st.setInt(1, idObjekt);
	            st.executeUpdate();

	        } catch (SQLException e) {
	            deleted = false;
	        } 
	        return deleted;
	    }
	 
	 public SportniObjekt getObjektById(int id) throws Exception {
			SportniObjekt objekt = new SportniObjekt();
			Connection c = bp.dobiPovezavo();
	        try {
	           
	            String sql = "SELECT * FROM sportniobjekt WHERE idSportniObjekt =?";
	            PreparedStatement prst = c.prepareStatement(sql);
	            prst.setInt(1, id);

	            ResultSet rs = prst.executeQuery();
	            while (rs.next()) {
	             
	            	objekt.setIdSportniObjekt(rs.getInt("idSportniObjekt"));
	            	objekt.setLokacija(rs.getString("lokacija"));
	            	objekt.setNaziv(rs.getString("naziv"));
	            	objekt.setOpis(rs.getString("opis"));
	            	objekt.setLat(rs.getFloat("Lat"));
	            	objekt.setLng(rs.getFloat("Lng"));
	            	objekt.setAktiven(rs.getString("aktiven"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } 
			return objekt;
		}
	 
	 public boolean updateObjekt(SportniObjekt objekt) throws Exception{
			boolean updated = true;
			Connection c = bp.dobiPovezavo();
			try{
				
				String sql = "UPDATE sportniobjekt SET naziv =?, opis =?, lokacija =?, Lat =?, Lng =?, aktiven =? WHERE idSportniObjekt = ?";
				PreparedStatement st = c.prepareStatement(sql);
				
				st.setString(1, objekt.getNaziv());
				st.setString(2, objekt.getOpis());
				st.setString(3, objekt.getLokacija());
				st.setFloat(4, objekt.getLat());
				st.setFloat(5,objekt.getLng());
				st.setString(6, objekt.getAktiven());
				st.setInt(7, objekt.getIdSportniObjekt());

				st.executeUpdate();
				
			}catch (SQLException e){
				updated = false;
			}finally{
				System.out.println("SHRANJENO!" +objekt);
				
			}		
			return updated;
		}
}
