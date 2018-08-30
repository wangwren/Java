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

