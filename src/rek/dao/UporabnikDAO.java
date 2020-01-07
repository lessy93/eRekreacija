package rek.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.util.ArrayList;
import java.util.List;

import rek.sql.BazenPovezav;
import rek.vao.SportniObjekt;
import rek.vao.Uporabnik;


public class UporabnikDAO {
	
	private BazenPovezav bp;
	public UporabnikDAO()
	{
	this.bp=new BazenPovezav();
	}
	
	public void close () {
		bp.izprazniBazen();
	}
	
	//kodiranje gesla v SHA1
	public static String sha1(String geslo) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(geslo.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}	         
	    return sb.toString();  
	}
	
	public void shraniUporabnik(Uporabnik up) throws Exception {
		Connection c=bp.dobiPovezavo();
		
		try{
			PreparedStatement st=c.prepareStatement("INSERT INTO uporabnik (ime, priimek, ePosta, geslo, status, sportniobjekt_idSportniObjekt, aktiven) VALUES (?,?,?,?,?,?,?)");
			
			st.setString(1, up.getIme());
			st.setString(2, up.getPriimek());
			st.setString(3, up.getEmail());
			st.setString(4, sha1(up.getGeslo()));
			st.setString(5, up.getStatus());
			st.setInt(6, up.getSportniObjekt().getIdSportniObjekt());
			st.setString(7, "da");
			st.executeUpdate();
				
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public Uporabnik getUporabnikByEmailGeslo(String Posta, String Geslo) throws Exception {
		Uporabnik up = new Uporabnik();
		Connection c = bp.dobiPovezavo();
        try {
           
            String sql = "SELECT * FROM uporabnik u, sportniobjekt s WHERE u.ePosta=? AND u.geslo=? AND u.sportniobjekt_idSportniObjekt=s.idSportniObjekt AND u.aktiven='da'";
            PreparedStatement prst = c.prepareStatement(sql);
            prst.setString(1, Posta);
            prst.setString(2, sha1(Geslo));

            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
            	SportniObjekt sp = new SportniObjekt();
            	
            	sp.setIdSportniObjekt(rs.getInt("idSportniObjekt"));
            	sp.setNaziv(rs.getString("naziv"));
            	sp.setLokacija(rs.getString("lokacija"));
            	
            	up.setId(rs.getInt("idUporabnik"));
                up.setIme(rs.getString("ime"));
                up.setPriimek(rs.getString("priimek"));
                up.setEmail(rs.getString("ePosta"));
                up.setGeslo(rs.getString("geslo"));
                up.setStatus(rs.getString("status"));
                up.setAktiven(rs.getString("aktiven"));
                up.setSportniObjekt(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return up;
    }
	// Preverjanje ce je email ze v bazi!		
	public Boolean getCheckEmail(String Posta) throws Exception {			
		Connection c = bp.dobiPovezavo();      
		try {	         	         
			String sql = "SELECT * FROM uporabnik u WHERE u.ePosta LIKE ? AND u.aktiven='da'";	        
			PreparedStatement prst = c.prepareStatement(sql);	        
			prst.setString(1, Posta);	        
			ResultSet rs = prst.executeQuery();	        
			while (rs.next()) {                   		        
			}     
			if(rs.first()){        
				System.out.println("NAPAKA! EMAIL JE ZE V UPORABI!");	        	
				return false;    	
			}else{        
				System.out.println("OK! EMAIL JE NA VOLJO!");	        	
				return true;	
			}        
		} catch (SQLException e) {	    
			e.printStackTrace();      
		} 	
		return null;	
	}		
	
	
	public List<Uporabnik> getUporabniks(){
		List<Uporabnik> seznamUporabnikov = new ArrayList<Uporabnik>();
		
		try{
			Connection c = bp.dobiPovezavo();
			String sql ="SELECT * FROM uporabnik WHERE aktiven='da';";
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				Uporabnik temp = new Uporabnik();		

				temp.setId(rs.getInt("idUporabnik"));
				temp.setIme(rs.getString("ime"));
				temp.setPriimek(rs.getString("priimek"));
				temp.setEmail(rs.getString("ePosta"));
				temp.setStatus(rs.getString("status"));
				temp.setAktiven(rs.getString("aktiven"));
				seznamUporabnikov.add(temp);
				
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return seznamUporabnikov;
	}
	
	 public boolean deleteUporabnikById(int idUporabnik) throws Exception {
	        boolean deleted = true;
	        Connection c = bp.dobiPovezavo();
	        try {
	        //    String sql = "DELETE FROM uporabnik WHERE idUporabnik = ?";
	            String sql = "UPDATE uporabnik SET aktiven='ne' WHERE idUporabnik = ?";
	            PreparedStatement st = c.prepareStatement(sql);
	            st.setInt(1, idUporabnik);
	            st.executeUpdate();

	        } catch (SQLException e) {
	            deleted = false;
	        } 
	        return deleted;
	    }
	 
	 
	 public Uporabnik getUporabnikById(int geslo) throws Exception {
			Uporabnik up = new Uporabnik();
			Connection c = bp.dobiPovezavo();
	        try {
	           
	            String sql = "SELECT * FROM uporabnik WHERE idUporabnik = ? ";
	            PreparedStatement prst = c.prepareStatement(sql);
	            prst.setInt(1, geslo);

	            ResultSet rs = prst.executeQuery();
	            while (rs.next()) {
	            	SportniObjekt objekt = new SportniObjekt();
	            	objekt.setIdSportniObjekt(rs.getInt("sportniobjekt_idSportniObjekt"));
	            	
	            	up.setId(rs.getInt("idUporabnik"));
	                up.setIme(rs.getString("ime"));
	                up.setPriimek(rs.getString("priimek"));
	                up.setEmail(rs.getString("ePosta"));
	                up.setGeslo(rs.getString("geslo"));
	                up.setSportniObjekt(objekt);
	                up.setStatus(rs.getString("status"));
	                up.setAktiven(rs.getString("aktiven"));
	  
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	    	System.out.print(up.toString());
	        return up;
	    }	
	 
	 
	 public boolean updateUporabnik (Uporabnik up) throws Exception{
			boolean updated = true;
			Connection c = bp.dobiPovezavo();
			try{
				String sql = "UPDATE uporabnik SET ime =?, priimek =?, ePosta =?, geslo =?, sportniobjekt_idSportniObjekt =?, aktiven =? WHERE idUporabnik = ?";
				PreparedStatement st = c.prepareStatement(sql);
				
				st.setString(1, up.getIme());
				st.setString(2, up.getPriimek());
			//	st.setString(3, up.getStatus());
				st.setString(3, up.getEmail());
				st.setString(4,	sha1(up.getGeslo()));
				st.setInt(5, up.getSportniObjekt().getIdSportniObjekt());
				st.setString(6, up.getAktiven());
				st.setInt(7, up.getId());
				
				st.executeUpdate();
				
			}catch (SQLException e){
				updated = false;
			}finally{
				System.out.println("SHRANJENO!" +up);
			}		
			return updated;
		}
}
