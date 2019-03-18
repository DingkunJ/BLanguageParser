package Exception;

public class NotAVarException extends Exception{

	/**
	 * 规定 该内容不是一个变量为 2号异常
	 * 即 在本该是变量的位置 存在了非法的字符
	 */
	private static final long serialVersionUID = 2L;

	public NotAVarException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotAVarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NotAVarException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotAVarException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotAVarException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}