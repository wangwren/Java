package holding;
/**
 * 栈在编程语言中经常用来对表达式求值。
 * 请使用net.mindview.util.Stack对下面的表达式求值。
 * 其中 + 表示将后面的字母压进栈，而 - 表示弹出栈顶字母并打印它
 * +U+n+c---+e+r+t---+a-+i-+n+t+y---+-+r+u--+l+e+s---
 * @author wwr
 *
 */
public class E15_Evaluator {

	public static void main(String[] args) {
		evaluate("+U+n+c---+e+r+t---+a-+i-+n+t+y---+-+r+u--+l+e+s---");
	}

	private static void evaluate(String string) {
		Stack<Character> stack = new Stack<Character>();
		char[] ch = string.toCharArray();
		for(int i = 0;i < ch.length;) {
			//先拿i，再++。
			switch (ch[i++]) {
			case '+':
				//如果是+，则取+后面的字符，加入栈中。i再加1
				stack.push(ch[i++]);
				break;
			case '-':
				System.out.print(stack.pop());
			}
		}
	}
}
