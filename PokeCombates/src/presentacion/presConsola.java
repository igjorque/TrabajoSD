package presentacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dominio.*;
import negocio.NegocioPokemon;

public class presConsola {

	private static Scanner sc = new Scanner(System.in);
	
	public static Jugador menuCreacionJugador (ArrayList<Pokemon> lPoke, ArrayList<Movimiento> lMov) {
		
		Jugador jugador;
		String nombreJugador;
		String eleccion;
		int indice1, indice2;
		Equipo equipoJugador = new Equipo();
		boolean continuar = false;
		List<Pokemon> listaPokemon = lPoke;
		List<Movimiento> listaMovimiento = lMov;
		
		
		System.out.println("	                          .::.\r\n" + 
				"                               .;:**'            \r\n" + 
				"                              `                 \r\n" + 
				"  .:XHHHHk.              db.   .;;.     dH  MX   \r\n" + 
				"oMMMMMMMMMMM       ~MM  dMMP :MMMMMR   MMM  MR      ~MRMN\r\n" + 
				"QMMMMMb  \"MMX       MMMMMMP !MX' :M~   MMM MMM  .oo. XMMM 'MMM\r\n" + 
				"  `MMMM.  )M> :X!Hk. MMMM   XMM.o\"  .  MMMMMMM X?XMMM MMM>!MMP\r\n" + 
				"   'MMMb.dM! XM M'?M MMMMMX.`MMMMMMMM~ MM MMM XM `\" MX MMXXMM\r\n" + 
				"    ~MMMMM~ XMM. .XM XM`\"MMMb.~*?**~ .MMX M t MMbooMM XMMMMMP\r\n" + 
				"     ?MMM>  YMMMMMM! MM   `?MMRb.    `\"\"\"   !L\"MMMMM XM IMMM\r\n" + 
				"      MMMX   \"MMMM\"  MM       ~%:           !Mh.\"\"\" dMI IMMP\r\n" + 
				"      'MMM.                                             IMX\r\n" + 
				"       ~M!M                                             IMP");
		
		System.out.println();
		System.out.println();
		
		System.out.println("Profesor Pokemon: �Te doy la bienvenida al fabuloso mundo de los PokeCombates!");
		System.out.println("Profesor Pokemon: �Me llamo Oak!");
		
		System.out.println("Profesor Oak: �C�mo te llamas?");
		nombreJugador = sc.nextLine();
		System.out.println("Profesor Oak: As� que te llamas " + nombreJugador + "... �Estupendo!");
		
		System.out.println("Profesor Oak: Sin m�s dilaci�n, vamos al grano:");
		System.out.println("Profesor Oak: Dime, �qu� quieres saber?");
		
		while(continuar != true) {
			System.out.println("1. Mu�strame el listado de Pok�mon disponibles.");
			System.out.println("2. Mu�strame las estad�sticas de un Pok�mon.");
			System.out.println("3. Quiero saber m�s sobre un movimiento.");
			System.out.println("4. �Elijamos a mis Pok�mon!");
			indice1 = sc.nextInt();
			sc.nextLine(); //Flush
			switch (indice1) {
				case 1:
					mostrarListadoPoke(listaPokemon);
					System.out.println();
					break;
				case 2:
					System.out.println("Profesor Oak: Por favor, dime qu� Pok�mon quieres que te explique. (introduce su �ndice)");
					indice2 = sc.nextInt();
					sc.nextLine(); //Flush
					System.out.println(listaPokemon.get(indice2).toString());
					break;
				case 3:
					System.out.println("Profesor Oak: �Quieres ver antes un listado de los movimientos conocidos?");
					eleccion = sc.nextLine();
					switch(eleccion) {
						// Muestra el listado, y luego pasa al men� de elecci�n de movimiento.
						case "s":		//Es una cascada de casos, cualquiera de ellos significando Afirmativo.
						case "S":
						case "si":
						case "Si":
							mostrarListadoMovi(listaMovimiento);
							break;
					}
					System.out.println("Profesor Oak: �Qu� movimiento quieres consultar? (introduce su �ndice)");
					indice2 = sc.nextInt();
					sc.nextLine(); //Flush
					System.out.println(listaMovimiento.get(indice2).toString());
					break;
				case 4:
					System.out.println("Profesor Oak: �Quieres ver el listado de Pok�mon? (si/no)");
					eleccion = sc.nextLine();
					switch(eleccion) {
						// Muestra el listado, y luego pasa al men� de elecci�n de equipo.
						case "s":		//Es una cascada de casos, cualquiera de ellos significando Afirmativo.
						case "S":
						case "si":
						case "Si":
							mostrarListadoPoke(listaPokemon);
							break;
					}
					elegirEquipo(equipoJugador, listaPokemon);
					continuar = true;
					break;
				default:
					System.out.println("Profesor Oak: �No entend� lo que me pediste! �Podr�as repetirlo?");
			}
		}
		
		System.out.println("Profesor Oak: �Est�s listo? Excelente, �vamos all�!");
		
		jugador = new Jugador(nombreJugador, equipoJugador);
		
		return jugador;
	}
	
	public static void mostrarEspera() {
		System.out.println("Esperando...");
	}
	
	public static int menuAcciones() {
		
		int opcion;
		
		do {
			System.out.println("Acciones disponibles:");
			System.out.println("	1. Elegir movimiento.");
			System.out.println("	2. Cambiar de pok�mon.");
			System.out.println("	3. Rendirse.");
			
			opcion = sc.nextInt();
			sc.nextLine(); //flush
		} while ((opcion < 1) || (opcion > 3));
		
		return opcion;
	}
	
	public static int menuMovimientos(Pokemon p) {
		
		int opcion;
		
		do {
			System.out.println("Movimientos de " + p.getNombre() + ":");
			System.out.println("	1. " + p.getMovimientos().get(0));
			System.out.println("	2. " + p.getMovimientos().get(1));
			System.out.println("	3. " + p.getMovimientos().get(2));
			System.out.println("	4. " + p.getMovimientos().get(3));
			
			opcion = sc.nextInt() - 1;
			sc.nextLine(); //flush
		} while ((opcion < 0) || (opcion > 4));
		
		return opcion;
	}
	
	public static Pokemon menuCambiarPokemon (Pokemon actual, Equipo e) {
		
		int opcion;
		
		int numActual;
		if (actual.getNombre().equals(e.getListaPokemon().get(0).getNombre())) {
			numActual = 0;
		}
		else if (actual.getNombre().equals(e.getListaPokemon().get(1).getNombre())) {
			numActual = 1;
		}
		else if (actual.getNombre().equals(e.getListaPokemon().get(2).getNombre())) {
			numActual = 2;
		}
		else if (actual.getNombre().equals(e.getListaPokemon().get(3).getNombre())) {
			numActual = 3;
		}
		else if (actual.getNombre().equals(e.getListaPokemon().get(4).getNombre())) {
			numActual = 4;
		}
		else {
			numActual = 5;
		}
	
		do {
			System.out.println("Equipo:");
			System.out.println("	1. " + e.getListaPokemon().get(0).getNombre() + ", " + e.getListaPokemon().get(0).getPs() + " PS.");
			System.out.println("	2. " + e.getListaPokemon().get(1).getNombre() + ", " + e.getListaPokemon().get(1).getPs() + " PS.");
			System.out.println("	3. " + e.getListaPokemon().get(2).getNombre() + ", " + e.getListaPokemon().get(2).getPs() + " PS.");
			System.out.println("	4. " + e.getListaPokemon().get(3).getNombre() + ", " + e.getListaPokemon().get(3).getPs() + " PS.");
			System.out.println("	5. " + e.getListaPokemon().get(4).getNombre() + ", " + e.getListaPokemon().get(4).getPs() + " PS.");
			System.out.println("	6. " + e.getListaPokemon().get(5).getNombre() + ", " + e.getListaPokemon().get(5).getPs() + " PS.");
			
			opcion = sc.nextInt() - 1;
			sc.nextLine(); //flush
			
			if (opcion == numActual) {
				System.out.println("�Ya es el pok�mon seleccionado!");
			}
			if (e.getListaPokemon().get(opcion).getDebilitado() == true) {
				System.out.println("Este pok�mon est� debilitado.");
			}
			
		} while ((opcion < 0) || (opcion > 5) || (opcion == numActual) || (e.getListaPokemon().get(opcion).getDebilitado() == true));
		
		return e.getListaPokemon().get(opcion);
	}
	
	public static void mostrarDannos(String j, String poke, int vida, float d) {
		System.out.println("El pok�mon " + poke + " de " + j + " ha recibido " + (int) d + " de da�o, le queda " + vida + " de vida.");
	}
	
	public static void mostrarDebilitado(String j) {
		System.out.println("�El pok�mon de " + j + " se ha debilitado!");
	}
	
	public static void mostrarCambioPokemon(String j, String poke) {
		System.out.println("El jugador " + j + " ha cambiado al pok�mon por " + poke);
	}
	
	public static String preguntaCambioPorDebilitado(Jugador j) {
		
		String opcion;
		
		boolean quedanNoDebilitados = false;
		List<Pokemon> listP = j.getEquipoPokemon().getListaPokemon();
		for (int i = 0; i < listP.size(); i++) {
			if (listP.get(i).getDebilitado() == false) {
				quedanNoDebilitados = true;
			}
		}
		
		if (quedanNoDebilitados) {
			System.out.println("Tu pok�mon se ha debilitado, �quieres sacar otro? (si/no)");
			opcion = sc.nextLine();
			
			return opcion;
		}
		else {
			return "no";
		}
		
	}
	
	public static int cambioPorDebilitado(Jugador j) {
		
		int opcion;
		
		System.out.println("�Qu� pok�mon quieres sacar al combate?");
		List<Pokemon> listP = j.getEquipoPokemon().getListaPokemon();
		for (int i = 1; i < listP.size()+1; i++) {
			if (listP.get(i-1).getDebilitado() == false) {
				System.out.println(i + " " + listP.get(i-1).getNombre());
			}
		}
		
		do {
		opcion = sc.nextInt() - 1;
		sc.nextLine(); //flush
		
		} while (listP.get(opcion).getDebilitado());
		
		return opcion;
	}
	
	public static void perder(String j) {
		System.out.println(j + ": �Has perdido! M�s suerte la pr�xima vez.");
	}
	
	public static void ganar(String j) {
		System.out.println(j + ": �Felicidades, eres el mejor entrenador pok�mon!");
	}
	
	private static void mostrarListadoPoke(List<Pokemon> listado) {
		System.out.println("Profesor Oak: Voy a mostrarte los que tengo disponibles:");
		for (int i = 1; i < listado.size()+1; i++) {
			System.out.println(i + " " + listado.get(i-1).getNombre());
		}
	}
	
	private static void mostrarListadoMovi(List<Movimiento> listado) {
		System.out.println("Profesor Oak: Voy a mostrarte los que conocemos:");
		for (int i = 1; i<listado.size()+1; i++) {
			System.out.println(i + " " + listado.get(i-1).getNombre());
		}
	}
	
	private static void elegirEquipo(Equipo equipoJugador, List<Pokemon> listado){
		System.out.println("Profesor Oak: Tienes que elegir un equipo con 6 Pok�mon.");
		System.out.println("Profesor Oak: El primer pok�mon que elijas, ser� el que combata primero.");
		System.out.println("(Para seleccionar un Pok�mon, introduce su �ndice)");
		int opcion;
		for (int i = 1; i < 7; i++) {
			System.out.println("Pokemon " + i + ": ");
			opcion = sc.nextInt() - 1;
			sc.nextLine(); //flush
			
			NegocioPokemon np = new NegocioPokemon();
			
			equipoJugador.addPokemon(np.getServiciosPokemon().clonarPokemon(listado.get(opcion)));
		}
	}
	
}


