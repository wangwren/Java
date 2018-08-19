# 多态

> 在面向对象的程序设计语言中，多态是继数据抽象和继承之后的第三种基本特征。

- “封装”通过合并特征和行为来创建新的数据类型。“实现隐藏”则通过将细节**私有化**把接口和实现分离开来。
- 而多态的作用则是消除类型之间的耦合关系。

## 转机

### 方法调用绑定

将一个方法调用同一个方法主体关联起来被称作**绑定**。

- 若在程序执行前进行绑定，叫做前期绑定。（它是面向过程的语言中不需要选择就默认的绑定方式。）
- 后期绑定，它的含义就是在运行时根据对象的类型进行绑定。后期绑定也叫做动态绑定或运行时绑定。
  - 如果一种语言想实现后期绑定，就必须具有某种机制，以便在运行时能判断对象的类型，从而调用恰当的方法。
  - 也就是说，编译器一直不知道对象的类型，但是方法调用机制能找到正确的方法体，并加以调用。

Java中除了 static 方法和 final 方法（private方法属于 final 方法）之外，其他所有的方法都是后期绑定。

- 为什么将某个方法声明为 final ?
  - 可以防止其他人覆盖该方法。
  - 更重要的一点或许是：这样做可以有效地“关闭”动态绑定，或者说，告诉编译器不需要对其进行动态绑定。这样，编译器就可以为 final 方法调用生成更有效的代码。
  - 然而，大多数情况下，这样做对程序的整体性能不会有什么改观。所以，最好根据设计来决定是否使用 final，而不是出于试图提高性能的目的来使用 final。

### 产生正确的行为

面向对象程序设计中，有一个经典例子就是“几何形状”(shape)。在“几何形状”这个例子中，有一个基类 Shape （形状），以及多个导出类---如 Circle （圆）、Square （矩形）、Triangle（三角形）等。这个例子之所以好用，是因为我们可以说“圆是一种几何形状”，这种说法很容易被理解。

像上转型可以像下面这条语句这么简单：

```java
Shape s = new Circle();
```

这里创建了一个Circle对象，并把得到的引用立即赋给Shape，这样做看似错误（将一种类型赋值给另一种类型）；但实际上是没问题的，因为通过继承，Circle就是一种Shape。因此，编译器认可这条语句，也就不会产生错误信息。

假设调用一个基类方法（它已在导出类中被覆盖）：`s.draw();`，你可能再次认为调用的是 Shape的 draw() ，因为这毕竟是一个Shape引用。然而，由于后期绑定（多态），还是正确调用了 Circle.draw()方法。

```java
package polymorphism.shape;

public class Shape {

	public void draw() {
		
	}
	
	public void erase() {
		
	}	
}

package polymorphism.shape;

public class Circle extends Shape {

	public void draw() {
		System.out.println("Circle.draw()");
	}
	
	public void erase() {
		System.out.println("Circle.erase()");
	}
}


package polymorphism.shape;

public class Square extends Shape{

	public void draw() {
		System.out.println("Square.draw()");
	}
	
	public void erase() {
		System.out.println("Square.erase()");
	}
}


package polymorphism.shape;

public class Triangle extends Shape{

	public void draw() {
		System.out.println("Triangle.draw()");
	}
	
	public void erase() {
		System.out.println("Triangle.erase()");
	}
}


package polymorphism.shape;

import java.util.Random;

public class RandomShapeGenerator {

	private Random rand = new Random(47);
	
	public Shape next() {
		//return就已经跳出switch了，不需要再写break了，写了反而报错
		switch (rand.nextInt(3)) {
			default:				
			case 0:
				return new Circle();
			case 1:
				return new Square();
			case 2:
				return new Triangle();
		}
	}
}


package polymorphism.shape;

public class Shapes {

	private static RandomShapeGenerator gen = new RandomShapeGenerator();
	
	public static void main(String[] args) {
		
		Shape[] s = new Shape[9];
		
		for(int i = 0;i < s.length;i++) {
			s[i] = gen.next();
		}
		
		for(Shape sha : s) {
			sha.draw();
		}
		
	}
}
/**
Output:
Triangle.draw()
Triangle.draw()
Square.draw()
Triangle.draw()
Square.draw()
Triangle.draw()
Square.draw()
Triangle.draw()
Circle.draw()
*/
```

Shape基类为自它那里继承而来的所有导出类建立了一个公用接口---也就是说，所有形状都可以秒回和擦除。导出类通过覆盖这些定义，来为每种特殊类型的几何形状提供单独的行为。

RandomShapeGenerator是一种“工厂”，在我们每次调用next()方法时，它可以为随机选择的 Shape 对象产生一个引用。注意，向上转型是在 return 语句里发生的。

随机选择几何形状可以看出，在编译时，编译器不需要获得任何特殊信息就能进行正确的调用。对draw()方法的所有调用都是通过动态绑定进行的。

### 可扩展性

可以从通用的基类继承出新的数据类型，从而添加一些功能。那些操纵基类接口的方法不需要任何改动就可以应用于新类。

多态是一项让程序员“将改变的事物与未变的事物分离开来”的重要技术。

### 缺陷：“覆盖”私有方法

```java
public class PrivateOverride{
    private void f(){
        System.out.println("private f()");
    }
    public static void main(String[] args){
        PrivateOverrride po = new Derived();
        po.f();
    }
}

class Derived extends PrivateOverride{
    public void f(){
        System.out.println("public f()");
    }
}
/**
private f()
*/
```

我们所期望的输出是public f()。但是由于private方法被自动认为是final方法，而且对导出类是屏蔽的。因此，在这种情况下，Derived类中的 f()方法就是一个全新的方法；既然基类中的 f()方法在子类 Derived中不可见，因此甚至也不能被重载。

结论就是：**只有非private方法才可以被覆盖；但是还需要密切注意覆盖private方法的现象，这时虽然编译器不会报错，但是也不会按照我们所期望的来执行。确切地说，在导出类中，对于基类中的private方法，最好采用不同的名字。**

### 缺陷：域和静态方法

只有普通方法调用可以是多态的。例如，如果直接访问某个域，这个访问就将在编译器进行解析。

```java
package polymorphism.field;

class Super{
	public int field = 0;
	public int getField() {
		return field;
	}
}

class Sub extends Super{
	public int field = 1;
	public int getField() {
		return field;
	}
	public int getSuperField() {
		return super.field;
	}
}

public class FieldAccess {

	public static void main(String[] args) {
		Super sup = new Sub();
		System.out.println("sup.field = " + sup.field + ", sup.getField() = " + sup.getField());
		Sub sub = new Sub();
		System.out.println("sub.field = " + sub.field + ",sub.getField() = " + sub.getField() + ",sub.getSuperField() = " + sub.getSuperField());
	}
}
/**
sup.field = 0, sup.getField() = 1
sub.field = 1,sub.getField() = 1,sub.getSuperField() = 0
*/
```

当Sub对象转型为Super引用时，任何域访问操作都将由编译器解析，因此不是多态的。在本例中，为Super.field和Sub.field分配了不同的存储空间。这样，Sub实际上包含两个称为 field 的域：它自己的和它从Super处得到的。然而，在引用Sub中的field时所产生的默认域并非Super版本的field域。因此，为了得到Super.field，必须显示地指明super.field。

在实践中，这种问题不会发生，通常会将所有的域都设置为private。

**如果某个方法是静态的，它的行为就不具有多态性。**

```java
class StaticSuper{
    public static String staticGet(){
        return "Base staticGet()";
    }
    public String dynamicGet(){
        return "Base dynamicGet()";
    }
}

class StaticSub extends StaticSuper{
    public static String staticGet(){
        return "Devied staticGet()";
    }
    public String dynamincGet(){
        return "Drvied dynaminGet()";
    }
}
public class StaticPolymorphism{
    public static void main(String[] args){
        StaticSuper sup = new StaticSub();
        System.out.println(sup.staticGet());
        System.out.println(sup.dynamincGet());
    }
}
/**
Output:
Base staticGet()
Derived dynaminGet()
*/
```

静态方法是与类，而并非单个的对象相关联的。

## 构造器和多态

### 构造器的调用顺序

在导出类的构造器主体中，如果没有明确指定调用某个基类构造器，它就会“默默”地调用默认构造器。如果不存在默认构造器，编译器就会报错（若某个类没有构造器，编译器会自动合成出一个默认构造器）。

```java
class Meal{
    Meal(){
        System.out.println("Meal()");
    }
}
class Bread{
    Bread(){
        System.out.println("Bread()");
    }
}
class Cheese{
    Cheese(){
        System.out.println("Cheese()");
    }
}
class Lettuce{
    Lettuce(){
        System.out.println("Lettuce()");
    }
}
class Lunch extends Meal{
    Lunch(){
        System.out.println("Lunch()");
    }
}
class ProtableLunch extends Lunch{
    ProtableLunch(){
        System.out.println("ProtableLunch()");
    }
}
public class Sandwich extends ProtableLunch{
    private Bread b = new Bread();
    private Cheese c = new Cheese();
    private Lettuce l = new Lettuce();
    public Sandwich(){
        System.out.println("Sandwich()");
    }
    public static void main(String[] args){
        new Sandwich();
    }
}
/**
Output:
Meal()
Lunch()
ProtableLunch()    //从main方法入口开始调用的，以上，先初始化Sandwich父类构造器
Bread()
Cheese()
Lettuce()
Sandwich()  //组合对象
*/
```

复杂对象调用构造器要遵照下面的顺序：

1. 调用基类构造器。这个步骤会不断地反复递归下去，首先是构造器这种层次结构的根，然后是下一层导出类，等等，直到最底层的导出类。
2. 按声明顺序调用成员的初始化方法。
3. 调用导出类构造器主体。

在构造器内部，必须确保所要使用的成员都已经构建完毕。为确保这一目的，唯一的办法就是首先调用基类构造器。那么在进入导出类构造器时，在基类中可供我们访问的成员都已得到初始化。

### 继承与清理

```java
package polymorphism.cleanup;

class Characteristic{
	private String s;
	public Characteristic(String s) {
		this.s = s;
		System.out.println("Creating Characteristic " + s);
	}
	protected void dispose() {
		System.out.println("disposing Characteristic " + s);
	}
}

class Description{
	private String s;
	public Description(String s) {
		this.s = s;
		System.out.println("Creating Description " + s);
	}
	protected void dispose() {
		System.out.println("disposing Description " + s);
	}
}

class LivingCreature{
	private Characteristic p = new Characteristic("is alive");
	private Description t = new Description("Basic Living Creature");
	public LivingCreature() {
		System.out.println("LivingCreature()");
	}
	protected void dispose() {
		System.out.println("LivingCreature dispose");
		t.dispose();
		p.dispose();
	}
}

class Animal extends LivingCreature{
	private Characteristic p = new Characteristic("has heart");
	private Description t = new Description("Animal not Vegetable");
	Animal(){
		System.out.println("Animal()");
	}
	protected void dispose() {
		System.out.println("Animal dispose");
		t.dispose();
		p.dispose();
		super.dispose();
	}
}

class Amphibian extends Animal{
	private Characteristic p = new Characteristic("can live in water");
	private Description t = new Description("Both water and land");
	public Amphibian() {
		System.out.println("Amphibian");
	}
	protected void dispose() {
		System.out.println("Amphibian dispose");
		t.dispose();
		p.dispose();
		super.dispose();
	}
}

public class Frog extends Amphibian {

	private Characteristic p = new Characteristic("Croaks");
	private Description t = new Description("Eats Bugs");
	Frog(){
		System.out.println("Frog");
	}
	protected void dispose() {
		System.out.println("Frog dispose");
		t.dispose();
		p.dispose();
		super.dispose();
	}
	public static void main(String[] args) {
		Frog frog = new Frog();
		System.out.println("Bye!");
		frog.dispose();
	}
}
/**
Output:
Creating Characteristic is alive
Creating Description Basic Living Creature
LivingCreature()
Creating Characteristic has heart
Creating Description Animal not Vegetable
Animal()
Creating Characteristic can live in water
Creating Description Both water and land
Amphibian
Creating Characteristic Croaks
Creating Description Eats Bugs
Frog
Bye!
Frog dispose
disposing Description Eats Bugs
disposing Characteristic Croaks
Amphibian dispose
disposing Description Both water and land
disposing Characteristic can live in water
Animal dispose
disposing Description Animal not Vegetable
disposing Characteristic has heart
LivingCreature dispose
disposing Description Basic Living Creature
disposing Characteristic is alive
*/
```

由上面例子可以看出，万一某个**子对象**要依赖于其他**对象**。销毁的顺序应该和初始化顺序相反。对于**字段**，则意味着与声明的顺序相反（因为字段的初始化是按照声明的顺序进行的）。从输出结果可以看到，Frog对象的所有部分都是按照创建的逆序进行销毁的。

然而，如果这些成员对象中存在于其他一个或多个对象共享的情况，问题就变得更加复杂了。在这种情况下，也许就必须使用**引用计数**来跟踪仍旧访问者共享对象的对象数量了。

### 构造器内部的多态方法的行为

```java
class Glyph{
    void draw(){
        System.out.println("Glyph.draw()")；
    }
    Glyph(){
        System.out.println("Glyph() before draw()");
        draw();
        System.out.println("Glyph() after draw()");
    }
}
class RoundGlyph extends Glyph{
    private int radius = 1;
    RoundGlyph(int r){
        radius = r;
        System.out.println("RoundGlyph.RoundGlyph(),radius = " + radius);
    }
    void draw(){
        System.out.println("RoundGlyph.draw(),radius = " + radius);
    }
}
public class PolyConstructors{
    public static void main(String[] args){
        new RoundGlyph(5);
    }
}
/**
Output:
Glyph() before draw()
RoundGlyph.draw(),radius = 0  //new的是子类，先初始化父类，父类构造器中调用了draw()方法，但是调用的是子类中的方法，此时，radius还没有被初始化。
Glyph() after draw()
RoundGlyph.RoundGlyph(),radius = 5
*/
```

**即初始化的实际过程是：**

1. 在其他任何事物发生之前，将分配给对象的存储空间初始化成二进制零。
2. 如前所述那样调用基类构造器。此时，调用被覆盖后的draw()方法（要在调用RoundGlyph构造器之前调用），由于步骤 1 的缘故，此时会发现radius的值为0.
3. 按照声明的顺序调用成员的初始化方法。
4. 调用导出类的构造器主体。

因此，编写构造器时有一条有效的准则：**用尽可能简单的方法使对象进入正常状态；如果可以的话，避免调用其他方法。**

在构造器内**唯一能够安全调用**的那些方法是基类中的 final 方法（也适用于 private方法，它们自动属于 final 方法）。这些方法不能被覆盖，因此也就不会出现上述令人惊讶的问题。

## 用继承进行设计

一条通用的准则是：**用继承表达行为间的差异，并用字段表达状态上的变化。**

### 纯继承与扩展

**导出类接口中接口的扩展部分不能被基类访问，因此，一旦向上转型，就不能调用那些新方法。**

### 向下转型与运行时类型识别

由于向上转型会丢失具体的类型信息，所以我们就想，通过向下转型，也就是在继承层次中向下移动，应该能够获取类型信息。然而，向上转型是安全的，因为基类不会具有大于导出类的接口。因此，我们通过基类接口发送的消息保证都能被接受。

在Java语言中，所有转型都会得到检查！所以即使我们只是进行一次普通的加括弧形式的类型转换，在进入运行期时仍然会对其进行检查，以便保证它的确是我们希望的那种类型。如果不是，就会返回一个 ClassCastException （类型转换异常）。这种在运行期间对类型进行检查的行为称作“运行时类型识别”（RTTI）。

```java
package polymorphism.rtti;

class Useful{
	public void f() {
		
	}
	public void g() {
		
	}
}

class MoreUseful extends Useful{
	public void f() {
		
	}
	public void g() {
		
	}
	public void u() {
		
	}
	public void v() {
		
	}
	public void w() {
		
	}
}

public class RTTI {

	public static void main(String[] args) {
		Useful[] x = {
			new Useful(),new MoreUseful()	
		};
		//x[1].u  访问不到
		
		((MoreUseful) x[1]).u();  //向下转型
		((MoreUseful) x[0]).u();  //java.lang.ClassCastException6
	}
}

```

如果我们试图调用 u() 方法（它只存在于MoreUseful），就会返回一条编译时出错消息。

如果想访问MoreUseful对象的扩展接口，就可以尝试进行向下转型。如果所转类型是正确的类型，那么转型成功；否则，就会返回一个ClassCastException异常。  
[练习题](https://github.com/wangwren/Java/tree/master/Chapter8Exercise/src/polymorphism)
