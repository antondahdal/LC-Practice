package test.week03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week03.ProductOfArrayExceptSelf;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ProductOfArrayExceptSelfTest {

    private ProductOfArrayExceptSelf sol;

    @BeforeEach
    void setUp() {
        sol = new ProductOfArrayExceptSelf();
    }

    @Test
    void basic() {
        assertArrayEquals(new int[] {24, 12, 8, 6}, sol.productExceptSelf(new int[] {1, 2, 3, 4}));
    }

    @Test
    void withZero() {
        assertArrayEquals(new int[] {0, 0, 9, 0, 0}, sol.productExceptSelf(new int[] {-1, 1, 0, -3, 3}));
    }
}
