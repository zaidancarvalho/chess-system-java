package chess;

import boardgame.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if(column < 'a' || column > 'h' || row < 1 || row > 8) { //programação defensiva
			throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}
	

	//foi apagado os sets porque não posso deixar que a coluna e a linha sejam livremente alteradas
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	//retornar uma nova posição 
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	//precisa me retornar a formula inversa do position
	protected static ChessPosition fromPosition(Position position) {
				//necessário realizar um casting para char porque o java não faz automaticamente
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow() );
	}
	
	
	//é colocado as aspas para forçar o compilador a enteder
	//que isso é uma concatneção de strings
	@Override
	public String toString() {
		return "" + column + row;
	}
	
}
