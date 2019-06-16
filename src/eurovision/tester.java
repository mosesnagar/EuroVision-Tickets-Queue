package eurovision;


public class tester {
    CustomerElement[] heap ;
    int size = 0;
    public int size() { return size;}


    public void percDown(int i) {
        if (i * 2 > this.size())
            return;
        if (i * 2 == this.size()) {
            if (heap[i].c.compareTo(heap[i * 2].c) < 0) {
                CustomerElement temp1 = heap[i * 2];
                heap[i * 2] = heap[i];
                heap[i] = temp1;
                heap[i * 2].heapIndex = i * 2;
                heap[i].heapIndex = i;
            }
            return;
        }
        if (heap[i * 2].c.compareTo(heap[(i * 2) + 1].c) > 0) {
            if (heap[i].c.compareTo(heap[i * 2].c) < 0) {
                CustomerElement temp2 = heap[i];
                heap[i] = heap[i * 2];
                heap[i * 2] = temp2;
                heap[i * 2].heapIndex = i * 2;
                heap[i].heapIndex = i;
                percDown(i * 2);
            }
        }
        if (heap[(i * 2) + 1].c.compareTo(heap[i * 2].c) > 0) {
            if (heap[i].c.compareTo(heap[(i * 2) + 1].c) < 0) {
                CustomerElement temp3 = heap[i];
                heap[i] = heap[(i * 2) + 1];
                heap[(i * 2) + 1] = temp3;
                heap[(i * 2) + 1].heapIndex = i * 2 + 1;
                heap[i].heapIndex = i;
                percDown((i * 2) + 1);
            }
        }
        return;
    }


    public static void main(String[] args) {


        CustomerHeap heap = new CustomerHeap();

        Customer a = new Customer(10, "Netta");
        Customer b = new Customer(2, "Izhar");
        Customer c = new Customer(3, "Dana");
        Customer d = new Customer(20, "Liran");
        Customer e = new Customer(18, "Static");
        Customer f = new Customer(5, "ABBA");
        Customer g = new Customer(8, "Freddie");
        Customer h = new Customer(12, "Elton");
        Customer j = new Customer(13, "Prince");
        Customer i = new Customer(4, "Tailor");
        Customer l = new Customer(7, "Eric");
        Customer m = new Customer(8, "Rihanna");
        Customer n = new Customer(1, "Idan");
        Customer o = new Customer(6, "Bruno");
        Customer p = new Customer(19, "Shlomo");

        heap.insert(new CustomerElement(a));
        heap.insert(new CustomerElement(b));
        heap.insert(new CustomerElement(c));
        heap.insert(new CustomerElement(d));
        heap.insert(new CustomerElement(e));
        heap.insert(new CustomerElement(g));
        heap.insert(new CustomerElement(f));

        heap.insert(new CustomerElement(h));
        heap.insert(new CustomerElement(j));
        heap.insert(new CustomerElement(i));
        heap.insert(new CustomerElement(l));
        heap.insert(new CustomerElement(m));
        heap.insert(new CustomerElement(n));
        heap.insert(new CustomerElement(o));
        heap.insert(new CustomerElement(p));
        System.out.println(heap.size);//
        System.out.println(heap);//
        heap.remove(8);//remove b, leaf
        heap.remove(6);// remove in middle , m
        heap.remove(1);//remove d, root
        System.out.println();
        System.out.println(heap.size);
        System.out.println(heap);
        System.out.println(heap.findMax());
        System.out.println(heap.extractMax());
        System.out.println(heap.findMax());
        System.out.println(heap.extractMax());
        System.out.println(heap.extractMax());
        System.out.println(heap.extractMax());
        System.out.println(heap.findMax());
        System.out.println(heap.extractMax());
        System.out.println(heap.findMax());



    }
}
