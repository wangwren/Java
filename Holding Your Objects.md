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

LinkedList也像ArrayList一样实现了基本的List接口，**但是它执行某些操作（在List的中间插入和移除）时比ArrayList更高效，但在随机访问操作方面却要逊色一些。**

**LinkedList还添加了可以使用其用作栈、队列或双端队列的方法。**

这些方法中有些彼此之间只是名称有些差异，或者只存在些许差异，以使得这些名字在特定用法的上下文环境中更加适用（特别是在Queue中）。

例如，getFirst() 和 element()完全一样，它们都返回列表的头（第一个元素），而并不移除它，如果 List 为空，则抛出NoSuchElementException。peek()方法与这两个方式只是稍有差异，它在列表为空时返回 null。

addFirst()与add()和addLast()相同，它们都将某个元素插入到列表的尾部。

removeLast()移除并返回列表的最后一个元素。

## Stack

“栈”通常是指“后进先出”（LIFO）的容器。有时栈也被称为叠加栈。因为最后“压入”栈的元素，第一个“弹出”栈。经常用来类比栈的事物是装有弹簧的储放器中的自助餐托盘，最后装入的托盘总是最先拿出使用。

LinkedList具有能够直接实现栈的所有功能的方法，因此可以直接将LinkedList作为栈使用，不过，有时一个真正的“栈”更能把使其讲清楚：

```java
package holding;

import java.util.LinkedList;

public class Stack<T> {
	private LinkedList<T> storage = new LinkedList<T>();
	/**
	 * 栈顶添加元素
	 * @param t
	 */
	public void push(T t) {
		storage.addFirst(t);
	}
	/**
	 * 获取栈顶元素
	 * @return
	 */
	public T peek() {
		return storage.getFirst();
	}
	/**
	 * 移除栈顶元素，并返回被移除的元素
	 * @return
	 */
	public T pop() {
		return storage.removeFirst();
	}
	public boolean empty() {
		//如果栈为空，则返回true
		return storage.isEmpty();
	}
	public String toString() {
		return storage.toString();
	}
}
```

这里通过使用泛型，引入了在栈的类定义中最简单的可行示例。类名之后的\<T>告诉编译器这将是一个参数化类型，而其中的类型参数，即在类被使用时将会被实际类型替换的参数，就是T。大体上，这个类是在声明“我们在定义一个可以持有 T 类型对象的 Stack”。Stack是用LinkedList实现的，而LinkedList也被告知它将持有T类型对象。push()接受的是T类型的对象，而 peek() 和 pop() 将返回T类型的对象。peek()方法将提供栈顶元素，但是并不将其从栈顶移除，而pop()将移除并返回栈顶元素。

如果只需要栈的行为，这里使用继承就不合适了，因为这样会产生具有LinkedList的其他所有方法的类。下面演示了这个新的Stack类：

```java
package holding;

public class StackTest {
	public static void main(String[] args) {
		Stack<String> stack =  new Stack<String>();
		for(String s : "My dog has fleas".split(" ")) {
			stack.push(s);
		}
		//如果栈不为空
		while(!stack.empty()) {
			System.out.print(stack.pop() + " ");
		}
	}
}
/**
fleas has dog My 
*/
```

java.util.Stack 与 自己编写的Stack具有相同的接口，但是在java.util中没有任何公共的Stack接口，这可能是因为在Java1.0中的设计欠佳，最初的java.util.Stack类占用了这个名字。尽管已经有了java.util.Stack，但是LinkedList可以产生更好的Stack，因此，上述自己写的Stack所采用的的方式更是可取的。

> 现在，任何对Stack的引用都将选择net.mindview.util版本，而在选择java.util.Stack时，必须使用全限定名称。

## Set

Set不保证重复的元素。如果试图将相同对象的多个实例添加到Set中，那么它就会组织这种重复现象。Set中最常被使用的是测试归属性，可以很容易地询问某个对象是否在某个Set中。正因如此，查找就成为了Set中最重要的操作，因此通常都会选择一个HashSet的实现，它专门对快速查找进行了优化。

Set具有与Collection完全一样的接口，因此没有任何额外的功能，不像前面有两个不同的List。实际上Set就是Collection，只是行为不同。（这是继承与多态思想的典型应用：表现不同的行为。）Set是基于对象的值来确定归属性的，而更加复杂的问题在17章还有。

下面是使用存放Integer对象的HashSet的实例：

```java
package holding;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SetOfInteger {

	public static void main(String[] args) {
		Random rand = new Random(47);
		Set<Integer> inset = new HashSet<Integer>(); 
		for(int i = 0;i < 10000;i++) {
			inset.add(rand.nextInt(30));
		}
		System.out.println(inset);
	}
}
/**
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
*/
```

在 0 到 29之间的10000个随机数被添加到了Set中，因此可以想象，每一个数都重复了许多次。但是，每一个数只有一个实例出现在结果中。

还可以注意到，输出的顺序没有任何规律可循，这是因为出于速度原因的考虑，HashSet使用了散列。HashSet所维护的顺序与TreeSet或LinkedHashSet都不同，因为它们的实现具有不同的元素存储方式。TreeSet将元素存储在红-黑树数据结构中，而HashSet使用的是散列函数。LinkedHashSet因为查询速度的原因也使用了散列，但是看起来它使用了链表来维护元素的插入顺序。

对于上面程序中的输出结果，我运行了很多次，都是有序的，于是Google一下，找到了答案，以下：

> 作者：RednaxelaFX
>
> 链接：https://www.zhihu.com/question/28414001/answer/40733996
>
> 来源：知乎
>
> 著作权归作者所有，转载请联系作者获得授权。
>
> “不保证有序”和“保证无序”不等价，HashSet的iterator是前者而不是后者，所以在一次运行中看到有序的结果也是正常的，但不能依赖这个有序行为。
>
> 况且HashSet并不关心key的“排序”，就算其iterator“有序”通常也是说“按元素插入顺序”（LinkedHashSet就支持插入顺序遍历）。题主在此看到的所谓“有序”纯粹是个巧合。然后我复制粘贴了题主的代码运行了一次：
>
>
>
> ```java
> $ java SetOfInteger
> 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 17 16 19 18 21 20 23 22 25 24 27 26 29 28 
> $ java -version
> java version "1.7.0-internal-zing_99.99.99.99.dev"
> Zing Runtime Environment for Java Applications (build 1.7.0-internal-zing_99.99.99.99.dev-b65)
> Zing 64-Bit Tiered VM (build 1.7.0-zing_99.99.99.99.dev-b870-product-azlinuxM-X86_64, mixed mode)
> ```
>
> （Zing JDK7的开发版）
> 就不是有序的嘛。同样在Oracle JDK7u51上也是如此：
>
> ```java
> $ java SetOfInteger
> 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 17 16 19 18 21 20 23 22 25 24 27 26 29 28 
> $ java -version
> java version "1.7.0_51"
> Java(TM) SE Runtime Environment (build 1.7.0_51-b13)
> Java HotSpot(TM) 64-Bit Server VM (build 24.51-b03, mixed mode)
> ```
>
> 换到Zing JDK8：
>
> ```java
> $ java SetOfInteger
> 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 
> $ java -version
> java version "1.8.0-internal-zing_99.99.99.99.dev"
> Zing Runtime Environment for Java Applications (build 1.8.0-internal-zing_99.99.99.99.dev-b65)
> Zing 64-Bit Tiered VM (build 1.8.0-zing_99.99.99.99.dev-b870-product-azlinuxM-X86_64, mixed mode)
> ```
>
> 再换到Oracle JDK8u25：
>
> ```java
> $ java SetOfInteger
> 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 
> $ java -version
> java version "1.8.0_25"
> Java(TM) SE Runtime Environment (build 1.8.0_25-b17)
> Java HotSpot(TM) 64-Bit Server VM (build 25.25-b02, mixed mode)
> ```
>
> 就看到了题主说的有序行为。
>
> JDK8的HashSet实现变了，导致元素插入的位置发生了变化；iterator自身实现的顺序倒没变，还是按照内部插入的位置顺序来遍历，于是题主就看到了JDK7和JDK8的结果不一样。具体来说，是JDK7与JDK8的java.util.HashMap的hash算法以及HashMap的数据布局发生了变化。
>
> 题主插入HashSet的是Integer，其hashCode()实现就返回int值本身。所以在对象hashCode这一步引入了巧合的“按大小排序”。
> 然后HashMap.hash(Object)获取了对象的hashCode()之后会尝试进一步混淆。
> JDK8版java.util.HashMap内的hash算法比JDK7版的混淆程度低；在[0, 2^32-1]范围内经过HashMap.hash()之后还是得到自己。题主的例子正好落入这个范围内。外加load factor正好在此例中让这个HashMap没有hash冲突，这就导致例中元素正好按大小顺序插入在HashMap的开放式哈希表里。
> 根据它的实现特征，把题主的例子稍微修改一下的话：
>
> ```text
> $ cat SetOfInteger.java 
> import java.util.*;
> 
> public class SetOfInteger {
>     public static void main(String[] args){
>         Random rand=new Random(47);
>         Set<Integer> intset=new HashSet<Integer>();
>         for (int i=0;i<10000;i++){
>             intset.add(rand.nextInt(30) + (1 << 16));
>         }
>         Iterator<Integer> iterator=intset.iterator();
>         while (iterator.hasNext()){
>             System.out.print((iterator.next() - (1 << 16)) +" ");
>         }
>     }
> }
> $ java SetOfInteger
> 1 0 3 2 5 4 7 6 9 8 11 10 13 12 15 14 17 16 19 18 21 20 23 22 25 24 27 26 29 28 
> $ java -version
> java version "1.8.0_25"
> Java(TM) SE Runtime Environment (build 1.8.0_25-b17)
> Java HotSpot(TM) 64-Bit Server VM (build 25.25-b02, mixed mode)
> ```
>
> 就可以看到顺序不一样了。修改的内容就是把插入的数字先加上2的16次方，然后拿出来之后再减去2的16次方

而我本身就是用的jdk8版本，jdk8对HashMap的实现做了改变，**认真读上述引用。**

如果想对结果排序，一种方式就是用TreeSet来代替HashSet：

```java
package holding;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class SortedSetOfInteger {
	public static void main(String[] args) {
		Random rand = new Random(47);
		Set<Integer> inset = new TreeSet<Integer>(); 
		for(int i = 0;i < 10000;i++) {
			inset.add(rand.nextInt(30));
		}
		System.out.println(inset);
	}
}
/**
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
*/
```

你将会执行的最常见的操作之一，就是使用contains()测试Set的归属性。

```java
package holding;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetOperations {
	public static void main(String[] args) {
		Set<String> set1 = new HashSet<String>();
		Collections.addAll(set1, "A B C D E F G H I J K L".split(" "));
		set1.add("M");
		System.out.println("H: " + set1.contains("H"));
		System.out.println("N: " + set1.contains("N"));
		Set<String> set2 = new HashSet<String>();
		Collections.addAll(set2, "H I J K L".split(" "));
		System.out.println("set2 in set1: " + set1.contains(set2));
		set1.remove("H");
		System.out.println("set1: " + set1);
		System.out.println("set2 in set1: " + set1.contains(set2));
		set1.remove(set2);
		System.out.println("set2 removed from set1: " + set1);
		Collections.addAll(set1, "X Y Z".split(" "));
		System.out.println("'X Y Z' added to set1: " + set1);
	}
}
/**
H: true
N: false
set2 in set1: false
set1: [A, B, C, D, E, F, G, I, J, K, L, M]
set2 in set1: false
set2 removed from set1: [A, B, C, D, E, F, G, I, J, K, L, M]
'X Y Z' added to set1: [A, B, C, D, E, F, G, I, J, K, L, M, X, Y, Z]
*/
```

这些方法名都是自解释型的，而有几个方法可以在JDK文档中找到。

能够产生每个元素都唯一的列表是相当有用的功能。

如果想要按照字母序排序，那么可以向TreeSet的构造器传入String.CASE_INSENTIVE_ORDER比较器（比较器就是建立排序顺序的对象）。

## Map

将对象映射到其他对象的能力是一种解决编程问题的杀手锏。例如，考虑一个程序，它将用来检查Java的Random类的随机性。理想状态下，Random可以将产生理想的数字分布，但要想测试它，则需要生成大量的随机数，并对落入各种不同范围的数字进行计数。Map可以很容易地解决该问题。在本例中，键是由Random产生的数字，而值是该数字出现的次数：

```java
package holding;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Statistics {

	public static void main(String[] args) {
		Random rand = new Random(47);
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		for(int i = 0;i < 10000;i++) {
			int key = rand.nextInt(20);
			Integer value = map.get(key);
			//如果value不为空，则value+1之后存入map中。
			//不能再定义一个变量来记录，这样记录的是整个，只要有就+1了
			//应该是针对当前的key值，如果有，就+1，之后存入
			map.put(key, value == null ? 1 : value + 1);
		}
		System.out.println(map);
	}
}
/**
{0=481, 1=502, 2=489, 3=508, 4=481, 5=503, 6=519, 7=471, 8=468, 9=549, 10=513, 11=531, 12=521, 13=506, 14=477, 15=497, 16=533, 17=509, 18=478, 19=464}
*/
```

在main()中，自动包装机制将随机生成的 int 转换为HashMap可以使用的Integer引用（不能使用基本类型的容器）。如果键不在容器中，get()方法将返回null（这表示该数字第一次被找到）。否则，get()方法将产生与该键相关联的Integer值，然后这个值被递增。

可以使用containsKey()和containsValue()来测试一个Map，以便查看它是否包含某个键或某个值。

Map可以返回它的键的Set，它的值的Collection，或者它的键值对的Set。keySet()方法产生了由在map中的所有键组成的Set，它在foreach语句中被用来迭代遍历Map。
## Queue
队列是一个典型的**先进先出**(FIFO)的容器。即从容器的一端放入事物，从另一端取出，并且事物放入容器的顺序与取出的顺序是相同的。队列常被当作一种可靠的将对象从程序的某个区域传输到另一个区域的途径。队列在并发编程中特别重要，因为它们可以安全地将对象从一个任务传输给另一个任务。

LinkedList提供了方法以支持队列的行为，并且它实现了Queue接口，因此LinkedList可以用作Queue的一种实现。通过将LinkedList向上转型为Queue，下面的示例使用了在Queue接口中与Queue相关的方法：

```java
package holding;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class QueueDemo {

	public static void printQ(Queue queue) {
		while(queue.peek() != null) {
			System.out.print(queue.remove() + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		Queue<Integer> queue = new LinkedList<Integer>();
		Random rand = new Random();
		for(int i = 0;i < 10;i++) {
			queue.offer(rand.nextInt((i + 10)));
		}
		System.out.println(queue);
		Queue<Character> qc = new LinkedList<Character>();
		for(char c : "Brontosaurus".toCharArray()) {
			qc.offer(c);
		}
		System.out.println(qc);
	}
}
/**
[1, 1, 11, 2, 4, 3, 7, 2, 14, 8]
[B, r, o, n, t, o, s, a, u, r, u, s]
*/
```

offer() 方法是与Queue相关的方法之一，它在允许的情况下，将一个元素插入到队尾，或者返回false。

peek()和element()都将在不移除的情况下返回队头，但是peek()方法在队列为空时返回null，而element()会抛出 NoSuchElementException 异常。

poll()和remove()方法将移除并返回队头，但是poll()在队列为空时返回null，而remove()会抛出NoSuchElementException异常。

自动包装机制会自动地将nextInt()方法的int结果转换为queue所需的Integer对象，将 char c 转换为 qc 所需的 Character 对象。Queue接口窄化了对LinkedLisrt的方法访问权限，以使得只有恰当的方法才可以使用，因此，能够访问的LinkedList的方法会变少。

注意，与Queue相关的方法提供了完整而独立的功能。即，对于Queue所继承的Collection，在不需要使用它的任何方法的情况下，就可以拥有一个可用的Queue。

### PriorityQueue

先进先出描述了最典型的队列规则。队列规则是指在给定一组队列中的元素的情况下，确定下一个弹出队列的元素的规则。先进先出声明的是下一个元素应该是等待时间最长的元素。

**优先级队列**声明下一个弹出元素是最需要的元素（具有最高的优先级）。例如，在飞机场，当飞机临近起飞时，这架飞机的乘客可以在办理登机手续时排到队头。如果构建了一个消息系统，某些消息比其他消息更重要，因而应该更快地得到处理，那么它们何时得到处理就与它们何时到达无关。PriorityQueue添加到Java SE5中，是为了提供这种行为的一种自动实现。

当在PriorityQueue上调用offer()方法来插入一个对象时，这个对象会在队列中被排序。默认的排序将使用对象在队列中的自然顺序，但是可以通过提供自己的Comparator来修改这个顺序。PriorityQueue可以确保当你调用peek()、poll()和remove()方法时，获取的元素将是队列中优先级最高的元素。

让PriorityQueue与Integer、String和Character这样的内置类型一起工作易如反掌。在下面的示例中，第一个值集与前一个示例中的随机值相同，因此可以看到它们从PriorityQueue中弹出的顺序与前一个示例不同：

```java
package holding;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

public class PriorityQueueDemo {
	public static void main(String[] args) {
		PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>();
		Random rand = new Random(47);
		for(int i = 0;i < 10;i++) {
			priorityQueue.offer(rand.nextInt(i + 10));
		}
		QueueDemo.printQ(priorityQueue);
		
		List<Integer> ints = 
				Arrays.asList(25,22,20,18,14,9,3,1,1,2,3,9,14,18,21,23,25);
		priorityQueue = new PriorityQueue<Integer>(ints);
		QueueDemo.printQ(priorityQueue);
		
		priorityQueue = new PriorityQueue<>(ints.size(), Collections.reverseOrder());
		priorityQueue.addAll(ints);
		QueueDemo.printQ(priorityQueue);
		
		String fact = "EDUCATION SHOULD ESCHEW OBFUSCATION";
		List<String> strings = Arrays.asList(fact.split(""));
		PriorityQueue<String> stringPQ = new PriorityQueue<String>(strings);
		QueueDemo.printQ(stringPQ);
		
		//Collections.reverseOrder() API解释：
		//返回一个比较器，它强行逆转实现了 Comparable 接口的对象 collection 的自然顺序。
		//（自然顺序是通过对象自身的 compareTo 方法强行排序的。）
		//此方法允许使用单个语句，以逆自然顺序对实现了 Comparable 接口的对象 collection（或数组）进行排序（或维护）。
		//例如，假设 a 是一个字符串数组。那么：
        //Arrays.sort(a, Collections.reverseOrder());
		//将按照逆字典（字母）顺序对数组进行排序。
		//返回的比较器是可序列化的。 
		stringPQ = new PriorityQueue<String>(strings.size(),Collections.reverseOrder());
		stringPQ.addAll(strings);
		QueueDemo.printQ(stringPQ);
		
		Set<Character> charSet = new HashSet<Character>();
		for(char c : fact.toCharArray()) {
			charSet.add(c);
		}
		PriorityQueue<Character> characterPQ = new PriorityQueue<Character>(charSet);
		QueueDemo.printQ(characterPQ);
	}
}
/**
0 1 1 1 1 1 3 5 8 14 
1 1 2 3 3 9 9 14 14 18 18 20 21 22 23 25 25 
25 25 23 22 21 20 18 18 14 14 9 9 3 3 2 1 1 
      A A B C C C D D E E E F H H I I L N N O O O O S S S T T U U U W 
W U U U T T S S S O O O O N N L I I H H F E E E D D C C C B A A       
  A B C D E F H I L N O S T U W
*/
```

可以看到，重复是允许的，**最小的值拥有最高的优先级**（如果是String，空格也可以算作值，并且比字母的优先级高）。为了展示你可以使用怎样的方法通过提供自己的Comparator对象来改变排序，第三个对PriorityQueue\<Integer>的构造器调用，和第二个对PriorityQueue\<String>的调用使用了由Collection.reverseOrder() （新添加到Java SE5中的）产生的反序的Comparator。（代码中也有解释该方法）

最后一部分添加了一个HashSet来消除重复的Character，这么做只是为了增添点乐趣。

Integer、String和Character可以与PriorityQueue一起工作，因为这些类已经内建了自然排序。如果你想在PriorityQueue中使用自己的类，就必须包括额外的功能以产生自然排序，或者必须提供自己的Comparator。

## Collection和Iterator

Collection是描述所有序列容器的共性的根接口，它可能会被认为是一个“附属接口”，即因为要表示其他若干个接口的共性而出现的接口。另外，java.util.AbstractCollection类提供了Collection的默认实现，使得你可以创建AbstractCollection的子类型，而其中没有不必要的代码重复。

使用接口描述的一个理由是它可以使我们能够创建更通用的代码。通过针对接口而非具体实现来编码，我们的代码可以应用于更多的对象类型。因此，**如果我编写的方法将接受一个Collection，那么该方法就可以应用于任何实现了Collection的类--这也就使得一个新类可以选择去实现Collection接口，以便我的方法可以使用它。**

但是，有一点很有趣，就是我们注意到标准C++类库中并没有其容器的任何公共基类--容器之间的所有共性都是通过迭代器达成的。在Java中，遵循C++的方式看起来似乎很明智，即用迭代器而不是Collection来表示容器之间的共性。但是，这两种方法绑定到一起，因为实现Collection就意味着需要提供iterator()方法。

```java
package holding;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public class CollectionSequence extends AbstractCollection<Integer> {

	private Integer[] pets = {1,2,3,4,5,6,7,8};
	
	@Override
	public Iterator<Integer> iterator() {
		
		return new Iterator<Integer>() {
			private int index = 0;
			@Override
			public boolean hasNext() {
				
				return index < pets.length;
			}

			@Override
			public Integer next() {

				return pets[index++];
			}

			@Override
			public void remove() {
				//Not implemented
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public int size() {
		
		return pets.length;
	}
	
	public static void main(String[] args) {
		CollectionSequence c = new CollectionSequence();
		InterfaceVsIterator.display(c);
		InterfaceVsIterator.display(c.iterator());
	}
}

class InterfaceVsIterator{
	public static void display(Iterator<Integer> it) {
		while(it.hasNext()) {
			Integer i = it.next();
			System.out.print(i + " ");
		}
		System.out.println();
	}
	public static void display(Collection<Integer> pets) {
		for(Integer i : pets) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
/**
1 2 3 4 5 6 7 8 
1 2 3 4 5 6 7 8 
*/
```

remove()方法是一个“可选操作”。这里不必实现它，如果你调用它，它会抛出异常。

从本例中，你可以看到，如果你实现Collection，就必须实现iterator()，并且只拿实现 iterator() 与继承AbstractCollection相比，花费的代价只有略微减少。但是，如果你的类已经继承了其他类，那么你就不能再继承AbstractCollection了。在这种情况下，要实现Collection，就必须实现该接口中的所有方法。此时，继承并提供创建迭代器的能力就会显得容易得多了。

```java
package holding;

import java.util.Iterator;

class PetSequence{
	protected Integer[] pets = {1,2,3,4,5,6,7,8};
}

public class NonCollectionSequence extends PetSequence {
	public Iterator<Integer> iterator(){
		return new Iterator<Integer>() {
			private int index = 0;
			@Override
			public boolean hasNext() {
				return index < pets.length;
			}

			@Override
			public Integer next() {

				return pets[index++];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	public static void main(String[] args) {
		NonCollectionSequence nc = new NonCollectionSequence();
		InterfaceVsIterator.display(nc.iterator());
	}
}
/**
1 2 3 4 5 6 7 8 
*/
```

生成Iterator是将队列与消费队列的方法连接在一起耦合度最小的方式，并且与实现Collection相比，它在序列类上所施加的约束也少得多。

## Foreach与迭代器

到目前为止，foreach语法主要用于数组，但是它也可以应用于任何Collection对象。

```java
package holding;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class ForEachCollections {

	public static void main(String[] args) {
			Collection<String> cs = new LinkedList<String>();
			Collections.addAll(cs, "Take the long way home".split(" "));
			for(String s : cs) {
				System.out.print("'" + s + "' ");
			}
	}
}
/**
'Take' 'the' 'long' 'way' 'home' 
*/
```

由于 cs 是一个Collection，所以这段代码展示了能够与foreach一起工作是所有Collection对象的特性。

之所以能够工作，是**因为Java SE5引入了新的被称为Iterable的接口，该接口包含了一个能够产生Iterator的iterator()方法，并且Iterable接口被foreach用来在序列中移动**。因此如果你创建了任何实现Iterable的类。都可以将它用于foreach语句中：

```java
package holding;

import java.util.Iterator;

public class IterableClass implements Iterable<String>{

	protected String[] words = ("And that is how we know the Earth to be banana-shaped.").split(" ");
	
	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			private int index = 0;
			@Override
			public boolean hasNext() {
				return index < words.length;
			}

			@Override
			public String next() {
				return words[index++];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	public static void main(String[] args) {
		for(String s : new IterableClass()) {
			System.out.print(s + " ");
		}
	}
}
/**
And that is how we know the Earth to be banana-shaped. 
*/
```

iterator()方法返回的是实现了Iterator\<String>的匿名内部类的实例，该匿名内部类可以遍历数组中的所有单词。在main()中，可以看到 IterableClass 确实可以用于foreach语句中。

在Java SE5中，大量的类都是 Iterable 类型，主要包括所有的Collection类（但是不包括各种Map）。例如，下面的代码可以显示所有的操作系统的环境变量：

```java
package holding;

import java.util.Map;

public class EnvironmentVariables {

	public static void main(String[] args) {
		for(Map.Entry entry: System.getenv().entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
}
```

System.getenv()返回一个Map，entrySet()产生一个由Map.Entry的元素构成的Set，并且这个Set是一个Iterable，因此它可以用于foreach循环。

foreach语句可以用于数组或其他任何Iterable，但是这并不意味着数组肯定也是一个Iterable，而任何自动包装也不会自动发生。

```java
package holding;

import java.util.Arrays;

public class ArrayIsNotIterable {

	static <T> void test(Iterable<T> ib) {
		for(T t : ib) {
			System.out.print(t + " ");
		}
	}
	public static void main(String[] args) {
		test(Arrays.asList(1,2,3));
		String[] strings = {"A","B","C"};
		//An array works in foreach,but it's not Iterable
		//!test(strings);
		//You must explicitly convert it to an Iterable
		test(Arrays.asList(strings));
	}
}
/**
1 2 3 A B C 
*/
```

尝试把数组当作一个Iterable参数传递会导致失败。这说明不存在任何从数组到Iterable的自动转换，你必须手工执行这种转换。

### 适配器方法惯用法

如果现有一个Iterable类，你想要添加一种或多种在foreach语句中使用这个类的方法，应该怎么做？例如，假设你希望可以选择以前的方向或是向后的方向迭代一个单词列表。如果直接继承这个类，并覆盖iterator()方法，你只能替换现有的方法，而不能实现选择。

一种解决方案是所谓适配器方法的惯用法。“适配器”部分来自于设计模式，因为你必须提供特定接口以满足foreach语句。当你有一个接口并需要另一个接口时，编写适配器就可以解决问题。这里，我希望在默认的前向迭代器的基础上，添加产生反向迭代器的能力，因此不能使用覆盖，**而是添加了一个能够产生Iterable对象的方法**，该对象可以用于foreach语句。正如你所见，这使得我们可以提供多种使用foreach的方式：

```java
package holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

class ReversibleArrayList<T> extends ArrayList<T>{
	public ReversibleArrayList(Collection<T> c) {
		super(c);
	}
	public Iterable<T> reversed(){
		
		return new Iterable<T>() {
			
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					int current = size() - 1;
					@Override
					public boolean hasNext() {
						return current > -1;
					}

					@Override
					public T next() {
						return get(current--);
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}

public class AdapterMethodIdiom {

	public static void main(String[] args) {
		
		ReversibleArrayList<String> ral =
				new ReversibleArrayList<String>(
						Arrays.asList("To be or not to be".split(" ")));
		for(String s : ral) {
			System.out.print(s + " ");
		}
		System.out.println();
		for(String s : ral.reversed()) {
			System.out.print(s + " ");
		}
	}
}
/**
To be or not to be 
be to not or be To 
*/
```

如果直接将 ral 对象置于foreach语句中，将得到默认的向前迭代器。但是如果在该对象上调用reversed()方法，就会产生不同的行为。

## 总结

Java提供了大量持有对象的方式：

1. 数组将数字与对象联系起来。它保存类型明确的对象，查询对象时，不需要对结果做类型转换。它可以是多维的，可以保存基本类型的数据。但是，数组一旦生成，其容量就不能改变。
2. Collection保存单一的元素，而Map保存相关的键值对。有了Java的泛型，你就可以指定容器中存放的对象类型，因此你就不会将错误类型的对象放置到容器中，并且在从容器中获取元素时，不必进行类型转换。各种Collection和各种Map都可以在你向其中添加更多的元素时，自动调整尺寸。**容器不能持有基本类型，但是自动包装机制会仔细地执行基本类型到容器中所持有的包装器类型之间的双向转换。**
3. 像数组一样，List也建立数字索引与对象的关联，因此，数组和List都是排好序的容器。List能够自动扩充容量。
4. 如果要进行大量的随机访问，就使用ArrayList；如果要经常从表中间插入或删除元素，则应该使用LinkedList。
5. 各种Queue以及栈的行为，由LinkedList提供支持。
6. Map是一种将对象（而非数字）与对象相关联的设计。HashMap设计用来快速访问；而TreeMap保持“键”始终处于排序状态，所以没有JHashMap快。LinkedHashMap保持元素插入的顺序，但是也通过散列提供了快速访问的能力。
7. Set不接受重复元素。HashSet提供最快的查询速度，而TreeSet保持元素处于排序状态。LinkedHashSet以插入顺序保存元素。
8. 新程序中不应该使用过时的Vector、Hashtable和Stack。

浏览一下Java容器的简图：（不包含抽象类和遗留构件）

![img](E:\github上项目\Java\Java\_image\0_13145197181F0f.gif)

可以看到，其实只有四种容器：Map、List、Set和Queue，它们各有两到三个实现版本。

- 常用的容器用黑色粗线框表示。
- 点线框表示接口
- 实现框表示普通的（具体的）类
- 带有空心箭头的点线表示一个特定的类实现了一个接口
- 实心箭头表示某个类可以生成箭头所指向类的对象

例如，任意的Collection可以生成Iterator，而List可以生成ListIterator（也能生成普通的Iterator，因此List继承自Collection）