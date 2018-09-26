package strings;

import java.util.Scanner;

public class ScannerDelimiter {

	public static void main(String[] args) {
		Scanner sc = new Scanner("12,42,78,99,42");
		sc.useDelimiter("\\s*,\\s*");
		while(sc.hasNextInt()) {
			System.out.println(sc.nextInt());
		}
	}
}
