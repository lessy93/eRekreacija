package rek.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class BazenPovezav {

	public BazenPovezav() {
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			povezaveDoBaze = new ArrayList<>();
			for (int i = 0; i < 1; i++) {
				Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/p22013rekreacija?useSSL=false"
						, "root",
						"root");
						
						/*.getConnection("jdbc:mysql://164.8.251.196:3306/"
								+ "p22013rekreacija", "p22013rekreacija",
								"gp22013rekreacija");*/
				povezaveDoBaze.add(c);
				
//				Connection c = DriverManager
//				.getConnection("jdbc:mysql://ndure.si:3306/"
//						+ "zadmin_lubek", "mojlubek",
//						"enu4ypuhe");
//				povezaveDoBaze.add(c);
//				
				if (i == 0) {
					System.out.println("i=0");
				}
				System.out.println("Ustvarjam povezavo: " + c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Connection> povezaveDoBaze = null;

	public Connection dobiPovezavo() {
		try {
			Connection c = povezaveDoBaze.get(0);
			System.out.println("Dajem povezavo: " + c);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void vrniPovezavo(Connection c) throws Exception {
		System.out.println("VraÄ�am povezavo v bazen: " + c);
		povezaveDoBaze.add(c);
	}

	public void izprazniBazen() {
		try {
			for (Connection c : povezaveDoBaze) {
				System.out.println("Zapiram povezavo: " + c);
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
