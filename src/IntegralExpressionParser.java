public class IntegralExpressionParser {
    public static String normalize(String contents) {
        return contents.replace(" ", "");
    }

    public static double parseOuterCoefficient(String contents, char delimiter) {
        double c = 1;
        int index = contents.indexOf(delimiter);
        if (index > 0) {
            String coeffStr = contents.substring(0, index);
            if (coeffStr.equals("-")) {
                c = -1;
            } else if (!coeffStr.equals("")) {
                c = Double.parseDouble(coeffStr);
            }
        }
        return c;
    }

    public static double parseInnerCoefficientForExponential(String contents) {
        int start = contents.indexOf("(") + 1;
        int end = contents.indexOf("x");
        String kStr = contents.substring(start, end);
        if (kStr.equals("") || kStr.equals("+")) {
            return 1;
        }
        if (kStr.equals("-")) {
            return -1;
        }
        return Double.parseDouble(kStr);
    }

    public static double parseInnerCoefficientForTrig(String contents) {
        int start = contents.indexOf("(");
        int end = contents.indexOf("x");
        String kString = contents.substring(start, end);
        if (kString.equals("") || kString.equals("+")) {
            return 1;
        }
        if (kString.equals("-")) {
            return -1;
        }
        return Double.parseDouble(kString);
    }

    public static double parseInnerCoefficientForLogarithm(String contents) {
        int start = contents.indexOf("(") + 1;
        int end = contents.indexOf("x");
        String kString = contents.substring(start, end);
        if (kString.equals("") || kString.equals("+")) {
            return 1;
        }
        if (kString.equals("-")) {
            return -1;
        }
        return Double.parseDouble(kString);
    }

    public static int parsePolynomialPower(String contents) {
        int start = contents.indexOf("(") + 1;
        int end = contents.indexOf(")");
        return Integer.parseInt(contents.substring(start, end));
    }

    public static double parseBound(String bound) {
        String lowerBound = bound.toLowerCase();
        if (lowerBound.equals("pi")) {
            return Math.PI;
        }
        if (lowerBound.equals("-pi")) {
            return -Math.PI;
        }
        if (lowerBound.matches("[-]?\\d*\\.?\\d+pi")) {
            double c = 1;
            int piIndex = lowerBound.indexOf("pi");
            if (piIndex > 0) {
                String coeffStr = lowerBound.substring(0, piIndex);
                if (coeffStr.equals("-")) {
                    c = -1;
                } else if (!coeffStr.equals("")) {
                    c = Double.parseDouble(coeffStr);
                }
            }
            if (piIndex == 0) {
                c = 1;
            }
            return c * Math.PI;
        }
        if (lowerBound.matches("[-]?\\d*\\.?\\d+")) {
            return Double.parseDouble(lowerBound);
        }
        throw new IllegalArgumentException("Invalid upper bound: " + bound);
    }
}
