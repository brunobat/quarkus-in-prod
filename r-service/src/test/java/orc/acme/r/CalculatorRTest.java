package orc.acme.r;

import org.junit.jupiter.api.Test;

class CalculatorRTest {
    @Test
    void add() throws Exception {
        System.out.println(CalculatorR.ADD.execute(0d, 0d));
    }
}
