package com.soaint.proyectoService.metodosConexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Metodos {

	static private final String USER_AGENT = "Mozilla/5.0";
	static public HttpURLConnection con = null;
	static String mensajeCaracteres;

//--------------------------------------------Método para conexion con url más id---------------------------------------------------
	public static void conexionId(long id, String url) throws Exception {

		// conectar a la url
		URL obj = new URL(url + id);
		con = (HttpURLConnection) obj.openConnection();
	}

//--------------------------------------------Método para conexion con url más email------------------------------------------------
	public static void conexionEmail(String email, String url) throws Exception {

		// conectar a la url
		URL obj = new URL(url + "'" + email + "'");
		con = (HttpURLConnection) obj.openConnection();
	}

//----------------------------------------------------Método para autenticar---------------------------------------------------------
	public static void Autenticador(String key, String user) throws Exception {

		// pasarlo a Base64 el autenticador y poner autorizaciones
		String auth = user + ":" + key;
		String autorizacion = new String(Base64.getEncoder().encode(auth.getBytes()));
		con.setRequestProperty("Authorization", "Basic " + autorizacion);
	}

	//---------------------------------------------Método para el tipo de petición---------------------------------------------------
	public static void conexionMetodo(String metodo) throws Exception {

		// el valor la petición
		con.setRequestMethod(metodo);

		// agregar encabezado de solicitud
		con.setRequestProperty("User-Agent", USER_AGENT);
	}

//-----------------------------------------------Metodo de creación del contacto-----------------------------------------------------
	public static void crearContacto(String url, String key, String user, String json) throws Exception {

		String autorizacion = user + ":" + key;
		String basicAutorizacion = "Basic " + Base64.getEncoder().encodeToString(autorizacion.getBytes());

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		StringEntity entity = new StringEntity(json);
		httpPost.setHeader(HttpHeaders.AUTHORIZATION, basicAutorizacion);
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		client.execute(httpPost);
		client.close();
	}

//----------------------------------------------------Eliminar contacto por id------------------------------------------------------
	public static void eliminar(long id, String url, String user, String key) throws Exception {

		Metodos.conexionId(id, url);
		Metodos.conexionMetodo("DELETE");
		Metodos.Autenticador(key, user);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		in.close();

		con.disconnect();
	}

//------------------------------------------Recorro el json para devolver un string del json-----------------------------------------
	public static String recorrerJson() throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

//-----------------------------------------------------Compruebo si existen tildes, ñ-------------------------------------------------
	public static String comprobacionCaracteres(String api) throws IOException {

		if (Metodos.con.getResponseCode() == 200) {
			mensajeCaracteres = "Fué creado satisfatoriamente en " + api + "  😎🍻.\n";
		} else {
			mensajeCaracteres = "Error en el email de " + api + ", pude que contenga caracteres extraños";
		}

		return mensajeCaracteres;
	}

//----------------------------------------------Parseo letas para quitar tildes y las ñ------------------------------------------------
	public static String parseLetras(String jsonSend) {
		jsonSend = jsonSend.replace("ñ", "n").replace("Ñ", "N").replace("Á", "A").replace("á", "a").replace("É", "E")
				.replace("é", "e").replace("Í", "I").replace("í", "i").replace("Ó", "O").replace("ó", "o")
				.replace("Ú", "U").replace("ú", "u").replace("Ä", "A").replace("ä", "a").replace("Ë", "E")
				.replace("ë", "e").replace("Ï", "I").replace("ï", "i").replace("Ö", "O").replace("ö", "o")
				.replace("Ü", "U").replace("ü", "u");
		return jsonSend;
	}
}