package br.com.cincoquatro.locacaoespaco.util;

public class StringUtil {

	public static String toUpperCase(String text) {
		if (text == null)
			return null;

		return text.trim().toUpperCase();
	}

}
