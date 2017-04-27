package br.com.blockade_letrinhas.lgc_game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.blockade_letrinhas.lgc_base.BackgroundStandart;

public class Game extends JFrame{
	
	private static final int FPS = 1000 / 20;

	private static final int JANELA_ALTURA = 450;

	private static final int JANELA_LARGURA = 450;

	private JPanel tela;

	private Graphics2D g2d;

	private BufferedImage buffer;

	private BackgroundStandart background;

	
	public Game(){
		
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
				
				background.update();
				background.paint(g2d);
				
				tela.repaint();
				prxUpdate = System.currentTimeMillis() + FPS;
			}
		}
	}
	
	
	public static void main(String[] args) {
		Game jogo = new Game();
		jogo.startGame();
	}

}
