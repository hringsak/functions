package org.hringsak.functions;

import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

/**
 * Methods that build functions to map a target element into another result. This class deals specifically with mapper
 * functions involving primitive <code>int</code> types.
 */
public final class IntMapperUtils {

    private IntMapperUtils() {
    }

    /**
     * Simply casts a method reference, which takes a single parameter of type <code>int</code> and returns &lt;R&gt;,
     * to an <code>IntFunction</code>. Everything said about the {@link MapperUtils#mapper(Function)} method applies
     * here. The difference is that instead of an element of type &lt;T&gt; being streamed through, it would be a
     * primitive <code>int</code> instead. This method might be useful in a situation where you have an
     * <code>IntStream</code>, and the <code>IntStream.mapToObj(IntFunction mapper)</code> method is called to convert
     * the primitive to some generic type of object, converting the <code>IntStream</code> to an object stream.
     * <p>
     * Note that the difference between this method and {@link #toIntMapper(ToIntFunction)} is that the
     * <code>IntFunction</code> built from this method takes an <code>int</code> and returns a generic type, where the
     * <code>ToIntFunction</code> built from {@link #toIntMapper(ToIntFunction)} takes a generic type and returns an
     * <code>int</code>.
     *
     * @param function A method reference to be cast to an IntFunction.
     * @param <R>      The type of the result of the IntFunction built by this method.
     * @return A method reference cast to an IntFunction.
     */
    @SuppressWarnings({"unused", "WeakerAccess"})
    public static <R> IntFunction<R> intMapper(IntFunction<R> function) {
        return function;
    }

    /**
     * Builds an <code>IntFunction</code> from a passed <code>BiFunction</code>. Everything said about the
     * {@link MapperUtils#mapper(BiFunction, Object)} method applies here. The difference is that instead of an element
     * of type &lt;T&gt; being streamed through, it would be a primitive <code>int</code> instead. This method might be
     * useful in a situation where you have an <code>IntStream</code>, and the
     * <code>IntStream.mapToObj(IntFunction mapper)</code> method is called to convert the primitive to some generic
     * type of object, converting the <code>IntStream</code> to an object stream.
     * <p>
     * Note that the difference between this method and {@link #toIntMapper(ToIntBiFunction, Object)} is that the
     * <code>IntFunction</code> built from this method takes an <code>int</code> and returns a generic type, where the
     * <code>ToIntFunction</code> built from {@link #toIntMapper(ToIntBiFunction, Object)} takes a generic type and
     * returns an <code>int</code>.
     *
     * @param biFunction A method reference which is a BiFunction, taking two parameters - the first of type int, and
     *                   the second of type &lt;U&gt;, which can be any type. The method reference will be converted by
     *                   this method to an IntFunction, taking a single parameter of type int. Behind the scenes, this
     *                   BiFunction will be called, passing the constant value to each invocation as the second
     *                   parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biFunction as the
     *                   second parameter to it, and will have the same value for each of them.
     * @param <U>        The type of the constant value to be passed as the second parameter to each invocation of
     *                   biFunction.
     * @param <R>        The type of the result of the IntFunction built by this method.
     * @return An IntFunction taking a single parameter of type int, and returning a result of type &lt;R&gt;.
     */
    public static <U, R> IntFunction<R> intMapper(BiFunction<Integer, ? super U, ? extends R> biFunction, U value) {
        return i -> biFunction.apply(i, value);
    }

    /**
     * Builds an <code>IntFunction</code> from a passed <code>BiFunction</code>. Everything said about the
     * {@link MapperUtils#inverseMapper(BiFunction, Object)} method applies here. The difference is that instead of an
     * element of type &lt;T&gt; being streamed through, it would be a primitive <code>int</code> instead. This method
     * might be useful in a situation where you have an <code>IntStream</code>, and the
     * <code>IntStream.mapToObj(IntFunction mapper)</code> method is called to convert the primitive to some generic
     * type of object, converting the <code>IntStream</code> to an object stream.
     * <p>
     * Note that the difference between this method and {@link #inverseToIntMapper(ToIntBiFunction, Object)} is that the
     * <code>IntFunction</code> built from this method takes an <code>int</code> and returns a generic type, where the
     * <code>ToIntFunction</code> built from {@link #inverseToIntMapper(ToIntBiFunction, Object)} takes a generic type
     * and returns an <code>int</code>.
     *
     * @param biFunction A method reference which is a BiFunction, taking two parameters - the first of type &lt;U&gt;
     *                   which can be any type, and the second of type int. The method reference will be converted by
     *                   this method to an IntFunction, taking a single parameter of type int. Behind the scenes, this
     *                   biFunction will be called, passing the constant value to each invocation as the first
     *                   parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biFunction as the
     *                   first parameter to it, and will have the same value for each of them.
     * @param <U>        The type of the constant value to be passed as the first parameter to each invocation of
     *                   biFunction.
     * @param <R>        The type of the result of the IntFunction built by this method.
     * @return An IntFunction taking a single parameter of type int, and returning a result of type &lt;R&gt;.
     */
    public static <U, R> IntFunction<R> inverseIntMapper(BiFunction<? super U, Integer, ? extends R> biFunction, U value) {
        return i -> biFunction.apply(value, i);
    }

    /**
     * Simply casts a method reference, which takes a single parameter of type &lt;T&gt; and returns <code>int</code>,
     * to a <code>ToIntFunction</code>. Everything said about the {@link MapperUtils#mapper(Function)} method applies
     * here. The difference is that instead of returning a result of a generic object type, it returns a primitive
     * <code>int</code> instead. This method might be useful in a situation where you have a <code>Stream</code> of a
     * generic object type, and the <code>mapToInt(ToIntFunction mapper)</code> method is called to convert the object
     * to a primitive <code>int</code>, converting the stream to an <code>IntStream</code>.
     * <p>
     * Note that the difference between this method and {@link #intMapper(IntFunction)} is that the
     * <code>ToIntFunction</code> built from this method takes a generic type and returns an <code>int</code>, where the
     * <code>IntFunction</code> built from {@link #intMapper(IntFunction)} takes an <code>int</code> and returns a
     * generic type.
     *
     * @param function A method reference to be cast to a ToIntFunction.
     * @param <T>      The type of the single parameter to the ToIntFunction.
     * @return A method reference cast to a ToIntFunction.
     */
    @SuppressWarnings({"unused", "WeakerAccess"})
    public static <T> ToIntFunction<T> toIntMapper(ToIntFunction<T> function) {
        return function;
    }

    /**
     * Builds a mapper <code>ToIntFunction</code> that, if the target element is <code>null</code>, or the result of the
     * <code>Function</code> call on the target element is <code>null</code>, then the passed default value is returned.
     * Everything said about the {@link MapperUtils#mapperDefault(Function, Object)} method applies here. The difference
     * is that instead of returning a result of a generic object type, it returns a primitive <code>int</code> instead.
     * This method might be useful in a situation where you have a <code>Stream</code> of a generic object type, and the
     * <code>mapToInt(ToIntFunction mapper)</code> method is called to convert the object to a primitive
     * <code>int</code>, converting the stream to an <code>IntStream</code>.
     *
     * @param function     A method reference which takes a single parameter of type &lt;T&gt;, and returns a value of
     *                     type int.
     * @param defaultValue A default value of type int, to be returned in case the target element, or the result of
     *                     the ToIntFunction call on the target element is null.
     * @param <T>          The type of the target element on which the mapper ToIntFunction is to be called.
     * @return A ToIntFunction taking a single parameter of type &lt;T&gt;, and returning a result of type int.
     */
    public static <T> ToIntFunction<T> toIntMapperDefault(ToIntFunction<? super T> function, int defaultValue) {
        return t -> t == null ? defaultValue : function.applyAsInt(t);
    }

    /**
     * Builds a <code>ToIntFunction</code> from a passed <code>ToIntBiFunction</code>. Everything said about the
     * {@link MapperUtils#mapper(BiFunction, Object)} method applies here. The difference is that instead of returning a
     * result of a generic object type, it returns a primitive <code>int</code> instead. This method might be useful in
     * a situation where you have a <code>Stream</code> of a generic object type, and the
     * <code>mapToInt(ToIntFunction mapper)</code> method is called to convert the object to a primitive
     * <code>int</code>, converting the stream to an <code>IntStream</code>.
     * <p>
     * Note that the difference between this method and {@link #intMapper(BiFunction, Object)} is that the
     * <code>ToIntFunction</code> built from this method takes a generic type and returns an <code>int</code>,
     * where the <code>IntFunction</code> built from {@link #intMapper(BiFunction, Object)} takes an <code>int</code>
     * and returns a generic type.
     *
     * @param biFunction A method reference which is a ToIntBiFunction, taking two parameters - the first of type
     *                   &lt;T&gt;, and the second of type &lt;U&gt;, both of which can be any type. The method
     *                   reference will be converted by this method to a ToIntFunction, taking a single parameter of
     *                   type &lt;T&gt;. Behind the scenes, this ToIntBiFunction will be called, passing the constant
     *                   value to each invocation as the second parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biFunction as the
     *                   second parameter to it, and will have the same value for each of them.
     * @param <T>        The type of the target element on which the mapper ToIntFunction is to be called.
     * @param <U>        The type of the constant value to be passed as the second parameter to each invocation of
     *                   biFunction.
     * @return A ToIntFunction taking a single parameter of type &lt;T&gt;, and returning a result of type int.
     */
    public static <T, U> ToIntFunction<T> toIntMapper(ToIntBiFunction<? super T, ? super U> biFunction, U value) {
        return t -> biFunction.applyAsInt(t, value);
    }

    /**
     * Builds a <code>ToIntFunction</code> from a passed <code>ToIntBiFunction</code>. Everything said about the
     * {@link MapperUtils#inverseMapper(BiFunction, Object)} method applies here. The difference is that instead of
     * returning a result of a generic object type, it returns a primitive <code>int</code> instead. This method might
     * be useful in a situation where you have a <code>Stream</code> of a generic object type, and the
     * <code>mapToInt(ToIntFunction mapper)</code> method is called to convert the object to a primitive
     * <code>int</code>, converting the stream to an <code>IntStream</code>.
     * <p>
     * Note that the difference between this method and {@link #inverseIntMapper(BiFunction, Object)} is that the
     * <code>ToIntFunction</code> built from this method takes a generic type and returns an <code>int</code>, where the
     * <code>IntFunction</code> built from {@link #inverseIntMapper(BiFunction, Object)} takes an <code>int</code> and
     * returns a generic type.
     *
     * @param biFunction A method reference which is a ToIntBiFunction, taking two parameters - the first of type
     *                   &lt;U&gt; which can be any type, and the second of type int. The method reference will be
     *                   converted by this method to a ToIntFunction, taking a single parameter of type &lt;T&gt;.
     *                   Behind the scenes, this biFunction will be called, passing the constant value to each
     *                   invocation as the first parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biFunction as the
     *                   first parameter to it, and will have the same value for each of them.
     * @param <T>        The type of the target element on which the mapper ToIntFunction is to be called.
     * @param <U>        The type of the constant value to be passed as the first parameter to each invocation of
     *                   biFunction.
     * @return A ToIntFunction taking a single parameter of type &lt;T&gt;, and returning a result of type int.
     */
    public static <T, U> ToIntFunction<T> inverseToIntMapper(ToIntBiFunction<? super U, ? super T> biFunction, U value) {
        return t -> biFunction.applyAsInt(value, t);
    }

    public static IntFunction<Pair<Integer, Integer>> pairIntWithIndex() {
        return pairIntWithIndex(Integer::valueOf);
    }

    public static <R> IntFunction<Pair<R, Integer>> pairIntWithIndex(IntFunction<? extends R> function) {
        AtomicInteger idx = new AtomicInteger();
        return t -> Pair.of(function.apply(t), idx.getAndIncrement());
    }
}
