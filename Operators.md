# 操作符

> 在最底层，Java中的数据是通过使用操作符来操作的

## 使用Java操作符

几乎所有的操作符都只能“基本类型”。例外的操作符是“=”、“==”和“!=”，这些操作符能操作**所有对象**。除此之外，String类支持“+”和“+=”。

在Java中，“+”意味着**字符串连接**，并且如果必要，它还要执行**字符串转换**。当编译器观察到一个String后面紧跟一个“+”，而这个“+”的后面又紧跟一个非String。正如在输出中所看到的，它成功地将 a 和 b从 int 转换为 String。

### 赋值

对一个对象进行操作时，其实真正操作的是对对象的引用。所以倘若**将一个对象赋值给另一个对象**，实际是将“引用”从一个地方复制到另一个地方。这意味着假若对对象使用`c=d`，那么 c 和 d 都指向原本只有 d 指向的那个对象。

```java
class Tank{
    int level;
}

public class Assigment{
    public static void main(String[] args){
        Tank t1 = new Tank();
        Tank t2 = new Tank();
        t1.level = 9;
        t2.level = 47;
        System.out.println("1:t1.level: " + t1.level + ",t2.level: " + t2.level);
        /**
        将t2赋给t1，此时t1，t2指向堆内存中的同一个对象，
        改变t1的值会改变t2的值。
        **/
        t1 = t2;	//赋值的是对象的引用
        System.out.println("2:t1.level: " + t1.level + "t2.level: " + t2.level);
        t1.level = 27;
        System.out.println("3:t1.level: " + t1.level + "t2.level: " + t2.level");
    }
}
/**
Output:
1:t1.level: 9,t2.level: 47
2:t1.level: 47,t2.level: 47
3:t1.level: 27,t2.level: 27
**/
```

赋值操作的**是一个对象的引用**，所以修改t1的同时也改变了t2，这是由于t1和t2包含的是相同的引用，它们指向相同对象。（原本t1包含的对象的引用。是指向一个值为9的对象。在对t1赋值的时候，这个引用被覆盖，也就是丢了；而那个不再被引用的对象会由“垃圾回收器”自动清理）。

这种特殊的现象通常称作“别名现象”，是Java操作对象的一种基本方式。在这个例子中、如果想避免别名问题，应该这样写：

```java
t1.level = t2.level;
```

#### 方法调用的别名问题

```java
class Letter{
    char c;
}
public class PassObject{
    static void f(Letter y){
        y.c = 'z';
    }
    
    public static void main(String[] args){
        Letter x = new Letter();
        x.c = 'a';
        System.out.println("1:x.c: " + x.c);
        f(x);//将x这个引用传给了y，此时y与x指向堆内存中同一个对象，改变y的值，x的值也会改变。
        System.out.println("2:x.c: " + x.c);
    }
}
```

通过Random类的对象，程序可生成许多不同类型的**随机数字**，只需要调用方法`nextInt()`和`nextFloat()`即可（也可以调用`nextLong()`或者`nextDouble()`）。传递给`nextInt()`的参数设置了所产生的随机数上限，而其下限为0，但是这个下限并不是我们想要的，因为它会产生除0的可能性，因此我们对结果做了加 1 的操作。

## 自动递增和递减

对于前缀递增和前缀递减（如++a或--a），会先执行运算，再生成值。而对于后缀递增和后缀递减（如a++或a--），会先生成值，再执行运算。

```java
public class AutoInv{
    public static void main(String[] args){
        int i = 1;
        System.out.println("i : " + i);
        System.out.println("++i : " + ++i);
        System.out.println("i++ : =" + i++);
        System.ouy.println("i : " + i);
        System.out.println("--i : " + --i);
        System.out.println("i-- : " + i--);
        System.out.println("i : " + i);
    }
}
/**
Output:
i : 1
++i : 2
i++ : 2
i : 3
--i : 2
i-- : 2
i:1
*/
```

**对于前缀形式，我们在执行完运算后才能得到值。但对于后缀形式，则是在运算之前就得到值。**

## 关系操作符

关系操作符 == 和 != 也**使用与所有对象**。

```java
public class Equivalence{
    public static void main(String[] args){
        Integer n1 = new Integer(47);
        Integer n2 = new Integer(47);
        System.out.println(n1 = n2);
        System.out.println(n1 != n2);
    }
}
/**
Output:
false
true
*/
```

尽管对象的内容相同，然而对象的**引用却是不同的**，而 == 和 != 比较的就是**对象的引用**。

如果想比较两个对象的**实际内容**是否相同，必须使用**所有对象**都使用的特殊方法`equals()`。但这个方法**不适用于基本类型，基本类型直接使用 == 和 != 即可。**

但是如果创建了自己的类，即由程序员定义的类，使用equals()的**默认行为是比较引用**，所以需要在自己的新类中**覆盖equals()**方法。

**大多数Java类库都实现了equals()方法，以便用来比较对象的内容，而非比较对象的引用。**

### 短路

```java
test1(0) && test2(2) && test3(2)
```

第一个测试生成结果为true，所以表达式计算会继续下去，然后，第二个测试产生了一个false结果。由于这意味着整个表达式肯定为false，所以没必要继续计算剩余的表达式，那样只是浪费。事实上，如果所有的逻辑表达式都有一部分不必计算，那将获得潜在的性能提升。

## 直接常量

直接常量后面的后缀字符标志了它的类型。若为大写（或小写）的 L，代表 long（但是使用小写字母 l 容易造成混淆，因为它看起来很像数字 1）。大写（或小写）字母 F，代表float，大写（或小写）字母D，即代表double。

以二进制形式显示的结果非常有用。通过使用 Integer和 Long类的静态方法`toBinaryString()`可以很容易地实现这一点。注意，如果将比较小的类型传递给`Integer.toBinaryString()`方法，则该类型将自动被转换为int。

## 按位操作符

- 如果两个输入位都是 1，则按位“与”操作符（&）生成一个输出位 1；否则生成一个输出位 0。
- 如果两个输入位里只有一个 1，则按位“或”操作符（|）生成一个输出位 1；只有在两个输入位都是 0 的情况下，它才会生成一个输出位 0。
- 如果输入位的某一个是 1，但不全都是 1，那么按位“异或”操作符(^)生成一个输出位 1；只有两个都是 1，或两个都是 0的情况下才会输出 0。
- 按位“非”操作符（~），也被称为取反操作符，它属于一元操作符，只对一个操作数进行操作（其他按位操作符是二元操作符）。按位“非”生成与输入位相反的值，即输入 0，则输出 1,；若输入 1，则输出 0。

按位操作符可与等号（=）联合使用，以便合并运算和赋值：&=、!=、^=都是合法的（由于"~"是一元操作符，所以不可与“=”联合使用）。

## 移位操作符

**移位操作符只可用来处理整数类型**。

- 左移位操作符（<<）能按照操作符右侧指定的位数将操作符左边的操作数向左移动（在低位补 0）
- “有符号”右移位操作符（>>）则按照操作符右侧指定的位数将操作符左边的操作数向右移动。“有符号”右移位操作符使用“符号扩展”；若括号为正，则在高位插入 0；若符号为负，则在高位插入 1。
- Java中增加了一种“无符号”右移操作符（>>>），它使用“零扩展”；无论正负，都在高位插入 0。**这一操作符是C或C++中所没有的。**

但在进行“无符号”右移位结合赋值操作时，可能会遇到一个问题；如果对byte或short值进行这样的移位运算，得到的可能不是正确的结果。**它们会先被转换成int类型，再进行右移操作，然后被截断，被赋值给原来的类型，在这种情况下可能得到-1的结果。**

## 三元操作符

```java
boolean-exp ? value0 : value1
```

如果boolean-exp（布尔表达式）的结果为true，就计算value0，而且这个计算结果就是操作符最终产生的值。如果boolean-exp的结果为false，就计算value1，同样，它的结果也就成为了操作符最终产生的值。

## 类型转换

在适当的时候，Java会将一种数据类型自动转换成另一种。例如，假设我们为某浮点变量赋以一个整数值，编译器会将 int自动转换成float。

类型转换运算允许我们显示地进行这种类型的转换，或者在不能自动进行转换的时候强制进行类型转换。

Java允许我们把任何**基本类型**转换成别的**基本数据类型**，但布尔型除外，后者根本不允许进行任何类型的转换处理。

“类”数据类型不允许进行类型转换。为了将一种类转换成另一种，必须采用特殊的方法（对象可以在其**所属类型的类族之间可以进行类型转换**）。

### 截尾和舍入

在将 float 或 double 转型为整数值时，总是对该数字执行截尾。如果想要得到舍入的结果，就需要使用`java.lang.Math`中的`round()`方法（四舍五入）。

**表达式中出现的最大的数据类型决定了表达式最终结果的数据类型。**如果将一个float值与一个double值相乘，结果就是double；如果将一个 int 和一个 long 值相加，则结果为false。

在char、byte和short中，使用算术操作符会有数据类型提升的效果。对这些类型的任何一个进行算术运算，都会获得一个int结果，必须将其显示地类型转换回原来的类型（**窄化转换可能会造成信息的丢失**），以将赋给原本的类型。

但对于 int 值，却不必进行类型转化，因为所有数据都已经属于int类型。但不要放松警惕，认为一切事情都是安全的，如果两个足够大的int值执行乘法运算，结果就会溢出。

```java
public class Overflow{
    public static void main(String[] main){
        int big = Integer.MAX_VALUE;
        System.out.println("big = " + big);
        int bigger = big * 4;
        System.out.println("bigger = " + bigger);
    }
}
/**
Output:
big = 2147483647
bigger = -4    溢出了
*/
```
[操作符](https://github.com/wangwren/Java/tree/master/Chapter3Exercise/src/exercise)