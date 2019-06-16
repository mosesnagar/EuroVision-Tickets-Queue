package old.eurovision;

/**
 * A queue class, implemented as a linked list.
 * The nodes of the linked list are implemnted as the CustomerElement class.
 * <p>
 * IMPORTANT: you may not use any loops/recursions in this class.
 */
public class CustomerQueue {

    CustomerElement first;
    CustomerElement last;

    /**
     * Constructs an empty queue
     */
    public CustomerQueue() {
        first = null;
        last = null;
    }

    /**
     * Removes and returns the first element in the queue
     *
     * @return the first element in the queue
     */
    public CustomerElement dequeue() {
        if (this.last == null || this.first == null) {
            return null;
        }

        CustomerElement c = this.peek();
        this.last = c.prev;

        if (this.last == null) {
            this.first = null;
        } else {
            this.last.next = null;
        }
        return c;
    }

    /**
     * Returns and does not remove the first element in the queue
     *
     * @return the first element in the queue
     */
    public CustomerElement peek() {
        return this.last;
//        return this.first;
    }

    /**
     * Adds a new element to the back of the queue
     *
     * @param node
     */
    public void enqueue(CustomerElement node) {
        node.next = this.first;
        if (this.first != null) {
            this.first.prev = node;
        }
        if (this.last == null) {
            this.last = node;
        }
        this.first = node;
    }

}

