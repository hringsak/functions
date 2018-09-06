package org.hringsak.functions;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;

/**
 * Methods that build predicates specifically those involving primitive <code>int</code> types.
 */
public final class DblPredicateUtils {

    private DblPredicateUtils() {
    }

    public static DoublePredicate dblPredicate(DoublePredicate predicate) {
        return predicate;
    }

    public static <U> DoublePredicate dblPredicate(BiPredicate<Double, ? super U> biPredicate, U value) {
        return d -> biPredicate.test(d, value);
    }

    public static <U> DoublePredicate inversedblPredicate(BiPredicate<? super U, Double> biPredicate, U value) {
        return d -> biPredicate.test(value, d);
    }

    public static DoublePredicate dblConstant(boolean b) {
        return d -> b;
    }

    public static DoublePredicate fromDblMapper(DoubleFunction<Boolean> function) {
        return function::apply;
    }

    public static DoublePredicate notDbl(DoublePredicate predicate) {
        return predicate.negate();
    }

    public static <R extends CharSequence> DoublePredicate isDblStrEmpty(DoubleFunction<? extends R> function) {
        return d -> { CharSequence seq = function.apply(d); return seq == null || seq.length() == 0; };
    }

    public static <R extends CharSequence> DoublePredicate isDblStrNotEmpty(DoubleFunction<? extends R> function) {
        return notDbl(isDblStrEmpty(function));
    }

    public static <R> DoublePredicate isDblEqual(R value, DoubleFunction<? extends R> function) {
        return d -> Objects.equals(value, function.apply(d));
    }

    public static <R> DoublePredicate isDblNotEqual(R value, DoubleFunction<? extends R> function) {
        return notDbl(isDblEqual(value, function));
    }

    public static <R> DoublePredicate dblContains(Collection<? extends R> collection, DoubleFunction<? extends R> function) {
        return d -> collection != null && collection.contains(function.apply(d));
    }

    public static <R> DoublePredicate inverseDblContains(DoubleFunction<? extends Collection<R>> collectionExtractor, R value) {
        return d -> {
            Collection<R> collection = collectionExtractor.apply(d);
            return collection != null && collection.contains(value);
        };
    }

    public static <R> DoublePredicate dblContainsKey(Map<R, ?> map, DoubleFunction<? extends R> function) {
        return d -> map != null && map.containsKey(function.apply(d));
    }

    public static <R> DoublePredicate dblContainsValue(Map<?, R> map, DoubleFunction<? extends R> function) {
        return d -> map != null && map.containsValue(function.apply(d));
    }

    public static DoublePredicate dblContainsChar(DoubleFunction<? extends CharSequence> function, int searchChar) {
        return d -> StringUtils.contains(function.apply(d), searchChar);
    }

    public static DoublePredicate dblContainsSequence(DoubleFunction<? extends CharSequence> function, CharSequence searchSeq) {
        return d -> StringUtils.contains(function.apply(d), searchSeq);
    }

    public static DoublePredicate dblContainsIgnoreCase(DoubleFunction<? extends CharSequence> function, CharSequence searchSeq) {
        return d -> StringUtils.containsIgnoreCase(function.apply(d), searchSeq);
    }

    public static DoublePredicate dblIsAlpha(DoubleFunction<? extends CharSequence> function) {
        return d -> StringUtils.isAlpha(function.apply(d));
    }

    public static DoublePredicate dblIsAlphanumeric(DoubleFunction<? extends CharSequence> function) {
        return d -> StringUtils.isAlphanumeric(function.apply(d));
    }

    public static DoublePredicate dblIsNumeric(DoubleFunction<? extends CharSequence> function) {
        return d -> StringUtils.isNumeric(function.apply(d));
    }

    public static DoublePredicate dblStartsWith(DoubleFunction<? extends CharSequence> function, CharSequence prefix) {
        return d -> StringUtils.startsWith(function.apply(d), prefix);
    }

    public static DoublePredicate dblStartsWithIgnoreCase(DoubleFunction<? extends CharSequence> function, CharSequence prefix) {
        return d -> StringUtils.startsWithIgnoreCase(function.apply(d), prefix);
    }

    public static DoublePredicate dblEndsWith(DoubleFunction<? extends CharSequence> function, CharSequence suffix) {
        return d -> StringUtils.endsWith(function.apply(d), suffix);
    }

    public static DoublePredicate dblEndsWithIgnoreCase(DoubleFunction<? extends CharSequence> function, CharSequence suffix) {
        return d -> StringUtils.endsWithIgnoreCase(function.apply(d), suffix);
    }

    public static <R> DoublePredicate isDblNull(DoubleFunction<? extends R> function) {
        return d -> Objects.isNull(function.apply(d));
    }

    public static <R> DoublePredicate isNotDblNull(DoubleFunction<? extends R> function) {
        return notDbl(isDblNull(function));
    }

    public static <R extends Comparable<R>> DoublePredicate dblGt(R compareTo, DoubleFunction<? extends R> function) {
        return d -> Objects.compare(compareTo, function.apply(d), nullsLast(naturalOrder())) > 0;
    }

    public static <R extends Comparable<R>> DoublePredicate dblGte(R compareTo, DoubleFunction<? extends R> function) {
        return d -> Objects.compare(compareTo, function.apply(d), nullsLast(naturalOrder())) >= 0;
    }

    public static <R extends Comparable<R>> DoublePredicate dblLt(R compareTo, DoubleFunction<? extends R> function) {
        return d -> Objects.compare(compareTo, function.apply(d), nullsLast(naturalOrder())) < 0;
    }

    public static <R extends Comparable<R>> DoublePredicate dblLte(R compareTo, DoubleFunction<? extends R> function) {
        return d -> Objects.compare(compareTo, function.apply(d), nullsLast(naturalOrder())) <= 0;
    }

    public static <R> DoublePredicate isDblCollEmpty(DoubleFunction<? extends Collection<R>> function) {
        return d -> CollectionUtils.isEmpty(function.apply(d));
    }

    public static <R> DoublePredicate isDblCollNotEmpty(DoubleFunction<? extends Collection<R>> function) {
        return notDbl(isDblCollEmpty(function));
    }
}