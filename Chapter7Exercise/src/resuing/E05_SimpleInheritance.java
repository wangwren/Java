package resuing;
/**
 * ������������Ĭ�Ϲ����������������б�����A����B
 * ��A�м̳в���һ����ΪC�����࣬����C�ڴ���һ��B��ĳ�Ա����Ҫ��C��д��������
 * ����һ��C����󲢹۲�����
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
