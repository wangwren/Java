package exercise1;

/**
 * ����һ���࣬������һ��int���һ��char��
 * ���Ƕ�û�б���ʼ���������ǵ�ֵ��ӡ������
 * ����֤Javaִ����Ĭ�ϳ�ʼ��
 * @author wwr
 *
 */
public class E01_DefaultInitialization {

	int i;
	char c;
	
	//ͨ�����캯�������
	public E01_DefaultInitialization() {
		
		System.out.println("i : " + i);
		System.out.println("c : " + "[" + c + "]");
	}
	
	public static void main(String[] args) {
		
		new E01_DefaultInitialization();
	}
	
	/*
	 * i : 0
	 * c : [ ]
	 */
}
