package tdd.coding.gym

import spock.lang.Specification
import spock.lang.Unroll

class EngineSpec extends Specification {

    def "New Engine is empty"(){
        given:
        def engine = new Engine();

        expect:
        engine.liveCells.isEmpty();
    }


    @Unroll
    def "Engine populated with cells #xys has cells #population" (){
        given:
        def engine = new Engine()

        expect:
        engine.populate(  new GOL().populate(xys).cells ).liveCells.toString() == population;

        where:
        xys || population
        "1,1" || "[1,1]"
        "1,1, 2,2" || "[1,1, 2,2]"
    }
    @Unroll
    def "Engine populated with cells #xys has embryos #embryos"(){
        given:
        def engine = new Engine()

        expect:
        engine.populate(  new GOL().populate(xys).cells ).embryos.toString() == embryos;

        where:
        xys || embryos
        "1,1" || "[1,0, 2,1, 0,0, 2,2, 0,1, 1,2, 0,2, 2,0]"
        "1,1, 2,2" || "[1,0, 2,1, 3,2, 0,0, 3,3, 0,1, 1,2, 2,3, 0,2, 1,3, 2,0, 3,1]"
    }
    @Unroll
    def "Engine populated with cells #xys has liveNeighbours #liveNeighbours"(){
        given:
        def engine = new Engine()

        expect:
        engine.populate(  new GOL().populate(xys).cells ).neighbourMapping.toString() == liveNeighbours;

        where:
        xys || liveNeighbours
        "1,1" || "[1,0:1, 2,1:1, 1,1:0, 0,0:1, 2,2:1, 0,1:1, 1,2:1, 0,2:1, 2,0:1]"
        "1,1, 2,2" || "[1,0:1, 2,1:2, 3,2:1, 1,1:1, 2,2:1, 0,0:1, 3,3:1, 0,1:1, 1,2:2, 2,3:1, 0,2:1, 1,3:1, 2,0:1, 3,1:1]"
    }

    @Unroll
    def "Engine populated with cells #xys evolves to #nexGeneration"(){
        given:
        def engine = new Engine()

        expect:
        engine.populate(  new GOL().populate(xys).cells ).evolve().toString() == nexGeneration;

        where:
        xys || nexGeneration
        "1,1" || "[]"
        "5,5, 6,5, 7,5" || "[6,5, 6,6, 6,4]"
    }
}
