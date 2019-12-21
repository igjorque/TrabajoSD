package presentacion;

import java.util.List;
import java.util.Scanner;

import dominio.*;
import persistencia.MovimientoPers;
import persistencia.PokemonPers;

public class presConsola {

	public static void menuCreacionJugador () {
		
		Jugador jugador;
		String nombreJugador;
		String eleccion;
		int indice1, indice2;
		Equipo equipoJugador = new Equipo();
		boolean continuar = false;
		List<Pokemon> listaPokemon = PokemonPers.getPokemonList();
		List<Movimiento> listaMovimiento = MovimientoPers.getMovimientoList();
		
		
		Scanner sc = new Scanner(System.in);
		
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
			switch (indice1) {
				case 1:
					mostrarListadoPoke(listaPokemon);
					break;
				case 2:
					System.out.println("Profesor Oak: Por favor, dime qu� Pok�mon quieres que te explique. (introduce su �ndice)");
					indice2 = sc.nextInt();
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
					System.out.println(listaMovimiento.get(indice2).toString());
					break;
				case 4:
					System.out.println("Profesor Oak: �Quieres ver el listado de Pok�mon? (s/n)");
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
					elegirEquipo(sc, equipoJugador, listaPokemon);
					continuar = true;
					break;
				default:
					System.out.println("Profesor Oak: �No entend� lo que me pediste! �Podr�as repetirlo?");
			}
		}
		
		jugador = new Jugador(nombreJugador, equipoJugador);
		
		System.out.println("");
		sc.close();
	}
	
	private static void mostrarListadoPoke(List<Pokemon> listado) {
		System.out.println("Profesor Oak: Voy a mostrarte los que tengo disponibles:");
		for (int i = 0; i < listado.size(); i++) {
			System.out.println(i + " " + listado.get(i).getNombre());
		}
	}
	
	private static void mostrarListadoMovi(List<Movimiento> listado) {
		System.out.println("Profesor Oak: Voy a mostrarte los que conocemos:");
		for (int i = 0; i<listado.size(); i++) {
			System.out.println(i + " " + listado.get(i).getNombre());
		}
	}
	
	private static void elegirEquipo(Scanner sc, Equipo equipoJugador, List<Pokemon> listado){
		System.out.println("Profesor Oak: Tienes que elegir un equipo con 6 Pok�mon.");
		System.out.println("Profesor Oak: El primer pok�mon que elijas, ser� el que combata primero.");
		System.out.println("(Para seleccionar un Pok�mon, introduce su �ndice)");
		int opcion;
		for (int i = 1; i < 7; i++) {
			System.out.println("Pokemon " + i + ": ");
			opcion = sc.nextInt();
			equipoJugador.addPokemon(listado.get(opcion));
		}
	}
	
}


