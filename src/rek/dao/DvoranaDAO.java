package rek.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;

import rek.sql.BazenPovezav;
import rek.vao.Dvorana;
import rek.vao.SportniObjekt;
import rek.vao.TipSporta;

public class DvoranaDAO {

	private BazenPovezav bp;
	public DvoranaDAO()
	{
	this.bp=new BazenPovezav();
	}
	
	
	public void close () {
		bp.izprazniBazen();
	}
	
	public void shraniDvorana(Dvorana dvorana) throws Exception {
		Connection c = bp.dobiPovezavo();
		
		try{
			String sql = "insert into dvorana (naziv, tipIgrisca, opis, sportniobjekt_idSportniObjekt, tipsporta_idTipSporta) values(?, ?, ?, ?, ?)";
			PreparedStatement st=c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, dvorana.getNazivDvorane());
			st.setString(2, dvorana.getTipIgrisca());
			st.setString(3, dvorana.getOpis());
			st.setInt(4, dvorana.getSportniobjekt().getIdSportniObjekt());
			st.setInt(5, dvorana.getTipSporta().getIdTipSporta());

			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs != null && rs.next()) {
				System.out.println("Id vnosa: " + rs.getLong(1));
			}

		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public List<Dvorana> getDvoranas() throws Exception{
		List<Dvorana> seznamDvoran = new ArrayList<Dvorana>();
		Connection c=null; 
		try{
			c = bp.dobiPovezavo();
			String sql ="SELECT * FROM dvorana d, tipsporta t WHERE t.idTipSporta=d.tipsporta_idTipSporta;";
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				
				Dvorana temp = new Dvorana();
				SportniObjekt sportniObjekt = new SportniObjekt();
				TipSporta tipSporta = new TipSporta();
				
				sportniObjekt.setIdSportniObjekt(rs.getInt("sportniobjekt_idSportniObjekt"));
				tipSporta.setIdTipSporta(rs.getInt("tipsporta_idTipSporta"));
				tipSporta.setNaziv(rs.getString("t.naziv"));
				
				temp.setIdDvorana(rs.getInt("idDvorana"));
				temp.setNazivDvorane(rs.getString("naziv"));
				temp.setTipIgrisca(rs.getString("tipIgrisca"));
				temp.setOpis(rs.getString("opis"));
				temp.setSportniobjekt(sportniObjekt);
				temp.setTipSporta(tipSporta);
				
				seznamDvoran.add(temp);		
			}
			
		}catch (SQLException e){
			System.out.println("Napaka Dvorana list!");
			
		}
		return seznamDvoran;
	}
	
	public List<Dvorana> getDvoranaById(int idDvorana) throws Exception{
		List<Dvorana> dvo = new ArrayList<Dvorana>();
		Connection c = null;
		try{
			c = bp.dobiPovezavo();
			String sql = "SELECT * FROM dvorana d, sportniObjekt s,tipsporta t WHERE sportniobjekt_idSportniObjekt = ? AND s.idSportniObjekt=d.sportniobjekt_idSportniObjekt AND t.idTipSporta=d.tipsporta_idTipSporta;";
			PreparedStatement prst = c.prepareStatement(sql);
			prst.setInt(1, idDvorana);
			ResultSet rs = prst.executeQuery();
			while (rs.next()){
				Dvorana temp = new Dvorana();
				SportniObjekt sportniObjekt = new SportniObjekt();
				TipSporta tipSporta = new TipSporta();
				
				sportniObjekt.setIdSportniObjekt(rs.getInt("sportniobjekt_idSportniObjekt"));
				tipSporta.setIdTipSporta(rs.getInt("tipsporta_idTipSporta"));
				tipSporta.setNaziv(rs.getString("t.naziv"));
				
				temp.setIdDvorana(rs.getInt("idDvorana"));
				temp.setNazivDvorane(rs.getString("naziv"));
				temp.setTipIgrisca(rs.getString("tipIgrisca"));
				temp.setOpis(rs.getString("opis"));
				temp.setSportniobjekt(sportniObjekt);
				temp.setTipSporta(tipSporta);
				
				dvo.add(temp);
			}
			
		} catch (Exception e){
			System.out.println("Napaka ListDvoranbyID");
			System.out.println(e.getStackTrace());
		} 
		
		return dvo; 
	}
	public Dvorana getDvoranaByIdEna(int id) throws Exception {
		Dvorana d = new Dvorana();
		Connection c = bp.dobiPovezavo();
        try {
           
            String sql = "SELECT * FROM dvorana d, tipsporta t, sportniobjekt o WHERE d.idDvorana =? AND d.tipsporta_idTipSporta = t.idTipSporta AND o.idSportniObjekt=d.sportniobjekt_idSportniObjekt;";
            PreparedStatement prst = c.prepareStatement(sql);
            prst.setInt(1, id);

            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
            	
            	SportniObjekt objekt = new SportniObjekt();
            	TipSporta tipSporta = new TipSporta();
            	
            	objekt.setIdSportniObjekt(rs.getInt("sportniobjekt_idSportniObjekt"));
            	objekt.setNaziv(rs.getString("o.naziv"));
            	objekt.setLokacija(rs.getString("lokacija"));
            	
            	tipSporta.setIdTipSporta(rs.getInt("tipsporta_idTipSporta"));
            	tipSporta.setNaziv(rs.getString("t.naziv"));
            	
            	
            	d.setIdDvorana(rs.getInt("idDvorana"));
            	d.setNazivDvorane(rs.getString("d.naziv"));
            	d.setOpis(rs.getString("opis"));
            	d.setTipIgrisca(rs.getString("tipIgrisca"));
            	d.setSportniobjekt(objekt);
            	d.setTipSporta(tipSporta);
            	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    	System.out.print(d.toString());
        return d;
    }	
	
	 public boolean deleteDvoranaById(int id) throws Exception {
	        boolean deleted = true;
	        Connection c = bp.dobiPovezavo();
	        try {
	            String sql = "DELETE FROM dvorana WHERE idDvorana = ?";
	            PreparedStatement st = c.prepareStatement(sql);
	            st.setInt(1, id);
	            st.executeUpdate();

	        } catch (SQLException e) {
	            deleted = false;
	        }
	        return deleted;
	    }
	 public boolean updateDvorana (Dvorana d) throws Exception{
			boolean updated = true;
			Connection c = bp.dobiPovezavo();
			try{
				System.out.println("POTATO:" + d);
				String sql = "UPDATE dvorana SET naziv =?, tipIgrisca =?, opis =?, sportniobjekt_idSportniObjekt =?, tipsporta_idTipSporta =?  WHERE idDvorana = ?";
				PreparedStatement st = c.prepareStatement(sql);
				
				st.setString(1, d.getNazivDvorane());
				st.setString(2, d.getTipIgrisca());
				st.setString(3, d.getOpis());
				st.setInt(4, d.getSportniobjekt().getIdSportniObjekt());
				st.setInt(5, d.getTipSporta().getIdTipSporta());
				st.setInt(6, d.getIdDvorana());
				
				st.executeUpdate();
				
			}catch (SQLException e){
				updated = false;
			}		
			return updated;
		}
 
}
