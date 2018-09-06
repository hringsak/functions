package org.hringsak.functions

import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.ToDoubleBiFunction

import static org.apache.commons.lang3.StringUtils.defaultString
import static org.hringsak.functions.DoubleSupplierUtils.doubleSupplier
import static org.hringsak.functions.SupplierUtils.arguments

class DoubleSupplierUtilsSpec extends Specification {

    @Unroll
    def 'double supplier passing value parameter "#value"'() {

        expect:
        def function = { String s -> (double) defaultString(s).length() }
        doubleSupplier(function, value).getAsDouble() == expected

        where:
        value  | expected
        null   | 0.0D
        ''     | 0.0D
        'test' | 4.0D
    }

    @Unroll
    def 'double supplier passing parameters "#left" and "#right"'() {

        expect:
        def function = { String l, String r -> ((double) defaultString(l).length()) + defaultString(r).length() } as ToDoubleBiFunction
        doubleSupplier(function, arguments(left, right)).getAsDouble() == expected

        where:
        left   | right  | expected
        null   | null   | 0.0D
        null   | ''     | 0.0D
        null   | 'test' | 4.0D
        ''     | null   | 0.0D
        ''     | ''     | 0.0D
        ''     | 'test' | 4.0D
        'test' | null   | 4.0D
        'test' | ''     | 4.0D
        'test' | 'test' | 8.0D
    }
}