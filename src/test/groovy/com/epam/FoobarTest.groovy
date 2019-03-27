package com.epam

import spock.lang.Specification

class FoobarTest extends Specification {

    def "should return 7 when adding 3 and 4"() {
        given:
        Foobar foobar = new Foobar()

        when:
        int result = foobar.add(3, 4)

        then:
        result == 7
    }
}
