package xtremecraft.vista;

import java.awt.Color;
import java.util.ArrayList;

import xtremecraft.edificios.RecolectorDeMineral;

@SuppressWarnings("serial")
public class VistaRecolectorDeMineral extends VistaIdentificable {
	
	static String nombreRecolector = "Recolector de Mineral";
	static String estadoInicial = RecolectorDeMineral.getEstadoInicial();
	
	public VistaRecolectorDeMineral(){
		
		super(nombreRecolector,estadoInicial);
		this.color = new Color(255,200,0);
	
	}
	
	public ArrayList<String> devolverAcciones(){
		
		ArrayList<String> acciones = new ArrayList<String>();
		
		return acciones;
	}

}
