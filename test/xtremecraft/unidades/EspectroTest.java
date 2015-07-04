package xtremecraft.unidades;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import xtremecraft.mapa.Aire;
import xtremecraft.mapa.Mapa;
import xtremecraft.mapa.Terreno;
import xtremecraft.mapa.Tierra;
import xtremecraft.partida.Jugador;
import xtremecraft.raza.RecursosInsuficientesException;
import xtremecraft.raza.Terran;

public class EspectroTest {

	public Jugador crearJugadorConRecursosSuficientesParaConstruir(){
		
		int fila = 15;
		int columna = 16;
		Mapa mapa = new Mapa(2);
		Tierra tierra = (Tierra) mapa.getCeldaEnFilaColumna(fila, columna).getCapaInferior();
		Jugador jugador = new Jugador("Juan", tierra, mapa);
		Terran razaTerran = jugador.nacion();
		razaTerran.juntarGas(1000);
		razaTerran.juntarMinerales(1000);
		return jugador;
		
	}
	
	@Test (expected = RecursosInsuficientesException.class)
	public void espectroCreadoParaRazaSinRecursosLanzaExcepcion(){

		int fila = 15;
		int columna = 16;
		Mapa mapa = new Mapa(2);
		Tierra tierra = (Tierra) mapa.getCeldaEnFilaColumna(fila, columna).getCapaInferior();
		Jugador jugador = new Jugador("Juan", tierra, mapa);
		
		new Espectro(jugador);
		
	}
	
	@Test
	public void espectroInicializadoConVidaCompleta(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Espectro gengar = new Espectro(jugador);
		
		assertEquals(gengar.getVida(),120);
	}
	
	@Test
	public void espectroInicializadoConEstadoVivo(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Espectro gengar = new Espectro(jugador);
		
		assertTrue(gengar.estaVivo());
		
	}
	
	@Test
	public void espectroPuedeUbicarseSobreRecursoNaturalDevuelveFalse(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Espectro gengar = new Espectro(jugador);
		
		assertFalse(gengar.puedeUbicarseSobreRecursoNatural());
		
	}
	
	@Test
	public void espectroGetVisionDevuelveRadioDeVisionDelEspectro(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Espectro gengar = new Espectro(jugador);
		
		assertEquals(gengar.getRadioVision(),7);
	}
	
	@Test
	public void siUnEspectroAtacaAOtroPorAireLeSacaVeinteDeVida(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno aire = new Aire(1,2);
		Terreno tierra = new Tierra(2,3);
		Espectro gengar = new Espectro(jugador);
		Espectro misdreavus = new Espectro(jugador);
		
		gengar.setUbicacionInicial(tierra);
		misdreavus.setUbicacionInicial(aire);
		
		for (int i = 0; i < gengar.tiempoConstruccion(); i++){
			gengar.pasarTiempo();
			misdreavus.pasarTiempo();
		}
		
		gengar.atacar(misdreavus);
		
		assertEquals(misdreavus.vitalidad.getValor(),100);
	}

	@Test
	public void siUnEspectroAtacaAOtroPorTierraLeSacaOchoDeVida(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno tierra1 = new Tierra(1,2);
		Terreno tierra2 = new Tierra(2,3);
		Espectro gengar = new Espectro(jugador);
		Espectro misdreavus = new Espectro(jugador);
		
		gengar.setUbicacionInicial(tierra1);
		misdreavus.setUbicacionInicial(tierra2);
		
		for (int i = 0; i < gengar.tiempoConstruccion(); i++){
			gengar.pasarTiempo();
			misdreavus.pasarTiempo();
		}
		
		gengar.atacar(misdreavus);
		
		assertEquals(misdreavus.vitalidad.getValor(),112);
	}
	
	
	@Test(expected = AtaqueFueraDelRangoDeVisionException.class)
	public void siUnEspectroAtacaAOtroFueraDeSuRangoSeLanzaAtaqueFueraDelRangoDeVisionException(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno unTerreno = new Aire(1,4);
		Terreno otroTerreno = new Tierra(10,10);
		Espectro gengar = new Espectro(jugador);
		Espectro misdreavus = new Espectro(jugador);
		
		gengar.setUbicacionInicial(unTerreno);
		misdreavus.setUbicacionInicial(otroTerreno);
		
		for (int i = 0; i < gengar.tiempoConstruccion(); i++){
			gengar.pasarTiempo();
			misdreavus.pasarTiempo();
		}
		
		gengar.atacar(misdreavus);
		
	}
	
	
	@Test
	public void siUnEspectroAtacaAOtroPorTierraHastaMatarloSuVidaQuedaEnCero(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno unTerreno = new Aire(1,1);
		Terreno otroTerreno = new Tierra(1,2);
		Espectro gengar = new Espectro(jugador);
		Espectro misdreavus = new Espectro(jugador);
		
		gengar.setUbicacionInicial(unTerreno);
		misdreavus.setUbicacionInicial(otroTerreno);
		
		for (int i = 0; i < gengar.tiempoConstruccion(); i++){
			gengar.pasarTiempo();
			misdreavus.pasarTiempo();
		}
		
		for (int i = 0; i < 16; i++){
			gengar.atacar(misdreavus);
			gengar.pasarTiempo();
		}
		
		assertEquals(misdreavus.vitalidad.getValor(), 0);
	}
	
	@Test
	public void siUnEspectroAtacaAOtroPorTierraHastaMatarloPasaAEstadoNoVivo(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno unTerreno = new Aire(1,1);
		Terreno otroTerreno = new Tierra(1,2);
		Espectro gengar = new Espectro(jugador);
		Espectro misdreavus = new Espectro(jugador);
		
		gengar.setUbicacionInicial(unTerreno);
		misdreavus.setUbicacionInicial(otroTerreno);
		
		for (int i = 0; i < gengar.tiempoConstruccion(); i++){
			gengar.pasarTiempo();
			misdreavus.pasarTiempo();
		}
		
		for (int i = 0; i < 16; i++){
			gengar.atacar(misdreavus);
			gengar.pasarTiempo();
		}
		
		assertFalse(misdreavus.estaVivo());
		
	}
		
	@Test
	public void actualizarUbicacionCambiaLasCoordenadasActualesDeLaUnidad(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno unTerreno=new Aire(1,4);
		
		Espectro unEspectro = new Espectro(jugador);
		
		unEspectro.setUbicacionInicial(unTerreno);
		
		assertEquals(unEspectro.getUbicacionActual(),unTerreno.getUbicacionActual());
		
	}
	
	@Test
	public void actualizarUbicacionLiberaElTerrenoDeUbicacionAnterior(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno aire1 = new Aire(1,2);
		Terreno aire2 = new Aire(2,3);
		Espectro unEspectro = new Espectro(jugador);
		
		unEspectro.setUbicacionInicial(aire1);
		unEspectro.actualizarUbicacion(aire2);

		assertFalse(aire1.estaOcupado());
		
	}
	
	@Test
	public void actualizarUbicacionOcupaElNuevoTerreno(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno aire1 = new Aire(1,2);
		Terreno aire2 = new Aire(2,3);
		Espectro unEspectro = new Espectro(jugador);
		
		unEspectro.setUbicacionInicial(aire1);
		unEspectro.actualizarUbicacion(aire2);

		assertTrue(aire2.estaOcupado());
		
	}
	
	@Test (expected = NaveFueraDelRangoDeVisionUnidadException.class)
	public void subirANaveDeTransporteLanzaNaveFueraDelRangoDeVisionUnidadExceptionSiNaveNoEstaDentroDelRangoDeVision(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno tierra = new Tierra(1,2);
		Terreno otraTierra = new Tierra(35,40);
		Espectro gengar = new Espectro(jugador);
		NaveTransporte nave = new NaveTransporte(jugador);
		
		gengar.setUbicacionInicial(tierra);
		nave.setUbicacionInicial(otraTierra);
		
		nave.transportarNuevaUnidad(gengar);
				
	}
	
	@Test
	public void recibirAtaqueRadiacionLeProduceDanioALaUnidadAfectada(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Mapa mapa = new Mapa(2);
		Terreno tierra = mapa.getCeldaEnFilaColumna(6,6).getCapaInferior();
		Espectro gengar = new Espectro(jugador);
		int vidaInicial = gengar.getVida();
		
		gengar.setUbicacionInicial(tierra);
		for(int tiempo=0;tiempo<gengar.tiempoConstruccion();tiempo++) gengar.pasarTiempo();
		Radiacion radiacion = new Radiacion(mapa, 2, 10);
		gengar.recibirDanio(radiacion);
		
		assertEquals((vidaInicial-10), gengar.getVida());
		
	}
	
	@Test
	public void recibirAtaqueRadiacionDejaALaUnidadEnEstadoRadioactivo(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Mapa mapa = new Mapa(2);
		Terreno tierra = mapa.getCeldaEnFilaColumna(6,6).getCapaInferior();
		Espectro gengar = new Espectro(jugador);
		
		gengar.setUbicacionInicial(tierra);
		for(int tiempo=0;tiempo<gengar.tiempoConstruccion();tiempo++) gengar.pasarTiempo();
		Radiacion radiacion = new Radiacion(mapa, 2, 10);
		gengar.recibirDanio(radiacion);
		
		assertTrue(gengar.esRadioactivo());
		
	}

}
