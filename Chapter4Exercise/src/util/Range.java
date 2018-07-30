package util;
/**
 * 自动生成恰当的数组
 * @author wwr
 *
 */
public class Range {

	/**
	 * 从0开始产生值，直至范围上限，但不包括范围上限
	 * @param num
	 * @return
	 */
	public static int[] range(int num) {
		
		int[] a = new int[num];
		for(int i = 0;i < num;i++) {
			a[i] = i;
		}
		
		return a;
	}
	
	/**
	 * 第一个值开始产生值，直至比第二个值小1的值为止
	 * @param first 开始产生的值
	 * @param num	最大
	 * @return
	 */
	public static int[] range(int first,int num) {
		
		int[] a = new int[num - first];
		for(int i = 0;i < a.length;i++) {
			a[i] = first++;
		}
		
		return a;
	}
	
	/**
	 * 
	 * @param first 起始值
	 * @param num	最大值，但是该方法不能比最大值大
	 * @param step  步长，每次增长值
	 * @return
	 */
	public static int[] range(int first,int num,int step) {
		
		int[] a ;
		int size = (num - first)%step;
		
		if(size == 0) {
			a = new int[(num - first)/step];
		}else {
			a = new int[(num - first)/step + 1];
		}
		
		for(int i = 0;i < a.length;i++) {
			a[i] = first;
			first = first + step;
		}
		
		return a;
	}
}
