package xtremecraft.unidades;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import xtremecraft.mapa.Aire;
import xtremecraft.mapa.Terreno;
import xtremecraft.mapa.Tierra;

public class GoliatTest {
	
	@Test
	public void goliatInicializadoConVidaCompleta(){

		Goliat tanque1 = new Goliat();
		
		assertEquals(tanque1.getVida(),125);
		
	}
	
	@Test
	public void goliatInicializadoConEstadoVivo(){

		Goliat tanque1 = new Goliat();
		
		assertTrue(tanque1.estaVivo());
		
	}
	
	@Test
	public void goliatPuedeUbicarseSobreRecursoNaturalDevuelveFalse(){

		Goliat tanque1 = new Goliat();
		
		assertFalse(tanque1.puedeUbicarseSobreRecursoNatural());
	}
	
	@Test
	public void goliatGetVisionDevuelveRadioDeVisionDelGoliat(){

		Goliat tanque1 = new Goliat();
		
		assertEquals(tanque1.getRadioVision(),8);
	}
	
	@Test
	public void siUnGoliatAtacaAUnEspectroPorAireLeSacaDiezDeVida(){

		Terreno tierra = new Tierra(1,3);
		Terreno aire = new Aire(1,4);
		Goliat tanque1 = new Goliat();
		Espectro tanque2 = new Espectro();
		
		tanque1.actualizarUbicacion(tierra);
		tanque2.actualizarUbicacion(aire);
		tanque1.atacar(tanque2);
		
		assertEquals(tanque2.vitalidad.getValor(),110);
	}

	@Test
	public void siUnGoliatAtacaAOtroPorTierraLeSacaDoceDeVida(){

		Terreno tierra1 = new Tierra(1,2);
		Terreno tierra2 = new Tierra(2,3);
		Goliat tanque1 = new Goliat();
		Goliat tanque2 = new Goliat();
		
		tanque1.actualizarUbicacion(tierra1);
		tanque2.actualizarUbicacion(tierra2);
		tanque1.atacar(tanque2);
		
		assertEquals(tanque2.vitalidad.getValor(),113);
	}
	
	
	@Test
	public void siUnGoliatAtacaAOtroFueraDeSuRangoNoLeHaceDaño(){

		Terreno tierra = new Tierra(1,4);
		Terreno aire = new Aire(10,10);
		Goliat tanque1 = new Goliat();
		Goliat tanque2 = new Goliat();
		
		tanque1.actualizarUbicacion(tierra);
		tanque2.actualizarUbicacion(aire);
		tanque1.atacar(tanque2);
		
		assertEquals(tanque2.vitalidad.getValor(),125);
	}
	
	
	@Test
	public void siUnGoliatAtacaAOtroPorTierraHastaMatarloSuVidaQuedaEnCero(){

		Terreno tierra1 = new Tierra(1,2);
		Terreno tierra2 = new Tierra(2,3);
		Goliat tanque1 = new Goliat();
		Goliat tanque2 = new Goliat();
		
		tanque1.actualizarUbicacion(tierra1);
		tanque2.actualizarUbicacion(tierra2);
		for (int i = 0; i < 11; i++){
			tanque1.atacar(tanque2);
		}
		
		assertEquals(tanque2.vitalidad.getValor(), 0);
	}
	
	@Test
	public void siUnGoliatAtacaAOtroPorTierraHastaMatarloPasaAEstadoNoVivo(){

		Terreno tierra1 = new Tierra(1,2);
		Terreno tierra2 = new Tierra(2,3);
		Goliat tanque1 = new Goliat();
		Goliat tanque2 = new Goliat();
		
		tanque1.actualizarUbicacion(tierra1);
		tanque2.actualizarUbicacion(tierra2);
		for (int i = 0; i < 11; i++){
			tanque1.atacar(tanque2);
		}
		
		assertFalse(tanque2.estaVivo());
		
	}
	
	@Test
	public void moverACeldaCambiaLasCoordenadasActualesDeLaUnidad(){
		
		Terreno tierra1 = new Tierra(1,2);
		Terreno tierra2 = new Tierra(2,3);
		Goliat tanque1 = new Goliat();
		
		tanque1.actualizarUbicacion(tierra1);

		assertEquals(tanque1.getUbicacionActual().fila(),1);
		assertEquals(tanque1.getUbicacionActual().columna(),2);
		
		tanque1.actualizarUbicacion(tierra2);
		
		assertEquals(tanque1.getUbicacionActual().fila(),2);
		assertEquals(tanque1.getUbicacionActual().columna(),3);
		
	}

}

