package innerclasses;
/**
 * 创建一个接口U，它包含三个方法。
 * 创建第一个类A，它包含一个方法，在此方法中通过创建一个匿名内部类，来生成指向U的引用。
 * 创建第二个类B，它包含一个由U构成的数组。
 * 			B应该有几个方法，第一个方法可以接受对U的引用并存储到数组中；
 * 						 第二个方法将数组中的引用设为null；
 * 					     第三个方法遍历此数组，并在U中调用这些方法。
 * 在main中创建一组A的对象和一个B的对象。
 * 用那些A类对象所产生的U类型的引用填充B对象的数组。
 * 使用B回调所有A的对象。再从B中移除某些U的引用。
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
