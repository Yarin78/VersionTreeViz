public class LayoutException extends RuntimeException {
	public LayoutException() {
	}

	public LayoutException(String s) {
		super(s);
	}

	public LayoutException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public LayoutException(Throwable throwable) {
		super(throwable);
	}
}
