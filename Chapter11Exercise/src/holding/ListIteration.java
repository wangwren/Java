package holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class ListIteration {

	public static void main(String[] args) {
		List<String> list = 
				new ArrayList<String>(Arrays.asList("A","B","C","D","E","F","G"));
		ListIterator<String> lit = list.listIterator();
		while(lit.hasNext()) {//����������б�ʱ������б�������ж��Ԫ�أ��򷵻� true
			//next()������һ��Ԫ��
			//nextIndex()���ض� next �ĺ�������������Ԫ�ص�������
			//previousIndex()���ض� previous �ĺ�������������Ԫ�ص�������
			System.out.print(lit.next() + "," + lit.nextIndex() +
					"," + lit.previousIndex() + ";");
		}
		System.out.println();
		
		while(lit.hasPrevious()) {//�������������б��б�������ж��Ԫ�أ��򷵻� true��
			//previous()�����б��е�ǰһ��Ԫ�ء�
			System.out.print(lit.previous() + "," +lit.previousIndex() + ";");//�������
		}
		System.out.println();
		System.out.println(list);
		
		lit = list.listIterator(3);
		while(lit.hasNext()) {
			lit.next();
			lit.set("Z");	//�滻�� 3 λ�ÿ�ʼ����ǰ������Ԫ��
		}
		System.out.println(list);
	}
}
