package innerclasses;
/**
 * ����һ���ӿ�U������������������
 * ������һ����A��������һ���������ڴ˷�����ͨ������һ�������ڲ��࣬������ָ��U�����á�
 * �����ڶ�����B��������һ����U���ɵ����顣
 * 			BӦ���м�����������һ���������Խ��ܶ�U�����ò��洢�������У�
 * 						 �ڶ��������������е�������Ϊnull��
 * 					     �������������������飬����U�е�����Щ������
 * ��main�д���һ��A�Ķ����һ��B�Ķ���
 * ����ЩA�������������U���͵��������B��������顣
 * ʹ��B�ص�����A�Ķ����ٴ�B���Ƴ�ĳЩU�����á�
 * @author wwr
 *
 */

interface U{
	void f();
	void g();
	void h();
}

class A{
	public U getU() {
		return new U() {

			@Override
			public void f() {
				System.out.println("A.f");
			}

			@Override
			public void g() {
				System.out.println("A.g");
			}

			@Override
			public void h() {
				System.out.println("A.h");
			}
		};
	}
}

class B{
	U[] us;
	public B(int size) {
		us = new U[size];
	}
	
	public boolean add(U elem) {
		for(int i = 0;i < us.length;i++) {
			if(us[i] == null) {
				us[i] = elem;
				return true;
			}
		}
		return false;
	}
	
	public boolean setNull(int i) {
		if(i < 0 || i >= us.length) {
			return false;
		}
		us[i] = null;
		return true;
	}
	
	public void cellMethods() {
		for(int i = 0;i < us.length;i++) {
			if(us[i] != null) {
				us[i].f();
				us[i].g();
				us[i].h();
			}
		}
	}
}

public class E23_UAB {
	public static void main(String[] args) {
		A[] aa = {new A(),new A(),new A()};
		B bb = new B(3);
		for(int i = 0;i < aa.length;i++) {
			bb.add(aa[i].getU());
		}
		bb.cellMethods();
		System.out.println("**********");
		bb.setNull(0);
		bb.cellMethods();
	}
}
