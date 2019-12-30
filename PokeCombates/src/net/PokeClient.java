package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

	@SuppressWarnings("unchecked")
	public void ejecutar() {
		String lineaCambio1, lineaCambio2;
		String [] lineaCambio1Split; 
		String [] lineaCambio2Split;
		String lineaDanno1, lineaDanno2;
		String[] lineaDanno1Split;
		String[] lineaDanno2Split;
		String lineaDeb1, lineaDeb2;
		String finContinua;
		String preguntaDeb;
		String respuestaSiNo;
		
		
		
		
		try(Socket sComb = new Socket(this.host, this.puerto);
				BufferedReader brComb = new BufferedReader (new InputStreamReader (sComb.getInputStream()));
				PrintWriter w = new PrintWriter(sComb.getOutputStream(), true);
				ObjectOutputStream oosC = new ObjectOutputStream(sComb.getOutputStream());
			Socket sFich = new Socket(this.host, this.puertoFicheros);
				ObjectInputStream ois = new ObjectInputStream(sFich.getInputStream());) 
		{
			w.println(".");	//Envía esta línea porque recibe caracteres extraños y no sabemos el motivo.
			
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

					brComb.readLine(); //Cambio j1
					lineaCambio1 = brComb.readLine();
					if (!lineaCambio1.equals("nulo")) {
						lineaCambio1Split = lineaCambio1.split(";");
						if (this.jugador.getNombre().equals(lineaCambio1Split[0])) {
							for(int i = 0; i < this.jugador.getEquipoPokemon().getListaPokemon().size(); i++) {
								if (this.jugador.getEquipoPokemon().getListaPokemon().get(i).getNombre().equals(lineaCambio1Split[1])) {
									this.jugador.setSeleccionado(this.jugador.getEquipoPokemon().getListaPokemon().get(i));
								}
							}
						}
						presConsola.mostrarCambioPokemon(lineaCambio1Split[0], lineaCambio1Split[1]);
					}
					
					brComb.readLine(); //Cambio j2
					lineaCambio2 = brComb.readLine();
					if (!lineaCambio2.equals("nulo")) {
						lineaCambio2Split = lineaCambio2.split(";");
						if (this.jugador.getNombre().equals(lineaCambio2Split[0])) {
							for(int i = 0; i < this.jugador.getEquipoPokemon().getListaPokemon().size(); i++) {
								if (this.jugador.getEquipoPokemon().getListaPokemon().get(i).getNombre().equals(lineaCambio2Split[1])) {
									this.jugador.setSeleccionado(this.jugador.getEquipoPokemon().getListaPokemon().get(i));
								}
							}
						}
						presConsola.mostrarCambioPokemon(lineaCambio2Split[0], lineaCambio2Split[1]);
					}
					
					brComb.readLine(); // Daño j1
					lineaDanno1 = brComb.readLine();
					if (!lineaDanno1.equals("nulo")) {
						lineaDanno1Split = lineaDanno1.split(";");
						if (this.jugador.getNombre().equals(lineaDanno1Split[0])) {
							this.jugador.getSeleccionado().setPs(Integer.parseInt(lineaDanno1Split[2]));
						}
						
						presConsola.mostrarDannos(lineaDanno1Split[0], lineaDanno1Split[1],
								Integer.parseInt(lineaDanno1Split[2]), Float.parseFloat(lineaDanno1Split[3]));
					}

					brComb.readLine(); // Daño j2
					lineaDanno2 = brComb.readLine();
					if (!lineaDanno2.equals("nulo")) {
						lineaDanno2Split = lineaDanno2.split(";");
						if (this.jugador.getNombre().equals(lineaDanno2Split[0])) {
							this.jugador.getSeleccionado().setPs(Integer.parseInt(lineaDanno2Split[2]));
						}
						presConsola.mostrarDannos(lineaDanno2Split[0], lineaDanno2Split[1],
								Integer.parseInt(lineaDanno2Split[2]), Float.parseFloat(lineaDanno2Split[3]));
					}

					brComb.readLine(); // Debilitado j1
					lineaDeb1 = brComb.readLine();
					if (!lineaDeb1.equals("nulo")) {
						if (this.jugador.getNombre().equals(lineaDeb1)) {
							this.jugador.getSeleccionado().setDebilitado(true);;
						}
						presConsola.mostrarDebilitado(lineaDeb1);
					}

					brComb.readLine(); // Debilitado j2
					lineaDeb2 = brComb.readLine();
					if (!lineaDeb2.equals("nulo")) {
						if (this.jugador.getNombre().equals(lineaDeb2)) {
							this.jugador.getSeleccionado().setDebilitado(true);;
						}
						presConsola.mostrarDebilitado(lineaDeb2);
					}

					brComb.readLine(); // Fin
					
					finContinua = brComb.readLine();
					if (finContinua.equals("Fin")) {
						this.finalizar = true;
					}
					else {
						preguntaDeb = brComb.readLine();
						if (preguntaDeb.equals("DebilitadoCambio")) {
							respuestaSiNo = presConsola.preguntaCambioPorDebilitado(this.jugador);
							if (respuestaSiNo.equals("si")) {
								int pokeElegido = presConsola.cambioPorDebilitado(this.jugador);
								
								this.jugador.setSeleccionado(this.jugador.getEquipoPokemon().getListaPokemon().get(pokeElegido));
								
								w.println("si");
								w.println(pokeElegido);
							}
							else {
								w.println("no");
							}
						}
					}
					
					
					pok = null;
					mov = null;
					ren = false;
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
