package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.RenderPanel;

@SuppressWarnings("serial")
class DummyDomain extends JFrame {
	
	Level debugLevel;
	RenderPanel debugRenderer;
	
	DummyDomain() {
		debugLevel = new Level(new Map(12,12), new DummyPlayer(new Position<Integer>(0,0)), Animator.NONE);
		debugRenderer = new RenderPanel(debugLevel);
		
		setSize(400,400);
		add(debugRenderer);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Timer timer = new Timer(17, e -> tick());
		
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				timer.stop();
			}
		});
		setVisible(true);
		timer.start();
	}
	
	public void tick() {
		debugLevel.tick();
		debugRenderer.tick();
		debugRenderer.repaint();
	}
	
	public static void main(String... args) {
		System.setProperty("sun.java2d.opengl", "true");
		SwingUtilities.invokeLater(DummyDomain::new);
	}
}