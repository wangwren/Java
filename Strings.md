# 字符串

> 可以证明，字符串操作是计算机程序设计中最常见的行为

## 不可变String

String对象是不可变的。String类中每一个看起来会修改String值的方法，实际上都是创建了一个全新的 String 对象，以包含修改后的字符串内容。而最初的String对象则丝毫未动。

```java
package strings;

public class Immutable {

	public static String upcase(String s) {
		return s.toUpperCase();
	}
	public static void main(String[] args) {
		String q = "howdy";
		System.out.println(q); //howdy
		String qq = upcase(q);
		System.out.println(qq); //HOWDY
		System.out.println(q); 	//howdy
	}
}
/**
howdy
HOWDY
howdy
*/
```

当把 q 传给upcase()方法时，实际传递的是引用的一个拷贝。其实，每当把 String 对象作为方法的参数时，都会复制一份引用，而该引用所指的对象其实一直待在单一的物理位置上，从未动过。

回到 upcase() 定义，传入其中的引用有了名字 s，只有 upcase() 运行的时候，局部引用 s 才存在。一旦upcase()运行结束，s 就消失。当然了，upcase()的返回值，其实只是最终结果的引用。这足以说明，upcase()返回的引用指向了一个新的对象，而原本的 q 则还在原地。

String的这种行为方式其实正是我们想要的。例如：

```java
String s = "asdf";
String x = Immutable.upcase(s);
```

对于一个方法而言，参数是为该方法提供信息的，而不是想让该方法改变自己的。

## 重载“+”与StringBuilder

String对象是不可变的，可以给一个String对象加任意多的别名。因为String对象具有只读特性，所以指向它的任何引用都不可能改变它的值，因此，也就不会对其他的引用有什么影响。

不可变性会带来一定的效率问题。为String对象重载的“+”操作符就是一个例子。重载的意思是，一个操作符在应用于特定的类时，被赋予了特殊的意义（用于String的“+”与“+=”是Java中仅有的两个重载过的操作符，而Java并不允许程序员重载任何操作符）。

操作符“+”可以用来连接String：

```java
public class Concatenation{
    public static void main(String[] args){
        String mango = "mango";
        String s = "abc" + mango + "def" + 47;
        System.out.println(s);
    }
}
/**
abcmangodef47
*/
```

可以想象一下，这段代码可能是这样工作的：String可能有一个append()方法，它会生成一个新的String对象，以包含“abc”与mango连接后的字符串。然后，该对象再与“def”相连，生成另一个新的String对象，以此类推。

这种工作方式当然也行得通，但是为了生成最终的String，此方式会产生一大堆需要垃圾回收的中间对象。

想看以上代码到底是如何工作的，可以用JDK自带的 javap 来反编译以上代码。命令如下：

```java
javap -c Concatenation
```

这里的 -c 标志表示将生成JVM字节码。

![1537882674309](./_image/201809251.png)

其中的dup与invokevirtural语句相当于Java虚拟机上的会变语句。需要注意的重点是：编译器自动引入了java.lang.StringBuilder类。虽然我们在源码中并没有使用StringBuilder类，但是编译器却自作主张地使用了它，因为它更高效。

在这个例子中，编译器创建了一个StringBuilder对象，用以构造最终的String，并为每个字符串调用一次StringBuilder的append()方法，总计四次。最后调用toString()生成结果，并存为 s (使用的命令为astore_2)。

下面的程序采用两种方式生成一个String：方法一使用了多个String对象；方法二在代码中使用了StringBuilder。

```java
package strings;

public class WhiterStringBuilder {

	public String implicit(String[] fileds) {
		String result = "";
		for(int i = 0;i < fileds.length;i++) {
			result += fileds[i];
		}
		return result;
	}
	public String explicit(String[] fileds) {
		StringBuilder result = new StringBuilder();
		for(int i = 0;i < fileds.length;i++) {
			result.append(fileds[i]);
		}
		return result.toString();
	}
}
```

现在运行 javap -c WhiterStringBuilder，可以看到两个对应的字节码。首先是  implicit()方法：

![1537888294548](./_image/201809252.png)

注意从第8行到第35行构成了一个循环体。第8行：对堆栈中的操作数进行“大于或等于的整数比较运算”，循环结束时跳到38行。第35行：返回循环体的起始点（第5行）。要注意的重点是：StringBuilder是在循环之内构造的，这意味着每经过循环一次，就会创建一个新的StringBuilder对象。

下面是explicit()方法对应的字节码：

![1537888851894](./_image/201809253.png)

可以看到，不仅循环部分的代码更简短、更简单，而且它只生成了一个StringBuilder对象。显示地创建StringBuilder还允许你预先为其指定大小。如果你已经知道最终的字符串大概有多长，那预先指定StringBuilder的大小可以避免多次重新分配缓冲。

因此，当你为一个类编写toString()方法时，如果字符串操作比较简单，那就可以信赖编译器，它会为你合理地构造器最终的字符串结果。但是，如果你要在toString()方法中使用循环，那么最好自己创建一个StringBuilder对象，用它来构造最终的结果。

```java
package strings;

import java.util.Random;

public class UsingStingBuilder {

	public static Random rand = new Random(47);
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		for(int i = 0;i < 25;i++) {
			result.append(rand.nextInt(100));
			result.append(", ");
		}
		result.delete(result.length() - 2, result.length());
		result.append("]");
		return result.toString();
	}
	public static void main(String[] args) {
		UsingStingBuilder usb = new UsingStingBuilder();
		System.out.println(usb);
	}
}
/**
[58, 55, 93, 61, 61, 29, 68, 0, 22, 7, 88, 28, 51, 89, 9, 78, 98, 61, 20, 58, 16, 40, 11, 22, 4]
*/
```

最终的结果是用append()语句一点点拼接其起来的。如果想走捷径，例如append(a + ":" + c),那编译器就会调入陷阱，从而为你另外创建一个StringBuilder对象处理括号内的字符串操作。

上面的例子中，使用delete()方法用它删除最后一个逗号与空格，以便添加右括号。

StringBuilder是Java SE5引入的，在这之前Java**用的是StringBuffer**。后者是线程安全的（参见21章，不知道能不能坚持到），因此开销也会大写。

## 无意识递归

Java中的每个类从根本上都是继承自 Object，标准容器类自然也不例外。因此容器类都有 toString() 方法，并且覆写了该方法，使得它生成的 String 结果能够表达容器自身，以及容器所包含的对象。例如ArrayList.toString()，它会遍历ArrayList中包含的所有对象，调用每个元素上的toString()方法。

如果你希望 toString() 方法打印出对象的内存地址，也许你会考虑使用 this 关键字：

```java
package strings;

import java.util.ArrayList;
import java.util.List;

public class InfiniteRecursion {

	public String toString() {
		return " InfiniteRecursion address: " + this + "\n";
        //正确的写法：return " InfiniteRecursion address: " + super.toString() + "\n";
	}
	public static void main(String[] args) {
		List<InfiniteRecursion> v = new ArrayList<InfiniteRecursion>();
		for(int i = 0;i < 10;i++) {
			v.add(new InfiniteRecursion());
		}
		System.out.println(v);
	}
}
/**
Exception in thread "main" java.lang.StackOverflowError
	at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:449)
	at java.lang.StringBuilder.append(StringBuilder.java:136)
	at java.lang.StringBuilder.<init>(StringBuilder.java:113)
	at strings.InfiniteRecursion.toString(InfiniteRecursion.java:9)
	at java.lang.String.valueOf(String.java:2994)
	at java.lang.StringBuilder.append(StringBuilder.java:131)
	at strings.InfiniteRecursion.toString(InfiniteRecursion.java:9)...
*/
```

当创建了InfiniteRecursion对象，并将其打印出来的时候，会得到一串非常长的异常。其实，当如下代码运行时：

```java
"InfiniteRecursion address:" + this
```

这里发生了自动类型转换，由 InfiniteRecursion 类型转换成 String 类型。**因为编译器看到一个String对象后面跟着一个“+”，而后面的对象不是String，于是编译器试着将 this 转换成一个 String。正是通过调用 this 上的 toString() 方法，于是就发生了递归调用。**

如果真的想要打印出对象的内存地址，应该调用`Object.toString()`方法，这才是负责此任务的方法。所以，不使用 this，而是应该调用super.toString()方法。

## String上的操作

查看JDK中String方法可以看出，当需要改变字符串内容时，String类的方法都会返回一个新的String对象。同时，如果内容没有发生改变，String的方法只是返回指向原对象的引用而已。这样可以节约存储空间以及避免额外的开销。

## 扫描输入

Java SE5 新增了 Scanner 类，它可以大大减轻扫描输入的工作负担。

Scanner的构造器可以接受任何类型的输入对象，包括File对象、InputStream、String。有了Scanner，所有的输入、分词以及翻译的操作都隐藏在不同类型的next方法中。普通的next()方法返回下一个String。所有的基本类型（除char之外）都有对应的next方法，包括BigDecimal和BigInteger。所有的next方法，只有在找到一个完整的分词之后才会返回。Scanner还有相应的hasNext方法，用以判断下一个输入分词是否所需的类型。

### Scanner定界符

在默认情况下，Scanner根据空白字符对输入进行分词，但是可以用正则表达式指定自己所需的定界符：

```java
package strings;

import java.util.Scanner;

public class ScannerDelimiter {

	public static void main(String[] args) {
		Scanner sc = new Scanner("12,42,78,99,42");
		sc.useDelimiter("\\s*,\\s*");
		while(sc.hasNextInt()) {
			System.out.println(sc.nextInt());
		}
	}
}
/**
12
42
78
99
42
*/
```

这个例子使用逗号（包括逗号前后任意的空白字符）作为定界符，同样的技术也可以用来读取逗号分隔的文件。可以用`useDelimiter()`来设置定界符，同时，还有一个delimiter()方法，用来返回当前正在作为定界符使用的Pattern对象。

[练习题](https://github.com/wangwren/Thinking-in-Java-notes/tree/master/Chapter13Exercise/src/strings)

