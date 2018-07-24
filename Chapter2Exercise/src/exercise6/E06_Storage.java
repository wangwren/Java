package exercise6;

/**
 * 编写一个程序，
 * 让它含有本章所定义的storage()方法的代码段，并调用
 * @author wwr
 *
 */
public class E06_Storage {

	int storage(String s) {
		return s.length() * 2;
	}
	
	public static void main(String[] args) {
		
		E06_Storage e06_Storage = new E06_Storage();
		System.out.println(e06_Storage.storage("Hello World!"));
	}
	
	/*
	 * 官方答案
	 * public class E06_Storage {
		 String s = "Hello, World!";
		 int storage(String s) {
		 return s.length() * 2;
		 }
		 void print() {
		 System.out.println("storage(s) = " + storage(s));
		 }
		 public static void main(String[] args) {
		 E06_Storage st = new E06_Storage();
		 st.print();
		 }
		} /* Output:
		storage(s) = 26
		*/
}
