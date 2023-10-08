package ru.axnikonov;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static String[] romanNumbers = {
            "I","II","III","IV","V","VI","VII","VIII","IX","X"
    };

    public static boolean isRomanCalculation = false;

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        calc(input);
        in.close();
    }


    public static void calc(String input) throws Exception {
        checkExpressionOnCorrectInput(input);
        String[] expression = input.split(" ");

        checkRomanCalculation(expression);
        if (isRomanCalculation) {
            expression = romanToArabicExpression(expression);
        } else {
            checkOnlyIntegers(expression);
            checkLimitNumber(expression);
        }

        switch(expression[1]) {
            case "+" -> printAnswer(addition(expression).toString());
            case "-" -> printAnswer(subtraction(expression).toString());
            case "*" -> printAnswer(multiplication(expression).toString());
            case "/" -> printAnswer(division(expression).toString());
        }
    }

    public static Integer addition(String[] expression) {
        return Integer.parseInt(expression[0]) + Integer.parseInt(expression[2]);
    }

    public static Integer subtraction(String[] expression) {
        return Integer.parseInt(expression[0]) - Integer.parseInt(expression[2]);
    }

    public static Integer multiplication(String[] expression) {
        return Integer.parseInt(expression[0]) * Integer.parseInt(expression[2]);
    }

    public static Integer division(String[] expression) {
        return Integer.parseInt(expression[0]) / Integer.parseInt(expression[2]);
    }

    public static void printAnswer(String answer) throws Exception {
        if (isRomanCalculation) {
            if (Integer.parseInt(answer) < 1) throw new Exception("Roman numbers cannot be negative. Try again.");
            answer = arabicToRoman(Integer.parseInt(answer));
        }
        System.out.println(answer);
    }

    public static void checkLimitNumber(String[] expression) throws Exception {
        String[] numbers = { expression[0], expression[2] };
        for (String number : numbers) {
            if (Integer.parseInt(number) > 10 || Integer.parseInt(number) < 1) throw new Exception("The numbers must be between 1 and 10. Try again.");
        }
    }

    public static void checkOnlyIntegers(String[] expression) {
        String[] numbers = { expression[0], expression[2] };
        try {
            for (String number : numbers) {
                if (StringUtils.isNumeric(number)) {
                    Integer.parseInt(number);
                } else throw new Exception("The entered data must be entirely Arabic or Roman numerals. Try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("The entered data must be integers. Try again.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkExpressionOnCorrectInput(String input) throws Exception {
        if(StringUtils.countMatches(input, " ") != 2) throw new Exception("The expression is written incorrectly, use only integers and separate operator and two operands with a space. Try again.");
    }

    public static void checkRomanCalculation(String[] expression) {
        if (isRomanNumber(expression[0]) && isRomanNumber(expression[2])) isRomanCalculation = true;
    }

    public static Boolean isRomanNumber(String number) {
        for (String romanNumber : romanNumbers) {
            if (number.equalsIgnoreCase(romanNumber)) return true;
        }
        return false;
    }

    public static Integer romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral.");
        }

        return result;
    }

    public static String arabicToRoman(Integer number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000)");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

    public static String[] romanToArabicExpression (String[] expression) {
        expression[0] = romanToArabic(expression[0]).toString();
        expression[2] = romanToArabic(expression[2]).toString();
        return expression;
    }
}