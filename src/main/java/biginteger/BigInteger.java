package biginteger;

import java.util.Stack;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents sufficiently large numbers that do not fit into a `long` or `integer` datatype.
 */
public class BigInteger {

    private Node reversedIntegerList;

    /**
     * Empty constructor, used internally to create and modify the object incrementally during calculations.
     */
    private BigInteger(){};

    /**
     * Public constructor used to create BigIntegers. Input must be non-null, non-empty, and only contain numeric
     * characters.
     *
     * @param string representation of the integer
     */
    public BigInteger(String string){

        checkNotNull(string);
        if ("".equals(string)) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }

        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("Input must contain numeric values only, found: " + c);
            }

            this.reversedIntegerList = new Node(Character.getNumericValue(c), this.reversedIntegerList);
        }
    }

    /**
     * Adds a BigInteger to this BigInteger. The returned value is a new BigInteger object.
     *
     * @param bigint to add
     * @return sum of two BigIntegers
     */
    public BigInteger add(BigInteger bigint) {

        if (bigint == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }

        BigInteger solution = new BigInteger();
        Node a = bigint.reversedIntegerList;
        Node b = this.reversedIntegerList;

        int carry = 0;
        Node last = null;
        while (a != null || b != null) {

            int aint = a != null ? a.value : 0;
            int bint = b != null ? b.value : 0;
            int sum = aint + bint + carry;

            Node digit = new Node(sum % 10);
            carry = sum / 10;

            if (solution.reversedIntegerList == null) {
                solution.reversedIntegerList = digit;
            } else {
                last.next = digit;
            }

            last = digit;
            a = a != null ? a.next : null;
            b = b!= null ? b.next : null;

        }

        if (carry != 0) {
            last.next = new Node(carry);
        }

        return solution;

    }

    /**
     * Two big integers are considered to be equal if they represent the same numbers.
     *
     * @param obj to compare against
     * @return
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof BigInteger)) {
            return false;
        }

        BigInteger first = this;
        BigInteger second = (BigInteger) obj;

        Node currentIntFirst = first.reversedIntegerList;
        Node currentIntSecond = second.reversedIntegerList;

        while(currentIntFirst != null && currentIntSecond != null) {

            if (currentIntFirst.value != currentIntSecond.value) {
                return false;
            }

            currentIntFirst = currentIntFirst.next;
            currentIntSecond = currentIntSecond.next;
        }

        return currentIntFirst == null && currentIntSecond == null;
    }

    @Override
    public String toString() {
        Stack<Node> stack = new Stack<>();
        Node current = this.reversedIntegerList;
        while (current != null) {
            stack.push(current);
            current = current.next;
        }

        StringBuilder builder = new StringBuilder();
        while(!stack.empty()) {
            builder.append(stack.pop().value);
        }

        return builder.toString();
    }

    /**
     * Internal class used to create a LinkedList. Each object holds a single digit value that represents a single
     * digit in the BigInteger.
     */
    private static class Node {
        private final int value;
        private Node next;

        private Node(int value) {
            this(value, null);
        }

        private Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

}
