package connectfour;

import java.awt.*; //importing Frame container

class GUI extends Frame {
	//GUI constructor
	GUI() {
		//invoking Frame constructor to specify window title
		super("Connect Four");
		
		this.setSize(600, 450);
		this.setVisible(true);
	}
	
}
