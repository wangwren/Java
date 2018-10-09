# 类型信息

> 运行时类型信息使得你可以在程序运行时发现和使用类型信息

本章将讨论Java是如何让我们在运行时识别对象和类的信息的。主要有两种方式：

- 一种是“传统的”RTTI，它假定我们在编译时已经知道了所有的类型；
- 另一种是“反射”机制，它允许我们在运行时发现和使用类的信息。

## 为什么需要RTTI

RTTI（Runtime Type Information）运行时类型信息。

```java
package typeinfo;

import java.util.Arrays;
import java.util.List;

abstract class Shape{
	void draw() {
		System.out.println(this + ".draw()");
	}
	abstract public String toString();
}

class Circle extends Shape{

	@Override
	public String toString() {
		return "Circle";
	}
}

class Square extends Shape{

	@Override
	public String toString() {
		return "Square";
	}
}

class Triangle extends Shape{

	@Override
	public String toString() {
		return "Triangle";
	}
}

public class Shapes {

	public static void main(String[] args) {
		List<Shape> shapeList = 
				Arrays.asList(new Circle(),new Square(),new Triangle());
		for(Shape shape : shapeList) {
			shape.draw();
		}
	}
}
/**
Circle.draw()
Square.draw()
Triangle.draw()
*/
```

如果某个对象出现在字符串表达式中（涉及“+”和字符串对象的表达式），toString()方法就会被自动调用，以生成表示该对象的String。每个派生类都要覆盖（从Object继承来的）toString()方法，这样draw()在不同情况下就打印出不同的消息（多态）。

在这个例子中，当把Shape对象放入List\<Shape\>的数组时会向上转型。但在向上转型为Shape的时候也丢失了Shape对象的具体类型。对于数组而言，它们只是Shape类的对象。

当从数组中取出元素时，这种容器--实际上它将所有的事物都当作Object持有--会自动将结果转型回Shape。**这是RTTI最基本的使用形式**，因为在Java中，所有的类型转换都是在运行时进行正确性检查的。这也是RTTI名字的含义：**在运行时，识别一个对象的类型。**

## Class对象

要理解 RTTI 中的工作原理，首先必须知道类型信息在运行时是如何表示的。这项工作是由称为 Class 对象的特殊对象完成的，它包含了与类有关的信息。事实上，Class对象就是用来创建类的所有的“常规”对象的。Java使用Class对象来执行其 RTTI，即使你正在执行的是类似转型这样的操作。Class类还拥有大量的使用RTTI的其他方式。

类是程序的一部分，每个类都有一个Class对象。换言之，每当编写并且编译了一个新类，就会产生一个Class对象（更恰当的说，是被保存在一个同名的.class文件中）。为了生成这个类的对象，运行这个程序的Java虚拟机（JVM）将使用被称为“类加载器”的子系统。

类加载器子系统实际上可以包含一条类加载器，但是只有一个原生类加载器，它是 JVM 实现的一部分。原生类加载器加载的是所谓的**可信类**，包括Java API类，它们通常是从本地盘加载的。在这条链中，通常不需要添加额外的类加载器，但是如果你有特殊需求（例如以某种特殊的方式加载类，以支持Web服务器应用，或者在网络中下载类），那么你有一种方式可以挂接额外的类加载器。

所有的类都是在对其第一次使用时，动态加载到JVM中的。当程序创建第一个对类的静态成员的引用时，就会加载这个类。这个证明构造器也是类的静态方法，即使在构造器之前并没有使用static关键字。因此，使用new操作符创建类的新对象也会被当作对类的静态成员的引用。

**因此，Java程序在它开始运行之前并非被完全加载，其各个部分是在必需时才加载的。**

类加载器首先检查这个类的 Class 对象是否已经加载。如果尚未加载，默认的类加载器就会根据类名查找 .class 文件（例如，某个附加类加载器可能会在数据库中查找字节码）。在这个类的字节码被加载时，它们会接受验证，以确保其没有被破坏，并且不包含不良Java代码（这是Java中用于安全防范目的的措施之一）。

一旦某个类的Class对象被载入内存，它就被用来创建这个类的所有对象。
```java
package test_01;

class Candy{
	static{
		System.out.println("Loading Candy");
	}
}

class Gum{
	static{
		System.out.println("Loading Gum");
	}
}

class Cookie{
	static{
		System.out.println("Loading Cookie");
	}
}

public class SweetShop {

	public static void main(String[] args) {
		System.out.println("inside main");
		new Candy();
		System.out.println("After creating Candy");
		try {
			Class.forName("Gum");
		} catch (ClassNotFoundException e) {
			System.out.println("Couldn't find Gum");
		}
		//转义
		System.out.println("After Class.forName(\"Gum\")");
		new Cookie();
		System.out.println("After creating Cookie");
	}
}
/**
Output:
inside main
Loading Candy
After creating Candy
Couldn't find Gum
After Class.forName("Gum")
Loading Cookie
After creating Cookie
*/
```

这里的每个类Candy、Gum和Cookie，都有一个static子句，该子句在类第一次被加载时执行。这时会有相应的信息打印出来，告诉我们呢这个类什么时候被加载了。在main()中，创建对象的代码被置于打印语句之间，以帮助我们判断加载的时间点。

从输出中可以看到，Class对象仅在需要的时候才被加载，static初始化是在类加载时进行的。

```java
Class.forName("Gum");
```
这个方法是Class类（所有Class对象都属于这个类）的一个static成员。Class对象就和其他对象一样，我们可以获取并操作它的引用（这也就是类加载器的工作）。forName()是取得Class对象的引用的一种方法，调用forName("X")将导致命名为 X 的类被初始化。它是用一个包含目标类的文本名（注意拼写和大小写）的String作输入参数，返回的是一个Class对象的引用，上面的代码忽略了返回值。对forName()的调用是为了它产生的“副作用”：如果类Gum还没有被加载就加载它。
