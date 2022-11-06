import Analyzer.LexicalAnalyze;
import Analyzer.SLR1Analyze;
import Entity.Grammar;
import Entity.Product;
import Getter.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.*;

public class SyntaxAnalyze {

    public static void main(String[] args) {
        Grammar G = new Grammar();
        init(G);
        FirstGetter.getFirst(G);
        FollowGetter.getFollow(G);
        LR0CollectionGetter.getLR0Collection(G);
        SLR1TableGetter.getSLR1Table(G);

        BufferedInputStream in = new BufferedInputStream(Objects.requireNonNull(SyntaxAnalyze.class.getResourceAsStream("/code.txt")));
        Scanner scanner = new Scanner(in);
        List<String> input = new ArrayList<>();
        while (scanner.hasNext()) {
            input.add(scanner.next());
        }
        List<String> code = LexicalAnalyze.analyze(input);

//        code = new ArrayList<>(List.of("int","id","(","int","id",",","int","id",")","{","int","id","=","value",";","}"));
        SLR1Analyze.analyze(G,code);
    }

    private static void init(Grammar g) {
        Set<String> s = new HashSet<String>(Collections.singleton("程序"));
        Set<String> v = new HashSet<String>(Arrays.asList("程序", "函数定义", "形式参数", "代码块", "变量类型", "算术表达式", "布尔表达式", "比较运算符", "算术运算符"));
        Set<String> t = new HashSet<String>(Arrays.asList("id", "value", "(", ")", "{", "}", ",", ";", "=", "while", "if", "else", "return", "int", "float", "double", "bool", "char", "&&", "||", "!", "true", "false", "<", ">", "<=", ">=", "==", "!=", "-", "+", "*", "/"));
        List<Product> p = new ArrayList<Product>();
        p.add(new Product("程序", List.of("函数定义")));
        p.add(new Product("函数定义", List.of("函数定义", "函数定义")));
        p.add(new Product("函数定义", List.of("变量类型", "id", "(", ")", "{", "代码块", "}")));
        p.add(new Product("函数定义", List.of("变量类型", "id", "(", "形式参数", ")", "{", "代码块", "}")));
        p.add(new Product("形式参数", List.of("变量类型", "id")));
        p.add(new Product("形式参数", List.of("变量类型", "id", ",", "形式参数")));

        p.add(new Product("代码块", List.of("代码块", "代码块")));
        p.add(new Product("代码块", List.of("变量类型", "id", ";")));
        p.add(new Product("代码块", List.of("变量类型", "id", "=", "算术表达式", ";")));
        p.add(new Product("代码块", List.of("id", "=", "算术表达式", ";")));
        p.add(new Product("代码块", List.of("while", "(", "布尔表达式", ")", "{", "代码块", "}")));
        p.add(new Product("代码块", List.of("if", "(", "布尔表达式", ")", "{", "代码块", "}")));
        p.add(new Product("代码块", List.of("if", "(", "布尔表达式", ")", "{", "代码块", "}", "else", "{", "代码块", "}")));
        p.add(new Product("代码块", List.of("return",";")));
        p.add(new Product("代码块", List.of("return", "算术表达式", ";")));

        p.add(new Product("变量类型", List.of("int")));
        p.add(new Product("变量类型", List.of("float")));
        p.add(new Product("变量类型", List.of("double")));
        p.add(new Product("变量类型", List.of("bool")));
        p.add(new Product("变量类型", List.of("char")));

        p.add(new Product("算术表达式", List.of("算术表达式", "算术运算符", "算术表达式")));
        p.add(new Product("算术表达式", List.of("-", "算术表达式")));
        p.add(new Product("算术表达式", List.of("(", "算术表达式", ")")));
        p.add(new Product("算术表达式", List.of("id")));
        p.add(new Product("算术表达式", List.of("value")));

        p.add(new Product("布尔表达式", List.of("算术表达式", "比较运算符", "算术表达式")));
        p.add(new Product("布尔表达式", List.of("布尔表达式", "&&", "布尔表达式")));
        p.add(new Product("布尔表达式", List.of("布尔表达式", "||", "布尔表达式")));
        p.add(new Product("布尔表达式", List.of("!", "布尔表达式")));
        p.add(new Product("布尔表达式", List.of("(", "布尔表达式", ")")));
        p.add(new Product("布尔表达式", List.of("true")));
        p.add(new Product("布尔表达式", List.of("false")));

        p.add(new Product("比较运算符", List.of("<")));
        p.add(new Product("比较运算符", List.of(">")));
        p.add(new Product("比较运算符", List.of("<=")));
        p.add(new Product("比较运算符", List.of(">=")));
        p.add(new Product("比较运算符", List.of("==")));
        p.add(new Product("比较运算符", List.of("!=")));

        p.add(new Product("算术运算符", List.of("+")));
        p.add(new Product("算术运算符", List.of("-")));
        p.add(new Product("算术运算符", List.of("*")));
        p.add(new Product("算术运算符", List.of("/")));

        g.setS(s);
        g.setV(v);
        g.setT(t);
        g.setP(p);
    }
}