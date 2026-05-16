import java.util.Scanner;

public class IntegralSolver {
    //DEFINITE INTEGRAL CALCULATION
    public String definiteIntegral(double lowerBound, double upperBound, String contents, String type)
    {
        //INITIALIZED CONSTANT VARIABLES
        double c = 1;
        double k = 1;
        type = type.toLowerCase();
        contents = contents.replace(" ", "");
        
        //N FOR POLYNOMIAL INTEGRATION
        int n = 1;
        
        //IF EQUAL BOUNDS
        if(lowerBound == upperBound)
        {
            return "Output: 0";
        }
        
        //CONSTANT CALCULATION 
        if(type.equals("c"))
        {
            double numConstant = Double.parseDouble(contents);
            double constantSum = numConstant*upperBound - numConstant*lowerBound;
            return "Output: " + constantSum;
        }
        
        //EXPONENTIAL CALCULATION
        if(type.equals("e"))
        {
            //GENERAL CASE
            if(contents.matches("[-]?\\d*\\.?\\d*e\\^\\([-]?\\d*\\.?\\d*x\\)"))
            {
                int eIndex = contents.indexOf("e");
                //EXTRACT OUTER COEFFICIENT (C)
                if(eIndex > 0)
                {
                    String coeffStr = contents.substring(0, eIndex);
                    if(coeffStr.equals("-")) c = -1;
                    else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                    else c = 1;
                }
                else c = 1;

                //EXTRACT INNER COEFFICIENT (K)
                int start = contents.indexOf("(") + 1;
                int end = contents.indexOf("x");
                String kStr = contents.substring(start, end);

                if(kStr.equals("") || kStr.equals("+")) k = 1;
                else if(kStr.equals("-")) k = -1;
                else k = Double.parseDouble(kStr);

                double newCoeff = c / k;
                upperBound = newCoeff * Math.exp(k * upperBound);
                lowerBound = newCoeff * Math.exp(k * lowerBound);
                double result = upperBound - lowerBound;
                
                //RETURN
                return "Output: " + result;
        }

        }

        //TRIG-BASED CALCULATION
        if(type.equals("t"))
        {
            //STANDARD TRIG FUNCTIONS
            if(contents.equals("cos(x)"))
            {
                upperBound = Math.sin(upperBound);
                lowerBound = Math.sin(lowerBound);
                return "Output: " + (upperBound - lowerBound);    
            }
            if(contents.equals("sin(x)"))
            {
                upperBound = -Math.cos(upperBound);
                lowerBound = -Math.cos(lowerBound);
                return "Output: " + (upperBound - lowerBound);    
            }
            if(contents.equals("tan(x)"))
            {
                upperBound = -Math.log(Math.abs(Math.cos(upperBound)));
                lowerBound = -Math.log(Math.abs(Math.cos(lowerBound)));
                return "Output: " + (upperBound - lowerBound);    
            }

            //GENERAL CASE a*cos(kx)
            if(contents.matches("[-]?\\d*\\.?\\d*cos\\([-]?\\d*\\.?\\d*x\\)"))
            {
                int cIndex = contents.indexOf("c");
                if(cIndex>0)
                {
                    String coeffStr = contents.substring(0,cIndex);
                    if(coeffStr.equals("-")) c = -1;
                    else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                int start = contents.indexOf("(");
                int end = contents.indexOf("x");
                String kString = contents.substring(start,end);

                if(kString.equals("")||kString.equals("+")) k=1;
                else if(kString.equals("-")) k = -1;
                else k = Double.parseDouble(kString);

                double newCoeff = c/k;
                upperBound = newCoeff * Math.sin(k*upperBound);
                lowerBound = newCoeff * Math.sin(k*lowerBound);
                return "Output: " + (upperBound-lowerBound);
            }

            //GENERAL CASE a*sin(kx)
            if(contents.matches("[-]?\\d*\\.?\\d*sin\\([-]?\\d*\\.?\\d*x\\)"))
            {
                int sIndex = contents.indexOf("s");
                if(sIndex>0)
                {
                    String coeffStr = contents.substring(0,sIndex);
                    if(coeffStr.equals("-")) c = -1;
                    else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                int start = contents.indexOf("(");
                int end = contents.indexOf("x");
                String kString = contents.substring(start,end);

                if(kString.equals("")||kString.equals("+")) k=1;
                else if(kString.equals("-")) k = -1;
                else k = Double.parseDouble(kString);

                double newCoeff = -c/k;
                upperBound = newCoeff * Math.cos(k*upperBound);
                lowerBound = newCoeff * Math.cos(k*lowerBound);
                return "Output: " + (upperBound-lowerBound);
            }

            //GENERAL CASE a*tan(kx)
            if(contents.matches("[-]?\\d*\\.?\\d*tan\\([-]?\\d*\\.?\\d*x\\)"))
            {
                int tIndex = contents.indexOf("t");
                if(tIndex>0)
                {
                    String coeffStr = contents.substring(0,tIndex);
                    if(coeffStr.equals("-")) c = -1;
                    else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                int start = contents.indexOf("(");
                int end = contents.indexOf("x");
                String kString = contents.substring(start,end);

                if(kString.equals("")||kString.equals("+")) k=1;
                else if(kString.equals("-")) k = -1;
                else k = Double.parseDouble(kString);

                double newCoeff = -c/k;
                upperBound = newCoeff * Math.log(Math.abs(Math.cos(k*upperBound)));
                lowerBound = newCoeff * Math.log(Math.abs(Math.cos(k*lowerBound)));
                return "Output: " + (upperBound-lowerBound);
            }
            return "Output: Invalid trig input.";
        }
        //LOGARITHMIC CALCULATION
        if(type.equals("l"))
        {
            if(contents.matches("[-]?\\d*\\.?\\d*ln\\([-]?\\d*\\.?\\d*x\\)"))
            {
                int lIndex = contents.indexOf("l");
                //EXTRACT OUTER COEFFICIENT (C)
                if(lIndex > 0)
                    {
                        String coeffStr = contents.substring(0,lIndex);
                        if(coeffStr.equals("-")) c =-1;
                        else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                    }
                //EXTRACT INNER COEFFICIENT (K)
                int start = contents.indexOf("(") + 1;
                int end = contents.indexOf("x");
                String kString = contents.substring(start,end);
                if(kString.equals("")|| kString.equals("+")) k = 1;
                else if(kString.equals("-")) k = -1;
                else k = Double.parseDouble(kString);
                //RETURN
                upperBound = c * (upperBound * Math.log(k*upperBound)-upperBound);
                lowerBound = c * (lowerBound * Math.log(k*lowerBound)-lowerBound);
                double result = upperBound - lowerBound;
                //RETURN
                return "Output: " + result;
            }
            return "Output: Unsupported logarithmic input.";
        }

        //POLYNOMIAL CALCULATION
        if(type.equals("p"))
        {
            //CHECK FOR x
            if(contents.equals("x"))
            {
                double upperVal = 0.5 * Math.pow(upperBound,2);
                double lowerVal = 0.5 * Math.pow(lowerBound,2);
                return "Output: " + (upperVal - lowerVal);
            }

            //CHECK FOR x^(-1)
            if(contents.equals("x^(-1)"))
            {
                double upperVal = Math.log(Math.abs(upperBound));
                double lowerVal = Math.log(Math.abs(lowerBound));
                return "Output: " + (upperVal - lowerVal);
            }

            //GENERAL CASE
            if(contents.matches("[-]?\\d*\\.?\\d*x\\^\\([-]?\\d+\\)"))
            {
                int pIndex = contents.indexOf("x");
                if(pIndex > 0)
                {
                    String coeffStr = contents.substring(0,pIndex);
                    if(coeffStr.equals("-")) c = -1;
                    else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }

                int start = contents.indexOf("(") + 1;
                int end = contents.indexOf(")");
                n = Integer.parseInt(contents.substring(start, end));

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
    //INDEFINITE INTEGRAL CALCULATION
    public String indefiniteIntegral(String contents, String type)
    {
        //INITIALIZED CONSTANT VARIABLES
        double c = 1.0;
        double k = 1.0;
        type = type.toLowerCase();
        contents = contents.replace(" ", "");

        //N FOR POLYNOMIAL INTEGRATION
        int n = 1;
        
        //EXPONENTIAL CALCULATION
        if(type.equals("e"))
        {
            //CHECK FOR e^x
            if(contents.equals("e^x"))
            {
                return "Output: e^x + C";
            }
            //GENERAL CASE
            if(contents.matches("[-]?\\d*\\.?\\d*e\\^\\([-]?\\d*\\.?\\d*x\\)"))
            {
                int eIndex = contents.indexOf("e");
                //EXTRACT OUTER COEFFICIENT (C)
                if(eIndex > 0)
                {
                    String coeffStr = contents.substring(0, eIndex);
                    if(coeffStr.equals("-")) c = -1;
                    else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }

            //EXTRACT INNER COEFFICIENT (K)
            int start = contents.indexOf("(") + 1;
            int end = contents.indexOf("x");
            String kStr = contents.substring(start, end);

            if(kStr.equals("") || kStr.equals("+")) k = 1;
            else if(kStr.equals("-")) k = -1;
            else k = Double.parseDouble(kStr);

            double newCoeff = c / k;
            return "Output: " + newCoeff + "e^(" + k + "x) + C";
            }

        }

        //CONSTANT CALCULATION
        if(type.equals("c"))
        {
            return "Output: " + contents + "x + C";     
        }

        //TRIG BASED CALCULATION
        if(type.equals("t"))
        {
            //STANDARD TRIG FUNCTIONS
            if(contents.equals("cos(x)")) return "Output: sin(x) + C";
            if(contents.equals("sin(x)")) return "Output: -cos(x) + C";
            if(contents.equals("tan(x)")) return "Output: -ln|cos(x)| + C";

            //GENERAL CASE: a*cos(kx)
            if(contents.matches("[-]?\\d*\\.?\\d*cos\\([-]?\\d*\\.?\\d*x\\)"))
            {
                int cIndex = contents.indexOf("c");
                //OUTER COEFFICIENT (c)
                if(cIndex > 0)
                {
                    String coeffStr = contents.substring(0, cIndex);
                    if(coeffStr.equals("-")) c = -1;
                    else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                //INNER COEFFICIENT (k)
                int start = contents.indexOf("(") + 1;
                int end = contents.indexOf("x");
                String kString = contents.substring(start, end);
                if(kString.equals("") || kString.equals("+")) k = 1;
                else if(kString.equals("-")) k = -1;
                else k = Double.parseDouble(kString);
                //CHAIN RULE
                return "Output: " + (c / k) + "sin(" + k + "x) + C";
            }

            //GENERAL CASE: a*sin(kx)
            if(contents.matches("[-]?\\d*\\.?\\d*sin\\([-]?\\d*\\.?\\d*x\\)"))
            {
                int sIndex = contents.indexOf("s");
                //OUTER COEFFICIENT (c)
                if(sIndex > 0)
                {
                    String coeffStr = contents.substring(0, sIndex);
                    if(coeffStr.equals("-")) c = -1;
                    else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                //INNER COEFFICIENT (k)
                int start = contents.indexOf("(") + 1;
                int end = contents.indexOf("x");
                String kString = contents.substring(start, end);
                if(kString.equals("") || kString.equals("+")) k = 1;
                else if(kString.equals("-")) k = -1;
                else k = Double.parseDouble(kString);
                return "Output: " + (-c / k) + "cos(" + k + "x) + C";
            }

            //GENERAL CASE: a*tan(kx)
            if(contents.matches("[-]?\\d*\\.?\\d*tan\\([-]?\\d*\\.?\\d*x\\)"))
            {
                int tIndex = contents.indexOf("t");
                //OUTER COEFFICIENT (c)
                if(tIndex > 0)
                {
                    String coeffStr = contents.substring(0, tIndex);
                    if(coeffStr.equals("-")) c = -1;
                    else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                //INNER COEFFICIENT (k)
                int start = contents.indexOf("(") + 1;
                int end = contents.indexOf("x");
                String kString = contents.substring(start, end);
                if(kString.equals("") || kString.equals("+")) k = 1;
                else if(kString.equals("-")) k = -1;
                else k = Double.parseDouble(kString);
                return "Output: " + (-c / k) + "ln|cos(" + k + "x)| + C";
            }
        }   
        
        //LOGARITHMIC CALCULATION
        if(type.equals("l"))
        {
            if(contents.matches("[-]?\\d*\\.?\\d*ln\\([-]?\\d*\\.?\\d*x\\)"))
            {
                int lIndex = contents.indexOf("l");
                //EXTRACT OUTER COEFFICIENT (C)
                if(lIndex > 0)
                {
                    String coeffStr = contents.substring(0,lIndex);
                    if(coeffStr.equals("-")) c =-1;
                    else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
                //EXTRACT INNER COEFFICIENT (K)
                int start = contents.indexOf("(") + 1;
                int end = contents.indexOf("x");
                String kString = contents.substring(start,end);
                if(kString.equals("")|| kString.equals("+")) k = 1;
                else if(kString.equals("-")) k = -1;
                else k = Double.parseDouble(kString);
                //RETURN
                return "Output: " + c + "xln(" + k + ") - " + c + "x + C"; 
            }
            if(contents.equals("ln(x)"))
            {
                return "Output: xln(x) - x + C";
            }
            return "Output: Unsupported logarithmic input.";
        }

        //POLYNOMIAL CALCULATION
        if(type.equals("p"))
        {
            //CHECK FOR X, X^(-1)
            if(contents.equals("x"))
            {
                return "Output: (1/2)x^2 + C";
            }
            if(contents.equals("x^(-1)"))
            {
                return "Output: ln|x| + C";
            }
            //GENERAL CASE
            if(contents.matches("[-]?\\d*\\.?\\d*x\\^\\([-]?\\d+\\)"))
            {
                int pIndex = contents.indexOf("x");
                //EXTRACT OUTER COEFFICIENT (C)
                if(pIndex > 0)
                {
                    String coeffStr = contents.substring(0,pIndex);
                    if(coeffStr.equals("-")) c = -1;
                    else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);
                }
            //EXTRACT INNER COEFFICIENT
            int start = contents.indexOf("(") + 1;
            int end = contents.indexOf(")");
            n = Integer.parseInt(contents.substring(start, end));
            //POWER RULE
            int newPower = n+1;
            double newCoeff = c/newPower;
            //RETURN
            String coeffOut = (newCoeff == (int)newCoeff)
            ? String.valueOf((int)newCoeff)
            : String.valueOf(newCoeff);

            return "Output: " + coeffOut + "x^" + newPower + " + C";
            }
            return "Output: Unsupported polynomial input.";
        }

        return "Output: Invalid. Please try again.";
    }
    //CHECK UPPERBOUND FOR PI
    public double convertupperBound(String upperBound)
    {
        if(upperBound.toLowerCase().equals("pi"))
        {
            return Math.PI;
        }
        if(upperBound.toLowerCase().equals("-pi"))
        {
            return -Math.PI;
        }
        else if(upperBound.matches("[-]?\\d*\\.?\\d+pi"))
        {
            double c = 1;
            int piIndex = upperBound.indexOf("pi");
            if(piIndex > 0)
            {
                String coeffStr = upperBound.substring(0, piIndex);
                if(coeffStr.equals("-")) c = -1;
                else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);    
            }
            if(piIndex == 0) c = 1;
            return c * Math.PI;
        }
        else if(upperBound.matches("[-]?\\d*\\.?\\d+"))
        {
            return Double.parseDouble(upperBound);
        }
        else
        {
            throw new IllegalArgumentException("Invalid upper bound: " + upperBound);
        }    
    }
    //CHECK LOWERBOUND FOR PI
    public double convertlowerBound(String lowerBound)
    {
        if(lowerBound.toLowerCase().equals("pi"))
        {
            return Math.PI;
        }
        if(lowerBound.toLowerCase().equals("-pi"))
        {
            return -Math.PI;
        }
        else if(lowerBound.matches("[-]?\\d*\\.?\\d+pi"))
        {
            double c = 1;
            int piIndex = lowerBound.indexOf("pi");
            if(piIndex > 0)
            {
                String coeffStr = lowerBound.substring(0, piIndex);
                if(coeffStr.equals("-")) c = -1;
                else if(!coeffStr.equals("")) c = Double.parseDouble(coeffStr);    
            }
            if(piIndex == 0) c = 1;
            return c * Math.PI;
        }
        else if(lowerBound.matches("[-]?\\d*\\.?\\d+"))
        {
            return Double.parseDouble(lowerBound);
        }
        else
        {
            throw new IllegalArgumentException("Invalid upper bound: " + lowerBound);
        }
    }
    //MAIN       
    public static void main(String[] args) throws Exception 
    {
    //INITIALIZATION
    Scanner scan = new Scanner(System.in);
    IntegralSolver solver = new IntegralSolver();
    
    System.out.print("Hello! Welcome to Integral Solver!\n----------------------------------------------------------------\nEnter \'D\' for definite integral or \'I\' for indefinite integral: ");
    String deforIndef = scan.nextLine();
    deforIndef = deforIndef.toLowerCase();
    
    //DEFINITE INTEGRAL RESULT
    if(deforIndef.equals("d"))
    {
        System.out.print("Is your equation Exponential (e), Trig-based (t), Logarithmic (l), Polynomial (p), or a Constant (c) ?: ");
        String type = scan.nextLine();
        if(!type.toLowerCase().equals("e")&&!type.toLowerCase().equals("t")&&!type.toLowerCase().equals("l")&&!type.toLowerCase().equals("p")&&!type.toLowerCase().equals("c"))
        {
            System.out.println("Sorry! That's invalid! Please try again.");
        }
        else
        {
            System.out.print("Enter lower bound: ");
            String lowerBound = scan.nextLine(); 
            System.out.print("Enter upper bound: ");
            String upperBound = scan.nextLine();
            System.out.print("Enter the integrand with respect to \'x\': ");
            String contents = scan.nextLine();
            System.out.println("----------------------------------------------------------------\nInput: Definite integral of " + contents + " dx, from " + lowerBound + " to " + upperBound);
            String result = solver.definiteIntegral(solver.convertupperBound(lowerBound), solver.convertlowerBound(upperBound), contents,type);
            System.out.println(result);
            System.out.println("----------------------------------------------------------------\nThank you for using Integral Solver!");
        }
    }
    //INDEFINITE INTEGRAL RESULT
    else if(deforIndef.equals("i"))
    {
        System.out.print("Is your equation Exponential (e), Trig-based (t), Logarithmic (l), Polynomial (p), or a Constant (c) ?: ");
        String type = scan.nextLine();
        if(!type.toLowerCase().equals("e")&&!type.toLowerCase().equals("t")&&!type.toLowerCase().equals("l")&&!type.toLowerCase().equals("p")&&!type.toLowerCase().equals("c"))
        {
            System.out.println("Sorry! That's invalid! Please try again.");
        }
        else
        {
            System.out.print("Enter the integrand with respect to \'x\': ");
            String contents = scan.nextLine();
            System.out.println("----------------------------------------------------------------\nInput: Indefinite integral of " + contents + " dx");
            String result = solver.indefiniteIntegral(contents,type);
            System.out.println(result);
            System.out.println("----------------------------------------------------------------\nThank you for using Integral Solver!");
        }
    }
    else
    {
        System.out.println("Sorry! That's invalid. Please try again.");
    }
    scan.close();
    }
}