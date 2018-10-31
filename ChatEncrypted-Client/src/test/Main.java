package test;

import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		String text = "Hallo World! You are bright!";
		System.out.println("Text: "+text);

		String binary = new BigInteger(text.getBytes()).toString(2);
		System.out.println("As binary: "+binary);

		String text2 = new String(new BigInteger(binary, 2).toByteArray());
		System.out.println("As text: "+text2);
		
		BigInteger text1 = new BigInteger(binary);
		System.out.println(text1.multiply(new BigInteger("2")));
	}

}
