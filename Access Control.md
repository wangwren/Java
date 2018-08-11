# 访问控制权限

> 访问控制（或隐藏具体实现）与“最初的实现并不恰当”有关

Java提供了访问权限修饰词，以供类库开发人员向客户端程序员指明哪些是可用的，哪些是不可用的。

访问权限的控制等级，从最大权限到最小权限依次为：public 、protected 、包访问权限（没有关键词）和private。

## 包：库单元

包内含有一组类，它们在单一的名字空间之下被组织在一起。

例如，在Java的标准发布中有一个工具库，它被组织在java.util名字空间下。java.util中有一个叫做ArrayList的类，使用ArrayList的一种方式是用其全名java.utilArrayList来指定。

```java
public class FullQualification{
    public static void main(String[] args){
        java.util.ArrayList list = new java.util.ArrayList();
    }
}
```

这立刻就是程序变得冗长了，因此你可能想转而使用 import 关键字。如果想要导入单个的类，可以在 import 语句中命名该类：

```java
import java.util.ArrayList;
public class SingleImport{
    public static void main(String[] args){
        ArrayList list = new ArrayList();
    }
}
```

这样做 java.util 中的其他类仍旧是都不可用的。要想导入其中的所有类，只需要使用 “\*”。`import java.util.*`

之所以要导入，就是要提供一个管理名字空间的机制。所有类成员的名称都是彼此隔离的。A类中的方法 f() 与B类中具有相同特征标记(参数列表)的方法 f() 不会彼此冲突。

当编写一个Java源文件时，此文件通常被称为编译单元（有时也被称为转译单元）。每个编译单元都必须有一个后缀名 .java ，而在编译单元内则可以有一个public 类，**该类的名称必须与文件的名称相同**（包括大小写，但不包括文件的后缀 .java）。每个编译单元**只能有一个** public  类，否则编译器就不会接受。如果在编译单元之中还有额外的类的话，那么在**包之外**的世界是无法看见这些类的，这是因为它们不是 public 类，而且它们主要用来为主 public 类提供支持。

### 代码组织

Java可运行程序是一组可以打包并压缩为一个Java文档文件(JAR，使用Java的jar文档生成器)的 .class 文件。

类库实际上是一组类文件。其中每个文件都有一个 public 类，以及任意数量的非 public 类。因此每个文件都有一个构件。如果希望这些构件（每一个都有它们自己的独立的 .java和 .class文件）从属于同一个群组，就可以使用关键字 package。

如果使用 package 语句，它**必须是文件中除注释意外的第一句程序代码。**在文件起始处写：

```java
package access;
```

就表示在声明该编译单元是名为 access 的类库一部分。或者说，正在声明该编译单元中的 public 类名称是位于 access 名称的保护伞下。任何想要使用该名称的人都必须使用前面给出的选择，指定全名或者与access结合使用关键字 import。（注意：**Java包的命名规则全部使用小写字母，包括中间的字也是如此**）。

### 创建独一无二的包名

按照惯例，package名称的第一部分是类的创建者的反顺序的 Internet 域名。

如果两个含有相同名称的类库以 “\*”形式同时导入，比如：

```java
import net.mindview.simple.*;
import java.util.*;
```

如果`net.mindview.simple`类库中有一个Vector类，`java.util`类库中是真有这么一个类。那么如果创建一个Vector类的话：`Vector v = new Vector()`到底取哪个Vector类？编译器就会提示出错误信息，强制明确指明，如果想要一个标准的Java Vector类，就得这样写：

```java
java.util.Vector v = new java.util.Vector();
```

## Java访问权限修饰词

### 包访问权限

默认访问权限没有任何关键字，但通常是指**包访问权限**。这就意味着当前的包中的所有其他类对那个成员都有访问权限，但对于这个包之外的所有类，这个成员却是private。

取得对某成员的访问权的唯一途径是：

- 使该成员成为public。于是，无论是谁，无论在哪，都可以访问该成员。
- 通过不加访问权限修饰词并将其他类放置于同一个包内的方式给成员赋予包访问权。于是包内的其他类也就可以访问该成员了。
- 继承而来的类既可以访问public成员也可以访问protected成员（但访问private成员却不行）。只有在两个类都处于同一个包内时，它才可以访问包访问权限的成员。
- 提供访问器和变异器方法，也称作get/set方法，以读取和改变数值。

### public：接口访问权限

使用关键字 public，就意味着 public 之后紧跟着的成员声明自己对每个人都是可用的，尤其是使用类库的客户程序员更是如此。

### private：你无法访问

除了包含该成员的类之外，其他任何类都无法访问这个成员。

```java
class Sundae{
    private Sundae(){} //构造器私有，外界不可实例化该类
    static Sundae makeASundae(){
        return new Sundae();
    }
}
public class IceCream{
    public static void main(String[] args){
        Sundae s = Sundae.makeASundae();
    }
}
```

这是一个说明private终有其用武之地的示例：可能想控制如何创建对象，并阻止别人直接访问某个特定的构造器（或全部构造器）。在上面例子中，不能通过构造器来创建Sundae对象，而必须调用 makeASundae() 方法来达到此目的。此例还有一个效果：既然默认构造器是唯一定义的构造器，并且它是 private的，那么它将阻碍对此类的继承。

### protected：继承访问权限

关键字protected处理的是继承的概念。

如果创建了一个新包，并自另一个包中继承类，那么唯一可以访问的成员就是源包的 public 成员。（当然，如果在同一个包内执行继承工作，就可以操纵所有的拥有包访问权限的成员。）有时，基类的创建者会希望有某个特定成员，把对它的访问权限赋予派生类而不是所有类。这就需要protected来完成这一工作。protected也提供包访问权限，也就是说，相同包内的其他类可以访问protected元素。

|    访问权限     | 类   | 包   | 子类 | 其他包 |
| :-------------: | ---- | ---- | ---- | ------ |
|     public      | √    | √    | √    | √      |
|    protected    | √    | √    | √    | ×      |
| default（默认） | √    | √    | ×    | ×      |
|     private     | √    | ×    | ×    | ×      |

## 接口和实现

访问权限的控制常被称为是具体实现的隐藏。把数据和方法包装进类中，以及具体实现的隐藏，常共同被称作是**封装**。其结果是一个同时带有特征和行为的数据类型。

## 类的访问权限

- 每个编译单元（文件）都只能有一个public类。这表示，每个编译单元都有单一的公共接口，用public类来实现。该接口可以按要求包含众多的支持包访问权限的类。如果在某个编译单元内有一个以上的public类，编译器就会给出出错信息。
- public类的名称必须完全与含有该编译单元的文件名相匹配，包括大小写。
- 虽然不是很常用，但编译单元内完全不带public类也是可能的。在这种情况下，可以随意对文件命名。

注意：**类既不可以是private的**（这样会使得除该类之外，其他任何类都不可以访问它），**也不可以是protected的**。所以对类的访问权限，仅有两个选择：包访问权限和public。如果不希望其他任何人对该类拥有访问权限，可以把类的构造器指定为private，从而阻止任何人创建该类的对象，但是有一个例外，就是在该类的static成员内部可以创建。

```java
class Soup1{
    private Soup1(){}
    public static Soup1 makeSoup(){
        return new Soup1;
    }
}
class Soup2{
    private Soup2(){}
    private static Soup2 sp2 = new Soup2(); //类内部可访问
    public static Soup2 access(){
        return sp2;
    }
    public void f(){}
}
public class Lunch{
    void testPrivate(){
        // ! Soup1 soup = new Soup1() 不能够实例化
    }
    void testStatic(){
        Soup1 soup = Soup1.makeSoup();
    }
    void testSingleton(){
        Soup2.access.f();
    }
}
```

Soup1类和Soup2类展示了如何通过将所有的构造器指定为 private 来阻止直接创建某个类的实例。请牢记，**如果没有明确地至少创建一个构造器的话，就会帮你创建一个默认构造器（不带有任何参数的构造器）**。如果我们自己写了默认构造器，那么就不会自动创建它了。如果把该构造器指定为 private，那么就谁也无法创建该类的对象了。但是如果还想要使用该类的话，上面的例子给出了两个选择：Soup1中，创建一个 static 方法，它创建一个新的 Soup1对象并返回一个对它的引用。

Soup2用到了所谓的设计模式。这种特定的模式被称为singleton（单例），这是因为你始终只能创建它的一个对象。Soup2类的对象作为Soup2的一个static private成员而创建的，所以有且仅有一个，而且除非是通过public方法access()，否则无法访问到它的。

相同目录下的所有不具有明确package声明的文件，都被视作是该目录下默认包的一部分。然而，如果该类的某个 static 成员是 public 的话，则客户端程序员仍旧可以调用该static成员，尽管他们并不能生成该类的对象。