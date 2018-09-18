package exceptions;
/**
 * 使用extends关键字建立一个自定义异常类。为这个类写一个接受字符串参数的构造器，
 * 把此参数保存在对象内部的字符串引用中。写一个方法显示此字符串。写一个try-catch子句，对这个新异常进行测试。
 * @author wwr
 *
 */

class MyException extends Exception{
	String msg;
	public MyException(String msg) {
		this.msg = msg;
	}
	
	public void printMsg() {
		System.out.println(msg);
	}
}

class MyException2 extends Exception{
	public MyException2(String s) {
		super(s);
	}
}


public class E04_ExceptionClass {

	public static void main(String[] args) {
		try {
			throw new MyException("MyException message");
		}catch(MyException e) {
			e.printMsg();
		}
		
		try {
			throw new MyException2("MyException2 message");
		}catch(MyException2 e){
			System.out.println("e.getMessgae() = " + e.getMessage());
		}
	}
}
