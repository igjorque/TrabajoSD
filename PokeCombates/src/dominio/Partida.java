package dominio;

public class Partida {
	
	private Jugador j1;
	private Jugador j2;
	private TablaTipos tabla;
	
	public Partida (Jugador j1, Jugador j2, TablaTipos tabla) {
		this.setJ1(j1);
		this.setJ2(j2);
		this.setTabla(tabla);
	}

	public Jugador getJ1() {
		return j1;
	}

	public void setJ1(Jugador j1) {
		this.j1 = j1;
	}

	public Jugador getJ2() {
		return j2;
	}

	public void setJ2(Jugador j2) {
		this.j2 = j2;
	}

	public TablaTipos getTabla() {
		return tabla;
	}

	public void setTabla(TablaTipos tabla) {
		this.tabla = tabla;
	}
}
