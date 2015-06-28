package xtremecraft.vista;

//import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
//import javax.swing.JFrame;
import javax.swing.JPanel;

import xtremecraft.mapa.Celda;
import xtremecraft.mapa.Mapa;
import xtremecraft.mapa.Terreno;
import xtremecraft.partida.Identificable;
import xtremecraft.partida.Partida;

public class MapaObservable extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = 7787529771808926374L;
	
	private Mapa modeloReal;
	private HashMap<Class<?>, Class<?>> vistas;
	
	public MapaObservable(){};
	
	//public MapaObservable(Mapa mapa, int x, int y){
	public MapaObservable(Partida partida, HashMap<Class<?>, Class<?>> vistas) throws InstantiationException, IllegalAccessException{
		
		//this.addMouseListener(this);
		Mapa mapa = partida.getMapa();
		this.modeloReal = mapa;
		this.vistas = vistas;
		setBounds(mapa.ancho(), mapa.alto(), 800, 800);
		this.setLayout(new GridLayout(mapa.ancho(), mapa.alto()));
		
				
		TreeMap<Integer, TreeMap<Integer, Celda>> mapaIterable = this.modeloReal.devolverMapaEstatico();
		for (int i = 0; i < this.modeloReal.ancho(); i++){
			for (int j = 0; j < this.modeloReal.alto(); j++){
				Celda celda = mapaIterable.get(i).get(j);
				Terreno terrenoInferior = celda.getCapaInferior();
				Class<?> vistaClase = null;
				Vista vistaNueva = null;
				
				if(terrenoInferior.estaOcupado()){
					Identificable identificable = (Identificable)terrenoInferior.getUbicableEnTerreno();
					int numero =identificable.getJugador().getNumeroDeJugador();
					vistaClase = this.vistas.get(terrenoInferior.getUbicableEnTerreno().getClass());
					IdentificableVisible identificableVisible = (IdentificableVisible) vistaClase.newInstance();
					identificableVisible.setJugador(numero);
					vistaNueva = (Vista) identificableVisible;
				}else{
					if (!terrenoInferior.tieneRecursos()){
						vistaClase = this.vistas.get(terrenoInferior.getClass());
						vistaNueva = (Vista) vistaClase.newInstance();
					}else{
						vistaClase = this.vistas.get(terrenoInferior.getRecurso().getClass());
						vistaNueva = (Vista) vistaClase.newInstance();
					}
				}

				vistaNueva.setCoordenada(terrenoInferior.getCoordenada());

				vistaNueva.paintComponents(getGraphics());
				add(vistaNueva);
				
			}
		}
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Vista vistaSeleccionada = (Vista) this.findComponentAt(e.getX(), e.getY());
		
		/*Coordenada ubicacion = vistaSeleccionada.getCoordenada();
		this.modeloReal.getCeldaEnFilaColumna(ubicacion.fila(), ubicacion.columna());*/
		
		ArrayList<String> opciones = vistaSeleccionada.mostrarOpcionesAccion();
		
		JPanel panel = new JPanel();
		JLabel mensaje = new JLabel("Elija la opcion que desee:");
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox menuOpciones = new JComboBox((ComboBoxModel) opciones);
		
		panel.add(mensaje);		
		
		panel.add(menuOpciones);
		
		add(panel);
		panel.setVisible(true);
		
		setVisible(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseClicked(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseClicked(e);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}