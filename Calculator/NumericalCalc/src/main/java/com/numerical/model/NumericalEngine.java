package com.numerical.model;

import java.util.*;
import java.util.function.Function;
public class NumericalEngine {
    public static double directInterpolation(double x0, double y0, double x1, double y1, double x) {
        if (x1 == x0) throw new ArithmeticException("x0 and x1 must be different.");
        return y0 + (y1 - y0) * (x - x0) / (x1 - x0);
    }
    public static double lagrangeInterpolation(double[] xVals, double[] yVals, double x) {
        int n = xVals.length;
        double result = 0.0;
        for (int i = 0; i < n; i++) {
            double term = yVals[i];
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    term *= (x - xVals[j]) / (xVals[i] - xVals[j]);
                }
            }
            result += term;
        }
        return result;
    }

    public static String lagrangeSteps(double[] xVals, double[] yVals, double x) {
        int n = xVals.length;
        StringBuilder sb = new StringBuilder();
        sb.append("Lagrange Interpolation\n");
        sb.append("------------------------------\n");
        sb.append(String.format("Estimating f(%.4f)\n\n", x));
        double result = 0.0;
        for (int i = 0; i < n; i++) {
            double Li = 1.0;
            sb.append(String.format("L%d(x) = ", i));
            StringBuilder numStr = new StringBuilder();
            StringBuilder denStr = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    Li *= (x - xVals[j]) / (xVals[i] - xVals[j]);
                    numStr.append(String.format("(x - %.4f)", xVals[j]));
                    denStr.append(String.format("(%.4f - %.4f)", xVals[i], xVals[j]));
                }
            }
            sb.append(numStr).append(" / ").append(denStr);
            sb.append(String.format(" = %.6f\n", Li));
            double contrib = yVals[i] * Li;
            sb.append(String.format("  → y%d × L%d = %.4f × %.6f = %.6f\n\n", i, i, yVals[i], Li, contrib));
            result += contrib;
        }
        sb.append("------------------------------\n");
        sb.append(String.format("f(%.4f) ≈ %.6f", x, result));
        return sb.toString();
    }
    public static double simpsons13(Function<Double, Double> f, double a, double b, int n) {
        if (n % 2 != 0) n++;   // must be even
        double h = (b - a) / n;
        double sum = f.apply(a) + f.apply(b);
        for (int i = 1; i < n; i++) {
            double xi = a + i * h;
            sum += (i % 2 == 0 ? 2 : 4) * f.apply(xi);
        }
        return (h / 3) * sum;
    }

    public static String simpsons13Steps(Function<Double, Double> f, double a, double b, int n) {
        if (n % 2 != 0) n++;
        double h = (b - a) / n;
        StringBuilder sb = new StringBuilder();
        sb.append("Simpson's 1/3 Rule\n");
        sb.append("-----------------------------\n");
        sb.append(String.format("a=%.4f, b=%.4f, n=%d, h=%.6f\n\n", a, b, n, h));
        sb.append("x values and f(x):\n");
        double sum = 0;
        double[] coeffs = new double[n + 1];
        coeffs[0] = 1; coeffs[n] = 1;
        for (int i = 1; i < n; i++) coeffs[i] = (i % 2 == 0 ? 2 : 4);
        for (int i = 0; i <= n; i++) {
            double xi = a + i * h;
            double fx = f.apply(xi);
            sum += coeffs[i] * fx;
            sb.append(String.format("  x%d=%.4f  f(x%d)=%.6f  coeff=%d\n", i, xi, i, fx, (int) coeffs[i]));
        }
        double result = (h / 3) * sum;
        sb.append(String.format("\nResult = (h/3) × Σ = (%.6f/3) × %.6f\n", h, sum));
        sb.append(String.format("-----------------------------\n∫f(x)dx ≈ %.6f", result));
        return sb.toString();
    }
    public static double simpsons38(Function<Double, Double> f, double a, double b, int n) {
        while (n % 3 != 0) n++;
        double h = (b - a) / n;
        double sum = f.apply(a) + f.apply(b);
        for (int i = 1; i < n; i++) {
            double xi = a + i * h;
            sum += (i % 3 == 0 ? 2 : 3) * f.apply(xi);
        }
        return (3 * h / 8) * sum;
    }

    public static String simpsons38Steps(Function<Double, Double> f, double a, double b, int n) {
        while (n % 3 != 0) n++;
        double h = (b - a) / n;
        StringBuilder sb = new StringBuilder();
        sb.append("Simpson's 3/8 Rule\n");
        sb.append("------------------------------\n");
        sb.append(String.format("a=%.4f, b=%.4f, n=%d, h=%.6f\n\n", a, b, n, h));
        sb.append("x values and f(x):\n");
        double sum = 0;
        double[] coeffs = new double[n + 1];
        coeffs[0] = 1; coeffs[n] = 1;
        for (int i = 1; i < n; i++) coeffs[i] = (i % 3 == 0 ? 2 : 3);
        for (int i = 0; i <= n; i++) {
            double xi = a + i * h;
            double fx = f.apply(xi);
            sum += coeffs[i] * fx;
            sb.append(String.format("  x%d=%.4f  f(x%d)=%.6f  coeff=%d\n", i, xi, i, fx, (int) coeffs[i]));
        }
        double result = (3 * h / 8) * sum;
        sb.append(String.format("\nResult = (3h/8) × Σ = (3×%.6f/8) × %.6f\n", h, sum));
        sb.append(String.format("-----------------------------\n∫f(x)dx ≈ %.6f", result));
        return sb.toString();
    }
    public static List<double[]> eulerMethod(Function<double[], Double> f, double x0, double y0, double h, int steps) {
        List<double[]> table = new ArrayList<>();
        double x = x0, y = y0;
        table.add(new double[]{x, y});
        for (int i = 0; i < steps; i++) {
            double dy = f.apply(new double[]{x, y});
            y = y + h * dy;
            x = x + h;
            table.add(new double[]{x, y});
        }
        return table;
    }

    public static String eulerSteps(Function<double[], Double> f, double x0, double y0, double h, int steps) {
        StringBuilder sb = new StringBuilder();
        sb.append("Euler's Method\n");
        sb.append("------------------------------------------\n");
        sb.append(String.format("x₀=%.4f, y₀=%.4f, h=%.4f, steps=%d\n\n", x0, y0, h, steps));
        sb.append(String.format("%-6s %-12s %-12s %-12s\n", "Step", "x", "y", "f(x,y)"));
        sb.append("------------------------------------------\n");
        double x = x0, y = y0;
        sb.append(String.format("%-6d %-12.6f %-12.6f\n", 0, x, y));
        for (int i = 0; i < steps; i++) {
            double dy = f.apply(new double[]{x, y});
            sb.append(String.format("       f(%.4f,%.4f) = %.6f\n", x, y, dy));
            y = y + h * dy;
            x = x + h;
            sb.append(String.format("%-6d %-12.6f %-12.6f\n", i + 1, x, y));
        }
        sb.append("-----------------------------------------\n");
        sb.append(String.format("Final y(%.4f) ≈ %.6f", x, y));
        return sb.toString();
    }
    public static String errorAnalysis(double exact, double approx) {
        double absError = Math.abs(exact - approx);
        double relError = (exact != 0) ? absError / Math.abs(exact) : Double.NaN;
        double pctError = relError * 100;
        return String.format(
            "Error Analysis\n------------------------------\n" +
            "Exact Value     : %.8f\n" +
            "Approximate     : %.8f\n" +
            "Absolute Error  : %.8f\n" +
            "Relative Error  : %.8f\n" +
            "Percentage Error: %.4f%%",
            exact, approx, absError, relError, pctError);
    }
    public static Function<Double, Double> parseFunction(String expr) {
        return x -> {
            String e = expr
                .replace("x", "(" + x + ")")
                .replace("^", "**")
                .replace("sin", "§SIN§")
                .replace("cos", "§COS§")
                .replace("tan", "§TAN§")
                .replace("log", "§LOG§")
                .replace("ln",  "§LN§")
                .replace("sqrt","§SQRT§")
                .replace("exp", "§EXP§");
            return evalMath(e);
        };
    }

    public static Function<double[], Double> parseFunctionXY(String expr) {
        return args -> {
            double x = args[0], y = args[1];
            String e = expr
                .replace("y", "(" + y + ")")
                .replace("x", "(" + x + ")")
                .replace("^", "**");
            return evalMath(e);
        };
    }
    private static double evalMath(String expr) {
        expr = expr.trim();
        expr = evalStringFunctions(expr);
        return new ExprParser(expr).parse();
    }

    private static String evalStringFunctions(String expr) {
        return expr;
    }
    public static class ExprParser {
        private final String input;
        private int pos = 0;

        public ExprParser(String input) {
            this.input = input.replaceAll("\\s+", "");
        }

        public double parse() {
            double result = parseAddSub();
            if (pos < input.length()) {
                throw new RuntimeException("Unexpected character at pos " + pos + ": " + input.charAt(pos));
            }
            return result;
        }

        private double parseAddSub() {
            double left = parseMulDiv();
            while (pos < input.length()) {
                char op = input.charAt(pos);
                if (op == '+') { pos++; left += parseMulDiv(); }
                else if (op == '-') { pos++; left -= parseMulDiv(); }
                else break;
            }
            return left;
        }

        private double parseMulDiv() {
            double left = parsePower();
            while (pos < input.length()) {
                char op = input.charAt(pos);
                if (op == '*' && pos + 1 < input.length() && input.charAt(pos + 1) == '*') {
                    pos += 2; left = Math.pow(left, parsePower());
                } else if (op == '*') { pos++; left *= parsePower(); }
                else if (op == '/') { pos++; left /= parsePower(); }
                else break;
            }
            return left;
        }

        private double parsePower() {
            double base = parseUnary();
            if (pos < input.length() && input.charAt(pos) == '^') {
                pos++;
                base = Math.pow(base, parseUnary());
            }
            return base;
        }

        private double parseUnary() {
            if (pos < input.length() && input.charAt(pos) == '-') { pos++; return -parsePrimary(); }
            if (pos < input.length() && input.charAt(pos) == '+') { pos++; return parsePrimary(); }
            return parsePrimary();
        }

        private double parsePrimary() {
            if (pos < input.length() && input.charAt(pos) == '(') {
                pos++;
                double val = parseAddSub();
                if (pos < input.length() && input.charAt(pos) == ')') pos++;
                return val;
            }
            String[] funcs = {"sin","cos","tan","asin","acos","atan","log","ln","sqrt","exp","abs"};
            for (String fn : funcs) {
                if (input.startsWith(fn, pos)) {
                    pos += fn.length();
                    if (pos < input.length() && input.charAt(pos) == '(') {
                        pos++;
                        double arg = parseAddSub();
                        if (pos < input.length() && input.charAt(pos) == ')') pos++;
                        return applyFunc(fn, arg);
                    }
                }
            }
            if (input.startsWith("pi", pos)) { pos += 2; return Math.PI; }
            if (input.startsWith("e", pos) && (pos + 1 >= input.length() || !Character.isLetter(input.charAt(pos + 1)))) {
                pos++; return Math.E;
            }
            int start = pos;
            if (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.')) {
                while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.' || input.charAt(pos) == 'E' || (input.charAt(pos) == '-' && pos > 0 && input.charAt(pos-1) == 'E'))) pos++;
                return Double.parseDouble(input.substring(start, pos));
            }
            throw new RuntimeException("Cannot parse at: " + input.substring(pos));
        }

        private double applyFunc(String fn, double arg) {
            return switch (fn) {
                case "sin"  -> Math.sin(arg);
                case "cos"  -> Math.cos(arg);
                case "tan"  -> Math.tan(arg);
                case "asin" -> Math.asin(arg);
                case "acos" -> Math.acos(arg);
                case "atan" -> Math.atan(arg);
                case "log"  -> Math.log10(arg);
                case "ln"   -> Math.log(arg);
                case "sqrt" -> Math.sqrt(arg);
                case "exp"  -> Math.exp(arg);
                case "abs"  -> Math.abs(arg);
                default -> throw new RuntimeException("Unknown function: " + fn);
            };
        }
    }
}
