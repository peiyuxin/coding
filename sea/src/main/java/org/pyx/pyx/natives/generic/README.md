#泛型
###泛型下界 ? extends Number
###泛型上界 ? super Integer

###PECS原则
   如果要从集合中读取类型T的数据，并且不能写入，可以使用 ? extends 通配符；(Producer Extends) 
   如果要从集合中写入类型T的数据，并且不需要读取，可以使用 ? super 通配符；(Consumer Super) 
   如果既要存又要取，那么就不要使用任何通配符。
成员变量 类变量