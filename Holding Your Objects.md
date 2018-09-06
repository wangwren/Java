# 持有对象

> 如果一个程序只包含固定数量的且其生命周期都是已知的对象，那么这是一个非常简单的程序。

Set对于每个值都保存一个对象，Map是允许你将某些对象与其他对象关联起来的关联数组，Java容器类都可以自动地调整自己的尺寸。因此，与数组不同，在编程时，你可以将任意数量的对象放置到容器中，并且不需要担心容器应该设置为多大。

## 泛型和类型安全的容器

```java
class Apple{
    private static long counter;
    private final long id = counter++;
    public long id(){
        return id;
    }
}
class Orange{
    
}
public class ApplesAndOrangesWithoutGenerics{
    @SuppressWarnings("unchecked")
    public static void main(String[] args){
        ArrayList apples = new ArrayList();
        for(int i = 0;i < 3;i++){
            apples.add(new Apple());
        }
        apples.add(new Orange());
        for(int i = 0;i < apples.size();i++){
            ((Apple)apples.get(i)).id();
        }
    }
}
```

我们使用Java SE5所特有的注解来抑制了警告信息。注解以“@”符号开头，可以接受参数，这里的@SuppressWarnings注解及其参数表示只有有关“不受检查的异常”的警告信息应该被抑制。

当指定了某个类型作为泛型参数时，并不仅限于只能讲该确切类型的对象放置到容器中。向上转型也可以像作用于其他类型一样作用于泛型:

```java
class GrannySmith extends Apple{}
class Gala extends Apple{}
class Fuji extends Apple{}
class Braeburn extends Apple{}
public class GenericsAndUpcasting{
    public static void main(String[] args){
        ArrayList<Apple> apples = new ArrayList<Apple>();
        apples.add(new GrannySmith());
        apples.add(new Gala());
        apples.add(new Fuji());
        apples.add(new Braeburn());
        for(Apple c : apples){
            System.out.println(c);
        }
    }
}
/**
Output:(Sample)
GrannySmith@7d772e
Gala@11b86e7
Fuji@35ce36
Braeburn@757aef
*/
```

因此，可以将Apple的子类型添加到被指定为保存Apple对象的容器中。

程序的输出是从Object默认的toString()方法产生的，该方法将打印类名，后面跟随该对象的散列码的无符号十六进制表示（这个散列码是通过hashCode()方法产生的）。

## 基本概念

Java容器类类库的用途是“保存对象”，并将其划分为两个不同的概念：

1. Collection。一个独立元素的序列，这些元素都服从一条或多条规则。List必须按照插入的顺序保存元素，而Set不能有重复元素。Queue按照**排队规则**来确定对象产生的顺序（通常与它们被插入的顺序相同）。
2. Map。一组成对的“键值对”对象，允许你使用键来查找值。ArrayList允许你使用数字来查找值，因此在某种意义上讲，它将数字与对象关联在了一起。**映射表**允许我们使用**另一个对象**来查找某个对象，它也被称为“关联数组”，因为它将某些对象与另外一些对象关联在了一起；或者被称为“字典”，因为你可以使用键对象来查找值对象。Map是强大的编程工具。

尽管并非总是这样，但是在理想情况下，编写的大部分代码都是在与这些接口打交道，并且唯一需要指定所使用的精确类型的地方就是在创建的时候。因此，可以像下面这样创建一个List：

```java
List<Apple> apples = new ArrayList<Apple>();
```

注意，ArrayList已经被向上转型为List，这与前一个示例中的处理方式正好相反。使用接口的目的在于如果决定去修改你的实现，你所需的只是在创建处修改它，就像下面这样：

```java
List<Apple> apples = new LinkedList<Apple>();
```

因此，应该创建一个具体类的对象，将其转型为对应的接口，然后在其余的代码中都使用这个接口。

这种方式并非总能奏效，因为某些类具有额外的功能，例如，LinkedList具有在List接口中未包含的额外方法，而TreeMap也具有在Map接口中未包含的方法。如果你需要使用这些方法，就不能将它们向上转型为更通用的接口。

Collection接口概括了**序列**的概念---一种存放一组对象的方式。下面这个简单的示例用Integer对象填充了一个Collection（这里用ArrayList）表示，然后打印所产生的容器中的所有元素：

```java
package holding;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleCollection {

	public static void main(String[] args) {
		Collection<Integer> c = new ArrayList<Integer>();
		for(int i = 0;i < 10;i++) {
			c.add(i);
		}
		for(Integer i : c) {
			System.out.print(i + " ");
		}
	}
}
/**
0 1 2 3 4 5 6 7 8 9 
*/
```

这个示例只使用了Collection方法，因此任何继承自Collection的类的对象都可以正常工作，但是ArrayList是最基本的序列类型。

add()方法的名称就表明它是要将一个新元素放置到Collection中。但是，文档中非常仔细地叙述到：“要确保这个Collection包含指定的元素。”这是因为考虑到来了Set的含义，因为在Set中，只有元素不存在的情况下才会添加。在使用ArrayList，或者任何种类的List时，add()总是表示“把它放进去”，因为List不关心是否存在重复。

## 添加一组元素

在java.util包中的 Arrays 和 Collections 类中都有很多实用方法，可以在一个Collection中添加一组元素。

- Arrays.asList()方法接受一个数组或是一个用逗号分隔的元素列表（使用可变参数），并将其转换为一个List对象。
- Collections.addAll()方法接受一个Collection对象，以及一个数组或是一个用逗号分割的列表，将元素添加到Collection中。

```java
package holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AddingGroups {

	public static void main(String[] args) {
		Collection<Integer> collection = 
				new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
		Integer[] moreInts = {6,7,8,9,10};
		collection.addAll(Arrays.asList(moreInts));
		Collections.addAll(collection, 11,12,13,14,15);
		Collections.addAll(collection, moreInts);
		List<Integer> list = Arrays.asList(16,17,18,19,20);
		list.set(1, 99);
		//list.add(21); 运行时报错，底层数组无法调整大小
		//java.lang.UnsupportedOperationException不支持此操作。
	}
}
```

API文档中对Arrays.asList()方法的说明：

> public static <T> List<T> asList(T... a)
>
> 返回一个受**指定数组支持的固定大小的列表**（对返回列表的更改会“直接”写到数组）。此方法同Collection.toArray()一起，充当了基于数组的API与基于collection的API之间的桥梁。

Collection的构造器可以接受另一个Collection，用它来将自身初始化，因此你可以使用Arrays.List()来为这个构造器产生输入。但是Collection.addAll()方法运行起来要快得多，而且构造一个不包含元素的Collection，然后调用Collections.addAll()这种方式很方便，因此它是首选方式。

Collection.addAll()成员方法只能接受另一个Collection对象作为参数，因此它不如Arrays.asList()或Collections.addAll()灵活，这两个方法使用的都是可变参数列表。

你也可以直接使用Arrays.asList()的输出，将其当做List，但是这种情况下，其底层表示的是数组，因此不能调整尺寸。如果试图用 add() 或 delete() 方法在这种列表中添加或删除元素，就有可能引发去改变数组尺寸的尝试，因此将在运行时获得“Unsupported Operation”(不支持的操作)错误。

Arrays.asList()方法的限制是它对所产生的List的类型做出了最理想的假设，而并没有注意你对它会赋予什么样的类型。有时这就会引发问题：

```java
package holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Snow{}
class Powder extends Snow{}
class Light extends Powder{}
class Heavy extends Powder{}
class Crusty extends Snow{}
class Slush extends Snow{}

public class AsListInterence {

	public static void main(String[] args) {
		List<Snow> snow1 = Arrays.asList(new Crusty(),new Slush(),new Powder());
		//不会编译
		//编译器说：
		//  found:java.util.List<Power>
		//  required:java.util.List<Snow>
		//书中使用的是1.5jdk，该代码，在jdk1.8中没问题
		List<Snow> snow2 = Arrays.asList(new Light(),new Heavy());
		
		List<Snow> snow3 = new ArrayList<Snow>();
		Collections.addAll(snow3, new Light(),new Heavy());
		
		List<Snow> snow4 = Arrays.<Snow>asList(new Light(),new Heavy());
		
	}
}

```

上述代码在JDK1.8环境中没问题，以下为书中1.5解答：

> 当试图创建snow2时。Arrays.asList()中只有Powder类型，因此它会创建List<Power>而不是List<Snow>，尽管Collections.addAll()工作的很好，因为它从第一个参数中了解到了目标类型是什么。
>
> 正如从创建snow4的操作中所看到的，可以在Arrays.asList()中间插入一条“线索”，以告诉编译器对于由Arrays.asList()产生的List类型，实际的目标类型应该是什么。这称为显示类型参数说明。

## 容器的打印

你必须使用Arrays.toString()来产生数组的可打印表示，但是打印容器无需任何帮助。下面是一个例子，这个例子中也介绍了一些基本类型的容器：

```java
package holding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class PrintingContainers {

	static Collection<String> fill(Collection<String> collection) {
		collection.add("rat");
		collection.add("cat");
		collection.add("red");
		collection.add("red");
		return collection;
	}
	static Map<String, String> fill(Map<String,String> map) {
		map.put("rat", "Fuzzy");
		map.put("cat", "Rags");
		map.put("dog", "Bosco");
		map.put("dog", "Spot");
		return map;
	}
	public static void main(String[] args) {
		System.out.println(fill(new ArrayList<String>()));
		System.out.println(fill(new LinkedList<String>()));
		System.out.println(fill(new HashSet<String>()));
		System.out.println(fill(new TreeSet<String>()));
		System.out.println(fill(new LinkedHashSet<String>()));
		System.out.println(fill(new HashMap<String,String>()));
		System.out.println(fill(new TreeMap<String,String>()));
		System.out.println(fill(new LinkedHashMap<String,String>()));
	}
}
/**
[rat, cat, red, red]
[rat, cat, red, red]
[red, rat, cat]
[cat, rat, red]
[rat, cat, red]
{rat=Fuzzy, cat=Rags, dog=Spot}
{cat=Rags, dog=Spot, rat=Fuzzy}
{rat=Fuzzy, cat=Rags, dog=Spot}
*/
```

这里展示了Java容器类库中的两种主要类型，它们的区别在于容器中每个“槽”保存的元素个数。Collection在每个槽中只能保存一个元素。此类容器包括：

- List，它以特定的顺序保存一组元素；
- Set，元素不能重复；
- Queue，只允许在容易的一“端”插入对象，并从另外一“端”移除对象（对于本例来说，这只是另外一种观察序列的方式，因此并没有展示它）。
- Map在每个槽内保存了两个对象，即键和与之相关联的值。

查看输出会发现，默认的打印行为（使用容器提供的toString()方法）即可生成可读性很好的结果。Collection打印出来的内容用方括号括住，每个元素由逗号分隔。Map则用大括号括住，键与值由等号联系（键在等号左边，值在右边）。

第一个fill()方法可以作用于所有类型的Collection，这些类型都实现了用来添加新元素的add()方法。

ArrayList和LinkedList都是List类型，从输出可以看出，它们都按照被插入的顺序保存元素。两者的不同之处不仅在于执行某些类型的操作时的性能，而且LinkedList包含的操作也多余ArrayList。

HashSet、TreeSet和LinkedHashSet都是Set类型，输出显示在Set中，每个相同的项只有保存一次，但是输出也显示了不同的Set实现存储元素的方式也不同。HashSet使用的是相当复杂的方式来存储元素的，**此刻你只需要知道这种技术是最快的获取元素方式**，因此，存储的顺序看起来并无意义。如果存储顺序最重要，那么可以使用TreeSet，它按照比较结果的升序保存对象；或者使用LinkedHashSet，它按照被添加的顺序保存对象。

Map（也被称为关联数组）使得你可以用键来查找对象，就像一个简单的数据库。键所关联的对象称为键。使用Map可以将美国州名与其首府联系起来，如果想知道Ohio的首府，可以将Ohio作为键进行查找，几乎就像使用数组下标一样。正由于这种行为，**对于每一个键，Map只接受存储一次。**

Map.put(key,value)方法将增加一个值（你想要增加的对象），并将它与某个键关联起来。Map.get(key)方法将产生与这个键相关联的值。上面的示例只添加了键-值对。

注意，你不必指定（或考虑）Map的尺寸，因为它自己会自动地调整尺寸。Map还知道如何打印自己，它会显示相关关联的键和值。**键和值在Map中的保存顺序并不是它们的插入顺序，因为HashMap实现使用的是一种非常快的算法来控制顺序。**

本例使用了三种基本风格的Map：HashMap、TreeMap和LinkedHashMap。与HashSet一样，**HashMap也提供了最快的查找技术，也没有按照任何明显的顺序来保存其元素**。**TreeMap按照比较结果的升序保存键，而LinkedHashMap则按照插入顺序保存键，同时还保留了HashMap的查询速度。**

## List

