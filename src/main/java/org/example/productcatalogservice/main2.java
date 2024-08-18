package org.example.productcatalogservice;

import java.util.List;

public class main2 {
    public static void main(String[] args) {

//   Why is there no class literal for wildcard parameterized types?
//        Because a wildcard parameterized type has no exact runtime type representation.

//        Wildcard parameterized types lose their type arguments when they are translated to byte code in a process called type erasure .
//        As a side effect of type erasure, all  instantiations of a generic type share the same runtime representation, namely that of the corresponding raw type .
//        In other words, parameterized types do not have type representation of their own.
//        Consequently, there is no point to forming class literals such as List<?>.class , List<? extends Number>.class and List<Long>.class , since no such Class objects exist.
//        Only the raw type List has a Class object that represents its runtime type. It is referred to as List.class .
        List<Integer> l1 = List.of(1,2);
        List<String> l2 = List.of("Lavs");
        System.out.println(l1.getClass().getName()); //Output : java.util.ImmutableCollections$List12
        System.out.println(l2.getClass().getName()); //Output : java.util.ImmutableCollections$List12
    }
}
