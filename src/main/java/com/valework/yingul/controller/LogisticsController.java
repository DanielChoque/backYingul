package com.valework.yingul.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.net.ssl.HttpsURLConnection;
import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valework.yingul.logistic.SucursalHandler;
import com.valework.yingul.model.AndreaniCot;
import com.valework.yingul.model.Yng_AndreaniCotizacion;
import com.valework.yingul.model.Yng_AndreaniSucursal;
import com.valework.yingul.model.Yng_Cotizar;
import com.valework.yingul.model.Yng_Envio;
import com.valework.yingul.model.Yng_Product;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_Token;
import com.valework.yingul.logistic.*;



@RestController
@RequestMapping("/logistics")
public class LogisticsController {
	private String urlCotizar="https://cotizadorpreprod.andreani.com/ws?wsdl";
	private String urlSuc="https://sucursalespreprod.andreani.com/ws?wsdl";
	
	
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
 	
 	
 	@RequestMapping(value = "/cotizarItemA", method = RequestMethod.POST)
	@ResponseBody
    public String andreaniCotizar(@Valid @RequestBody Yng_Cotizar cotizar) throws MessagingException {
 		Yng_Cotizar cotizarTemp=cotizar;
 		System.out.println(cotizarTemp.toString());
 		
 		try {
 			andreaniPost();
		} catch (Exception e) {
						e.printStackTrace();
		}
 		List<Yng_Cotizar>cotizarList;
 		String ta="";
 		try {
			ta=andreaniCotizacion(cotizarTemp.getCodigo_postal(),cotizarTemp.getCodAndreani(),cotizarTemp.getPeso(),cotizarTemp.getVolumen(),"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		System.out.println("tarifa:"+ta);
 		return ""+ta;
 	}
 	
 	@RequestMapping(value = "/cotizarItemB", method = RequestMethod.POST)
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
    	
    	System.out.println("póst");
		String body="<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"xmlns:ns1=\"urn:ConsultarSucursales\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\"> <env:Header><ns3:Security env:mustUnderstand=\"true\"><ns3:UsernameToken> <ns3:Username>STAGING_WS</ns3:Username><ns3:Password>ANDREANI</ns3:Password> </ns3:UsernameToken></ns3:Security></env:Header><env:Body><ns1:ConsultarSucursales env:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\"><Consulta xsi:type=\"ns2:Map\"><item><key xsi:type=\"xsd:string\">consulta</key><value xsi:type=\"ns2:Map\"> <item><key xsi:type=\"xsd:string\">Localidad</key><value xsi:type=\"xsd:string\"></value></item><item><key xsi:type=\"xsd:string\">"
		+ "CodigoPostal</key><value xsi:type=\"xsd:string\">"
		+ "</value></item><item><key xsi:type=\"xsd:string\">Provincia</key><value xsi:type=\"xsd:string\"></value></item></value></item></Consulta></ns1:ConsultarSucursales></env:Body></env:Envelope>";
    	
		StringEntity stringEntity = new StringEntity(body, "UTF-8");
		
        stringEntity.setChunked(true);

        // Request parameters and other properties.
        HttpPost httpPost = new HttpPost("https://sucursalespreprod.andreani.com/ws?wsdl");
        httpPost.setEntity(stringEntity);
        //httpPost.addHeader("Accept", "text/xml");
        //request.setHeader("Content-Type","application/xml;charset=UTF-8");
        //httpPost.setHeader("Content-Type","application/xml;charset=UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("SOAPAction", "soapAction");

        // Execute and get the response.
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();

        String strResponse = null;
        if (entity != null) {
            strResponse = EntityUtils.toString(entity);
            
        }
      //System.out.println("post  " +strResponse);
        System.out.println("uno");
       
 
     }
    
    
   
    
    
     private Document document;
     public void generadorDom() throws ParserConfigurationException {
    	 //document
    	 DocumentBuilderFactory factoria=DocumentBuilderFactory.newInstance();
    	 DocumentBuilder builder=factoria.newDocumentBuilder();
    	 document =builder.newDocument();
    	     	 
    	 
     }
     public void generarDocument() {
    	 Element productos=document.createElement("productos");
    	 document.appendChild(productos);
    	 
    	 Element producto=document.createElement("producto");
    	 productos.appendChild(producto);
    	 producto.setAttribute("codigo", "1");
    	 
    	 Element nombre=document.createElement("nombre");
    	 nombre.appendChild(document.createTextNode("Teclado"));
    	 producto.appendChild(nombre);
    	 
     }
     
     public void generarXml() throws IOException, TransformerException {
    	 TransformerFactory factoria =TransformerFactory.newInstance();
    	 Transformer transformer=factoria.newTransformer();
    	 Source sourse = new DOMSource(document);
    	 File file= new File("productos.xml");
    	 FileWriter fw=new FileWriter(file);
    	 PrintWriter pw= new PrintWriter(fw);
    	 Result result =new StreamResult(pw);
    	 
    	 transformer.transform(sourse, result);
    	 
     }
     String CodigoPostal="";
     String Localidad="";
     String Provincia="";
     
     @RequestMapping("/test")
     private String testP() throws Exception {
    	 
  		try {
  			this.CodigoPostal="";
  			this.Localidad="";
  			this.Provincia="";
  			andreaniSucursales(this.CodigoPostal,this.Localidad,this.Provincia);
		} catch (Exception e) {
						e.printStackTrace();
		}
   	
    	 return "save";
    }
     

     public String andreaniSucursales(String CodigoPostal,String Localidad,String Provincia) throws Exception{ 
    	 String body="<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"xmlns:ns1=\"urn:ConsultarSucursales\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\"> <env:Header><ns3:Security env:mustUnderstand=\"true\"><ns3:UsernameToken> <ns3:Username>STAGING_WS</ns3:Username><ns3:Password>ANDREANI</ns3:Password> </ns3:UsernameToken></ns3:Security></env:Header><env:Body><ns1:ConsultarSucursales env:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\"><Consulta xsi:type=\"ns2:Map\"><item><key xsi:type=\"xsd:string\">consulta</key><value xsi:type=\"ns2:Map\"> <item><key xsi:type=\"xsd:string\">Localidad</key><value xsi:type=\"xsd:string\"></value></item><item><key xsi:type=\"xsd:string\">"
		+ "CodigoPostal</key><value xsi:type=\"xsd:string\">"+CodigoPostal+ "</value></item><item><key xsi:type=\"xsd:string\">Provincia</key><value xsi:type=\"xsd:string\"></value></item></value></item></Consulta></ns1:ConsultarSucursales></env:Body></env:Envelope>";
    	
		String body3="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<env:Envelope\r\n" + 
				"    xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"\r\n" + 
				"    xmlns:ns1=\"urn:ConsultarSucursales\"\r\n" + 
				"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
				"    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\r\n" + 
				"    xmlns:ns2=\"http://xml.apache.org/xml-soap\"\r\n" + 
				"    xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\r\n" + 
				"    xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
				"    <env:Header>\r\n" + 
				"        <ns3:Security env:mustUnderstand=\"true\">\r\n" + 
				"            <ns3:UsernameToken>\r\n" + 
				"                <ns3:Username></ns3:Username>\r\n" + 
				"                <ns3:Password></ns3:Password>\r\n" + 
				"            </ns3:UsernameToken>\r\n" + 
				"        </ns3:Security>\r\n" + 
				"    </env:Header>\r\n" + 
				"    <env:Body>\r\n" + 
				"        <ns1:ConsultarSucursales env:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
				"            <Consulta xsi:type=\"ns2:Map\">\r\n" + 
				"                <item>\r\n" + 
				"                    <key xsi:type=\"xsd:string\">consulta</key>\r\n" + 
				"                    <value xsi:type=\"ns2:Map\">\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Localidad</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ Localidad
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">CodigoPostal</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ CodigoPostal
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Provincia</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ Provincia
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                    </value>\r\n" + 
				"                </item>\r\n" + 
				"            </Consulta>\r\n" + 
				"        </ns1:ConsultarSucursales>\r\n" + 
				"    </env:Body>\r\n" + 
				"</env:Envelope>";
		String body2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<env:Envelope\r\n" + 
				"    xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"\r\n" + 
				"    xmlns:ns1=\"urn:ConsultarSucursales\"\r\n" + 
				"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
				"    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\r\n" + 
				"    xmlns:ns2=\"http://xml.apache.org/xml-soap\"\r\n" + 
				"    xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\r\n" + 
				"    xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
				"    <env:Header>\r\n" + 
				"        <ns3:Security env:mustUnderstand=\"true\">\r\n" + 
				"            <ns3:UsernameToken>\r\n" + 
				"                <ns3:Username></ns3:Username>\r\n" + 
				"                <ns3:Password></ns3:Password>\r\n" + 
				"            </ns3:UsernameToken>\r\n" + 
				"        </ns3:Security>\r\n" + 
				"    </env:Header>\r\n" + 
				"    <env:Body>\r\n" +""+
				
				"    </env:Body>\r\n" + 
				"</env:Envelope>";

    	 
    	 StringEntity stringEntity = new StringEntity(body3, "UTF-8");
		
        stringEntity.setChunked(true);
        HttpPost httpPost = new HttpPost(urlSuc);
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("SOAPAction", "soapAction");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
        SAXParser sAXParser=saxParseFactory.newSAXParser();

        String numero="";
        String strResponse = null;
        if (entity != null) {
            strResponse = EntityUtils.toString(entity);
            SucursalHandler handlerS=new SucursalHandler();
            sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerS);
            ArrayList<ResultadoConsultarSucursales> sucursaleses=handlerS.getResultadoSucursales();
            for (ResultadoConsultarSucursales versione : sucursaleses) {
            	numero=versione.getNumero();
                System.out.println("versione.getNumero:"+versione.getNumero());
            
            }
        }
        
        System.out.println("strResponse:"+convertiraISO(strResponse));
        return ""+numero;

     }
     
     
     public String andreaniCotizacion(String CodigoPostal,String SucursalRetiro,String Peso,String Volumen,String ValorDeclarado) throws Exception{ 
    	 	String body2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<env:Envelope\r\n" + 
				"    xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"\r\n" + 
				"    xmlns:ns1=\"urn:CotizarEnvio\"\r\n" + 
				"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
				"    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\r\n" + 
				"    xmlns:ns2=\"http://xml.apache.org/xml-soap\"\r\n" + 
				"    xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\r\n" + 
				"    xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
				"    <env:Header>\r\n" + 
				"        <ns3:Security env:mustUnderstand=\"true\">\r\n" + 
				"            <ns3:UsernameToken>\r\n" + 
				"                <ns3:Username>STAGING_WS</ns3:Username>\r\n" + 
				"                <ns3:Password>ANDREANI</ns3:Password>\r\n" + 
				"            </ns3:UsernameToken>\r\n" + 
				"        </ns3:Security>\r\n" + 
				"    </env:Header>\r\n" + 
				"    <env:Body>\r\n" + 
				"        <ns1:CotizarEnvio env:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
				"            <cotizacionEnvio xsi:type=\"ns2:Map\">\r\n" + 
				"                <item>\r\n" + 
				"                    <key xsi:type=\"xsd:string\">cotizacionEnvio</key>\r\n" + 
				"                    <value xsi:type=\"ns2:Map\">\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">CPDestino</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ CodigoPostal
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Cliente</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">CL0003750</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Contrato</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">400006711</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Peso</key>\r\n" + 
				"                            <value xsi:type=\"xsd:int\">"
				+ Peso
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">SucursalRetiro</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ SucursalRetiro
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Volumen</key>\r\n" + 
				"                            <value xsi:type=\"xsd:int\">"
				+ Volumen
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">ValorDeclarado</key>\r\n" + 
				"                            <value xsi:type=\"xsd:int\">"
				+ ValorDeclarado
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                    </value>\r\n" + 
				"                </item>\r\n" + 
				"            </cotizacionEnvio>\r\n" + 
				"        </ns1:CotizarEnvio>\r\n" + 
				"    </env:Body>\r\n" + 
				"</env:Envelope>\r\n" + 
				"";

    	 StringEntity stringEntity = new StringEntity(body2, "UTF-8");
		
        stringEntity.setChunked(true);
        HttpPost httpPost = new HttpPost(urlCotizar);
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("SOAPAction", "soapAction");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
        SAXParser sAXParser=saxParseFactory.newSAXParser();

        String numero="";
        String strResponse = null;
        if (entity != null) {
            strResponse = EntityUtils.toString(entity);
            /*SucursalHandler handlerS=new SucursalHandler();
            sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerS);
            ArrayList<ResultadoConsultarSucursales> sucursaleses=handlerS.getResultadoSucursales();
            for (ResultadoConsultarSucursales versione : sucursaleses) {
            	numero=versione.getNumero();
                System.out.println("versione.getNumero:"+versione.getNumero());
            
            }*/
            
            CotizarHandler handlerC=new CotizarHandler();
            sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerC);
             ArrayList<CotizarEnvioResponse> cotizarEnvioResponses=handlerC.getCotizarEnvioResponse();
            for (CotizarEnvioResponse versione : cotizarEnvioResponses) {
            	numero=versione.getTarifa();
                System.out.println(" log versione:"+versione);
            
            }
        }
        
        System.out.println("strResponse:"+convertiraISO(strResponse));
        return ""+numero;

     }
     
     
     public static String convertiraISO(String s) {
         String out = null;
         try {
             out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
         } catch (java.io.UnsupportedEncodingException e) {
             return null;
         }
         return out;
     }
     
     
     public String andreaniEnviar(Yng_Envio envio) throws Exception{ 
 	 	String body2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
 	 			"<SOAP-ENV:Envelope\r\n" + 
 	 			"    xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\r\n" + 
 	 			"    xmlns:ns1=\"http://www.andreani.com.ar\"\r\n" + 
 	 			"    xmlns:ns2=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\r\n" + 
 	 			"    <SOAP-ENV:Header>\r\n" + 
 	 			"        <ns2:Security SOAP-ENV:mustUnderstand=\"1\">\r\n" + 
 	 			"            <ns2:UsernameToken>\r\n" + 
 	 			"                <ns2:Username>STAGING_WS</ns2:Username>\r\n" + 
 	 			"                <ns2:Password>ANDREANI</ns2:Password>\r\n" + 
 	 			"            </ns2:UsernameToken>\r\n" + 
 	 			"        </ns2:Security>\r\n" + 
 	 			"    </SOAP-ENV:Header>\r\n" + 
 	 			"    <SOAP-ENV:Body>\r\n" + 
 	 			"        <ns1:ConfirmarCompra>\r\n" + 
 	 			"            <ns1:compra>\r\n" + 
 	 			"                <ns1:Calle>Triumvirato</ns1:Calle>\r\n" + 
 	 			"                <ns1:CategoriaDistancia>"
 	 			+ envio.getCategoriaDistancia()
 	 			+ "</ns1:CategoriaDistancia>\r\n" + 
 	 			"                <ns1:CategoriaFacturacion>"
 	 			+envio.getCategoriaFacturacion()
 	 			+ "</ns1:CategoriaFacturacion>\r\n" + 
 	 			"                <ns1:CategoriaPeso>"
 	 			+ envio.getCategoriaFacturacion()
 	 			+ "</ns1:CategoriaPeso>\r\n" + 
 	 			"                <ns1:CodigoPostalDestino>"
 	 			+ envio.getCategoriaPeso()
 	 			+ "</ns1:CodigoPostalDestino>\r\n" + 
 	 			"                <ns1:Contrato>"
 	 			+ envio.getCodigoPostalDestino()
 	 			+ "</ns1:Contrato>\r\n" + 
 	 			"                <ns1:Departamento></ns1:Departamento>\r\n" + 
 	 			"                <ns1:DetalleProductosEntrega></ns1:DetalleProductosEntrega>\r\n" + 
 	 			"                <ns1:DetalleProductosRetiro></ns1:DetalleProductosRetiro>\r\n" + 
 	 			"                <ns1:Email>"
 	 			+ envio.getDetalleProductosRetiro()
 	 			+ "</ns1:Email>\r\n" + 
 	 			"                <ns1:Localidad>"
 	 			+ Localidad
 	 			+ "</ns1:Localidad>\r\n" + 
 	 			"                <ns1:NombreApellido>"
 	 			+ envio.getNombreApellido()
 	 			+ "</ns1:NombreApellido>\r\n" + 
 	 			"                <ns1:NombreApellidoAlternativo>"
 	 			+ envio.getNombreApellidoAlternativo()
 	 			+ "</ns1:NombreApellidoAlternativo>\r\n" + 
 	 			"                <ns1:Numero>"
 	 			+ envio.getNumero()
 	 			+ "</ns1:Numero>\r\n" + 
 	 			"                <ns1:NumeroCelular>"
 	 			+ envio.getNumeroCelular()
 	 			+ "</ns1:NumeroCelular>\r\n" + 
 	 			"                <ns1:NumeroDocumento>"
 	 			+ envio.getNumeroDocumento()
 	 			+ "</ns1:NumeroDocumento>\r\n" + 
 	 			"                <ns1:NumeroTelefono>"
 	 			+ envio.getNumeroTelefono()
 	 			+ "</ns1:NumeroTelefono>\r\n" + 
 	 			"                <ns1:NumeroTransaccion>"
 	 			+ envio.getNumeroTransaccion()
 	 			+ "</ns1:NumeroTransaccion>\r\n" + 
 	 			"                <ns1:Peso>"
 	 			+ envio.getPeso()
 	 			+ "</ns1:Peso>\r\n" + 
 	 			"                <ns1:Piso>"
 	 			+ envio.getPiso()
 	 			+ "</ns1:Piso>\r\n" + 
 	 			"                <ns1:Provincia>"
 	 			+ envio.getProvincia()
 	 			+ "</ns1:Provincia>\r\n" + 
 	 			"                <ns1:SucursalCliente></ns1:SucursalCliente>\r\n" + 
 	 			"                <ns1:SucursalRetiro>"
 	 			+ envio.getSucursalRetiro()
 	 			+ "</ns1:SucursalRetiro>\r\n" + 
 	 			"                <ns1:Tarifa>"
 	 			+ envio.getTarifa()
 	 			+ "</ns1:Tarifa>\r\n" + 
 	 			"                <ns1:TipoDocumento>"
 	 			+ envio.getTipoDocumento()
 	 			+ "</ns1:TipoDocumento>\r\n" + 
 	 			"                <ns1:ValorACobrar>"
 	 			+ envio.getValorACobrar()
 	 			+ "</ns1:ValorACobrar>\r\n" + 
 	 			"                <ns1:ValorDeclarado>"
 	 			+ envio.getValorDeclarado()
 	 			+ "</ns1:ValorDeclarado>\r\n" + 
 	 			"                <ns1:Volumen>"
 	 			+ envio.getValorDeclarado()
 	 			+ "</ns1:Volumen>\r\n" + 
 	 			"            </ns1:compra>\r\n" + 
 	 			"        </ns1:ConfirmarCompra>\r\n" + 
 	 			"    </SOAP-ENV:Body>\r\n" + 
 	 			"</SOAP-ENV:Envelope>";

 	 StringEntity stringEntity = new StringEntity(body2, "UTF-8");
		
     stringEntity.setChunked(true);
     HttpPost httpPost = new HttpPost(urlCotizar);
     httpPost.setEntity(stringEntity);
     httpPost.addHeader("Content-Type", "text/xml");
     httpPost.addHeader("SOAPAction", "soapAction");
     HttpClient httpClient = new DefaultHttpClient();
     HttpResponse response = httpClient.execute(httpPost);
     HttpEntity entity = response.getEntity();
     SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
     SAXParser sAXParser=saxParseFactory.newSAXParser();

     String numero="";
     String strResponse = null;
     if (entity != null) {
         strResponse = EntityUtils.toString(entity);

          EnvioHandler handlerE=new EnvioHandler();
          sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerE);
           ArrayList<EnvioResponce> envioResponses=handlerE.getEnvioResponse();
           System.out.println("EnvioResponce1:");
          for (EnvioResponce versione : envioResponses) {
        	  numero=versione.getNumeroAndreani();
              System.out.println("EnvioResponce:"+versione);
          
          }
     }
     
     System.out.println("strResponse:"+convertiraISO(strResponse));
     return ""+numero;

  }
  
     public String andreanImprimirLink(Yng_Envio envio) throws Exception{ 
  	 	String body2="";

  	 StringEntity stringEntity = new StringEntity(body2, "UTF-8");
 		
      stringEntity.setChunked(true);
      HttpPost httpPost = new HttpPost(urlCotizar);
      httpPost.setEntity(stringEntity);
      httpPost.addHeader("Content-Type", "text/xml");
      httpPost.addHeader("SOAPAction", "soapAction");
      HttpClient httpClient = new DefaultHttpClient();
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
      SAXParser sAXParser=saxParseFactory.newSAXParser();
      

      String numero="";
      String strResponse = null;
      if (entity != null) {
          strResponse = EntityUtils.toString(entity);

           ImprimirConstanciaHandler handlerE=new ImprimirConstanciaHandler();
           sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerE);
            ArrayList<ImprimirConstanciaResponse> envioResponses=handlerE.getImprimirResponce();
            System.out.println("EnvioResponce1:");
           for (ImprimirConstanciaResponse versione : envioResponses) {
         	  numero=versione.getPdfLinkFile();
               System.out.println("EnvioResponce:"+versione);
           
           }
      }
      
      System.out.println("strResponse:"+convertiraISO(strResponse));
      return ""+numero;
      
   
      

   }
  

 	
 	 public List<ResultadoConsultarSucursales> andreaniSucursalList(Yng_AndreaniSucursal cot) throws Exception{ 
    	String body3="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<env:Envelope\r\n" + 
				"    xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"\r\n" + 
				"    xmlns:ns1=\"urn:ConsultarSucursales\"\r\n" + 
				"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
				"    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\r\n" + 
				"    xmlns:ns2=\"http://xml.apache.org/xml-soap\"\r\n" + 
				"    xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\r\n" + 
				"    xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
				"    <env:Header>\r\n" + 
				"        <ns3:Security env:mustUnderstand=\"true\">\r\n" + 
				"            <ns3:UsernameToken>\r\n" + 
				"                <ns3:Username>"
				+ cot.getPassword()
				+ "</ns3:Username>\r\n" + 
				"                <ns3:Password>"
				+ cot.getPassword()
				+ "</ns3:Password>\r\n" + 
				"            </ns3:UsernameToken>\r\n" + 
				"        </ns3:Security>\r\n" + 
				"    </env:Header>\r\n" + 
				"    <env:Body>\r\n" + 
				"        <ns1:ConsultarSucursales env:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
				"            <Consulta xsi:type=\"ns2:Map\">\r\n" + 
				"                <item>\r\n" + 
				"                    <key xsi:type=\"xsd:string\">consulta</key>\r\n" + 
				"                    <value xsi:type=\"ns2:Map\">\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Localidad</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ cot.getLocalidad()
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">CodigoPostal</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ cot.getCodigoPostal()
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                        <item>\r\n" + 
				"                            <key xsi:type=\"xsd:string\">Provincia</key>\r\n" + 
				"                            <value xsi:type=\"xsd:string\">"
				+ cot.getProvincia()
				+ "</value>\r\n" + 
				"                        </item>\r\n" + 
				"                    </value>\r\n" + 
				"                </item>\r\n" + 
				"            </Consulta>\r\n" + 
				"        </ns1:ConsultarSucursales>\r\n" + 
				"    </env:Body>\r\n" + 
				"</env:Envelope>";
		

    	 
    	 StringEntity stringEntity = new StringEntity(body3, "UTF-8");
		
        stringEntity.setChunked(true);
        HttpPost httpPost = new HttpPost(urlSuc);
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("SOAPAction", "soapAction");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
        SAXParser sAXParser=saxParseFactory.newSAXParser();

        String numero="";
        String strResponse = null;
     List<ResultadoConsultarSucursales> sucursal = null;
        if (entity != null) {
            strResponse = EntityUtils.toString(entity);
            SucursalHandler handlerS=new SucursalHandler();
            sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerS);
            ArrayList<ResultadoConsultarSucursales> sucursaleses=handlerS.getResultadoSucursales();
            sucursal=handlerS.getResultadoSucursales();
            for (ResultadoConsultarSucursales versione : sucursaleses) {
            	numero=versione.getNumero();
                System.out.println("versione.getNumero:"+versione.getNumero());
                
            
            }
        }
        
        // List<ResultadoConsultarSucursales> sucursal
        
        System.out.println("strResponse:"+convertiraISO(strResponse));
        return sucursal;

     }
 	 
  	@RequestMapping(value = "/branch", method = RequestMethod.POST)
  	@ResponseBody
      public List<ResultadoConsultarSucursales> sucursalesList(@Valid @RequestBody Yng_AndreaniSucursal suc){
 		System.out.println(suc.toString());
    	  List<ResultadoConsultarSucursales> sucursal = null;
    	  Yng_AndreaniSucursal cot=new Yng_AndreaniSucursal();
    	  cot.setUsername("");
    	  cot.setPassword("");
    	  cot.setCodigoPostal("");
    	  cot.setLocalidad("");
    	  cot.setProvincia("");
    	  cot=suc;
    	  cot.setUsername("STAGING_WS");
    	  cot.setPassword("ANDREANI");
    	  System.out.println(cot.toString());
		try {
			sucursal = andreaniSucursalList(cot);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  return sucursal;
      }
 	@RequestMapping(value = "/cotizarAndreani", method = RequestMethod.POST)
	@ResponseBody
    public CotizarEnvioResponse andreaniCotizarList(@Valid @RequestBody Yng_AndreaniCotizacion cotizar) throws MessagingException {
 		CotizarEnvioResponse cot=null;
 		Yng_AndreaniCotizacion cotizarTemp=cotizar;
 		System.out.println(cotizarTemp.toString()); 		
 		try {
 			andreaniPost();
		} catch (Exception e) {
						e.printStackTrace();
		}
 		List<Yng_Cotizar>cotizarList;
 		String ta="";
 		try {
 			cot=andreaniCotiza(cotizarTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		System.out.println("tarifa:"+ta);
 		return cot;
 	}
    public CotizarEnvioResponse andreaniCotiza(Yng_AndreaniCotizacion cotizarTemp) throws Exception{ 
	 	String body2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"<env:Envelope\r\n" + 
			"    xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"\r\n" + 
			"    xmlns:ns1=\"urn:CotizarEnvio\"\r\n" + 
			"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
			"    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\r\n" + 
			"    xmlns:ns2=\"http://xml.apache.org/xml-soap\"\r\n" + 
			"    xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\r\n" + 
			"    xmlns:enc=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
			"    <env:Header>\r\n" + 
			"        <ns3:Security env:mustUnderstand=\"true\">\r\n" + 
			"            <ns3:UsernameToken>\r\n" + 
			"                <ns3:Username>"
			+ cotizarTemp.getUsername()
			+ "</ns3:Username>\r\n" + 
			"                <ns3:Password>"
			+ cotizarTemp.getPassword()
			+ "</ns3:Password>\r\n" + 
			"            </ns3:UsernameToken>\r\n" + 
			"        </ns3:Security>\r\n" + 
			"    </env:Header>\r\n" + 
			"    <env:Body>\r\n" + 
			"        <ns1:CotizarEnvio env:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\r\n" + 
			"            <cotizacionEnvio xsi:type=\"ns2:Map\">\r\n" + 
			"                <item>\r\n" + 
			"                    <key xsi:type=\"xsd:string\">cotizacionEnvio</key>\r\n" + 
			"                    <value xsi:type=\"ns2:Map\">\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">CPDestino</key>\r\n" + 
			"                            <value xsi:type=\"xsd:string\">"
			+ cotizarTemp.getCodigoPostal()
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">Cliente</key>\r\n" + 
			"                            <value xsi:type=\"xsd:string\">"
			+ "CL0003750"
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">Contrato</key>\r\n" + 
			"                            <value xsi:type=\"xsd:string\">"
			+ "400006711"
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">Peso</key>\r\n" + 
			"                            <value xsi:type=\"xsd:int\">"
			+ cotizarTemp.getPeso()
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">SucursalRetiro</key>\r\n" + 
			"                            <value xsi:type=\"xsd:string\">"
			+ cotizarTemp.getCodigoDeSucursal()
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">Volumen</key>\r\n" + 
			"                            <value xsi:type=\"xsd:int\">"
			+ cotizarTemp.getVolumen()
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                        <item>\r\n" + 
			"                            <key xsi:type=\"xsd:string\">ValorDeclarado</key>\r\n" + 
			"                            <value xsi:type=\"xsd:int\">"
			+ cotizarTemp.getValorDeclarado()
			+ "</value>\r\n" + 
			"                        </item>\r\n" + 
			"                    </value>\r\n" + 
			"                </item>\r\n" + 
			"            </cotizacionEnvio>\r\n" + 
			"        </ns1:CotizarEnvio>\r\n" + 
			"    </env:Body>\r\n" + 
			"</env:Envelope>\r\n" + 
			"";

	 StringEntity stringEntity = new StringEntity(body2, "UTF-8");
	
    stringEntity.setChunked(true);
    HttpPost httpPost = new HttpPost(urlCotizar);
    httpPost.setEntity(stringEntity);
    httpPost.addHeader("Content-Type", "text/xml");
    httpPost.addHeader("SOAPAction", "soapAction");
    HttpClient httpClient = new DefaultHttpClient();
    HttpResponse response = httpClient.execute(httpPost);
    HttpEntity entity = response.getEntity();
    SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
    SAXParser sAXParser=saxParseFactory.newSAXParser();

    String numero="";
    String strResponse = null;
    CotizarEnvioResponse cot=null;
    if (entity != null) {
        strResponse = EntityUtils.toString(entity);
        /*SucursalHandler handlerS=new SucursalHandler();
        sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerS);
        ArrayList<ResultadoConsultarSucursales> sucursaleses=handlerS.getResultadoSucursales();
        for (ResultadoConsultarSucursales versione : sucursaleses) {
        	numero=versione.getNumero();
            System.out.println("versione.getNumero:"+versione.getNumero());
        
        }*/
        
        CotizarHandler handlerC=new CotizarHandler();
        sAXParser.parse(new InputSource(new StringReader(strResponse)), handlerC);
         ArrayList<CotizarEnvioResponse> cotizarEnvioResponses=handlerC.getCotizarEnvioResponse();
        for (CotizarEnvioResponse versione : cotizarEnvioResponses) {
        	numero=versione.getTarifa();
            System.out.println(" log versione:"+versione);
            cot=versione;
            
        
        }
    }
    
    System.out.println("strResponse:"+convertiraISO(strResponse));
    return cot;

 }

     
    
}
