package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	//vai retornar a minha matriz na linha row e na columa column
	public Piece piece(int row, int column) {
		return pieces [row][column];
	}
	
	//realizado uma sobrecarga, então, ele vai retornar o pieces na position.getrow e position .getcolumn
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}
	
	//esse método vai ter que ir na matriz de peçs na linha e na coluna 
	//depois vai ter que atribuir a essa posição da minha matriz de peças a peça que veio como argumento
	public void placePiece(Piece piece, Position position) {
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
}
