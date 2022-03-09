package com.hologramsciences;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Algorithms {

    /**
     * Compute the cartesian product of a list of lists of any type T the result is a list of lists
     * of type T, where each element comes each successive element of the each list.
     * <p>
     * https://en.wikipedia.org/wiki/Cartesian_product
     * <p>
     * For this problem order matters.
     * <p>
     * Example:
     * <p>
     * listOfLists = Arrays.asList( Arrays.asList("A", "B"), Arrays.asList("K", "L") )
     * <p>
     * returns:
     * <p>
     * Arrays.asList( Arrays.asList("A", "K"), Arrays.asList("A", "L"), Arrays.asList("B", "K"),
     * Arrays.asList("B", "L") )
     */
    public static final <T> List<List<T>> cartesianProductForLists(List<List<T>> listOfLists) {
        List<List<T>> finalResult = new ArrayList<>();
        cartesianHelper(finalResult, new ArrayList<>(), listOfLists);

        return finalResult;
    }

    private static <T> void cartesianHelper(List<List<T>> result, List<T> currentTuple,
            List<List<T>> existingElements) {
        existingElements.get(0).forEach(element -> {
            List<T> newElements = new ArrayList<>(currentTuple);
            newElements.add(element);

            if (existingElements.size() == 1) {
                result.add(newElements);
            } else {
                List<List<T>> newGroupElements = IntStream.range(1, existingElements.size())
                        .mapToObj(existingElements::get)
                        .collect(Collectors.toList());

                cartesianHelper(result, newElements, newGroupElements);
            }
        });
    }

    /**
     * In the United States there are six coins: 1¢ 5¢ 10¢ 25¢ 50¢ 100¢ Assuming you have an
     * unlimited supply of each coin, implement a method which returns the number of distinct ways
     * to make totalCents
     */
    public static long countNumWaysMakeChange(final int totalCents) {
        final int[] COINS = {1, 5, 10, 25, 50, 100};

        long[] waysToMakeChange = new long[totalCents + 1];
        waysToMakeChange[0] = 1;

        for (int coin : COINS) {
            for (int i = 0; i < waysToMakeChange.length; i++) {
                if (coin <= i) {
                    waysToMakeChange[i] += waysToMakeChange[i - coin];
                }
            }
        }

        return waysToMakeChange[totalCents];
    }
}
