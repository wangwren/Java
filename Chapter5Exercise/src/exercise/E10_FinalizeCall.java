package exercise;

public class E10_FinalizeCall {

	@Override
	protected void finalize() throws Throwable {
		System.out.println("finalize() called");
	}

	public static void main(String[] args) {
		
		new E10_FinalizeCall();
	}
}
