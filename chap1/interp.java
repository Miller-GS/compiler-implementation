class interp {
    static void interp(Stm s) { /* you write this part */ }

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

public static void main(String args[]) throws java.io.IOException {
        System.out.println(maxargs(prog.prog));
        interp(prog.prog);
    }
}
