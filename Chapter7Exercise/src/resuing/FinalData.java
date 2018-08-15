package resuing;

import java.util.Random;

class Value{
	int i;
	public Value(int i) {
		this.i = i;
	}
}

public class FinalData {

	private static Random rand = new Random(47);
	private String id;
	
	public FinalData(String id) {
		this.id = id;
	}
	
	private final int valueOne = 9;
	private static final int VALUE_TWO = 99;
	
	public static final int VALUE_THREE = 39;
	
	private final int i4 = rand.nextInt(20);
	static final int INT_5 = rand.nextInt(20);
	
	private Value v1 = new Value(11);
	private final Value v2 = new Value(22);
	private static final Value VAL_3 = new Value(33);
	
	private final int[] a = {1,2,3,4,5,6};
	
	public String toString() {
		return id + ": " + "i4 = " + i4  + ",INT_5 = " + INT_5;
	}
	
	public static void main(String[] args) {
		FinalData fd1 = new FinalData("fd1");
		//fd1.valueOne++;  Error:can't change value
		fd1.v2.i++;		//Object isn't constant 对象不是常数，对象不可变，但其中的属性可以
		fd1.v1 = new Value(9);    //ok --not final
		
		for(int i = 0;i < fd1.a.length;i++) {
			fd1.a[i]++;		//Object isn't constant
		}
		
		//fd1.v2 = new Value(0);  Error:can't
		//fd1.VAL_3 = new Value(1);  change reference 改变了对象引用，不可以
		//fd1.a = new int[3];  数组也是引用，不可以改变
		System.out.println(fd1);
		System.out.println("Creating new FinalData");
		FinalData fd2 = new FinalData("fd2");
		System.out.println(fd1);
		System.out.println(fd2);
	}
}
