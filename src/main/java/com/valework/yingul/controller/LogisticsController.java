package com.valework.yingul.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.mail.MessagingException;
import javax.net.ssl.HttpsURLConnection;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valework.yingul.model.Yng_Cotizar;

import com.valework.yingul.model.Yng_Token;


@RestController
@RequestMapping("/logistics")
public class LogisticsController {
	
	
	/*
	 curl -X GET \
	'https://api.enviopack.com/cotizar/costo?access_token=[TU_ACCESS_TOKEN]
	  &provincia=C
	  &codigo_postal=1405
	  &peso=1.5
	  &paquetes=20x3x5
	  &direccion_envio=22' 
	 
	 curl -X GET \
	'https://api.enviopack.com/cotizar/precio/a-domicilio?access_token=[TU_ACCESS_TOKEN]
	  &provincia=C
	  &codigo_postal=1414
	  &peso=1.5'
	   
	 curl -X GET \
	'https://api.enviopack.com/cotizar/precio/a-sucursal?access_token=[TU_ACCESS_TOKEN]
	  &provincia=C
	  &localidad=137
	  &peso=1
	  &paquetes=20x3x5,5x20x10
	  &correo=andreani
	  &direccion_envio=268'
	  
	  
	  
	  *
	  */
	
	private final String apiKey="46e903f2a3e1cb1933db836b99df089f91e9fd57";
	private final String secretKey="8a5b791c1e426a96650b54693f04aec02e89b280";
	private final String urlApi= "https://api.enviopack.com";
	private final String cotizarDomicilio="/cotizar/precio/a-domicilio?access_token=";
	private final String cotizarSucursal="/cotizar/precio/a-sucursal?access_token=";
	private final String cotizarCosto="/cotizar/costo?access_token=";
	
	private final String auth="/auth";
	private String TOKEN;
    private String TYPE="application/x-www-form-urlencoded";
    private final String USER_AGENT = "Mozilla/5.0";
    private Yng_Cotizar yngCotizar= new Yng_Cotizar();
	
    @RequestMapping("/token")
    private String token() {
    	String da="guardado";
    	LogisticsController http = new LogisticsController();

		
		try {
				http.getAccessToken();
			} catch (Exception e) {

			e.printStackTrace();
		}
 	
    	return da;
    }
    
    private void cotizarDomicilio()throws Exception{
    	getAccessToken();
    	String urlDomiciliio="https://api.enviopack.com/cotizar/precio/a-domicilio?access_token="+TOKEN+"&provincia=C&codigo_postal=1414&peso=1.5";
    	urlDomiciliio=this.urlApi+this.cotizarDomicilio+this.TOKEN+"&provincia=C&codigo_postal=1414&peso=1.5";

 		URL obj = new URL(urlDomiciliio);
 		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 		con.setRequestMethod("GET");
 		con.setRequestProperty("User-Agent", USER_AGENT);
 		int responseCode = con.getResponseCode();
 		System.out.println("Response Code : " + responseCode);
 		BufferedReader in = new BufferedReader(
 		           new InputStreamReader(con.getInputStream()));
 		String inputLine;
 		StringBuffer response = new StringBuffer();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		in.close();
 		System.out.println(response.toString());
    	
    }
    
    @RequestMapping("/cotizar")
     private String cotizar() {
    	try {
    		
			cotizarDomicilio();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	 return "save";
    }
    
    private void getAccessToken() throws Exception{

    	URL obj=new URL(urlApi+auth);
    	HttpURLConnection con=(HttpURLConnection) obj.openConnection();
    	con.setRequestMethod("POST");
    	con.setRequestProperty("User-Agent", USER_AGENT);
    	con.setRequestProperty("Content-Type", TYPE);
    	String urlParameters = "api-key"+"="+apiKey+"&secret-key="+secretKey;
    	con.setDoOutput(true);
    	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
 		wr.writeBytes(urlParameters);
 		wr.flush();
 		wr.close();
    	
    	int responseCode=con.getResponseCode();
 		//System.out.println("\nSending 'POST' request to URL : " + urlApi);
 		//System.out.println("Post parameters : " + urlParameters);
 		System.out.println("Response Code : " + responseCode);

    	BufferedReader in =new BufferedReader(new InputStreamReader(con.getInputStream()));
    	
    	String inputLine;
    	StringBuffer response=new StringBuffer();
    	while((inputLine = in.readLine())!=null) {
    		response.append(inputLine);
    		
    	}
    	in.close();    	
    	System.out.println(response.toString());
    	System.out.println("");
    	jsonToken(response.toString());
}
    
    
 	// HTTP POST request
 	private void sendPost() throws Exception {
 		String url = "https://selfsolve.apple.com/wcResults.do";
 		URL obj = new URL(url);
 		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 		//add reuqest header
 		con.setRequestMethod("POST");
 		con.setRequestProperty("User-Agent", USER_AGENT);
 		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

 		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

 		// Send post request
 		con.setDoOutput(true);
 		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
 		wr.writeBytes(urlParameters);
 		wr.flush();
 		wr.close();

 		int responseCode = con.getResponseCode();
 		System.out.println("\nSending 'POST' request to URL : " + url);
 		System.out.println("Post parameters : " + urlParameters);
 		System.out.println("Response Code : " + responseCode);

 		BufferedReader in = new BufferedReader(
 		        new InputStreamReader(con.getInputStream()));
 		String inputLine;
 		StringBuffer response = new StringBuffer();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		in.close();

 		//print result
 		System.out.println(response.toString());

 	}
    
 	private void sendGet() throws Exception {

 		//String url = "http://www.google.com/search?q=mkyong";
 		//String urlCotizar="https://api.enviopack.com/cotizar/costo?access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXUyJ9.eyJleHAiOjE1MTI4NDA3MzcsInVzZXJuYW1lIjoiNDZlOTAzZjJhM2UxY2IxOTMzZGI4MzZiOTlkZjA4OWY5MWU5ZmQ1NyIsImlhdCI6IjE1MTI4MjYzMzcifQ.NYUk7AKJI0oPt0okRKkmnAaMvv8jgt5jMby2al0HeeVzobwhFbm-uj9Acfvhgl-Yf7Px8NEY9RSbIdhGU36mvyOgq2jGl9anjt4uIBmMTaR6OaNQ4IYzpoVc7ZouUbg-qZqj5bRmLIAIq_ckFwnwagtotYYm_qyYUc1Yi4xboaG81FqAZSpALGpF3FM8Z8neN6dtvRE_sgzMVgnPVcLe4FsExUKZS4BmPLraO94rOpT_p61WOl8xvJ2vKHWiRDiqJ2Kryb66aij6I8crj6EECvOR5zv7gcMa7Szzrg4eFkCVuZBwRak3SUg3F-VKENczUZ9L9rWOLJdxYyclRQlFZMXLdYkNyCgZJUY5Q4Fh1JdFg7S0Cj8VuJ5Jmdtx8HyqxDfPEngAnkASa9xdXi8v2jW1PVD4Q9r-B1gA2fF4I8PwjSwsk4uvdQ-SDoI-eitXq_AXGXUAzBohavRjvtrgq0zI-Ez0anbTMQ_D18bDWTgwtAFljHN5sLiMjLEuDtZjhodPX9jjSqn_s_S45Pkd5oO1ikYryQgO-2VggpKc3w40b7dDLBcHGFqpDsfaRbgdq6I-4MHgmg3X2un4XAMq4ZAdMl4kpg99cO6-2M1X6p7AAdp59Cy7TTQMrI-wA0uDq4FXVWY8mp7q9cXeMg2bJTjCm6z9SnwDn-4w_mN_S_A&provincia=C&codigo_postal=1405&peso=1.5&paquetes=20x3x5&direccion_envio=829";
 		String urlDomiciliio="https://api.enviopack.com/cotizar/precio/a-domicilio?access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXUyJ9.eyJleHAiOjE1MTI4NDA3MzcsInVzZXJuYW1lIjoiNDZlOTAzZjJhM2UxY2IxOTMzZGI4MzZiOTlkZjA4OWY5MWU5ZmQ1NyIsImlhdCI6IjE1MTI4MjYzMzcifQ.NYUk7AKJI0oPt0okRKkmnAaMvv8jgt5jMby2al0HeeVzobwhFbm-uj9Acfvhgl-Yf7Px8NEY9RSbIdhGU36mvyOgq2jGl9anjt4uIBmMTaR6OaNQ4IYzpoVc7ZouUbg-qZqj5bRmLIAIq_ckFwnwagtotYYm_qyYUc1Yi4xboaG81FqAZSpALGpF3FM8Z8neN6dtvRE_sgzMVgnPVcLe4FsExUKZS4BmPLraO94rOpT_p61WOl8xvJ2vKHWiRDiqJ2Kryb66aij6I8crj6EECvOR5zv7gcMa7Szzrg4eFkCVuZBwRak3SUg3F-VKENczUZ9L9rWOLJdxYyclRQlFZMXLdYkNyCgZJUY5Q4Fh1JdFg7S0Cj8VuJ5Jmdtx8HyqxDfPEngAnkASa9xdXi8v2jW1PVD4Q9r-B1gA2fF4I8PwjSwsk4uvdQ-SDoI-eitXq_AXGXUAzBohavRjvtrgq0zI-Ez0anbTMQ_D18bDWTgwtAFljHN5sLiMjLEuDtZjhodPX9jjSqn_s_S45Pkd5oO1ikYryQgO-2VggpKc3w40b7dDLBcHGFqpDsfaRbgdq6I-4MHgmg3X2un4XAMq4ZAdMl4kpg99cO6-2M1X6p7AAdp59Cy7TTQMrI-wA0uDq4FXVWY8mp7q9cXeMg2bJTjCm6z9SnwDn-4w_mN_S_A&provincia=C&codigo_postal=1414&peso=1.5";

 		
 		URL obj = new URL(urlDomiciliio);
 		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

 		// optional default is GET
 		con.setRequestMethod("GET");

 		//add request header
 		con.setRequestProperty("User-Agent", USER_AGENT);

 		int responseCode = con.getResponseCode();
 		//System.out.println("\nSending 'GET' request to URL : " + url);
 		System.out.println("Response Code : " + responseCode);

 		BufferedReader in = new BufferedReader(
 		        new InputStreamReader(con.getInputStream()));
 		String inputLine;
 		StringBuffer response = new StringBuffer();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		in.close();

 		//print result
 		System.out.println(response.toString());

 	}
 	
 	
 	
 	
 	private void jsonToken( String json) {
         ObjectMapper mapper = new ObjectMapper();
         Yng_Token token;
		try {
			token = mapper.readValue(json, Yng_Token.class);
			// System.out.println(token.toString());
			 System.out.println("token:"+token.getToken());
			 TOKEN=token.getToken();
			 
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
 	}
 	
 	@RequestMapping(value = "/cotizarItem", method = RequestMethod.POST)
	@ResponseBody
    public String cotizarPost(@Valid @RequestBody Yng_Cotizar cotizar) throws MessagingException {
 		Yng_Cotizar cotizarTemp=cotizar;
 		try {
			cotizarDomicilio1(cotizarTemp);
		} catch (Exception e) {
						e.printStackTrace();
		}
 		List<Yng_Cotizar>cotizarList;
 		
 		return "save";
 	}
 	
 	private void cotizarDomicilio1(Yng_Cotizar cotizar)throws Exception{
    	getAccessToken();
    	String urlDomiciliio="https://api.enviopack.com/cotizar/precio/a-domicilio?access_token="+TOKEN+"&provincia=C&codigo_postal=1414&peso=1.5";
    	urlDomiciliio=this.urlApi+this.cotizarSucursal+this.TOKEN+"&provincia="+cotizar.getProvincia()+"&codigo_postal="+cotizar.getCodigo_postal()+"&peso="+cotizar.getPeso();

 		URL obj = new URL(urlDomiciliio);
 		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 		con.setRequestMethod("GET");
 		con.setRequestProperty("User-Agent", USER_AGENT);
 		int responseCode = con.getResponseCode();
 		System.out.println("Response Code : " + responseCode);
 		BufferedReader in = new BufferedReader(
 		           new InputStreamReader(con.getInputStream()));
 		String inputLine;
 		StringBuffer response = new StringBuffer();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		in.close();
 		System.out.println(response.toString());
    	
    }
 	
 	
 	@RequestMapping(value = "/cotizarItem", method = RequestMethod.POST)
	@ResponseBody
    public String andreaniPost(@Valid @RequestBody Yng_Cotizar cotizar) throws MessagingException {
 		Yng_Cotizar cotizarTemp=cotizar;
 		try {
 			andreaniPost();
		} catch (Exception e) {
						e.printStackTrace();
		}
 		List<Yng_Cotizar>cotizarList;
 		
 		return "save";
 	}
 	
    private void andreaniPost() throws Exception{

    	URL obj=new URL(urlApi+auth);
    	HttpURLConnection con=(HttpURLConnection) obj.openConnection();
    	con.setRequestMethod("POST");
    	con.setRequestProperty("User-Agent", USER_AGENT);
    	con.setRequestProperty("Content-Type", TYPE);
    	String urlParameters = "api-key"+"="+apiKey+"&secret-key="+secretKey;
    	con.setDoOutput(true);
    	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
 		wr.writeBytes(urlParameters);
 		wr.flush();
 		wr.close();
    	
    	int responseCode=con.getResponseCode();
 		//System.out.println("\nSending 'POST' request to URL : " + urlApi);
 		//System.out.println("Post parameters : " + urlParameters);
 		System.out.println("Response Code : " + responseCode);

    	BufferedReader in =new BufferedReader(new InputStreamReader(con.getInputStream()));
    	
    	String inputLine;
    	StringBuffer response=new StringBuffer();
    	while((inputLine = in.readLine())!=null) {
    		response.append(inputLine);
    		
    	}
    	in.close();    	
    	System.out.println(response.toString());
    	System.out.println("");
    	jsonToken(response.toString());
}
    
    private void sendPosyyt() throws Exception {
    	

 		String url = "https://selfsolve.apple.com/wcResults.do";
 		URL obj = new URL(url);
 		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

 		//add reuqest header
 		con.setRequestMethod("POST");
 		con.setRequestProperty("User-Agent", USER_AGENT);
 		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

 		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

 		// Send post request
 		con.setDoOutput(true);
 		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
 		wr.writeBytes(urlParameters);
 		wr.flush();
 		wr.close();

 		int responseCode = con.getResponseCode();
 		System.out.println("\nSending 'POST' request to URL : " + url);
 		System.out.println("Post parameters : " + urlParameters);
 		System.out.println("Response Code : " + responseCode);

 		BufferedReader in = new BufferedReader(
 		        new InputStreamReader(con.getInputStream()));
 		String inputLine;
 		StringBuffer response = new StringBuffer();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		in.close();

 		//print result
 		System.out.println(response.toString());

 	}
    
    
     private Document document;
     public void generadorDom() {
    	 //document
    	 
    	 
    	 
    	 
     }

	
}
