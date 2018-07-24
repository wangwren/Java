package exercise1;

/**
 * 创建一个类，它包含一个int域和一个char域，
 * 它们都没有被初始化，将它们的值打印出来，
 * 以验证Java执行了默认初始化
 * @author wwr
 *
 */
public class E01_DefaultInitialization {

	int i;
	char c;
	
	//通过构造函数来输出
	public E01_DefaultInitialization() {
		
		System.out.println("i : " + i);
		System.out.println("c : " + "[" + c + "]");
	}
	
	public static void main(String[] args) {
		
		new E01_DefaultInitialization();
	}
	
	/*
	 * i : 0
	 * c : [ ]
	 */
}
