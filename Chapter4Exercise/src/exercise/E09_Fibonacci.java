package exercise;

import java.util.Scanner;

/**
 * 
 * @author wwr
 *
 */
public class E09_Fibonacci {
	
	/**
	 * 用来算出值，自己调用自己
	 * @param n
	 * @return
	 */
	public static int fib(int n) {
		
		if(n <= 2) {
			return 1;
		}
		return fib(n-1) + fib(n-2);
	}
	

	public static void main(String[] args) {
		
		//获取从键盘输入
		Scanner sc = new Scanner(System.in);
		
		int num = sc.nextInt();
		
		for(int i = 0;i <= num;i++) {
			System.out.print(fib(i) + ",");
		}
		
	}
}
