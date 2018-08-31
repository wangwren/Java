package innerclasses;

public class Parcel9 {
	
	/*
	 *  Java 编程思想用的是 Java 5 的编译器。
	 *  在 Java 8 之前的所有版本的 Java，
	 *  局部内部类和匿名内部类访问的局部变量必须由final修饰，
	 *  java8开始，可以不加final修饰符，由系统默认添加。
	 *  java将这个功能称为：Effectively final 功能。
	 */
	public Destination destination(final String dest) {//java8 可以不加final
		
		return new Destination() {
			private String label = dest;
			@Override
			public String readLabel() {
				return label;
			}
		};
	}
	public static void main(String[] args) {
		Parcel9 p = new Parcel9();
		Destination d = p.destination("Tasmania");
	}
}
