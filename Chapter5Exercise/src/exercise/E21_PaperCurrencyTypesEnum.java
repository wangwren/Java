package exercise;
/**
 * 创建一个enum
 * 它包含纸币中最小面值的6种类型
 * 通过values()循环并打印每一个值及其ordinal()
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
