package dominio;

import java.util.HashMap;
import java.util.Map;

public class TablaTipos {
	
	private HashMap<Tipo, HashMap<Tipo, Float>> tabla;

	public TablaTipos() {
		this.tabla = new HashMap<Tipo, HashMap<Tipo, Float>>();
	}

	public HashMap<Tipo, HashMap<Tipo, Float>> getTabla() {
		return tabla;
	}
	
	//También sirve para modificar. El método put reemplaza si ya existe la clave.
	public void anadirEficacia(Tipo tipo1, Tipo tipo2, float multiplicador) {
		HashMap<Tipo, Float> aux;
		
		if (!tabla.containsKey(tipo1)) {
			aux = new HashMap<Tipo, Float>();
		}
		else {
			aux = tabla.get(tipo1);
		}
		
		aux.put(tipo2, multiplicador);
		this.tabla.put(tipo1, aux);
	}
	
	public float buscarEficacia(Tipo tipo1, Tipo tipo2) {
		return this.tabla.get(tipo1).get(tipo2);
	}
}
