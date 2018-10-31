import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClientThread extends Thread {
	public Member member;
	public List<String> msgs;

	public ClientThread(Member member) {
		this.member = member;
		msgs = new ArrayList<String>();
	}

	public void write(String msg) {
		msgs.add(msg);
	}

	public void run() {
		System.out.println("ClientThread started on Port " + member.getPort());
		ServerSocket sock = null;
		try {
			sock = new ServerSocket(member.getPort());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket client = null;
		try {
			client = sock.accept();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client connected on Port " + member.getPort());
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (true) {
				String msg = null;
				if (in.ready() || out.checkError()) {
					if ((msg = in.readLine()) == null) {
						break;
					}
					String parts[] = msg.split(";");
					for (int i = 0; i < Main.clients.size(); i++) {
						String name = Main.clients.get(i).member.getName();
						try {
							if (name.equals(parts[0])) {
								StringBuilder build = new StringBuilder();
								build.append(parts[1]);
								for (int j = 2; j < parts.length; j++) {
									build.append(";" + parts[j]);
								}
								Main.clients.get(i).write(member.getName() + ";" + build.toString());
								System.out.println(member.getName() + ": " + build.toString());

							}
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Invalid Message from " + member.getName());
						}

					}

				}
				for (String string : msgs) {
					out.println(string);
					out.flush();
					System.out.println("OUT");
				}
				msgs = new ArrayList<String>();
				StringBuilder build = new StringBuilder();
				for (ClientThread thrd : Main.clients) {
					build.append(";" + thrd.member.getName()+"!"+thrd.member.getPublicKey());
				}
				out.println(build.toString());
				out.flush();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AcceptThread.openPorts[member.getPort() - 10001] = 0;
		try {
			client.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < Main.clients.size(); i++) {
			if (Main.clients.get(i).member.getName().equals(member.getName())) {
				Main.clients.remove(i);
				break;
			}
		}
		System.out.println("Port " + member.getPort() + " is free");

	}

}
