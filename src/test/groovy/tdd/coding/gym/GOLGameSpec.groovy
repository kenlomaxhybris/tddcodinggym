package tdd.coding.gym


import org.springframework.boot.test.rule.OutputCapture;


import spock.lang.Specification

class GOLGameSpec extends Specification{
    @org.junit.Rule
    OutputCapture capture = new OutputCapture()

    def "GOL Display ok"(){
        given:
        def golGame = new GOLGame( new GOL().populate("5,5, 6,5, 7,5") );

        when:
        golGame.step();

        then:
        capture.toString() ==
"""----------
----------
----------
----------
----------
-----+++--
----------
----------
----------
----------
----------
==========================
"""
    }

    def "Play Game"(){
        given:
        String[] repeats = ["10"];

        when:
        GOLGame.main( repeats );

        then:
        capture.toString().contains("==");
    }

}
