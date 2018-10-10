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

在前面的例子里，如果Class.forName()找不到你要加载的类，它会抛出异常ClassNotFoundException，这里我们只需简单报告问题，但在更严密的程序里，可能要在异常处理程序中解决这个问题。

无论何时，只要想在运行时使用类型信息，就必须首先获得恰当的Class对象的引用。Class.forName()就是实现此功能的便捷途径，因为你不需要为了获得Class引用而持有**该类型**的对象。但是，如果已经拥有了一个感兴趣的类型的对象，那就可以通过调用getClass()方法来获取Class引用了，这个方法属于根类Object的一部分，它将返回表示该对象的实际类型的Class引用。Class包含很多有用的方法：

```java
package test_01;

interface HasBatteries{}
interface Waterproof{}
interface Shoots{}

class Toy{
	Toy(){}
	Toy(int i){}
}

class FancyToy extends Toy implements HasBatteries,Waterproof,Shoots{
	FancyToy() {
		super(1);
	}
}

public class ToyTest {
	static void printInfo(Class cc){
		System.out.println("Class name: " + cc.getName() + 
				" is interface? [" + cc.isInterface() + "]");
		System.out.println("Simple name: " + cc.getSimpleName());
		System.out.println("Canonical name : " + cc.getCanonicalName());
	}
	
	public static void main(String[] args) {
		Class c = null;
		try {
			c = Class.forName("test_01.FancyToy");
		} catch (ClassNotFoundException e) {
			System.out.println("Can't find FancyToy");
			System.exit(1);
		}
		printInfo(c);
		
		for (Class face : c.getInterfaces()) {
			printInfo(face);
		}
		Class up = c.getSuperclass();
		Object obj = null;
		try {
			obj = up.newInstance();
		} catch (InstantiationException e) {
			System.out.println("Cannot instantiate");
			System.exit(1);
		} catch (IllegalAccessException e) {
			System.out.println("Cannot access");
			System.exit(1);
		}
		printInfo(obj.getClass());
	}
}
/**
Output:
Class name: test_01.FancyToy is interface? [false]
Simple name: FancyToy
Canonical name : test_01.FancyToy
Class name: test_01.HasBatteries is interface? [true]
Simple name: HasBatteries
Canonical name : test_01.HasBatteries
Class name: test_01.Waterproof is interface? [true]
Simple name: Waterproof
Canonical name : test_01.Waterproof
Class name: test_01.Shoots is interface? [true]
Simple name: Shoots
Canonical name : test_01.Shoots
Class name: test_01.Toy is interface? [false]
Simple name: Toy
Canonical name : test_01.Toy
*/
```
FancyToy继承自Toy并实现了HashBatteries、Waterproof和Shoots接口。在main()中，用forName()方法在适当的try语句块中，创建了一个Class引用，并将其初始化指向FancyToyClass。注意，在传递给forName()的字符串中，必须使用全限定名（包含包名）。

printInfo()使用getName()来产生全限定的类名，并分别使用getSimpleName()和getCanonicalName()(在Java SE5中引入的)来产生不包含包名的类名和全限定的类名。isInterface()方法如同其名，可以告诉你这个Class对象是否表示某个接口。因此，通过Class对象，可以发现你想要了解的类型的所有信息。

在main()方法中调用Class.getInterfaces()方法返回的是Class对象，它们表示在感兴趣的Class对象中所包含的接口。

如果你有一个Class对象，还可以使用getSupperclass()方法查询其直接基类，这将返回你可以用来进一步查询的Class对象。因此，可以运行时发现一个对象完整的类继承结构。

**Class的newInstance()方法是实现“虚拟构造器”的一种途径**，虚拟构造器允许你声明：“我不知道i的确切类型，但是无论如何要正确的创建你自己”。在前面的示例中，up仅仅只是一个Class引用，在编译期不具备任何更进一步的类型信息。当你创建新实例时，会得到Object引用，但是这个引用指向的是Toy对象。当然，在你可以发送Object能够接受的消息之外的任何消息之前，你必须更多地了解它，并执行某种转型。另外，**使用newInstance()来创建的类，必须带有默认的构造器**。
### 类字面常量
Java还提供了另一种方法来生成对Class对象的引用，即使用**类字面常量**。对上述程序来说，就像下面这样：
```java
FancyToy.class
```
这样做不仅更简单，而且更安全，因为它在编译时就会受到检查（因此不需要置于try语句块中）。并且它根除了对forName()方法的调用，所以它更高效。

类字面常量不仅可以应用于普通的类，也可以应用于接口、数组以及基本数据类型。另外，对于基本数据类型的包装器类，还有一个标准字段TYPE。TYPE字段是一个引用，指向对应的基本数据类型的Class对象，如下所示。（其左部分与右部分等价）

|    等价于     | 等价于   |
| :-------------: | ---- |
|     boolean.class      | Boolean.TYPE    |
|    char.class    | Character.TYPE    |
| byte.class | Byte.TYPE    |
|     short.class     | Short.TYPE    |
|     int.class     | Integer.TYPE    |
|     long.class     | Long.TYPE    |
|     float.class     | Float.TYPE    |
|     double.class     | Double.TYPE    |
|     void.class     | Void.TYPE    |

建议使用“.class”的形式，以保持与普通类的一致性。
注意，**当使用“.class”来创建Class对象的引用时，不会自动地初始化该Class对象**。为了使用类而做的准备工作实际包含三个步骤：

1. 加载，这是由类加载器执行的。该步骤将查找字节码（通常在classpath所指定的路径中查找，但这并非是必需的），并从这些字节码中创建一个Class对象。
2. 链接。在链接阶段将验证类中的字节码，为静态域分配存储空间，并且如果必需的话，将解析这个类创建的对其他类的所有引用。
3. 初始化。如果该类具有超类，则对其初始化，执行静态初始化器和静态初始化块。

初始化被延迟到了对静态方法（构造器隐式地是静态的）或者非常数静态域进行首次引用时才执行。
