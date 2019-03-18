package Exception;

public class WrongGrammaticalException extends Exception {

	/**
	 * 规定 语法错误为 3号异常
	 * 即 出现了不符合规定的语法
	 */
	private static final long serialVersionUID = 3L;

	public WrongGrammaticalException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WrongGrammaticalException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public WrongGrammaticalException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WrongGrammaticalException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WrongGrammaticalException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
