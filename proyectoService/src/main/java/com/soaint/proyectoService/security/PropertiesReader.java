package com.soaint.proyectoService.security;

import java.util.ResourceBundle;

public class PropertiesReader {

	private static final ResourceBundle PROPERTIES = ResourceBundle.getBundle("application");
	
 //------------------------------------------Url, clave y usuario de RightNow----------------------------------------
    public static String getUrlRightNow(){  return PROPERTIES.getString("url.rn"); }
    public static String getUrlRightNowApi(){  return PROPERTIES.getString("url.rn.api"); }
    public static String getUrlRightNowEmail(){  return PROPERTIES.getString("url.rn.email"); }
    public static String getClaveRightNow(){  return PROPERTIES.getString("clave.rn"); }
    public static String getUsuarioRightNow(){  return PROPERTIES.getString("usuario.rn"); }
    
 //-----------------------------------------Url, clave y usuario de Eloqua-------------------------------------------
    public static String getUrlEloqua(){  return PROPERTIES.getString("url.elo"); }
    public static String getUrlEloquaApi(){  return PROPERTIES.getString("url.elo.api"); }
    public static String getUrlEloquaEmail(){  return PROPERTIES.getString("url.elo.email"); }
    public static String getClaveEloqua(){  return PROPERTIES.getString("clave.elo"); }
    public static String getUsuarioEloquaCreacion(){  return PROPERTIES.getString("usuario.elo"); }

 //-----------------------------------------Url, clave y usuario de SalesCloud---------------------------------------   
    public static String getUrlSalesCloud(){  return PROPERTIES.getString("url.osc"); }
    public static String getUrlSalesCloudApi(){  return PROPERTIES.getString("url.osc.api"); }
    public static String getUrlSalesCloudMail(){  return PROPERTIES.getString("url.osc.email"); }
    public static String getUrlSalesCloudLeed(){  return PROPERTIES.getString("url.osc.lead"); }
    public static String getUrlSalesCloudLeedQuery(){  return PROPERTIES.getString("url.osc.lead.query"); }
    public static String getUrlSalesCloudLeedQueryEmail() { return PROPERTIES.getString("url.osc.lead.query.Email"); }
    public static String getClaveSalesCloud(){  return PROPERTIES.getString("clave.osc"); }
    public static String getUsuarioSalesCloud(){  return PROPERTIES.getString("usuario.osc"); }
}