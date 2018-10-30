import java.net.InetAddress;

public class Member {
private String name;
private String publicKey;
private String ip;
private int port;
public Member(String name, String publicKey, String ip, int port) {
	this.name = name;
	this.publicKey = publicKey;
	this.ip = ip;
	this.port = port;
}

public String getName() {
	return name;
}
public String getPublicKey() {
	return publicKey;
}
public String getIp() {
	return ip;
}
public int getPort() {
	return port;
}

}
