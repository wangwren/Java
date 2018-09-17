package exceptions;

class SimpleException extends Exception{}

public class InherintingExceptions {
	public void f() throws SimpleException {
		System.out.println("Throw SimpleException from f()");
		throw new SimpleException();
	}
	public static void main(String[] args) {
		InherintingExceptions sed = new InherintingExceptions();
		try {
			sed.f();
		}catch (SimpleException e) {
			System.out.println("Caught it!");
		}
	}
}
