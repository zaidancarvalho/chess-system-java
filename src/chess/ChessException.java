package chess;

import boardgame.BoardException;

//agora pode captarar exceções de chessException e BoardException
public class ChessException extends BoardException{
	private static final long serialVersionUID = 1L;

	public ChessException(String msg) {
		super(msg);
	}
}
