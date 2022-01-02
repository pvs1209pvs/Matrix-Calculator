package com.company.parse;



import java.util.ArrayList;
import java.util.List;

 public class EquationParser {

    static public List<Double> convert(String equation) {

        StringBuilder eqtn = new StringBuilder();
        eqtn.append(equation);

        StringBuilder current = new StringBuilder();
        List<Double> numbers = new ArrayList<>();

        for (int i = 0; i < eqtn.length(); i++) {
            if (eqtn.charAt(i) == '=') continue;
            if (Character.isLetter(eqtn.charAt(i))) {
                numbers.add(Double.parseDouble(current.toString()));
                current = new StringBuilder();
                continue;
            }
            current.append(eqtn.charAt(i));
        }

        numbers.add(Double.parseDouble(current.toString()));

        return numbers;

    }

}
