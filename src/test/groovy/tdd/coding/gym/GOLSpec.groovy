package tdd.coding.gym

import spock.lang.Specification
import spock.lang.Unroll

class GOLSpec extends Specification {

    def "A new GOL has a populationSize of zero"() {
        given:
        def gol = new GOL();
        expect:
        gol.populationSize() == 0;
    }

    @Unroll
    def "Populating with #xys gives census #census"() {
        given:
        def gol = new GOL();

        expect:
        gol.populate(xys).census() == census

        where:
        xys        || census
        "1,1"      || "[1,1]"
        "1,1, 2,2" || "[1,1, 2,2]"
        "1,1, 1,1" || "[1,1]"
    }

    @Unroll
    def "Populating with #xys evolves to #census"() {
        given:
        def gol = new GOL();

        expect:
        gol.populate(xys).evolve().census() == census

        where:
        xys   || census
        "5,5, 6,5, 7,5" || "[6,5, 6,6, 6,4]"

    }
}