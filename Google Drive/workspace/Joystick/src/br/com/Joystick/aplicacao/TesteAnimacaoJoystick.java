package br.com.Joystick.aplicacao;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TesteAnimacaoJoystick extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel tela;
	private float px;
	private float py;
	private Point mouseClick = new Point();
	private boolean jogando = true;

	private final int FPS = 1000 / 20; // 50
	
	public TesteAnimacaoJoystick(){
		tela = new JPanel(){
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g){
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, tela.getWidth(), tela.getHeight());
				
				int x = (int) px - 20;
				int y = (int) py - 20;
				
				g.setColor(Color.BLUE);
				g.fillRect(x, y, 40, 40);
				g.drawString("Agora eu estou em " + x + "x" + y, 5, 10);
			}
		};
		
		
		/*tela.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			  
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseClick = e.getPoint();
			}
		});*/
		
		getContentPane().add(tela);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640,480);
		setVisible(true);
	
	}
	
	private void searchForController(){
		
	}
	
	public void inicia(){
		long prxAtualizando = 0;
		
		while(jogando){
			if(System.currentTimeMillis() >= prxAtualizando){
				searchForController();
				atualizaJogo();
				tela.repaint();
				
				prxAtualizando = System.currentTimeMillis() + FPS;
			}
		}
	}
	
	public void atualizaJogo(){
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		
		Controller joystick = null;
		
		for (int i = 0; i < controllers.length && joystick == null; i++) {
			if (controllers[i].getType() == Controller.Type.GAMEPAD) {
				joystick = controllers[i];
				joystick.poll();
				
				Component[] components = joystick.getComponents();
				
				for (int j = 0; j < components.length; j++) {
					Component component = components[j];
					
					if(component.getIdentifier().toString() == "pov"){
						float valor = component.getPollData();
						
						if( valor == 1){
							px = px - 1;
							//py = py - 1;
						}
						
						if( valor >= 0.25 && valor <0.5){
							//px = px + 1;
							py = py - 1;
						}
						
						if( valor >= 0.5 && valor < 0.75){
							px = px + 1;
							//py = py + 1;
						}
						
						if( valor >= 0.75 && valor < 1){
							//px = px - 1;
							py = py + 1;
						}
						
					}
					
				}
				
			}	
		}
	}
	
	public static void main(String[] args){
		TesteAnimacaoJoystick jogo = new TesteAnimacaoJoystick();
		jogo.inicia();
	}
}
