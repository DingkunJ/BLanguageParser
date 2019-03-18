package Exception;

public class NotSuchVarException extends Exception{
	
	/**
	 * 规定 变量未定义为 1号异常
	 * 即 使用的变量未定义
	 */
	private static final long serialVersionUID = 1L;

	public NotSuchVarException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotSuchVarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NotSuchVarException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotSuchVarException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotSuchVarException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}