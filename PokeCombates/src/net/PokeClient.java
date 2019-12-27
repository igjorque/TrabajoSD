package net;

import java.io.BufferedReader;
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
			
			//Avisa al servidor de que est� preparado
			w.write("Listo\r\n");
			presConsola.mostrarEspera();
			
			if (brComb.readLine().equals("Empieza")) {
				int opcion;
				int imov;
				
				Pokemon pok = null;
				Movimiento mov = null;
				boolean ren = false;
				
				while (this.finalizar == false) {
					opcion = presConsola.menuAcciones();
					switch (opcion) {
						case 1: 
							imov = presConsola.menuMovimientos(jugador.getSeleccionado());
							mov = jugador.getSeleccionado().getMovimientos().get(imov);
							break;
						case 2:
							pok = presConsola.menuCambiarPokemon(jugador.getSeleccionado(), jugador.getEquipoPokemon());
							break;
						case 3:
							ren = true;
							break;
					}
					
					protocoloCliente(w, mov, pok, ren);
					
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
	
	private static void protocoloCliente(Writer w, Movimiento m, Pokemon p, boolean r) {
		try {
			w.write("MensajeInicio\r\n");

			w.write("Movimiento\r\n");
			if (m != null) {
				w.write(m.getNombre() + "\r\\n");
			} else {
				w.write("nulo\r\n");
			}

			w.write("Cambiar\r\n");
			if (p != null) {
				w.write(p.getNombre() + "\r\n");
			} else {
				w.write("nulo\r\n");
			}

			w.write("Rendirse\r\n");
			if (r == true) {
				w.write("si\r\n");
			} else {
				w.write("no\r\n");
			}

			w.write("MensajeFin\r\n");
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
