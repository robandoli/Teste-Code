package br.com.blockade_letrinhas.lgc_game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.blockade_letrinhas.lgc_base.BackgroundStandart;

public class Game extends JFrame{
	
	private static final int FPS = 1000 / 20;

	private static final int JANELA_ALTURA = 490;

	private static final int JANELA_LARGURA = 490;

	private JPanel tela;

	private Graphics2D g2d;

	private BufferedImage buffer;

	private BackgroundStandart background;
	
	private float valorControle;
	
	public enum Tecla{
		CIMA, BAIXO, ESQUERDA, DIREITA, ESPACO, ESCAPE
	}
	
	public static boolean[] controleTecla = new boolean[Tecla.values().length];
	
	public static void liberaTeclas(){
		for (int i = 0; i < controleTecla.length; i++) {
			controleTecla[i] = false;
		}
	}
	
	private void setaTecla(int tecla, boolean pressionada) {
		switch (tecla) {
		case KeyEvent.VK_UP:
			controleTecla[Tecla.CIMA.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_DOWN:
			controleTecla[Tecla.BAIXO.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_LEFT:
			controleTecla[Tecla.ESQUERDA.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_RIGHT:
			controleTecla[Tecla.DIREITA.ordinal()] = pressionada;
			break;

		case KeyEvent.VK_ESCAPE:
			controleTecla[Tecla.ESCAPE.ordinal()] = pressionada;
			break;

		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_ENTER:
			controleTecla[Tecla.ESPACO.ordinal()] = pressionada;
		}
	}

	public Game(){
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				setaTecla(e.getKeyCode(), false);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				setaTecla(e.getKeyCode(), true);
			}
		});		
		
		buffer = new BufferedImage(JANELA_LARGURA, JANELA_ALTURA, BufferedImage.TYPE_INT_RGB);
		
		g2d = buffer.createGraphics();
		
		tela = new JPanel(){
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(buffer, 0, 0, null);
			}
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(JANELA_LARGURA, JANELA_ALTURA);
			}

			@Override
			public Dimension getMinimumSize() {
				return getPreferredSize();
			}
		};
		
		getContentPane().add( tela );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		
		setVisible(true);
		tela.repaint();
	}
	
	private void load(){
		
	}
	
	public void startGame(){
		long prxUpdate = 0;
		
		while (true) {
			
			if(System.currentTimeMillis() >= prxUpdate){
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0, JANELA_LARGURA, JANELA_ALTURA);
				
				if(!(background instanceof BackgroundOfGame)){
					background = new BackgroundOfGame(tela.getWidth(), tela.getHeight());
					background.load();
				}
				
				long t1 = System.currentTimeMillis();
				
				background.update();
				background.paint(g2d);
				
				long t2 = System.currentTimeMillis();
				
				tela.repaint();
				
				Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
				
				Controller tipoControle = null;
				
				for (int i = 0; i < controllers.length && tipoControle == null; i++) {
					if (controllers[i].getType() == Controller.Type.GAMEPAD) {
						tipoControle = controllers[i];
						tipoControle.poll();
						
						Component[] controles = tipoControle.getComponents();
						
						for (int j = 0; j < controles.length; j++) {
							Component controle = controles[j];
							
							if(controle.getIdentifier().toString() == "pov"){
								valorControle = controle.getPollData();
								if( valorControle == 1)
									controleTecla[Tecla.ESQUERDA.ordinal()] = true;
									
								if( valorControle >= 0.25 && valorControle <0.5)
									controleTecla[Tecla.CIMA.ordinal()] = true;
								
								if( valorControle >= 0.5 && valorControle < 0.75)
									controleTecla[Tecla.DIREITA.ordinal()] = true;
								
								if( valorControle >= 0.75 && valorControle < 1)
									controleTecla[Tecla.BAIXO.ordinal()] = true;
							} else{
								
								liberaTeclas();
							}
						}	
					}	
				}
				
				prxUpdate = System.currentTimeMillis() + FPS;
				
			}
			
		}
	}
	
	
	public static void main(String[] args) {
		Game jogo = new Game();
		jogo.startGame();
	}

}
