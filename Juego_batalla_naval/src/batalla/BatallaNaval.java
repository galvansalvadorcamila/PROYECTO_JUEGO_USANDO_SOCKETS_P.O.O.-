package batalla;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;

import javax.swing.ImageIcon;
    
@SuppressWarnings("serial")
public class BatallaNaval extends JFrame {
	
	public Image imagen_portada;
	public URL portada;
	public Image imagen_tablero;
	public URL tablero;
	int estado=0;
	@SuppressWarnings("unused")
	private JPanel contentPane;
	int tablero_jugador[][]=new int[8][8];
	int tablero_computador[][]=new int[8][8];
	
	int pFila=0;
	int pCol=0;
	int p_ncuadrados=5;
	int p_pocicion=0;
	int pHor;
	int n;
	int m;
	int pTam;
	
	public void iniciarPartida(){
		for (int i=0; i<8;i++){
			for (int j=0; j<8;j++){
				tablero_jugador[i][j]=0;
				tablero_computador[i][j]=0;
			}
		}
		for (int tam=1; tam<=5;tam++){
			ponerBarco(tablero_computador, tam);	
		}
		p_ncuadrados=5;
	}

	public void ponerBarco(int[][] tablero, int tam) { 
		int i,j,hor;
		
		do{
			i=(int)Math.random()*8;
			
			j=(int)Math.random()*8;
		
			hor=(int)Math.random()*2;
		
		}while(!cuadrado_vacio(tablero,tam,i,j,hor));
		int x=0,y=0;
		if(hor==1){
			x=1;
		}else{
			y=1;
		}
		if(x==1){
			for(int i2=i;i2<=i+tam;i2++){
					tablero[i2][y]=tam;
			}
		}else{
			for(int j2=j;j2<=j+tam;j2++){
					tablero[x][j2]=tam;
			}	
		}
	}

	private boolean cuadrado_vacio(int[][] tablero, int tam, int i, int j, int hor) {
		int x=0,y=0;
		if(hor==1){
			x=1;
		}else{
			y=1;
		}
		if(x==1){
			for(int i2=i;i2<=i+tam;i2++){
				if(!existe_enTablero(i2,j)){
					return false;
				}
			}
		}else{
			for(int j2=j;j2<=j+tam;j2++){
				if(!existe_enTablero(i,j2)){
					return false;
				}
			}
		}
		
		for(int i2=i-1;i2<=i+1+x*tam;i2++){
			for(int j2=j-1;j2<j+1+y*tam;j2++){
				if(existe_enTablero(i2,j2)& tablero[i2][j2]!=0){
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BatallaNaval frame = new BatallaNaval();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BatallaNaval() {
		
		this.setBounds(100, 100, 900, 600);
		this.setTitle("Batalla Naval");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		portada=this.getClass().getResource("/batalla/portada.png");
		imagen_portada=new ImageIcon(portada).getImage();
		tablero=this.getClass().getResource("/batalla/tablero.jpg");
		imagen_tablero=new ImageIcon(tablero).getImage();
		
		Container contenedor=getContentPane();
		contenedor.add(panel);
		panel.setLayout(null);
		
		addMouseListener(
			new MouseAdapter(){ 
				public void mouseClicked(MouseEvent e){
					/**Al hacer click a la pantalla, la imagen cambia de 
				    portada a tablero con un poco de retraso
				    **/
					if (estado==0){
						estado=1;
						iniciarPartida();
						repaint();
					}
					if (estado==1){
						int pDF=0;
						int pDC=0;
						if (pHor==1){
							pDF=1;
						}else{
							pDC=1;
						}
						for(int n=pFila;n<=pFila+pTam*pDF;n++){
							for (int m=pCol;m<=pCol+pTam*pDC;m++){
								tablero_jugador[n][m]=pTam;
							}
						}
					}
						
				}
			}
		);
		//el mouse no salga fuera del tablero
		addMouseMotionListener(
			new MouseMotionAdapter(){
				/**El tablero siente la presencia del mouse
				 * 
				 */
				@Override
				public void mouseMoved(MouseEvent e){
					int x=e.getX();
					int y=e.getY();
					if(x>=115 && y>=250 && x<115+30*8 && y<250+30*8){
						int f=(y-200)/30;
						int c=(x-100)/30;
						if(f!=pFila || c!=pCol){
							pFila=f;
							pCol=c;
							int pDF=0;
							int pDC=0;
							if (pHor==1){
								pDF=1;
							}else{
								pDC=1;
							}
							if(pFila+pTam*pDF>=8){
								pFila=8-pFila+pTam*pDC;
							}if(pCol+pTam*pDF>=8){
								pCol=8-pCol+pTam*pDC;
							}
							repaint();
						}
					}
				}
			}
		);		
	}
	
	public JPanel panel=new JPanel(){
		public void paint(Graphics g){
			
			if(estado==0){
			g.drawImage(imagen_portada, 0,0, getWidth(), getHeight(), this);
			}else{
				g.drawImage(imagen_tablero, 0,0, getWidth(), getHeight(), this);
				pintarTablero(g, tablero_jugador,115,250);
				pintarTablero(g, tablero_computador,525,250);
				
			}
		}
	};

	public void pintarTablero(Graphics g, int[][] tablero, int i, int j) {
		for (int n=0; n<8;n++){
			for (int m=0; m<8;m++){
				if (tablero[n][m]>0){
					g.setColor(Color.RED);
					g.fillRect(i+n*30, j+m*30, 30, 30);
				}
				g.setColor(Color.BLACK);
				g.drawRect(i+n*30, j+m*30, 30, 30);
				}
			
			if(estado==1){
				int pDF=0;
				int pDC=0;
				if (pHor==1){
					pDF=1;
				}else{
					pDC=1;
				}
				
				if (n>=pFila && m>=pCol &&n<=pFila + pTam*pDF && m<=pCol+pTam*pDC){
					g.setColor(Color.green);
					g.fillRect(i+n*30, j+m*30, 30, 30);
				}
			}
			}
	}
	
	public boolean existe_enTablero(int i,int j){
		if (i<0) return false;
		if (j<0) return false;
		if (i>=8) return false;
		if (j>=8) return false;
	return true;
	}
	
}