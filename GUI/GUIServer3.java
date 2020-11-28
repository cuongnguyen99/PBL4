package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.JButton;

public class GUIServer3 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIServer3 frame = new GUIServer3();
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
	public GUIServer3() {
		setTitle("Server 3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(SystemColor.inactiveCaption);
		textArea.setBounds(10, 72, 290, 186);
		contentPane.add(textArea);
		
		JButton btnReqToAll = new JButton("REQ to all");
		btnReqToAll.setBounds(10, 41, 140, 21);
		contentPane.add(btnReqToAll);
		
		JButton btnReqToServer_1 = new JButton("REQ to Server 1");
		btnReqToServer_1.setBounds(10, 10, 140, 21);
		contentPane.add(btnReqToServer_1);
		
		JButton btnReqToServer_2 = new JButton("REQ to Server 2");
		btnReqToServer_2.setBounds(160, 10, 140, 21);
		contentPane.add(btnReqToServer_2);
		
		JButton btnRel = new JButton("REL");
		btnRel.setBounds(160, 41, 140, 21);
		contentPane.add(btnRel);
	}

}
