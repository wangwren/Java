# 接口

## 抽象类和抽象方法

Java提供一个叫做**抽象方法**的机制，这种方法是不完整的；仅有声明而没有方法体。下面是抽象方法声明所采用的语法：

```java
abstract void f();
```

包含抽象方法的类叫做**抽象类**。如果一个类包含一个或多个抽象方法，该类必须被限定为**抽象的**。（否则，编译器就会报错）

如果一个抽象类不完整，那么当我们试图产生该类的对象时，由于为抽象类创建对象是不安全的，所以会从编译器那里得到一条出错消息。这样，编译器会确保抽象类的纯粹性。

如果从一个抽象类继承，并想创建该新类的对象，那么就必须为基类中的所有抽象方法提供方法定义。如果不这样做（可以选择不做），那么导出类便也是抽象类，且编译器将会强制用**abstract**关键字来限定这个类。

**使某个类成为抽象类并不需要所有的方法都是抽象的**，仅需将某些方法声明为抽象的即可。

## 接口

interface关键字使抽象类的概念更向前迈进了一步。

- abstract关键字允许人们在类中创建一个或多个没有任何定义的方法---提供了接口部分，但是没有提供任何相应的具体实现，这些实现是由此类的继承者创建的。
- interface这个关键字产生一个**完全抽象的类**，它根本就没有提供任何具体实现。
  - 它允许创建者确定方法名、参数列表和返回类型，但是没有任何方法体。接口提供了形式，而未提供任何具体实现。

一个接口表示：“所有实现了该特定接口的类看起来都像这样”。

**interface不仅仅是一个极度抽象的类，因为它允许通过创建一个能够被向上转型为多种基类的类型，来实现某种类似多重继承变种的特性。**

要想创建一个接口，需要用**interface关键字**替代**class关键字**。可以在 interface 关键字前添加 public 关键字（但仅限于该接口在与其同名的文件中被定义）。如果不添加public关键字，则它只具有包访问权限，这样它就只能在同一个包内可用。

接口也可以包含域，但是这些域**隐式地是static和final的。**

要让一个类遵循某个特定接口（或者是一组接口），需要使用**implements关键字**。它看起来还很像继承。

可以选择在接口中显示地将方法声明为 public的，但即使不这么做，它们也是 public的。因此，**当要实现一个接口时，在接口中被定义的方法必须被定义为是 public的。**否则，它们将只能得到默认的包访问权限，这样在方法被继承的过程中，其方法权限就被降低了，这是Java编译器所不允许的。

## 完全解耦

```java
package interfaces.classprocessor;

import java.util.Arrays;

/**
 * 假设有一个Processor类，它有一个name()方法；
 * 另外还有一个process()方法，该方法接收输入参数，修改它的值，然后产生输出。
 * 这个类作为基类而被扩展，用来创建各种不同类型的Processor。
 * 
 * @author wwr
 *
 */

class Processor{
	public String name() {
		return getClass().getSimpleName();
	}
	Object process(Object input) {
		return input;
	}
}

class Upcase extends Processor{
	String process(Object input) {
		return ((String)input).toUpperCase();
	}
}

class Downcase extends Processor{
	String process(Object input) {
		return ((String)input).toLowerCase();
	}
}

class Splitter extends Processor{
	String process(Object input) {
		return Arrays.toString(((String)input).split(" "));
	}
}

public class Apply {

	public static void process(Processor p,Object s) {
		System.out.println("Using Processor " + p.name());
		System.out.println(p.process(p.process(s)));
	}
	public static String s = "Disagreement with beliefs is by definition incorrect";
	public static void main(String[] args) {
		process(new Upcase(),s);
		process(new Downcase(), s);
		process(new Splitter(), s);
	}
}
/**
Using Processor Upcase
DISAGREEMENT WITH BELIEFS IS BY DEFINITION INCORRECT
Using Processor Downcase
disagreement with beliefs is by definition incorrect
Using Processor Splitter
[[Disagreement,, with,, beliefs,, is,, by,, definition,, incorrect]]
*/
```

Apply.process() 方法可以接受任何类型的 Processor ，并将其应用到 Object 对象上，然后打印结果。像本例这样，**创建一个能够根据所传递的参数对象的不同而具有不同行为的方法，被称为策略设计模式。**这类方法包含所要执行的算法中固定不变，而“策略”包含变化的部分。策略就是传递进去的参数对象，它包含要执行的代码。这里，Processor对象就是一个策略，在main() 中可以看到有三种不同类型的策略应用到了 String 类型的 s 对象上。

split() 方法是 String 类的一部分，它接受 String 类型的对象，并以传递进来的参数作为边界，将该 String 对象分隔开，然后返回一个数组 String[]。它在这里被用来当作创建 String 数组的快捷方式。

使用**适配器设计模式**。适配器中的代码将接受你所拥有的接口，并产生你所需要的接口。

```java
package interfaces.filters;

public class Filter {
	public String name() {
		return getClass().getSimpleName();
	}
	public Waveform process(Waveform input) {
		return input;
	}
}

package interfaces.interfaceprocessor;

public interface Processor {

	String name();
	Object process(Object input);
}

package interfaces.filters;

public class Waveform {

	private static long counter;
	private final long id = counter++;
	
	public String toString() {
		return "Waveform " + id;
	}
}

package interfaces.interfaceprocessor;

import interfaces.filters.Filter;
import interfaces.filters.Waveform;

public class LowPass extends Filter {

	double cutoff;
	public LowPass(double cutoff) {
		this.cutoff = cutoff;
	}
	public Waveform pocess(Waveform input) {
		return input;
	}
}

package interfaces.interfaceprocessor;

import interfaces.filters.Filter;
import interfaces.filters.Waveform;

public class HighPass extends Filter {
	double cutoff;
	public HighPass(double cutoff) {
		this.cutoff = cutoff;
	}
	public Waveform pocess(Waveform input) {
		return input;
	}
}

package interfaces.interfaceprocessor;

import interfaces.filters.Filter;
import interfaces.filters.Waveform;

public class BandPass extends Filter {
	double lowCutoff;
	double highCutoff;
	public BandPass(double lowCutoff,double highCutoff) {
		this.lowCutoff = lowCutoff;
		this.highCutoff = highCutoff;
	}
	public Waveform pocess(Waveform input) {
		return input;
	}
}

package interfaces.interfaceprocessor;

public class Apply {

	public static void process(Processor p,Object s) {
		System.out.println("Using Processor " + p.name());
		System.out.println(p.process(s));
	}
}

package interfaces.interfaceprocessor;

import interfaces.filters.Filter;
import interfaces.filters.Waveform;

public class FilterProcessor {

	public static void main(String[] args) {
		Waveform w = new Waveform();
		Apply.process(new FilterAdapter(new LowPass(1.0)), w);
		Apply.process(new FilterAdapter(new HighPass(2.0)), w);
		Apply.process(new FilterAdapter(new BandPass(3.0,4.0)), w);
	}
}

class FilterAdapter implements Processor {

	Filter filter;
	public FilterAdapter(Filter filter) {
		this.filter = filter;
	}
	
	@Override
	public String name() {
		return filter.name();
	}

	@Override
	public Object process(Object input) {
		return filter.process((Waveform) input);
	}
}

/**
Using Processor LowPass
Waveform 0
Using Processor HighPass
Waveform 0
Using Processor BandPass
Waveform 0
*/
```

在这种使用适配器的方式中，FilterAdapter的构造器接受你所拥有的接口 Filter，然后生成具有你所需要的Processor接口的对象。

将接口从具体实现中解耦使得接口可以应用于多种不同的具体实现，因此代码也就更具可复用性。

[以上代码对应地址](https://github.com/wangwren/Java/tree/master/Chapter9Exercise/src/interfaces/interfaceprocessor)

## Java中的多重继承

