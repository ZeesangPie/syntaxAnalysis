package Getter;

import Entity.Grammar;
import Entity.Item;
import Entity.Product;

import java.util.*;

public class LR0CollectionGetter {

    private static Set<Item> getClosure(Grammar g, List<Item> I) {
        Set<Item> closure = new HashSet<>(I);
        Queue<Item> queue = new LinkedList<>(closure);
        while (!queue.isEmpty()) {
            Item i = queue.poll();
            if (i.getRight().size() == i.getDot()) {
                continue;
            }
            String b = i.getRight().get(i.getDot());
            if (!g.getV().contains(b)) {
                continue;
            }
            for (Product p:g.getP()) {
                if (p.getLeft().equals(b)) {
                    Item item = new Item(p, 0);
                    if (!closure.contains(item)) {
                        queue.add(item);
                        closure.add(item);
                    }
                }
            }
        }
        return closure;
    }

    public static void getLR0Collection(Grammar g) {
        for (String s:g.getS()) {
            g.getP().add(0,new Product("S'", List.of(s)));
        }
        g.getV().add("S'");

        g.setI(new ArrayList<>());
        g.setClosure(new ArrayList<>());
        Item i0 = new Item(g.getP().get(0), 0);
        g.getClosure().add(getClosure(g, List.of(i0)));

        Set<String> VT = new HashSet<String>(g.getV());
        VT.addAll(g.getT());

        Queue<Set<Item>> queue = new LinkedList<>(List.of(g.getClosure().get(0)));
        while (!queue.isEmpty()) {
            Set<Item> i = queue.poll();
            for (String X: VT) {
                Set<Item> J = GO(g,i,X);
                if (J.isEmpty()) continue;
                boolean flag= false;
                for (Set<Item> k: g.getClosure()) {
                    if (k.equals(J)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) continue;
                queue.add(J);
                g.getClosure().add(J);
            }
        }

        g.setNrOfStates(g.getClosure().size());
        display(g);
    }

    private static void display(Grammar g) {
        int state=0;
        for (Set<Item> i: g.getClosure()) {
            System.out.println("I(" + state++ +"):");
            for (Item item: i) {
                System.out.println(item);
            }
        }
    }

    public static Set<Item> GO(Grammar g, Set<Item> i, String x) {
        Set<Item> J = new HashSet<>();
        for (Item item: i) {
            if (item.getDot() >= item.getRight().size())continue;
            String B = item.getRight().get(item.getDot());
            if (B.equals(x)) {
                J.add(new Item(item.getLeft(),item.getRight(),item.getDot()+1));
            }
        }
        return getClosure(g,new ArrayList<>(J));

    }
}
