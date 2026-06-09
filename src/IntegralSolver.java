public class IntegralSolver {
    public String definiteIntegral(double lowerBound, double upperBound, String contents, String type) {
        double c = 1;
        double k = 1;
        type = type.toLowerCase();
        contents = IntegralExpressionParser.normalize(contents);
        int n = 1;

        if (lowerBound == upperBound) {
            return "Output: 0";
        }

        if (type.equals("c")) {
            double numConstant = Double.parseDouble(contents);
            double constantSum = numConstant * upperBound - numConstant * lowerBound;
            return "Output: " + constantSum;
        }

        if (type.equals("e")) {
            if (contents.matches("[-]?\\d*\\.?\\d*e\\^\\([-]?\\d*\\.?\\d*x\\)")) {
                int eIndex = contents.indexOf("e");
                if (eIndex > 0) {
                    String coeffStr = contents.substring(0, eIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                    else c = 1;
                } else {
                    c = 1;
                }

                k = IntegralExpressionParser.parseInnerCoefficientForExponential(contents);
                double newCoeff = c / k;
                upperBound = newCoeff * Math.exp(k * upperBound);
                lowerBound = newCoeff * Math.exp(k * lowerBound);
                double result = upperBound - lowerBound;
                return "Output: " + result;
            }
        }

        if (type.equals("t")) {
            if (contents.equals("cos(x)")) {
                upperBound = Math.sin(upperBound);
                lowerBound = Math.sin(lowerBound);
                return "Output: " + (upperBound - lowerBound);
            }
            if (contents.equals("sin(x)")) {
                upperBound = -Math.cos(upperBound);
                lowerBound = -Math.cos(lowerBound);
                return "Output: " + (upperBound - lowerBound);
            }
            if (contents.equals("tan(x)")) {
                upperBound = -Math.log(Math.abs(Math.cos(upperBound)));
                lowerBound = -Math.log(Math.abs(Math.cos(lowerBound)));
                return "Output: " + (upperBound - lowerBound);
            }

            if (contents.matches("[-]?\\d*\\.?\\d*cos\\([-]?\\d*\\.?\\d*x\\)")) {
                int cIndex = contents.indexOf("c");
                if (cIndex > 0) {
                    String coeffStr = contents.substring(0, cIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                k = IntegralExpressionParser.parseInnerCoefficientForTrig(contents);
                double newCoeff = c / k;
                upperBound = newCoeff * Math.sin(k * upperBound);
                lowerBound = newCoeff * Math.sin(k * lowerBound);
                return "Output: " + (upperBound - lowerBound);
            }

            if (contents.matches("[-]?\\d*\\.?\\d*sin\\([-]?\\d*\\.?\\d*x\\)")) {
                int sIndex = contents.indexOf("s");
                if (sIndex > 0) {
                    String coeffStr = contents.substring(0, sIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                k = IntegralExpressionParser.parseInnerCoefficientForTrig(contents);
                double newCoeff = -c / k;
                upperBound = newCoeff * Math.cos(k * upperBound);
                lowerBound = newCoeff * Math.cos(k * lowerBound);
                return "Output: " + (upperBound - lowerBound);
            }

            if (contents.matches("[-]?\\d*\\.?\\d*tan\\([-]?\\d*\\.?\\d*x\\)")) {
                int tIndex = contents.indexOf("t");
                if (tIndex > 0) {
                    String coeffStr = contents.substring(0, tIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                k = IntegralExpressionParser.parseInnerCoefficientForTrig(contents);
                double newCoeff = -c / k;
                upperBound = newCoeff * Math.log(Math.abs(Math.cos(k * upperBound)));
                lowerBound = newCoeff * Math.log(Math.abs(Math.cos(k * lowerBound)));
                return "Output: " + (upperBound - lowerBound);
            }
            return "Output: Invalid trig input.";
        }

        if (type.equals("l")) {
            if (contents.matches("[-]?\\d*\\.?\\d*ln\\([-]?\\d*\\.?\\d*x\\)")) {
                int lIndex = contents.indexOf("l");
                if (lIndex > 0) {
                    String coeffStr = contents.substring(0, lIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                k = IntegralExpressionParser.parseInnerCoefficientForLogarithm(contents);
                upperBound = c * (upperBound * Math.log(k * upperBound) - upperBound);
                lowerBound = c * (lowerBound * Math.log(k * lowerBound) - lowerBound);
                double result = upperBound - lowerBound;
                return "Output: " + result;
            }
            return "Output: Unsupported logarithmic input.";
        }

        if (type.equals("p")) {
            if (contents.equals("x")) {
                double upperVal = 0.5 * Math.pow(upperBound, 2);
                double lowerVal = 0.5 * Math.pow(lowerBound, 2);
                return "Output: " + (upperVal - lowerVal);
            }

            if (contents.equals("x^(-1)")) {
                double upperVal = Math.log(Math.abs(upperBound));
                double lowerVal = Math.log(Math.abs(lowerBound));
                return "Output: " + (upperVal - lowerVal);
            }

            if (contents.matches("[-]?\\d*\\.?\\d*x\\^\\([-]?\\d+\\)")) {
                int pIndex = contents.indexOf("x");
                if (pIndex > 0) {
                    String coeffStr = contents.substring(0, pIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                n = IntegralExpressionParser.parsePolynomialPower(contents);
                int newPower = n + 1;
                double newCoeff = c / newPower;
                double upperVal = newCoeff * Math.pow(upperBound, newPower);
                double lowerVal = newCoeff * Math.pow(lowerBound, newPower);
                return "Output: " + (upperVal - lowerVal);
            }
            return "Output: Unsupported polynomial input.";
        }

        return "Output: Invalid. Please try again.";
    }

    public String indefiniteIntegral(String contents, String type) {
        double c = 1.0;
        double k = 1.0;
        type = type.toLowerCase();
        contents = IntegralExpressionParser.normalize(contents);
        int n = 1;

        if (type.equals("e")) {
            if (contents.equals("e^x")) {
                return "Output: e^x + C";
            }
            if (contents.matches("[-]?\\d*\\.?\\d*e\\^\\([-]?\\d*\\.?\\d*x\\)")) {
                int eIndex = contents.indexOf("e");
                if (eIndex > 0) {
                    String coeffStr = contents.substring(0, eIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                k = IntegralExpressionParser.parseInnerCoefficientForExponential(contents);
                double newCoeff = c / k;
                return "Output: " + newCoeff + "e^(" + k + "x) + C";
            }
        }

        if (type.equals("c")) {
            return "Output: " + contents + "x + C";
        }

        if (type.equals("t")) {
            if (contents.equals("cos(x)")) return "Output: sin(x) + C";
            if (contents.equals("sin(x)")) return "Output: -cos(x) + C";
            if (contents.equals("tan(x)")) return "Output: -ln|cos(x)| + C";

            if (contents.matches("[-]?\\d*\\.?\\d*cos\\([-]?\\d*\\.?\\d*x\\)")) {
                int cIndex = contents.indexOf("c");
                if (cIndex > 0) {
                    String coeffStr = contents.substring(0, cIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                k = IntegralExpressionParser.parseInnerCoefficientForLogarithm(contents);
                return "Output: " + (c / k) + "sin(" + k + "x) + C";
            }

            if (contents.matches("[-]?\\d*\\.?\\d*sin\\([-]?\\d*\\.?\\d*x\\)")) {
                int sIndex = contents.indexOf("s");
                if (sIndex > 0) {
                    String coeffStr = contents.substring(0, sIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                k = IntegralExpressionParser.parseInnerCoefficientForLogarithm(contents);
                return "Output: " + (-c / k) + "cos(" + k + "x) + C";
            }

            if (contents.matches("[-]?\\d*\\.?\\d*tan\\([-]?\\d*\\.?\\d*x\\)")) {
                int tIndex = contents.indexOf("t");
                if (tIndex > 0) {
                    String coeffStr = contents.substring(0, tIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                k = IntegralExpressionParser.parseInnerCoefficientForLogarithm(contents);
                return "Output: " + (-c / k) + "ln|cos(" + k + "x)| + C";
            }
        }

        if (type.equals("l")) {
            if (contents.matches("[-]?\\d*\\.?\\d*ln\\([-]?\\d*\\.?\\d*x\\)")) {
                int lIndex = contents.indexOf("l");
                if (lIndex > 0) {
                    String coeffStr = contents.substring(0, lIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                k = IntegralExpressionParser.parseInnerCoefficientForLogarithm(contents);
                return "Output: " + c + "xln(" + k + ") - " + c + "x + C";
            }
            if (contents.equals("ln(x)")) {
                return "Output: xln(x) - x + C";
            }
            return "Output: Unsupported logarithmic input.";
        }

        if (type.equals("p")) {
            if (contents.equals("x")) {
                return "Output: (1/2)x^2 + C";
            }
            if (contents.equals("x^(-1)")) {
                return "Output: ln|x| + C";
            }
            if (contents.matches("[-]?\\d*\\.?\\d*x\\^\\([-]?\\d+\\)")) {
                int pIndex = contents.indexOf("x");
                if (pIndex > 0) {
                    String coeffStr = contents.substring(0, pIndex);
                    if (coeffStr.equals("-")) c = -1;
                    else if (!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                n = IntegralExpressionParser.parsePolynomialPower(contents);
                int newPower = n + 1;
                double newCoeff = c / newPower;
                String coeffOut = (newCoeff == (int) newCoeff) ? String.valueOf((int) newCoeff) : String.valueOf(newCoeff);
                return "Output: " + coeffOut + "x^" + newPower + " + C";
            }
            return "Output: Unsupported polynomial input.";
        }

        return "Output: Invalid. Please try again.";
    }

    public double convertupperBound(String upperBound) {
        return IntegralExpressionParser.parseBound(upperBound);
    }

    public double convertlowerBound(String lowerBound) {
        return IntegralExpressionParser.parseBound(lowerBound);
    }
}
