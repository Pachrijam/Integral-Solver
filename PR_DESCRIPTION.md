# Integral-Solver: quadrature additions

This pull request adds numerical quadrature implementations in Java:

- Composite trapezoid rule
- Composite Simpson's 1/3 rule
- Adaptive Simpson's rule (recursive)
- Gauss-Kronrod 7/15 estimator (kronrod=15, gauss=7) with an error estimate

Files added:
- src/main/java/com/pachrijam/integrals/Integrators.java
- src/main/java/com/pachrijam/integrals/IntegratorsDemo.java

How to run the demo

- Compile with your usual build tool (javac/IDE/maven/gradle). From the repo root with javac:

  javac -d out src/main/java/com/pachrijam/integrals/*.java
  java -cp out com.pachrijam.integrals.IntegratorsDemo

Notes

- The Gauss-Kronrod implementation uses the canonical 15-point Kronrod rule with the 7-point Gauss subset for an internal error estimate. No external libraries are required.
- The demo includes smoke checks that assert correctness for the integral of sin(x) from 0..pi.

If you want, I can:
- Add JUnit tests and a GitHub Actions workflow to run them.
- Adjust package names to match your project structure.
- Split these utilities into a package that integrates with your existing codebase (if you tell me the package layout).
