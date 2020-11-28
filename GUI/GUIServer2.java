package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class GUIServer2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIServer2 frame = new GUIServer2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIServer2() {
		setTitle("Server 2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("REQ to Server 1");
		btnNewButton.setBounds(10, 10, 140, 21);
		contentPane.add(btnNewButton);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(SystemColor.inactiveCaption);
		textArea.setBounds(10, 72, 290, 186);
		contentPane.add(textArea);
		
		JButton btnReqToServer = new JButton("REQ to Server 3");
		btnReqToServer.setBounds(160, 10, 140, 21);
		contentPane.add(btnReqToServer);
		
		JButton btnReqToAll = new JButton("REQ to all");
		btnReqToAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnReqToAll.setBounds(10, 41, 140, 21);
		contentPane.add(btnReqToAll);
		
		JButton btnRel = new JButton("REL");
		btnRel.setBounds(160, 41, 140, 21);
		contentPane.add(btnRel);
	}
}
