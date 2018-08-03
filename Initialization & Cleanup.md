# 初始化与清理

> 随着计算机革命的发展，“不安全”的编程方式已逐渐成为编程代价高昂的主因之一

## 用构造器确保初始化

构造器采用与类相同的名称。

```java
class Rock{
    Rock(){
        System.out.print("Rock ");
    }
}

public class SimpleConstructor{
    public static void main(String[] args){
        for(int i = 0;i < 5;i++){
            new Rock();
        }
    }
}
/**
Output:
Rock Rock Rock Rock Rock
*/
```

- 由于构造器的名称**必须**与类名完全相同，所以“每个方法首字母小写”的编码风格并不适用于构造器。
- 不接受任何参数的构造器叫做默认构造器，Java文档中通常使用术语无参构造器。
- 在Java中，“初始化”和“创建”捆绑在一起，两者不能分离
- 构造器是一种特殊类型的方法，因为它没有返回值。（new表达式确实返回了对新建对象的引用，但构造器本身并没有任何返回值。）

## 方法重载

在Java（和C++）里，构造器是强制重载方法名的另一个原因。既然构造器的名字已经由类名决定，就只能有一个**构造器名**。

假设要创建一个类，既可以用标准方式进行初始化，也可以从文件里读取信息初始化。这就需要两个构造器：一个**默认构造器**，另一个**取字符串作为形式参数**----该字符串表示初始化对象所需的文件名称。由于都是构造器，所以它们必须有相同的名字，即**类名**。为了让**方法名相同而形式参数不同的构造器同时存在，必须用到方法重载。**同样，尽管方法重载是构造器所必需的，但它亦可以应用于其他方法，且用法同样方便。

### 区分方法重载

每个重载的方法都必须有一个独一无二的**参数类型列表**。**甚至参数顺序不同也足以区分两个方法**。不过，一般情况下别这么做，因为这会使代码难以维护。

### 涉及基本类型的重载

如果传入的数据类型（实际传入的数据类型）小于方法中声明的形式参数类型，实际数据类型就会被提升。char略有不同，如果无法找到恰好接受char参数的方法，就会把char直接提升至int型。

如果传入的实际参数较大，就得通过类型转换来窄化转换。如果不这样做，编译器就会报错。

### 以返回值区分方法重载

下面两个方法，虽然它们有同样的名字和形式参数，但却很容易区分它们：

```java
void f(){}
int f(){return 1;}
```

只要编译器可以根据语境明确判断出语义，比如在`int x = f()`中，那么的确可以据此区分重载方法。

不过，有时并不关心方法的返回值，想要的是方法调用的其他效果（常被称为“为了副作用而调用”），这时可能会调用方法而忽略其返回值。所以，如果像下面这样调用方法：

```java
f();
```

所以，**根据方法返回值来区分重载方法是行不通的。**

## 默认构造器

默认构造器，是没有形式参数的---它的作用是创建一个“默认对象”。**如果写的类中没有构造器，则编译器会自动帮你创建一个默认构造器。**但是，如果已经定义了一个构造器（无论是否有参数），编译器就不会帮你自动创建构造器。

## this关键字

```java
class Banana{
    void peel(int i){
        /*....*/
    }
}
public class BananaPeel{
    public static void main(String[] args){
        Banana a = new Banana();
        Banana b = new Banana();
        
        a.peel(1);
        b.peel(2);
    }
}
```

如果只有一个peel()方法，它如何知道是被 a 还是被 b 调用？

- 为了能用简单、面向对象的语法来编写代码---即“发送消息给对象”，编译器做了一些幕后工作。它暗自把“所操作对象的引用”作为第一个参数传递给peel()。所以上述两个方法的调用就变成了这样：

```java
Banana.peel(a,1);
Banana.peel(b,2);
```

这是内部的表达形式，我们并不能这样书写代码，并试图通过编译。

如果希望在**方法内部**获得**对当前对象的引用**。由于这个引用是由编译器“偷偷”传入的，所以没有标识符可用。但是，为此有个专门的关键字：**this**。this关键字只能在方法内部使用，表示对**调用方法的那个对象**的引用。this的用法和其他对象引用并无不同。但要注意，如果在方法内部调用**同一个类的另一个方法**，就不必使用this，直接调用即可。

**this指代当前对象**

只有当需要明确指出对当前对象的引用时，才需要使用this关键字。例如，当需要返回对当前对象的引用时：

```java
public class Leaf{
    int i = 0;
    Leaf increment(){
        i++;
        return this;
    }
    void print(){
        System.out.println("i = " + i);
    }
    public static void main(String[] args){
        Leaf x = new Leaf();
        x.increment().increment().increment().print();
    }
}
/**
Output:
i = 3
*/
```

由于increment()通过this关键字返回了对当前对象的引用，所以很容易在一条语句里对同一个对象执行多次操作。

### 在构造器中调用构造器

可能为一个类写了多个构造器，有时可能想在一个构造器中调用另一个构造器，以避免重复代码。可用this关键字做到这一点。

通常写this的时候，都是指“这个对象”或“当前对象”，而且它本身表示对当前对象的引用。在构造中，如果为this添加了参数列表，那么就有了不同的含义。这将产生对符合此参数列表的某个构造器的明确调用；这样，调用其他构造器就有了直接途径：

```java
public class Flower{
    int petalCount = 0;
    String s = "initial value";
    Flower(int petals){
        System.out.println("Constructor w/ int arg only,petalCount=" + petalCount);
    }
    Flower(String ss){
        System.out.println("Constructor w/ String arg only,s=" + ss);
    }
    Flower(String ss,int petals){
        this(petals);
        //this(s)   this不可以调用两次
        this.s = s;
        System.out.println("String & int args");
    }
    Flower(){
        this("hi",47);
        System.out.println("default constructor (no args)");
    }
    void printPetalCount(){
        //this(11) 除构造器外，编译器禁止在其他任何方法中调用构造器
        System.out.println("petalCount = " + petalCount + " s = " + s);
    }
    public static void main(String[] args){
        Flower x = new Flower();
        x.printPetalCount();
    }
}
/**
Constructor w/ int arg only,petalCount=47
String & int args
default constructor(no args)
petalCount = 47 s = hi
*/
```

构造器你Flower(String s,int petals)表明：**尽管可以用this调用一个构造器，但却不能调用两个。此外，必须将构造器调用置于最起始处，否则编译器会报错。**

这个例子也展示了this的另一种用法。用于参数 s 的名称和数据成员 s 的名字相同，所以会产生歧义。使用`this.s`来代表数据成员就能解决这个问题。

printPetalCount()方法表明，**除构造器外，编译器禁止在其他任何方法中调用构造器**。（这里是指调用，而不是实例化new）

### static的含义

- static方法就是没有this的方法。在static方法的内部不能调用非静态方法。
  - 这不是完全不可能。如果传递一个对象的引用到静态方法里（静态方法可以创建其自身对象），然后通过这个引用（和this效果相同），就可以调用非静态方法和访问非静态数据成员了。但通常要达到这样的效果，只需要写一个非静态方法即可。