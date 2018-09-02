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
	
	//Callee2���ڲ��࣬��ʵ��Incrementable�ӿ�
	private class Closure implements Incrementable{

		@Override
		public void increment() {
			Callee2.this.increment();	//���õ���Callee2��increment����
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
		caller2.go();	//caller2 ��õ���Closure��������Callee2���ڲ��࣬����Closureʵ����Incrementable�ӿ�
		caller2.go();
	}
}

/*
 *������
 *
 *Other operation 	--->>MyIncrement.f(c2);
	1				--->>MyIncrement.f(c2);
	1				--->>caller1.go();
	2				--->>caller1.go();
	Other operation	--->>caller2.go();
	2				--->>caller2.go();	����Closure��c2�������ɵģ�����increment()������
										����ʹ��Callee2.this ��õ��ĵ�ǰ����c2��c2�е�i��֮ǰ�Ѿ������ù��ˣ���ʱiΪ1�����������ֵ�����һ�飬��ʱiΪ2
	Other operation --->>caller2.go();
	3				--->>caller2.go();
 *
 */
