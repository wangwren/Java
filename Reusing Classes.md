# 复用类

> 复用代码是Java众多引人注目的功能之一。但要成为极具革命性的语言，仅仅能够复制代码并对之加以改变是不够的，它还必须能够做更多的事情。

使用类而不破坏现有程序代码，有两种方法达到这一目的。

- 第一种方法：只需在新的类中产生现有类的对象。由于新的类是由现有类的对象组成，所以这种方法称为**组合**。该方法只是复用了现有程序代码的功能，而非它的形式。
- 第二种方法则更细致一些：它按照现有类的类型来创建新类。无需改变现有类的形状，采用现有类的形式并在其中添加新代码。这种方式称为**继承**。**继承是面向对象程序设计的基石之一。**

## 组合语法

只需将对象引用置于新类中即可。例如，假设需要某个对象，它具有多个String对象、几个基本类型数据，以及另一个类的对象。对于非基本类型的对象，必须将其引用置于新的类中，但可以直接定义基本类型数据。

```java
class WaterSource{
    private String s;
    WaterSource(){
        System.out.println("WaterSource()");
        s = "Constructed";
    }
    public String toString(){
        return s;
    }
}

public class SprinklerSystem{
    private String value1,value2,value3,value4;
    private WaterSource source = new WaterSource();
    private int i;
    private float f;
    public String toString(){
        return "value1 = " + value1 + " " + 
               "value2 = " + value2 + " " +
               "value3 = " + value3 + " " +
               "value4 = " + value4 + " " +
               "i = " + i + " " + "f = " + f + " " + 
               "source = " + source;
    }
    public static void main(String[] args){
        SprinklerSystem sprinkler = new SprinklerSystem();
        System.out.println(sprinkler);
    }
}
/**
Output:
WaterSource()
value1 = null value2 = null value3 = null value4 = null
i = 0 f = 0.0 source = Constructed
*/
```

上述两个类所定义的方法中，有一个很特殊:`toString()`。每一个非基本类型的对象都有一个`toString()`方法，而且**当编译器需要一个String而你却只有一个对象时，该方法便会被调用。**

如果想初始化这些引用，可以在代码中的下列位置进行：

1. 在定义对象的地方。这意味着它们总是能够在构造器被调用之前被初始化。
2. 在类的构造器中。
3. 就在正要使用这些对象之前，这种方式称为**惰性初始化**。在生成对象不值得及不必每次都生成对象的情况下，这种方式可以减少额外的负担。
4. 使用实力初始化。

## 继承语法

继承是所有OOP（Object Oriented Programming 面向对象编程）语言和 Java 语言不可缺少的组成部分。当创建一个类时，总是在继承，因此，除非已明确指出要从其他类中继承，否则就是在隐式地从 Java 的标准根类 Object 进行继承。

**子类会自动得到基类中所有的域和方法**。

```java
class Cleanser{
    private String s = "Cleanser";
    public void append(String s){
        s += a;
    }
    public void dilute(){
        append(" dilute()");
    }
    public void apply(){
        append(" apple()");
    }
    public void scrub(){
        append(" scrub()");
    }
    public String toString(){
        return s;
    }
    public static void main(String[] args){
        Cleanser x = new Cleanser();
        x.dilute();
        x.apply();
        x.scrub();
        System.out.println(x);
    }
}
public class Detergent extends Cleanser{
    public void scrub(){
        append(" Detegent.scrub()");
        super.scrub();
    }
    public void foam(){
        append(" foam()");
    }
    public static void main(String[] args){
        Detegrent x = new Detegrent();
        x.dilute();
        x.apply();
        x.scrub();
        x.foam();
        System.out.println(x);
        System.out.println("Testing base class:");
        Cleanser.main(args);
    }
}
/**
Output:
Cleanser dilute() apply() Detergent.scrub() scrub() foam()
Testing base class:
Cleanser dilute() apply() scrub()
*/
```

为了继承，一般的规则是将所有的数据成员都指定为 private ，将所有方法指定为 public。当然，在特殊情况下，必须做出调整。可以将继承视作是对类的复用。

正如在`scrub()`中所见，使用基类中定义的方法及对它进行修改时可行的。在此例中，可能想要在新版本中调用从基类继承而来的方法。但是在`scrub()`中，并不能直接调用`scrub()`，因为这样做会产生递归，这不是所期望的。为解决此问题，Java用**super**关键字表示超类的意思，当前类就是从基类继承来的。为此，表达式`super.scrub()`将调用基类版本的`scrub()`方法。

在继承过程中，并不一定非得使用基类的方法。也可以在导出类中添加新方法，其添加方式与在类中添加任意方法一样，即对其加以定义即可。`foam()`方法即为一例。

### 初始化基类

对基类子对象的正确初始化至关重要，而基类构造器具有执行基类初始化所需要的所有知识和能力。**Java会自动在导出类的构造器中插入对基类构造器的调用。**

```java
class Art{
    Art(){
        System.out.println("Art constructor");
    }
}
class Drawing extends Art{
    Drawing(){
        System.out.println("Drawing constructor");
    }
}
public class Cartoon extends Drawing{
    public Cartoon(){
        System.out.println("Cartoon constrcutor");
    }
    public static void main(String[] args){
        Cartoon x = new Cartoon();
    }
}
/**
Art constructor
Drawing constructor
Cartoon constructor
*/
```

构建过程是从基类“向外”扩散的，所以基类在导出类构造器可以访问它之前，就已经完成了初始化。即使不为`Cartoon()`创建构造器，编译器也会为你合成一个默认的构造器，该构造器将调用基类的构造器。

#### 带参数的构造器

上例中各个类均含有默认的构造器，即这些构造器都不带参数。编译器可以轻松地调用它们是因为不必考虑要传递什么样的参数的问题。但是，如果没有默认的基类构造器，或者想调用一个带参数的基类构造器，就必须用关键字**super**显示地编写调用基类构造器的语句，并且配以适当的参数列表。

```java
package resuing;

class Game{
	Game(int i){
		System.out.println("Game constructor");
	}
}

class BoardGame extends Game{

	BoardGame(int i) {
		super(i);	//该语句必须放在构造器第一行，否则编译不通过
		System.out.println("BoardGame constructor");
	}
}

public class Chess extends BoardGame {

	Chess(){
		super(11);
		System.out.println("Chess constructor");
	}
	
	public static void main(String[] args) {
		Chess x = new Chess();
	}
}
```

如果不在`BoardGame()`中调用基类构造器，编译器将“抱怨”无法找到符合`Game()`形式的构造器。而且，**调用基类构造器必须是在导出类构造器中要做的第一件事。**

## 结合使用组合和继承

### 名称屏蔽

如果Java的基类拥有某个已被多次重载的方法名称，那么在导出类中重新定义该方法名称并不会屏蔽其在基类中的任何版本（这一点与C++不同）。因此，无论是在该层或者它的基类中对方法进行定义，重载机制都可以正常工作。

```java
package resuing;

class Homer{
	
	char doh(char c) {
		System.out.println("doh(char)");
		return 'd';
	}
	
	float doh(float f) {
		System.out.println("doh(float)");
		return 1.0f;
	}
}

class Milhouse{
	
}

class Bart extends Homer{
	
	void doh(Milhouse m) {
		
		System.out.println("doh(Milhouse)");
		
	}
    /**
    这个方法，与基类完全相同，而且编译器不会报错
    如果子类调用这个方法，调用的是自己的，而不是父类的
    float doh(float f) {
		System.out.println("Bart.doh(float)");
		return 1.0f;
	}
	*/
}

public class Hide {

	public static void main(String[] args) {
		
		Bart b = new Bart();
		b.doh(1);
		b.doh('x');
		b.doh(1.0f);
		b.doh(new Milhouse());
	}
}
/**
Output:
doh(float)
doh(char)
doh(float)
doh(Milhouse)
*/
```

虽然 Bart 引入了一个新的重载方法（在C++中若要完成这项工作则需要屏蔽基类方法），但是在 Bart 中 Homer 的所有重载方法都是可用的。**使用与基类完全相同的特征签名及返回类型来覆盖具有相同名称的方法，是一件极其平常的事**

Java SE5新增加了 @Override 注解，它并不是关键字，但是可以把它当作关键字使用。当想要覆盖某个方法时，可以选择添加这个注解，在不留心重载而并非覆写了该方法时，编译器就会生成一条错误消息。

```java
class Lisa extends Homer{
    @Override
    void doh(Milhouse m){
        System.out.println("doh(Milhouse)");
    }
}
//编译器会报错
//提示：method does not override a method from its superclass
```

@Override注解可以防止在不想重载时而意外进行了重载

## 在组合与继承之间选择

> is-a （是一个）的关系是用继承来表达的，而 has-a（有一个）的关系则是用组合来表达的。

## protected关键字

在实际项目中，经常会想要将某些事物尽可能对这个世界隐藏起来，但仍然允许导出类的成员访问它们。

关键字protected就是起这个作用的。它指明“就类用户而言，这是private的，但对于任何继承于此类的导出类或其他任何位于同一个包内的类来说，它确实可以访问的。”

尽管可以创建 protected域，但是最好的方式还是讲域保持为private；应当一直保留“更改底层实现”的权利。然后通过protected方法来控制类的继承者的访问权限。

## 向上转型

“为新的类提供方法”并不是继承技术中最重要的方面，其最重要的方面是用来表现新类和基类之间的关系。这种关系可以用“新类是现有类的一种类型”这句话加以概括。

假设有一个称为 Instrument 的代表乐器的基类和一个称为 Wind 的导出类。由于继承可以确保基类中所有的方法在导出类中也同样有效，所以能够向基类发送的所有信息同样也可以向导出类发送。如果 Instrument类具有一个 play() 方法，那么 Wind 乐器也将同样具备。这意味着可以准确地说Wind对象也是一种类型的 Instrument。

```java
class Instrument{
    public void play(){}
    static void tune(Instrument i){
        //...
        i.play();
    }
}
public class Wind extends Instrument{
    public static void main(String[] args){
        Wind flute = new Wind();
        Instrument.tune(flute);
    }
}
```

在此例中，tune()方法可以接受 Instrument 引用。但在 Wind.main()中，传递给 tune()方法的是一个 Wind 引用。鉴于Java对类型的检查十分严格，接受某种类型的方法同样可以接受另一种类型就会显得很奇怪，除非已经认识到 Wind 对象同样也是一种 Instrument 对象，而且也不存在任何 tune() 方法是可以通过 Instrument来调用，同时又不存在与Wind之中。在tune()中，程序代码可以对 Instrument和它所有的导出类起作用，**这种将Wind引用转换为Instrument引用的动作，称之为向上转型。**

### 为什么称为向上转型

由于向上转型是从一个较专用类型向通用类型转换，所以总是安全的。也就是说，导出类是基类的一个超集。它可能比基类含有更多的方法，但它必须至少具备基类中所含有的方法。

### 再论组合与继承

尽管多次强调继承，但这并不意味着要尽可能使用它。相反，应当慎用这一技术，其使用场合仅限于你确信使用该技术确实有效的情况。到底是该用组合还是用继承，一个最清晰的判断办法就是问一问自己是否需要从新类向基类进行向上转型。如果必须向上转型，则继承是必要；但如果不需要，则应当好好考虑自己是否需要继承。

## final关键字

### final数据

许多编程语言都有某种方法，来向编译器告知一块数据是恒定不变的。有时数据的恒定不变是很有用的，比如：

1. 一个永不改变得编译时常量。
2. 一个在运行时被初始化的值，而你不希望它被改变。

对于编译期常量这种情况，编译器可以将该常量值代入任何代入可能用到它的计算式中，也就是说，可以在编译时执行计算式，这减轻了一些运行时的负担。**在Java中，这类常量必须是基本数据类型，并且以关键字 final表示。在对这个常量进行定义的时候，必须对其进行赋值。**

**一个既是static又是final的域只占据一段不能改变的存储空间。**

当对对象引用而不是基本类型运用 final 时，其含义会有一点令人迷惑。**对于基本类型，final 使数值恒定不变；而对于对象引用，final使引用恒定不变。**一旦引用被初始化指向一个对象，就无法再把它改为指向另一个对象。然而，对象其自身却是可以被修改的，Java并未提供使任何对象恒定不变的途径（但可以自己编写类以取得使对象恒定不变的效果）。这一限制同样适用数组，它也是对象。

根据惯例，既是static又是final的域（即编译器常量）将用大写表示，并适用下划线分隔各个单词。

```java
package resuing;

import java.util.Random;

class Value{
	int i;
	public Value(int i) {
		this.i = i;
	}
}

public class FinalData {

	private static Random rand = new Random(47);
	private String id;
	
	public FinalData(String id) {
		this.id = id;
	}
	
	private final int valueOne = 9;
	private static final int VALUE_TWO = 99;
	
	public static final int VALUE_THREE = 39;
	
	private final int i4 = rand.nextInt(20);
	static final int INT_5 = rand.nextInt(20);
	
	private Value v1 = new Value(11);
	private final Value v2 = new Value(22);
	private static final Value VAL_3 = new Value(33);
	
	private final int[] a = {1,2,3,4,5,6};
	
	public String toString() {
		return id + ": " + "i4 = " + i4  + ",INT_5 = " + INT_5;
	}
	
	public static void main(String[] args) {
		FinalData fd1 = new FinalData("fd1");
		//fd1.valueOne++;  Error:can't change value
		fd1.v2.i++;		//Object isn't constant 对象不是常数，对象不可变，但其中的属性可以
		fd1.v1 = new Value(9);    //ok --not final
		
		for(int i = 0;i < fd1.a.length;i++) {
			fd1.a[i]++;		//Object isn't constant
		}
		
		//fd1.v2 = new Value(0);  Error:can't
		//fd1.VAL_3 = new Value(1);  change reference 改变了对象引用，不可以
		//fd1.a = new int[3];  数组也是引用，不可以改变
		System.out.println(fd1);
		System.out.println("Creating new FinalData");
		FinalData fd2 = new FinalData("fd2");
		System.out.println(fd1);
		System.out.println(fd2);
	}
}
```

- 由于 valueOne 和VAL_TWO都是带有编译时数值的 final 基本类型，所以它们二者均可以用作编译期常量，并且没有重大区别。
- VAL_THREE 是一种更加典型的对常量进行定义的方式：定义为public，则可以被用于包之外；定义为 static，则强调只有一份；定义为 final，则说明它是一个常量。
- 不能因为某数据是 final 的就认为在编译时可以知道它的值。在运行时随机生成的数值来初始化 i4 和INT_5就说明了这一点。示例部分也展示了将final 数值定义为静态和非静态的区别。此区别只有当数值在运行时内被初始化时才会显现，这是因为编译器对编译时数值一视同仁（并且他们可能因优化而消失）。当运行程序时就会看到这个区别。
  - 请注意，在fd1和fd2中，i4 的值是唯一的，但 INT_5 的值是不可以通过创建第二个FinalData对象而加以改变的。这是因为它是static的，在装载时已被初始化，而不是每次创建新对象时被初始化。
- v1 到 VAL_3这些变量说明了 final 引用的意义。正如在 main() 中所看到的，不能因为 v2 是final的，就认为无法改变它的值。由于它是一个引用，final 意味着无法将 v2 再次指向另一个新的对象。这对数组具有同样的意义，数组只不过是另一种引用。
- 看起来，使引用成为final没有使基本类型成为final的用处大。

#### 空白final

Java允许生成“空白final”，所谓空白final是指被声明为final但又未给定初值的域。无论什么情况，编译器都确保空白final在使用前必须被初始化。但是，空白final在关键字final的使用上提供了更大的灵活性，为此，一个类中的final域就可以做到根据对象而有所不同，却又保持其恒定不变的特性。

```java
class Poppet{
    private int i;
    Poppet(int ii){
        i = ii;
    }
}
public class BlankFinal{
    private final int i = 0;//Initialized final
    private final int j;//Blank final
    private final Poppet p;//Blank final reference
    
    public BlankFinal(){
        j = 1;//Initialized blank final
        p = new Poppet(1); //Initialized blank final reference
    }
    
    public BlankFinal(int x){
        j = x;//Initialized blank final
        p = new Poppet(x); //Initialized blank final reference
    }
    
    public static void main(String[] args){
        new BlankFinal();
        new BlankFinal(47);
    }
}
```

必须在域的定义处或者每个构造器中用表达式对final进行赋值，这正是final域在使用前总是被初始化的原因所在。

#### final参数

Java允许在参数列表中以声明的方式将参数指明为final。**这意味着无法在方法中更改参数引用所指向的对象。**

### final方法
8月16日结束第七章
