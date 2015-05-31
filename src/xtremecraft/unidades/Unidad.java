package xtremecraft.unidades;

public abstract class Unidad {
	BarraDeVitalidad vitalidad;
	int transporte;
	int costoM;
	int costoG;
	int tiempoConstruccion;
	int daño;
	int suministro;
	
    public void recibirDaño(int daño){
        vitalidad.recibirAtaque(daño);
    	
    }
    
    public void atacar (Unidad otraUnidad){
    	otraUnidad.recibirDaño(this.daño);
    }

}
