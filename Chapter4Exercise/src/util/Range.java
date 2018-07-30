package util;
/**
 * �Զ�����ǡ��������
 * @author wwr
 *
 */
public class Range {

	/**
	 * ��0��ʼ����ֵ��ֱ����Χ���ޣ�����������Χ����
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
	 * ��һ��ֵ��ʼ����ֵ��ֱ���ȵڶ���ֵС1��ֵΪֹ
	 * @param first ��ʼ������ֵ
	 * @param num	���
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
	 * @param first ��ʼֵ
	 * @param num	���ֵ�����Ǹ÷������ܱ����ֵ��
	 * @param step  ������ÿ������ֵ
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
