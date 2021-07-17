package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MainStream {

    public static void main(String[] args) {
        System.out.println(oddOrEven(Arrays.asList(8, 9)));
        System.out.println(oddOrEven(Arrays.asList(8, 9, 1)));

        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3, 1}));
        System.out.println(minValue(new int[]{8, 9}));
    }

    public static int minValue(int[] values) {
        return IntStream
                .of(values)
                .sorted()
                .distinct()
                .reduce(0, (x, y) -> x * 10 + y);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers
                .stream()
                .mapToInt(i -> i)
                .sum();
        return integers
                .stream()
                .filter(x -> x % 2 != sum % 2)
                .collect(Collectors.toList());
    }
}
