package resuing;
/**
 * 创建两个带有默认构造器（不含参数列表）的类A和类B
 * 从A中继承产生一个名为C的新类，并在C内创建一个B类的成员，不要给C编写构造器。
 * 创建一个C类对象并观察其结果
 * @author wwr
 *
 */
class A{
	public A() {
		System.out.println("A()");
	}
}

class B{
	public B() {
		System.out.println("B()");
	}
}

class C extends A{
	B b = new B();
}


public class E05_SimpleInheritance {

	public static void main(String[] args) {
		new C();
	}
}
