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

在导出类中，不强制要求必须有一个是抽象的或“具体的”（没有任何抽象方法的）基类。如果要从一个非接口的类继承，那么只能从一个类去继承。其余的元素都必须是接口。需要将所有的接口都置于 implements 关键字之后，用**逗号**将它们一一隔开。可以继承任意多个接口，并可以向上转型为每个接口，因为接口都是一个独立的类型。

```java
package interfaces.adventure;

interface CanFight{
	void fight();
}

interface CanSwin{
	void swim();
}

interface CanFly{
	void fly();
}

class ActionCharacter{
	public void fight() {
		
	}
}

class Hero extends ActionCharacter implements CanFight,CanSwin,CanFly{
	//CanFight接口与ActionCharacter类中的fight()方法的特征签名是一样的，而且在Hero
	//中并没有提供 fight() 的定义。
	@Override
	public void fly() {
		
	}

	@Override
	public void swim() {
		
	}
	
}

public class Adventure {

	public static void t(CanFight x) {
		x.fight();
	}
	public static void u(CanSwin x) {
		x.swim();
	}
	public static void v(CanFly x) {
		x.fly();
	}
	public static void w(ActionCharacter x) {
		x.fight();
	}
	public static void main(String[] args) {
		Hero h = new Hero();
		t(h);	//Treat it as a CanFight
		u(h);	//Treat it as a CanSwim
		v(h);	//Treat it as a CanFly
		w(h);	//Treat it as an ActionCharacter
	}
}
```

Hero组合了具体类ActionCharacter和接口CanFight、CanSwim和CanFly。当通过这种方式将一个具体类和多个接口组合到一起时，这个具体类必须放在前面，后面跟着的才是接口（否则编译器会报错）。

**注意：**，CanFight接口与ActionCharacter类中的fight()方法的特征签名是一样的，而且，在 Hero 中并没有提供 fight()的定义。可以扩展接口，但是得到的只是另一个接口。当想要创建对象时，所有的定义首先必须都存在。**即使 Hero 没有显示地提供 fight() 的定义，其定义也因 ActionCharacter而随之而来，这样就使得创建 Hero对象成为了可能。**

一定要记住，前面的例子所展示的就是使用接口的核心原因：

- 为了能够向上转型为多个基类型（以及由此而带来的灵活性）。
- 使用接口的第二个原因却是与使用抽象基类相同：防止客户端程序员创建该类的对象，并确保这仅仅是建立一个接口。

问题：我们应该使用接口还是抽象类？

> 如果要创建不带任何方法定义和成员变量的基类，那么就应该选择接口而不是抽象类。

## 通过继承来扩展接口

```java
package interfaces.adventure;

interface Monster{
	void menace();
}

interface DangerousMonster extends Monster{
	void destory();
}

interface Lethal{
	void kill();
}

class DragonZilla implements DangerousMonster{

	@Override
	public void menace() {
		
	}

	@Override
	public void destory() {
		
	}
}

interface Vampire extends DangerousMonster,Lethal{
	void drinkBlood();
}

class VeryBadVampire implements Vampire{

	@Override
	public void destory() {
		
	}

	@Override
	public void menace() {
		
	}

	@Override
	public void kill() {
		
	}

	@Override
	public void drinkBlood() {
		
	}
}

public class HorrorShow {

	static void u(Monster b) {
		b.menace();
	}
	static void v(DangerousMonster d) {
		d.menace();
		d.destory();
	}
	
	static void w(Lethal l) {
		l.kill();
	}
	public static void main(String[] args) {
		DangerousMonster barney = new DragonZilla();
		u(barney);
		v(barney);
		Vampire vlad = new VeryBadVampire();
		u(vlad);
		v(vlad);
		w(vlad);
	}
}
```

**在Vampire中使用的语法仅适用于接口继承。一般情况下，只可以将extends用于单一类，但是可以引用多个基类接口。就像所看到的，只需用逗号将接口名一一分隔开即可。**

## 适配接口

接口一种常见用法就是前面提到的**策略设计模式**，此时你编写一个执行某些操作的方法，而方法将接受一个同样是你指定的接口。你主要就是要声明：**你可以用任何你想要的对象来调用我的方法，只要你的对象遵循我的接口**，这使得你的方法更加灵活、通用，并更具可复用性。

例如，Java SE5的 Scanner类的构造器接受的就是一个 Readable接口。你会发现 Readable没有用作Java标准类库中其他任何方法的参数，它是单独为 Scanner创建的，以使得 Scanner 不必将其参数限制为某个特定类。通过这种方式，Scanner可以作用于更多的类型。如果你创建了一个新的类，并且想让 Scanner可以作用于它，那么你就应该让它成为 Readable。

```java
package interfaces.adventure;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Random;
import java.util.Scanner;

public class RandomWords implements Readable {

	private static Random rand = new Random(47);
	private static final char[] capitals = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final char[] lowers = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private static final char[] vowels = "aeiou".toCharArray();
	private int count;
	public RandomWords(int count) {
		this.count = count;
	}
	
	@Override
	public int read(CharBuffer cb) throws IOException {
		
		if(count-- == 0)
			return -1;
		
		cb.append(capitals[rand.nextInt(capitals.length)]);
		for(int i = 0;i < 4;i++) {
			cb.append(vowels[rand.nextInt(vowels.length)]);
			cb.append(lowers[rand.nextInt(lowers.length)]);
		}
		cb.append(" ");
		return 10;
		
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(new RandomWords(10));
		while(sc.hasNext()) {
			System.out.println(sc.next());
		}
	}

}
/**
Yazeruyac
Fowenucor
Goeazimom
Raeuuacio
Nuoadesiw
Hageaikux
Ruqicibui
Numasetih
Kuuuuozog
Waqizeyoy
*/
```

Readable接口只要求实现 read()方法，在read()内部，将输入内容添加到CharBuffer参数中，或者在没有任何输入时返回-1。

## 接口中的域

因为放入**接口中的域都自动是 static 和 final 的**，所以接口就成为了一种很便捷的用来创建常量组的工具。

在JavaSE5之前，这是产生与C或C++中的enum（枚举类型）具有相同效果的类型的唯一途径。

有了JavaSE5，就可以使用更加强大而灵活的enum关键字，因此，使用接口来群组常量已经显得没什么意义了。

### 初始化接口中的域

在接口中定义的域不能是“空final”，但是可以被非常量表达式初始化。

```java
import java.util.*;
public interface RandVals{
    Random RAND = new Random(47);
    int RANDOM_INT = RAND.nextInt(10);
    long RANDOM_LONG = RAND.nextLong() * 10;
    float RANDOM_FLOAT = RAND.nextLong() * 10;
    double RANDOM_DOUBLE = RAND.nextDouble() * 10;
}
```

既然域是 static 的，它们就可以在类第一次被加载时被初始化，这发生在任何域首次被访问时。

```java
public class TestRandVals{
    public static void main(String[] args){
        System.out.println(RandVals.RANDOM_INT);
        System.out.println(RandVals.RANDOM_LONG);
        System.out.println(RandVals.RANDOM_FLOAT);
        System.out.println(RandVals.RANDOM_DOUBLE);
    }
}
/**
8
-32032247016559954
-8.5939291E18
5.779976127815049
*/
```

当然，这些域不是接口的一部分，**它们的值被存储在该接口的静态存储区域内。**

## 接口与工厂

接口是实现多重继承的途径，而生成遵循某个接口的对象的典型方式就是**工厂方法设计模式**。这与直接调用构造器不同，我们在工厂对象上调用的是创建方法，而该工厂对象将生成接口的某个实现的对象。理论上，我们的代码将完全与接口的实现分离，这就使得我们可以透明地将某个实现替换为另一个实现。

```java
package interfaces.adventure;

interface Service{
	void method1();
	void method2();
}

interface ServiceFactory{
	Service getService();
}

class Implementation1 implements Service{

	public Implementation1() {

	}
	
	@Override
	public void method1() {
		System.out.println("Implementation1 method1");
	}

	@Override
	public void method2() {
		System.out.println("Implementation1 method2");
	}
}

class Implementation1Factory implements ServiceFactory{

	@Override
	public Service getService() {
		return new Implementation1();
	}
}

class Implementation2 implements Service{

	public Implementation2() {

	}
	
	@Override
	public void method1() {
		System.out.println("Implementation2 method1");
	}

	@Override
	public void method2() {
		System.out.println("Implementation2 method2");
	}
}

class Implementation2Factory implements ServiceFactory{

	@Override
	public Service getService() {
		return new Implementation2();
	}
}

public class Factories {

	public static void serviceConsumer(ServiceFactory fact) {
		Service s = fact.getService();
		s.method1();
		s.method2();
	}
	
	public static void main(String[] args) {
		serviceConsumer(new Implementation1Factory());
		serviceConsumer(new Implementation2Factory());
	}
}
/**
Implementation1 method1
Implementation1 method2
Implementation2 method1
Implementation2 method2
*/
```

如果不是用工厂方法，代码就必须在某处指定要创建的Service的确切类型，以便调用合适的构造器。

**恰当的原则应该是优先选择类而不是接口。从类开始，如果接口的必需性变得非常明确，那么就进行重构。接口是一种重要的工具，但是它们容易被滥用。**