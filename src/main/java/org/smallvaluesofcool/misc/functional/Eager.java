package org.smallvaluesofcool.misc.functional;

import org.smallvaluesofcool.misc.datastructures.TwoTuple;
import org.smallvaluesofcool.misc.functional.functors.DoFunction;
import org.smallvaluesofcool.misc.functional.functors.MapFunction;
import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;
import org.smallvaluesofcool.misc.functional.functors.ReduceFunction;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.smallvaluesofcool.misc.IterableUtils.materialize;
import static org.smallvaluesofcool.misc.IteratorUtils.toIterable;

public class Eager {
    public static <S, T> T reduce(Iterable<? extends S> iterable, T initialValue, ReduceFunction<S, T> function) {
        T accumulator = initialValue;
        for (S element : iterable) {
            accumulator = function.accumulate(accumulator, element);
        }
        return accumulator;
    }

    public static <T> T reduce(Iterable<? extends T> iterable, ReduceFunction<T, T> function) {
        final Iterator<? extends T> iterator = iterable.iterator();
        final T firstElement = iterator.next();
        final Iterable<? extends T> restOfElements = toIterable(iterator);
        return reduce(restOfElements, firstElement, function);
    }

    public static Integer sum(Iterable<Integer> iterable) {
        return reduce(iterable, integerAdditionAccumulator());
    }

    public static Long sum(Iterable<Long> iterable) {
        return reduce(iterable, longAdditionAccumulator());
    }

    public static Double sum(Iterable<Double> iterable) {
        return reduce(iterable, doubleAdditionAccumulator());
    }

    public static Float sum(Iterable<Float> iterable) {
        return reduce(iterable, floatAdditionAccumulator());
    }

    public static ReduceFunction<Integer, Integer> integerAdditionAccumulator() {
        return new ReduceFunction<Integer, Integer>() {
            public Integer accumulate(Integer accumulator, Integer element) {
                return accumulator + element;
            }
        };
    }

    public static ReduceFunction<Long, Long> longAdditionAccumulator() {
        return new ReduceFunction<Long, Long>() {
            public Long accumulate(Long accumulator, Long element) {
                return accumulator + element;
            }
        };
    }

    private static ReduceFunction<Double, Double> doubleAdditionAccumulator() {
        return new ReduceFunction<Double, Double>() {
            public Double accumulate(Double accumulator, Double element) {
                return accumulator + element;
            }
        };
    }

    private static ReduceFunction<Float, Float> floatAdditionAccumulator() {
        return new ReduceFunction<Float, Float>() {
            public Float accumulate(Float accumulator, Float element) {
                return accumulator + element;
            }
        };
    }

    public static <T> Boolean any(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        for (T item : iterable) {
            if (predicate.matches(item)) {
                return true;
            }
        }
        return false;
    }

    public static <T> Boolean all(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        for (T item : iterable) {
            if (!predicate.matches(item)) {
                return false;
            }
        }
        return true;
    }

    public static <T> Boolean none(Iterable<? extends T> items, PredicateFunction<T> predicate) {
        return !any(items, predicate);
    }

    public static <T extends Comparable<T>> T max(Iterable<? extends T> iterable) {
        T max = null;
        for (T item : iterable) {
            if (max == null || (item != null && item.compareTo(max) > 0)) {
                max = item;
            }
        }
        return max;
    }

    public static <T extends Comparable<T>> T min(Iterable<? extends T> iterable) {
        T min = null;
        for (T item : iterable) {
            if (min == null || (item != null && item.compareTo(min) < 0)) {
                min = item;
            }
        }
        return min;
    }

    public static <S, T> Collection<T> map(Iterable<? extends S> iterable, MapFunction<S, T> function) {
        return materialize(Lazy.map(iterable, function));
    }

    public static <S, T> Collection<TwoTuple<S, T>> zip(Iterable<? extends S> iterable1, Iterable<? extends T> iterable2) {
        return materialize(Lazy.zip(iterable1, iterable2));
    }

    public static <T> Collection<TwoTuple<Integer, T>> enumerate(Iterable<? extends T> iterable) {
        return materialize(Lazy.enumerate(iterable));
    }

    public static <T> void each(Iterable<? extends T> targets, DoFunction<T> function) {
        materialize(Lazy.each(targets, function));
    }
}
