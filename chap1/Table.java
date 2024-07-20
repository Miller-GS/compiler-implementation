import javax.naming.NameNotFoundException;

public class Table {
    TableRegister head;

    public Table() {
        this.head = null;
    }

    public Table(TableRegister head) {
        this.head = head;
    }

    public Table update(String id, int value) {
        TableRegister register = new TableRegister(id, value, this.head);
        return new Table(register);
    }

    public int lookup(String id) throws NameNotFoundException {
        if (this.head == null)
            throw new NameNotFoundException();
        return this.head.lookup(id);
    }
}

class TableRegister {
    String id;
    int value;
    TableRegister tail;

    TableRegister(String id, int value, TableRegister tail) {
        this.id = id;
        this.value = value;
        this.tail = tail;
    }

    int lookup(String id) throws NameNotFoundException {
        if (this.id.equals(id))
            return this.value;
        if (this.tail == null)
            throw new NameNotFoundException();

        return this.tail.lookup(id);
    }
}