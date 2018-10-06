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

类是程序的一部分，每个类都有一个Class对象、换言之，每当编写并且编译了一个新类，就会产生一个Class对象（更恰当的说，是被保存在一个同名的.class文件中）。为了生成这个类的对象，运行这个程序的Java虚拟机（JVM）将使用被称为“类加载器”的子系统。