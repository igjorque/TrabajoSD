package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.Socket;
import java.io.OutputStreamWriter;

import dominio.Jugador;
import dominio.Pokemon;
import negocio.NegocioPokemon;
import presentacion.presConsola;

public class PokeClient {
	NegocioPokemon np;
	
	private String host;
	private int puerto;
	
	private Jugador jugador;
	private boolean finalizar;
	
	public PokeClient(String host, int puerto) {
		np = new NegocioPokemon();
		finalizar = false;
		this.host = host;
		this.puerto = puerto;
	}

	public void ejecutar() {
		try(Socket s = new Socket(this.host, this.puerto);
				BufferedReader br = new BufferedReader (new InputStreamReader (s.getInputStream(), "UTF-8"));
				Writer w = new OutputStreamWriter(s.getOutputStream(), "UTF-8");) {
			
			jugador = presConsola.menuCreacionJugador();
			
			//Avisa al servidor de que está preparado
			w.write("Listo\r\n");
			presConsola.mostrarEspera();
			
			if (br.readLine().equals("Empieza")) {
				int opcion;
				int mov;
				Pokemon pok;
				
				while (this.finalizar == false) {
					opcion = presConsola.menuAcciones();
					switch (opcion) {
						case 1: 
							mov = presConsola.menuMovimientos(jugador.getSeleccionado());
							this.np.getServiciosEquipo().elegirAtaque(jugador, mov);
							break;
						case 2:
							pok = presConsola.menuCambiarPokemon(jugador.getSeleccionado(), jugador.getEquipoPokemon());
							this.np.getServiciosEquipo().cambiarPokemon(jugador, pok);
							break;
						case 3:
							this.finalizar = true;
							break;
					}
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
