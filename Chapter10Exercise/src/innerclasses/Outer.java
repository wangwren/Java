package innerclasses;
/**
 * 创建一个包含内部类的类，在另一个独立的类中，创建此内部类的实例
 * @author wwr
 *
 */
public class Outer {

	public class Inner{
		public Inner() {
			System.out.println("Inner created");
		}
	}
	
	public static void main(String[] args) {
		Outer outer = new Outer();
		Outer.Inner inner = outer.new Inner();
	}
}
