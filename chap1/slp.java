import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class Stm {}

class CompoundStm extends Stm {
   Stm stm1, stm2;
   CompoundStm(Stm s1, Stm s2) {stm1=s1; stm2=s2;}
}

class AssignStm extends Stm {
   String id; Exp exp;
   AssignStm(String i, Exp e) {id=i; exp=e;}
}

class PrintStm extends Stm {
   ExpList exps;
   PrintStm(ExpList e) {exps=e;}
}

abstract class Exp {}

class IdExp extends Exp {
   String id;
   IdExp(String i) {id=i;}
}

class NumExp extends Exp {
   int num;
   NumExp(int n) {num=n;}
}

class OpExp extends Exp {
   Exp left, right; int oper;
   final static int Plus=1,Minus=2,Times=3,Div=4;
   OpExp(Exp l, int o, Exp r) {left=l; oper=o; right=r;}
}

class EseqExp extends Exp {
   Stm stm; Exp exp;
   EseqExp(Stm s, Exp e) {stm=s; exp=e;}
}

abstract class ExpList {
    abstract int length();
    abstract Exp getHead();

    ExpListIterator iterator() {
        return new ExpListIterator(this);
    }
}

class PairExpList extends ExpList {
    Exp head; ExpList tail;
    public PairExpList(Exp h, ExpList t) {head=h; tail=t;}

    public int length() {
       return 1 + tail.length();
    }

    public Exp getHead() {
        return this.head;
    }
}

class LastExpList extends ExpList {
   Exp head; 
   public LastExpList(Exp h) {head=h;}

    public int length() {
        return 1;
    }

    public Exp getHead() {
        return this.head;
    }
}

class ExpListIterator implements Iterator<Exp> {
    ExpList list;
    
    public ExpListIterator(ExpList list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return this.list != null;
    }

    public Exp next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Exp ret = list.getHead();
        if (list instanceof PairExpList) {
            this.list = ((PairExpList) list).tail;
        } else {
            this.list = null;
        }

        return ret;
    }
}
