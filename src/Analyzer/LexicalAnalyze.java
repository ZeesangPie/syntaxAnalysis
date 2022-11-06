package Analyzer;

import Entity.Token;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LexicalAnalyze {

    static final Set<String> keywords = new HashSet<>(Set.of("int", "float", "char", "double", "bool", "if", "while", "return", "else"));
    static final Set<String> operators = new HashSet<>(Set.of("+", "-", "*", "/", "=", "==", ">", "<", ">=", "<=", "&&", "||", "!"));
    static final Set<String> separators = new HashSet<>(Set.of("(", ")", "[", "]", "{", "}", ";", ",", " ", "."));

    public static List<String> analyze(List<String> input) {
        String codeLine = preprocess(input);
        System.out.println("Preprocessed input: \n  " + codeLine);
        List<Token> tokens = new ArrayList<Token>();

        int pos=0;
        boolean flag = true;
        while (pos<codeLine.length()) {
            char ch = codeLine.charAt(pos);

            if (ch == ' ') {
                pos++;
//                tokens.add(new Token("分界符", 7, " "));
                continue;
            }

            if (separators.contains(ch+"")) {
                tokens.add(new Token("分界符", 7, ch+""));
                pos++;
                continue;
            }

            if (operators.contains(ch+"")) {
                if (pos+1<codeLine.length() && operators.contains(ch+""+codeLine.charAt(pos+1))) {
                    tokens.add(new Token("运算符", 6, ch+""+codeLine.charAt(pos+1)));
                    pos+=2;
                } else {
                    tokens.add(new Token("运算符", 6, ch+""));
                    pos++;
                }
                continue;
            }

            if (isDigit(ch)) {
                int start = pos;
                while (pos<codeLine.length() && isDigit(codeLine.charAt(pos))) {
                    pos++;
                }

                if (pos==codeLine.length() || isLetter(codeLine.charAt(pos)) || codeLine.charAt(pos)=='.') flag=false;

                String temp = codeLine.substring(start, pos);
                if (temp.contains(".")) {
                    tokens.add(new Token("浮点数", 3, temp));
                } else {
                    tokens.add(new Token("整数", 2, temp));
                }
                continue;
            }

            if (ch=='\'' || ch=='"') {
                int start = pos;
                pos++;
                while (pos<codeLine.length() && codeLine.charAt(pos)!=ch) {
                    pos++;
                }
                if (pos==codeLine.length()) flag=false;
                tokens.add(new Token("字符串", 4, codeLine.substring(start, pos)));
                pos++;
                continue;
            }

            if (isLetter(ch)) {
                int start = pos;
                while (pos<codeLine.length() && (isDigit(codeLine.charAt(pos)) || isLetter(codeLine.charAt(pos)))) {
                    pos++;
                }
                String temp = codeLine.substring(start, pos);
                if (keywords.contains(temp)) {
                    tokens.add(new Token("关键字", 6, temp));
                } else {
                    tokens.add(new Token("标识符", 1, temp));
                }
            }
        }

        for (Token token : tokens) {
            System.out.println(token);
        }

        List<String> result = new ArrayList<>();
        for (Token token : tokens) {
            switch (token.getType()) {
                case "关键字", "分界符", "运算符" -> result.add(token.getToken());
                case "整数", "字符串", "浮点数" -> result.add("value");
                case "标识符" -> result.add("id");
                default -> System.out.println("Error: " + token);
            }
        }

        return result;
    }

    private static String preprocess(List<String> input) {
        StringBuilder result = new StringBuilder();
        for (String line : input) {
            line = line.trim();
            if (line.equals("")) continue;
            result.append(line).append(" ");
        }
        return result.toString();
    }

    private static boolean isLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch=='_';
    }

    private static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
}
