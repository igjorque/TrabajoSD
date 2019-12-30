package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.List;

import dominio.TablaTipos;
import dominio.Equipo;
import dominio.Jugador;
import dominio.Movimiento;
import dominio.Pokemon;
import negocio.NegocioPokemon;
import persistencia.PokemonPers;

public class AtiendeCombate implements Runnable{
	
	private Socket s1;
	private Socket s2;
	private Jugador j1;
	private Jugador j2;
	
	private NegocioPokemon np = new NegocioPokemon();
	private TablaTipos tabla = new TablaTipos();
	
	public AtiendeCombate(Socket conexion1, Socket conexion2) {
		this.s1 = conexion1;
		this.s2 = conexion2;
	}
	
	@Override
	public void run() {
		
		this.tabla.rellenarTabla();
		
		try(BufferedReader br1 = new BufferedReader (new InputStreamReader (s1.getInputStream()));
				PrintWriter w1 = new PrintWriter(s1.getOutputStream(), true);
			BufferedReader br2 = new BufferedReader (new InputStreamReader (s2.getInputStream()));
				PrintWriter w2 = new PrintWriter(s2.getOutputStream(), true);)
		{
			
			br1.readLine(); //Recibe esta línea porque envía caracteres extraños y no sabemos el motivo.
			br2.readLine(); //Recibe esta línea porque envía caracteres extraños y no sabemos el motivo.
			
			String lineaDatosJ1 = br1.readLine();
			String [] lineaDatosJ1Split = lineaDatosJ1.split(";");
			Equipo equipoJ1aux = new Equipo();
			equipoJ1aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ1Split[1])));
			equipoJ1aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ1Split[2])));
			equipoJ1aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ1Split[3])));
			equipoJ1aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ1Split[4])));
			equipoJ1aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ1Split[5])));
			equipoJ1aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ1Split[6])));
			this.j1 = new Jugador(lineaDatosJ1Split[0], equipoJ1aux);
			br1.readLine(); //listo j1
			
			String lineaDatosJ2 = br2.readLine();
			String [] lineaDatosJ2Split = lineaDatosJ2.split(";");
			Equipo equipoJ2aux = new Equipo();
			equipoJ2aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ2Split[1])));
			equipoJ2aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ2Split[2])));
			equipoJ2aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ2Split[3])));
			equipoJ2aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ2Split[4])));
			equipoJ2aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ2Split[5])));
			equipoJ2aux.addPokemon(np.getServiciosPokemon().clonarPokemon(PokemonPers.encontrarPokemon(lineaDatosJ2Split[6])));
			this.j2 = new Jugador(lineaDatosJ2Split[0], equipoJ2aux);
			br2.readLine(); //listo j2
			
			w1.println("Empieza");
			w2.println("Empieza");
			
			System.out.println("dnisoadn");
			boolean comprobacionFinalizado = comprobarFinalizado(this.np, this.j1, this.j2);
			System.out.println("dnisoadn");
			while (comprobacionFinalizado == false) 
			{
				///////////////////////////Lee protocolo cliente
				//DATOS J1
				br1.readLine(); //Lee inicio j1
				br1.readLine(); //Lee movimiento j1 
				String lineaMovimiento1 = br1.readLine();
				Movimiento m1 = null;
				if (!lineaMovimiento1.equals("nulo")) {
					List<Movimiento> listMov = this.j1.getSeleccionado().getMovimientos();
					for (int i = 0; i < listMov.size(); i++) {
						if (listMov.get(i).getNombre().equals(lineaMovimiento1)) {
							m1 = listMov.get(i);
						}
					}
				}
				
				br1.readLine(); //Lee cambiar j1
				String lineaCambiar1 = br1.readLine();
				Pokemon p1 = null;
				if (!lineaCambiar1.equals("nulo")) {
					List<Pokemon> listPoke = this.j1.getEquipoPokemon().getListaPokemon();
					for (int i = 0; i < listPoke.size(); i++) {
						if (listPoke.get(i).getNombre().equals(lineaCambiar1)) {
							p1 = listPoke.get(i);
							np.getServiciosEquipo().cambiarPokemon(j1, p1);
						}
					}
					
				}
				
				br1.readLine(); //Lee rendirse j1
				String lineaRendir1 = br1.readLine();
				if (lineaRendir1.equals("si")) {
					np.getServiciosEquipo().rendirse(j1.getEquipoPokemon());
				}
				
				//DATOS J2
				br2.readLine(); //Lee inicio j2
				
				br2.readLine(); //Lee movimiento j2 
				String lineaMovimiento2 = br2.readLine();
				Movimiento m2 = null;
				if (!lineaMovimiento2.equals("nulo")) {
					List<Movimiento> listMov = this.j2.getSeleccionado().getMovimientos();
					for (int i = 0; i < listMov.size(); i++) {
						if (listMov.get(i).getNombre().equals(lineaMovimiento2)) {
							m2 = listMov.get(i);
						}
					}
				}
				
				br2.readLine(); //Lee cambiar j2
				String lineaCambiar2 = br2.readLine();
				Pokemon p2 = null;
				if (!lineaCambiar2.equals("nulo")) {
					List<Pokemon> listPoke = this.j2.getEquipoPokemon().getListaPokemon();
					for (int i = 0; i < listPoke.size(); i++) {
						if (listPoke.get(i).getNombre().equals(lineaCambiar2)) {
							p2 = listPoke.get(i);
							np.getServiciosEquipo().cambiarPokemon(j2, p2);
						}
					}
					
				}
				
				br2.readLine(); //Lee rendirse j2
				String lineaRendir2 = br2.readLine();
				if (lineaRendir2.equals("si")) {
					np.getServiciosEquipo().rendirse(j2.getEquipoPokemon());
				}
				
				//
				br1.readLine(); //Lee fin j1
				br2.readLine(); //Lee fin j2
				
				//////////////////////////////////// fin leer protocolo cliente
				
				if (p1 != null) {
					np.getServiciosEquipo().cambiarPokemon(j1, p1);
				}
				if (p2 != null) {
					np.getServiciosEquipo().cambiarPokemon(j2, p2);
				}
				
				int prioridadM1 = -1;
				int prioridadM2 = -1;
				float ataqueExito1 = 0;
				float ataqueExito2 = 0;
				
				if ((p1 == null) && (m1 != null)) {
					prioridadM1 = m1.getPrioridad();
				}
				if ((p2 == null) && (m2 != null)) {
					prioridadM2 = m2.getPrioridad();
				}
				
				if (prioridadM1 > prioridadM2) {
					ataqueExito1 = np.getServiciosPokemon().atacar(j1.getSeleccionado(), j2.getSeleccionado(), tabla, m1);
					if ((m2 != null) && (j2.getSeleccionado().getDebilitado() == false)) {
						ataqueExito2 = np.getServiciosPokemon().atacar(j2.getSeleccionado(), j1.getSeleccionado(), tabla, m2);
					}
				}
				if (prioridadM1 < prioridadM2) {
					ataqueExito2 = np.getServiciosPokemon().atacar(j2.getSeleccionado(), j1.getSeleccionado(), tabla, m2);
					if ((m1 != null) && (j1.getSeleccionado().getDebilitado() == false)) {
						ataqueExito1 = np.getServiciosPokemon().atacar(j1.getSeleccionado(), j2.getSeleccionado(), tabla, m1);
					}
				}
				if ((prioridadM1 == prioridadM2) && (prioridadM1 != -1)) {
					if (j1.getSeleccionado().getVelocidad() > j2.getSeleccionado().getVelocidad()) {
						ataqueExito1 = np.getServiciosPokemon().atacar(j1.getSeleccionado(), j2.getSeleccionado(), tabla, m1);
						if (j2.getSeleccionado().getDebilitado() == false) {
							ataqueExito2 = np.getServiciosPokemon().atacar(j2.getSeleccionado(), j1.getSeleccionado(), tabla, m2);
						}
					}
					
					if (j1.getSeleccionado().getVelocidad() < j2.getSeleccionado().getVelocidad()) {
						ataqueExito2 = np.getServiciosPokemon().atacar(j2.getSeleccionado(), j1.getSeleccionado(), tabla, m2);
						if (j1.getSeleccionado().getDebilitado() == false) {
							ataqueExito1 = np.getServiciosPokemon().atacar(j1.getSeleccionado(), j2.getSeleccionado(), tabla, m1);
						}
					}
					
					if (j1.getSeleccionado().getVelocidad() == j2.getSeleccionado().getVelocidad()) {
						int jugadorAzar = (int) ((Math.random() * 2) + 1);
						if (jugadorAzar == 1) {
							ataqueExito1 = np.getServiciosPokemon().atacar(j1.getSeleccionado(), j2.getSeleccionado(), tabla, m1);
							if (j2.getSeleccionado().getDebilitado() == false) {
								ataqueExito2 = np.getServiciosPokemon().atacar(j2.getSeleccionado(), j1.getSeleccionado(), tabla, m2);
							}
						}
						else {
							ataqueExito2 = np.getServiciosPokemon().atacar(j2.getSeleccionado(), j1.getSeleccionado(), tabla, m2);
							if (j1.getSeleccionado().getDebilitado() == false) {
								ataqueExito1 = np.getServiciosPokemon().atacar(j1.getSeleccionado(), j2.getSeleccionado(), tabla, m1);
							}
						}
					}
				}
				
				boolean deb1 = j1.getSeleccionado().getDebilitado();
				boolean deb2 = j2.getSeleccionado().getDebilitado();
				
				protocoloServidor(w1, j1, j2, p1, p2, ataqueExito1, ataqueExito2, deb1, deb2);
				protocoloServidor(w2, j1, j2, p1, p2, ataqueExito1, ataqueExito2, deb1, deb2);
				
				comprobacionFinalizado = comprobarFinalizado(this.np, this.j1, this.j2);
				System.out.println("comprueba");
				if (comprobacionFinalizado == true) {
					w1.println("Fin");
					w2.println("Fin");
				}
				else {
					w1.println("Continua");
					w2.println("Continua");
					
					if (deb1 == true) {
						w1.println("DebilitadoCambio");
						String respuesta = br1.readLine();
						if (respuesta.equals("si")) {
							int lineaPokemon1 = Integer.parseInt(br1.readLine());
							Pokemon po1 = this.j1.getEquipoPokemon().getListaPokemon().get(lineaPokemon1);
							np.getServiciosEquipo().cambiarPokemon(j1, po1);
						}
						else {
							np.getServiciosEquipo().rendirse(j1.getEquipoPokemon());
						}
					}
					else {
						w1.println("NoDebilitadoCambio");
					}
					
					if (deb2 == true) {
						w2.println("DebilitadoCambio");
						String respuesta = br2.readLine();
						if (respuesta.equals("si")) {
							int lineaPokemon2 = Integer.parseInt(br2.readLine());
							Pokemon po2 = this.j2.getEquipoPokemon().getListaPokemon().get(lineaPokemon2);
							np.getServiciosEquipo().cambiarPokemon(j2, po2);
						}
						else {
							np.getServiciosEquipo().rendirse(j2.getEquipoPokemon());
						}
					}
					else {
						w2.println("NoDebilitadoCambio");
					}
				}
				
				p1 = null;
				p2 = null;
				m1 = null;
				m2 = null;
				ataqueExito1 = 0;
				ataqueExito2 = 0;
				deb1 = false;
				deb2 = false;
				
			}
			
			//FINALIZA COMBATE
			
			w1.println("CombFinalizado");
			w2.println("CombFinalizado");
			
			boolean debJugador1 = np.getServiciosEquipo().equipoDebilitado(j1.getEquipoPokemon());
			boolean debJugador2 = np.getServiciosEquipo().equipoDebilitado(j2.getEquipoPokemon());
			
			if (debJugador1 && (!debJugador2)) {
				w1.println(j1.getNombre() + ";Pierde");
				w1.println(j2.getNombre() + ";Gana");
				
				w2.println(j1.getNombre() + ";Pierde");
				w2.println(j2.getNombre() + ";Gana");
			}
				
			if ((!debJugador1) && debJugador2) {
				w1.println(j1.getNombre() + ";Gana");
				w1.println(j2.getNombre() + ";Pierde");
				
				w2.println(j1.getNombre() + ";Gana");
				w2.println(j2.getNombre() + ";Pierde");
			}
				
			if (debJugador1 && debJugador2) {
				w1.println(j1.getNombre() + ";Pierde");
				w1.println(j2.getNombre() + ";Pierde");
				
				w2.println(j1.getNombre() + ";Pierde");
				w2.println(j2.getNombre() + ";Pierde");
			}
			
			
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		
	}
	
	private static void protocoloServidor(PrintWriter w, Jugador j1, Jugador j2, Pokemon p1, Pokemon p2, float danno2, float danno1, boolean deb1, boolean deb2) {

		w.println("MensajeInicio");

		w.println("CambioJ1");
		if(p1 != null) {
			w.println(j1.getNombre() + ";" + p1.getNombre());
		}
		else {
			w.println("nulo");
		}
		
		w.println("CambioJ2");
		if(p2 != null) {
			w.println(j2.getNombre() + ";" + p2.getNombre());
		}
		else {
			w.println("nulo");
		}
		
		w.println("DannoJ1");
		if (danno1 != 0) {
			w.println(j1.getNombre() + ";" + j1.getSeleccionado().getNombre() + ";" + j1.getSeleccionado().getPs() + ";"
					+ danno1);
		} else {
			w.println("nulo");
		}

		w.println("DannoJ2");
		if (danno2 != 0) {
			w.println(j2.getNombre() + ";" + j2.getSeleccionado().getNombre() + ";" + j2.getSeleccionado().getPs() + ";"
					+ danno2);
		} else {
			w.println("nulo");
		}

		w.println("DebilitadoJ1");
		if (deb1) {
			w.println(j1.getNombre());
		} else {
			w.println("nulo");
		}

		w.println("DebilitadoJ2");
		if (deb2) {
			w.println(j2.getNombre());
		} else {
			w.println("nulo");
		}

		w.println("MensajeFin");
		
	}
	
	private static boolean comprobarFinalizado (NegocioPokemon np, Jugador j1, Jugador j2) {
		boolean pierdeJ1 = np.getServiciosEquipo().equipoDebilitado(j1.getEquipoPokemon());
		boolean pierdeJ2 = np.getServiciosEquipo().equipoDebilitado(j2.getEquipoPokemon());
		
		if (pierdeJ1 || pierdeJ2) {
			return true;
		}
		else {
			return false;
		}
	}

}
