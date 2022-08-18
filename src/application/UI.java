package application;

import chess.ChessPiece;

//função para imprimir as peças da partida
public class UI {
	
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i=0; i<pieces.length; i++) { //pieces.lenght considerando que a matriz vai ser quadrada
			System.out.print((8 - i) + " "); //irá imprimir o 1,2,3,etc e mais um espaço em branco
			for (int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j]);
			}
			System.out.println(); // quebra de linha 
		}
		System.out.println("  a b c d e f g h");
		
	}
	
	//método auxiliar para imprimir uma peça
	private static void printPiece(ChessPiece piece) {
		if (piece == null) { // não tem peça nesse tabuleiro
			System.out.print("-"); // imprimir um traço
		}
		else {
			System.out.print(piece);// caso ao contrário imprimir a peça
		}
		System.out.print(" ");//para que não fiquem uma gruada uma nas  outras
	}
}
