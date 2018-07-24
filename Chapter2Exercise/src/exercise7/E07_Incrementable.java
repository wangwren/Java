package exercise7;

public class E07_Incrementable {

	static void increment() {
		StaticTest.i++;
	}
	
	public static void main(String[] args) {
		
		E07_Incrementable e07_Incrementable = new E07_Incrementable();
		e07_Incrementable.increment();
		
		E07_Incrementable.increment();
		
		increment();
		
	}
	
}


class StaticTest{
	static int i = 47;
}
