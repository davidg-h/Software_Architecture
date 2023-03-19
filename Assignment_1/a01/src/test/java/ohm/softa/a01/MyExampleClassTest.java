package ohm.softa.a01;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class MyExampleClassTest {
    @Test
    void testMultiply() {
        IntStream.range(-5, 5)
                .forEach(i -> assertEquals(i * (i - 1), new MyExampleClass().multiply(i, i - 1)));
    }
}