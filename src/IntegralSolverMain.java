import java.util.Scanner;

public class IntegralSolverMain {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        IntegralSolver solver = new IntegralSolver();

        System.out.print("Hello! Welcome to Integral Solver!\n----------------------------------------------------------------\nEnter 'D' for definite integral or 'I' for indefinite integral: ");
        String deforIndef = scan.nextLine();
        deforIndef = deforIndef.toLowerCase();

        if (deforIndef.equals("d")) {
            System.out.print("Is your equation Exponential (e), Trig-based (t), Logarithmic (l), Polynomial (p), or a Constant (c) ?: ");
            String type = scan.nextLine();
            if (!type.toLowerCase().equals("e") && !type.toLowerCase().equals("t") && !type.toLowerCase().equals("l") && !type.toLowerCase().equals("p") && !type.toLowerCase().equals("c")) {
                System.out.println("Sorry! That's invalid! Please try again.");
            } else {
                System.out.print("Enter lower bound: ");
                String lowerBound = scan.nextLine();
                System.out.print("Enter upper bound: ");
                String upperBound = scan.nextLine();
                System.out.print("Enter the integrand with respect to 'x': ");
                String contents = scan.nextLine();
                System.out.println("----------------------------------------------------------------\nInput: Definite integral of " + contents + " dx, from " + lowerBound + " to " + upperBound);
                String result = solver.definiteIntegral(solver.convertupperBound(lowerBound), solver.convertlowerBound(upperBound), contents, type);
                System.out.println(result);
                System.out.println("----------------------------------------------------------------\nThank you for using Integral Solver!");
            }
        } else if (deforIndef.equals("i")) {
            System.out.print("Is your equation Exponential (e), Trig-based (t), Logarithmic (l), Polynomial (p), or a Constant (c) ?: ");
            String type = scan.nextLine();
            if (!type.toLowerCase().equals("e") && !type.toLowerCase().equals("t") && !type.toLowerCase().equals("l") && !type.toLowerCase().equals("p") && !type.toLowerCase().equals("c")) {
                System.out.println("Sorry! That's invalid! Please try again.");
            } else {
                System.out.print("Enter the integrand with respect to 'x': ");
                String contents = scan.nextLine();
                System.out.println("----------------------------------------------------------------\nInput: Indefinite integral of " + contents + " dx");
                String result = solver.indefiniteIntegral(contents, type);
                System.out.println(result);
                System.out.println("----------------------------------------------------------------\nThank you for using Integral Solver!");
            }
        } else {
            System.out.println("Sorry! That's invalid. Please try again.");
        }

        scan.close();
    }
}