package eurovision;

import java.util.NoSuchElementException;

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
        heap = new CustomerElement[10];
        size = 0;
    }

    /**
     * Returns the size of the heap.
     *
     * @return the size of the heap
     */
    public int size() {
        return this.size;
    }

    /**
     * Inserts a given element into the heap.
     *
     * @param e - the element to be inserted.
     */
    public void insert(CustomerElement e) {
        if (size == heap.length - 1) {
            CustomerElement[] newHeap = new CustomerElement[heap.length * 2];
            for (int i = 1; i < heap.length; i++) {
                newHeap[i] = heap[i];
            }
            this.heap = newHeap;
        }


        heap[size + 1] = e;
        size++;
        e.heapIndex = size;
        percUp(size);
    }

    /**
     * Percolate up the element in the given poisition, by the heap rules
     *
     * @param i
     */
    public void percUp(int i) {
        int position = i;
        if (i > size) return;
        while (position > 1 && heap[position].c.compareTo(heap[position / 2].c) > 0) {
            int j = position / 2;
            CustomerElement temp = heap[j];
            heap[j] = heap[position];
            heap[position] = temp;
            heap[j].heapIndex = j;
            heap[position].heapIndex = position;
            position = j;
        }
    }

    /**
     * Percolate down the element in the given poisition, by the heap rules
     *
     * @param position
     */
    public void percDown(int position) {
        if (position > size / 2) {
            return;
        }
        while (position <= size / 2) {
            if (position == size / 2) {
                if (heap[position].c.compareTo(heap[position * 2].c) < 0) {
                    CustomerElement temp = heap[position];

                    heap[position] = heap[position * 2];
                    heap[position * 2] = temp;
                    heap[position].heapIndex = position;
                    heap[position * 2].heapIndex = position * 2;
                }
                position = position * 2;
            } else {
                CustomerElement max;
                if (heap[position * 2].c.compareTo(heap[position * 2 + 1].c) > 0) {
                    max = heap[position * 2];
                } else if (heap[position * 2].c.compareTo(heap[position * 2 + 1].c) < 0) {
                    max = heap[position * 2 + 1];
                } else {
                    return;
                }
                int index = max.heapIndex;
                if (heap[position].c.compareTo(max.c) < 0) {
                    CustomerElement temp = heap[position];

                    heap[position] = heap[index];
                    heap[index] = temp;

                    heap[position].heapIndex = position;
                    heap[index].heapIndex = index;
                }
                position = index;
            }
            if (position == 1) {
                return;
            }
        }

    }

    /**
     * Returns and does not remove the element which wraps the cutomer with maximal priority.
     *
     * @return the element which wraps the cutomer with maximal priority.
     */
    public CustomerElement findMax() {
        return this.heap[1];
    }

    /**
     * Returns and removes the element which wraps the cutomer with maximal priority.
     *
     * @return the element which wraps the cutomer with maximal priority.
     */
    public CustomerElement extractMax() {
        CustomerElement top = this.findMax();
        if (top == null) return null;
        heap[1] = heap[size];
        heap[1].heapIndex = 1;
        heap[size] = null;
        size--;
        percDown(1);
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
        if (index < 1 || index > size) {
            return;
        }
        if (size == 1) {
            heap[1] = null;
            size--;
        } else {
            if (index == 1) {
                extractMax();
            } else {
                heap[index] = heap[size];
                heap[index].heapIndex = index;
                heap[size] = null;
                size--;
                percUp(index);
                percDown(index);
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
