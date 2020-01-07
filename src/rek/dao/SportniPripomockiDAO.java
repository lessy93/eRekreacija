package rek.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rek.sql.BazenPovezav;
import rek.vao.Dvorana;
import rek.vao.RezervacijaPripomockov;
import rek.vao.SportniPripomocki;

public class SportniPripomockiDAO {
	
	private BazenPovezav bp;
	public SportniPripomockiDAO()
	{
	this.bp = new BazenPovezav();
	}
	public void close () {
		bp.izprazniBazen();
	}
	
	public void shraniSportnoOpremo (SportniPripomocki oprema) throws Exception{
		Connection c = bp.dobiPovezavo();
		
		try{
			String sql = ("INSERT INTO PRIPOMOCKI (idPripomocki, naziv, cena,stevilo, dvorana_idDvorana) values(?,?,?,?,?)");
			
			PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			st.setFloat(1, oprema.getIdPripomocki());	
			st.setString(2, oprema.getNaziv());
			st.setFloat(3, oprema.getCena());
			st.setInt(4, oprema.getStevilo());
			st.setInt(5, oprema.getDvorana().getIdDvorana());

			ResultSet rs = st.getGeneratedKeys();
			if(rs != null && rs.next()){
				System.out.println("Id: " + rs.getLong(1));
			}
			
			st.executeUpdate();
			
		}catch (SQLException e){
			e.printStackTrace();
		}

	}
	
	public List<SportniPripomocki> getSportniPripomockis(){
		List<SportniPripomocki> seznamPripomockov = new ArrayList<SportniPripomocki>();
		
		try{
			Connection c = bp.dobiPovezavo();
			String sql ="SELECT * FROM pripomocki;";
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				SportniPripomocki temp = new SportniPripomocki();
				Dvorana dvorana = new Dvorana();
				
				dvorana.setIdDvorana(rs.getInt("dvorana_idDvorana"));
				
				temp.setIdPripomocki(rs.getInt("idPripomocki"));
				temp.setNaziv(rs.getString("naziv"));
				temp.setCena(rs.getFloat("cena"));
				temp.setDvorana(dvorana);
				temp.setStevilo(rs.getInt("stevilo"));
				
				
				seznamPripomockov.add(temp);
				
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return seznamPripomockov;
	}
	
	public SportniPripomocki getSportniPripomocekByDvorana(Dvorana dvor, RezervacijaPripomockov rezPrip) throws Exception{
		SportniPripomocki spPrip= new SportniPripomocki();
		Connection c = bp.dobiPovezavo();
		try{
			
			String sql ="SELECT * FROM pripomocki WHERE dvorana_idDvorana = ? AND naziv LIKE ?";
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, dvor.getIdDvorana());
			st.setString(2, rezPrip.getNaziv());
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				Dvorana dvorana = new Dvorana();
				dvorana.setIdDvorana(rs.getInt("dvorana_idDvorana"));				
				spPrip.setIdPripomocki(rs.getInt("idPripomocki"));
				spPrip.setNaziv(rs.getString("naziv"));
				spPrip.setCena(rs.getFloat("cena"));
				spPrip.setDvorana(dvorana);							
			}
		
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return spPrip;
	}
	
	public List<SportniPripomocki> getSportniPripomockisById(int id) throws Exception{
		List<SportniPripomocki> seznamPripomockov = new ArrayList<SportniPripomocki>();
		Connection c = bp.dobiPovezavo();
		try{
			
			String sql ="SELECT * FROM pripomocki WHERE dvorana_idDvorana = ? AND dvorana_idDvorana = idDvorana;";
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				SportniPripomocki temp = new SportniPripomocki();
				Dvorana dvorana = new Dvorana();
				
				dvorana.setIdDvorana(rs.getInt("dvorana_idDvorana"));
				
				temp.setIdPripomocki(rs.getInt("idPripomocki"));
				temp.setNaziv(rs.getString("naziv"));
				temp.setCena(rs.getFloat("cena"));
				temp.setDvorana(dvorana);
				
				seznamPripomockov.add(temp);				
			}
		
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return seznamPripomockov;
	}
	
	 public boolean rezervacijaPripomocka (int st, SportniPripomocki sp) throws Exception{
			boolean rezervirano = true;
			Connection c = bp.dobiPovezavo();
			try{
				String sql = "UPDATE pripomocki SET stevilo= (stevilo- ?) WHERE idPripomocki=?";
				PreparedStatement prst = c.prepareStatement(sql);
				prst.setInt(1, st);
				prst.setInt(2, sp.getIdPripomocki());
				
			
				prst.executeUpdate();
				
			}catch (SQLException e){
				rezervirano = false;
			}finally{
				System.out.println("REZERVIRANO!" );
			}		
			return rezervirano;
		}
	 public List<SportniPripomocki> getSportniPripomockiList(int idDvorana) throws Exception{
			List<SportniPripomocki> seznamPripomockov = new ArrayList<SportniPripomocki>();
			Connection c = null; 
			try{
				c = bp.dobiPovezavo();
				String sql ="SELECT * FROM pripomocki p, dvorana d WHERE p.dvorana_idDvorana =? AND p.dvorana_idDvorana = d.idDvorana;";
				PreparedStatement st = c.prepareStatement(sql);
				st.setInt(1, idDvorana);		
				ResultSet rs = st.executeQuery();
				while (rs.next()){
					
					SportniPripomocki pripomocki = new SportniPripomocki();
					Dvorana dvorana = new Dvorana();
					
					dvorana.setIdDvorana(rs.getInt("dvorana_idDvorana"));
					dvorana.setNazivDvorane(rs.getString("d.naziv"));
					
					pripomocki.setNaziv(rs.getString("p.naziv"));
					pripomocki.setIdPripomocki(rs.getInt("idPripomocki"));
					pripomocki.setDvorana(dvorana);
					pripomocki.setCena(rs.getFloat("cena"));
					pripomocki.setStevilo(rs.getInt("stevilo"));
					
					seznamPripomockov.add(pripomocki);		
				}
				
			}catch (SQLException e){
				e.printStackTrace();
			}
			return seznamPripomockov;
		}
	 
	 public boolean deletePripomocekById(int id) throws Exception {
	        boolean deleted = true;
	        Connection c = bp.dobiPovezavo();
	        try {
	            String sql = "DELETE FROM pripomocki WHERE idPripomocki = ?";
	            PreparedStatement st = c.prepareStatement(sql);
	            st.setInt(1, id);
	            st.executeUpdate();

	        } catch (SQLException e) {
	            deleted = false;
	        }
	        return deleted;
	    }
}
