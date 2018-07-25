package exercise;

public class ShowPropreties {

	public static void main(String[] args) {
		
		//System.getProperties()方法返回一个properties对象
		//list()方法就是Properties对象中的方法
		//list()方法作用就是，将属性列表输出到指定的输出流
		System.getProperties().list(System.out);
		System.out.println(System.getProperty("user.name"));
		System.out.println(System.getProperty("java.library.path"));
		
	}
}
