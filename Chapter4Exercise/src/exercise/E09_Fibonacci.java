package exercise;

import java.util.Scanner;

/**
 * 
 * @author wwr
 *
 */
public class E09_Fibonacci {
	
	/**
	 * �������ֵ���Լ������Լ�
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
		
		//��ȡ�Ӽ�������
		Scanner sc = new Scanner(System.in);
		
		int num = sc.nextInt();
		
		for(int i = 0;i <= num;i++) {
			System.out.print(fib(i) + ",");
		}
		
	}
}
