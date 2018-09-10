package holding;

import java.util.LinkedList;

public class Stack<T> {
	private LinkedList<T> storage = new LinkedList<T>();
	/**
	 * 栈顶添加元素
	 * @param t
	 */
	public void push(T t) {
		storage.addFirst(t);
	}
	/**
	 * 获取栈顶元素
	 * @return
	 */
	public T peek() {
		return storage.getFirst();
	}
	/**
	 * 移除栈顶元素，并返回被移除的元素
	 * @return
	 */
	public T pop() {
		return storage.removeFirst();
	}
	public boolean empty() {
		//如果栈为空，则返回true
		return storage.isEmpty();
	}
	public String toString() {
		return storage.toString();
	}
}
