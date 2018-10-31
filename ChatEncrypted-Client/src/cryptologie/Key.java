package cryptologie;

import java.math.BigInteger;

public class Key {

	private int privateKey;
	public int p = 13009;
	public int g = 2001;
	public int publicKeyMe;
	public int publicKeyYou;
	private int key;
	
	public Key() {
		
		privateKey = (int) ((Math.random() * p - 2) + 1);
		publicKeyMe = (int) Math.pow(g, privateKey) % p;
	}
	
	public int getPublicKey() {
		return publicKeyMe;
	}
	
	public void createKey(int publicKey) {
		this.key = (int) Math.pow(publicKey, privateKey) % p;
	}

	public String encrypt(String msg) {
		String binary = new BigInteger(msg.getBytes()).toString(2);
		BigInteger binaryInt = new BigInteger(binary);
		
		binaryInt = binaryInt.multiply(new BigInteger(Integer.toString(key)));
		
		return binaryInt.toString();
	}
	
	public String decrypt(String msg) {
		
		BigInteger cryMsg = new BigInteger(msg);
		
		cryMsg = cryMsg.divide(new BigInteger(Integer.toString(key)));
		
		return new String(new BigInteger(cryMsg.toString(), 2).toByteArray());
	}
	
	public String bitToString(String msg) {
		return new String(new BigInteger(msg, 2).toByteArray());
	}
	
}
