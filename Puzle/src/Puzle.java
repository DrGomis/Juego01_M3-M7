import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Label;
import javax.swing.SwingConstants;


public class Puzle {

	private JFrame frame;
	private JButton btn_0_0, btn_0_1, btn_0_2, btn_1_0, btn_1_1, btn_1_2, btn_2_0, btn_2_1, btn_2_2;
	private JButton btnArriba, btnAbajo, btnIzquierda, btnDerecha;
	
	private JLabel label_contador;
	private int puzle[][];
	
	private String nota;
	
	private int movimientos, fallos, movimiento, ceroX, ceroY;
	private JLabel bgCielo;
	private JLabel bgNube;
	private JLabel barra_lateral_izq;
	private JLabel barra_superior_izq;
	private JLabel barra_superior_der;
	private JLabel barra_lateral_der;
	private JLabel barra_inferior_der;
	private JLabel suelo;
	private JMenu mnInfo;
	private JMenu mnRango;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Puzle window = new Puzle();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Puzle() {
		initialize();
	}
	

	/**
	 * Método para mostrar el resultado de los botones	
	 */
	
	public void Reiniciar(){
		//PREPARAR TABLERO
		// Desordena el tablero
		Random genAleatorio = new Random();
		int numAleatorio = genAleatorio.nextInt(9);
		int desorden[] = new int[9]; // Array de números aleatorios

		// Números aleatorios que no se repiten
		for (int i = 0; i < desorden.length; i++) {	
			numAleatorio = genAleatorio.nextInt(9);
			desorden[i] = numAleatorio;       // Primera vez: desorden[0] = Aleatorio		
			for (int j = 0; j < i; j++) { 
				if (desorden[i] == desorden[j]) { // Primera vez: (desorden[1] == desorden[0]) -> Motivo: j < i
					i--; // Si coinciden, se reinicia el valor de 'i' hasta que no coincida
					break;
				}
			} 
		}
		// Asignar números aleatorios al tablero
		puzle = new int[3][3];
		
		puzle[0][0] = desorden[0];
		puzle[0][1] = desorden[1];
		puzle[0][2] = desorden[2];	
		puzle[1][0] = desorden[3];
		puzle[1][1] = desorden[4];
		puzle[1][2] = desorden[5];	
		puzle[2][0] = desorden[6];
		puzle[2][1] = desorden[7];
		puzle[2][2] = desorden[8];	
	}
	
	public void Rank() {
		// Aquí añadimos un rango al jugador dependiendo de su desempeño
		if (movimientos <= 31 && fallos == 0) {
			nota = "S+";
		}
		else if (movimientos + fallos <= 35) {
			nota = "S";
		}
		else if (movimientos + fallos >= 36 && movimientos + fallos <= 45) {
			nota = "A";
		}
		else if (movimientos + fallos >= 46 && movimientos + fallos <= 52) {
			nota = "B";
		}
		else if (movimientos + fallos >= 53 && movimientos + fallos <= 60) {
			nota = "C";
		}
		else if (movimientos + fallos >= 61) {
			nota = "D";
		}
		
		
		
	}
	
	
	public void printButtons() {
		// BOTONES JUEGO
		btn_0_0.setIcon(new ImageIcon(Puzle.class.getResource("/img/" + Integer.toString(puzle[0][0]) + ".png")));
		btn_0_1.setIcon(new ImageIcon(Puzle.class.getResource("/img/" + Integer.toString(puzle[0][1]) + ".png")));
		btn_0_2.setIcon(new ImageIcon(Puzle.class.getResource("/img/" + Integer.toString(puzle[0][2]) + ".png")));
		
		btn_1_0.setIcon(new ImageIcon(Puzle.class.getResource("/img/" + Integer.toString(puzle[1][0]) + ".png")));
		btn_1_1.setIcon(new ImageIcon(Puzle.class.getResource("/img/" + Integer.toString(puzle[1][1]) + ".png")));
		btn_1_2.setIcon(new ImageIcon(Puzle.class.getResource("/img/" + Integer.toString(puzle[1][2]) + ".png")));
		
		btn_2_0.setIcon(new ImageIcon(Puzle.class.getResource("/img/" + Integer.toString(puzle[2][0]) + ".png")));
		btn_2_1.setIcon(new ImageIcon(Puzle.class.getResource("/img/" + Integer.toString(puzle[2][1]) + ".png")));
		btn_2_2.setIcon(new ImageIcon(Puzle.class.getResource("/img/" + Integer.toString(puzle[2][2]) + ".png")));
	}
	
	public void verificarFinal() {
		// VERIFICAMOS SI EL PUZLE ESTÁ RESUELTO
		if (1 == puzle[0][0] && 2 == puzle[0][1] && 3 == puzle[0][2] && 4 == puzle[1][0] && 5 == puzle[1][1] && 6 == puzle[1][2] && 7 == puzle[2][0] && 8 == puzle[2][1] && 0 == puzle[2][2]) {
			// Desactiva los botones en caso de estar resuelto
			btnArriba.setEnabled(false);
			btnAbajo.setEnabled(false);
			btnDerecha.setEnabled(false);
			btnIzquierda.setEnabled(false);
			// Muestra un texto de felicitación
			JOptionPane.showMessageDialog(frame, "<html><font size=19><span style=\"color:#ff0000;\">¡</span><span style=\"color:#ff4000;\">E</span><span style=\"color:#ff7f00;\">N</span><span style=\"color:#ffaa00;\">H</span><span style=\"color:#ffd400;\">O</span><span style=\"color:#ffff00;\">R</span><span style=\"color:#80ff00;\">A</span><span style=\"color:#00ff00;\">B</span><span style=\"color:#00ff55;\">U</span><span style=\"color:#00ffaa;\">E</span><span style=\"color:#00ffff;\">N</span><span style=\"color:#0080ff;\">A</span><span style=\"color:#0000ff;\">!</span></font>\n"
					+ "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¡Has ayudado a <font color=#0485B4>Sadie Salamandra</font> con éxito!\n\n"
					+ "<html><u>Estadísticas</u>:\n"
					+ "- Movimientos totales: " + movimientos + "\n"
							+ "- Errores cometidos: " + fallos + "\n"
									+ "- Calificación:  " + nota, "¡Puzle resuelto!", JOptionPane.INFORMATION_MESSAGE);
			
		
			
			
		}
	}
	
	public void actionW() {
		if (0 == puzle[0][0] || 0 == puzle[0][1] || 0 == puzle[0][2] || 0 == puzle[1][0] || 0 == puzle[1][1] || 0 == puzle[1][2]) {
			// Encuentra 0
			for(int i = 0; i < puzle.length; i++){
				for(int j = 0; j < puzle[i].length; j++){
					if(puzle[i][j] == 0){
						ceroX = i;
						ceroY = j;
					}
				}	
			}
			// Reemplaza 0 por el valor de abajo
			movimiento = puzle[ceroX][ceroY];
			puzle[ceroX][ceroY] = puzle[ceroX + 1][ceroY];
			puzle[ceroX + 1][ceroY] = movimiento;
			movimientos++;
			
		}
		else {
			// DIRECCIÓN INCORRECTA
			fallos++;
		}	
		// Actualiza el puzle tras la acción
		printButtons();
		// Intento de limitar el contador
		if (movimientos > 99) {
			label_contador.setText("+99");
		}
		else if (movimientos <= 9) {
			label_contador.setText("  " + Integer.toString(movimientos));
		}
		else {
			label_contador.setText(" " + Integer.toString(movimientos));
		}
		// Asigna tu rango actual
		Rank();
		// Verifica si el puzle está completado
		verificarFinal();
	}
	
	public void actionS() {
		if (0 == puzle[1][0] || 0 == puzle[1][1] || 0 == puzle[1][2] || 0 == puzle[2][0] || 0 == puzle[2][1] || 0 == puzle[2][2]) {
			for(int i = 0; i < puzle.length; i++){
				for(int j = 0; j < puzle[i].length; j++){
					if(puzle[i][j] == 0){
						ceroX = i;
						ceroY = j;
					}
				}	
			}
			// Reemplaza 0 por el valor de arriba
			movimiento = puzle[ceroX][ceroY];
			puzle[ceroX][ceroY] = puzle[ceroX - 1][ceroY];
			puzle[ceroX - 1][ceroY] = movimiento;
			movimientos++;	
		}
		else {
			// DIRECCIÓN INCORRECTA
			fallos++;
		}
		// Actualiza el puzle tras la acción
		printButtons();
		if (movimientos > 99) {
			label_contador.setText("+99");
		}
		else if (movimientos <= 9) {
			label_contador.setText("  " + Integer.toString(movimientos));
		}
		else {
			label_contador.setText(" " + Integer.toString(movimientos));
		}
		// Asigna tu rango actual
		Rank();
		// Verifica si el puzle está completado
		verificarFinal();

		
	}

	public void actionA() {	
		if (0 == puzle[0][0] || 0 == puzle[1][0] || 0 == puzle[0][1] || 0 == puzle[1][1] || 0 == puzle[2][0] || 0 == puzle[2][1]) {
			for(int i = 0; i < puzle.length; i++){
				for(int j = 0; j < puzle[i].length; j++){
					if(puzle[i][j] == 0){
						ceroX = i;
						ceroY = j;
					}
				}	
			}
			// Reemplaza 0 por el valor de la derecha
			movimiento = puzle[ceroX][ceroY];
			puzle[ceroX][ceroY] = puzle[ceroX][ceroY + 1];
			puzle[ceroX][ceroY + 1] = movimiento;
			movimientos++;														
		}
		else {
			// DIRECCIÓN INCORRECTA
			fallos++;
		}
		// Actualiza el puzle tras la acción
		printButtons();
		if (movimientos > 99) {
			label_contador.setText("+99");
		}
		else if (movimientos <= 9) {
			label_contador.setText("  " + Integer.toString(movimientos));
		}
		else {
			label_contador.setText(" " + Integer.toString(movimientos));
		}
		// Asigna tu rango actual
		Rank();
		// Verifica si el puzle está completado
		verificarFinal();

	}
	
	public void actionD() {	
		if (0 == puzle[0][1] || 0 == puzle[1][1] || 0 == puzle[2][1] || 0 == puzle[0][2] || 0 == puzle[1][2] || 0 == puzle[2][2]) {
			for(int i = 0; i < puzle.length; i++){
				for(int j = 0; j < puzle[i].length; j++){
					if(puzle[i][j] == 0){
						ceroX = i;
						ceroY = j;
					}
				}	
			}
			// Reemplaza 0 por el valor de la izquierda
			movimiento = puzle[ceroX][ceroY];
			puzle[ceroX][ceroY] = puzle[ceroX][ceroY - 1];
			puzle[ceroX][ceroY - 1] = movimiento;
			movimientos++;
		}
		else {
			
			// DIRECCIÓN INCORRECTA
			fallos++;
		}	
		// Actualiza el puzle tras la acción
		printButtons();
		if (movimientos > 99) {
			label_contador.setText("+99");
		}
		else if (movimientos <= 9) {
			label_contador.setText("  " + Integer.toString(movimientos));
		}
		else {
			label_contador.setText(" " + Integer.toString(movimientos));
		}
		// Asigna tu rango actual
		Rank();
		// Verifica si el puzle está completado
		verificarFinal();
	
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(SystemColor.desktop);
		frame.setBounds(100, 100, 450, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		JMenuBar barraMenu = new JMenuBar();
		frame.setJMenuBar(barraMenu);
		
		mnInfo = new JMenu("Información");
		mnInfo.setIcon(new ImageIcon(Puzle.class.getResource("/img/info.png")));
		mnInfo.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				
				JOptionPane.showMessageDialog(frame, "<html>A <font color=#0485B4>Sadie Salamandra</font> le han desordenado la colección de piedras.\n"
						+ "<html>Las piedras <u>son muy pesadas</u>, así que sólo puedes moverlas de una en una.\n"
						+ "<html>Ayuda a <font color=#0485B4>Sadie Salamandra</font> a ordenarlas de la siguiente manera:\n\n"
						+ "                                                     1 - 2 - 3\n"
						+ "                                                     4 - 5 - 6\n"
						+ "                                                     7 - 8 - x\n\n"
						+ "<html>Pulsa el <font color=#D81D11>BOTÓN ROJO</font> para desordenar la colección de <font color=#0485B4>Sadie</font> de nuevo.", "Información", JOptionPane.INFORMATION_MESSAGE);

				
				
				
				
			}
		});
		barraMenu.add(mnInfo);
		
		JMenu mnResolver = new JMenu("Hacer trampas");
		mnResolver.setIcon(new ImageIcon(Puzle.class.getResource("/img/satan.png")));
		mnResolver.addMouseListener(new MouseAdapter() {
	
			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "<html>MODO COBARDE: <font color=#B21111>[ACTIVADO]</font>", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);	
				//Puzle semi-resuelto
				puzle[0][0] = 1;
				puzle[0][1] = 2;
				puzle[0][2] = 3;	
				puzle[1][0] = 4;
				puzle[1][1] = 5;
				puzle[1][2] = 6;	
				puzle[2][0] = 7;
				puzle[2][1] = 0;
				puzle[2][2] = 8;
				// Mostramos el puzle actualizado
				printButtons();
				
				// Contador de fallos para calcular el Rango
				//fallo = 999;
				
				// Mostramos el contador actualizado
				movimientos = 998;
				if (movimientos > 99) {
					label_contador.setText("+99");
				}
				else if (movimientos <= 9) {
					label_contador.setText("  " + Integer.toString(movimientos));
				}
				else {
					label_contador.setText(" " + Integer.toString(movimientos));
				}
				
				
			}
		});
		
		mnRango = new JMenu("Calificaciones");
		mnRango.setIcon(new ImageIcon(Puzle.class.getResource("/img/estrella.png")));
		mnRango.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "<html><font color=#0485B4>Sadie Salamandra</font> evaluará tu desempeño mediante las siguientes <u>notas</u>:\n"
						+ "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Leyenda: <font color=#8a2bce>S+</font>\n"
						+ "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Increíble: <font color=#4b27ce>S</font>\n"
						+ "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Perfecto: <font color=#243fce>A</font>\n"
						+ "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Not bad: <font color=#1dbcce>B</font>\n"
						+ "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Meh: <font color=#15ce12>C</font>\n"
						+ "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Mediocre: <font color=#ce1b00>D</font>\n", "Rangos", JOptionPane.INFORMATION_MESSAGE);
				
				
			}
		});
		barraMenu.add(mnRango);
		barraMenu.add(mnResolver);
		
		JMenu mnSalir = new JMenu("<html><u>S</u>alir</html>");
		mnSalir.setIcon(new ImageIcon(Puzle.class.getResource("/img/salir.png")));
		mnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);	
			}
		});
		barraMenu.add(mnSalir);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setLayout(null);
		
		
		
		////// LABEL CONTADOR MOVIMIENTOS REALIZADOS
		// 298, 45
		label_contador = new JLabel("");
		label_contador.setFont(new Font(" ", Font.PLAIN, 16));
		label_contador.setForeground(Color.BLACK);
		label_contador.setBounds(83, 16, 85, 40);
		frame.getContentPane().add(label_contador);
		// Con esta línea sale '  ' en el contador al inicio
		label_contador.setText("  " + Integer.toString(movimientos));
		

		///// DINOCOUNT
		JLabel DinoCount = new JLabel("");
		DinoCount.setIcon(new ImageIcon(Puzle.class.getResource("/img/dinocount.png")));
		DinoCount.setBounds(42, -45, 294, 251);
		frame.getContentPane().add(DinoCount);
		
		
		// FILA 1
		btn_0_0 = new JButton("");
		btn_0_0.setOpaque(false);
		btn_0_0.setContentAreaFilled(false);
		btn_0_0.setBorderPainted(false);
		btn_0_0.setBounds(139, 73, 45, 45);
		frame.getContentPane().add(btn_0_0);
		
		btn_0_1 = new JButton("");
		btn_0_1.setOpaque(false);
		btn_0_1.setContentAreaFilled(false);
		btn_0_1.setBorderPainted(false);
		btn_0_1.setBounds(194, 73, 45, 45);
		frame.getContentPane().add(btn_0_1);
		
		btn_0_2 = new JButton("");
		btn_0_2.setOpaque(false);
		btn_0_2.setContentAreaFilled(false);
		btn_0_2.setBorderPainted(false);
		btn_0_2.setBounds(249, 73, 45, 45);
		frame.getContentPane().add(btn_0_2);
		
		// FILA 2
		btn_1_0 = new JButton("");
		btn_1_0.setOpaque(false);
		btn_1_0.setContentAreaFilled(false);
		btn_1_0.setBorderPainted(false);
		btn_1_0.setBounds(139, 128, 45, 45);
		frame.getContentPane().add(btn_1_0);
		
		btn_1_1 = new JButton("");
		btn_1_1.setOpaque(false);
		btn_1_1.setContentAreaFilled(false);
		btn_1_1.setBorderPainted(false);
		btn_1_1.setBounds(194, 128, 45, 45);
		frame.getContentPane().add(btn_1_1);
		
		btn_1_2 = new JButton("");
		btn_1_2.setOpaque(false);
		btn_1_2.setContentAreaFilled(false);
		btn_1_2.setBorderPainted(false);
		btn_1_2.setBounds(249, 128, 45, 45);
		frame.getContentPane().add(btn_1_2);
			
		
		
		///// VALLAS DE MADERA	
		barra_superior_der = new JLabel("");
		barra_superior_der.setIcon(new ImageIcon(Puzle.class.getResource("/img/fence_superior_derecha.png")));
		barra_superior_der.setBounds(159, 29, 310, 125);
		frame.getContentPane().add(barra_superior_der);
		
		barra_superior_izq = new JLabel("");
		barra_superior_izq.setIcon(new ImageIcon(Puzle.class.getResource("/img/fence_superior_izquierda.png")));
		barra_superior_izq.setBounds(95, -5, 323, 192);
		frame.getContentPane().add(barra_superior_izq);
		
		barra_inferior_der = new JLabel("");
		barra_inferior_der.setIcon(new ImageIcon(Puzle.class.getResource("/img/fence_inferior_derecha.png")));
		barra_inferior_der.setBounds(116, 189, 267, 100);
		frame.getContentPane().add(barra_inferior_der);
		
		JLabel barra_inferior_izq = new JLabel("");
		barra_inferior_izq.setIcon(new ImageIcon(Puzle.class.getResource("/img/fence_inferior_izquierda.png")));
		barra_inferior_izq.setBounds(-33, 186, 293, 107);
		frame.getContentPane().add(barra_inferior_izq);
				
		
		
		
		// FILA 3
		btn_2_0 = new JButton("");
		btn_2_0.setOpaque(false);
		btn_2_0.setContentAreaFilled(false);
		btn_2_0.setBorderPainted(false);
		btn_2_0.setBounds(139, 183, 45, 45);
		frame.getContentPane().add(btn_2_0);
		
		btn_2_1 = new JButton("");
		btn_2_1.setOpaque(false);
		btn_2_1.setContentAreaFilled(false);
		btn_2_1.setBorderPainted(false);
		btn_2_1.setBounds(194, 183, 45, 45);
		frame.getContentPane().add(btn_2_1);
		
		btn_2_2 = new JButton("");
		btn_2_2.setOpaque(false);
		btn_2_2.setContentAreaFilled(false);
		btn_2_2.setBorderPainted(false);
		btn_2_2.setBounds(249, 183, 45, 45);
		frame.getContentPane().add(btn_2_2);
		
		
		// SE REINICIA POR PRIMERA VEZ EL JUEGO
		Reiniciar();
		
	
		// SE MUESTRAN LAS PIEZAS DEL PUZLE
		printButtons();
		
		
		// LATERALES Y SUELO DE LA VALLA
		barra_lateral_der = new JLabel("");
		barra_lateral_der.setIcon(new ImageIcon(Puzle.class.getResource("/img/fence_lateral_derecho.png")));
		barra_lateral_der.setBounds(328, 79, 45, 185);
		frame.getContentPane().add(barra_lateral_der);
		
		barra_lateral_izq = new JLabel("");
		barra_lateral_izq.setIcon(new ImageIcon(Puzle.class.getResource("/img/fence_lateral_izquierda.png")));
		barra_lateral_izq.setBounds(96, 16, 119, 263);
		frame.getContentPane().add(barra_lateral_izq);
		
		suelo = new JLabel("");
		suelo.setIcon(new ImageIcon(Puzle.class.getResource("/img/suelo.png")));
		suelo.setBounds(105, 98, 227, 155);
		frame.getContentPane().add(suelo);
		
		// BOTONES DE MOVIMIENTO
		btnArriba = new JButton("");
		btnArriba.setIcon(new ImageIcon(Puzle.class.getResource("/img/boton_up.png")));
		btnArriba.setContentAreaFilled(false);
		btnArriba.setBorderPainted(false);
		btnArriba.setOpaque(false);
		btnArriba.setRolloverIcon(new ImageIcon(Puzle.class.getResource("/img/boton_up_over.png")));
		btnArriba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionW();
			}
		});
		btnArriba.setBounds(199, 255, 50, 48);
		frame.getContentPane().add(btnArriba);
		
		btnAbajo = new JButton("");
		btnAbajo.setIcon(new ImageIcon(Puzle.class.getResource("/img/boton_down.png")));
		btnAbajo.setContentAreaFilled(false);
		btnAbajo.setOpaque(false);
		btnAbajo.setBorderPainted(false);
		btnAbajo.setRolloverIcon(new ImageIcon(Puzle.class.getResource("/img/boton_down_over.png")));
		btnAbajo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionS();
			}
		});
		btnAbajo.setBounds(199, 320, 50, 48);
		frame.getContentPane().add(btnAbajo);
		
		btnIzquierda = new JButton("");
		btnIzquierda.setIcon(new ImageIcon(Puzle.class.getResource("/img/boton_left.png")));
		btnIzquierda.setOpaque(false);
		btnIzquierda.setContentAreaFilled(false);
		btnIzquierda.setBorderPainted(false);
		btnIzquierda.setRolloverIcon(new ImageIcon(Puzle.class.getResource("/img/boton_left_over.png")));
		btnIzquierda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionA();
			}
		});
		btnIzquierda.setBounds(140, 287, 50, 48);
		frame.getContentPane().add(btnIzquierda);
		
		// 	BOTÓN REINICIAR
		
		
		
		JButton btn_reset = new JButton("");
		btn_reset.setIcon(new ImageIcon(Puzle.class.getResource("/img/boton_reset.png")));
		btn_reset.setPressedIcon(new ImageIcon(Puzle.class.getResource("/img/boton_reset_pulsado.png")));
		btn_reset.setBorderPainted(false);
		btn_reset.setOpaque(false);
		btn_reset.setBorder(null);
		btn_reset.setContentAreaFilled(false);
		btn_reset.setForeground(new Color(0, 0, 0));
		btn_reset.setBackground(new Color(255, 255, 255));
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				fallos = 0;
				movimientos = 0;
				Reiniciar();
				printButtons();
				label_contador.setText("  0");
		
				btnArriba.setEnabled(true);
				btnAbajo.setEnabled(true);
				btnIzquierda.setEnabled(true);
				btnDerecha.setEnabled(true);

				
				
			}
		});
		btn_reset.setBounds(347, 130, 72, 61);
		frame.getContentPane().add(btn_reset);
		
		btnDerecha = new JButton("");
		btnDerecha.setIcon(new ImageIcon(Puzle.class.getResource("/img/boton_right.png")));
		btnDerecha.setOpaque(false);
		btnDerecha.setContentAreaFilled(false);
		btnDerecha.setBorderPainted(false);
		btnDerecha.setRolloverIcon(new ImageIcon(Puzle.class.getResource("/img/boton_right_over.png")));
		btnDerecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionD();
			}
		});
		btnDerecha.setBounds(260, 287, 50, 48);
		frame.getContentPane().add(btnDerecha);		

		
		
		///// FONDOS
		// 266, -31, 184, 85
		bgNube = new JLabel("");
		bgNube.setIcon(new ImageIcon(Puzle.class.getResource("/img/nube_bt_reset.png")));
		bgNube.setBounds(266, -25, 184, 85);
		frame.getContentPane().add(bgNube);
		
		// 0, 55, 450, 429
		JLabel bgCesped = new JLabel("");
		bgCesped.setIcon(new ImageIcon(Puzle.class.getResource("/img/bg_transparente.png")));
		bgCesped.setBounds(0, 55, 450, 429);
		frame.getContentPane().add(bgCesped);
		
		// -11, -37, 483, 183
		bgCielo = new JLabel("");
		bgCielo.setIcon(new ImageIcon(Puzle.class.getResource("/img/bg_cielo_despejado.png")));
		bgCielo.setBounds(-11, -37, 483, 183);
		frame.getContentPane().add(bgCielo);
		
	

	}
	


	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
