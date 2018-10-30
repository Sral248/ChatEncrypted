import java.util.ArrayList;
import java.util.List;

public class Main {
public static List<ClientThread> clients;
	public static void main(String[] args) {
		clients = new ArrayList<ClientThread>();
		AcceptThread ac = new AcceptThread();
		ac.start();
		while(true) {
			
		}

	}

}
