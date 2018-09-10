package holding;

import java.util.LinkedList;

public class Stack<T> {
	private LinkedList<T> storage = new LinkedList<T>();
	/**
	 * ջ�����Ԫ��
	 * @param t
	 */
	public void push(T t) {
		storage.addFirst(t);
	}
	/**
	 * ��ȡջ��Ԫ��
	 * @return
	 */
	public T peek() {
		return storage.getFirst();
	}
	/**
	 * �Ƴ�ջ��Ԫ�أ������ر��Ƴ���Ԫ��
	 * @return
	 */
	public T pop() {
		return storage.removeFirst();
	}
	public boolean empty() {
		//���ջΪ�գ��򷵻�true
		return storage.isEmpty();
	}
	public String toString() {
		return storage.toString();
	}
}
