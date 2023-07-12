package br.com.erudio.converters;

public class NumberConverter {
	
	public static Double convertToDouble(String number) {
		if (number == null) {
			return 0D;
		} else {
			String num = number.replaceAll(",", ".");
			if (isNumeric(num)) {
				return Double.parseDouble(num);
			} else {
				return 0D;
			}
		}
	}

	public static boolean isNumeric(String number) {
		if (number == null) {
			return false;
		} else {
			String num = number.replaceAll(",", ".");
			return num.matches("[-+]?[0-9]*\\.?[0-9]+");
		}
	}

}
