package exercise;

import javafx.print.PageOrientation;

/**
 * ����һ��enum
 * ������ֽ������С��ֵ��6������
 * ͨ��values()ѭ������ӡÿһ��ֵ����ordinal()
 * Ϊenumдһ��switch���
 * ����ÿһ��case
 * ����ض����ҵ�����
 * @author wwr
 *
 */
public class E22_PaperCurrencyTypesEnum2 {

	enum PaperCurrencyTypes{
		ONE,TWO,FIVE,TEN,TEWNTY,FIFTY
	}
	
	public void describe(PaperCurrencyTypes pct) {
		switch(pct) {
			case ONE:
				System.out.println("һ��Ǯ");
				break;
			case TWO:
				System.out.println("����Ǯ");
				break;
			case FIVE:
				System.out.println("���Ǯ");
				break;
			case TEN:
				System.out.println("ʮ��Ǯ");
				break;
			case TEWNTY:
				System.out.println("��ʮ��Ǯ");
				break;
			case FIFTY:
				System.out.println("��ʮ��Ǯ");
		}
	}
	
	
	public static void main(String[] args) {
		
		E22_PaperCurrencyTypesEnum2 currencyTypesEnum2 = new E22_PaperCurrencyTypesEnum2();
		
		for(PaperCurrencyTypes pct : PaperCurrencyTypes.values()) {
			currencyTypesEnum2.describe(pct);
		}
		
	}
}
