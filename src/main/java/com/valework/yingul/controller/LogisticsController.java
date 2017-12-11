package com.valework.yingul.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logistics")
public class LogisticsController {

	
	
	 private final String USER_AGENT = "Mozilla/5.0";
	
    @RequestMapping("/da")
    private String daniel() {
    	String da="guardado";
    	LogisticsController http = new LogisticsController();

		System.out.println("Testing 1 - Send Http GET request");
		try {
				http.getAccessToken();
			} catch (Exception e) {

			e.printStackTrace();
		}
 	
    	return da;
    }
	private final String apiKey="46e903f2a3e1cb1933db836b99df089f91e9fd57";
	private final String secretKey="8a5b791c1e426a96650b54693f04aec02e89b280";
	private final String urlApi= "https://api.enviopack.com";
	private final String auth="/auth";
	private String token;
    private String TYPE="application/x-www-form-urlencoded";
    
    
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
 		System.out.println("\nSending 'POST' request to URL : " + urlApi);
 		System.out.println("Post parameters : " + urlParameters);
 		System.out.println("Response Code : " + responseCode);

    	BufferedReader in =new BufferedReader(new InputStreamReader(con.getInputStream()));
    	
    	String inputLine;
    	StringBuffer response=new StringBuffer();
    	while((inputLine = in.readLine())!=null) {
    		response.append(inputLine);
    		
    	}
    	in.close();    	
    	System.out.println(response.toString());
    	
    	
    	
    	
    	
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

	
}
