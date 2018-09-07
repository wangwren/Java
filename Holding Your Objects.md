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
- Queue，只允许在容器的一“端”插入对象，并从另外一“端”移除对象（对于本例来说，这只是另外一种观察序列的方式，因此并没有展示它）。
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

List承诺可以将元素维护在特定的序列中。List接口在Collection的基础上添加了大量的方法，使得可以在List的中间插入和移除元素。

有两种类型的List：

- 基本的ArrayList，它擅长随机访问元素，但是在List的中间插入和移除元素时较慢。
- LinkedList，它通过代价较低的在List中间进行的插入和删除操作，提供了优化的顺序访问。Linked在随机访问方面相对比较慢，但是它的特性集较ArrayList更大。

List允许在它被创建之后添加元素、移除元素，或者自我调整尺寸。这正是它的重要价值所在：**一种可修改的序列。**

可以用 contains()方法来确定某个对象是否在列表中。如果想移除一个对象，则可以将这个对象的引用传递给 remove() 方法。同样，如果有一个对象的引用，则可以使用indexOf()来发现该对象在List中所处位置的索引编号。

当确定一个元素是否属于某个List，发现某个元素的索引，以及从某个List中移除一个元素时，都会用到equals()方法（它是根类Object的一部分）。

## 迭代器

任何容器类，都必须有某种方式可以插入元素并将它们再次取回。毕竟，持有事物是容器最基本的工作。对于List，add()是插入元素的方法之一，而get()是取出元素的方法之一。

如果从更高层的角度思考，会发现这里有一个缺点：要使用容器，必须对容器的确切类型编程。初看起来这没什么不好，但是考虑下面的情况：如果原本是对着List编码的，但是后来发现如果能够把相同的代码应用于Set，将会显得非常方便，此时应该怎么做？或者打算从头开始编写通用的代码，它们只是容器，不知道或者说不关心容器的类型，那么如何才能不重写代码就可以应用于不同类型的容器？

**迭代器**（也是一种设计模式）的概念可以用于达成此目的。迭代器是一个**对象**，它的工作是遍历并选择序列中的对象，而客户端程序员不必知道或关心该序列底层的结构。此外，迭代器通常被称为**轻量级对象**：创建它的代价小。因此，经常可以见到对迭代器有些奇怪的限制：例如，Java的  Iterator只能**单向移动**，这个Iterator只能用来：

1. 使用方法iterator()要求容器返回一个Iterator。Iterator将准备好返回序列的第一个元素。
2. 使用 next() 获得序列中的下一个元素。
3. 使用 hasNext() 检查序列中是否还有元素。
4. 使用 remove() 将迭代器新近返回的元素删除。

```java
package holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SimpleIteration {

	public static void main(String[] args) {
		List<Integer> list = 
				new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()) {
			Integer i = it.next();
			System.out.print(i + " ");
		}
		System.out.println();
		for(Integer i : list) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		it = list.iterator();
		for(int i = 0;i < 6;i++) {
			it.next();
			it.remove();
		}
		System.out.println(list);
	}
}
/**
1 2 3 4 5 6 7 8 9 10 
1 2 3 4 5 6 7 8 9 10 
[7, 8, 9, 10]
*/
```

有了Iterator就不必为容器中元素的数量操心了，那是由hasNext()和next()关心的事情。

如果只是向前遍历List，并不打算修改List对象本身，那么可以看到foreach语法会显得更加简洁。

Iterator还可以移除由next()产生的最后一个元素，这意味着在调用remove()之前必须先调用next()。

**接受对象容器并传递它，从而在每个对象上都执行操作。**

现在考虑创建一个display()方法，它不必知晓容器的确切类型：

```java
package holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class CrossContainerIteration {

	public static void display(Iterator<Integer> it) {
		while(it.hasNext()) {
			Integer i = it.next();
			System.out.print(i + " ");
		}
		System.out.println();
	}
	public static void main(String[] args) {
		ArrayList<Integer> aList = 
				new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		LinkedList<Integer> linkedList = 
				new LinkedList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		HashSet<Integer> hashSet = 
				new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		TreeSet<Integer> treeSet = 
				new TreeSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		display(aList.iterator());
		display(linkedList.iterator());
		display(hashSet.iterator());
		display(treeSet.iterator());
	}
}
/**
1 2 3 4 5 6 7 8 9 10 
1 2 3 4 5 6 7 8 9 10 
1 2 3 4 5 6 7 8 9 10 
1 2 3 4 5 6 7 8 9 10 
*/
```

注意，display()方法不包含任何有关它所遍历的序列的类型信息，而这也展示了Iterator的真正威力：能够将遍历序列的操作与序列底层的结构分离。正由于此：迭代器统一了对容器的访问方式。

### ListIterator

ListIterator是一个更加强大的Iterator的子类型，它只能用于各种List类的访问。尽管Iterator只能向前移动，但是ListIterator可以双向移动。它还可以产生相对于迭代器在列表中指向的当前位置的前一个和后一个元素的索引，并且可以使用set()方法替换它访问过的最后一个元素。

可以通过调用listIterator()方法产生一个指向List开始处的ListIterator，并且还可以通过调用listIterator(n)方法创建一个一开始就指向列表索引为 n 的元素处的 ListIterator。

```java
package holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class ListIteration {

	public static void main(String[] args) {
		List<String> list = 
				new ArrayList<String>(Arrays.asList("A","B","C","D","E","F","G"));
		ListIterator<String> lit = list.listIterator();
		while(lit.hasNext()) {//以正向遍历列表时，如果列表迭代器有多个元素，则返回 true
			//next()返回下一个元素
			//nextIndex()返回对 next 的后续调用所返回元素的索引。
			//previousIndex()返回对 previous 的后续调用所返回元素的索引。
			System.out.print(lit.next() + "," + lit.nextIndex() +
					"," + lit.previousIndex() + ";");
		}
		System.out.println();
		
		while(lit.hasPrevious()) {//如果以逆向遍历列表，列表迭代器有多个元素，则返回 true。
			//previous()返回列表中的前一个元素。
			System.out.print(lit.previous() + "," +lit.previousIndex() + ";");//倒着输出
		}
		System.out.println();
		System.out.println(list);
		
		lit = list.listIterator(3);
		while(lit.hasNext()) {
			lit.next();
			lit.set("Z");	//替换从 3 位置开始，向前的所有元素
		}
		System.out.println(list);
	}
}
/**
A,1,0;B,2,1;C,3,2;D,4,3;E,5,4;F,6,5;G,7,6;
G,5;F,4;E,3;D,2;C,1;B,0;A,-1;
[A, B, C, D, E, F, G]
[A, B, C, Z, Z, Z, Z]
*/
```

## LinkedList

