package org.hringsak.functions

import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.*

import static java.util.function.Function.identity
import static java.util.stream.Collectors.toList
import static org.apache.commons.lang3.StringUtils.defaultString

class MapperUtilsSpec extends Specification {

    @Unroll
    def 'mapper with default passing value "#value" and mapperResult "#mapperResult" returns "#expected"'() {

        expect:
        def mapper = { s -> mapperResult }
        MapperUtils.mapperDefault(mapper, 'default').apply(value) == expected

        where:
        value  | mapperResult | expected
        null   | 'test'       | 'default'
        'test' | null         | 'default'
        ''     | 'test'       | 'test'
        'test' | 'test'       | 'test'
    }

    @Unroll
    def 'mapper for bi-function passing values "#paramOne" and "#paramTwo" returns #expected'() {

        expect:
        def mapper = { a, b -> Objects.equals(a, b) } as BiFunction<String, String, Boolean>
        MapperUtils.mapper(mapper, paramOne).apply(paramTwo) == expected

        where:
        paramOne | paramTwo | expected
        'test'   | null     | false
        'test'   | ''       | false
        'test'   | 'test'   | true
        null     | 'test'   | false
        ''       | 'test'   | false
    }

    @Unroll
    def 'mapper for bi-function as second parameter passing values "#paramOne" and "#paramTwo" returns #expected'() {

        expect:
        def mapper = { a, b -> Objects.equals(a, b) } as BiFunction<String, String, Boolean>
        MapperUtils.mapper(paramOne, mapper).apply(paramTwo) == expected

        where:
        paramOne | paramTwo | expected
        'test'   | null     | false
        'test'   | ''       | false
        'test'   | 'test'   | true
        null     | 'test'   | false
        ''       | 'test'   | false
    }

    @Unroll
    def 'double mapper with default passing value "#value" returns "#expected"'() {

        expect:
        def mapper = { s -> (double) s.length() }
        MapperUtils.doubleMapperDefault(mapper, -1).applyAsDouble(value) == expected

        where:
        value  | expected
        null   | -1
        ''     | 0
        'test' | 4
    }

    @Unroll
    def 'double mapper for bi-function passing values "#paramOne" and "#paramTwo" returns #expected'() {

        expect:
        def mapper = { String a, String b -> ((double) defaultString(a).length()) + defaultString(b).length() } as ToDoubleBiFunction<String, String>
        MapperUtils.doubleMapper(mapper, paramOne).applyAsDouble(paramTwo) == expected

        where:
        paramOne | paramTwo | expected
        'test'   | null     | 4.0D
        'test'   | ''       | 4.0D
        'test'   | 'test'   | 8.0D
        null     | 'test'   | 4.0D
        ''       | 'test'   | 4.0D
    }

    @Unroll
    def 'double mapper for bi-function as second parameter passing values "#paramOne" and "#paramTwo" returns #expected'() {

        expect:
        def mapper = { String a, String b -> ((double) defaultString(a).length()) + defaultString(b).length() } as ToDoubleBiFunction<String, String>
        MapperUtils.doubleMapper(paramOne, mapper).applyAsDouble(paramTwo) == expected

        where:
        paramOne | paramTwo | expected
        'test'   | null     | 4.0D
        'test'   | ''       | 4.0D
        'test'   | 'test'   | 8.0D
        null     | 'test'   | 4.0D
        ''       | 'test'   | 4.0D
    }

    @Unroll
    def 'int mapper with default passing value "#value" returns "#expected"'() {

        expect:
        def mapper = { s -> s.length() }
        MapperUtils.intMapperDefault(mapper, -1).applyAsInt(value) == expected

        where:
        value  | expected
        null   | -1
        ''     | 0
        'test' | 4
    }

    @Unroll
    def 'int mapper for bi-function passing values "#paramOne" and "#paramTwo" returns #expected'() {

        expect:
        def mapper = { String a, String b -> defaultString(a).length() + defaultString(b).length() } as ToIntBiFunction<String, String>
        MapperUtils.intMapper(mapper, paramOne).applyAsInt(paramTwo) == expected

        where:
        paramOne | paramTwo | expected
        'test'   | null     | 4
        'test'   | ''       | 4
        'test'   | 'test'   | 8
        null     | 'test'   | 4
        ''       | 'test'   | 4
    }

    @Unroll
    def 'int mapper for bi-function as second parameter passing values "#paramOne" and "#paramTwo" returns #expected'() {

        expect:
        def mapper = { String a, String b -> defaultString(a).length() + defaultString(b).length() } as ToIntBiFunction<String, String>
        MapperUtils.intMapper(paramOne, mapper).applyAsInt(paramTwo) == expected

        where:
        paramOne | paramTwo | expected
        'test'   | null     | 4
        'test'   | ''       | 4
        'test'   | 'test'   | 8
        null     | 'test'   | 4
        ''       | 'test'   | 4
    }

    @Unroll
    def 'long mapper with default passing value "#value" returns "#expected"'() {

        expect:
        def mapper = { s -> (long) s.length() }
        MapperUtils.longMapperDefault(mapper, -1).applyAsLong(value) == expected

        where:
        value  | expected
        null   | -1
        ''     | 0
        'test' | 4
    }

    @Unroll
    def 'long mapper for bi-function passing values "#paramOne" and "#paramTwo" returns #expected'() {

        expect:
        def mapper = { String a, String b -> ((long) defaultString(a).length()) + defaultString(b).length() } as ToLongBiFunction<String, String>
        MapperUtils.longMapper(mapper, paramOne).applyAsLong(paramTwo) == expected

        where:
        paramOne | paramTwo | expected
        'test'   | null     | 4L
        'test'   | ''       | 4L
        'test'   | 'test'   | 8L
        null     | 'test'   | 4L
        ''       | 'test'   | 4L
    }

    @Unroll
    def 'long mapper for bi-function as second parameter passing values "#paramOne" and "#paramTwo" returns #expected'() {

        expect:
        def mapper = { String a, String b -> ((long) defaultString(a).length()) + defaultString(b).length() } as ToLongBiFunction<String, String>
        MapperUtils.longMapper(paramOne, mapper).applyAsLong(paramTwo) == expected

        where:
        paramOne | paramTwo | expected
        'test'   | null     | 4L
        'test'   | ''       | 4L
        'test'   | 'test'   | 8L
        null     | 'test'   | 4L
        ''       | 'test'   | 4L
    }

    @Unroll
    def 'stream of passing value "#target" finding first returns "#expected"'() {

        expect:
        def stream = MapperUtils.streamOf(identity()).apply(target)
        stream.findFirst().orElse(null) == expected

        where:
        target || expected
        'test' || 'test'
        null   || null
    }

    @Unroll
    def 'flat mapper taking function passing value "#paramOne" returns #expected'() {

        expect:
        Function function = { String param -> param.codePoints().boxed().collect(toList()) }
        def codePoints = MapperUtils.flatMapper(function).apply(paramOne).collect(toList())
        codePoints == expected

        where:
        paramOne || expected
        'test'   || ['t', 'e', 's', 't']
        null     || []
        ''       || []
    }

    @Unroll
    def 'pair of with values "#target" and "#right" returns #expected'() {

        expect:
        def leftFunction = { t -> t } as Function<String, String>
        def rightFunction = { t -> right } as Function<String, String>
        MapperUtils.pairOf(leftFunction, rightFunction).apply(target) == expected

        where:
        target   | right   || expected
        null     | 'right' || Pair.of(null, 'right')
        'target' | 'right' || Pair.of('target', 'right')
        'target' | null    || Pair.of('target', null)
    }

    @Unroll
    def 'pair with list passing values "#target" and "#listParam" returns #expected'() {

        expect:
        MapperUtils.pairWith(listParam).apply(target) == expected

        where:
        target   | listParam     || expected
        null     | ['listParam'] || Pair.of(null, 'listParam')
        'target' | ['listParam'] || Pair.of('target', 'listParam')
        'target' | null          || Pair.of('target', null)
    }

    @Unroll
    def 'pair with list passing function with values "#target" and "#listParam" returns #expected'() {

        expect:
        def identity = { t -> t } as Function<String, String>
        MapperUtils.pairWith(identity, listParam).apply(target) == expected

        where:
        target   | listParam     || expected
        null     | ['listParam'] || Pair.of(null, 'listParam')
        'target' | ['listParam'] || Pair.of('target', 'listParam')
        'target' | null          || Pair.of('target', null)
    }

    def 'pair with index calling multiple times returns incremented indexes'() {
        expect:
        def mapper = MapperUtils.pairWithIndex()
        (0..5).each { i ->
            with(mapper.apply('test')) {
                getRight() == i
            }
        }
    }

    @Unroll
    def 'pair with index returns #expected'() {

        expect:
        MapperUtils.pairWithIndex().apply(value) == expected

        where:
        value   | expected
        'value' | Pair.of('value', 0)
        null    | Pair.of(null, 0)
        ''      | Pair.of('', 0)
    }

    @Unroll
    def 'pair with index passing function returns #expected'() {

        expect:
        def identity = Function.identity()
        MapperUtils.pairWithIndex(identity).apply(value) == expected

        where:
        value   | expected
        'value' | Pair.of('value', 0)
        null    | Pair.of(null, 0)
        ''      | Pair.of('', 0)
    }

    @Unroll
    def 'ternary testing predicateParameter=#predicateParameter'() {

        expect:
        def predicate = { String s -> s.isEmpty() } as Predicate
        def trueValueFunction = { s -> trueValue } as Function
        def falseValueFunction = { s -> falseValue } as Function
        def result = MapperUtils.ternary(predicate, trueValueFunction, falseValueFunction).apply(predicateParameter)
        result == expectedResult

        where:
        predicateParameter | trueValue   | falseValue   || expectedResult
        'a'                | 'trueValue' | 'falseValue' || 'falseValue'
        ''                 | 'trueValue' | 'falseValue' || 'trueValue'
    }
}