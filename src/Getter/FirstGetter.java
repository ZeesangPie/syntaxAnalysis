package Getter;

import Entity.Grammar;
import Entity.Product;

import java.util.*;

public class FirstGetter {
    public static void getFirst(Grammar g) {
        Map<String,Set<String>> first = new HashMap<>();
        g.setFirst(first);
        for (String t: g.getT()) {
            first.put(t,new HashSet<>(Set.of(t)));
        }
        for (String v: g.getV()) {
            first.put(v,new HashSet<>());
        }

        for (Product p: g.getP()) {
            if (g.getT().contains(p.getRight().get(0))) {
                String right = p.getRight().get(0);
                first.get(p.getLeft()).add(right);
            }
            if (p.getRight().contains("ε")) {
                first.get(p.getLeft()).add("ε");
            }
        }

        boolean flag = true;
        while (flag) {
            flag = false;
            for (Product p: g.getP()) {
                if (g.getV().contains(p.getRight().get(0))) {
                    Set<String> temp = first.get(p.getRight().get(0));
                    temp.remove("ε");
                    flag |= union(first.get(p.getLeft()),temp);
                }
                if (p.getRight().size() >1) {
                    boolean epsilon = false;
                    for (String y:p.getRight()) {
                        epsilon = false;
                        if (g.getFirst().get(y).contains("ε")) {
                            epsilon = true;
                            Set<String> temp = first.get(y);
                            temp.remove("ε");
                            flag |= union(first.get(p.getLeft()),temp);
                        }
                        if (!epsilon) {
                            Set<String> temp = first.get(y);
                            temp.remove("ε");
                            flag |= union(first.get(p.getLeft()),temp);
                            break;
                        }
                    }
                    if (epsilon) {
                        flag |= union(first.get(p.getLeft()),new HashSet<>(Set.of("ε")));
                    }
                }
            }
        }

        display(g);
    }

    private static void display(Grammar g) {
        Map<String,Set<String>> first = g.getFirst();
        for (String key: first.keySet()) {
            System.out.println(key + " : " + first.get(key));
        }
        System.out.println("--------------------------------------------------------");
    }


    public static Set<String> getFirst(Grammar g, List<String> a) {
        if (a.size() == 1) {
            if (a.contains("ε")) {
                return new HashSet<>(Set.of("ε"));
            }
            return g.getFirst().get(a.get(0));
        }
        boolean epsilon = false;
        Set<String> result = new HashSet<>();
        for (String x:a) {
            epsilon = false;
            if (g.getFirst().get(x).contains("ε")) {
                epsilon = true;
                Set<String> temp = g.getFirst().get(x);
                temp.remove("ε");
                union(result,temp);
            }
            if (!epsilon) {
                Set<String> temp = g.getFirst().get(x);
                temp.remove("ε");
                union(result,temp);
                break;
            }
        }
        if (epsilon) {
            result.add("ε");
        }
        return result;
    }

    static boolean union(Set<String> a, Set<String> b) {
        int aSize = a.size();
        a.addAll(b);
        return aSize != a.size();
    }
}
