package shell.tetris;

public class Element {
	private int[][] matrix; 	/*!< Main matrix of an element which changes its dimensions if needed because of rotations and compression state */
	private int type;			/*!< Final type of an element  */
	private int height;			/*!< Total height of an element  */
	private int width;			/*!< Total width of an element */
	private int density;		/*!< Final density of an element */
	private int compressed;		/*!< State of compression (true/false) */
	private int positionX;		/*!< Value is the distance of an element from the left edge of the board */
	private int positionY;		/*!< Value is the distance of an element from the top edge of the board */
	
	Element(int posX, int posY, int density, int rType) throws Exception {
		this.positionX = posX;
		this.positionY = posY;
		this.setDensity(density);
		this.setType(rType);
		if (this.type < 1 || this.type > 6) {
			throw new Exception("ID must be inside [1,6] interval");
		} else {
			switch (this.type) {
				case 1:
					setMatrix(new int[][] {
						{
							this.type
						}, {
							this.type
						}, {
							this.type
						}, {
							this.type
						}
					});
					setHeight(4);
					setWidth(1);
					break;
				case 2:
					setMatrix(new int[][] {
						{
							this.type, this.type
						}, {
							this.type, this.type
						}
					});
					setHeight(2);
					setWidth(2);
					break;
				case 3:
					setMatrix(new int[][] {
						{
							0, this.type, this.type
						}, {
							this.type, this.type, 0
						}
					});
					setHeight(2);
					setWidth(3);
					break;
				case 4:
					setMatrix(new int[][] {
						{
							this.type, this.type, 0
						}, {
							0, this.type, this.type
						}
					});
					setHeight(2);
					setWidth(3);
					break;
				case 5:
					setHeight(3);
					setWidth(2);
					setMatrix(new int[][] {
						{
							this.type, 0
						}, {
							this.type, 0
						}, {
							this.type, this.type
						}
					});
					break;
				case 6:
					setHeight(3);
					setWidth(2);
					setMatrix(new int[][] {
						{
							0, this.type
						}, {
							0, this.type
						}, {
							this.type, this.type
						}
					});
					break;
				default:
					setHeight(2);
					setWidth(2);
					setMatrix(new int[][] {
						{
							this.type, this.type
						}, {
							this.type, this.type
						}
					});
					break;
			}
			this.setCompressed(1);
			this.decompressMatrix();
		}
	}
	public void decompressMatrix() {
		if (this.getCompressed() == 1) {
			int[][] tmpMatrix = new int[this.getHeight()][this.getWidth() * this.getDensity()];
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					for (int k = 0; k < this.getDensity(); k++) {
						tmpMatrix[i][j * this.getDensity() + k] = matrix[i][j];
					}
				}
			}
			this.matrix = tmpMatrix;
			this.setWidth(this.getWidth() * this.getDensity());
			this.setCompressed(0);
		}
	}
	public void compressMatrix() {
		if (this.getCompressed() == 0) {
			int[][] tmpMatrix = new int[this.getHeight()][this.getWidth() / this.getDensity()];
			for (int i = 0; i < tmpMatrix.length; i++) {
				for (int j = 0; j < tmpMatrix[0].length; j++) {
					tmpMatrix[i][j] = matrix[i][j * this.getDensity()];
				}
			}
			this.matrix = tmpMatrix;
			this.setWidth(this.getWidth() / this.getDensity());
			this.setCompressed(1);
		}
	}
	public int getDensity() {
		return density;
	}
	public void setDensity(int density) {
		this.density = density;
	}
	
	public void setPosition(int posX, int posY) {
		this.positionX = posX;
		this.positionY = posY;
	}
	public int getPositionX() {
		return this.positionX;
	}
	public int getPositionY() {
		return this.positionY;
	}
	public void incPositionX() {
		this.positionX++;
	}
	public void decPositionX() {
		this.positionX--;
	}
	public void incPositionY() {
		this.positionY++;
	}
	public void decPositionY() {
		this.positionY--;
	}
	public void setPositionX(int num) {
		this.positionX = num;
	}
	public void setPositionY(int num) {
		this.positionY = num;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public void rotateRight() {
		this.compressMatrix();
		int[][] tmp = new int[this.getWidth()][this.getHeight()];
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				tmp[j][tmp[0].length - i - 1] = matrix[i][j];
			}
		}
		matrix = tmp;
		int tmpHW = this.getHeight();
		this.setHeight(this.getWidth());
		this.setWidth(tmpHW);
		this.decompressMatrix();
	}
	
	public void rotateLeft() {
		this.compressMatrix();
		int[][] tmp = new int[this.getWidth()][this.getHeight()];
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				tmp[tmp.length - j - 1][i] = matrix[i][j];
			}
		}
		matrix = tmp;
		int tmpHW = this.getHeight();
		this.setHeight(this.getWidth());
		this.setWidth(tmpHW);
		this.decompressMatrix();
	}
	public int getCompressed() {
		return compressed;
	}
	public void setCompressed(int compressed) {
		this.compressed = compressed;
	}
	public void print() {
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[0].length; j++) {
				System.out.print(this.matrix[i][j]);
			}
			System.out.print("\n");
		}
	}
}