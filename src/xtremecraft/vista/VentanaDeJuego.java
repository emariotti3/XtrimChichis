package xtremecraft.vista;

//import java.awt.BorderLayout;
//import java.awt.Container;
//import java.awt.GridLayout;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;




//import xtremecraft.mapa.Mapa;
import xtremecraft.partida.Partida;

public class VentanaDeJuego extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public VentanaDeJuego(Partida partida, HashMap<Class<?>, Class<?>> vistas) throws InstantiationException, IllegalAccessException{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ALGO CRAFT");
		setPreferredSize(new Dimension(1200,800));
		setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
		//this.addMouseListener(this);
		
		
		JPanel panelPrincipal = new JPanel();
		//panelPrincipal.setVisible(true);
		
		
		MapaObservable mapaObservable = this.generarNuevoMapa(partida, vistas);
		mapaObservable.setPreferredSize(new Dimension(600,600));
		panelPrincipal.setVisible(true);
		//TODO: Trucho
		//agrego lista para probar panel:
		DefaultListModel<String> accionesPrueba = new DefaultListModel<String>();	
		accionesPrueba.addElement("Construir barraca");
		accionesPrueba.addElement("Construir deposito");
		accionesPrueba.addElement("Atacar");
		JPanel panelAccionesDisponibles = new AccionesDisponibles(accionesPrueba);
		panelPrincipal.setBorder(BorderFactory.createLineBorder(panelAccionesDisponibles.getBackground(),25));
		panelPrincipal.setBorder(BorderFactory.createLineBorder(mapaObservable.getBackground(),25));
		panelPrincipal.add(mapaObservable);
		
		panelPrincipal.add(panelAccionesDisponibles);
		panelPrincipal.add(mapaObservable);
		add(panelPrincipal);
		pack();// Tarea: Entender esto.

		
		
		setVisible(true);
		
	}

	private MapaObservable generarNuevoMapa(Partida partida, HashMap<Class<?>, Class<?>> vistas) throws InstantiationException, IllegalAccessException{
		
		return new MapaObservable(partida, vistas);
		
	}
}