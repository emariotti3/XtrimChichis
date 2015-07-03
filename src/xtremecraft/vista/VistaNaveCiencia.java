package xtremecraft.vista;

import java.awt.Color;

@SuppressWarnings("serial")
public class VistaNaveCiencia extends VistaIdentificable {
	
	static String nombreNave = "Nave Ciencia";
	
	public VistaNaveCiencia(){
		
		super(nombreNave);
		this.color = new Color(153,153,0);
	
	}

}
