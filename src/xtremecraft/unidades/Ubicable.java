package xtremecraft.unidades;

import xtremecraft.mapa.Coordenada;
import xtremecraft.mapa.Terreno;

public interface Ubicable {
	
	public Coordenada getUbicacionActual();
	
	public void actualizarUbicacion(Terreno terreno);
	
	public boolean puedeUbicarseSobreRecursoNatural();
	
	public boolean puedeUbicarseEnTierra();
	
	public boolean puedeUbicarseEnAire();
	
	public boolean estaElevado();

	public boolean elevar();
}
