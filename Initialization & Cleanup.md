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