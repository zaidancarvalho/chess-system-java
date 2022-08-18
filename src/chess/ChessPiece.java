package chess;

import boardgame.Board;
import boardgame.Piece;

//subclasse de Piece

public class ChessPiece extends Piece{
	
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	
	//está sendo deixada apenas o get porque não quero que a cor de uma peça seja modificada, apenas acessada
	public Color getColor() {
		return color;
	}	
}
