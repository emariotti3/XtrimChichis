package xtremecraft.unidades;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import xtremecraft.edificios.Barraca;
import xtremecraft.edificios.Fabrica;
import xtremecraft.edificios.PuertoEstelar;
import xtremecraft.mapa.Aire;
import xtremecraft.mapa.Mapa;
import xtremecraft.mapa.Terreno;
import xtremecraft.mapa.Tierra;
import xtremecraft.partida.Jugador;
import xtremecraft.partida.SeleccionadoNoEsPropiedadDelJugadorException;
import xtremecraft.raza.RecursosInsuficientesException;
import xtremecraft.raza.Terran;

public class NaveCienciaTest {
	
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
	public void unidadCreadaParaRazaSinRecursosLanzaExcepcion(){

		int fila = 15;
		int columna = 16;
		Mapa mapa = new Mapa(2);
		Tierra tierra = (Tierra) mapa.getCeldaEnFilaColumna(fila, columna).getCapaInferior();
		Jugador jugador = new Jugador("Juan", tierra, mapa);
		
		new NaveCiencia(jugador);
	}
	
	@Test
	public void naveCienciaSeInicializaConBarraDeVidaCompleta(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		assertEquals(naveCiencia.getVida(),200);
	
	}
	
	@Test
	public void naveCienciaInicializadoConEstadoVivo(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		assertTrue(naveCiencia.estaVivo());
		
	}
	
	@Test
	public void naveCienciaPuedeUbicarseSobreRecursoNaturalDevuelveFalse(){
	
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		assertFalse(naveCiencia.puedeUbicarseSobreRecursoNatural());
	
	}
	
	@Test
	public void naveCienciaSeInicializaConRadioDeVision(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		assertEquals(naveCiencia.getRadioVision(),10);
	
	}
	
	@Test
	public void naveCienciaSeInicializaConEnergia(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		assertEquals(naveCiencia.getEnergia(),50);
	
	}
	
	@Test
	public void aumentarEnergiaEnTurnoAumentaNivelDeEnergia(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		naveCiencia.aumentarEnergiaEnTurno();
		
		assertEquals(naveCiencia.getEnergia(),60);
			
	}
	
	@Test
	public void aumentarEnergiaEnTurnoSiEnergiaEsMayorOIgualADoscientosNoAumentaNivelDeEnergia(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		for(int i=0;i<15;i++){
			naveCiencia.aumentarEnergiaEnTurno();
		}
		
		naveCiencia.aumentarEnergiaEnTurno();
		
		assertEquals(naveCiencia.getEnergia(),200);
	
	}
	
	@Test
	public void actualizarUbicacionCambiaLasCoordenadasActualesDeLaUnidad(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno unTerreno=new Aire(1,4);
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		naveCiencia.setUbicacionInicial(unTerreno);
		
		assertEquals(naveCiencia.getUbicacionActual(),unTerreno.getUbicacionActual());
		
	}
	
	@Test
	public void actualizarUbicacionLiberaElTerrenoDeUbicacionAnterior(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno aire1 = new Aire(1,2);
		Terreno aire2 = new Aire(2,3);
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		naveCiencia.setUbicacionInicial(aire1);
		naveCiencia.actualizarUbicacion(aire2);

		assertFalse(aire1.estaOcupado());
		
	}
	
	@Test
	public void actualizarUbicacionOcupaElNuevoTerreno(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno aire1 = new Aire(1,2);
		Terreno aire2 = new Aire(2,3);
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		naveCiencia.setUbicacionInicial(aire1);
		naveCiencia.actualizarUbicacion(aire2);

		assertTrue(aire2.estaOcupado());
		
	}
	
	@Test(expected = UbicacionNoValidaException.class)
	public void siLaUnidadTrataDeUbicarseEnUnTerrenoQueNoPuedeVerSeLanzaExcepcion(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno aire = new Aire(1,2);
		Terreno aireDestino = new Aire(20,20);
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		naveCiencia.setUbicacionInicial(aire);
		naveCiencia.actualizarUbicacion(aireDestino);
		
	}
	
	@Test (expected = NaveFueraDelRangoDeVisionUnidadException.class)
	public void subirANaveDeTransporteLanzaNaveFueraDelRangoDeVisionExceptionSiLaNaveNoEstaDentroDelRangoDeVision(){

		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Terreno tierra = new Tierra(1,1);
		Terreno otraTierra = new Tierra(35,47);
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		NaveTransporte nave = new NaveTransporte(jugador);
		
		naveCiencia.setUbicacionInicial(tierra);
		nave.setUbicacionInicial(otraTierra);
		
		nave.transportarNuevaUnidad(naveCiencia);
				
	}
	
	@Test
	public void atacarConRadiacionLeSacaEnergiaALaNaveAtacante(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Mapa mapa = new Mapa(2);
		Terreno aire = mapa.getCeldaEnFilaColumna(5,5).getCapaSuperior();
		Terreno tierra = mapa.getCeldaEnFilaColumna(6,6).getCapaInferior();
		NaveCiencia naveCienciaAtacante = new NaveCiencia(jugador);
		Goliat goliatAtacado = new Goliat(jugador);
		int energiaInicialNaveAtacante = naveCienciaAtacante.getEnergia();
		
		for (int tiempo=0;tiempo<450;tiempo++) goliatAtacado.pasarTiempo();
		
		naveCienciaAtacante.setUbicacionInicial(aire);
		goliatAtacado.setUbicacionInicial(tierra);
		naveCienciaAtacante.atacarConRadiacion(mapa,goliatAtacado);
		
		assertTrue(energiaInicialNaveAtacante > naveCienciaAtacante.getEnergia());
				
	}
	
	@Test(expected = AtaqueFueraDelRangoDeVisionException.class)
	public void siSeIntentaAtacarConRadiacionAUnaUnidadFueraDelRangoDeVisionSeLanzaExcepcion(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Mapa mapa = new Mapa(2);
		Terreno aire = mapa.getCeldaEnFilaColumna(5,5).getCapaSuperior();
		Terreno tierra = mapa.getCeldaEnFilaColumna(20,18).getCapaInferior();
		NaveCiencia naveCienciaAtacante = new NaveCiencia(jugador);
		Goliat goliatAtacado = new Goliat(jugador);
		
		naveCienciaAtacante.setUbicacionInicial(aire);
		goliatAtacado.setUbicacionInicial(tierra);
		naveCienciaAtacante.atacarConRadiacion(mapa,goliatAtacado);
				
	}
	
	@Test
	public void recibirAtaqueRadiacionDejaALaUnidadEnEstadoRadioactivo(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Mapa mapa = new Mapa(2);
		Terreno tierra = mapa.getCeldaEnFilaColumna(6,6).getCapaInferior();
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		
		naveCiencia.setUbicacionInicial(tierra);
		for(int tiempo=0;tiempo<naveCiencia.tiempoConstruccion();tiempo++) naveCiencia.pasarTiempo();
		Radiacion radiacion = new Radiacion(mapa, 2, 10);
		naveCiencia.recibirDanio(radiacion);
		
		assertTrue(naveCiencia.esRadioactivo());
		
	}

	@Test
	public void atacarConRadiacionHaceQueLaUnidadAtacadaMueraDespuesDePasarUnTiempo(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Mapa mapa = new Mapa(2);
		Terreno aire = mapa.getCeldaEnFilaColumna(5,5).getCapaSuperior();
		Terreno tierra = mapa.getCeldaEnFilaColumna(5,6).getCapaInferior();
		NaveCiencia naveCienciaAtacante = new NaveCiencia(jugador);
		Goliat goliatAtacado = new Goliat(jugador);
		
		
		goliatAtacado.setUbicacionInicial(tierra);
		naveCienciaAtacante.setUbicacionInicial(aire);
		for(int tiempo=0;tiempo<goliatAtacado.tiempoConstruccion();tiempo++) goliatAtacado.pasarTiempo();
		int tiempoMuerteUnidad = (int)(goliatAtacado.getVida()/10);
		naveCienciaAtacante.atacarConRadiacion(mapa,goliatAtacado);
		for(int tiempo=0;tiempo<tiempoMuerteUnidad;tiempo++) goliatAtacado.pasarTiempo();
		
		assertEquals(goliatAtacado.getVida(),0);
				
	}
	
	@Test
	public void atacarConRadiacionHaceQueLasUnidadesADistanciaMenorOIgualAUnoDeLaAfectadaRecibanDanio() throws SeleccionadoNoEsPropiedadDelJugadorException{
		//TODO: ver que pasa con emitir radiacion cuando hago goliatAtacado.pasarTurno();
		Jugador jugador1 = crearJugadorConRecursosSuficientesParaConstruir();
		Jugador jugador2 = crearJugadorConRecursosSuficientesParaConstruir();
		Mapa mapa = new Mapa(2);
		Terreno tierra1 = mapa.getCeldaEnFilaColumna(1, 2).getCapaInferior();
		Terreno tierra2 = mapa.getCeldaEnFilaColumna(3, 3).getCapaInferior();
		Terreno tierra3 = mapa.getCeldaEnFilaColumna(4, 4).getCapaInferior();
		Terreno tierra4 = mapa.getCeldaEnFilaColumna(5, 5).getCapaInferior();
		Terreno tierra5 = mapa.getCeldaEnFilaColumna(9, 9).getCapaInferior();
		Terreno tierra6 = mapa.getCeldaEnFilaColumna(4, 6).getCapaInferior();
		Terreno tierra7 = mapa.getCeldaEnFilaColumna(10, 11).getCapaInferior();
		Terreno tierra8 = mapa.getCeldaEnFilaColumna(10, 10).getCapaInferior();
		Terreno tierra9 = mapa.getCeldaEnFilaColumna(11, 10).getCapaSuperior();
		
		Barraca barracaJugador1 = jugador1.crearBarraca(tierra1, mapa);
		Barraca barracaJugador2 = jugador2.crearBarraca(tierra4, mapa);
		for (int tiempo=0;tiempo<barracaJugador1.tiempoConstruccion();tiempo++) barracaJugador1.pasarTiempo();
		for (int tiempo=0;tiempo<barracaJugador2.tiempoConstruccion();tiempo++) barracaJugador2.pasarTiempo();
		
		Fabrica fabricaJugador1 = jugador1.crearFabrica(tierra2, mapa);
		Fabrica fabricaJugador2 = jugador2.crearFabrica(tierra5, mapa);
		for (int tiempo=0;tiempo<fabricaJugador1.tiempoConstruccion();tiempo++) fabricaJugador1.pasarTiempo();
		for (int tiempo=0;tiempo<fabricaJugador2.tiempoConstruccion();tiempo++) fabricaJugador2.pasarTiempo();
		
		PuertoEstelar puertoEstelarJugador1 = jugador1.crearPuertoEstelar(tierra3, mapa);	
		PuertoEstelar puertoEstelarJugador2 = jugador2.crearPuertoEstelar(tierra6, mapa);	
		for (int tiempo=0;tiempo<puertoEstelarJugador1.tiempoConstruccion();tiempo++) puertoEstelarJugador1.pasarTiempo();
		for (int tiempo=0;tiempo<puertoEstelarJugador2.tiempoConstruccion();tiempo++) puertoEstelarJugador2.pasarTiempo();
		
		NaveCiencia naveCienciaAtacante = jugador1.crearNaveCiencia(puertoEstelarJugador1, mapa);
		Goliat goliatAtacado = jugador2.crearGoliat(fabricaJugador2, mapa);
		Goliat goliatIrradiado = jugador2.crearGoliat(fabricaJugador2, mapa);
		
		naveCienciaAtacante.actualizarUbicacion(tierra9);
		goliatAtacado.actualizarUbicacion(tierra7);
		goliatIrradiado.actualizarUbicacion(tierra8);		
		
		int vidaInicialGoliatIrradiado = goliatIrradiado.getVida();
		for (int tiempo=0;tiempo<450;tiempo++) goliatAtacado.pasarTiempo();
		for (int tiempo=0;tiempo<450;tiempo++) goliatIrradiado.pasarTiempo();
		
		Radiacion radiacion = new Radiacion(mapa, 2 , 10);
		goliatAtacado.recibirDanio(radiacion);
		radiacion.emitirRadiacion(goliatAtacado);
		
		assertTrue(vidaInicialGoliatIrradiado > goliatIrradiado.getVida());
				
	}
	
	@Test
	public void recibirAtaqueRadiacionLeProduceDanioALaUnidadAfectada(){
		
		Jugador jugador = crearJugadorConRecursosSuficientesParaConstruir();
		Mapa mapa = new Mapa(2);
		Terreno tierra = mapa.getCeldaEnFilaColumna(6,6).getCapaInferior();
		NaveCiencia naveCiencia = new NaveCiencia(jugador);
		int vidaInicial = naveCiencia.getVida();
		
		naveCiencia.setUbicacionInicial(tierra);
		for(int tiempo=0;tiempo<naveCiencia.tiempoConstruccion();tiempo++) naveCiencia.pasarTiempo();
		Radiacion radiacion = new Radiacion(mapa, 2, 10);
		naveCiencia.recibirDanio(radiacion);
		
		assertEquals((vidaInicial-10), naveCiencia.getVida());
		
	}
	
}
