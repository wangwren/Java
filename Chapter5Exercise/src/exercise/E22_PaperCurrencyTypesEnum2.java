package exercise;

import javafx.print.PageOrientation;

/**
 * 创建一个enum
 * 它包含纸币中最小面值的6种类型
 * 通过values()循环并打印每一个值及其ordinal()
 * 为enum写一个switch语句
 * 对于每一个case
 * 输出特定货币的描述
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
				System.out.println("一块钱");
				break;
			case TWO:
				System.out.println("两块钱");
				break;
			case FIVE:
				System.out.println("五块钱");
				break;
			case TEN:
				System.out.println("十块钱");
				break;
			case TEWNTY:
				System.out.println("二十块钱");
				break;
			case FIFTY:
				System.out.println("五十块钱");
		}
	}
	
	
	public static void main(String[] args) {
		
		E22_PaperCurrencyTypesEnum2 currencyTypesEnum2 = new E22_PaperCurrencyTypesEnum2();
		
		for(PaperCurrencyTypes pct : PaperCurrencyTypes.values()) {
			currencyTypesEnum2.describe(pct);
		}
		
	}
}
