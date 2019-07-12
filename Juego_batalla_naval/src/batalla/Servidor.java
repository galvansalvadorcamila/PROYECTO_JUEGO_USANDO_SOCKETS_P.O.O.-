package batalla;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Servidor extends JFrame implements Runnable {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servidor frame = new Servidor();
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
	public Servidor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea.setBounds(55, 13, 315, 227);
		contentPane.add(textArea);
		
		/**Creacion de un thread para que el socket reciba texto en el JTextArea,
		permanecer a la escucha y tener el puerto abierto**/ 
		Thread mi_hilo=new Thread(this);
		mi_hilo.start();
	}
	
	private JTextArea textArea=new JTextArea();

	@Override
	public void run() {
		try {
			@SuppressWarnings("resource")
			ServerSocket servidor =new ServerSocket(6666);
			while(true){
			Socket mi_socket=servidor.accept();
			DataInputStream flujo_entrada=new DataInputStream(mi_socket.getInputStream());
			String mensaje_entrada=flujo_entrada.readUTF();
			textArea.append("\n"+mensaje_entrada);
			mi_socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
		
}