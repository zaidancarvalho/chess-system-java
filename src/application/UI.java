package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

//função para imprimir as peças da partida
public class UI {
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	//cores dos textos
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	//cores do fundo
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	//limpando o console
	public static void clearScreen() { 
		 System.out.print("\033[H\033[2J"); 
		 System.out.flush(); 
	}
	
	//scanner vai receber do programa princial e aqui está sendo recebido como argumento
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0); //para ler a letra
			int row = Integer.parseInt(s.substring(1)); // para ler o número preciso recortar
			return new ChessPosition(column, row);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
		}
	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.println("Turn : " + chessMatch.getTurn());
		if (!chessMatch.getCheckMate()) {
			System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
			if (chessMatch.getCheck()) {
				System.out.println("CHECK!");
			}
		}
		else {
			System.out.println("CHECKMATE!");
			System.out.println("Winner: " + chessMatch.getCurrentPlayer());
		}
	}
	
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i=0; i < pieces.length; i++) { //pieces.lenght considerando que a matriz vai ser quadrada
			System.out.print((8 - i) + " "); //irá imprimir o 8,7,6 etc e mais um espaço em branco
			for (int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println(); // quebra de linha 
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j=0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);//irá pintar o fundo colorido dependendo dessa variável
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPiece(ChessPiece piece, boolean background) {
		if (background) { //fazendo um boolean para saber se é v ou f para mudar de cor
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
    	if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {//utilizando cores para as peças
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	//esse método vai receber uma lista de peças de xadrez e vai ser responsável por imprimir na tela essa lista de peças
	private static void printCapturedPieces(List<ChessPiece> captured) {
		//filtrando da  minha lista todos cuja a cor seja branca
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());//filtrando a lista
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		System.out.println("Captured pieces: ");
		System.out.print("White: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray())); //jeito padrão de imprimir um array de valores
		System.out.print(ANSI_RESET);
		System.out.print("Black: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);

	}
}
