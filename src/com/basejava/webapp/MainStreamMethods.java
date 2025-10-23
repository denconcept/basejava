package com.basejava.webapp;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class MainStreamMethods {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 3, 2, 3};
        System.out.println(minValue(nums));

        List<Integer> integers = List.of(1, 2, 3, 3, 2, 3);
        System.out.println(oddOrEven(integers));
    }

    public static int minValue(int[] values) {
        return stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().collect(Collectors.teeing(
                Collectors.summingInt(Integer::intValue),
                Collectors.toList(),
                (sum, list) -> list.stream()
                        .filter(i -> (sum % 2 == 0) == (i % 2 != 0))
                        .toList()
        ));
    }
}
