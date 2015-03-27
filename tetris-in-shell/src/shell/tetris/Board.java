package shell.tetris;

import java.util.Random;


/**
 * Class for variable density tetris board that can instantiate new elements with
 * all the movement, rotations and their preconditions checks
 */
public class Board {
	int[][] matrix; 	/*!< Main result matrix  */
	int[][] bMatrix;	/*!< Blank matrix before lock  */
	int height;			/*!< Final height dimension */
	int width;			/*!< Final width without density modifier taken into account */
	int density;		/*!< Final density modifier */
	Element element;	/*!< Placeholder for the current element that can be manipulated and is to be locked into the matrix*/
	int nextType;		/*!< Defines type of a next element  */
	Random r;
	/**
	 * Variable board element constructor.
	 */
	Board(int h, int w, int d) {
		this.setHeight(h);
		this.setWidth(w);
		this.setDensity(d);
	
		
		matrix = new int[this.getHeight()][this.getWidth() * this.getDensity()];
		this.setbMatrix(matrix);
		
		this.r = new Random();
		this.setNextType(r.nextInt(6)+1);		
	}

	/**
	 * Matrix getter
	 */
	public int[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * Backup matrix getter
	 */
	public int[][] getbMatrix() {
		return bMatrix;
	}
	
	/**
	 * Backup matrix setter
	 */
	public void setbMatrix(int[][] bMatrix) {
		this.bMatrix = bMatrix;
	}

	/**
	 * Get type of next element
	 */
	public int getNextType() {
		return nextType;
	}

	/**
	 * Set type of next element
	 */
	public void setNextType(int nextType) {
		this.nextType = nextType;
	}

	/**
	 * Basic getter for tetris board height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Final setter for board height
	 */
	private void setHeight(int height) {
		this.height = height;
	}

	/**
	 *  Bassic getter for board width (without density taken into account)
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Final setter for board width 
	 */
	private void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Basic setter for density
	 */
	private void setDensity(int density) {
		this.density = density;
	}

	/**
	 * Basic getter for density
	 */
	public int getDensity() {
		return density;
	}

	/**
	 * Print current state to std out
	 */
	public void print() {
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth() * this.getDensity(); j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * Instatiate new element to the board
	 */
	public void newElement() {
		try {
	
			element = new Element(this.getWidth() * this.getDensity() / 2, 0, this.getDensity(), this.getNextType());
			this.setNextType(r.nextInt(6)+1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Reset element position to the top
	 */
	public void resetPosition() {
		this.element.setPositionX(0);
		this.element.setPositionY(0);
	}

	/**
	 * Permanently lock existing element
	 */
	public void lockElement() throws Exception {
		if (this.element.equals(null)) {
			throw new Exception("There is no initialized element to be locked");
		} else {
			for (int i = 0; i < this.element.getHeight(); i++) {
				for (int j = 0; j < this.element.getWidth(); j++) {
					//System.out.println("Prd");
					this.matrix[this.element.getPositionY() + i][this.element.getPositionX() + j] = this.matrix[this.element.getPositionY() + i][this.element.getPositionX() + j] + this.element.getMatrix()[i][j];
				}
			}
		}
		this.resetPosition();
	}

	/**
	 * 	Move element down for a step if it's possible
	 */
	public void moveElementDown() {
		int doMove = 1;
		if (this.element.getPositionY() < this.getHeight() - this.element.getHeight()) {
			for (int i = 0; i < this.element.getHeight(); i++) {
				for (int j = 0; j < this.element.getWidth(); j++) {
					if (this.element.getMatrix()[i][j] > 0 && this.matrix[this.element.getPositionY() + i + 1][this.element.getPositionX() + j] > 0) {
						doMove = 0;
					}
				}
			}
		} else {
			doMove = 0;
		}


		this.element.setPositionY(this.element.getPositionY() + doMove);
	}

	/**
	 * Move element up for a step without performing any check (unneeded)
	 */
	public void moveElementUp() {
		this.element.decPositionY();
		if (this.element.getPositionY() < 0) {
			this.element.setPositionY(0);
		}
	}

	/**
	 * Move element to the left if it's possible
	 */
	public void moveElementLeft() {
		int doMove = 1;
		if (this.element.getPositionX() > 0) {
			for (int i = 0; i < this.element.getHeight(); i++) {
				for (int j = 0; j < this.element.getWidth(); j++) {
					if (this.element.getMatrix()[i][j] > 0 && this.matrix[this.element.getPositionY() + i][this.element.getPositionX() + j - 1] > 0) {
						doMove = 0;
					}
				}
			}
		} else {
			doMove = 0;
		}


		this.element.setPositionX(this.element.getPositionX() - doMove);
		System.out.println("move left: " + doMove);
	}
	
	/**
	 * Move element to the right if it's possible
	 */
	public void moveElementRight() {
		int doMove = 1;
		if (this.element.getPositionX() < this.getWidth() * this.getDensity() - this.element.getWidth()) {
			for (int i = 0; i < this.element.getHeight(); i++) {
				for (int j = 0; j < this.element.getWidth(); j++) {
					if (this.element.getMatrix()[i][j] > 0 && this.matrix[this.element.getPositionY() + i][this.element.getPositionX() + j + 1] > 0) {
						doMove = 0;
					}
				}
			}
		} else {
			doMove = 0;
		}


		this.element.setPositionX(this.element.getPositionX() + doMove);
		//System.out.println("move right " + doMove + "( " + this.element.getPositionX() + ";" + this.getWidth() + "-" + this.element.getWidth() + ")");
	
	}

	/**
	 * Perform rotation to the right if it's possible
	 */
	public void rotateElementRight() {
		if (element != null) {
			this.element.rotateRight();
			int doRotate = 1;
			for (int i = 0; i < this.element.getHeight(); i++) {
				for (int j = 0; j < this.element.getWidth(); j++) {
					if (this.element.getMatrix()[i][j] != 0 && this.matrix[this.element.getPositionY() + i][this.element.getPositionX() + j] != 0) {
						doRotate = 0;
					}
				}
			}
	
			if (doRotate == 0) {
				this.element.rotateLeft();
			}
		}
	}

	/**
	 * Perform rotation to the left if it's possible
	 */
	public void rotateElementLeft() {
		if (element != null) {
			this.element.rotateLeft();
			int doRotate = 1;
			for (int i = 0; i < this.element.getHeight(); i++) {
				for (int j = 0; j < this.element.getWidth(); j++) {
					if (this.element.getMatrix()[i][j] != 0 && this.matrix[this.element.getPositionY() + i][this.element.getPositionX() + j] != 0) {
						doRotate = 0;
					}
				}
			}
	
			if (doRotate == 0) {
				this.element.rotateRight();
			}
		}
	
	}
}