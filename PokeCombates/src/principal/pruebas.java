package principal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class pruebas {

	public static void main(String[] args) {
		
		try (BufferedReader br = new BufferedReader (new InputStreamReader(new FileInputStream("FicheroPokemon.txt")));) 
		{
			String linea;
			while ((linea = br.readLine()) != null) {
				System.out.println(linea);
				System.out.println("eenawedihawnodaw");
			}
			
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
