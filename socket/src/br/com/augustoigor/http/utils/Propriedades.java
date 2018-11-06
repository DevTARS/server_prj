package br.com.augustoigor.http.utils;

import java.util.ResourceBundle;

public class Propriedades {

	static ResourceBundle rb = ResourceBundle.getBundle("propriedades");
	
	public static String diretorio = rb.getString("diretorio");

}
