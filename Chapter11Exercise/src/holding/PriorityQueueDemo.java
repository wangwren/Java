package holding;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

public class PriorityQueueDemo {
	public static void main(String[] args) {
		PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>();
		Random rand = new Random(47);
		for(int i = 0;i < 10;i++) {
			priorityQueue.offer(rand.nextInt(i + 10));
		}
		QueueDemo.printQ(priorityQueue);
		
		List<Integer> ints = 
				Arrays.asList(25,22,20,18,14,9,3,1,1,2,3,9,14,18,21,23,25);
		priorityQueue = new PriorityQueue<Integer>(ints);
		QueueDemo.printQ(priorityQueue);
		
		priorityQueue = new PriorityQueue<>(ints.size(), Collections.reverseOrder());
		priorityQueue.addAll(ints);
		QueueDemo.printQ(priorityQueue);
		
		String fact = "EDUCATION SHOULD ESCHEW OBFUSCATION";
		List<String> strings = Arrays.asList(fact.split(""));
		PriorityQueue<String> stringPQ = new PriorityQueue<String>(strings);
		QueueDemo.printQ(stringPQ);
		
		//Collections.reverseOrder() API解释：
		//返回一个比较器，它强行逆转实现了 Comparable 接口的对象 collection 的自然顺序。
		//（自然顺序是通过对象自身的 compareTo 方法强行排序的。）
		//此方法允许使用单个语句，以逆自然顺序对实现了 Comparable 接口的对象 collection（或数组）进行排序（或维护）。
		//例如，假设 a 是一个字符串数组。那么：
        //Arrays.sort(a, Collections.reverseOrder());
		//将按照逆字典（字母）顺序对数组进行排序。
		//返回的比较器是可序列化的。 
		stringPQ = new PriorityQueue<String>(strings.size(),Collections.reverseOrder());
		stringPQ.addAll(strings);
		QueueDemo.printQ(stringPQ);
		
		Set<Character> charSet = new HashSet<Character>();
		for(char c : fact.toCharArray()) {
			charSet.add(c);
		}
		PriorityQueue<Character> characterPQ = new PriorityQueue<Character>(charSet);
		QueueDemo.printQ(characterPQ);
	}
}
