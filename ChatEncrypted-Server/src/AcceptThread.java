import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AcceptThread extends Thread {
	public static int[] openPorts;
	public static List<Member> members;

	public AcceptThread() {
		openPorts = new int[10000];
		for (int i : openPorts) {
			i = 0;
		}
		members = new ArrayList<Member>();

	}

	@Override
	public void run() {
		ServerSocket serSocket = null;
		System.out.println("ClientAcceptThread started");
		while (true) {
			try {
				serSocket = new ServerSocket(10000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Socket client = null;
			try {
				client = serSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter out = null;
			BufferedReader in = null;
			try {
				out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < openPorts.length; i++) {
				if (openPorts[i] == 0) {

					String info = null;
					try {
						info = in.readLine();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String parts[] = info.split(";");
					try {

						StringBuilder build = new StringBuilder();
						boolean noName = false;
						for (ClientThread thrd : Main.clients) {
							if (parts[0].equals(thrd.member.getName())) {
								noName = true;
							}
						}
						if (noName) {
							out.println("" + (0));
							out.flush();
							break;
						} else {
							out.println("" + (10001 + i));
							out.flush();
							members.add(new Member(parts[0], parts[1], client.getInetAddress().toString(), 10001 + i));
							Main.clients.add(new ClientThread(
									new Member(parts[0], parts[1], client.getInetAddress().toString(), 10001 + i)));
							Main.clients.get(Main.clients.size() - 1).start();
							System.out.println("Client connected");
							System.out.println("Name: " + parts[0] + " PublicKey: " + parts[1] + " IP: "
									+ client.getInetAddress().toString() + " Port: " + 10001 + i);
							openPorts[i] = 1;
							break;
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			try {
				client.close();
				serSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
