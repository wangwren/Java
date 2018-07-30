package exercise;

import java.util.Random;

public class E02_RandomInts {

	public static void main(String[] args) {
		
		Random r = new Random(47);
		E02_RandomInts randomInts = new E02_RandomInts();
		
		for(int i = 0;i < 25;i++) {
			randomInts.compareRand(r);
		}
	}
	
	public void compareRand(Random r) {
		
		int a = r.nextInt();
		int b = r.nextInt();
		
		System.out.println("a = " + a + "; b = " + b);
		
		if(a < b) {
			System.out.println("a < b");
		}else if(a > b) {
			System.out.println("a > b");
		}else {
			System.out.println("a = b");
		}
	}
}
