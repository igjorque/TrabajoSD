package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

			List<Pokemon> listPAux = jugador.getEquipoPokemon().getListaPokemon();
			
			w.write(jugador.getNombre() + "|" + listPAux.get(0).getNombre() + "|" + listPAux.get(1).getNombre() + "|" + listPAux.get(2).getNombre() + "|" +
					listPAux.get(3).getNombre() + "|" + listPAux.get(4).getNombre() + "|" + listPAux.get(5).getNombre() + "\r\n"); 
					
			
			// Avisa al servidor de que está preparado
			w.write("Listo\r\n");
			oosC.writeObject(jugador);
			presConsola.mostrarEspera();

			brComb.readLine(); //empieza
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

					///////////////////////

					brComb.readLine(); // Inicio

					brComb.readLine(); // Daño j1
					String lineaDanno1 = brComb.readLine();
					String[] lineaDanno1Split;
					if (!lineaDanno1.equals("nulo")) {
						lineaDanno1Split = lineaDanno1.split("|");
						presConsola.mostrarDannos(lineaDanno1Split[0], lineaDanno1Split[1],
								Integer.parseInt(lineaDanno1Split[2]), Float.parseFloat(lineaDanno1Split[3]));
					}

					brComb.readLine(); // Daño j2
					String lineaDanno2 = brComb.readLine();
					String[] lineaDanno2Split;
					if (!lineaDanno2.equals("nulo")) {
						lineaDanno2Split = lineaDanno2.split("|");
						presConsola.mostrarDannos(lineaDanno2Split[0], lineaDanno2Split[1],
								Integer.parseInt(lineaDanno2Split[2]), Float.parseFloat(lineaDanno2Split[3]));
					}

					brComb.readLine(); // Debilitado j1
					String lineaDeb1 = brComb.readLine();
					if (!lineaDanno1.equals("nulo")) {
						presConsola.mostrarDebilitado(lineaDeb1);
					}

					brComb.readLine(); // Debilitado j2
					String lineaDeb2 = brComb.readLine();
					if (!lineaDanno2.equals("nulo")) {
						presConsola.mostrarDebilitado(lineaDeb2);
					}

					brComb.readLine(); // Fin

					////////////////////////

					if (brComb.readLine().equals("DebilitadoCambio")) {
						String respuestaSiNo = presConsola.preguntaCambioPorDebilitado(this.jugador);
						if (respuestaSiNo.equals("si")) {
							int pokeElegido = presConsola.cambioPorDebilitado(this.jugador);
							w.write(pokeElegido);
						}
					}

					///////////////////////

					if (brComb.readLine().equals("CombFinalizado")) {
						this.finalizar = true;
					}

				}

				String resultadoJ1 = brComb.readLine();
				String resultadoJ2 = brComb.readLine();

				String[] resultadoJ1Split = resultadoJ1.split("|");
				String[] resultadoJ2Split = resultadoJ2.split("|");

				if (resultadoJ1Split[1].equals("Gana")) {
					presConsola.ganar(resultadoJ1Split[0]);
				} else {
					presConsola.perder(resultadoJ1Split[0]);
				}

				if (resultadoJ2Split[1].equals("Gana")) {
					presConsola.ganar(resultadoJ2Split[0]);
				} else {
					presConsola.perder(resultadoJ2Split[0]);
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
				w.write(m.getNombre() + "\r\n");
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
