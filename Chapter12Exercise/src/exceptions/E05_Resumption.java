package exceptions;
/**
 * 使用while循环建立类似“恢复模型”的异常处理行为，它将不断重复，直到异常不再抛出
 * @author wwr
 *
 */

class ResumerException extends Exception{}

class Resumer{
	static int count = 3;
	static void f() throws ResumerException {
		if(--count > 0) {
			throw new ResumerException();
		}
	}
}

public class E05_Resumption {
	public static void main(String[] args) {
		while(true) {
			try {
				Resumer.f();
			} catch (ResumerException e) {
				System.out.println("Caught" + e);
				continue;
			}
			System.out.println("Got through");
			break;
		}
		System.out.println("Success Exception");
	}
}
