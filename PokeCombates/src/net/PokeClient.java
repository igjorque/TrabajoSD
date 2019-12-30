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
import java.io.PrintWriter;

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
				BufferedReader brComb = new BufferedReader (new InputStreamReader (sComb.getInputStream()));
				PrintWriter w = new PrintWriter(sComb.getOutputStream(), true);
				ObjectOutputStream oosC = new ObjectOutputStream(sComb.getOutputStream());
			Socket sFich = new Socket(this.host, this.puertoFicheros);
				ObjectInputStream ois = new ObjectInputStream(sFich.getInputStream());) 
		{
			w.println("aednwsdo");	//Envía esta línea porque recibe caracteres extraños y no sabemos el motivo.
			
			
			ArrayList<Pokemon> listaPoke = (ArrayList<Pokemon>) ois.readObject();
			ArrayList<Movimiento> listaMov = (ArrayList<Movimiento>) ois.readObject();
			jugador = presConsola.menuCreacionJugador(listaPoke, listaMov);

			List<Pokemon> listPAux = jugador.getEquipoPokemon().getListaPokemon();
			w.println(jugador.getNombre() + ";" + listPAux.get(0).getNombre() + ";" + listPAux.get(1).getNombre() + ";" + listPAux.get(2).getNombre() + ";" +
					listPAux.get(3).getNombre() + ";" + listPAux.get(4).getNombre() + ";" + listPAux.get(5).getNombre()); 
					
			
			// Avisa al servidor de que está preparado
			w.println("Listo");
			//oosC.writeObject(jugador);
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
						lineaDanno1Split = lineaDanno1.split(";");
						presConsola.mostrarDannos(lineaDanno1Split[0], lineaDanno1Split[1],
								Integer.parseInt(lineaDanno1Split[2]), Float.parseFloat(lineaDanno1Split[3]));
					}

					brComb.readLine(); // Daño j2
					String lineaDanno2 = brComb.readLine();
					String[] lineaDanno2Split;
					if (!lineaDanno2.equals("nulo")) {
						lineaDanno2Split = lineaDanno2.split(";");
						presConsola.mostrarDannos(lineaDanno2Split[0], lineaDanno2Split[1],
								Integer.parseInt(lineaDanno2Split[2]), Float.parseFloat(lineaDanno2Split[3]));
					}

					brComb.readLine(); // Debilitado j1
					String lineaDeb1 = brComb.readLine();
					if (!lineaDeb1.equals("nulo")) {
						presConsola.mostrarDebilitado(lineaDeb1);
					}

					brComb.readLine(); // Debilitado j2
					String lineaDeb2 = brComb.readLine();
					if (!lineaDeb2.equals("nulo")) {
						presConsola.mostrarDebilitado(lineaDeb2);
					}

					brComb.readLine(); // Fin
					
					String finContinua = brComb.readLine();
					if (finContinua.equals("Fin")) {
						this.finalizar = true;
					}
					else {
						String preguntaDeb = brComb.readLine();
						if (preguntaDeb.equals("DebilitadoCambio")) {
							String respuestaSiNo = presConsola.preguntaCambioPorDebilitado(this.jugador);
							if (respuestaSiNo.equals("si")) {
								int pokeElegido = presConsola.cambioPorDebilitado(this.jugador);
								w.println("si");
								w.println(pokeElegido);
							}
							else {
								w.println("no");
							}
						}
					}
				}
				
				brComb.readLine(); //CombFinalizado

				String resultadoJ1 = brComb.readLine();
				String resultadoJ2 = brComb.readLine();

				String[] resultadoJ1Split = resultadoJ1.split(";");
				String[] resultadoJ2Split = resultadoJ2.split(";");

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
	
	private static void protocoloCliente(PrintWriter w, Movimiento m, Pokemon p, boolean r) {
		
		w.println("MensajeInicio");

		w.println("Movimiento");
		if (m != null) {
			w.println(m.getNombre());
		} else {
			w.println("nulo");
		}

		w.println("Cambiar");
		if (p != null) {
			w.println(p.getNombre());
		} else {
			w.println("nulo");
		}

		w.println("Rendirse");
		if (r == true) {
			w.println("si");
		} else {
			w.println("no");
		}

		w.println("MensajeFin");

	}
}
