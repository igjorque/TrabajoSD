package net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.io.OutputStreamWriter;

import dominio.Jugador;
import dominio.Movimiento;
import dominio.Pokemon;
import negocio.NegocioPokemon;
import presentacion.presConsola;

public class PokeClient {
	NegocioPokemon np;
	
	private String host;
	private int puerto;
	private int puertoFicheros;
	
	private Jugador jugador;
	private boolean finalizar;
	
	public PokeClient(String host, int puerto, int puertoFichero) {
		np = new NegocioPokemon();
		finalizar = false;
		this.host = host;
		this.puerto = puerto;
		this.puertoFicheros = puertoFichero;
	}

	public void ejecutar() {
		try(Socket sComb = new Socket(this.host, this.puerto);
				BufferedReader brComb = new BufferedReader (new InputStreamReader (sComb.getInputStream(), "UTF-8"));
				Writer w = new OutputStreamWriter(sComb.getOutputStream(), "UTF-8");
				ObjectOutputStream oosC = new ObjectOutputStream(sComb.getOutputStream());
			Socket sFich = new Socket(this.host, this.puertoFicheros);
				ObjectInputStream ois = new ObjectInputStream(sFich.getInputStream());) 
		{
			ArrayList<Pokemon> listaPoke = (ArrayList<Pokemon>) ois.readObject();
			ArrayList<Movimiento> listaMov = (ArrayList<Movimiento>) ois.readObject();
			
			
			jugador = presConsola.menuCreacionJugador(listaPoke, listaMov);
			
			//Avisa al servidor de que está preparado
			w.write("Listo\r\n");
			presConsola.mostrarEspera();
			
			if (brComb.readLine().equals("Empieza")) {
				int opcion;
				int mov;
				Pokemon pok;
				
				while (this.finalizar == false) {
					opcion = presConsola.menuAcciones();
					switch (opcion) {
						case 1: 
							mov = presConsola.menuMovimientos(jugador.getSeleccionado());
							oosC.writeObject(this.np.getServiciosEquipo().elegirAtaque(jugador, mov));
							oosC.writeObject(jugador.getSeleccionado());
							break;
						case 2:
							pok = presConsola.menuCambiarPokemon(jugador.getSeleccionado(), jugador.getEquipoPokemon());
							this.np.getServiciosEquipo().cambiarPokemon(jugador, pok);
							oosC.writeObject(pok);
							break;
						case 3:
							w.write("Rendirse");
							break;
					}
					
					while(!brComb.readLine().equals("MensajeFinalizado")) {
						
					}
					
					if (brComb.readLine().equals("Finaliza")) {
						
						this.finalizar = true;
						
						if (brComb.readLine().equals("Ganador")) {
							presConsola.ganar();
						}
						else {
							presConsola.perder();
						}
					}
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
