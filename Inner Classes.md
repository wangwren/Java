# 内部类

> 可以将一个类的定义放在另一个类的定义内部，这就是内部类

## 创建内部类

创建内部类的方式就是把类的定义置于外围类的里面：

```java
public class Parcel1{
    class Contents{
        private int i = 11;
        public int value(){
            return i;
        }
    }
    class Destination{
        private String label;
        Destination(String whereTo){
            label = whereTo;
        }
        String readLabel(){
            return label;
        }
    }
    public void ship(String dest){
        Contents c =new Contents();
        Destination d = new Destination(dest);
        System.out.println(d.readLabel());
    }
    public static void main(String[] args){
        Parcel1 p = new Parcel1();
        p.ship("Tasmania");
    }
}
/**
Tasmania
*/
```

当在ship()方法里面使用内部类的时候，与使用普通类没什么区别。在这里，实际的区别只是内部类的名字是嵌套在Parcel1里面的。

更典型的情况是，外部类有一个方法，该方法返回一个指向内部类的引用，就像下面例子中的 to() 和 contents()方法中看到的。

```java
package innerclasses;

public class Parcel2 {

	class Contents{
		private int i = 11;
		public int value() {
			return i;
		}
	}
	class Destination{
		private String label;
		Destination(String whereTo){
			label = whereTo;
		}
		String readLabel() {
			return label;
		}
	}
	
	public Destination to(String s) {
		return new Destination(s);
	}
	public Contents contents() {
		return new Contents();
	}
	public void ship(String dest) {
		Contents c = contents();
		Destination d = to(dest);
		System.out.println(d.readLabel());
	}
	public static void main(String[] args) {
		Parcel2 p = new Parcel2();
		p.ship("Tasmania");
		
		Parcel2.Contents c = p.contents();
		Parcel2.Destination d = p.to("Borneo");
	}
}
/**
Tasmania
*/
```

如果想从外部类的非静态方法之外的任意位置创建某个内部类，那么必须像在 main() 方法中那样，具体地指明这个对象的类型：OuterClassName.InnerClassName。（外部类.内部类）

## 链接到外部类

当生成一个内部类的对象时，此对象与制造它的外围对象之间就有了一种联系，所以**它能访问其外围对象的所有成员，而不需要任何特殊条件**。此外，内部类还拥有其外围类的所有元素的访问权。

```java
package innerclasses;

interface Selector{
	boolean end();
	Object current();
	void next();
}

public class Sequence {

	private Object[] items;
	private int next = 0;
	public Sequence(int size) {
		items = new Object[size];
	}
	public void add(Object x) {
		if(next < items.length) {
			items[next++] = x;
		}
	}
	
	private class SequenceSelector implements Selector{

		private int i = 0;
		
		@Override
		public boolean end() {
			return i == items.length;
		}

		@Override
		public Object current() {
			return items[i];
		}

		@Override
		public void next() {
			if(i < items.length) {
				i++;
			}
		}
	}
	
	public Selector selector(){
		return new SequenceSelector();
	}
	
	public static void main(String[] args) {
		Sequence sequence = new Sequence(10);
		for(int i = 0;i < 10;i++) {
			sequence.add(Integer.toString(i));
		}
		Selector selector = sequence.selector();
		
		while(!selector.end()) {
			System.out.print(selector.current() + " ");
			selector.next();
		}
	}
}
/**
0 1 2 3 4 5 6 7 8 9 
*/
```

最初看到 SequenceSelector ，可能会觉得他只不过是另一个内部类罢了。但请仔细观察它，注意方法 end() 、current() 和 next() 都用到了 objects（即items），这是一个引用，它并不是 SequenceSelector的一部分，而是外围类中的一个 private字段。然而内部类可以访问其外围类的方法和字段，就像自己拥有它似的，这带来了很大的方便。

所以内部类自动拥有对其外围类所有成员的访问权。当某个外围类的对象创建了一个内部类对象时，此内部类对象必定会秘密地捕获一个指向那个外围类对象的引用。然后，在你访问此外围类的成员时，就是用那个引用来选择外围类的成员。

## 使用 .this 与 .new

如果需要**生成对外部类对象的引用**，可以使用外部类的名字后面紧跟圆点和 this。这样产生的引用自动地具有正确的类型，这一点在编译期就被知晓并受到检查，因此没有任何运行时开销。

```java
public class DotThis{
    void f(){
        System.out.println("DotThis.f()");
    }
    public class Inner{
        public DotThis outer(){
            return DotThis.this;
        }
    }
    public Inner inner(){
        return new Inner();
    }
    public static void main(String[] args){
        DotThis dt = new DotThis();
        DotThis.Inner dti = dt.inner();
        dti.outer().f();	//使用内部类的引用获取到外部类的引用，接着调用外部类的方法
    }
}
/**
DotThis.f()
*/
```

有时可能想要告知某些其他对象，去创建其某个内部类的对象。要实现此目的，必须在 new 表达式中提供对其他外部类对象的引用，这是需要使用 .new语法。

```java
public class DotNew{
    public class Inner{
        public static void main(String[] args){
            DotNew dn = new DotNew();
            DotNew.Inner dni = dn.new DotNew();
        }
    }
}
```

要想直接创建内部类的对象，不能去引用外部类的名字 DotNew ，而是必须使用外部类的对象来创建该内部类对象，就像在上面的程序中所看到的那样。

## 内部类与向上转型

当将内部类向上转型为其基类，尤其是转型为一个接口的时候，内部类就有了用武之地。（从实现了某个接口的对象，得到对此接口的引用，与向上转型为这个对象的基类，实质上效果是一样的。）这是因为此内部类---某个接口的实现---能够完全不可见。所得到的只是指向基类或接口的引用，所以能够很方便地隐藏实现细节。

```java
package innerclasses;

public interface Destination {

	String readLabel();
}



package innerclasses;

public interface Contents {

	int value();
}
```

现在Contents和Destination表示客户端程序员可用的接口。（记住，接口的所有成员自动被设置为public）

当取得了一个指向基类或接口的引用时，甚至可能无法找出它确切的类型，看下面例子。

```java
package innerclasses;

class Parcel4{
	private class PContents implements Contents{

		private int i = 11;
		@Override
		public int value() {
			return i;
		}
	}
	protected class PDestination implements Destination{

		private String label;
		private PDestination(String whereTo) {
			label = whereTo;
		}
		@Override
		public String readLabel() {
			return label;
		}
	}
	public Destination destination(String s) {
		return new PDestination(s);
	}
	public Contents contents() {
		return new PContents();
	}
}

public class TestParcel {

	public static void main(String[] args) {
		Parcel4 p = new Parcel4();
		Contents c = p.contents();
		Destination d = p.destination("Tasmania");
		//非法，无法访问私有的class。因为PContents是private的
		//Parcel4.PContents pc = p.new PContents();
	}
}
```

Parcel4中增加了一些新东西：内部类PContents是private，所以除了Parcel4，没人能访问它。PDestination是protected，所以只有Parcel4及其子类，还有与Parcel4同一个包中的类能访问PDestination，其他类都不能访问PDestination。这意味着，如果客户端 程序员想了解或访问这些成员，那是要受到限制的。实际上，甚至不能向下转型成private内部类（或protected内部类，除非是继承自它的子类），因为不能访问其名字，就像在TestParcel类中看到的那样。于是，private内部类给类的设计者提供了一种途径，通过这种方式可以完全阻止任何依赖于类型的编码，并且完全隐藏了实现的细节。此外，从客户端程序员的角度来看，由于不能访问任何新增的、原本不属于公共接口的方法，所以扩展接口是没有价值的。

## 在方法和作用域内的内部类

可以在一个方法里面或者在任意的作用域内定义内部类。这么做有两个理由：

1. 如前所示，你实现了某类型的接口，于是可以创建并返回对其的引用。
2. 你要解决一个复杂的问题，想创建一个类来辅助你的解决方案，但是又不希望这个类时公共可用的。

展示在方法作用域内（而不是在其他类的作用域内）创建一个完整的类。这被称作**局部内部类**：

```java
public class Parcel5{
    public Destination destination(String s){
        class PDestination implements Destination{
            private String label;
            private PDestination(String whereTo){
                label = whereTo;
            }
            public String readLabel(){
                return label;
            }
        }
        return new PDestination(s);
    }
    public static void main(String[] args){
        Parcel5 p = new Parcel5();
        Destination d = p.destination("Tasmania");
    }
}
```

PDestination类是 destination() 方法的一部分，而不是 Parcel5 的一部分。所以，在 destination() 之外不能访问PDestination。注意出现在return语句中的向上转型---返回的是Destination引用，它是PDestination的基类。当然，在destination()中定义了内部类PDestination，并不意味着一旦destination()方法执行完毕，PDestination就不可用了。

可以在同一个子目录下的任意类中对某个内部类使用类标识符PDestination，这并不会有命名冲突。

下面的例子展示了如何在任意的作用域内嵌入一个内部类：

```java
public class Parcel6{
    private void internalTracking(boolean b){
        if(b){
            class TrackingSlip{
                private String id;
                TrackingSlip(String s){
                    id = s;
                }
                String getSlip(){
                    return id;
                }
            }
            TrackingSlip ts = new TrackingSlip("slip");
            String s = ts.getSlip();
        }
        //Can't use it here!Out of scope
        //!TrackingSlip ts = new TrackingSlip("x");
    }
    public void track(){
        internalTracking(true);
    }
    public static void main(String[] args){
        Parcel6 p = new Parcel6();
        p.track();
    }
}
```

TrackingSlip 类被嵌入在if语句的作用域内，这并不是说该类的创建时有条件的，它其实与别的类一起编译过了。然而，在定义TrackingSlip的作用域之外，它是不可用；除此之外，它与普通的类一样。

## 匿名内部类

```java
package innerclasses;

public class Parcel7 {

	public Contents contents() {
		return new Contents() {//insert a class definition
			private int i = 11;

			@Override
			public int value() {
				return i;
			}
		};
	}
	
	public static void main(String[] args) {
		Parcel7 p = new Parcel7();
		Contents c = p.contents();
	}
}
```

contents()方法将返回值得生成与表示这个返回值的类的定义结合在一起！另外，这个类时匿名的，它没有名字。更糟的是，看起来似乎是正要创建一个 Contents 对象。但是然后（在到达语句结束的分号之前）你却说：“等等，我想要在这里插入一个类的定义。”

这种奇怪的语法是指**创建一个继承自Contents的匿名类的对象**。通过 new表达式返回的引用被自动向上转型为对 Contents的引用。上述匿名内部类的语法是下述形式的简化形式：

```java
public class Parcel7b{
    class MyContents implements Contents{
        private int i = 11;
        public int value(){
            return i;
        }
    }
    public Contents contents(){
        return new MyContents();
    }
    public static void main(String[] args){
        Parcel7b p = new Parcel7b();
        Contents c = p.contents();
    }
}
```

在这个匿名内部类中，使用了默认的构造器来生成Contents。下面的代码展示的是，如果你的基类需要一个有参数的构造器，应该怎么办：

```java
package innerclasses;

public class Parcel8 {

	public Warpping warpping(int x) {
		return new Warpping(x) {
			public int value() {
				return super.value() * 47;
			}
		};
	}
	
	public static void main(String[] args) {
		Parcel8 p = new Parcel8();
		Warpping w = p.warpping(10);
	}
}
```

只需简单地传递合适的参数给基类的构造器即可，这里是将 x 传进 new Warpping(x) 。尽管Warpping只是一个具有具体实现的普通类，但它还是被其导出类当作公共“接口”来使用:

```java
package innerclasses;

public class Warpping {

	private int i;
	public Warpping(int x) {
		i = x;
	}
	public int value() {
		return i;
	}
}
```

Warpping拥有一个要求传递一个参数的构造器，这使得事情变得更加有趣了。

在匿名内部类末尾的分号，并不是用来标记此内部类结束的，实际上，它标记的是表达式的结束，只不过这个表达式正巧包含了匿名内部类罢了。因此，这与别的地方使用的分号是一致的。

在匿名类中定义字段时，还能够对其执行初始化操作：

```java
package innerclasses;

public class Parcel9 {
	
	/*
	 *  Java 编程思想用的是 Java 5 的编译器。
	 *  在 Java 8 之前的所有版本的 Java，
	 *  局部内部类和匿名内部类访问的局部变量必须由final修饰，
	 *  java8开始，可以不加final修饰符，由系统默认添加。
	 *  java将这个功能称为：Effectively final 功能。
	 */
	public Destination destination(final String dest) {//java8 可以不加final
		
		return new Destination() {
			private String label = dest;
			@Override
			public String readLabel() {
				return label;
			}
		};
	}
	public static void main(String[] args) {
		Parcel9 p = new Parcel9();
		Destination d = p.destination("Tasmania");
	}
}
```

如果定义一个匿名内部类，并且**希望它使用一个在其外部定义的对象，那么编译器会要求其参数引用是 final 的**，就像在destination()的参数中看到的那样。如果忘记了，将会得到一个编译时错误消息（在Java8之前，会有报错消息，java8就没有报错消息了）。

如果想做一些类似构造器的行为，在匿名类中不可能有命名构造器（因为它根本没有名字！），但通过**实例初始化**，就能够达到为匿名内部类创建一个构造器的效果，就像这样：

```java
package innerclasses;

abstract class Base{
	public Base(int i) {
		System.out.println("Base constructor,i = " + i);
	}
	public abstract void f();
}

public class AnonymousConstructor {

	public static Base getBase(int i){
		return new Base(i) {
			
			//代码块，实例初始化
			{
				System.out.println("Inside instance initializer");
			}
			
			@Override
			public void f() {
				System.out.println("In anonymous f()");
			}
		};
	}
	public static void main(String[] args) {
		Base base = getBase(47);
		base.f();
	}
}
/**
Base constructor,i = 47
Inside instance initializer
In anonymous f()
*/
```

在此例中，**不要求变量 i 一定是final的。因为 i 被传递给匿名类的基类的构造器，它并不会在匿名类内部被直接调用。**

下例是带实例初始化的“parcel”形式。**注意destination()的参数必须是final的，因为它们是在匿名类内部使用的。**

```java
package innerclasses;

public class Parcel10 {

	public Destination destination(final String dest,final float price) {
		return new Destination() {
			private int cost;
			{
				cost = Math.round(price);
				if(cost > 100) {
					System.out.println("Over budget!");
				}
			}
			
			private String label = dest;
			
			@Override
			public String readLabel() {
				return label;
			}
		};
	}
	public static void main(String[] args) {
		Parcel10 p = new Parcel10();
		Destination d = p.destination("Tasmania", 101.395F);
	}
}
/**
Over budget!
*/
```

对于匿名内部类而言，实例初始化的实际效果就是构造器。当然它受到了限制---**你不能重载实例初始化方法，所以你仅有一个这样的构造器。**

匿名内部类与正规继承相比有些受限，**因为匿名内部类既可以扩展类，也可以实现接口，但是两者不能兼备。而且如果是实现接口，也只能实现一个接口。**

### 再访工厂方法

```java
package innerclasses;

interface Service{
	void method1();
	void method2();
}

interface ServiceFactory{
	Service getService();
}

class Implementation1 implements Service{
	private Implementation1(){
		
	}

	@Override
	public void method1() {
		System.out.println("Implementation1.method1");
	}

	@Override
	public void method2() {
		System.out.println("Implementation1.method2");
	}
	public static ServiceFactory factory = 
			new ServiceFactory() {

				@Override
				public Service getService() {
					return new Implementation1();
				}
	};
}

class Implementation2 implements Service{
	private Implementation2(){
		
	}

	@Override
	public void method1() {
		System.out.println("Implementation2.method1");
	}

	@Override
	public void method2() {
		System.out.println("Implementation2.method2");
	}
	public static ServiceFactory factory = 
			new ServiceFactory() {

				@Override
				public Service getService() {
					return new Implementation2();
				}
	};
}

public class Factories {

	public static void serviceConsumer(ServiceFactory fact) {
		Service s = fact.getService();
		s.method1();
		s.method2();
	}
	public static void main(String[] args) {
		serviceConsumer(Implementation1.factory);
		serviceConsumer(Implementation2.factory);
	}
}
/**
Implementation1.method1
Implementation1.method2
Implementation2.method1
Implementation2.method2
*/
```

现在用Implementation1和Implementation2的构造器都可以是private的，并且没有任何必要去创建作为工厂的具名类。另外，你经常值需要单一的工厂对象，因此在本例中它被创建为Service实现中的一个static域。这样所产生语法也更具有实际意义。

请记住在第9章最后给出的建议：**优先使用类而不是接口。如果你的设计中需要某个接口，你必须了解它。否则，不到迫不得已，不要将其放到你的设计中。**

## 嵌套类

如果不需要内部类对象与其外围类对象之间有联系，那么可以将内部类声明为static。这通常称为**嵌套类**。

理解static应用于内部类时的含义，就必须记住，普通的内部类对象隐式地保存了一个引用，指向创建它的外围类对象。然而，当内部类时static时，就不是这样了。嵌套类意味着：

1. 要创建嵌套类的对象，并不需要其外围类的对象。
2. 不能从嵌套类的对象中访问非静态的外围类对象。

嵌套类与普通的内部类还有一个区别。普通内部类的字段与方法，只能放在类的外部层次上，所以普通的内部类不能有static数据和static字段，也不能包含嵌套类。但是嵌套类可以包含所有这些东西：

```java
package innerclasses;

public class Parcel11 {

	private static class ParcelContents implements Contents{
		
		private int i = 11;
		@Override
		public int value() {
			return i;
		}
	}
	protected static class ParcelDestination implements Destination{
		
		private String label;
		private ParcelDestination(String whereTo) {
			label = whereTo;
		}
		@Override
		public String readLabel() {
			return label;
		}
		public static void f() {
			}
		static int x = 10;
		static class AnotherLevel{
			public static void f() {
			}
			static int x = 10;
		}
	}
	public static Destination destination(String s) {
		return new ParcelDestination(s);
	}
	public static Contents contents() {
		return new ParcelContents();
	}
	public static void main(String[] args) {
		Contents c = contents();
		Destination d = destination("Tasmania");
	}
}
```

在main()中，没有任何Parcel11的对象是必须的；而是使用选取static成员的普通语法来调用方法---这些方法返回对Contents和Destination的引用。

就像在本章前面看到的那样，在一个普通的（非static）内部类中，通过一个特殊的this引用可以链接到其外围类对象。嵌套类就没有这个特殊的 this 引用，这使得它类似于一个static方法。

### 接口内部的类

正常情况下，不能在接口内部放置任何代码，但嵌套类可以作为接口的一部分。放到接口中的任何类都自动地是public和static。因为类是static的，只是将嵌套类置于接口的命名空间内，这并不违反接口的规则。甚至可以在内部类中实现其外围接口，就像下面这样：

```java
package innerclasses;

public interface ClassInInterface {
	void howdy();
	class Test implements ClassInInterface{

		@Override
		public void howdy() {
			System.out.println("Howdy!");
		}
		public static void main(String[] args) {
			new Test().howdy();
		}
	}
}
/**
Howdy!
*/
```

如果想要创建某些公共代码，使得它们可以被某个接口的所有不同实现所共用，那么使用接口内部的嵌套类显得很方便。

### 从多层嵌套类中访问外部类成员

