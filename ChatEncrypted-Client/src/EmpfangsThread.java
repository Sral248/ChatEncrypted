import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.ldap.Rdn;

public class EmpfangsThread extends Thread {
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	static boolean running;
	public static List<String> msgs;
	public static List<String> names;
	public static List<Integer> keys;

	public EmpfangsThread(int port) {
		running = true;
		msgs = new ArrayList<String>();
		names = new ArrayList<String>();
		keys = new ArrayList<Integer>();
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
							String partss[] = string.split("!");
							if (!ChatProgrammEncrypted.textField_1.getText().equals(partss[0])) {
								names.add(partss[0]);
								try {
								keys.add(Integer.parseInt(partss[1]));
								}
								catch (Exception e) {
									// TODO: handle exception
								}
							}

						}
						try {
							boolean reload = true;
							for (String string : names) {
								String partss[] = string.split("!");
								if (ChatProgrammEncrypted.comboBox.getSelectedItem().equals(partss[0])) {
									reload = false;
								}
							}
							if (reload) {
								keys = new ArrayList<Integer>();
								ChatProgrammEncrypted.comboBox.removeAllItems();
								for (String string : EmpfangsThread.names) {
									String partss[] = string.split("!");
									ChatProgrammEncrypted.comboBox.addItem(partss[0]);
									keys.add(Integer.parseInt(partss[1]));
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
						}

					} else {
						StringBuilder build = new StringBuilder();
						build.append(parts[1]);
						for (int j = 2; j < parts.length; j++) {
							build.append(";" + parts[j]);
						}
						String nachricht = build.toString();
						nachricht = nachricht.substring(0, nachricht.length() - 1);
						if (ChatProgrammEncrypted.bButton) {
							ChatProgrammEncrypted.verschlüsselung.createKey(EmpfangsThread.keys.get(ChatProgrammEncrypted.comboBox.getSelectedIndex()-1));
							try {
							ChatProgrammEncrypted.txtpnHallo.setText(
									ChatProgrammEncrypted.txtpnHallo.getText() + parts[0] + ": " + ChatProgrammEncrypted.verschlüsselung.decrypt(nachricht) + "\n");
							}
							catch(Exception e) {
								ChatProgrammEncrypted.txtpnHallo.setText(
										ChatProgrammEncrypted.txtpnHallo.getText() + parts[0] + ": " + nachricht + "\n");
							}
						}
						else {
							ChatProgrammEncrypted.txtpnHallo.setText(
									ChatProgrammEncrypted.txtpnHallo.getText() + parts[0] + ": " + nachricht + "\n");
						}
						
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (String string : msgs) {
				out.println(string + " ");
				out.flush();
				msgs = new ArrayList<String>();
			}
			try {
				if (ChatProgrammEncrypted.comboBox.getSelectedItem().equals("")) {
					ChatProgrammEncrypted.rdbtnEncrypt.setEnabled(false);
					ChatProgrammEncrypted.bButton = false;
					ChatProgrammEncrypted.rdbtnEncrypt.setSelected(false);
				} else {
					ChatProgrammEncrypted.rdbtnEncrypt.setEnabled(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
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
