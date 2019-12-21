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
		
		System.out.println("Profesor Pokemon: ¡Te doy la bienvenida al fabuloso mundo de los PokeCombates!");
		System.out.println("Profesor Pokemon: ¡Me llamo Oak!");
		
		System.out.println("Profesor Oak: ¿Cómo te llamas?");
		nombreJugador = sc.nextLine();
		System.out.println("Profesor Oak: Así que te llamas " + nombreJugador + "... ¡Estupendo!");
		
		System.out.println("Profesor Oak: Sin más dilación, vamos al grano:");
		System.out.println("Profesor Oak: Dime, ¿qué quieres saber?");
		
		while(continuar != true) {
			System.out.println("1. Muéstrame el listado de Pokémon disponibles.");
			System.out.println("2. Muéstrame las estadísticas de un Pokémon.");
			System.out.println("3. Quiero saber más sobre un movimiento.");
			System.out.println("4. ¡Elijamos a mis Pokémon!");
			indice1 = sc.nextInt();
			switch (indice1) {
				case 1:
					mostrarListadoPoke(listaPokemon);
					break;
				case 2:
					System.out.println("Profesor Oak: Por favor, dime qué Pokémon quieres que te explique. (introduce su índice)");
					indice2 = sc.nextInt();
					System.out.println(listaPokemon.get(indice2).toString());
					break;
				case 3:
					System.out.println("Profesor Oak: ¿Quieres ver antes un listado de los movimientos conocidos?");
					eleccion = sc.nextLine();
					switch(eleccion) {
						// Muestra el listado, y luego pasa al menú de elección de movimiento.
						case "s":		//Es una cascada de casos, cualquiera de ellos significando Afirmativo.
						case "S":
						case "si":
						case "Si":
							mostrarListadoMovi(listaMovimiento);
							break;
					}
					System.out.println("Profesor Oak: ¿Qué movimiento quieres consultar? (introduce su índice)");
					indice2 = sc.nextInt();
					System.out.println(listaMovimiento.get(indice2).toString());
					break;
				case 4:
					System.out.println("Profesor Oak: ¿Quieres ver el listado de Pokémon? (s/n)");
					eleccion = sc.nextLine();
					switch(eleccion) {
						// Muestra el listado, y luego pasa al menú de elección de equipo.
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
					System.out.println("Profesor Oak: ¡No entendí lo que me pediste! ¿Podrías repetirlo?");
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
		System.out.println("Profesor Oak: Tienes que elegir un equipo con 6 Pokémon.");
		System.out.println("Profesor Oak: El primer pokémon que elijas, será el que combata primero.");
		System.out.println("(Para seleccionar un Pokémon, introduce su índice)");
		int opcion;
		for (int i = 1; i < 7; i++) {
			System.out.println("Pokemon " + i + ": ");
			opcion = sc.nextInt();
			equipoJugador.addPokemon(listado.get(opcion));
		}
	}
	
}


