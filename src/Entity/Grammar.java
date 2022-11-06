package Entity;

import java.util.*;

public class Grammar {
    private Set<String> S,V,T;
    private List<Product> P;
    private List<Item> I;
    private Map<String,Set<String>> first,follow;
    private List<Map<String,Pair<String,Integer>>> actionTable;
    private List<Map<String,Integer>> gotoTable;
    private List<Set<Item>> closure;

    private Integer nrOfStates;

    public Grammar() {
    }

    public Set<String> getS() {
        return S;
    }

    public void setS(Set<String> s) {
        S = s;
    }

    public Set<String> getV() {
        return V;
    }

    public void setV(Set<String> v) {
        V = v;
    }

    public Set<String> getT() {
        return T;
    }

    public void setT(Set<String> t) {
        T = t;
    }

    public List<Product> getP() {
        return P;
    }

    public void setP(List<Product> p) {
        P = p;
    }

    public List<Item> getI() {
        return I;
    }

    public void setI(List<Item> i) {
        I = i;
    }

    public Map<String, Set<String>> getFirst() {
        return first;
    }

    public void setFirst(Map<String, Set<String>> first) {
        this.first = first;
    }

    public Map<String, Set<String>> getFollow() {
        return follow;
    }

    public void setFollow(Map<String, Set<String>> follow) {
        this.follow = follow;
    }


    public List<Map<String, Pair<String, Integer>>> getActionTable() {
        return actionTable;
    }

    public void setActionTable(List<Map<String, Pair<String, Integer>>> actionTable) {
        this.actionTable = actionTable;
    }

    public List<Map<String, Integer>> getGotoTable() {
        return gotoTable;
    }

    public void setGotoTable(List<Map<String, Integer>> gotoTable) {
        this.gotoTable = gotoTable;
    }

    public List<Set<Item>> getClosure() {
        return closure;
    }

    public void setClosure(List<Set<Item>> closure) {
        this.closure = closure;
    }

    public Integer getNrOfStates() {
        return nrOfStates;
    }

    public void setNrOfStates(Integer nrOfStates) {
        this.nrOfStates = nrOfStates;
    }
}
