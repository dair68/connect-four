package connectfour;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ConnectFourMouseListener implements MouseListener, MouseMotionListener {
	//mouse key pressed down
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed");
	}
	
	//mouse key released
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("Mouse released");
	}
	
	//mouse clicked
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse clicked (" + e.getX() + ", " + e.getY() + ")");
	}
	
	//mouse entered
	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("Mouse entered");
	}
	
	//mouse exited
	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Mouse exited");
	}
	
	//overriding MouseMotionListener methods
	
	//everytime mouse moves
	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("Mouse moved to (" + e.getX() + ", " + e.getY() + ")");
	}
	
	//everytime mouse dragged
	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("Mouse dragged");
	}
}
