package persistencia;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dominio.*;

public class MovimientoPers {

private String movimientos = "\\FicheroMovimientos.txt";
	
	public List<Movimiento> getMovimientoList() {
		
		List<Movimiento> listaMov = new ArrayList<Movimiento>();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(movimientos)));){
			
			String linea;
			String [] lins; // LineaSplit
			Movimiento mov = null;
			
			while((linea = br.readLine()) != null) {
				lins = linea.split(";");
				
				mov = new Movimiento(lins[0], MetodosAuxiliares.stringToTipo(lins[1]), Integer.parseInt(lins[2]), Integer.parseInt(lins[3]), 
						Integer.parseInt(lins[4]), Integer.parseInt(lins[5]));
				
				listaMov.add(mov);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return listaMov;
	}
	
	public static Movimiento encontrarMovimiento(String s) {
		return null;
	}
}
