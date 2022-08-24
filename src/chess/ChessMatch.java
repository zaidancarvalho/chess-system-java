package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	//uma partida de xadrez tem que ter um tabuleiro
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check; //por padrão já inicia como falso, pode colocar no construtor para enfatizazr (false)
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	//aqui está sendo aplicado a delegação, minha classe ChessMatch que vai dizer que meu tabuleiro precisa ser 8 por 8
	public ChessMatch() {
		board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	//criando o get pra ter acesso no meu programa principal
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
	
	//esse método serev para poder imprimir posições possíveis a partir da uma posição de origem
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position); // validar a posição depois que o usuário entrar com ela
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece perfomChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source,target);
		Piece capturedPiece = makeMove(source, target); //depois de executar o movimento precisa testar se o próprio jogador se colocou em check e isso não pode acontecer
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if (testCheckMate(opponent(currentPlayer))) { //se a jogada que eu fiz deixou o meu oponenete em cheque mate, o jogo vai ter que terminar
			checkMate = true;
		}
		else {
			nextTurn();
		}
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if (capturedPiece != null) {// contar as peças capturadas
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}
	
	//desfazendo o movimento
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if (capturedPiece != null) { // vai voltar para o tabuleiro na posição de destino
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	//validação da posição de origem
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}					//downcasting
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) // significa que é uma peça do adversário, eu não posso mover
			throw new ChessException("The chosen piece is not yours");
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	//troca os turnos
	private void nextTurn() {
		turn++; //incrementando o turno
		//se o jogador atual for igual a color.white, então, ele agora vai ser o color.balck, caso contrario, ele vai ser o color.white
		currentPlayer = (currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE);
	}
	
	//devolver o oponente de uma cor
	private Color opponent(Color color) {
		//se essa cor que passei como argumento ela for igual a Color.white, então, eu vou retornar o color.black, caso contrário, vou retornar o color.white
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	//localizar um rei de uma determinada cor
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); //forma padrão de filtar uma lista
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p; //downcasting
			}
		}
		//se acontecer essa exceção tem algum problema no sistema de xadrez
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition(); // pegando a posição do rei na forma de matriz
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves(); // matriz de movimentos possiveis dessa peça adversária p
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); //pegando todas as peças dessa cor (color)
		for(Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i=0; i<board.getRows(); i++) { // percorrendo as linhas da matriz
				for (int j=0; j<board.getColumns(); j++) { // percorrendo a coluna da matriz
					if (mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target); // realizou o movimento para fazer um teste
						boolean testCheck = testCheck(color);// testar se o rei da minha cor ainda está em cheque
						undoMove(source, target, capturedPiece); // agora desfazendo o movimento
						if (!testCheck) { //se não estava em cheque significa que esse movimento tirou o rei do cheque
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	
	//agora vai ser as coordenadas do sistema do xadrez e não no sistema da matriz
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	//agora está sendo adicionado mais peças
	private void initialSetup() {
		placeNewPiece('h', 7, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
		
		placeNewPiece('b', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 8, new King(board, Color.BLACK));
	}
}
