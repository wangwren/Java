package exercise;

import java.util.Random;

public class E03_RandomInts2 {

	public static void main(String[] args) {
		
		Random r = new Random();
		E02_RandomInts randomInts = new E02_RandomInts();
		
		while(true) {
			randomInts.compareRand(r);
		}
	}
}
