package xtremecraft.unidades;


public abstract class UnidadAerea extends Unidad{

	protected UnidadAerea() {
		super();
	}
	
	public boolean puedeUbicarseEnTierra() {
		return false;
	}

	public boolean puedeUbicarseEnAire() {
		return true;
	}

}
