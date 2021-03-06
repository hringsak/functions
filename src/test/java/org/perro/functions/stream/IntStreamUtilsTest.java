package org.perro.functions.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.perro.functions.stream.IntStreamUtils.defaultIntStream;

public class IntStreamUtilsTest {

    private int[] inputArray;
    private IntStream inputPrimitiveStream;
    private Collection<Integer> inputCollection;
    private Stream<Integer> inputStream;
    private IntStream result;

    @Test
    public void testDefaultStreamForNullArray() {
        givenNullInputArray();
        whenDefaultStreamIsRetrievedFromArray();
        thenExpectStream();
    }

    private void givenNullInputArray() {
        inputArray = null;
    }

    private void whenDefaultStreamIsRetrievedFromArray() {
        result = defaultIntStream(inputArray);
    }

    private void thenExpectStream(int... expected) {
        assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void testDefaultStreamForEmptyArray() {
        givenInputArray();
        whenDefaultStreamIsRetrievedFromArray();
        thenExpectStream();
    }

    private void givenInputArray(int... values) {
        inputArray = values;
    }

    @Test
    public void testDefaultStreamForPopulatedArray() {
        givenInputArray(1, 2);
        whenDefaultStreamIsRetrievedFromArray();
        thenExpectStream(1, 2);
    }

    @Test
    public void testDefaultStreamForNullPrimitiveStream() {
        givenNullInputPrimitiveStream();
        whenDefaultStreamIsRetrievedFromPrimitiveStream();
        thenExpectStream();
    }

    private void givenNullInputPrimitiveStream() {
        inputPrimitiveStream = null;
    }

    private void whenDefaultStreamIsRetrievedFromPrimitiveStream() {
        result = defaultIntStream(inputPrimitiveStream);
    }

    @Test
    public void testDefaultStreamForEmptyPrimitiveStream() {
        givenInputPrimitiveStream();
        whenDefaultStreamIsRetrievedFromPrimitiveStream();
        thenExpectStream();
    }

    private void givenInputPrimitiveStream(int... values) {
        inputPrimitiveStream = IntStream.of(values);
    }

    @Test
    public void testDefaultStreamForPopulatedPrimitiveStream() {
        givenInputPrimitiveStream(1, 2);
        whenDefaultStreamIsRetrievedFromPrimitiveStream();
        thenExpectStream(1, 2);
    }

    @Test
    public void testDefaultStreamForNullCollection() {
        givenNullInputCollection();
        whenDefaultStreamIsRetrievedFromCollection();
        thenExpectStream();
    }

    private void givenNullInputCollection() {
        inputCollection = null;
    }

    private void whenDefaultStreamIsRetrievedFromCollection() {
        result = defaultIntStream(inputCollection);
    }

    @Test
    public void testDefaultStreamForEmptyCollection() {
        givenInputCollection();
        whenDefaultStreamIsRetrievedFromCollection();
        thenExpectStream();
    }

    private void givenInputCollection(Integer... values) {
        inputCollection = Arrays.asList(values);
    }

    @Test
    public void testDefaultStreamForPopulatedCollection() {
        givenInputCollection(1, 2);
        whenDefaultStreamIsRetrievedFromCollection();
        thenExpectStream(1, 2);
    }

    @Test
    public void testDefaultStreamForNullStream() {
        givenNullInputStream();
        whenDefaultStreamIsRetrievedFromStream();
        thenExpectStream();
    }

    private void givenNullInputStream() {
        inputStream = null;
    }

    private void whenDefaultStreamIsRetrievedFromStream() {
        result = defaultIntStream(inputStream);
    }

    @Test
    public void testDefaultStreamForEmptyStream() {
        givenInputStream();
        whenDefaultStreamIsRetrievedFromStream();
        thenExpectStream();
    }

    private void givenInputStream(Integer... values) {
        inputStream = Stream.of(values);
    }

    @Test
    public void testDefaultStreamForPopulatedStream() {
        givenInputStream(1, 2);
        whenDefaultStreamIsRetrievedFromStream();
        thenExpectStream(1, 2);
    }
}
