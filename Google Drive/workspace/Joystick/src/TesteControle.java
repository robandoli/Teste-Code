import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;;


public class TesteControle extends JFrame{
	
	BufferStrategy bs;
	Controller[] cs;
	
	public TesteControle(){
	}
	
	public void initialize(){
		setTitle("Exemplo de uso do Jinput");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(1200, 1200);
		setIgnoreRepaint(true);
		setVisible(true);
		createBufferStrategy(2);
		bs = getBufferStrategy();
		
		ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();
		cs = ce.getControllers();
	}
	
	public void run(){
		initialize();
	
		while(true){
			update();
			render();
		}
	}
	
	public void update(){
		for (int i = 0; i <cs.length; i++) {
			cs[i].poll();
		}
	}
	
	public void render(){
		Graphics2D g = ( Graphics2D ) bs.getDrawGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		
        for (int i = 0; i < cs.length; i++) {
            g.drawString(i + ". " + cs[i].getName() + ", " + cs[i].getType(), 50, 80 + i * 20);
            Component[] cmps = cs[i].getComponents();
            for (int c = 0; c < cmps.length; c++) {
                g.drawString(c + ". " + cmps[c].getName() + " = " + cmps[c].getPollData(), 50 + i * 200, 180 + c * 20);
            }
        }
        
        g.dispose();
        bs.show();
	}
	
	public static void main(String[] args) {
		TesteControle jogo = new TesteControle();
		jogo.run();
	}
}
