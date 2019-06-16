package old.eurovision;

/**
 * A heap, implemnted as an array.
 * The elemnts in the heap are instances of the class CustomerElement,
 * and the heap is ordered according to the Customer instances wrapped by those objects.
 * <p>
 * IMPORTANT: Except in the constuctor no single function may loop/recurse through all elements in the heap.
 * You may only use loops which look at a small fraction of the heap.
 */
public class CustomerHeap {

    /*
     * The array in which the elements are kept according to the heap order.
     * The following must always hold true:
     * 			if i < size then heap[i].heapIndex == i
     */
    CustomerElement[] heap;
    int size; // the number of elements in the heap, neccesarilty size <= heap.length

    /**
     * Creates an empty heap which can initally accomodate 10 elements.
     */
    public CustomerHeap() {
        this.heap = new CustomerElement[10];
        this.size = 0;
    }

    /**
     * Returns the size of the heap.
     *
     * @return the size of the heap
     */
    public int size() {
        return this.size;
    }

    public void percUp(int i) {
        int t = size;
        while (t > 1 && heap[t].c.compareTo(heap[t / 2].c) > 0) {
            int j = t / 2;
            CustomerElement temp = heap[j];
            heap[j] = heap[t];
            heap[t] = temp;
            heap[t].heapIndex = t;
            heap[j].heapIndex = j;
            t = j;
        }
    }


    /**
     * Inserts a given element into the heap.
     *
     * @param e - the element to be inserted.
     */
    public void insert(CustomerElement e) {
        if (size == heap.length - 1) {
            CustomerElement[] newHeap = new CustomerElement[heap.length * 2];
            for (int i = 0; i < heap.length; i++) {
                newHeap[i] = heap[i];
            }
            this.heap = newHeap;
        }
        this.heap[size + 1] = e;
        this.size++;
        e.heapIndex = size;
        percUp(size);
//        int i = size;
//        while (i > 1 && heap[i].c.compareTo(heap[i / 2].c) > 0) {
//            int j = i / 2;
//            CustomerElement temp = heap[j];
//            heap[j] = heap[i];
//            heap[i] = temp;
//            heap[i].heapIndex = i;
//            heap[j].heapIndex = j;
//            i = j;
//        }
    }


    /**
     * Returns and does not remove the element which wraps the cutomer with maximal priority.
     *
     * @return the element which wraps the cutomer with maximal priority.
     */
    public CustomerElement findMax() {
        return this.heap[1];
    }

    public void percDown(int i) {
//        int t = i;
//        while (t <= size / 2) {
//            int j;
//            if (heap[t].c.compareTo(heap[t * 2].c) < 0) {
//                j = t * 2;
//            } else if (heap[t].c.compareTo(heap[t * 2 + 1].c) < 0) {
//                j = t * 2 + 1;
//            } else {
//                return;
//            }
//            CustomerElement temp = heap[j];
//            heap[j] = heap[t];
//            heap[t] = temp;
//            heap[t].heapIndex = t;
//            heap[j].heapIndex = j;
//            t = j;
//        }
    }

    /**
     * Returns and removes the element which wraps the cutomer with maximal priority.
     *
     * @return the element which wraps the cutomer with maximal priority.
     */
    public CustomerElement extractMax() {
        CustomerElement top = findMax();
        if (top == null) return null;
        this.heap[1] = this.heap[size];
        heap[size] = null;
        this.size--;
        percDown(1);
//        int i = 1;
//        while (i <= size / 2) {
//            if (heap[i].c.compareTo(heap[i * 2].c) < 0) {
//                int j = i * 2;
//                CustomerElement temp = heap[j];
//                heap[j] = heap[i];
//                heap[i] = temp;
//                heap[i].heapIndex = i;
//                heap[j].heapIndex = j;
//                i = j;
//            } else if (heap[i].c.compareTo(heap[i * 2 + 1].c) < 0) {
//                int j = i * 2 + 1;
//                CustomerElement temp = heap[j];
//                heap[j] = heap[i];
//                heap[i] = temp;
//                heap[i].heapIndex = i;
//                heap[j].heapIndex = j;
//                i = j;
//            } else {
//                break;
//            }
//        }


        return top;
    }

    /**
     * Removes the element located at the given index.
     * <p>
     * Note: this function is not part of the standard heap API.
     * Make sure you understand how to implement it why it is required.
     * There are several ways this function could be implemented.
     * No matter how you choose to implement it, you need to consider the different possible edge cases.
     *
     * @param index
     */
    public void remove(int index) {
        if (index < 0 || index > size || size == 0) {
            return;
        }
        if (index == 0 && size == 1) {
            heap[1] = null;
        } else {
            heap[index] = heap[size];
            heap[size] = null;
            size--;
            int i = index;
            while (i <= size / 2) {
                if (heap[i].c.compareTo(heap[i * 2].c) < 0) {
                    int j = i * 2;
                    CustomerElement temp = heap[j];
                    heap[j] = heap[i];
                    heap[i] = temp;
                    heap[i].heapIndex = i;
                    heap[j].heapIndex = j;
                    i = j;
                } else if (heap[i].c.compareTo(heap[i * 2 + 1].c) < 0) {
                    int j = i * 2 + 1;
                    CustomerElement temp = heap[j];
                    heap[j] = heap[i];
                    heap[i] = temp;
                    heap[i].heapIndex = i;
                    heap[j].heapIndex = j;
                    i = j;
                } else {
                    break;
                }
            }
        }
    }


    public static void main(String[] args) {
        /*
         * A basic test for the heap.
         * You should be able to run this before implementing the queue.
         *
         * Expected outcome:
         * 	customer: Netta, priority: 10
         *	customer: Liran, priority: 20
         *	customer: Liran, priority: 20
         *	customer: Netta, priority: 10
         *	customer: Dana, priority: 3
         *	customer: Izhar, priority: 2
         *
         */
        CustomerHeap heap = new CustomerHeap();
        Customer a = new Customer(10, "Netta");
        Customer b = new Customer(2, "Izhar");
        Customer c = new Customer(3, "Dana");
        Customer d = new Customer(20, "Liran");

        heap.insert(new CustomerElement(a));
        System.out.println(heap.findMax());

        heap.insert(new CustomerElement(b));
        heap.insert(new CustomerElement(c));
        heap.insert(new CustomerElement(d));
        System.out.println(heap.findMax());
        System.out.println(heap.extractMax());
        System.out.println(heap.extractMax());
        System.out.println(heap.extractMax());
        System.out.println(heap.extractMax());
    }
}
