package xtremecraft.mapa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import xtremecraft.mapa.Tierra;
import xtremecraft.recursos.MinaDeMinerales;
import xtremecraft.unidades.Goliat;
import xtremecraft.unidades.Unidad;

import org.junit.Test;

public class TierraTest {

	@Test
	public void nuevaTierraCreaTierraConEstadoNoOcupado(){
		
		Terreno terreno = new Tierra(1,1);
		
		assertFalse(terreno.estaOcupada());
		
	}
	
	@Test
	public void agregarRecursoNaturalDeberiaDevolverTrueSiSeAgregaUnaMinaDeMinerales(){
		
		Terreno terreno = new Tierra(1,1);
		MinaDeMinerales mina = new MinaDeMinerales(3);
		
		assertTrue(terreno.agregarRecursoNatural(mina));
		
	}
	
	@Test
	public void tierraEstaOcupadaDeberiaDevolverTrueLuegoDeOcuparla(){
		
		Terreno terreno=new Tierra(1,1);
		Unidad goliat= new Goliat(terreno);
		terreno.ubicar(goliat);
		
		assertTrue(terreno.estaOcupada());
		
	}
	
	@Test
	public void ocuparTerrenoConUnidadGuardaLaUnidadEnLaterreno(){
		
		Terreno terreno=new Tierra(1,1);
		Unidad goliat= new Goliat(terreno);	
	
		terreno.ubicar(goliat);
		Unidad unidad = (Unidad) terreno.getUbicableEnTerreno();
		int vida = unidad.getVida();
		
		assertEquals(vida,125);
		
	}
	
	@Test
	public void terrenoTieneRecursoNaturalDeberiaDevolverTrueSiGuardoUnMineralEnEseTerreno(){
		
		Tierra tierra=new Tierra(1,1);
		MinaDeMinerales nodoMineral=new MinaDeMinerales(5);
		tierra.ocuparConRecursoNatural(nodoMineral);
		
		assertTrue(tierra.tieneRecursosNaturales());
		assertEquals(tierra.getRecurso(),nodoMineral);
		
	}
	
}
