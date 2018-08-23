package interfaces.classprocessor;

import java.util.Arrays;

/**
 * 假设有一个Processor类，它有一个name()方法；
 * 另外还有一个process()方法，该方法接收输入参数，修改它的值，然后产生输出。
 * 这个类作为基类而被扩展，用来创建各种不同类型的Processor。
 * 
 * @author wwr
 *
 */

class Processor{
	public String name() {
		return getClass().getSimpleName();
	}
	Object process(Object input) {
		return input;
	}
}

class Upcase extends Processor{
	String process(Object input) {
		return ((String)input).toUpperCase();
	}
}

class Downcase extends Processor{
	String process(Object input) {
		return ((String)input).toLowerCase();
	}
}

class Splitter extends Processor{
	String process(Object input) {
		return Arrays.toString(((String)input).split(" "));
	}
}

public class Apply {

	public static void process(Processor p,Object s) {
		System.out.println("Using Processor " + p.name());
		System.out.println(p.process(p.process(s)));
	}
	public static String s = "Disagreement with beliefs is by definition incorrect";
	public static void main(String[] args) {
		process(new Upcase(),s);
		process(new Downcase(), s);
		process(new Splitter(), s);
	}
}
