package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1 ) { //programação defensiva 
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	//foi retirada os set de Rows e Columns para não ser mudadas 
	//as quantidades de linhas e colunas desse Board (Programação defensiva)
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	
	//vai retornar a minha matriz na linha row e na columa column
	public Piece piece(int row, int column) {
		if( !positionExists(row, column)) { //programação defensiva
			throw new BoardException("Position not on the board");
		}
		return pieces [row][column];
	}
	
	
	//realizado uma sobrecarga, então, ele vai retornar o pieces na position.getrow e position .getcolumn
	public Piece piece(Position position) {
		if(!positionExists(position)) { //programação defensiva
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	
	//esse método vai ter que ir na matriz de peçs na linha e na coluna 
	//depois vai ter que atribuir a essa posição da minha matriz de peças a peça que veio como argumento
	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) { //programação defensiva
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	//removendo a peça
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		if (piece(position) == null) {
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	//método auxiliar porque vai ser mais fácil de testar pela linha e coluna do que pela posição
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	//testando para saber se a posição existe
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) { //programação defensiva
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null; //significa que tem uma peça nessa posição
	}
}
