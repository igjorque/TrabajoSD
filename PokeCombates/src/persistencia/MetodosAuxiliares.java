package persistencia;

import dominio.*;

public class MetodosAuxiliares {

	// Método encargado de la conversión de string al tipo enumeración Tipo.
	public static Tipo stringToTipo(String s) {
		switch(s) {
			case "Fuego":
				return Tipo.Fuego;
			case "Agua":
				return Tipo.Agua;
			case "Planta":
				return Tipo.Planta;
			case "Normal":
				return Tipo.Normal;
			default:
				return null;
		}
	}
}
