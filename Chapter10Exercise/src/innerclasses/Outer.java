package innerclasses;
/**
 * ����һ�������ڲ�����࣬����һ�����������У��������ڲ����ʵ��
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
