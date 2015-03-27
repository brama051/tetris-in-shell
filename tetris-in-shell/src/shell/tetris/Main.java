package shell.tetris;


public class Main {

	public static void main(String[] args) {  
		Board ploca = new Board(10, 10, 4);
		
		ploca.newElement();
		
		ploca.element.rotateLeft();
		ploca.element.rotateLeft();
		
		
		int left = 0;
		int down = 10;
		int right = 50;
		
		for (int i = 0; i < left; i++) {
			ploca.moveElementLeft();
		}
		
		for (int i = 0; i < down; i++) {
			ploca.moveElementDown();
		}
		
		for (int i = 0; i < right; i++) {
			ploca.moveElementRight();
		}
	
		try {
			ploca.lockElement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ploca.newElement();
		
		left = 0;
		down = 10;
		right = 50;
		
		for (int i = 0; i < down; i++) {
			ploca.moveElementDown();
		}
		
		for (int i = 0; i < left; i++) {
			ploca.moveElementLeft();
		}
		
		for (int i = 0; i < right; i++) {
			ploca.moveElementRight();
		}
	
		try {
			ploca.lockElement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ploca.print();
		
		/*try {
			Element ele = new Element(0,0,2);
			
			ele.rotateRight();
			
			
			
			ele.print();
			System.out.println("wtf!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
	}
}
