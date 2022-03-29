package biginteger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BigIntegerTest {

    @Test
    void test_add_NonNumericInput_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new BigInteger("123ABC"));
    }

    @Test
    void test_add_EmptyInput_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new BigInteger(""));
    }

    @Test
    void test_add_NullInput_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> new BigInteger(null));
    }

    @Test
    void test_add_SumWithNullValue_ShouldThrowException() {
        BigInteger first = new BigInteger("11");
        BigInteger second = null;
        assertThrows(IllegalArgumentException.class, () -> first.add(second));
    }

    @Test
    void test_add_SumWithDifferentLengths_ShouldAddCorrectly() {
        BigInteger first = new BigInteger("11");
        BigInteger second = new BigInteger("111");

        BigInteger summed = first.add(second);
        assertEquals(new BigInteger("122"), summed);
    }

    @Test
    void test_add_SumWithCarry_ShouldAddCorrectly() {
        BigInteger first = new BigInteger("999");
        BigInteger second = new BigInteger("999");

        BigInteger summed = first.add(second);
        assertEquals(new BigInteger("1998"), summed);
    }

    @Test
    void test_add_SameLength_ShouldAddCorrectly() {
        BigInteger first = new BigInteger("123");
        BigInteger second = new BigInteger("456");

        BigInteger summed = first.add(second);
        assertEquals(new BigInteger("579"), summed);
    }

    @Test
    void test_equals_NullValue_ReturnsFalse() {
        BigInteger bigInteger = new BigInteger("123");
        assertFalse(bigInteger.equals(null));
    }

    @Test
    void test_equals_WrongType_ReturnsFalse() {
        BigInteger bigInteger = new BigInteger("123");
        String notBigInteger = "123";
        assertFalse(bigInteger.equals(notBigInteger));
    }

    @Test
    void test_equals_SameSizeDifferent_ReturnsFalse() {
        BigInteger first = new BigInteger("123");
        BigInteger second = new BigInteger("456");
        assertFalse(first.equals(second));
    }

    @Test
    void test_equals_DifferentSize_ReturnsFalse() {
        BigInteger first = new BigInteger("123");
        BigInteger second = new BigInteger("1234");
        assertFalse(first.equals(second));
    }

    @Test
    void test_equals_SameSizeEqual_ReturnsTrue() {
        BigInteger first = new BigInteger("123");
        BigInteger second = new BigInteger("123");
        assertTrue(first.equals(second));
    }
}
