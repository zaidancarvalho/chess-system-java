package chess;

import boardgame.Board;

public class ChessMatch {
	
	//uma partida de xadrez tem que ter um tabuleiro
	private Board board;
	
	//aqui está sendo aplicado a delegação, minha classe ChessMatch que vai dizer que meu tabuleiro precisa ser 8 por 8
	public ChessMatch() {
		board = new Board(8,8);
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat; 
	}
}
