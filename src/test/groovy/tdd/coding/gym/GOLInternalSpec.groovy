package tdd.coding.gym

import spock.lang.Specification
import spock.lang.Unroll

class GOLInternalSpec extends Specification {

    def "A GOL populated with xys '1,1' has expected neighbours"(){
        given:
        def gol = new GOL();

        expect:
        gol.populate("1,1").locateEmbryos().allNeighbours ==
                Cell.cells( "1,0, 2,1, 0,0, 2,2, 0,1, 1,2, 0,2, 2,0")
    }

    @Unroll
    def "A GOL populated with xys #xys has embryos #neighbours"(){
        given:
        def gol = new GOL();

        expect:
        gol.populate(xys).locateEmbryos().allNeighbours == neighbours

        where:
        xys || neighbours
        "1,1" || Cell.cells( "0,0, 0,1, 0,2, 1,0, 1,2, 2,0, 2,1, 2,2" )
        "1,1, 2,2" ||  Cell.cells(
                "0,0, 0,1, 0,2, 1,0, 1,2, 1,3, 2,0, 2,1, 2,3, 3,1, 3,2, 3,3" )

    }

    @Unroll
    def "Engine populated with cells #xys has liveNeighbours #liveNeighbours"(){
        given:
        def gol = new GOL()

        expect:
        gol.populate( xys ).locateEmbryos().setNeighbourMapping().mappingOfCellsToLiveNeighbours == liveNeighbours;

        where:
        xys || liveNeighbours
        "1,1" || MapFactory.map(
                Cell.cells(
                "0,0, 0,1, 0,2, 1,0, 1,1, 1,2, 2,0, 2,1, 2,2"),
            1,   1,   1,   1,   0,   1,   1,   1,   1)
        "1,1, 2,2" || MapFactory.map(
                Cell.cells(
                "0,0, 0,1, 0,2, 1,0, 1,1, 1,2, 1,3, 2,0, 2,1, 2,2, 2,3, 3,1, 3,2, 3,3" ),
            1,   1,   1,   1,   1,   2,   1,   1,   2,   1,   1,   1,   1,   1 )
    }
}

