package xtremecraft.partida;

import java.util.Observable;
import xtremecraft.edificios.Barraca;
import xtremecraft.edificios.DepositoDeSuministros;
import xtremecraft.edificios.Fabrica;
import xtremecraft.edificios.PuertoEstelar;
import xtremecraft.edificios.RecolectorDeGasVespeno;
import xtremecraft.edificios.RecolectorDeMineral;
import xtremecraft.mapa.Mapa;
import xtremecraft.mapa.Terreno;
import xtremecraft.mapa.Tierra;
import xtremecraft.raza.Terran;
import xtremecraft.unidades.Atacable;
import xtremecraft.unidades.Defendible;
import xtremecraft.unidades.Espectro;
import xtremecraft.unidades.Goliat;
import xtremecraft.unidades.Marine;
import xtremecraft.unidades.NaveCiencia;
import xtremecraft.unidades.NaveTransporte;
import xtremecraft.unidades.Ubicable;

public class Jugador extends Observable implements Actualizable {
	
	private String nombre;
	private Terran nacion;
	private Jugador siguienteJugador;
	private int numeroDeJugador;
	private boolean esMiTurno;
	
	public Jugador (String nombre, Tierra tierra) throws NombreMuyCortoException{
		
		if (nombre.length() < 4){
			throw new NombreMuyCortoException();
		}
		this.nombre = nombre;
		this.nacion = new Terran(this);
		this.crearBaseInicial(tierra);
		this.esMiTurno = false;
		
	}
		
	public String nombre(){
		
		return this.nombre;
		
	}
	
	public Terran nacion(){
		
		return this.nacion;
		
	}
	
	public void setNumeroDeJugador(int numero){
		
		this.numeroDeJugador = numero;
		
	}
	
	public int getNumeroDeJugador(){
		
		return this.numeroDeJugador;
		
	}

	public boolean estaEnJuego(){
		
		return this.nacion.estaViva();
		
	}
	
	public boolean esDeMiPropiedad(Ubicable ubicable){
		
		return this.nacion.esDeMiPropiedad(ubicable);
		
	}
	
	public void atacar(Defendible atacante, Atacable atacado){
		
		Ubicable atacanteUbicado = (Ubicable) atacante;
		
		if (!this.esDeMiPropiedad(atacanteUbicado)){
			throw new ElAtacanteNoEsDelJugadorException();
		}
		
		atacante.atacar(atacado);
	}
	
	private void crearBaseInicial(Tierra tierra){
		
		DepositoDeSuministros deposito = this.nacion.crearDepositoDeSuministros(tierra);
		for(int tiempo = 0; tiempo<deposito.tiempoConstruccion();tiempo++){
			deposito.pasarTiempo();
		}
		this.setChanged();
		this.notifyObservers();
		
	}
	
	//metodos de creacion
	
	public Barraca crearBarraca(Terreno unTerreno){
		
		Barraca nuevaBarraca = this.nacion.crearBarraca(unTerreno);
		this.setChanged();
		this.notifyObservers();
		return nuevaBarraca;
		
	}
	
	public Fabrica crearFabrica(Terreno unTerreno){
		
		Fabrica nuevaFabrica = this.nacion.crearFabrica(unTerreno);
		this.setChanged();
		this.notifyObservers();
		return nuevaFabrica;
		
	}
	
	public PuertoEstelar crearPuertoEstelar(Terreno unTerreno){
		
		PuertoEstelar nuevoPuerto = this.nacion.crearPuertoEstelar(unTerreno);
		this.setChanged();
		this.notifyObservers();
		return nuevoPuerto;
		
	}
	
	public RecolectorDeGasVespeno crearRecolectorDeGasVespeno(Terreno unTerreno){
		
		RecolectorDeGasVespeno recolectorDeGas = this.nacion.crearRecolectorDeGasVespeno(unTerreno);
		this.setChanged();
		this.notifyObservers();
		return recolectorDeGas;
		
	}
	
	public RecolectorDeMineral crearRecolectorDeMineral(Terreno unTerreno){
		
		RecolectorDeMineral recolectorDeMineral = this.nacion.crearRecolectorDeMineral(unTerreno);
		this.setChanged();
		this.notifyObservers();
		return recolectorDeMineral;
		
	}
	
	public Marine crearMarine(Barraca unaBarraca, Mapa unMapa){
		
		Marine nuevoMarine = (Marine) this.nacion.crearMarine(unaBarraca, unMapa);
		this.setChanged();
		this.notifyObservers();
		return nuevoMarine;
		
	}
	
	public Goliat crearGoliat(Fabrica unaFabrica, Mapa unMapa){
		
		Goliat nuevoGoliat = (Goliat) this.nacion.crearGoliat(unaFabrica, unMapa);
		this.setChanged();
		this.notifyObservers();
		return nuevoGoliat;
		
	}
	
	public Espectro crearEspectro(PuertoEstelar unPuerto, Mapa unMapa){
		
		Espectro nuevoEspectro = (Espectro) this.nacion.crearEspectro(unPuerto, unMapa);
		this.setChanged();
		this.notifyObservers();
		return nuevoEspectro;
		
	}
	
	public NaveCiencia crearNaveCiencia(PuertoEstelar unPuerto, Mapa unMapa){
		
		NaveCiencia nuevaNaveCiencia = (NaveCiencia) this.nacion.crearNaveCiencia(unPuerto, unMapa);
		this.setChanged();
		this.notifyObservers();
		return nuevaNaveCiencia;
		
	}
	
	public NaveTransporte crearNaveTransporte(PuertoEstelar unPuerto, Mapa unMapa){
		
		NaveTransporte nuevaNaveTransporte = (NaveTransporte) this.nacion.crearNaveTransporte(unPuerto, unMapa);
		this.setChanged();
		this.notifyObservers();
		return nuevaNaveTransporte;
		
	}
	
	public DepositoDeSuministros crearDepositoDeSuministros(Terreno unTerreno){
		
		DepositoDeSuministros nuevoDeposito = this.nacion.crearDepositoDeSuministros(unTerreno);
		this.setChanged();
		this.notifyObservers();
		return nuevoDeposito;
		
	}

	public void setJugadorSiguiente(Jugador jugador) {
		
		this.siguienteJugador = jugador;
		
	}

	public void setTurno() {
		
		this.esMiTurno = true;
		
	}

	public void pasarTurno() {
		
		if(!this.esMiTurno){
			throw new JugadorNoTieneElTurnoException();
		}
		this.pasarTiempo();
		this.esMiTurno = false;
		this.siguienteJugador.setTurno();
		
	}

	public boolean tieneTurno() {
		
		return this.esMiTurno;
		
	}

	public void pasarTiempo() {

		this.nacion.pasarTiempo();
		
	}
	
	public int getCantidadDeMinerales(){
		
		return this.nacion.getMinerales();
		
	}
	
	public int getCantidadDeGasVespeno(){
		
		return this.nacion.getGasVespeno();
		
	}
	
	public int getPoblacionDisponible(){
		
		return this.nacion.getPoblacionMaxima();
		
	}
	
}
