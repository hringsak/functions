package org.hringsak.functions;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Methods that build consumers useful in many different situations, particularly in Java streams.
 */
public final class ConsumerUtils {

    private ConsumerUtils() {
    }

    /**
     * Simply casts a method reference, which takes a single parameter of type &lt;T&gt; and returns void, to a <code>
     * Consumer</code>. This could be useful in a situation where methods of the <code>Consumer</code> interface are to
     * be called on a method reference. In the following example, assume that <code>methodOne()</code> and <code>
     * methodTwo()</code> are methods in the current class, and both take a single parameter of type <code>Widget</code>:
     * <pre>
     *     Collection&lt;Widget&gt; widgets = ...
     *     widgets.forEach(ConsumerUtils.consumer(this::methodOne)
     *             .andThen(this::methodTwo));
     * </pre>
     * The <code>Consumer.andThen()</code> method can only be called on the method reference because of the cast.
     *
     * @param consumer A method reference to be cast to a Consumer.
     * @param <T>      The type of the single parameter to the Consumer.
     * @return A method reference cast to a Consumer.
     */
    @SuppressWarnings({"unused", "WeakerAccess"})
    public static <T> Consumer<T> consumer(Consumer<T> consumer) {
        return consumer;
    }

    /**
     * Builds a consumer from a passed <code>BiConsumer</code>, which can be very useful in the common situation where
     * you are streaming through a collection of elements, and have a method to call that takes two parameters - the
     * first one being the element on which you are streaming, and the second being some constant value that will be
     * passed to all invocations. This would typically be called from within the <code>Collection.forEach(...)</code>
     * or <code>Stream.forEach(...)</code> method. Another example of a method that requires a <code>Consumer</code> is
     * the <code>Optional.ifPresent(...)</code> method. The following is a contrived example, but it illustrates the use
     * of the method. Assume we have a collection of persistent entities that we have manipulated somehow, and now they
     * need to be saved to a database. Also assume the <code>WidgetRepository.saveWidget()</code> method takes a <code>
     * Widget</code> as the first parameter, and a <code>String</code> projectId as a second parameter:
     * <pre>
     *     Collection&lt;Widget&gt; widgets = ...
     *     String projectId = ...
     *     widgets.forEach(ConsumerUtils.consumer(widgetRepository::saveWidget, projectId));
     * </pre>
     * In this example, we have each widget to be saved being passed to the save method, along with a projectId, which
     * remains constant for every call.
     * <p>
     * One note about using the Java <code>Consumer</code> interface, as it says in the Javadoc documentation for it,
     * "Unlike most other functional interfaces, Consumer is expected to operate via side-effects."
     *
     * @param biConsumer A method reference which is a BiConsumer, taking two parameters - the first of type &lt;T&gt;,
     *                   and the second of type &lt;U&gt;, either of which can be any type. The method reference will be
     *                   converted by this method to a Consumer, taking a single parameter of type &lt;T&gt;. Behind the
     *                   scenes, this BiConsumer will be called, passing the constant value to each invocation as the
     *                   second parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biConsumer as the
     *                   second parameter to it, and will have the same value for each of them.
     * @param <T>        The target type of the first parameter to the passed biConsumer.
     * @param <U>        The type of the constant value to be passed as the second parameter to each invocation of
     *                   biConsumer.
     * @return A Consumer taking a single parameter of type &lt;T&gt;.
     */
    public static <T, U> Consumer<T> consumer(BiConsumer<? super T, ? super U> biConsumer, U value) {
        return t -> biConsumer.accept(t, value);
    }

    /**
     * As in the {@link #consumer(BiConsumer, Object)} method, builds a consumer from a passed <code>BiConsumer</code>,
     * which can be very useful in the common situation where you are streaming through a collection elements, and have
     * a method to call that takes two parameters. In the <code>BiConsumer</code> passed to this method, the parameters
     * are basically the same as in {@link #consumer(BiConsumer, Object)}, but in the inverse order. Here, the first
     * parameter is a constant value that will be passed to all invocations of the method, and the second parameter is
     * the element on which you are streaming. This would typically be called from within the <code>Collection.forEach(...)
     * </code> or <code>Stream.forEach(...)</code> method. Another example of a method that requires a <code>Consumer</code>
     * is the <code>Optional.ifPresent(...)</code> method. The following is a contrived example, but it illustrates the
     * use of the method. Assume we have a collection of persistent entities that we have manipulated somehow, and now
     * they need to be saved to a database. Also assume the <code>WidgetRepository.saveWidget()</code> method takes a
     * <code>String</code> projectId as the first parameter, and a <code>Widget</code> as the second parameter:
     * <pre>
     *     Collection&lt;Widget&gt; widgets = ...
     *     String projectId = ...
     *     widgets.forEach(ConsumerUtils.inverseConsumer(widgetRepository::saveWidget, projectId));
     * </pre>
     * This example looks almost exactly the same as the one in {@link #consumer(BiConsumer, Object)}, but the difference
     * is that the order of the parameters in the passed <code>BiConsumer</code> method reference are reversed. So the
     * parameters to the widgetRepository.saveWidget(...) method would be <code>projectId</code>, and then the <code>
     * widget</code> as the second parameter.
     * <p>
     * One note about using the Java <code>Consumer</code> interface, as it says in the Javadoc documentation for it,
     * "Unlike most other functional interfaces, Consumer is expected to operate via side-effects."
     *
     * @param biConsumer A method reference which is a BiConsumer, taking two parameters - the first of type &lt;U&gt;,
     *                   and the second of type &lt;T&gt;, either of which can be any type. The method reference will be
     *                   converted by this method to a Consumer, taking a single parameter of type &lt;T&gt;. Behind the
     *                   scenes, this BiConsumer will be called, passing the constant value to each invocation as the
     *                   first parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biConsumer as the
     *                   first parameter to it, and will have the same value for each of them.
     * @param <T>        The target type of the second parameter to the passed biConsumer.
     * @param <U>        The type of the constant value to be passed as the first parameter to each invocation of
     *                   biConsumer.
     * @return A Consumer taking a single parameter of type &lt;T&gt;.
     */
    public static <T, U> Consumer<T> inverseConsumer(BiConsumer<? super U, ? super T> biConsumer, U value) {
        return t -> biConsumer.accept(value, t);
    }

    /**
     * Simply casts a method reference, which takes a single parameter of type <code>double</code> and returns void, to
     * a <code>DoubleConsumer</code>. Everything said about the {@link #consumer(Consumer)} method applies here. The
     * difference is that instead of an element of type &lt;T&gt; being streamed through, it would be a primitive <code>
     * double</code> instead. It may be harder to think of a situation where this overload would be useful, but
     * this method is included for sake of completeness.
     *
     * @param consumer A method reference to be cast to a DoubleConsumer.
     * @return A method reference cast to a DoubleConsumer.
     */
    @SuppressWarnings("unused")
    public static DoubleConsumer doubleConsumer(DoubleConsumer consumer) {
        return consumer;
    }

    /**
     * Builds a <code>DoubleConsumer</code> from a passed <code>BiConsumer</code>. Everything said about the
     * {@link #consumer(BiConsumer, Object)} method applies here. The difference is that instead of an element of type
     * &lt;T&gt; being streamed through, it would be a primitive <code>double</code> instead. It may be harder to think
     * of a situation where this overload would be useful, but this method is included for sake of completeness.
     * <p>
     * One note about using the Java <code>DoubleConsumer</code> interface, as it says in the Javadoc documentation for
     * it, "Unlike most other functional interfaces, DoubleConsumer is expected to operate via side-effects."
     *
     * @param biConsumer A method reference which is a BiConsumer, taking two parameters - the first of type double, and
     *                   the second of type &lt;U&gt;, which can be any type. The method reference will be converted by
     *                   this method to a DoubleConsumer, taking a single parameter of type double. Behind the scenes,
     *                   this BiConsumer will be called, passing the constant value to each invocation as the second
     *                   parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biConsumer as the
     *                   second parameter to it, and will have the same value for each of them.
     * @param <U>        The type of the constant value to be passed as the second parameter to each invocation of
     *                   biConsumer.
     * @return A DoubleConsumer taking a single parameter of type double.
     */
    public static <U> DoubleConsumer doubleConsumer(BiConsumer<Double, ? super U> biConsumer, U value) {
        return t -> biConsumer.accept(t, value);
    }

    /**
     * Builds a <code>DoubleConsumer</code> from a passed <code>BiConsumer</code>. Everything said about the
     * {@link #inverseConsumer(BiConsumer, Object)} method applies here. The difference is that instead of an element of
     * type &lt;T&gt; being streamed through, it would be a primitive <code>double</code> instead. It may be harder to
     * think of a situation where this overload would be useful, but this method is included for sake of completeness.
     * <p>
     * One note about using the Java <code>DoubleConsumer</code> interface, as it says in the Javadoc documentation for
     * it, "Unlike most other functional interfaces, DoubleConsumer is expected to operate via side-effects."
     *
     * @param biConsumer A method reference which is a BiConsumer, taking two parameters - the first of type &lt;U&gt;
     *                   which can be any type, and the second of type double. The method reference will be converted by
     *                   this method to a DoubleConsumer, taking a single parameter of type double. Behind the scenes,
     *                   this BiConsumer will be called, passing the constant value to each invocation as the first
     *                   parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biConsumer as the
     *                   first parameter to it, and will have the same value for each of them.
     * @param <U>        The type of the constant value to be passed as the first parameter to each invocation of
     *                   biConsumer.
     * @return A DoubleConsumer taking a single parameter of type double.
     */
    public static <U> DoubleConsumer inverseDoubleConsumer(ObjDoubleConsumer<? super U> biConsumer, U value) {
        return t -> biConsumer.accept(value, t);
    }

    /**
     * Simply casts a method reference, which takes a single parameter of type <code>int</code> and returns void, to
     * an <code>IntConsumer</code>. Everything said about the {@link #consumer(Consumer)} method applies here. The
     * difference is that instead of an element of type &lt;T&gt; being streamed through, it would be a primitive <code>
     * int</code> instead. It may be harder to think of a situation where this overload would be useful, but this method
     * is included for sake of completeness.
     *
     * @param consumer A method reference to be cast to an IntConsumer.
     * @return A method reference cast to an IntConsumer.
     */
    @SuppressWarnings("unused")
    public static IntConsumer intConsumer(IntConsumer consumer) {
        return consumer;
    }

    /**
     * Builds an <code>IntConsumer</code> from a passed <code>BiConsumer</code>. Everything said about the
     * {@link #consumer(BiConsumer, Object)} method applies here. The difference is that instead of an element of type
     * &lt;T&gt; being streamed through, it would be a primitive <code>int</code> instead. It may be harder to think
     * of a situation where this overload would be useful, but this method is included for sake of completeness.
     * <p>
     * One note about using the Java <code>IntConsumer</code> interface, as it says in the Javadoc documentation for
     * it, "Unlike most other functional interfaces, IntConsumer is expected to operate via side-effects."
     *
     * @param biConsumer A method reference which is a BiConsumer, taking two parameters - the first of type int, and
     *                   the second of type &lt;U&gt;, which can be any type. The method reference will be converted by
     *                   this method to an IntConsumer, taking a single parameter of type int. Behind the scenes, this
     *                   BiConsumer will be called, passing the constant value to each invocation as the second
     *                   parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biConsumer as the
     *                   second parameter to it, and will have the same value for each of them.
     * @param <U>        The type of the constant value to be passed as the second parameter to each invocation of
     *                   biConsumer.
     * @return An IntConsumer taking a single parameter of type int.
     */
    public static <U> IntConsumer intConsumer(BiConsumer<Integer, ? super U> biConsumer, U value) {
        return t -> biConsumer.accept(t, value);
    }

    /**
     * Builds an <code>IntConsumer</code> from a passed <code>BiConsumer</code>. Everything said about the
     * {@link #inverseConsumer(BiConsumer, Object)} method applies here. The difference is that instead of an element of
     * type &lt;T&gt; being streamed through, it would be a primitive <code>int</code> instead. It may be harder to
     * think of a situation where this overload would be useful, but this method is included for sake of completeness.
     * <p>
     * One note about using the Java <code>IntConsumer</code> interface, as it says in the Javadoc documentation for
     * it, "Unlike most other functional interfaces, IntConsumer is expected to operate via side-effects."
     *
     * @param biConsumer A method reference which is a BiConsumer, taking two parameters - the first of type &lt;U&gt;
     *                   which can be any type, and the second of type int. The method reference will be converted by
     *                   this method to an IntConsumer, taking a single parameter of type int. Behind the scenes, this
     *                   BiConsumer will be called, passing the constant value to each invocation as the first parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biConsumer as the
     *                   first parameter to it, and will have the same value for each of them.
     * @param <U>        The type of the constant value to be passed as the first parameter to each invocation of
     *                   biConsumer.
     * @return An IntConsumer taking a single parameter of type int.
     */
    public static <U> IntConsumer inverseIntConsumer(ObjIntConsumer<? super U> biConsumer, U value) {
        return t -> biConsumer.accept(value, t);
    }

    /**
     * Simply casts a method reference, which takes a single parameter of type <code>long</code> and returns void, to
     * a <code>LongConsumer</code>. Everything said about the {@link #consumer(Consumer)} method applies here. The
     * difference is that instead of an element of type &lt;T&gt; being streamed through, it would be a primitive <code>
     * long</code> instead. It may be harder to think of a situation where this overload would be useful, but this method
     * is included for sake of completeness.
     *
     * @param consumer A method reference to be cast to a LongConsumer.
     * @return A method reference cast to a LongConsumer.
     */
    @SuppressWarnings("unused")
    public static LongConsumer longConsumer(LongConsumer consumer) {
        return consumer;
    }

    /**
     * Builds a <code>LongConsumer</code> from a passed <code>BiConsumer</code>. Everything said about the
     * {@link #consumer(BiConsumer, Object)} method applies here. The difference is that instead of an element of type
     * &lt;T&gt; being streamed through, it would be a primitive <code>long</code> instead. It may be harder to think
     * of a situation where this overload would be useful, but this method is included for sake of completeness.
     * <p>
     * One note about using the Java <code>LongConsumer</code> interface, as it says in the Javadoc documentation for
     * it, "Unlike most other functional interfaces, LongConsumer is expected to operate via side-effects."
     *
     * @param biConsumer A method reference which is a BiConsumer, taking two parameters - the first of type long, and
     *                   the second of type &lt;U&gt;, which can be any type. The method reference will be converted by
     *                   this method to a LongConsumer, taking a single parameter of type long. Behind the scenes, this
     *                   BiConsumer will be called, passing the constant value to each invocation as the second
     *                   parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biConsumer as the
     *                   second parameter to it, and will have the same value for each of them.
     * @param <U>        The type of the constant value to be passed as the second parameter to each invocation of
     *                   biConsumer.
     * @return A LongConsumer taking a single parameter of type long.
     */
    public static <U> LongConsumer longConsumer(BiConsumer<Long, ? super U> biConsumer, U value) {
        return t -> biConsumer.accept(t, value);
    }

    /**
     * Builds a <code>LongConsumer</code> from a passed <code>BiConsumer</code>. Everything said about the
     * {@link #inverseConsumer(BiConsumer, Object)} method applies here. The difference is that instead of an element of
     * type &lt;T&gt; being streamed through, it would be a primitive <code>long</code> instead. It may be harder to
     * think of a situation where this overload would be useful, but this method is included for sake of completeness.
     * <p>
     * One note about using the Java <code>LongConsumer</code> interface, as it says in the Javadoc documentation for
     * it, "Unlike most other functional interfaces, LongConsumer is expected to operate via side-effects."
     *
     * @param biConsumer A method reference which is a BiConsumer, taking two parameters - the first of type &lt;U&gt;
     *                   which can be any type, and the second of type long. The method reference will be converted by
     *                   this method to a LongConsumer, taking a single parameter of type long. Behind the scenes, this
     *                   BiConsumer will be called, passing the constant value to each invocation as the first parameter.
     * @param value      A constant value, in that it will be passed to every invocation of the passed biConsumer as the
     *                   first parameter to it, and will have the same value for each of them.
     * @param <U>        The type of the constant value to be passed as the first parameter to each invocation of
     *                   biConsumer.
     * @return A LongConsumer taking a single parameter of type long.
     */
    public static <U> LongConsumer inverseLongConsumer(ObjLongConsumer<? super U> biConsumer, U value) {
        return t -> biConsumer.accept(value, t);
    }

    /**
     * This method is a variation of the <code>consumer(...)</code> methods that has some special properties. First, it
     * is assumed that a setter method is being called on the target element, so there is a null check to make sure that
     * it is an object instance. If not, the <code>Consumer</code> built by this method simply returns. Second, it takes
     * a function that, given the target element, it returns the value to be passed to the setter. This  means that any
     * of the methods of {@link MapperUtils} are fair game to be used to build that function. For example, suppose we
     * want to write a method that, given a collection of <code>Widget</code> instances, it will build and set a
     * description for each of them. We could use this method to help accomplish that in the following contrived example:
     * <pre>
     *     private void buildWidgetDescriptions(Collection&lt;Widget&gt; widgets) {
     *         String productLine = formatProductLine();
     *         widgets.forEach(ConsumerUtils.setter(Widget::setDescription, MapperUtils.mapper(this::buildDescription, productLine)));
     *     }
     *
     *     private String buildDescription(Widget widget, String productLine) {
     *         ...
     *     }
     * </pre>
     * By making judicious use of static imports, we can reduce the clutter in the <code>forEach(...)</code> invocation
     * to:
     * <pre>
     *         widgets.forEach(setter(Widget::setDescription, mapper(this::buildDescription, productLine)));
     * </pre>
     *
     * @param consumer  A setter method reference from the class of the target element. It is a BiConsumer because the
     *                  first parameter will be the element itself, and the second parameter is the value to be set on
     *                  it.
     * @param extractor An extractor function that, given the target element, returns the value to be set.
     * @param <T>       The type of the target input element.
     * @param <U>       The type of the parameter to be passed to the setter method.
     * @return A Consumer representing the invocation of a setter method on a target element.
     */
    public static <T, U> Consumer<T> setter(BiConsumer<? super T, ? super U> consumer, Function<T, U> extractor) {
        return t -> {
            if (t != null) {
                consumer.accept(t, extractor.apply(t));
            }
        };
    }

    /**
     * This method is a variation of the <code>consumer(...)</code> methods that has some special properties. First, it
     * is assumed that a setter method is being called on the target element, so there is a null check to make sure that
     * it is an object instance. If not, the <code>Consumer</code> built by this method simply returns. Second, it takes
     * a function that, given the target element, it returns the value to be passed to the setter. This  means that any
     * of the methods of {@link MapperUtils} are fair game to be used to build that function. For example, suppose we
     * want to write a method that, given a collection of <code>Widget</code> instances, it will calculate and set a
     * price for each of them. We could use this method to help accomplish that in the following contrived example
     * (also note that is generally not a good idea to use a <code>double</code> for currency amounts):
     * <pre>
     *     private void buildWidgetPrices(Collection&lt;Widget&gt; widgets, String customerId) {
     *         double discount = getCustomerDiscount(customerId);
     *         widgets.forEach(ConsumerUtils.setter(Widget::setPrice, MapperUtils.mapper(this::calculatePrice, discount)));
     *     }
     *
     *     private double calculatePrice(Widget widget, double discount) {
     *         ...
     *     }
     * </pre>
     * By making judicious use of static imports, we can reduce the clutter in the <code>forEach(...)</code> invocation
     * to:
     * <pre>
     *         widgets.forEach(setter(Widget::setPrice, mapper(this::calculatePrice, discount)));
     * </pre>
     *
     * @param consumer  A setter method reference from the class of the target element. It is a BiConsumer because the
     *                  first parameter will be the element itself, and the second parameter is the value to be set on
     *                  it.
     * @param extractor An extractor function that, given the target element, returns the double value to be set.
     * @param <T>       The type of the target input element.
     * @return A Consumer representing the invocation of a setter method on a target element.
     */
    public static <T> Consumer<T> doubleSetter(BiConsumer<? super T, Double> consumer, ToDoubleFunction<T> extractor) {
        return t -> {
            if (t != null) {
                consumer.accept(t, extractor.applyAsDouble(t));
            }
        };
    }

    /**
     * Builds a <code>Consumer</code> from a passed <code>BiConsumer</code>. Everything said about the
     * {@link #doubleSetter(BiConsumer, ToDoubleFunction)} applies here. The difference is simply that the <code>
     * extractor</code> function parameter is of type <code>ToIntFunction</code> rather than <code>ToDoubleFunction
     * </code>.
     *
     * @param consumer  A setter method reference from the class of the target element. It is a BiConsumer because the
     *                  first parameter will be the element itself, and the second parameter is the value to be set on
     *                  it.
     * @param extractor An extractor function that, given the target element, returns the int value to be set.
     * @param <T>       The type of the target input element.
     * @return A Consumer representing the invocation of a setter method on a target element.
     */
    public static <T> Consumer<T> intSetter(BiConsumer<? super T, Integer> consumer, ToIntFunction<T> extractor) {
        return t -> {
            if (t != null) {
                consumer.accept(t, extractor.applyAsInt(t));
            }
        };
    }

    /**
     * Builds a <code>Consumer</code> from a passed <code>BiConsumer</code>. Everything said about the
     * {@link #doubleSetter(BiConsumer, ToDoubleFunction)} applies here. The difference is simply that the <code>
     * extractor</code> function parameter is of type <code>ToLongFunction</code> rather than <code>ToDoubleFunction
     * </code>.
     *
     * @param consumer  A setter method reference from the class of the target element. It is a BiConsumer because the
     *                  first parameter will be the element itself, and the second parameter is the value to be set on
     *                  it.
     * @param extractor An extractor function that, given the target element, returns the long value to be set.
     * @param <T>       The type of the target input element.
     * @return A Consumer representing the invocation of a setter method on a target element.
     */
    public static <T> Consumer<T> longSetter(BiConsumer<? super T, Long> consumer, ToLongFunction<T> extractor) {
        return t -> {
            if (t != null) {
                consumer.accept(t, extractor.applyAsLong(t));
            }
        };
    }
}
