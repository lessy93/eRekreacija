package rek.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import rek.sql.BazenPovezav;

/**
 * Servlet implementation class SportniObjektServlet
 */
@WebServlet("/SportniObjektServlet")
@MultipartConfig(maxFileSize = 16177215)   
public class SportniObjektServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SportniObjektServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 int idSportniObj = 0;
	        String obj = "";
	        try {
	        	idSportniObj = Integer.parseInt(request.getParameter("idSpo"));
	        	System.out.println("id sportnega objekta je :"+idSportniObj);
			} catch (NumberFormatException e) {
				
				System.out.println("Napaka, dodaj sliko! ");
			}
	        
	        int idUporabnika = 0;
	        try {
	        	idUporabnika = Integer.parseInt(request.getParameter("idUpo"));
	        	System.out.println("id uporabnika je :"+idUporabnika);
			} catch (NumberFormatException e) {
				
				System.out.println("Napaka, dodaj sliko! ");
				System.out.println("id uporabnika je :"+idUporabnika);
			}
	        
	      if(idSportniObj!= 0){
	        	
	        	 InputStream inputStream = null; 
	             
	             Part filePart = request.getPart("photo");
	             if (filePart != null) {
	                
	            	 obj = filePart.getName();
	                 System.out.println(filePart.getName());
	                 System.out.println(filePart.getSize());
	                 System.out.println(filePart.getContentType());
	                 
	                 inputStream = filePart.getInputStream();
	             }

	             String message = null;  
	             BazenPovezav b = null;
	             try {
	             	 b = new BazenPovezav();
	             	Connection conn  = b.dobiPovezavo();
	                 String sql = "INSERT INTO slike (naziv, slika, sportniobjekt_idSportniObjekt, uporabnik_idUporabnik) values (?, ?, ?,?)";
	                 PreparedStatement statement = conn.prepareStatement(sql);
	                 statement.setString(1, obj);
	                 
	                 if (inputStream != null) {
	                     statement.setBlob(2, inputStream);
	                 }
	                 statement.setInt(3, idSportniObj);
	                 statement.setInt(4, idUporabnika);
	                 int row = statement.executeUpdate();
	                 if (row > 0) {
	                     message = "File uploaded and saved into database";
	                 }

	             } catch (SQLException ex) {
	                 message = "ERROR: " + ex.getMessage();
	                 ex.printStackTrace();
	             } finally {
	             	 b.izprazniBazen();
	                 request.setAttribute("Message", message);
	                  
	                 getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
	     	        }
	     		}
	}

}
