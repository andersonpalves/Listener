package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validacoes {

	private static Pattern FORMATO_PERMITIDOS = Pattern.compile("([^\\s]+(\\.(?i)(dat))$)");
	
	public static boolean validaExtensaoArquivo(String formato){
		Matcher mtch = FORMATO_PERMITIDOS.matcher(formato);
		if(mtch.matches()){
			return true;
		}
		return false;
	}
	
}