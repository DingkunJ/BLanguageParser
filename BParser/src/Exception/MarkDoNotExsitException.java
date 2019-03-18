package Exception;

public class MarkDoNotExsitException extends Exception {

	/**
	 * 规定 goto语句的标签不存在为 4号异常
	 * 即 goto语句后的标签不合法
	 */
	private static final long serialVersionUID = 4L;

	public MarkDoNotExsitException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarkDoNotExsitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public MarkDoNotExsitException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MarkDoNotExsitException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MarkDoNotExsitException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}