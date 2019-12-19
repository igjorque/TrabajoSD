package presentacion;

import java.util.List;
import java.util.Scanner;

import dominio.*;
import persistencia.PokemonPers;

public class presConsola {

	public static void menuCreacionJugador () {
		
		Jugador jugador;
		String nombreJugador;
		Equipo equipoJugador = new Equipo();
		
		List<Pokemon> listaPokemonDisponibles;
		
		Scanner s = new Scanner(System.in);
		
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
		nombreJugador = s.nextLine();
		System.out.println("Profesor Oak: Así que te llamas " + nombreJugador + "... ¡Estupendo!");
		
		System.out.println("Profesor Oak: Sin más dilación, vamos al grano:");
		System.out.println("Profesor Oak: Tienes que elegir un equipo con 6 POKÉMON.");
		System.out.println("Profesor Oak: Voy a mostrarte los que tengo disponibles:");
		System.out.println("(Para seleccionar un POKÉMON, introduce su índice)");
		
		listaPokemonDisponibles = PokemonPers.getPokemonList();
		int opcion;
		for (int i = 0; i < listaPokemonDisponibles.size(); i++) {
			System.out.println(i + " " + listaPokemonDisponibles.get(i).getNombre());
		}
		
		for (int i = 1; i < 7; i++) {
			System.out.println("Pokemon " + i + ": ");
			opcion = s.nextInt();
			equipoJugador.addPokemon(listaPokemonDisponibles.get(opcion));
		}
		
		
		
	}
	
}
