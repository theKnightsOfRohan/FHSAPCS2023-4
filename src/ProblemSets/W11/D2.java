package ProblemSets.W11;

public class D2 {
    public static void main(String[] args) {
        APList<String> list = new APList<>();
        list.add("Hello");
        list.add("World");
        list.add("!");
        System.out.println(list);
        System.out.println(list.remove(1));
        System.out.println(list);
        list.remove("!");
        System.out.println(list);
        System.out.println(list.contains("Hello"));
        list.add(0, "Potato");
        System.out.println(list);
        list.add(1, "Potato");
        System.out.println(list);
        System.out.println(list.removeAll("Potato"));
        System.out.println(list);
    }
}

@SuppressWarnings("unchecked")
class APList<T> {
    T[] list;

    public APList() {
        this.list = (T[]) new Object[0];
    }

    public void add(T item) {
        T[] temp = (T[]) new Object[list.length + 1];
        for (int i = 0; i < list.length; i++) {
            temp[i] = list[i];
        }
        temp[list.length] = item;
        list = temp;
    }

    public void add(int index, T value) {
        T[] temp = (T[]) new Object[list.length + 1];
        for (int i = 0; i < index; i++) {
            temp[i] = list[i];
        }
        temp[index] = value;
        for (int i = index + 1; i < temp.length; i++) {
            temp[i] = list[i - 1];
        }
        list = temp;
    }

    public T remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= list.length) {
            throw new IndexOutOfBoundsException();
        }

        T[] temp = (T[]) new Object[list.length - 1];
        for (int i = 0; i < index; i++) {
            temp[i] = list[i];
        }
        for (int i = index + 1; i < list.length; i++) {
            temp[i - 1] = list[i];
        }
        T removed = list[index];
        list = temp;
        return removed;
    }

    public boolean remove(T item) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(item)) {
                remove(i);
                return true;
            }
        }

        return false;
    }

    public int removeAll(T item) {
        int counter = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(item)) {
                remove(i);
                i--;
                counter++;
            }
        }

        return counter;
    }

    public boolean contains(T item) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(item)) {
                return true;
            }
        }

        return false;
    }

    public int size() {
        return list.length;
    }

    public T get(int i) {
        try {
            return list[i];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public String toString() {
        String str = "[";
        for (int i = 0; i < list.length; i++) {
            str += list[i];
            if (i != list.length - 1) {
                str += ", ";
            }
        }
        str += "]";
        return str;
    }
}
