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
import rek.vao.Uporabnik;

import java.sql.Statement;

public class TipSportaDAO {

	private BazenPovezav bp;
	public TipSportaDAO()
	{
	this.bp = new BazenPovezav();
	}
	
	public void close () {
		bp.izprazniBazen();
	}
	
	public void shraniTipSporta(TipSporta tip) throws Exception {
		Connection c = bp.dobiPovezavo();
		
		try{
			String sql = ("INSERT INTO tipsporta (naziv) VALUES(?)");
			PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, tip.getNaziv());

			ResultSet rs = st.getGeneratedKeys();
			if (rs != null && rs.next()) {
				System.out.println("Id vnosa: " + rs.getLong(1));
			}
			st.executeUpdate();			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public List<TipSporta> getTipSports(){
		List<TipSporta> seznamTipSporta = new ArrayList<TipSporta>();
		
		try{
			Connection c = bp.dobiPovezavo();
			String sql ="SELECT * FROM tipsporta;";
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				TipSporta temp = new TipSporta();
				
				temp.setIdTipSporta(rs.getInt("idTipSporta"));
				temp.setNaziv(rs.getString("naziv"));
				
				seznamTipSporta.add(temp);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
	
		return seznamTipSporta;
	}
	//za izpis vseh tipov sportov ki jih ponuja sportni objekt
	public List<TipSporta> getSportiZaObjekt(SportniObjekt objekt){
		List<TipSporta> seznamTipovSporta = new ArrayList<TipSporta>();
		
		try{
			Connection c = bp.dobiPovezavo();
			String sql ="SELECT DISTINCT s.naziv, s.idTipSporta FROM tipsporta s, dvorana d, sportniObjekt o WHERE d.tipsporta_idtipsporta= s.idTipSporta AND d.sportniObjekt_idSportniObjekt=o.idSportniObjekt  AND o.idSportniObjekt= ?;";
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, objekt.getIdSportniObjekt());
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				TipSporta temp = new TipSporta();
				
				temp.setIdTipSporta(rs.getInt("idTipSporta"));
				temp.setNaziv(rs.getString("naziv"));
				
				seznamTipovSporta.add(temp);
			}
			bp.vrniPovezavo(c);
		}catch (Exception e){
			e.printStackTrace();
		}
	
		return seznamTipovSporta;
	}
}