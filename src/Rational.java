package src;

public class Rational extends Number implements Comparable<Rational> {
    private static final Rational zero = new Rational(0, 1);

    private int num;   // the numerator
    private int den;   // the denominator

    // create and initialize a new Rational object
    public Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Denominator is zero");
        }

        // reduce fraction
        int g = gcd(numerator, denominator);
        num = numerator / g;
        den = denominator / g;

        // needed only for negative numbers
        if (den < 0) {
            den = -den;
            num = -num;
        }
    }

    public Rational(String s) {
        int indexOfDivide = s.indexOf('/');
        if (indexOfDivide == -1) {
            throw new NumberFormatException("Not a rational number");
        }
        num = Integer.parseInt(s.substring(0, indexOfDivide));
        den = Integer.parseInt(s.substring(indexOfDivide + 1, s.length()));
        new Rational(num, den);
    }

    public Rational() {
        num = 0;
        den = 0;
    }


    // return the numerator and denominator of (this)
    public int numerator() {
        return num;
    }

    public int denominator() {
        return den;
    }

    // return string representation of (this)
    public String toString() {
        if (den == 1) return num + "";
        else return num + "/" + den;
    }

    // return { -1, 0, +1 } if a < b, a = b, or a > b
    public int compareTo(Rational b) {
        Rational a = this;
        int lhs = a.num * b.den;
        int rhs = a.den * b.num;
        return Integer.compare(lhs, rhs);
    }

    // is this Rational object equal to y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Rational b = (Rational) y;
        return compareTo(b) == 0;
    }

    // hashCode consistent with equals() and compareTo()
    // (better to hash the numerator and denominator and combine)
    public int hashCode() {
        return this.toString().hashCode();
    }

    // return gcd(|m|, |n|)
    private static int gcd(int m, int n) {
        if (m < 0) m = -m;
        if (n < 0) n = -n;
        if (0 == n) return m;
        else return gcd(n, m % n);
    }

    // return lcm(|m|, |n|)
    private static int lcm(int m, int n) {
        if (m < 0) m = -m;
        if (n < 0) n = -n;
        return m * (n / gcd(m, n));    // parentheses important to avoid overflow
    }

    // return a * b, staving off overflow as much as possible by cross-cancellation
    public Rational times(Rational b) {
        Rational a = this;

        // reduce p1/q2 and p2/q1, then multiply, where a = p1/q1 and b = p2/q2
        Rational c = new Rational(a.num, b.den);
        Rational d = new Rational(b.num, a.den);
        return new Rational(c.num * d.num, c.den * d.den);
    }

    // return a + b, staving off overflow
    public Rational plus(Rational b) {
        Rational a = this;

        // special cases
        if (a.compareTo(zero) == 0) return b;
        if (b.compareTo(zero) == 0) return a;

        // Find gcd of numerators and denominators
        int f = gcd(a.num, b.num);
        int g = gcd(a.den, b.den);

        // add cross-product terms for numerator
        Rational s = new Rational((a.num / f) * (b.den / g) + (b.num / f) * (a.den / g), lcm(a.den, b.den));

        // multiply back in
        s.num *= f;
        return s;
    }

    public Rational reciprocal() { return new Rational(den, num);  }

    @Override
    public int intValue() {
        return num / den;
    }

    @Override
    public long longValue() {
        return (long) num / (long) den;
    }

    @Override
    public float floatValue() {
        return (float) num / (float) den;
    }

    @Override
    public double doubleValue() {
        return (double) num / (double) den;
    }
}