package exercise8;

/**
 * ��дһ������
 * չʾ�����㴴����ĳ���ض���Ķ��ٸ�����
 * ������е�ĳ���ض���static��ֻ��һ��ʵ��
 *
 * @author wwr
 *
 */
public class E08_StaticTest {

	static int i =1;
	
	public static void main(String[] args) {
		
		E08_StaticTest staticTest1 = new E08_StaticTest();
		E08_StaticTest staticTest2 = new E08_StaticTest();
		
		staticTest1.i++;
		System.out.println(staticTest1.i + " = " + staticTest2.i);
		
		
		staticTest1.i++;
		System.out.println(staticTest1.i + " = " + staticTest2.i);
	}
}
