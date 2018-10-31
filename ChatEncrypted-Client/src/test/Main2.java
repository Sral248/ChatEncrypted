package test;

import cryptologie.Key;

public class Main2 {

	public static void main(String[] args) {
		
		Key alice = new Key();
		Key bob = new Key();
		
		System.out.println("a public key: " + alice.getPublicKey());
		System.out.println("b public key: " + bob.getPublicKey());
		
		alice.createKey(bob.getPublicKey());
		bob.createKey(alice.getPublicKey());
		
		String msg = "Hallo wie geht es dir mein Freund?";
		System.out.println(msg);

		String cryptedMsg = alice.encrypt(msg);
		System.out.println(cryptedMsg);
		
		try {
			System.out.println(alice.bitToString(cryptedMsg));
		} catch (Exception e) {
			System.out.println("Can't convert this to a String");
		}
		
		
		String decryptedMsg = bob.decrypt(cryptedMsg);
		System.out.println(decryptedMsg);
	}

}
