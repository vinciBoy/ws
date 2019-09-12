package com.soaint.proyectoService.security;

import java.util.ResourceBundle;

public class PropertiesReader {

	private static final ResourceBundle PROPERTIES = ResourceBundle.getBundle("application");
    
	//Url, clave y usuario de RightNow
    public static String getUrlRightNow(){  return PROPERTIES.getString("url.rn"); }
    public static String getClaveRightNow(){  return PROPERTIES.getString("clave.rn"); }
    public static String getUsuarioRightNow(){  return PROPERTIES.getString("usuario.rn"); }
    public static String getUrlRightNowCreacion(){  return PROPERTIES.getString("url.rn.email"); }
    
	//Url, clave y usuario de Eloqua
    public static String getUrlEloqua(){  return PROPERTIES.getString("url.elo"); }
    public static String getClaveEloqua(){  return PROPERTIES.getString("clave.elo"); }
    public static String getUsuarioEloqua(){  return PROPERTIES.getString("usuario.elo"); }
    public static String getUsuarioEloquaCreacion(){  return PROPERTIES.getString("usuario.eloquaDosPuntos"); }
    public static String getUrlEloquaCreacion(){  return PROPERTIES.getString("url.elo.crear"); }
    public static String getUrlEloquaEmail(){  return PROPERTIES.getString("url.elo.email"); }
    
	//Url, clave y usuario de SalesCloud
    public static String getUrlSalesCloud(){  return PROPERTIES.getString("url.osc"); }
    public static String getUrlMailSalesCloud(){  return PROPERTIES.getString("url.osc.email"); }
    public static String getClaveSalesCloud(){  return PROPERTIES.getString("clave.osc"); }
    public static String getUsuarioSalesCloud(){  return PROPERTIES.getString("usuario.osc"); }
    public static String getUrlSalesCloudCreacion(){  return PROPERTIES.getString("url.osc.crear"); }
    public static String getUrlSalesCloudLeed(){  return PROPERTIES.getString("url.osc.lead"); }
    public static String getUrlSalesCloudLeedQuery(){  return PROPERTIES.getString("url.osc.lead.query"); }
}