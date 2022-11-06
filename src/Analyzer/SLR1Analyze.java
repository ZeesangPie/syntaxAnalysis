package Analyzer;

import Entity.Grammar;
import Entity.Pair;
import Entity.Product;

import java.util.List;
import java.util.Stack;

public class SLR1Analyze {
    public static void analyze(Grammar g, List<String> input) {
        Stack<Integer> stateStack = new Stack<>();
        Stack<String> symbolStack = new Stack<>();
        stateStack.push(0);
        symbolStack.push("$");
        input.add("#");

        int pos = 0;
        int step = 0;
        while (true) {
            System.out.println("Step " + step + ":");
            System.out.println("State stack: " + stateStack);
            System.out.println("Symbol stack: " + symbolStack);
            System.out.print("Input: ");
            for (String s : input.subList(pos, input.size())) {
                System.out.print(s + " ");
            }
            System.out.println();
            step++;

            int state = stateStack.peek();
            String symbol = input.get(pos);
            if (g.getActionTable().get(state).containsKey(symbol)) {
                Pair<String,Integer> action = g.getActionTable().get(state).get(symbol);
                if (action.getFirst().equals("S")) {
                    stateStack.push(action.getSecond());
                    symbolStack.push(symbol);
                    pos++;
                } else if (action.getFirst().equals("R")) {
                    int index = action.getSecond();
                    Product p = g.getP().get(index);
                    for (int i = 0; i < p.getRight().size(); i++) {
                        stateStack.pop();
                        symbolStack.pop();
                    }
                    state = stateStack.peek();
                    symbol = p.getLeft();
                    stateStack.push(g.getGotoTable().get(state).get(symbol));
                    symbolStack.push(symbol);
                } else if (action.getFirst().equals("Acc")) {
                    System.out.println("Accept!");
                    break;
                }
            } else {
                System.out.println("Error!");
                break;
            }
        }
    }
}
