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

## 清理：终结处理和垃圾回收

Java有垃圾回收器负责回收无用对象占据的资源。但也有特殊情况：假定你的对象（并非使用new）获得了一块“特殊”的内存区域。由于垃圾回收器只知道释放那些经由new分配的内存，所以它不知道该如何释放该对象的这块“特殊”内存。**为了应对这种情况，Java允许在类中定义一个名为finalize()的方法。**它的工作原理“假定”是这样的：一旦垃圾回收器准备好释放对象占用的存储空间，将首先调用其finalize()方法，就能在垃圾回收时刻做一些重要的清理工作。

这里有一个潜在的编程陷阱，因为有些程序员（特别是C++程序员）刚开始可能会误把finalize()当做C++中的**析构函数**（C++中销毁对象必须用到这个函数）。所以有必要明确区分一下：在C++中，对象一定会被销毁（如果程序没有缺陷的话）；而Java里的对象却并非总是被垃圾回收。或者换句话说：

- 对象可能不被垃圾回收
- 垃圾回收并不等于“析构”

在你不需要某个对象之前，如果必须执行某些动作，那么你得自己去做。Java并未提供“析构函数”或相似的概念，要做类似的清理工作，必须自己动手创建一个执行清理工作的普通方法。例如，假设某个对象在创建过程中会将自己绘制到屏幕上，如果不是明确地从屏幕上将其擦除，它可能永远得不到清理。如果在finalize()里加入某种擦除功能，当“垃圾回收”发生时（不能保证一定会发生），finalize()得到了调用，图像就会被擦除。要是“垃圾回收”没有发生，图像就会一直保留下来。

### finalize()的用途何在

finalize()的真正用途：

- 垃圾回收只与内存有关。

之所以要有finalize()，是由于在分配内存时可能采用了类似C语言中的做法，而非Java中的通常做法，这种情况主要发生在使用“本地方法”的情况下，本地方法是一种在Java中调用非Java代码的方式。本地方法目前只支持C和C++，但它们可以调用其他语言写的代码，所以实际上可以调用任何代码。

finalize()不是进行普通清理工作的合适场所。

### 你必须实施清理

Java不允许创建局部对象，必须使用new创建对象。

如果希望进行除释放存储空间之外的清理工作，还是得明确调用某个恰当的Java方法。这就等同于析构函数了，只是没有它方便。

无论是“垃圾回收”还是“终结”，都不保证一定会发生。如果Java虚拟机（JVM）并未面临内存耗尽的情形，它是不会浪费时间去执行垃圾回收以恢复内存的。

### 终结条件

```java
class Book{
    boolean checkOut = false;
    Book(boolean checkOut){
        this.checkOut = checkOut;
    }
    void checkIn(){
        checkOut = false;
    }
    protected void finalize(){
        if(checkOut){
            System.out.println("Error : checked out");
        }
    }
}
public class TerminationCondition{
    public static void main(String[] args){
        Book novel = new Book(true);
        novel.checkIn();
        new Book(true);
        System.gc();
    }
}
/**
Output:
Error:check out
*/
```

本例的终结条件是：所有的Book对象在被当做垃圾回收前都应该被签入（check in）。但在main()方法中，由于程序员的错误，有一本书未被签入。要是没有finalize()来验证终结条件，将很难发现这种缺陷。

注意，System.gc()用于强制进行终结动作。即使不这么做，通过重复地执行程序（假设将分配大量的存储空间而导致垃圾回收动作的执行），最终也能找出错误的Book对象。

### 垃圾回收器如何工作

存储空间的释放会影响存储空间的分配，这确实是某些Java虚拟机的工作方式。也意味着，Java从堆分配空间的速度，可以和其他语言从堆栈上分配空间的速度相媲美。

理解Java中的垃圾回收，先了解其他系统中的垃圾回收机制：

- 引用记数
  - 引用记数是一种简单但速度很慢的垃圾回收技术。每个对象都含有一个引用计数器，当引用连接至对象时，引用计数加1。当引用离开作用域或被置为null时，引用计数减1。
  - 虽然管理引用计数的开销不大，但这项开销在整个程序生命周期中将持续发生。
  - 垃圾回收器会在含有全部对象的列表上遍历，当发现某个对象的引用计数为0时，就释放其占用的空间（但是，引用计数模式经常会在计数值变为0时立即释放对象）。这种方法有个缺陷，如果对象之间存在循环引用，可能会出现“对象应该被回收，但引用记数不为零”的情况。对垃圾回收器而言，定位这样的交互自引用的对象组所需的工作量极大。
  - 引用记数常用来说明垃圾收集的工作方式，但似乎从未被应用于任何一种Java虚拟机实现中。

在一些更快的模式中，垃圾回收器并非基于引用记数技术。它们依据的思想是：**对于任何“活”的对象，一定能最终追溯到其存活在堆栈或静态存储区之中的引用。**这个引用链条可能穿过数个对象层次。由此，如果从堆栈和静态存储区开始，遍历所有的引用，就能找到所有“活”的对象。对于发现的每个引用，必须追踪它所引用的对象，然后是此对象包含的所有引用，如此反复进行，直到“根源于堆栈和静态存储区的引用”所形成的网络全部被访问为止。你所访问过的对象必须都是“活”的。注意，这就解决了“交互引用的对象组”的问题----这种现象根本不会被发现，因此也就被自动回收了。

在这种方式下，Java虚拟机将采用一种**自适应**的垃圾回收技术。至于如何处理找到的存活对象，取决于不同的Java虚拟机实现。有一种做法名为**停止--复制**（stop-and-copy）。显然这意味着，先暂停程序的运行（所以它不属于后台回收模式），然后将所有存活的对象从当前堆复制到另一个堆，没有被复制的全部都是垃圾。当对象被复制到新堆时，它们是一个挨着一个的，所以新堆保持紧凑排列，然后就可以按前述方法简单、直接地分配新空间了。

当把对象从一处搬到另一处时，所有指向它的那些引用都必须修正。位于堆或静态存储区的引用可以直接被修正，但可能还有其他指向这些对象的引用，它们在遍历的过程中才能被找到。

对于这种所谓的“复制式回收器”而言，效率会降低，这有两个原因：

- 首先，得有两个堆，然后得在这两个分离的堆之间来回倒腾，从而的维护比实际需要多一倍的空间。某些Java虚拟机对此问题的处理方式是，按需从堆中分配几块大的内存，复制动作发生在这些大块内存之间。
- 第二个问题在于复制。程序进入稳定状态之后，可能只会产生少量垃圾，甚至没有垃圾。尽管如此，复制式回收器仍然会将所有内存自一处复制到另一处，这很浪费。为了避免这种情形，一些Java虚拟机会进行检查：要是没有新垃圾产生，就会转换到另一种工作模式(即“自适应”)。这种模式称为**标记--清扫**。

对一般用途而言，**标记--清扫**方式速度相当慢，但是当你知道只会产生少量垃圾甚至不会产生垃圾时，它的速度就很快了。

**标记--清扫**所依据的思路同样是从堆栈和静态存储区出发，遍历所有引用，进而找出所有存活的对象。每当它找到一个存活对象，就会给对象设一个标记，这个过程中不会回收任何对象。只有全部标记工作完成的时候，清理动作才会开始。在清理过程中，没有标记的对象将被释放，不会发生任何复制动作。所以剩下的堆空间是不连续的，垃圾回收器要是希望得到连续空间的话，就得重新整理剩下的对象。

**停止--复制**的意思是这种垃圾回收动作不是在后台进行的；相反，垃圾回收动作发生的同时，程序将会被暂停。在Sun公司的文档中会发现，许多参考文献将垃圾回收视为低优先级的后台进程，但事实上垃圾回收器在Sun公司早期版本的Java虚拟机中并非以这种方式实现的。当可用内存数量较低时，Sun版本的垃圾回收器会暂停运行程序，同样，“标记--清扫”工作也必须在程序暂停的情况下才能进行。

在这里所讨论的Java虚拟机中，内存分配以较大的“块”为单位。如果对象较大，它会占用单独的块。严格来说，“停止--复制”要求在释放旧有对象之前，必须先把所有存活对象从旧堆复制到新堆，这将导致大量内存复制行为。有了块之后，垃圾回收器在回收的时候就可以往废弃的块里拷贝对象了。每个块都有相应的**代数**来记录它是否还存活。通常，如果块在某处被引用，其代数会增加；垃圾回收器将对上次回收动作之后新分配的块进行整理。这对处理大量短命的临时对象很有帮助。垃圾回收器会定期进行完整的清理动作。大型对象仍然不会被复制（只是其代数会增加），内涵小型对象的那些块则被复制并整理。Java虚拟机会进行监视，如果所有对象都很稳定，垃圾回收器的效率降低的话，就切换到“标记--清扫”方式；同样，Java虚拟机会跟踪“标记--清扫”的效果，要是堆空间出现很多碎片，就会切换回“停止--复制”方式。这就是**自适应技术**。

Java虚拟机中有许多附加技术用以提升速度。尤其是与加载器操作有关的，被称为“即时”(Just-In-Time,JIT)编译器的技术。这种技术可以把程序全部或部分翻译成本地机器码（这本来是Java虚拟机的工作），程序运行速度因此得以提升。当需要装载某个类（通常是在为该类创建第一个对象）时，编译器会先找到其.class文件，然后将该类的字节码装入内存。此时，有两种方案可供选择。

- 一种是就让即时编译器编译所有代码。但这种做法有两个缺陷：这种加载动作散落在整个程序生命周期内，累加起来要花更多时间；并且会增加可执行代码的长度（字节码要比即时编译器展开后的本地机器码小很多），这将导师页面调度，从而降低程序速度。
- 另一种做法称为**惰性评估**(lazy evaluation)，意思是即时编译器只在必要的时候才编译代码。这样，从不会被执行的代码也许就压根不会被JIT所编译。新版JDK中的Java HotSpot技术就采用了类似方法，代码每次被执行的时候都会做一些优化，所以执行的次数越多，它的速度就越快。

## 成员初始化

Java尽力保证：所有变量在使用前都能得到恰当的初始化。**对于方法的局部变量，Java以编译时错误的形式来贯彻这种保证。**

```java
void f(){
    int i;
    i++; //Error   i not initalized
}
```

会得到一条出错消息，告诉你 i 可能尚未初始化。

如果是**类**的数据成员（即字段）是基本类型，类的每个基本数据成员保证都会有一个初始值。

在**类**里定义一个对象引用时，如果不将其初始化，此引用就会获得一个特殊值 null。

## 构造器初始化

可以用构造器来进行初始化。在运行时刻，可以调用方法或执行某些动作来确定初值，这为编程带来了更大的灵活性。但要牢记：**无法阻止自动初始化的进行，它将在构造器被调用之前发生。**

```java
public class Counter{
    int i;
    Counter(){
        i = 7;
    }
}
```

对于上面的代码，i首先会被置为0，然后变成7.对于所有基本类型和对象的引用，包括在定义时已经指定初值的变量，这种情况都是成立的；因此，编译器不会强制你一定要在构造器的某个地方或在使用它们之前对元素进行初始化--因为初始化早已得到了保证。

### 初始化顺序

在类的内部，变量定义的先后顺序决定了初始化的顺序。即使变量定义散步于方法定义之间，它们仍旧会在任何方法（包括构造器）被调用之前得到初始化。

```java
class Window{
    Window(int marker){
        System.out.println("Window(" + marker + ")");
    }
}
class House{
    Window w1 = new Window(1);
    House(){
        System.out.println("House()");
        w3 = new Window(33);
    }
    Window w2 = new Window(2);
    void f(){
        System.out.println("f()");
    }
    Window w3 = new Window(3);
}
public class OrderOfInitialization{
    public static void main(String[] args){
        House h = new House();
        h.f();
    }
}
/**
Output:
Window(1)
Window(2)
Window(3)
House()
Window(33)
f()
*/
```

在House类中，故意把几个Window对象的定义散布到各处，**以证明它们全部都会在调用构造器或其他方法之前得到初始化。**此外，w3在构造器内再次被初始化。w3这个引用会被初始化两次：一次在调用构造器前，一次在调用期间（第一次引用的对象将被丢弃，并作为垃圾回收）。

### 静态数据的初始化

无论创建多少个对象，静态数据都只占用一份存储区域。static 关键字不能应用于局部变量，因此它只能作用于域。如果一个域是静态的基本类型域，且也没有对它进行初始化，那么它就会获得基本类型的标准初值；如果它是一个对象引用，那么它的默认初始化值就是 null。

初始化的顺序是先静态对象（如果他们尚未因前面的对象创建过程而被初始化），而后是“非静态”对象。

- 总结一下对象的创建过程，假设有个名为Dog的类
  - 即使没有显示地使用static关键字，构造器实际上也是静态方法。因此，当首次创建类型为Dog的对象时（构造器可以看成静态方法），或者Dog类的静态方法/静态域首次被访问时，Java解释器必须查找类路径，以定位Dog.class文件
  - 然后载入Dog.class，有关静态初始化的所有动作都会执行。因此，**静态初始化只在Class对象首次加载的时候进行一次。**
  - 当用`new Dog()`创建对象的时候，首先将在堆上为Dog对象分配足够的存储空间。
  - 这块存储空间会被清零，这就自动地将Dog对象中的所有基本类型数据都设置成了默认值（对数字来说就是0，对布尔型和字符型也相同），而引用则被设置成了null。
  - 执行所有出现于字段定义处的初始化动作。
  - 执行构造器。

### 显示静态初始化

Java允许将多个静态初始化动作组织成一个特殊的“静态子句”（有时也叫做“静态块”）。

```java
public class Spoon{
    static int i;
    static{
        i = 47;
    }
}
```

尽管上面的代码看起来像个方法，但它实际只是一段跟在static关键字后面的代码。与其他静态初始化动作一样。这段代码仅执行一次；**当首次生成这个类的一个对象时，或者首次访问属于那个类的静态数据成员时**（即便从未生成那个类的对象）。

```java
class Cup{
    Cup(int marker){
        System.out.println("Cup(" + marker + ")");
    }
    void f(int marker){
        System.out.println("f(" + marker + ")");
    }
}
class Cups{
    static Cup cup1;
    static Cup cup2;
    static{
        cup1 = new Cup(1);
        cup2 = new Cup(2);
    }
    Cups(){
        System.out.println("Cups()");
    }
}
public class ExplicitStatic{
    public static void main(String[] args){
        System.out.println("Inside main()");
        Cups.cup1.f(99); //注意，这里没有new Cups()，只是调用了该类中的一个静态数据成员，导致cup1被访问，cup1是在静态代码块中，导致cup2也被实例化
    }
}
/**
Inside main()
Cup(1)
Cup(2)
f(99)
*/
```

**静态初始化动作只进行一次！！！**

### 非静态实例初始化

Java中也有被称为实例初始化的类似语法，用来初始化每一个对象的非静态变量。

```java
class Mug{
    Mug(int marker){
        System.out.println("Mug(" + ")");
    }
    void f(int marker){
        System.out.println("f(" + marker + ")");
    }
}
public class Mugs{
    Mug mug1;
    Mug mug2;
    {
        mug1 = new Mug(1);
        mug2 = new Mug(2);
        System.out.print("mug1 & mug2 initialized");
    }
    Mugs(){
        System.out.println("Mugs()");
    }
    Mugs(int i){
        System.out.println("Mugs(int)");
    }
    public static void main(String[] args){
        System.out.print("Inside main()");
        new Mugs();
        System.out.println("new Mugs() completed");
        new Mugs(1);
        System.out.println("new Mugs(1) completed");
    }
}
/**
Output:
Inside main()
Mug(1)
Mug(2)
mug1 & mug2 initialized
Mugs()
new Mugs completed
Mug(1)
Mug(2)
mug1 & mug2 initialized
Mugs(int)
new Mugs(1) completed
*/
```

看起来它与静态初始化子句一模一样只不过少了static关键字。这种语法对于支持“匿名内部类”的初始化是必须的，但是它也使得你可以保证无论调用了哪个显示构造器，某些操作都会发生。从输出中可以看到实例初始化子句是在两个构造器之前执行的。

### 数组初始化

数组只是相同类型的、用同一个标识符名称封装到一起的一个对象序列或基本类型数据序列。数组是通过方括号下标操作符 [ ] 来定义和使用的。要定义一个数组，只需在类型名后加上一对空方括号即可：

```java
int[] a1;
```

方括号也可以置于标识符后面：

```java
int a1[];
```

现在拥有的只是对数组的一个引用，而且也没给数组对象本身分配任何空间。为了给数组创建相应的存储空间，必须写初始化表达式。

对于数组，初始化动作可以出现在代码的任何地方，但也可以使用一种特殊的初始化表达式，它必须在创建数组的地方出现。这种特殊的初始化是由一对花括号括起来的值组成的。这种情况下，存储空间的分配（等价于使用new）将由编译器负责，例如：

```java
int[] a1 = {1,2,3,4,5};
```

那么，为什么还要在没有数组的时候定义一个引用呢？

```java
int[] a2;
```

在Java中可以将一个数组赋值给另一个数组，所以可以这样：

```java
a2 = a1;
```

其实真正做的只是复制了一个引用。

```java
public class ArraysOfPrimitives{
    public static void main(String[] args){
        int[] a1 = {1,2,3,4,5};
        int[] a2;
        a2 = a1;
        for(int i = 0;i < a2.length;i++){
            a2[i] = a2[i] + 1;
        }
        for(int i = 0;i < a1.length;i++){
            System.out.println("a1[" + i + "] = " + a1[i]);
        }
    }
}
/**
Output:
a1[0] = 2
a1[1] = 3
a1[2] = 4
a1[3] = 5
a1[4] = 6
*/
```

由于 a2 和 a1 是相同数组的别名，因此通过 a2 所做的修改在 a1 中可以看到。

所有数组（无论它们的元素时对象还是基本类型）都有一个固有成员，可以通过它获知数组内包含了多少个元素，但不能对其修改。这个成员就是 length。

`Arrays.toString()`方法属于java.util标准类库，它将产生一维数组的可打印版本。

如果创建了一个**非基本类型的数组**，那么就创建了一个**引用数组**。以整型的包装器类Integer为例，它是一个类而不是基本类型。

### 可变参数列表

```java
public class NewVarArgs{
    static void printArray(Object... args){
        for(Object obj : args){
            System.out.print(obj + " ");
        }
        System.out.println();
    }
    public static void main(String[] args){
        printArray(47,3.14F,11.11);
        printArray(new Integer(47),new Float(3.14F),new Double(11.11));
        printArray("one","two","three");
        printArray(new A(),new A(),new A());//A是一个类，这里没写
        
        printArray((Object)new Integer[]{1,2,3,4})；
        printArray(); //Empty is ok
    }
}
/**
Output:
47 3.14 11.11
47 3.14 11.11
one two three
A@1bab50a A@c3c749 A@150bd4d
1 2 3 4
*/
```

有了可变参数，就再不也不用显示地编写数组语法了，当指定参数时，编译器实际上会为你去填充数组。你获取的仍旧是一个数组，这就是为什么可以使用foreach来迭代该数组的原因。但是，这不仅仅只是从元素列表到数组的自动转换，注意程序中倒数第二行，一个Integer数组（通过使用自动包装而创建的）被转型为一个Object数组（以便移除编译器警告信息），并且传递给了printArray()。很明显，编译器会发现它已经是一个数组，所以不会在其上执行任何转换。因此，如果有一组事物，可以把它们当作列表传递，而如果你已经有了一个数组，该方法可以把它们当作可变参数列表来接受。

该程序的最后一行表明将 0 个参数传递给可变参数列表是可行的，当具有可选的尾随参数时，这一特性就会很有用：

```java
public class OptionalTrailingArguments{
    static void f(int required,String... trailing){
        System.out.print("required: " + required + " ");
        for(String s : trailing){
            System.out.print(s + " ");
        }
        System.out.println();
    }
    public static void main(String[] args){
        f(1,"one");
        f(2,"two","three");
        f(0);
    }
}
/**
Output:
required: 1 one
required: 2 two three
required: 0
*/
```

这个程序还展示了你可以如何使用具有Object之外类型的可变参数列表。这里所有的可变参数都必须是String对象。在可变参数列表中可以使用任何类型的参数，包括基本类型。

**在不使用参数调用 f() 时，编译器就无法知道应该调用哪一个方法了。可以通过在某个方法中增加一个非可变参数来解决问题。**

## 枚举类型

```java
public enum Spiciness{
    NOT,MILD,MEDIUM,HOT,FLAMING
}
```

这里创建了一个名为Spiciness的枚举类型，它具有5个具名值。由于枚举类型的实例是常量，因此按照命名惯例它们都用大写字母表示（如果一个名字中有多个单词，用下划线将它们隔开）。

为了使用enum，需要创建一个该类型的引用，并将其复制给某个实例：

```java
public class SimpleEnumUse{
    public static void main(String[] args){
        Spiciness howHot = Spiciness.MEDIUM;
        System.out.println(howHot);
    }
}
/**
MEDIUM
*/
```

在创建enum时，编译器会自动添加一些有用的特性。例如，它会创建 toString() 方法，以便可以很方便地显示某个enum实例的名字，这正是上面的打印语句如何产生输出的答案。编译器还会创建 ordinal() 方法，用来表示某个特定 enum 常量的声明顺序，以及 static values() 方法，用来按照  enum 常量的声明顺序，产生由这些常量值构成的数组：

```java
public class EnumOrder{
    public static void main(String[] args){
        for(Spiciness s : Spiciness.values()){
            System.out.println(s + ",ordinal " + s.ordinal());
        }
    }
}
/**
Output:
NOT,ordinal 0
MILD,ordinal 1
MEDIUM,ordinal 2
HOT,ordinal 3
FLAMING,ordinal 4
*/
```

尽管enum看起来像是一种新的数据类型，但是这个关键字只是为enum生成对应的类时，产生了某些编译器行为，因此在很大程度上，你可以将enum当做其他任何类来处理。事实上，enum确实是类，并且有自己的方法。

enum有一个特别实用的特性，即它可以在switch语句内使用。  

**在19章还会继续。这里提醒自己。（希望能坚持到19章）**  
[练习题](https://github.com/wangwren/Java/tree/master/Chapter5Exercise/src/exercise)
