package xtremecraft.vista;

import java.awt.Color;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class VistaNaveTransporte extends VistaIdentificable {
	
	public VistaNaveTransporte(){
		
		this.color = new Color(153,153,0);
	
	}

	@Override
	public ArrayList<String> mostrarOpcionesAccion() {
		
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("Subir unidad");
		opciones.add("Bajar unidad");
		opciones.add("Mover");
		
		return opciones;
		
	}

}