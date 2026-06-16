package main.java.com.pachrijam.integrals;

import java.util.function.DoubleUnaryOperator;

/**
 * Simple demo and smoke tests for the integrators.
 * Run: java -cp target/classes com.pachrijam.integrals.IntegratorsDemo
 * (or run from your IDE)
 */
public class IntegratorsDemo {
    public static void main(String[] args) {
        DoubleUnaryOperator sin = Math::sin;
        double a = 0.0;
        double b = Math.PI;

        System.out.println("Composite trapezoid (n=1000): " + Integrators.compositeTrapezoid(sin, a, b, 1000));
        System.out.println("Composite Simpson (n=100): " + Integrators.compositeSimpson(sin, a, b, 100));
        System.out.println("Adaptive Simpson: " + Integrators.adaptiveSimpson(sin, a, b, 1e-10, 20));
        double[] gk = Integrators.gaussKronrod15(sin, a, b);
        System.out.println("Gauss-Kronrod (15): " + gk[0] + "  (err~" + gk[1] + ")");

        // Basic checks against exact value 2.0
        double exact = 2.0;
        assert Math.abs(Integrators.compositeSimpson(sin, a, b, 100) - exact) < 1e-6 : "Simpson error";
        assert Math.abs(Integrators.adaptiveSimpson(sin, a, b, 1e-10, 20) - exact) < 1e-9 : "Adaptive Simpson error";
        assert Math.abs(gk[0] - exact) < 1e-9 : "Gauss-Kronrod error";

        System.out.println("All smoke checks passed.");
    }
}
