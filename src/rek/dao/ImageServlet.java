package rek.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rek.sql.BazenPovezav;


/**
 * Servlet implementation class Image
 */
@WebServlet(urlPatterns="/image")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BazenPovezav bp= new BazenPovezav();
		Integer idSlike = 0;
		Integer idUpo = 0;
		if(Integer.parseInt(request.getParameter("imgid"))!=0)
		{
		idSlike= Integer.parseInt(request.getParameter("imgid"));
		
		System.out.println("id SLIIKE ____ :::: "+idSlike);
		  try{     
				Connection c = bp.dobiPovezavo();   
		    Statement st=c.createStatement();
		    String strQuery = "SELECT slika FROM slike WHERE sportniobjekt_idSportniObjekt="+idSlike;
		    
		    
		    ResultSet rs = st.executeQuery(strQuery);
			
		    String imgLen=null;
		    if(rs.next()){
		    
		      imgLen = rs.getString(1);
		      if (imgLen.isEmpty())
		    	  System.out.println("prazno : "+ imgLen);
		       } 
		    rs = st.executeQuery(strQuery);
		    if(rs.next()){
		      int len = imgLen.length();
		      byte [] rb = new byte[len];
		      InputStream readImg = rs.getBinaryStream(1);
		      int index=readImg.read(rb, 0, len);  
		      response.reset();
		      response.setContentType("image/jpg");
		      response.getOutputStream().write(rb, 0, len);
		      response.getOutputStream().flush();
		      response.getOutputStream().close();
		      st.close();
		      rs.close();
		    }
		  
		    
		    if(imgLen==null)
		    {
		    	strQuery = "SELECT slika FROM slike WHERE sportniobjekt_idSportniObjekt= 1";
		    	    rs = st.executeQuery(strQuery);
		    	 if(rs.next()){
		    	      imgLen = rs.getString(1);
		    	 }
		    	 rs = st.executeQuery(strQuery);
		    	 if(rs.next()){
		    	      int len = imgLen.length();
		    	      byte [] rb = new byte[len];
		    	      InputStream readImg = rs.getBinaryStream(1);
		    	      int index=readImg.read(rb, 0, len);  
		    	      response.reset();
		              response.setContentType("image/jpg");
		              response.getOutputStream().write(rb, 0, len);
		              response.getOutputStream().flush();
		              response.getOutputStream().close();
		    	    }
		    	 st.close();
		         rs.close();
		    }
		    
		   
		    bp.vrniPovezavo(c);
		  }
		  catch (Exception e ){
		    System.out.println(e.getMessage());
		  }
		  finally{
			  bp.izprazniBazen();
		  }
		}
		
		
		else if(Integer.parseInt(request.getParameter("idUpo"))!=0)
		{
			idUpo = Integer.parseInt(request.getParameter("idUpo"));
			System.out.println("id SLIIKE ____ :::: "+idUpo);
		
		 try{     
				Connection c = bp.dobiPovezavo();   
		    Statement st=c.createStatement();
		    String strQuery = "SELECT slika FROM slike WHERE uporabnik_idUporabnik="+idUpo;
		    
		    
		    ResultSet rs = st.executeQuery(strQuery);
			
		    String imgLen=null;
		    if(rs.next()){
		    
		      imgLen = rs.getString(1);
		      if (imgLen.isEmpty())
		    	  System.out.println("prazno : "+ imgLen);
		       } 
		    rs = st.executeQuery(strQuery);
		    if(rs.next()){
		      int len = imgLen.length();
		      byte [] rb = new byte[len];
		      InputStream readImg = rs.getBinaryStream(1);
		      int index=readImg.read(rb, 0, len);  
		      response.reset();
		      response.setContentType("image/jpg");
		      response.getOutputStream().write(rb, 0, len);
		      response.getOutputStream().flush();
		      response.getOutputStream().close();
		      st.close();
		      rs.close();
		    }
		  
		    
		    if(imgLen==null)
		    {
		    	strQuery = "SELECT slika FROM slike WHERE idSlike= 15";
		    	    rs = st.executeQuery(strQuery);
		    	 if(rs.next()){
		    	      imgLen = rs.getString(1);
		    	 }
		    	 rs = st.executeQuery(strQuery);
		    	 if(rs.next()){
		    	      int len = imgLen.length();
		    	      byte [] rb = new byte[len];
		    	      InputStream readImg = rs.getBinaryStream(1);
		    	      int index=readImg.read(rb, 0, len);  
		    	      response.reset();
		              response.setContentType("image/jpg");
		              response.getOutputStream().write(rb, 0, len);
		              response.getOutputStream().flush();
		              response.getOutputStream().close();
		    	    }
		    	 st.close();
		         rs.close();
		    }
		    
		   
		    bp.vrniPovezavo(c);
		  }
		  catch (Exception e ){
		    System.out.println(e.getMessage());
		  }
		  finally{
			  bp.izprazniBazen();
		  }
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
