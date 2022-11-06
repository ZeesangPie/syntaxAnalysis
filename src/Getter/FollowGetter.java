package Getter;

import Entity.Grammar;
import Entity.Product;

import java.util.*;

public class FollowGetter {
    public static void getFollow(Grammar g) {
        Map<String, Set<String>> follow = new HashMap<>();
        g.setFollow(follow);
        for (String v : g.getV()) {
            follow.put(v, new HashSet<>());
        }
        follow.get(g.getS().iterator().next()).add("#");
        boolean flag = true;
        while (flag) {
            flag = false;
            for (Product p: g.getP()) {
                String A = p.getLeft();
                List<String> Y = p.getRight();
                for (int i=0;i< Y.size();i++) {
                    String B = Y.get(i);
                    if (!g.getV().contains(B)) {
                        continue;
                    }
                    if (i==Y.size()-1) {
                        flag |= union(follow.get(B),follow.get(A));
                    } else {
                        List<String> beta = new ArrayList<>(Y.subList(i+1,Y.size()));
                        Set<String> first = FirstGetter.getFirst(g,beta);
                        if (first.contains("ε")) {
                            flag |= union(follow.get(B),follow.get(A));
                        }
                        first.remove("ε");
                        flag |= union(follow.get(B),first);
                    }
                }
            }
        }

        display(g);
    }

    private static void display(Grammar g) {
        Map<String,Set<String>> follow = g.getFollow();
        for (String key: follow.keySet()) {
            System.out.println(key + " : " + follow.get(key));
        }
        System.out.println("--------------------------------------------------------");
    }

    static boolean union(Set<String> a, Set<String> b) {
        int aSize = a.size();
        a.addAll(b);
        return aSize != a.size();
    }
}
