package batalla;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Cliente extends JFrame {

	private JPanel contentPane;
	private JTextField mensaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente();
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
	public Cliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.setBounds(161, 79, 97, 25);
		Enviar_texto evento=new Enviar_texto();
		btnNewButton.addActionListener(evento);
		contentPane.add(btnNewButton);
		
		mensaje = new JTextField();
		mensaje.setBounds(152, 44, 116, 22);
		contentPane.add(mensaje);
		mensaje.setColumns(10);
	}
	
	private class Enviar_texto implements ActionListener{ 

		private Socket mi_socket;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				mi_socket = new Socket("172.16.223.96",6666);
				
				DataOutputStream flujo_salida = new DataOutputStream(mi_socket.getOutputStream());
				
				flujo_salida.writeUTF(mensaje.getText());
				flujo_salida.close();
				
				
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				//nos lanza un mensaje si falla la conexion
				System.out.print(e1.getMessage());
			}
			System.out.println(mensaje.getText());
			
		}
		
	}
	
}