package boardgame;

									//exceção opcional para ser tratada
public class BoardException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//passando essa mensagem para o construtor da super classe que é o RuntimeException
	public BoardException(String msg) {
		super(msg);
	}
}
