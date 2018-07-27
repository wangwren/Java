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

