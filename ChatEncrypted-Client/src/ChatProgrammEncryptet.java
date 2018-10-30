import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSpinner;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import java.awt.CardLayout;
import javax.swing.JSeparator;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;

public class ChatProgrammEncryptet {

	private JFrame frmChat;
	private JTextField textField;
	private JRadioButton rdbtnEncrypt;
	public static JComboBox comboBox;
	private JButton btnSend;
	private JButton btnConnect;
	private JScrollPane scrollPane;
	public static JTextPane txtpnHallo;
	public static JTextField textField_1;
	private JLabel lblName;
	private int port;
	private boolean connected;
	private Socket serverConnect;
	PrintWriter out;
	BufferedReader in;
	private JLabel labelZeile1;
	private JLabel labelZeile2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ChatProgrammEncryptet main = new ChatProgrammEncryptet();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatProgrammEncryptet window = new ChatProgrammEncryptet();
					window.frmChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatProgrammEncryptet() {
		out = null;
		in = null;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		connected = false;
		frmChat = new JFrame();
		frmChat.setTitle("Chat");
		frmChat.setResizable(false);
		frmChat.setSize(465, 287);
		frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChat.getContentPane().setBackground(Color.BLACK);
		frmChat.getContentPane().setLayout(null);

		rdbtnEncrypt = new JRadioButton("Encrypt");
		rdbtnEncrypt.setBounds(365, 7, 84, 23);
		frmChat.getContentPane().add(rdbtnEncrypt);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n' && !e.isShiftDown()) {
					send();
				}

			}
		});
		textField.setToolTipText("");
		textField.setBounds(10, 198, 349, 52);
		frmChat.getContentPane().add(textField);
		textField.setColumns(10);
		comboBox = new JComboBox();
		comboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				comboBox.removeAllItems();
				for (String string : EmpfangsThread.names) {
					comboBox.addItem(string);
				}
			}
		});
		comboBox.setBounds(365, 37, 84, 23);
		frmChat.getContentPane().add(comboBox);

		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send();
			}
		});
		btnSend.setBounds(365, 198, 84, 52);
		frmChat.getContentPane().add(btnSend);

		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!connected) {
					if (!textField_1.getText().equals("")) {
						try {
							Socket sock = new Socket("5.230.147.219", 10000);

							PrintWriter out1 = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
							BufferedReader in1 = new BufferedReader(new InputStreamReader(sock.getInputStream()));
							out1.println(textField_1.getText() + ";TESTKEY");
							out1.flush();
							System.out.println("test");
							port = Integer.parseInt(in1.readLine());
							System.out.println(port);
							
							if (port != 0) {
								connected = true;
								enabled(connected);
								sock.close();
								new EmpfangsThread(port).start();
								lblName.setText("Name: ");
								labelZeile1.setText("");
								labelZeile2.setText("");
								btnConnect.setText("Disconnect");
							}
							else {
								labelZeile1.setText("Der Name ist");
								labelZeile2.setText("bereits vergeben");
							}
							
							
						} catch (Exception e) {
							// TODO: handle exception
						}

					} else {
						lblName.setText("NAME: ");
						labelZeile1.setText("Bitte einen");
						labelZeile2.setText("Namen eingeben");
					}
				} else {
					port = 0;
					btnConnect.setText("Connect");
					EmpfangsThread.stopIT();
					connected = false;
					enabled(connected);
				}
			}
		});
		btnConnect.setBounds(365, 71, 84, 23);
		frmChat.getContentPane().add(btnConnect);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 7, 349, 180);
		frmChat.getContentPane().add(scrollPane);

		txtpnHallo = new JTextPane();
		txtpnHallo.setEditable(false);
		scrollPane.setViewportView(txtpnHallo);

		lblName = new JLabel("Name:");
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(365, 105, 46, 14);
		frmChat.getContentPane().add(lblName);

		textField_1 = new JTextField();
		textField_1.setBounds(365, 126, 84, 20);
		frmChat.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		labelZeile1 = new JLabel("");
		labelZeile1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		labelZeile1.setForeground(Color.WHITE);
		labelZeile1.setBounds(365, 157, 84, 14);
		frmChat.getContentPane().add(labelZeile1);
		
		labelZeile2 = new JLabel("");
		labelZeile2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		labelZeile2.setForeground(Color.WHITE);
		labelZeile2.setBounds(365, 170, 84, 14);
		frmChat.getContentPane().add(labelZeile2);

		enabled(false);
	}

	private void send() {
		
		if (!comboBox.getSelectedItem().equals("")) {
			System.out.println("Text send");
			txtpnHallo.setText(txtpnHallo.getText() + "Du: " + textField.getText() + "\n");
			EmpfangsThread.msgs.add(comboBox.getSelectedItem()+";"+textField.getText());
			textField.setText("");
		}
		
	}

	private void enabled(boolean a) {
		btnSend.setEnabled(a);
		comboBox.setEnabled(a);
		rdbtnEncrypt.setEnabled(a);
		textField.setEnabled(a);
		txtpnHallo.setEnabled(a);
		textField_1.setEnabled(!a);
	}
}