package com.example.demo.features;

import java.util.List;

public class StreamsAndExpressions {
    List<String> someList = List.of("A", "B", "C", "", "D", "E", "F", "");

    public void streamExample() {
        List<String> modified = this.someList.stream()
                .filter((value) -> !value.isEmpty())
                .map(String::toLowerCase) // .map((value) -> value.toLowerCase())
                .sorted()
                .toList(); // .collect(Collectors.toList());
        // modified => ("a", "b", "c", "d", "e", "f")
    }

    class SomeVeryLongClassName {}
    public void varVariable() {
        // Old
        SomeVeryLongClassName val = new SomeVeryLongClassName();

        // New
        var val2 = new SomeVeryLongClassName();
    }

    public <T> void patternMatching(T input) {
        // Old style
        if (input instanceof String) {
            String string = (String) input;
            System.out.println(string);
        }
        // New style
        if (input instanceof String string) {
            // string already defined here
            System.out.println(string);
        }

        // 17 Preview
//        switch (input) {
//            case null -> System.out.println("Null");
//            case String string -> System.out.println("String: " + string);
//            case Integer integer -> System.out.println("Integer: " + integer);
//            case Double doublePrecision -> {
//                System.out.println("Double: " + doublePrecision);
//            }
//            default -> System.out.println("Default");
//        }
//
//        var re = switch (input) {
//            case null -> 42;
//            case String value -> Integer.parseInt(value);
//            default -> 0;
//        };
    }
}
