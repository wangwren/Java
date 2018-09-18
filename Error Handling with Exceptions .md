# 通过异常处理错误

## 基本异常

**异常情形**是阻止当前方法或作用域继续执行的问题。把异常情形与普通问题相区分很重要。所谓普通问题是指，在当前环境下能得到足够的信息，总能处理这个错误。而对于异常情形，就不能继续下去了，因为在当前环境下无法获得必要的信息来解决问题。你所能做的就是从当前环境跳出，并且把问题提交给上一级环境。这就是抛出异常时所发生的事情。

当抛出异常后，有几件事会随之发生。首先，同Java中其他对象的创建一样，将使用 new 在堆上创建异常对象。然后，当前的执行路径被终止，并且从当前环境中弹出对异常对象的引用。此时，异常处理机制接管程序，并开始寻找一个恰当的地方来继续执行程序。这个恰当的地方就是**异常处理程序**，它的任务是将程序从错误状态中恢复，以使程序要么换一种方式运行，要么继续运行下去。

举一个抛出异常的简单例子。对于对象引用 t ，传给你的时候可能尚未被初始化。所以在使用这个对象引用调用其方法之前，会先对引用进行检查。可以创建一个代表错误信息的对象，并且将它从当前环境中“抛出”，这样就把错误信息传播到了“更大”的环境中。这被称为**抛出一个异常**，看起来像这样：

```java
if(t == null)
    throw new NullPointerException();
```

这就抛出了异常，于是在当前环境下就不必再为这个问题操心了。

### 异常参数

与使用Java中的其他参数对象一样，我们总是用 new 在堆上创建异常对象，这也伴随着存储空间的分配和构造器的调用。所有标准异常类都有两个构造器：一个是默认构造器；另一个是接受字符串作为参数，以便能把相关信息放入异常对象的构造器：

```java
throw new NullPointException("t = null");
```

要把这个字符串的内容取出来可以有多重不同的方法。

关键字 throw 将产生许多有趣的结果。在使用 new 创建了异常对象之后，此对象的引用将传给throw。尽管返回的异常对象其类型通常与方法设计的返回类型不同，但从效果上看，它就像是从方法“返回”的。可以简单地把异常处理看成一种不同的返回机制，当然若过分强调这种类比的话，就会有麻烦了。**另外还能用抛出异常的方式从当前的作用域退出**。在这两种情况下，将会返回一个异常对象，然后退出方法或作用域。

此外，**能够抛出任意类型的 Throwable 对象，它是异常类型的根类。通常，对于不同类型的错误，要抛出相应的异常**。错误信息可以保存在异常对象内部或者用异常类的名称来暗示，上一层环境通过这些信息来决定如何处理异常。（通常，异常对象中仅有的信息就是异常类型，除此之外不包含任何有意义的内容。）

## 捕获异常
要明白异常是如何被捕获的，必须首先理解**监控区域**(guarded region)的概念。它是一段可能产生异常的代码，并且后面跟着处理这些异常的代码。

### try块
如果在方法内部抛出了异常（或者在方法内部调用的其他方法抛出了异常），这个方法将在抛出异常的过程中结束。要是不希望方法就此结束，可以在方法内设置一个特殊的块来捕获异常。因为在这个块里“尝试”各种（可能产生异常的）方法调用，所以称为 try 块。它是跟在try关键字之后的普通程序块：
```java
try{
    // Code that might generate exceptions
}
```
### 异常处理程序
当然，抛出的异常必须在某处得到处理。这个“地点”就是**异常处理程序**，而且针对每个要捕获的异常，得准备相应的处理程序。异常处理紧跟在 try 块之后。以关键字 catch 表示：

```java
try{
    //Code that might generate exception
}catch(Type1 id1){
    //Handle exceptions of Type1
}catch(Type2 id2){
    //Handle exceptions of Type2
}catch(Type3 id3){
    //Handle exceptions of Type3
}
// etc...
```

每个 catch 子句（异常处理程序）看起来就像是接收一个且仅接收一个特殊类型的参数的方法。可以在处理程序的内部使用标识符，因为异常的类型已经给了你足够的信息来对异常进行处理，但标识符并不可以省略。

异常处理程序必须紧跟在 try 块之后。当异常被抛出时，异常处理机制将负责搜寻参数与异常类型相匹配的第一个处理程序。然后进入 catch 子句执行。此时认为异常得到了处理。一旦 catch 子句结束，则处理程序的查找过程结束。注意，只有匹配的catch子句才能得到执行；这与 switch 语句不同，switch 语句需要在每一个 case 后面跟一个break，以避免执行后续的case子句。

注意在 try 块内部，许多不同的方法调用可能会产生类型相同的异常，而你只需要提供一个针对此类型的异常处理程序。
#### 终止与恢复

异常处理理论上有两种基本模型。Java支持**终止模型**。在这种模型中，将假设错误非常关键，以至于程序无法返回到异常发生的地方继续执行。一旦异常被抛出，就表明已无法挽回，也不能回来继续执行。

另一种称为**恢复模型**。意思是处理程序的工作是修正错误，然后重新尝试调用出问题的方法，并认为第二次能成功。对于恢复模型，通常希望异常被处理之后能继续执行程序。如果想要用Java实现类似恢复的行为，那么在遇见错误时就不能抛出异常，而是调用方法来修正该错误。或者，把 try 块放在 while 循环里，这样就不断地进行 try 块，直到得到满意的结果。

长久以来，尽管程序员们使用的操作系统支持恢复模型的异常处理，但他们最终还是转向使用类似“终止模型”的代码，并且忽略恢复行为。所以虽然恢复模型开始显得很吸引人，但不是很实用。其中的主要原因可能是它导致的耦合：**恢复性的处理程序需要了解异常抛出的地点，这势必要包含依赖于抛出位置的非通用性代码。这增加了代码编写和维护的困难，对于异常可能会从许多地方抛出的大型程序来说，更是如此**。

## 创建自定义异常

要自己定义异常类，必须从已有的异常类继承，最好是选择意思相近的异常类继承（不过这样的异常并不容易找）。建立新的异常类型最简单的方式就是**让编译器为你产生默认构造器**，所以这几乎不用写多少代码。

```java
package exceptions;

class SimpleException extends Exception{}

public class InherintingExceptions {
	public void f() throws SimpleException {
		System.out.println("Throw SimpleException from f()");
		throw new SimpleException();
	}
	public static void main(String[] args) {
		InherintingExceptions sed = new InherintingExceptions();
		try {
			sed.f();
		}catch (SimpleException e) {
			System.out.println("Caught it!");
		}
	}
}
/**
Throw SimpleException from f()
Caught it!
*/
```

**编译器创建了默认构造器，它将自动调用基类的默认构造器。**本例中不会得到像`SimpleException(String)`这样的构造器，这种构造器也不实用。你将看到，对异常来说，**最重要的部分就是类名**，所以本例中建立的异常类在大多数情况下已经够用了。

通过写入`System.err`而将错误发送给**标准错误流**。通常这比把错误信息输出到`System.out`要好，因为`System.out`也许会被重定向。如果把结果送到`System.err`，它就不会随`System.out`，一起被重定向，这样更容易被用户注意。

也可以为异常类定义一个接受字符串参数的构造器：

```java
package exceptions;

class MyException extends Exception{
	public MyException() {
		
	}
	
	public MyException(String msg) {
		super(msg);
	}
}

public class FullConstructors {
	public static void f() throws MyException {
		System.out.println("Throw MyException from f()");
		throw new MyException();
	}
	public static void g() throws MyException {
		System.out.println("Throw MyException from g()");
		throw new MyException("Originated in g()");
	}
	public static void main(String[] args) {
		try {
			f();
		}catch (MyException e) {
			e.printStackTrace(System.out);
		}
		
		try {
			g();
		}catch (MyException e) {
			e.printStackTrace(System.out);
		}
	}
}
/**
Throw MyException from f()
exceptions.MyException
	at exceptions.FullConstructors.f(FullConstructors.java:16)
	at exceptions.FullConstructors.main(FullConstructors.java:24)
Throw MyException from g()
exceptions.MyException: Originated in g()
	at exceptions.FullConstructors.g(FullConstructors.java:20)
	at exceptions.FullConstructors.main(FullConstructors.java:30)
*/
```

新增的代码不长：两个构造器定义了MyException类型对象的创建方式。对于第二个构造器，使用super关键字明确调用了其基类构造器，它接受一个字符串作为参数。

在异常处理程序中，调用了在Throwable类声明(Exception即从此类继承)的`printStackTrace()`方法。就像从输出中看到的，它将打印“从方法调用处”的方法调用序列。这里，信息被发送到了System.out，并自动地被捕获和显示在输出中。但是，如果调用默认版本：`e.printStackTrace();`则信息将被输出到标准错误流。

### 异常与记录日志

```java
package exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

class LoggingException extends Exception{
	private static Logger logger = Logger.getLogger("LoggingException");
	public LoggingException() {
		StringWriter trace = new StringWriter();
		printStackTrace(new PrintWriter(trace));
		logger.severe(trace.toString());
	}
}

public class LoggingExceptions {

	public static void main(String[] args) {
		try {
			throw new LoggingException();
		}catch (LoggingException e) {
			System.err.println("Caught" + e);
		}
		try {
			throw new LoggingException();
		}catch (LoggingException e) {
			System.err.println("Caught" + e);
		}
	}
}
/**
九月 18, 2018 11:44:51 下午 exceptions.LoggingException <init>
严重: exceptions.LoggingException
	at exceptions.LoggingExceptions.main(LoggingExceptions.java:20)

Caughtexceptions.LoggingException
九月 18, 2018 11:44:51 下午 exceptions.LoggingException <init>
严重: exceptions.LoggingException
	at exceptions.LoggingExceptions.main(LoggingExceptions.java:25)

Caughtexceptions.LoggingException
*/
```

静态的`Logger.getLogger()`方法创建了一个String参数相关联的 Logger 对象（通常与错误相关的包名或类名），这个Logger对象会将其输出发送到`System.err`。向Logger写入的最简单方式就是直接调用与日志记录消息的级别相关联的方法，这里使用的是`server()`。为了产生日志记录消息，我们想要获取异常抛出处的栈轨迹，但是`printStackTrace()`不会默认地产生字符串。为了获取字符串，我们需要使用重载的`printStackTrace()`方法，它接受一个`java.io.PrintWriter`对象作为参数。

尽管由于LoggerException将所有记录日志的基础设施都构建在异常自身中，使得它所使用的方式非常方便，并因此不需要客户端程序员的干预就可以自动运行，但是更常见的情形是我们需要捕获和记录其他人编写的异常，因此我们必须在异常处理程序中生成日志消息：

```java
package exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class LoggingException2 {

	private static Logger logger = Logger.getLogger("LoggingException2");
	static void logException(Exception e) {
		StringWriter trace = new StringWriter();
		e.printStackTrace(new PrintWriter(trace));
		logger.severe(trace.toString());
	}
	public static void main(String[] args) {
		try {
			throw new NullPointerException();
		}catch (NullPointerException e) {
			logException(e);
		}
	}
}
/**
九月 19, 2018 12:01:33 上午 exceptions.LoggingException2 logException
严重: java.lang.NullPointerException
	at exceptions.LoggingException2.main(LoggingException2.java:17)
*/
```

还可以更进一步自定义异常，比如加入额外的构造器和成员：

```java
package exceptions;


class MyException2 extends Exception{
	private int x;
	public MyException2() {
		
	}
	public MyException2(String msg) {
		super(msg);
	}
	public MyException2(String msg,int x) {
		super(msg);
		this.x = x;
	}
	
	public int val() {
		return x;
	}
	
	/**
	 * 重写父类Throwable的方法
	 */
	@Override
	public String getMessage() {
		return "Detail Message : " + x + " " + super.getMessage();
	}
	
}

public class ExtraFeatures {

	public static void f() throws MyException2 {
		System.out.println("Throwing MyException2 from f()");
		throw new MyException2();
	}
	
	public static void g() throws MyException2 {
		System.out.println("Throwing MyException2 from g()");
		throw new MyException2("Originated in g()");
	}
	
	public static void h() throws MyException2 {
		System.out.println("Throwing MyException2 from h()");
		throw new MyException2("Originated in g()", 47);
	}
	
	public static void main(String[] args) {
		try {
			f();
		}catch (MyException2 e) {
			e.printStackTrace(System.out);
		}
		try {
			g();
		}catch (MyException2 e) {
			e.printStackTrace(System.out);
		}
		try {
			h();
		}catch (MyException2 e) {
			e.printStackTrace(System.out);
			System.out.println("e.val() = " + e.val());
		}
	}
}
```

新的异常添加了字段 x 以及设定 x 值的构造器和读取数据的方法。此外，还覆盖了`Throwable.getMessgae()`方法，以产生更详细的信息。对于 异常类来说，getMessage()方法有点类似于toString()方法。

既然异常也是对象的一种，所以可以继续修改这个异常类，以得到更强的功能。但要记住，使用程序包的客户端程序员可能仅仅只是查看一下抛出的异常类型，其他的就不管了（大多数Java库里的异常都是这么用的），所以对异常所添加的其他功能也许根本用不上。

## 异常说明

