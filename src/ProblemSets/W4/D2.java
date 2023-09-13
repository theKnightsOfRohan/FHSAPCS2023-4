package ProblemSets.W4;

import GeneralHelpers.Annotations.HelperMethod;
import GeneralHelpers.Annotations.RunnableMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;

public class D2 {
    public static void main(String[] args) {
        String[] operators = new String[] { "+", "-", "*", "/", "^" };
        String[] values = { "4", "4", "4", "4" };

        HashMap<String, Double> results = computeAllCombos(operators, values);
        System.out.println(results);
    }

    @RunnableMethod
    public static HashMap<String, Double> computeAllCombos(String[] operators, String[] values) {
        HashMap<String, Double> results = new HashMap<>();

        List<String[]> combinations = generateCombinations(operators, values);

        for (String[] combination : combinations) {
            try {
                double result = parseStringExpression(combination, operators);
                if (result % 1 == 0 && result >= 0 && result <= 100 && !results.containsValue(result))
                    results.put(convertPrefixToAlgebra(combination, operators), result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    @HelperMethod
    private static List<String[]> generateCombinations(String[] operators, String[] values) {
        List<String[]> combinations = new ArrayList<>();
        for (String operator1 : operators) {
            for (String operator2 : operators) {
                for (String operator3 : operators) {
                    String[] combination = { operator1, operator2, values[0], operator3, values[1], values[2], values[3] };
                    combinations.add(combination);
                    combination = new String[] { operator1, operator2, values[0], values[1], operator3, values[2], values[3] };
                    combinations.add(combination);
                    combination = new String[] { operator1, values[0], operator2, values[1], operator3, values[2], values[3] };
                    combinations.add(combination);
                }
            }
        }

        return combinations;
    }

    @RunnableMethod
    public static double parseStringExpression(String[] terms, String[] operations) {
        String[] workingTerms = Arrays.copyOf(terms, terms.length);

        for (int i = workingTerms.length - 1; i >= 0; i--) {
            if (Arrays.asList(operations).contains(workingTerms[i])) {
                workingTerms[i] = computeValue(workingTerms[i], workingTerms[i + 1], workingTerms[i + 2]);
                workingTerms[i + 1] = null;
                workingTerms[i + 2] = null;
                workingTerms = clean(workingTerms);
            }
        }
        return Double.parseDouble(workingTerms[0]);
    }

    @HelperMethod
    private static String[] clean(String[] workingTerms) {
        String[] cleanedTerms = new String[workingTerms.length - 2];
        int j = 0;
        for (int i = 0; i < workingTerms.length; i++) {
            if (workingTerms[i] != null) {
                cleanedTerms[j] = workingTerms[i];
                j++;
            }
        }
        return cleanedTerms;
    }

    @HelperMethod
    private static String computeValue(String operator, String operand1, String operand2) {
        switch (operator) {
        case "+":
            return String.valueOf(Double.parseDouble(operand1) + Double.parseDouble(operand2));
        case "-":
            return String.valueOf(Double.parseDouble(operand1) - Double.parseDouble(operand2));
        case "*":
            return String.valueOf(Double.parseDouble(operand1) * Double.parseDouble(operand2));
        case "/":
            return String.valueOf(Double.parseDouble(operand1) / Double.parseDouble(operand2));
        case "^":
            return String.valueOf(Math.pow(Double.parseDouble(operand1), Double.parseDouble(operand2)));
        default:
            throw new IllegalArgumentException("Invalid operator");
        }
    }

    @HelperMethod
    private static String convertPrefixToAlgebra(String[] expression, String[] operators) {
        Stack<String> stack = new Stack<String>();

        for (int i = expression.length - 1; i >= 0; i--) {
            String c = expression[i];

            if (Arrays.asList(operators).contains(expression[i])) {
                String op1 = stack.pop();
                String op2 = stack.pop();

                String temp = "(" + op1 + c + op2 + ")";
                stack.push(temp);
            } else {
                stack.push(c + "");
            }
        }

        return stack.pop();
    }

}
