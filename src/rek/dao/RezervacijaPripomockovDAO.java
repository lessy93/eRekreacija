package rek.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rek.sql.BazenPovezav;
import rek.vao.RezervacijaPripomockov;
import rek.vao.Termini;
import java.sql.Statement;

public class RezervacijaPripomockovDAO {

	private BazenPovezav bp;
	public RezervacijaPripomockovDAO()
	{
	this.bp = new BazenPovezav();
	}
	public void close () {
		bp.izprazniBazen();
	}
	
	public void shraniRezervacijoPripomockov(RezervacijaPripomockov rezervacija) throws Exception {
		Connection c = bp.dobiPovezavo();
		
		try{
			String sql = ("INSERT INTO rezervacijaPripomockov ( naziv, termini_idTermini, stevilo) VALUES(?,?,?)");
			PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, rezervacija.getNaziv());
			st.setInt(2, rezervacija.getTermin().getIdTermini());
			st.setInt(3, rezervacija.getStevilo());


			ResultSet rs = st.getGeneratedKeys();
			if (rs != null && rs.next()) {
				System.out.println("Id vnosa: " + rs.getLong(1));
			}
			st.executeUpdate();
			
		}catch (SQLException e){
			e.printStackTrace();
		}	
	}
	
	public List<RezervacijaPripomockov> getRezervacijaPripomockov(){
		List<RezervacijaPripomockov> seznamRezervacijPripomockov= new ArrayList<RezervacijaPripomockov>();	
		try{
			Connection c = bp.dobiPovezavo();
			String sql ="SELECT * FROM rezervacijaPripomockov;";
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()){				
				RezervacijaPripomockov temp = new RezervacijaPripomockov();
				Termini tempT = new Termini();		
				tempT.setIdTermini(rs.getInt("termini_idTermini"));		
				temp.setIdRezervacijaPripomockov(rs.getInt("idRezervacijaPripomockov"));
				//temp.setTermin(tempT);			
				temp.setStevilo(rs.getInt("stevilo"));	
			
				seznamRezervacijPripomockov.add(temp);	
			}	
		}catch (Exception e){
			e.printStackTrace();
		}	
		return seznamRezervacijPripomockov;
	}
	
	public RezervacijaPripomockov getRezPripomockovByTermin(Termini ter) throws Exception {
		RezervacijaPripomockov rezPrip= new RezervacijaPripomockov();
		Connection c = bp.dobiPovezavo();
        try {
           
            String sql = "SELECT * FROM rezervacijaPripomockov WHERE termini_idTermini=?";
            PreparedStatement prst = c.prepareStatement(sql);
            prst.setInt(1, ter.getIdTermini());

            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
            	Termini tempTer= new Termini();
                        	
            	rezPrip.setIdRezervacijaPripomockov(rs.getInt("idRezervacijaPripomockov"));
            	rezPrip.setNaziv(rs.getString("naziv"));
            	tempTer.setIdTermini(rs.getInt("termini_idTermini"));
            	rezPrip.setTerminiPripomockiRez(tempTer);
            	rezPrip.setStevilo(rs.getInt("stevilo"));
            	
            
            	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezPrip;
    }	
}