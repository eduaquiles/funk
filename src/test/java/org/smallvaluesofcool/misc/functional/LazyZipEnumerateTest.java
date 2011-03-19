package org.smallvaluesofcool.misc.functional;

import org.junit.Test;
import org.smallvaluesofcool.misc.datastructures.TwoTuple;
import org.smallvaluesofcool.misc.functional.functors.DoFunction;
import org.smallvaluesofcool.misc.functional.functors.MapFunction;
import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.smallvaluesofcool.misc.IterableUtils.materialize;
import static org.smallvaluesofcool.misc.IterableUtils.toList;
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.Literals.twoTuple;
import static org.smallvaluesofcool.misc.matchers.Matchers.hasOnlyItemsInOrder;

public class LazyZipEnumerateTest {
    @Test
    public void shouldEnumerateSequence() {
        // Given
        Iterable<String> iterable = listWith("A", "B", "C");
        Collection<TwoTuple<Integer, String>> expected = listWith(twoTuple(0, "A"), twoTuple(1, "B"), twoTuple(2, "C"));

        // When
        Iterable<TwoTuple<Integer, String>> actual = Lazy.enumerate(iterable);

        // Then
        assertThat(toList(actual), hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipIterables() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C");
        Iterable<Integer> iterable2 = listWith(1, 2, 3);

        Collection<TwoTuple<String, Integer>> expected = listWith(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Collection<TwoTuple<String, Integer>> actual = toList(Lazy.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipIterablesWithLongerFirstIterable() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C", "D");
        Iterable<Integer> iterable2 = listWith(1, 2, 3);
        Collection<TwoTuple<String, Integer>> expected = listWith(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Collection<TwoTuple<String, Integer>> actual = toList(Lazy.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipIterablesWithShorterFirstIterable() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C");
        Iterable<Integer> iterable2 = listWith(1, 2, 3, 4);
        Collection<TwoTuple<String, Integer>> expected = listWith(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Iterable<TwoTuple<String, Integer>> actual = Lazy.zip(iterable1, iterable2);

        // Then
        assertThat(toList(actual), hasOnlyItemsInOrder(expected));
    }
}