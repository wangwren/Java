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
			//���value��Ϊ�գ���value+1֮�����map�С�
			//�����ٶ���һ����������¼��������¼����������ֻҪ�о�+1��
			//Ӧ������Ե�ǰ��keyֵ������У���+1��֮�����
			map.put(key, value == null ? 1 : value + 1);
		}
		System.out.println(map);
	}
}
