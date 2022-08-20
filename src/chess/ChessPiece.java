package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

//subclasse de Piece

public abstract class ChessPiece extends Piece{
	
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	
	//está sendo deixada apenas o get porque não quero que a cor de uma peça seja modificada, apenas acessada
	public Color getColor() {
		return color;
	}	
	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
}
