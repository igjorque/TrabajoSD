package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.List;

import dominio.TablaTipos;
import dominio.Jugador;
import dominio.Movimiento;
import dominio.Pokemon;
import negocio.NegocioPokemon;

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
		
		try(BufferedReader br1 = new BufferedReader (new InputStreamReader (s1.getInputStream(), "UTF-8"));
				Writer w1 = new OutputStreamWriter(s1.getOutputStream(), "UTF-8");
				ObjectInputStream ois1 = new ObjectInputStream(s1.getInputStream());
			BufferedReader br2 = new BufferedReader (new InputStreamReader (s2.getInputStream(), "UTF-8"));
				Writer w2 = new OutputStreamWriter(s2.getOutputStream(), "UTF-8");
				ObjectInputStream ois2 = new ObjectInputStream(s2.getInputStream());)
		{
		
			br1.readLine(); //listo j1
			this.j1 = (Jugador) ois1.readObject();
			
			br2.readLine(); //listo j2
			this.j2 = (Jugador) ois2.readObject();
			
			w1.write("Empieza\r\n");
			w2.write("Empieza\r\n");
			
			while (comprobarFinalizado(this.np, this.j1, this.j2) == false) 
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
						}
					}
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
						}
					}
				}
				
				//
				
				br1.readLine(); //Lee rendirse j1
				br2.readLine(); //Lee rendirse j2
				String lineaRendir1 = br1.readLine();
				String lineaRendir2 = br2.readLine();
				if (lineaRendir1.equals("si") && lineaRendir2.equals("si")) {
					np.getServiciosEquipo().rendirse(j1.getEquipoPokemon());
					np.getServiciosEquipo().rendirse(j2.getEquipoPokemon());
					break;
				}
				if (lineaRendir1.equals("si") && lineaRendir2.equals("no")) {
					np.getServiciosEquipo().rendirse(j1.getEquipoPokemon());
					break;
				}
				if (lineaRendir1.equals("no") && lineaRendir2.equals("si")) {
					np.getServiciosEquipo().rendirse(j2.getEquipoPokemon());
					break;
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
				
				protocoloServidor(w1, j1, j2, ataqueExito1, ataqueExito2, deb1, deb2);
				protocoloServidor(w2, j1, j2, ataqueExito1, ataqueExito2, deb1, deb2);
				
				if (deb1 == true) {
					w1.write("DebilitadoCambio");
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
				
				if (deb2 == true) {
					w2.write("DebilitadoCambio");
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
				
			}
			
			//FINALIZA COMBATE
			
			w1.write("CombFinalizado");
			w2.write("CombFinalizado");
			
			boolean debJugador1 = np.getServiciosEquipo().equipoDebilitado(j1.getEquipoPokemon());
			boolean debJugador2 = np.getServiciosEquipo().equipoDebilitado(j2.getEquipoPokemon());
			
			if (debJugador1 && (!debJugador2)) {
				w1.write(j1.getNombre() + "|Pierde\r\n");
				w2.write(j2.getNombre() + "|Gana\r\n");
			}
				
			if ((!debJugador1) && debJugador2) {
				w1.write(j1.getNombre() + "|Gana\r\n");
				w2.write(j2.getNombre() + "|Pierde\r\n");
			}
				
			if (debJugador1 && debJugador2) {
				w1.write(j1.getNombre() + "|Pierde\r\n");
				w2.write(j2.getNombre() + "|Pierde\r\n");
			}
			
			
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void protocoloServidor(Writer w, Jugador j1, Jugador j2, float danno1, float danno2, boolean deb1, boolean deb2) {
		try {
			w.write("MensajeInicio\r\n");
			
			w.write("DannoJ1\r\n");
			if (danno1 != 0) {
				w.write(j1.getNombre() + "|" + j1.getSeleccionado().getNombre() + "|" + j1.getSeleccionado().getPs() + "|" + danno1 + "\r\n");
			} else {
				w.write("nulo\r\n");
			}

			w.write("DannoJ2\r\n");
			if (danno2 != 0) {
				w.write(j2.getNombre() + "|" + j2.getSeleccionado().getNombre() + "|" + j2.getSeleccionado().getPs() + "|" + danno2 + "\r\n");
			} else {
				w.write("nulo\r\n");
			}

			w.write("DebilitadoJ1\r\n");
			if (deb1) {
				w.write(j1.getNombre() + "\r\n");
			} else {
				w.write("nulo\r\n");
			}

			w.write("DebilitadoJ2\r\n");
			if (deb2) {
				w.write(j2.getNombre() + "\r\n");
			} else {
				w.write("nulo\r\n");
			}

			w.write("MensajeFin\r\n");
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
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
