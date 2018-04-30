package tdd.coding.gym

import org.springframework.boot.test.rule.OutputCapture
import spock.lang.Specification
import spock.lang.Unroll

class GOLSpec extends Specification {

    def "A new GOL has a population of zero"(){
        given:
        def gol = new GOL();

        expect:
        gol.getPopulationSize() == 0;
    }

    @Unroll
    def "A GOL populated with xys #xys has cells #cells"(){
        given:
        def gol = new GOL();

        expect:
        gol.populate(xys).allCells == cells

        where:
        xys || cells
        "1,1" || Cell.createCells( "1,1")
        "1,1, 2,2, 1,1" || Cell.createCells( "1,1, 2,2")
    }

    @Unroll
    def "An empty GOL looks like #display"(){
        given:
        def gol = new GOL()

        expect:
        gol.show(5) == display

        where:
        display || something
        "----------\n----------\n----------\n----------\n----------\n----------\n----------\n----------\n----------\n----------\n----------\n" || ""
    }

    @Unroll
    def "A GOL populated with xys -1,0, 0,0, 1,0 looks like #display"(){
        given:
        def gol = new GOL();
        gol.populate("-1,0, 0,0, 1,0")

        expect:
        gol.show(5) == display

        where:
        display || something
        "----------\n----------\n----------\n----------\n----------\n----+++---\n----------\n----------\n----------\n----------\n----------\n" || ""
    }

    def "A GOL populated with xys -1,0, 0,0, 1,0 evolves to  #display"(){
        given:
        def gol = new GOL();
        gol.populate("-1,0, 0,0, 1,0").evolve();

        expect:
        gol.show(5) ==display

        where:
        display || something
        "----------\n----------\n----------\n----------\n-----+----\n-----+----\n-----+----\n----------\n----------\n----------\n----------\n" || ""
    }

    @org.junit.Rule
    OutputCapture capture = new OutputCapture()

    def "Playing Game shows results on the screen"(){
        given:
        def gol = new GOL().populate("0,0, 1,0, 2,0, 3,0, 4,0, 5,0, 6,0, 7,0");
        when:
        gol.play( 100,50 );

        then:
        capture.toString().contains("+++");
    }

}
