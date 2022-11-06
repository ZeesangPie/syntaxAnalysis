package Getter;

import Entity.Grammar;
import Entity.Item;
import Entity.Pair;
import Entity.Product;

import java.util.*;

public class SLR1TableGetter {
    public static void getSLR1Table(Grammar g) {
        g.setActionTable(new ArrayList<>());
        g.setGotoTable(new ArrayList<>());
        for (int i = 0; i < g.getNrOfStates(); i++) {
            g.getActionTable().add(i, new HashMap<>());
            g.getGotoTable().add(i, new HashMap<>());
        }
        for (int k= 0; k < g.getClosure().size(); k++) {
            Set<Item> I=g.getClosure().get(k);
            for (Item i:I) {
                if (i.getDot() < i.getRight().size()) {
                    String next = i.getRight().get(i.getDot());
                    for (Set<Item> J:g.getClosure()) {
                        if (isEquals(LR0CollectionGetter.GO(g,I,next), J)) {
                            if (g.getT().contains(next)) g.getActionTable().get(k).put(next, new Pair<>("S", g.getClosure().indexOf(J)));
                            else g.getGotoTable().get(k).put(next, g.getClosure().indexOf(J));
                        }
                    }
                } else {
                    if (i.getLeft().equals("S'")) {
                        g.getActionTable().get(k).put("#", new Pair<>("Acc", 0));
                        continue;
                    }
                    int j=g.getP().indexOf(new Product(i.getLeft(), i.getRight()));
                    for (String t:g.getT()) {
                        if (g.getFollow().get(i.getLeft()).contains(t)) g.getActionTable().get(k).put(t, new Pair<>("R", j));
                    }
                    if (g.getFollow().get(i.getLeft()).contains("#")) g.getActionTable().get(k).put("#", new Pair<>("R", j));
                    break;
                }
            }
        }

        display(g);
    }

    private static void display(Grammar g) {
        List<String> y= Arrays.asList("id", "value", "(", ")", "{", "}", ",", ";", "=", "while", "if", "else", "return", "int", "float", "double", "bool", "char", "&&", "||", "!", "true", "false", "<", ">", "<=", ">=", "==", "!=", "-", "+", "*", "/","#");
        System.out.print("\t|");
        for (String s:y) {
            System.out.print(s+"\t");
            if (s.length()<3) System.out.print("\t");
            System.out.print("|");
        }
        for (int state=0;state<g.getNrOfStates();state++) {
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print(state+"\t|");
            for (String s:y) {
                Pair<String, Integer> p=g.getActionTable().get(state).get(s);
                if (p==null) {
                    System.out.print("\t\t|");
                } else {
                    String out=p.getFirst()+p.getSecond().toString();
                    System.out.print(out+"\t");
                    if (out.length()<3) System.out.print("\t");
                    System.out.print("|");
                }
            }
        }

        System.out.println("\t");
        y = Arrays.asList("程序", "函数定义", "形式参数", "代码块", "变量类型", "算术表达式", "布尔表达式", "比较运算符", "算术运算符");
        System.out.print("\t|");
        for (String s:y) {
            System.out.print(s+"\t");
            if (s.length()<4) System.out.print("\t");
            if (s.length()<8) System.out.print("\t");
            System.out.print("|");
        }
        for (int state=0;state<g.getNrOfStates();state++) {
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print(state+"\t|");
            for (String s:y) {
                Integer p=g.getGotoTable().get(state).get(s);
                if (p==null) {
                    System.out.print("\t\t\t\t|");
                } else {
                    String out=p.toString();
                    System.out.print(out+"\t\t\t");
                    if (out.length()<3) System.out.print("\t");
                    System.out.print("|");
                }
            }
        }
    }

    private static boolean isEquals(Set<Item> a, Set<Item> b) {
        if (a.size() != b.size()) return false;
        for (Item i:a) {
            if (!b.contains(i)) return false;
        }
        return true;
    }
}
