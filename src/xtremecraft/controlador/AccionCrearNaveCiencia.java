package xtremecraft.controlador;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import xtremecraft.edificios.PuertoEstelar;
import xtremecraft.mapa.Coordenada;
import xtremecraft.partida.Jugador;
import xtremecraft.partida.Partida;
import xtremecraft.raza.CantidadDeSuministroInsuficienteException;
import xtremecraft.vista.MensajeDeError;

@SuppressWarnings("serial")
public class AccionCrearNaveCiencia extends AbstractAction{

	private Partida partida;
	private Coordenada coordenada;
	
	public AccionCrearNaveCiencia(Partida partida, Coordenada coordenada){
		
		super("CrearNaveCiencia");
		this.coordenada = coordenada;
		this.partida = partida;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Jugador jugadorActual = partida.quienJuega();
		PuertoEstelar puertoEstelar = (PuertoEstelar) partida.getMapa().getCeldaEnFilaColumna(coordenada.fila(), coordenada.columna()).getUbicableEnInferior();
		try{
			jugadorActual.crearNaveCiencia(puertoEstelar, this.partida.getMapa());
		}catch(CantidadDeSuministroInsuficienteException noHaySuministros){
			new MensajeDeError("La cantidad de poblacion disponibles no es suficiente");
		}
		
	}
}
