package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

//subclasse de Piece

public abstract class ChessPiece extends Piece{
	
	private Color color;
	private int moveCount; // começa com 0, por isso não precisa declarar no construtor

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	
	//está sendo deixada apenas o get porque não quero que a cor de uma peça seja modificada, apenas acessada
	public Color getColor() {
		return color;
	}	
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	public ChessPosition getChessPosition() { 
		return ChessPosition.fromPosition(position); //chessposition retornando do fromposition
	}
	
	//está sendo realizada aqui porque vai ser reaproveitada em outras peças
	protected boolean isThereOpponentPiece(Position position) {
						//realizando um downcasting
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
}
