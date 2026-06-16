package com.pachrijam.integrals;

import java.util.function.DoubleUnaryOperator;

/**
 * Numerical integration utilities.
 *
 * Provides composite trapezoid, composite Simpson (1/3), adaptive Simpson,
 * and a Gauss-Kronrod (7/15) quadrature estimator with an error estimate.
 */
public final class Integrators {

    private Integrators() {}

    /**
     * Composite trapezoid rule with n subintervals (n >= 1).
     */
    public static double compositeTrapezoid(DoubleUnaryOperator f, double a, double b, int n) {
        if (n < 1) throw new IllegalArgumentException("n must be >= 1");
        double h = (b - a) / n;
        double s = 0.5 * (f.applyAsDouble(a) + f.applyAsDouble(b));
        for (int i = 1; i < n; ++i) {
            double x = a + i * h;
            s += f.applyAsDouble(x);
        }
        return s * h;
    }

    /**
     * Composite Simpson's 1/3 rule. n must be even.
     */
    public static double compositeSimpson(DoubleUnaryOperator f, double a, double b, int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be positive");
        if ((n & 1) == 1) throw new IllegalArgumentException("n must be even for Simpson's 1/3 rule");
        double h = (b - a) / n;
        double s = f.applyAsDouble(a) + f.applyAsDouble(b);
        for (int i = 1; i < n; i += 2) {
            s += 4.0 * f.applyAsDouble(a + i * h);
        }
        for (int i = 2; i < n; i += 2) {
            s += 2.0 * f.applyAsDouble(a + i * h);
        }
        return s * (h / 3.0);
    }

    /**
     * Adaptive Simpson's rule (recursive) with tolerance eps and maximum recursion depth.
     */
    public static double adaptiveSimpson(DoubleUnaryOperator f, double a, double b, double eps, int maxRecursionDepth) {
        double fa = f.applyAsDouble(a);
        double fb = f.applyAsDouble(b);
        double c = 0.5 * (a + b);
        double fc = f.applyAsDouble(c);
        double initial = simpson(fa, fb, fc, a, b);
        return adaptiveSimpsonRecursive(f, a, b, eps, initial, fa, fb, fc, maxRecursionDepth);
    }

    private static double simpson(double fa, double fb, double fc, double a, double b) {
        return (b - a) * (fa + 4.0 * fc + fb) / 6.0;
    }

    private static double adaptiveSimpsonRecursive(DoubleUnaryOperator f, double a, double b, double eps,
                                                   double whole, double fa, double fb, double fc, int depth) {
        double c = 0.5 * (a + b);
        double d = 0.5 * (a + c);
        double e = 0.5 * (c + b);
        double fd = f.applyAsDouble(d);
        double fe = f.applyAsDouble(e);
        double left = simpson(fa, fc, fd, a, c);
        double right = simpson(fc, fb, fe, c, b);
        double delta = left + right - whole;
        if (depth <= 0) {
            return left + right + delta / 15.0; // return best effort
        }
        if (Math.abs(delta) <= 15.0 * eps) {
            return left + right + delta / 15.0; // Richardson extrapolation
        }
        return adaptiveSimpsonRecursive(f, a, c, eps / 2.0, left, fa, fc, fd, depth - 1)
                + adaptiveSimpsonRecursive(f, c, b, eps / 2.0, right, fc, fb, fe, depth - 1);
    }

    /**
     * Gauss-Kronrod 7-15 quadrature over [a,b].
     * Returns a two-element array: [kronrodEstimate, abs(kronrod - gauss)] (error estimate).
     *
     * Implementation uses the standard 15-point Kronrod abscissae/weights and the 7-point Gauss subset.
     */
    public static double[] gaussKronrod15(DoubleUnaryOperator f, double a, double b) {
        // Kronrod nodes (abscissae) in increasing order
        final double[] x = {
                -0.9914553711208126,
                -0.9491079123427585,
                -0.8648644233597691,
                -0.7415311855993945,
                -0.5860872354676911,
                -0.4058451513773972,
                -0.2077849550078985,
                0.0,
                0.2077849550078985,
                0.4058451513773972,
                0.5860872354676911,
                0.7415311855993945,
                0.8648644233597691,
                0.9491079123427585,
                0.9914553711208126
        };

        final double[] wk = {
                0.02293532201052922,
                0.06309209262997855,
                0.1047900103222502,
                0.1406532597155259,
                0.1690047266392679,
                0.1903505780647854,
                0.2044329400752989,
                0.2094821410847278,
                0.2044329400752989,
                0.1903505780647854,
                0.1690047266392679,
                0.1406532597155259,
                0.1047900103222502,
                0.06309209262997855,
                0.02293532201052922
        };

        // Gauss-7 weights placed at the Kronrod node positions (zeros elsewhere)
        final double[] wg = new double[15];
        wg[1] = 0.1294849661688697;   // x = -0.9491079123427585
        wg[3] = 0.2797053914892766;   // x = -0.7415311855993945
        wg[5] = 0.3818300505051189;   // x = -0.4058451513773972
        wg[7] = 0.4179591836734694;   // x = 0.0
        wg[9] = 0.3818300505051189;   // x = 0.4058451513773972
        wg[11] = 0.2797053914892766;  // x = 0.7415311855993945
        wg[13] = 0.1294849661688697;  // x = 0.9491079123427585

        double halfLength = 0.5 * (b - a);
        double center = 0.5 * (a + b);

        double kronrodSum = 0.0;
        double gaussSum = 0.0;

        for (int i = 0; i < x.length; ++i) {
            double t = center + halfLength * x[i];
            double fx = f.applyAsDouble(t);
            kronrodSum += wk[i] * fx;
            if (wg[i] != 0.0) gaussSum += wg[i] * fx;
        }

        double kronrod = halfLength * kronrodSum;
        double gauss = halfLength * gaussSum;
        double err = Math.abs(kronrod - gauss);
        return new double[]{kronrod, err};
    }
}
