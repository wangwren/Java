package exercise;
/**
 * дһ������ʹ������Ƕ��forѭ����ȡ���������
 * ��̽��ʹ�ӡ������ֻ�ܱ��������1�����������ܱ���������������
 * @author wwr
 *
 */
public class E04_FindPrimes {

	public static void main(String[] args) {
		
		
		for(int i = 1; i < 100;i++) {
			boolean flag = true;	//��ʶ���������ڵ�һ��for��ߣ�����ѭ����һ�飬flag�ֱ����µ���Ϊtrue
			//������������û��Ч����ѭ����flagû�б����¸�ֵ���ڲ�ѭ����4ʱ��flagΪfalse��֮���һֱΪfalse��
			
			for(int j = 2;j < i;j++) {
				if(i % j == 0) {
					flag = false;	//����ܱ��������������falg��Ϊfalse
				}
			}
			if(flag) {
				System.out.println(i);
			}
		}
	}
}
