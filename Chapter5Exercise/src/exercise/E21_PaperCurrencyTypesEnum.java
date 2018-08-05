package exercise;
/**
 * ����һ��enum
 * ������ֽ������С��ֵ��6������
 * ͨ��values()ѭ������ӡÿһ��ֵ����ordinal()
 * @author wwr
 *
 */
public class E21_PaperCurrencyTypesEnum {

	enum PaperCurrencyTypes{
		ONE,TWO,FIVE,TEN,TEWNTY,FIFTY
	}
	
	public static void main(String[] args) {
		
		for(PaperCurrencyTypes paperCurrencyTypes : PaperCurrencyTypes.values()) {
			System.out.println(paperCurrencyTypes);
			System.out.println(paperCurrencyTypes.ordinal());
		}
	}
}
