package rek.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import rek.sql.BazenPovezav;
import rek.vao.Dvorana;
import rek.vao.SportniObjekt;
import rek.vao.Termini;
import rek.vao.TipSporta;

import java.sql.Statement;


public class TerminiDAO {

	private BazenPovezav bp;
	public TerminiDAO()
	{
	this.bp = new BazenPovezav();
	}
	
	public void close () {
		bp.izprazniBazen();
	}
	
	public Termini shraniTermin(Termini termin) throws Exception {
		Connection c = bp.dobiPovezavo();
		int id =0;
		try{
			String sql = ("INSERT INTO termini (datum, zacetniCas, koncniCas, zasedenost, dvorana_idDvorana) VALUES(?,?,?,?,?)");
			PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setDate(1, new java.sql.Date(termin.getDatum().getTimeInMillis()));
			st.setTime(2, new java.sql.Time(termin.getZacetniCas().getTimeInMillis()));
			st.setTime(3, new java.sql.Time(termin.getKoncniCas().getTimeInMillis()));
			st.setBoolean(4, termin.getZasedenost());
			st.setInt(5, termin.getDvorana().getIdDvorana());
			
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs != null && rs.next()) {
				id = rs.getInt(1);
				System.out.println("Id vnosa: " + id);
				termin.setIdTermini(id);
			}
			
			
		}catch (SQLException e){
			e.printStackTrace();
		}
		return termin;
		
	}
	
	public Termini getTerminByIDDvorInCas(int idDvor,Calendar date, Calendar cas) throws Exception {
		Connection c = null;
		Termini temp = new Termini();
		
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String cs = sf.format(cas.getTime());
		String ds= df.format(date.getTime());
//		String Sz = sf.format(termin.getZacetniCas().getTime());
//		
//		String  konc [] = Sk.split(":");
//		String  zac [] = Sz.split(":");
//		 
//		int koncno =  Integer.parseInt(konc[0]) - Integer.parseInt(zac[0]);
			
			try{
			 	c = bp.dobiPovezavo();
	            String sql = "SELECT * FROM termini WHERE dvorana_idDvorana=? AND zacetniCas =? AND datum=?";
	            PreparedStatement prst = c.prepareStatement(sql);
	            prst.setInt(1, idDvor);
	            prst.setString(2,cs);
	            prst.setString(3,ds);
	            
	            
	            ResultSet rs = prst.executeQuery();
	            while (rs.next()) {
	             
					Dvorana tempD =  new Dvorana();
					
					tempD.setIdDvorana(rs.getInt("dvorana_idDvorana"));
					
					java.sql.Time zacetniCas =rs.getTime("zacetniCas");
					Calendar   zacetniC = Calendar.getInstance();  
					zacetniC.setTime(zacetniCas);
					
					temp.setZacetniCas(zacetniC);
					
					java.sql.Time koncniCas =rs.getTime("koncniCas");
					Calendar   koncniC = Calendar.getInstance();  
					koncniC.setTime(koncniCas);
					
					java.sql.Date datum =rs.getDate("datum");
					Calendar   dat = Calendar.getInstance();  
					dat.setTime(datum);
					
					temp.setKoncniCas(koncniC);
					
					temp.setIdTermini(rs.getInt("idTermini"));
					temp.setDatum(dat);
					temp.setZasedenost(rs.getBoolean("zasedenost"));
					temp.setDvorana(tempD);
				}
				
				
			}catch (SQLException e){
				e.printStackTrace();
			}
			return temp;
		}
		
	
	public List<Termini> getTerminis(){
		List<Termini> seznamTerminov = new ArrayList<Termini>();
		
		try{
			Connection c = bp.dobiPovezavo();
			String sql ="SELECT * FROM termini ORDER BY datum;";
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				Termini temp = new Termini();
				Dvorana tempD =  new Dvorana();
				
				tempD.setIdDvorana(rs.getInt("dvorana_idDvorana"));
				
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar   zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
				
				temp.setZacetniCas(zacetniC);
				
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
				
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
				
				temp.setKoncniCas(koncniC);
				
				temp.setIdTermini(rs.getInt("idTermini"));
				temp.setDatum(dat);
				temp.setZasedenost(rs.getBoolean("zasedenost"));
				temp.setDvorana(tempD);
				
				
				seznamTerminov.add(temp);
			}
		
		}catch (Exception e){
			e.printStackTrace();
		}
	
		return seznamTerminov;
	}
	
	public List<Termini> getTerminiBYidDvorane(int idDvorana){
		List<Termini> seznamTerminov = new ArrayList<Termini>();
		Connection c=null;
		try{
			c = bp.dobiPovezavo();
				String sql = "SELECT * FROM termini WHERE dvorana_idDvorana = ? ";
				PreparedStatement prst = c.prepareStatement(sql);
				prst.setInt(1, idDvorana);
				ResultSet rs = prst.executeQuery();
				while (rs.next()){
					
				Termini temp = new Termini();
				Dvorana tempD =  new Dvorana();
				
				tempD.setIdDvorana(rs.getInt("dvorana_idDvorana"));
				
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar   zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
				
				temp.setZacetniCas(zacetniC);
				
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
				
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
				
				temp.setKoncniCas(koncniC);
				
				temp.setIdTermini(rs.getInt("idTermini"));
				temp.setDatum(dat);
				temp.setZasedenost(rs.getBoolean("zasedenost"));
				temp.setDvorana(tempD);
				
				
				seznamTerminov.add(temp);
			}
		
		}catch (Exception e){
			e.printStackTrace();
		}
	
		return seznamTerminov;
	}
	
	public Termini getTerminById(int id) throws Exception {
		Termini ter = new Termini();
		Connection c = bp.dobiPovezavo();
        try {
           
            String sql = "SELECT * FROM Termini WHERE idTermini= ?;";
            PreparedStatement prst = c.prepareStatement(sql);
            prst.setInt(1, id);

            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
            	
				Dvorana tempD =  new Dvorana();		
				tempD.setIdDvorana(rs.getInt("dvorana_idDvorana"));
				
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ter;
    }	
}