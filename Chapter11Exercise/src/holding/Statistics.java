package holding;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Statistics {

	public static void main(String[] args) {
		Random rand = new Random(47);
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		for(int i = 0;i < 10000;i++) {
			int key = rand.nextInt(20);
			Integer value = map.get(key);
			//如果value不为空，则value+1之后存入map中。
			//不能再定义一个变量来记录，这样记录的是整个，只要有就+1了
			//应该是针对当前的key值，如果有，就+1，之后存入
			map.put(key, value == null ? 1 : value + 1);
		}
		System.out.println(map);
	}
}
