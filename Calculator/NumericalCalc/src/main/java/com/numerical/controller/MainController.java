package com.numerical.controller;

import com.numerical.model.NumericalEngine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.*;
import java.util.function.Function;

public class MainController implements Initializable {
    @FXML private Label expressionLabel;
    @FXML private Label resultLabel;
    @FXML private VBox standardPane;
    @FXML private VBox numericalPane;
    @FXML private ComboBox<String> modeSelector;
    @FXML private Button tabDirect, tabLagrange, tabS13, tabS38, tabEuler, tabError;
    @FXML private VBox formArea;
    @FXML private TextArea outputArea;

    private double operand1 = 0;
    private String operator = null;
    private boolean newInput = true;
    private String currentInput = "0";
    private String expression = "";
    private String activeTab = "direct";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modeSelector.getItems().addAll("Standard Calculator", "Numerical Methods");
        modeSelector.setValue("Standard Calculator");
        buildDirectForm();
    }
    @FXML
    private void onModeChanged() {
        String mode = modeSelector.getValue();
        boolean numerical = "Numerical Methods".equals(mode);
        standardPane.setVisible(!numerical);
        standardPane.setManaged(!numerical);
        numericalPane.setVisible(numerical);
        numericalPane.setManaged(numerical);
    }
    @FXML private void onDigit(javafx.event.ActionEvent e) {
        String d = ((Button) e.getSource()).getText();
        if (newInput) { currentInput = d; newInput = false; }
        else currentInput = "0".equals(currentInput) ? d : currentInput + d;
        updateDisplay();
    }
    @FXML private void onDot() {
        if (newInput) { currentInput = "0."; newInput = false; }
        else if (!currentInput.contains(".")) currentInput += ".";
        updateDisplay();
    }
    @FXML private void onOperator(javafx.event.ActionEvent e) {
        String op = (String) ((Button) e.getSource()).getUserData();
        if (op == null) op = ((Button) e.getSource()).getText();
        if (operator != null && !newInput) calculate();
        operand1 = Double.parseDouble(currentInput);
        operator = op;
        expression = fmt(operand1) + " " + displayOp(op);
        expressionLabel.setText(expression);
        newInput = true;
    }
    @FXML private void onEquals() {
        if (operator == null) return;
        expression = expressionLabel.getText() + " " + currentInput + " =";
        calculate();
        operator = null;
        expressionLabel.setText(expression);
        newInput = true;
    }

    private void calculate() {
        double b = Double.parseDouble(currentInput);
        double result = switch (operator) {
            case "+" -> operand1 + b;
            case "-" -> operand1 - b;
            case "*" -> operand1 * b;
            case "/" -> b != 0 ? operand1 / b : Double.NaN;
            default  -> b;
        };
        currentInput = fmt(result);
        resultLabel.setText(Double.isNaN(result) ? "Error" : currentInput);
        operand1 = result;
    }

    @FXML private void onClearAll() {
        currentInput = "0"; operand1 = 0; operator = null;
        expression = ""; newInput = true;
        expressionLabel.setText(""); resultLabel.setText("0");
    }

    @FXML private void onNegate() {
        double v = Double.parseDouble(currentInput);
        currentInput = fmt(-v);
        updateDisplay();
    }

    @FXML private void onPercent() {
        double v = Double.parseDouble(currentInput);
        currentInput = fmt(v / 100.0);
        updateDisplay();
    }

    @FXML private void onBackspace() {
        if (currentInput.length() > 1) currentInput = currentInput.substring(0, currentInput.length() - 1);
        else currentInput = "0";
        updateDisplay();
    }

    @FXML private void onSci(javafx.event.ActionEvent e) {
        String op = ((Button) e.getSource()).getText();
        double v = Double.parseDouble(currentInput);
        double result = switch (op) {
            case "sin"  -> Math.sin(Math.toRadians(v));
            case "cos"  -> Math.cos(Math.toRadians(v));
            case "tan"  -> Math.tan(Math.toRadians(v));
            case "ln"   -> Math.log(v);
            case "√"    -> Math.sqrt(v);
            case "x²"   -> v * v;
            case "xⁿ"   -> { operator = "^"; operand1 = v; newInput = true; yield v; }
            case "1/x"  -> 1.0 / v;
            default -> v;
        };
        if (!"xⁿ".equals(op)) {
            expressionLabel.setText(op + "(" + fmt(v) + ")");
            currentInput = fmt(result);
            resultLabel.setText(currentInput);
            newInput = true;
        }
    }

    private void updateDisplay() { resultLabel.setText(currentInput); }

    private String fmt(double v) {
        if (v == (long) v && !Double.isInfinite(v)) return String.valueOf((long) v);
        return String.format("%.8f", v).replaceAll("0+$", "").replaceAll("\\.$", "");
    }
    private String displayOp(String op) {
        return switch (op) { case "*" -> "×"; case "/" -> "÷"; case "-" -> "−"; default -> op; };
    }
    @FXML private void onTabSelect(javafx.event.ActionEvent e) {
        Button src = (Button) e.getSource();
        for (Button b : List.of(tabDirect, tabLagrange, tabS13, tabS38, tabEuler, tabError)) {
            b.getStyleClass().remove("tab-active");
        }
        src.getStyleClass().add("tab-active");
        outputArea.clear();

        activeTab = src.getId().replace("tab", "").toLowerCase();
        switch (activeTab) {
            case "direct"   -> buildDirectForm();
            case "lagrange" -> buildLagrangeForm();
            case "s13"      -> buildSimpson13Form();
            case "s38"      -> buildSimpson38Form();
            case "euler"    -> buildEulerForm();
            case "error"    -> buildErrorForm();
        }
    }
    private TextField field(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.getStyleClass().add("num-field");
        return tf;
    }

    private Label lbl(String text) {
        Label l = new Label(text);
        l.getStyleClass().add("form-label");
        return l;
    }

    private void buildDirectForm() {
        formArea.getChildren().clear();
        formArea.getChildren().addAll(
                lbl("Direct (Linear) Interpolation"),
                lbl("x₀"), field("e.g. 1.0"),
                lbl("y₀ = f(x₀)"), field("e.g. 2.0"),
                lbl("x₁"), field("e.g. 3.0"),
                lbl("y₁ = f(x₁)"), field("e.g. 8.0"),
                lbl("x (interpolate at)"), field("e.g. 2.0")
        );
    }

    private void buildLagrangeForm() {
        formArea.getChildren().clear();
        formArea.getChildren().addAll(
                lbl("Lagrange Interpolation"),
                lbl("Number of data points (n)"), field("e.g. 3"),
                lbl("x values (comma-separated)"), field("e.g. 1,2,4"),
                lbl("y values (comma-separated)"), field("e.g. 1,4,16"),
                lbl("x (interpolate at)"), field("e.g. 3")
        );
    }

    private void buildSimpson13Form() {
        formArea.getChildren().clear();
        formArea.getChildren().addAll(
                lbl("Simpson's 1/3 Rule — ∫f(x)dx"),
                lbl("f(x) — use x as variable"), field("e.g. x^2 + sin(x)"),
                lbl("Lower limit a"), field("e.g. 0"),
                lbl("Upper limit b"), field("e.g. 1"),
                lbl("Number of subintervals n (even)"), field("e.g. 4")
        );
    }

    private void buildSimpson38Form() {
        formArea.getChildren().clear();
        formArea.getChildren().addAll(
                lbl("Simpson's 3/8 Rule — ∫f(x)dx"),
                lbl("f(x) — use x as variable"), field("e.g. x^3 - x"),
                lbl("Lower limit a"), field("e.g. 0"),
                lbl("Upper limit b"), field("e.g. 3"),
                lbl("Number of subintervals n (mult. of 3)"), field("e.g. 3")
        );
    }

    private void buildEulerForm() {
        formArea.getChildren().clear();
        formArea.getChildren().addAll(
                lbl("Euler's Method — dy/dx = f(x,y)"),
                lbl("f(x,y) — use x and y as variables"), field("e.g. x + y"),
                lbl("x₀ (initial x)"), field("e.g. 0"),
                lbl("y₀ = y(x₀)"),   field("e.g. 1"),
                lbl("Step size h"),   field("e.g. 0.1"),
                lbl("Number of steps"), field("e.g. 5")
        );
    }

    private void buildErrorForm() {
        formArea.getChildren().clear();
        formArea.getChildren().addAll(
                lbl("Error Analysis"),
                lbl("Exact value"),        field("e.g. 3.14159265"),
                lbl("Approximate value"),  field("e.g. 3.14")
        );
    }
    @FXML
    private void onSolve() {
        try {
            switch (activeTab) {
                case "direct"   -> solveDirect();
                case "lagrange" -> solveLagrange();
                case "s13"      -> solveSimpson13();
                case "s38"      -> solveSimpson38();
                case "euler"    -> solveEuler();
                case "error"    -> solveError();
            }
        } catch (Exception ex) {
            outputArea.setText("⚠ Error: " + ex.getMessage());
        }
    }

    private List<TextField> fields() {
        List<TextField> list = new ArrayList<>();
        formArea.getChildren().forEach(n -> { if (n instanceof TextField tf) list.add(tf); });
        return list;
    }

    private void solveDirect() {
        var f = fields();
        double x0 = Double.parseDouble(f.get(0).getText());
        double y0 = Double.parseDouble(f.get(1).getText());
        double x1 = Double.parseDouble(f.get(2).getText());
        double y1 = Double.parseDouble(f.get(3).getText());
        double x  = Double.parseDouble(f.get(4).getText());
        double result = NumericalEngine.directInterpolation(x0, y0, x1, y1, x);
        showResult("Direct Interp  f(" + fmt(x) + ")", result);
        outputArea.setText(String.format(
                "Direct (Linear) Interpolation\n------------------------------\n" +
                        "Points: (%.4f, %.4f) and (%.4f, %.4f)\n\n" +
                        "Formula: y = y₀ + (y₁-y₀)/(x₁-x₀) × (x-x₀)\n" +
                        "       = %.4f + (%.4f-%.4f)/(%.4f-%.4f) × (%.4f-%.4f)\n\n" +
                        "-----------------------------\n" +
                        "f(%.4f) ≈ %.6f",
                x0,y0,x1,y1, y0,y1,y0,x1,x0,x,x0, x,result));
    }

    private void solveLagrange() {
        var f = fields();
        String[] xs = f.get(1).getText().split(",");
        String[] ys = f.get(2).getText().split(",");
        double x = Double.parseDouble(f.get(3).getText());
        double[] xv = Arrays.stream(xs).mapToDouble(s -> Double.parseDouble(s.trim())).toArray();
        double[] yv = Arrays.stream(ys).mapToDouble(s -> Double.parseDouble(s.trim())).toArray();
        double result = NumericalEngine.lagrangeInterpolation(xv, yv, x);
        showResult("Lagrange  f(" + fmt(x) + ")", result);
        outputArea.setText(NumericalEngine.lagrangeSteps(xv, yv, x));
    }

    private void solveSimpson13() {
        var f = fields();
        String expr = f.get(0).getText();
        double a = Double.parseDouble(f.get(1).getText());
        double b = Double.parseDouble(f.get(2).getText());
        int n    = Integer.parseInt(f.get(3).getText());
        Function<Double, Double> fn = NumericalEngine.parseFunction(expr);
        double result = NumericalEngine.simpsons13(fn, a, b, n);
        showResult("Simp. 1/3  ∫f(x)dx", result);
        outputArea.setText(NumericalEngine.simpsons13Steps(fn, a, b, n));
    }

    private void solveSimpson38() {
        var f = fields();
        String expr = f.get(0).getText();
        double a = Double.parseDouble(f.get(1).getText());
        double b = Double.parseDouble(f.get(2).getText());
        int n    = Integer.parseInt(f.get(3).getText());
        Function<Double, Double> fn = NumericalEngine.parseFunction(expr);
        double result = NumericalEngine.simpsons38(fn, a, b, n);
        showResult("Simp. 3/8  ∫f(x)dx", result);
        outputArea.setText(NumericalEngine.simpsons38Steps(fn, a, b, n));
    }

    private void solveEuler() {
        var f = fields();
        String expr = f.get(0).getText();
        double x0    = Double.parseDouble(f.get(1).getText());
        double y0    = Double.parseDouble(f.get(2).getText());
        double h     = Double.parseDouble(f.get(3).getText());
        int steps    = Integer.parseInt(f.get(4).getText());
        Function<double[], Double> fn = NumericalEngine.parseFunctionXY(expr);
        java.util.List<double[]> table = NumericalEngine.eulerMethod(fn, x0, y0, h, steps);
        double finalY = table.get(table.size() - 1)[1];
        double finalX = table.get(table.size() - 1)[0];
        showResult("Euler  y(" + fmt(finalX) + ")", finalY);
        outputArea.setText(NumericalEngine.eulerSteps(fn, x0, y0, h, steps));
    }

    private void solveError() {
        var f = fields();
        double exact  = Double.parseDouble(f.get(0).getText());
        double approx = Double.parseDouble(f.get(1).getText());
        double absErr = Math.abs(exact - approx);
        double pctErr = (exact != 0) ? absErr / Math.abs(exact) * 100 : Double.NaN;
        expressionLabel.setText("% Error");
        resultLabel.setText(String.format("%.4f%%", pctErr));
        outputArea.setText(NumericalEngine.errorAnalysis(exact, approx));
    }
    private void showResult(String label, double value) {
        expressionLabel.setText(label + " =");
        resultLabel.setText(fmt(value));
    }
}
