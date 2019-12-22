import java.io.*;
import java.util.Random;

public class Puzle {
	static final int max = 3;
	public static void main (String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		// Gestión del puzle
		boolean resuelto = false;
		String direccion = "";
		int ceroY = 0;
		int ceroX = 0;
		int movimiento;
		int contador = 0;
		
		// INTENTO DE DESORDENAR ALEATORIAMENTE EL PUZLE
		Random genAleatorio = new Random();
		int numAleatorio = genAleatorio.nextInt(9);
		int desorden[] = new int[9]; // Array de números aleatorios

		// Generador de números aleatorios (del 0 al 8) que no se repiten 
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
		
		// ASIGNACIÓN (chapucera) DE NÚMEROS ALEATORIOS AL PUZLE
		int puzle[][] = new int[3][3];
		
		puzle[0][0] = desorden[0];
		puzle[0][1] = desorden[1];
		puzle[0][2] = desorden[2];	
		puzle[1][0] = desorden[3];
		puzle[1][1] = desorden[4];
		puzle[1][2] = desorden[5];	
		puzle[2][0] = desorden[6];
		puzle[2][1] = desorden[7];
		puzle[2][2] = desorden[8];
		
		/* Puzle resuelto (pruebas)
		puzle[0][0] = 1;
		puzle[0][1] = 2;
		puzle[0][2] = 3;	
		puzle[1][0] = 4;
		puzle[1][1] = 5;
		puzle[1][2] = 6;	
		puzle[2][0] = 7;
		puzle[2][1] = 8;
		puzle[2][2] = 0;
		*/ 	
		
		
			
		// INICIO DEL ALGORITMO DEL PUZLE
		while (!resuelto) {
			// Contador de movimientos realizados
			System.out.println("\n     Movimientos realizados: " + contador + "\n\n");
			// Muestra el estado actual del puzle
			for (int i = 0; i < puzle.length; i++) { // Filas
				for (int j = 0; j < puzle[i].length; j++) {
					if (puzle[i][j] == 0) { // Genera el espacio vacío
						System.out.print("\t ");
					}
					else {
						System.out.print("\t" + puzle[i][j]);
					}
				}
				System.out.println("\n\n");
			}
			// Menú	
			System.out.print("\n-> Escoge una dirección\n[W] Arriba\n[S] Abajo\n[A] Izquierda\n[D] Derecha\nDirección: ");
			direccion = reader.readLine();
			// Verifica que la dirección indicada existe
			while (!"W".equalsIgnoreCase(direccion) && !"S".equalsIgnoreCase(direccion) && !"A".equalsIgnoreCase(direccion) && !"D".equalsIgnoreCase(direccion)) {
				System.out.print("-> ERROR: Dirección inexistente o errónea, introdúcela de nuevo por favor\n[W] Arriba\n[S] Abajo\n[A] Izquierda\n[D] Derecha\nDirección: ");
				direccion = reader.readLine();
			}
			// VERIFICA SI SE HA RESUELTO
			if (1 == puzle[0][0] && 2 == puzle[0][1] && 3 == puzle[0][2] && 4 == puzle[1][0] && 5 == puzle[1][1] && 6 == puzle[1][2] && 7 == puzle[2][0] && 8 == puzle[2][1] && 0 == puzle[2][2]) {
				System.out.print("¡¡Puzle resuelto!!");
				resuelto = true;
			} 
			
			// MOVIMIENTOS DEL PUZLE
			
			// Movimiento hacia arriba (De abajo a 0) Filas: 0 y 1 Enteras
			if ("W".equalsIgnoreCase(direccion)) {
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
					contador++;
					
				}
				else {
					System.out.println("***********************************");
					System.out.println("* ERROR: ¡Dirección incorrecta! *");
					System.out.println("***********************************");
				}	
			} 
			// Movimiento hacia abajo (De arriba a 0) Filas: 1 y 2 Enteras
			else if ("S".equalsIgnoreCase(direccion)) {
				// Encuentra 0
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
					contador++;	
				}
				else {
					System.out.println("***********************************");
					System.out.println("* ERROR: ¡Dirección incorrecta! *");
					System.out.println("***********************************");
				}	
			}
			
			// Movimiento hacia la izquierda (De la derecha del 0 al 0) Columnas: 0 y 1 Enteras
			else if ("A".equalsIgnoreCase(direccion)) {
				// Encuentra 0
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
					contador++;														
				}
				else {
					System.out.println("***********************************");
					System.out.println("* ERROR: ¡Dirección incorrecta! *");
					System.out.println("***********************************");
				}	
			}
			// Movimiento hacia la derecha (De la izquierda del 0 al 0) Columnas: 1 y 2 Enteras
			else if ("D".equalsIgnoreCase(direccion)) {
				// Encuentra 0
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
					contador++;
				}
				else {
					System.out.println("***********************************");
					System.out.println("* ERROR: ¡Dirección incorrecta! *");
					System.out.println("***********************************");
				}	
			}
			
			// Interior del while
			
			
		}
		
		
		
	}
}

