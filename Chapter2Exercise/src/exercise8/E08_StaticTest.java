package exercise8;

/**
 * 编写一个程序，
 * 展示无论你创建了某个特定类的多少个对象，
 * 这个类中的某个特定的static域只有一个实例
 *
 * @author wwr
 *
 */
public class E08_StaticTest {

	static int i =1;
	
	public static void main(String[] args) {
		
		E08_StaticTest staticTest1 = new E08_StaticTest();
		E08_StaticTest staticTest2 = new E08_StaticTest();
		
		staticTest1.i++;
		System.out.println(staticTest1.i + " = " + staticTest2.i);
		
		
		staticTest1.i++;
		System.out.println(staticTest1.i + " = " + staticTest2.i);
	}
}
