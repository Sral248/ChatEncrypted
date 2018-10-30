import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class EmpfangsThread extends Thread {
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	static boolean running;
	public static List<String> msgs;
	public static List<String> names;

	public EmpfangsThread(int port) {
		running = true;
		msgs = new ArrayList<String>();
		names = new ArrayList<String>();
		try {
			sock = new Socket("5.230.147.219", port);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void stopIT() {
		running = false;
	}

	public void run() {
		while (running) {

			try {
				if (in.ready()) {
					String msg = null;
					try {
						msg = in.readLine();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					String parts[] = msg.split(";");
					if (parts[0].equals("")) {
						names = new ArrayList<String>();
						for (String string : parts) {
							if (!ChatProgrammEncryptet.textField_1.getText().equals(string)) {
								names.add(string);
							}
							
						}
						try {
						boolean reload = true;
						for (String string : names) {
							if (ChatProgrammEncryptet.comboBox.getSelectedItem().equals(string)) {
								reload=false;
							}
						}
						if (reload) {
							ChatProgrammEncryptet.comboBox.removeAllItems();
							for (String string : EmpfangsThread.names) {
								ChatProgrammEncryptet.comboBox.addItem(string);
							}
						}
						}catch (Exception e) {
							// TODO: handle exception
						}
						
					} else {
						StringBuilder build = new StringBuilder();
						build.append(parts[1]);
						for (int j = 2; j < parts.length; j++) {
							build.append(";" + parts[j]);
						}
						String nachricht = build.toString();
						nachricht = nachricht.substring(0, nachricht.length()-1);
						ChatProgrammEncryptet.txtpnHallo.setText(
								ChatProgrammEncryptet.txtpnHallo.getText() + parts[0] + ": " + nachricht + "\n");
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (String string : msgs) {
				out.println(string+" ");
				out.flush();
				msgs = new ArrayList<String>();
			}

		}
		try {
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
