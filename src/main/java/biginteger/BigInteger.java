package biginteger;

import java.util.Stack;

public class BigInteger {

    private Node reversedIntegerList;

    private BigInteger(){};
    public BigInteger(String string){

        if (string == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }

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
