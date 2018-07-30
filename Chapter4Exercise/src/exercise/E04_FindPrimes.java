package exercise;
/**
 * 写一个程序，使用两个嵌套for循环和取余操作符，
 * 来探测和打印素数（只能被其自身和1整除，而不能被其他数字整除）
 * @author wwr
 *
 */
public class E04_FindPrimes {

	public static void main(String[] args) {
		
		
		for(int i = 1; i < 100;i++) {
			boolean flag = true;	//标识符，定义在第一个for里边，这样循环完一遍，flag又被重新的设为true
			//如果设在外边则没有效果，循环完flag没有被重新赋值，内层循环到4时，flag为false，之后就一直为false了
			
			for(int j = 2;j < i;j++) {
				if(i % j == 0) {
					flag = false;	//如果能被别的数整除，则将falg设为false
				}
			}
			if(flag) {
				System.out.println(i);
			}
		}
	}
}
