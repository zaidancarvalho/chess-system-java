package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		//lógica para movimentar as peças
		while (!chessMatch.getCheck()) {//enquanto a minha partida não estiver com chque mate, vai continuar rodando o meu programa
			try {
				UI.clearScreen();//limpando a tela a cada vez que voltar o while
				UI.printMatch(chessMatch, captured);//lista está como argumento
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.perfomChessMove(source, target);
				
				if (capturedPiece != null) {
					captured.add(capturedPiece);//adicionando na lista capituradas
				}
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();//faz com que o programa aguarde até apertar enter
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen(); //limpar a tela
		UI.printMatch(chessMatch, captured);// imprimir novamente a partida para visualizar a finalização da mesma
	}
	

}
