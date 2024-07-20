import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NameNotFoundException;

class interp {
    static void interp(Stm s) throws NameNotFoundException { 
        interpStm(s, new Table());
    }

    static Table interpStm(Stm s, Table t) throws NameNotFoundException {
        if (s instanceof CompoundStm) {
            CompoundStm cs = (CompoundStm) s;
            Table firstStmResult = interpStm(cs.stm1, t);
            return interpStm(cs.stm2, firstStmResult);
        } else if (s instanceof AssignStm) {
            AssignStm as = (AssignStm) s;
            IntAndTable expResult = interpExp(as.exp, t);
            return expResult.t.update(as.id, expResult.i);
        } else if (s instanceof PrintStm) {
            PrintStm ps = (PrintStm) s;
            ExpListIterator iterator = ps.exps.iterator();
            while (iterator.hasNext()) {
                Exp e = iterator.next();
                IntAndTable expResult = interpExp(e, t);
                t = expResult.t;
                System.out.println(expResult.i);
            }
        }

        return t;
    }

    static IntAndTable interpExp(Exp e, Table t) throws NameNotFoundException {
        if (e instanceof IdExp) {
            IdExp ie = (IdExp) e;
            return new IntAndTable(t.lookup(ie.id), t);
        } else if (e instanceof NumExp) {
            NumExp ne = (NumExp) e;
            return new IntAndTable(ne.num, t);
        } else if (e instanceof OpExp) {
            OpExp oe = (OpExp) e;
            IntAndTable leftResult = interpExp(oe.left, t);
            IntAndTable rightResult = interpExp(oe.right, leftResult.t);
            int operationResult = calculateOpExp(leftResult.i, rightResult.i, oe.oper);
            return new IntAndTable(operationResult, rightResult.t);
        } else if (e instanceof EseqExp) {
            EseqExp ee = (EseqExp) e;
            Table statementResult = interpStm(ee.stm, t);
            return interpExp(ee.exp, statementResult);
        }

        return new IntAndTable(0, t);
    }

    static int calculateOpExp(int leftResult, int rightResult, int oper) {
        if (oper == OpExp.Plus) {
            return leftResult + rightResult;
        } else if (oper == OpExp.Minus) {
            return leftResult - rightResult;
        } else if (oper == OpExp.Times) {
            return leftResult * rightResult;
        } else if (oper == OpExp.Div) {
            return leftResult / rightResult;
        }
        return 0;
    }

    static int maxargs(Stm s) {
        if (s instanceof CompoundStm) {
            CompoundStm cs = (CompoundStm) s;
            return Math.max(maxargs(cs.stm1), maxargs(cs.stm2));
        } else if (s instanceof AssignStm) {
            AssignStm as = (AssignStm) s;
            return maxargs(as.exp);
        } else if (s instanceof PrintStm) {
            PrintStm ps = (PrintStm) s;
            return Math.max(ps.exps.length(), maxargs(ps.exps));
        }

        return 0;
    }

    static int maxargs(Exp e) {
        if (e instanceof OpExp) {
            OpExp oe = (OpExp) e;
            return Math.max(maxargs(oe.left), maxargs(oe.right));
        } else if (e instanceof EseqExp) {
            EseqExp ee = (EseqExp) e;
            return Math.max(maxargs(ee.stm), maxargs(ee.exp));
        }

        return 0;
    }

    static int maxargs(ExpList el) {
        if (el instanceof PairExpList) {
            PairExpList pel = (PairExpList) el;
            return Math.max(maxargs(pel.head), maxargs(pel.tail));
        } else if (el instanceof LastExpList) {
            LastExpList lel = (LastExpList) el;
            return maxargs(lel.head);
        }

        return 0;
    }

public static void main(String args[]) throws java.io.IOException, NameNotFoundException {
        System.out.println(maxargs(prog.prog));
        interp(prog.prog);
    }
}

class IntAndTable {
    int i;
    Table t;

    IntAndTable(int i, Table t) {
        this.i = i;
        this.t = t;
    }
}

class ListAndTable {
    List<Integer> l;
    Table t;

    ListAndTable(List<Integer> l, Table t) {
        this.l = l;
        this.t = t;
    }
}