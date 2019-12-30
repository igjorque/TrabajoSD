package dominio;

import java.util.HashMap;

public class TablaTipos {
	
	private HashMap<Tipo, HashMap<Tipo, Float>> tabla;

	// Constructor del objeto TablaTipos. Inicia el atributo tabla como un hashmap vacío, un método lo rellenará a posteriori.
	public TablaTipos() {
		this.tabla = new HashMap<Tipo, HashMap<Tipo, Float>>();
	}

	// Obtiene el atributo tabla.
	public HashMap<Tipo, HashMap<Tipo, Float>> getTabla() {
		return tabla;
	}
	
	// Obtiene una eficacia de la tabla.
	public float buscarEficacia(Tipo tipo1, Tipo tipo2) {
		return this.tabla.get(tipo1).get(tipo2);
	}
	
	// Rellena la tabla de tipos.
	public void rellenarTabla() {
		anadirEficacia(Tipo.Fuego, Tipo.Fuego, 1f);
		anadirEficacia(Tipo.Fuego, Tipo.Agua, 0.5f);
		anadirEficacia(Tipo.Fuego, Tipo.Planta, 2f);
		anadirEficacia(Tipo.Fuego, Tipo.Normal, 1f);
		
		anadirEficacia(Tipo.Agua, Tipo.Agua, 1f);
		anadirEficacia(Tipo.Agua, Tipo.Fuego, 25f);
		anadirEficacia(Tipo.Agua, Tipo.Planta, 0.5f);
		anadirEficacia(Tipo.Agua, Tipo.Normal, 1f);
		
		anadirEficacia(Tipo.Planta, Tipo.Planta, 1f);
		anadirEficacia(Tipo.Planta, Tipo.Agua, 2f);
		anadirEficacia(Tipo.Planta, Tipo.Fuego, 0.5f);
		anadirEficacia(Tipo.Planta, Tipo.Normal, 1f);

		anadirEficacia(Tipo.Normal, Tipo.Normal, 1f);
		anadirEficacia(Tipo.Normal, Tipo.Agua, 1f);
		anadirEficacia(Tipo.Normal, Tipo.Planta, 1f);
		anadirEficacia(Tipo.Normal, Tipo.Fuego, 1f);
	}

	// Añade una eficacia a la tabla. También sirve para modificar. El método put
	// reemplaza si ya existe la clave.
	private void anadirEficacia(Tipo tipo1, Tipo tipo2, float multiplicador) {
		HashMap<Tipo, Float> aux;

		if (!tabla.containsKey(tipo1)) {
			aux = new HashMap<Tipo, Float>();
		} else {
			aux = tabla.get(tipo1);
		}

		aux.put(tipo2, multiplicador);
		this.tabla.put(tipo1, aux);
	}
}
