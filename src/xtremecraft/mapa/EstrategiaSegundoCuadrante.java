package xtremecraft.mapa;

public class EstrategiaSegundoCuadrante extends EstrategiaCuadrante {
	
	public int getAlto(int posicion_base){
		return posicion_base + this.corrimiento();
	}
	public int getAncho(int posicion_base){
		return posicion_base + this.corrimiento();
	}
}
