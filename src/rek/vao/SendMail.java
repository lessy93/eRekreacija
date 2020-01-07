package rek.vao;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {
	
	
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	public static void poslji(String sub, String text, String recipient)
	{
		String username = "st.pdm2019";
	    String password = "pdm2019*";

	    Properties props = new Properties();

	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.from", "st.pdm2019@gmail.com");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.port", "587");
	    props.setProperty("mail.debug", "true");

	    Session session = Session.getInstance(props, null);
	    MimeMessage msg = new MimeMessage(session);

	    try {
			msg.setRecipients(Message.RecipientType.TO, recipient);
			msg.setSubject(sub);
			msg.setSentDate(new Date());
			msg.setText(text);

			Transport transport = session.getTransport("smtp");

			transport.connect(username, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			System.out.println("Email ni poslan");
			e.printStackTrace();
		}
	  
	}
	public static String regText(Uporabnik up)
	{
		String st = null; 
		if(up.getStatus().equalsIgnoreCase("Upravljalec"))
		{
			st ="Upravljalec";
		}
		if(up.getStatus().equalsIgnoreCase("uporabnik"))
		{
			st="Uporabnik";
		}
		return "Dobrodošli na strani eRekreacija, vaši registrirani podatki : \n "+ up.getIme() +"\n "+ up.getPriimek()+"\n status :"+st +"\n Email : "+up.getEmail()+"\n Veselimo se sodelovanja z vami." ;
	}
	
	public static String rezText(Uporabnik up, Termini t)
	{
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY");
		SimpleDateFormat cas = new SimpleDateFormat("HH:ss");
		
		return up.getIme()+"\nRezervirali ste dvorano : "+t.getDvorana().getNazivDvorane()+ ",\nki se nahaja na lokaciji : \n"+t.getDvorana().getSportniobjekt().getLokacija() +",\nza dan : " + df.format(t.getDatum().getTime())+ " ob "+cas.format(t.getZacetniCas().getTime())+ " do "+ cas.format(t.getKoncniCas().getTime())+ "\n \n Lep dan naprej vaš Erekreacija ! ";
	}
}
