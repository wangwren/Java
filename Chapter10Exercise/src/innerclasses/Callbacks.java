package innerclasses;

interface Incrementable{
	void increment();
}

class Callee1 implements Incrementable{

	private int i = 0;
	
	@Override
	public void increment() {
		i++;
		System.out.println(i);
	}
}

class MyIncrement{
	public void increment() {
		System.out.println("Other operation");
	}
	static void f(MyIncrement mi) {
		mi.increment();
	}
}

class Callee2 extends MyIncrement{
	private int i = 0;
	public void increment() {
		super.increment();
		i++;
		System.out.println(i);
	}
	
	//Callee2的内部类，并实现Incrementable接口
	private class Closure implements Incrementable{

		@Override
		public void increment() {
			Callee2.this.increment();	//调用的是Callee2的increment方法
		}
	}
	
	Incrementable getCallbackReference() {
		return new Closure();
	}
}

class Caller{
	private Incrementable callbackReference;
	Caller(Incrementable cbh){
		callbackReference = cbh;
	}
	void go() {
		callbackReference.increment();
	}
}

public class Callbacks {

	public static void main(String[] args) {
		Callee1 c1 = new Callee1();
		Callee2 c2 = new Callee2();
		
		MyIncrement.f(c2);
		
		Caller caller1 = new Caller(c1);
		Caller caller2 = new Caller(c2.getCallbackReference());
		
		caller1.go();
		caller1.go();
		caller2.go();	//caller2 获得的是Closure，该类是Callee2的内部类，并且Closure实现了Incrementable接口
		caller2.go();
	}
}

/*
 *输出结果
 *
 *Other operation 	--->>MyIncrement.f(c2);
	1				--->>MyIncrement.f(c2);
	1				--->>caller1.go();
	2				--->>caller1.go();
	Other operation	--->>caller2.go();
	2				--->>caller2.go();	由于Closure是c2调用生成的，其中increment()方法，
										又是使用Callee2.this 获得到的当前对象即c2，c2中的i在之前已经被调用过了，当时i为1，所以这里又调用了一遍，此时i为2
	Other operation --->>caller2.go();
	3				--->>caller2.go();
 *
 */
