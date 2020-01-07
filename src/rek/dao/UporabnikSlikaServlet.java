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

import com.sun.swing.internal.plaf.basic.resources.basic;

import rek.sql.BazenPovezav;

/**
 * Servlet implementation class UporabnikSlikaServlet
 */
@WebServlet("/UporabnikSlikaServlet")
@MultipartConfig(maxFileSize = 16177215)   
public class UporabnikSlikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     //private BazenPovezav b;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UporabnikSlikaServlet() {
        super();
       
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
		
        int idUporabnika = 0;
        String klobasa = "";
        try {
        	idUporabnika = Integer.parseInt(request.getParameter("idUpo"));
        	System.out.println("id uporabnika je :"+idUporabnika);
		} catch (NumberFormatException e) {
			
			System.out.println("Napaka, dodaj sliko! ");
			System.out.println("id uporabnika je :"+idUporabnika);
		}
       
        if(idUporabnika!=0){

        InputStream inputStream = null; 
         
        Part filePart = request.getPart("photo");
        if (filePart != null) {
           
        	klobasa = filePart.getName();
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
            String sql = "INSERT INTO slike (naziv, slika, uporabnik_idUporabnik) values (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, klobasa);
            if (inputStream != null) {
                statement.setBlob(2, inputStream);
            }
            statement.setInt(3, idUporabnika);
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
