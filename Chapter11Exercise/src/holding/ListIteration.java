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
		while(lit.hasNext()) {//以正向遍历列表时，如果列表迭代器有多个元素，则返回 true
			//next()返回下一个元素
			//nextIndex()返回对 next 的后续调用所返回元素的索引。
			//previousIndex()返回对 previous 的后续调用所返回元素的索引。
			System.out.print(lit.next() + "," + lit.nextIndex() +
					"," + lit.previousIndex() + ";");
		}
		System.out.println();
		
		while(lit.hasPrevious()) {//如果以逆向遍历列表，列表迭代器有多个元素，则返回 true。
			//previous()返回列表中的前一个元素。
			System.out.print(lit.previous() + "," +lit.previousIndex() + ";");//倒着输出
		}
		System.out.println();
		System.out.println(list);
		
		lit = list.listIterator(3);
		while(lit.hasNext()) {
			lit.next();
			lit.set("Z");	//替换从 3 位置开始，向前的所有元素
		}
		System.out.println(list);
	}
}
