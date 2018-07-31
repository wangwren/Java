# 控制执行流程

> 就像有知觉的生物一样，程序必须在执行过程中控制它的世界，并做出选择。在Java中，你要使用执行控制语句来做出选择。

## true和false

Java**不允许我们将一个数字作为布尔值**使用，虽然这在C和C++里是允许的（在这些语言里，“真”是非零，而“假”是零）。

## if-else

```java
if(Boolean-expression)
    statement;
//或
if(Boolean-expression)
    statement;
else
    statement;
```

## 迭代

while、do-while和for用来控制循环，有时将它们划分为迭代语句。语句会重复执行，直到起控制作用的布尔表达式得到“假”的结果为止。

### while

```java
while(Boolean-expression)
    statement;
```

### do-while

```java
do
    statement;
while(Boolean-expression);
```

- while和do-while唯一的区别就是
  - do-while中的语句至少会执行一次，即便表达式第一次就被计算为false
  - 而在while循环结构中，如果条件第一次就为false，那么其中的语句根本不会执行。

### for

```java
for(initialization;Boolean-expression;step)
    statement;
```

初始化（initialization）表达式、布尔表达式（Boolean-expression），或者步进（step）运算，都可以为空。每次迭代前会测试布尔表达式。若获得的结果为true，就会执行for语句后面的代码行。每次循环结束，会执行一次步进。

### 逗号操作符

使用逗号操作符，可以在for语句内定义多个变量，**但是它们必须具有相同的类型**。

```java
public class CommaOperator{
    public static void main(String[] args){
        for(int i = 1,j = i + 10;i < 5;i++,j = i * 2){
            System.out.println("i = " + i + "j = " + j);
        }
    }
}
/**
Output:
i = 1  j = 11
i = 2  j = 4
i = 3  j = 6
i = 4  j = 8
*/
```

**在一个控制表达式中，定义多个变量的这种能力只限于for循环适用。**

## Foreach语法

Java SE5引入了一种新的更加简洁的for语法用于**数组和容器**，即foreach语法，表示不必创建int变量去对由，访问项构成的序列进行计数，foreach将自动产生每一项。

```java
for(float x : f){
    
}
```

这条语句定义了一个float类型对的变量x，继而将每一个 f 的元素赋值给 x。

返回一个数组的方法都可以使用foreach。例如：String类有一个方法`toCharArray()`，它返回一个char数组。

```java
public class ForEachString{
    public static void main(String[] args){
        for(char c : "An African Swallow".toCharArray()){
        	System.out.println(c + " ");
        }
    }
}
/**
Output:
A n A f r i c a n  S w a l l o w
*/
```

## return

- return关键词有两方面的用途
  - 一方面指定一个方法返回什么值。
  - 另一方面它会导致当前的方法退出。

## break和continue

在任何迭代语句的主体部分，都可用break和continue控制循环的流程。

- break用于强行退出循环，不执行循环中剩余的语句。
- continue则停止执行当前的迭代，然后退回循环起始处，开始下一次迭代。
- 无穷循环
  - `for(;;)`和`while(true){}`

## 臭名昭著的goto

Java没有goto。

然而，Java也能完成一些类似于跳转的操作，这与break和continue这两个关键词有关。使用标签。

- 标签后面跟有冒号标识符
  - `labe1:`

标签起作用的唯一的地方**刚好**是在迭代语句**之前**。**刚好之前**的意思表明，在标签和迭代之间置于任何语句都不好。

而在迭代之前设置标签的唯一理由是：希望在其中嵌套另一个迭代或一个switch。这是由于break和continue关键词通常只中断当前循环，但若随同标签一起使用，它们就会中断循环，直到标签所在的地方。

```java
label1:
outer-iteration{
    inner-iteration{
        //...
    break; //(1)
    //...
    continue; //(2)
    //...
    continue label1; //(3)
    //...
    break label1; //(4)
    } 
}
```

- 在（1）中，break中断内部迭代，回到外部迭代。
- 在（2）中，continue使执行点移回内部迭代的起始处
- 在（3）中，continue label1同时中断内部迭代以及外部迭代，直接转到label1处；随后，它实际上是继续迭代过程，但却从外部迭代开始
- 在（4）中，break label1也会中断所有迭代，并回到label1处，但**并不重新进入迭代**。也就是说，它实际是完全中止了两个迭代。

**重点：在Java里需要使用标签的唯一理由就是因为有循环嵌套存在，而且想从多层嵌套中break和continue**。

## switch

switch也被归为一种选择语句。根据表达式的值，switch语句可以从一系列代码中选出一段去执行。

```java
switch(integral-selector){
    case integral-value1:statement;break;
    case integral-value2:statement;break;
    case integral-value3:statement;break;
    case integral-value4:statement;break;
    //...
    default:statement;
}
```

其中，Integral-selector(整数选择因子)是一个能够产生整数值的表达式，switch能将这个表达式的结果与每个integral-value（整数值）相比较。若发现相符的，就执行对应的语句（单一语句或多条语句，其中并不需要括号）。若没有发现相符的，就指定default（默认）语句。

这个选择因子，必须是int或char那样的整数值，还可以使用enum（枚举），因为enum可以和switch协调工作。