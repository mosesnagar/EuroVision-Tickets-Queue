package eurovision;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EurovisionTests {

    private boolean equalCustomers(Customer c1, Customer c2) {
        return c1.name.equals(c2.name) && c1.priority == c2.priority;
    }

    //##########################
    //####  Customer Tests  ####
    //##########################
    @Test(timeout = 5000)
    public void testCompareToSameCustomer() {
        Customer c = new Customer(6, "alice");

        int compareResult = c.compareTo(c);
        assertEquals("Customer testCompareToSameCustomer: Customers are equal to themselves, compareTo should return 0\n", 0, compareResult);

    }

    @Test(timeout = 5000)
    public void testCompareToDifferentPriority() {
        Customer c1 = new Customer(6, "alice");
        Customer c2 = new Customer(5, "bob");

        int compareResult = c1.compareTo(c2);
        boolean isPositive = compareResult > 0;
        assertEquals("Customer testCompareToDifferentPriority: If this customer has bigger priority, a positive number should be returned\n", true, isPositive);

        compareResult = c2.compareTo(c1);
        boolean isNegative = compareResult < 0;
        assertEquals("Customer testCompareToDifferentPriority: If this customer has smaller priority, a negative number should be returned\n", true, isNegative);
    }

    @Test(timeout = 5000)
    public void testCompareToSamePriority() {
        Customer c1 = new Customer(6, "bob");
        Customer c2 = new Customer(6, "alice");

        int compareResult = c1.compareTo(c2);
        boolean isPositive = compareResult > 0;
        assertEquals("Customer testCompareToSamePriority: If the customers have equal priority, the one with the bigger lexicographic order should be prioritized\n", true, isPositive);

        compareResult = c2.compareTo(c1);
        boolean isNegative = compareResult < 0;
        assertEquals("Customer testCompareToSamePriority: If the customers have equal priority, the one with the bigger lexicographic order should be prioritized\n", true, isNegative);
    }


    //#################################
    //####  CustomerElement Tests  ####
    //#################################

    @Test(timeout = 5000)
    public void testConstructor() {
        Customer c = new Customer(6, "bob");
        CustomerElement ce = new CustomerElement(c);
        assertEquals("CustomerElement testConstructor: customer's name was not set properly", true, ce.c.name == c.name);
        assertEquals("CustomerElement testConstructor: customer's priority was not set properly", true, ce.c.priority == c.priority);
    }


    //###############################
    //####  CustomerQueue Tests  ####
    //###############################

    @Test(timeout = 5000)
    public void testDequeue() {
        CustomerQueue queue = new CustomerQueue();
        Customer alice = new Customer(1, "alice");
        queue.enqueue(new CustomerElement(alice));
        assertEquals("CustomerQueue testDequeue: Dequeue did not return the expected customer", true, equalCustomers(queue.dequeue().c, alice));
    }

    @Test(timeout = 5000)
    public void testEnqueue() {
        CustomerQueue queue = new CustomerQueue();
        Customer alice = new Customer(1, "alice");
        queue.enqueue(new CustomerElement(alice));
        assertEquals("CustomerQueue testEnqueue: Enqueue did not work as expected", true, equalCustomers(queue.first.c, alice));
    }

    @Test(timeout = 5000)
    public void testFirstBasic() {
        CustomerQueue queue = new CustomerQueue();
        String firstCustomerName = "customer 1";
        for (int i = 1; i <= 30; i++) {
            queue.enqueue(new CustomerElement(new Customer(i, "customer " + i)));
            assertEquals("CustomerQueue testFirstBasic: First does not point the first enqueued customer", firstCustomerName, queue.last.c.name);
        }
    }

    @Test(timeout = 5000)
    public void testFirstAfterDequeue() {
        CustomerQueue queue = new CustomerQueue();
        int firstCustomerIndex = 1;
        for (int i = 1; i <= 30; i++) {
            queue.enqueue(new CustomerElement(new Customer(i, "customer " + i)));
        }

        assertEquals("CustomerQueue testFirstAfterDequeue: First does not point the first enqueued customer", "customer " + firstCustomerIndex, queue.last.c.name);
        queue.dequeue();
        firstCustomerIndex++;
        assertEquals("CustomerQueue testFirstAfterDequeue: First was not updated properly after dequeue", "customer " + firstCustomerIndex, queue.last.c.name);
        queue.dequeue();
        firstCustomerIndex++;
        assertEquals("CustomerQueue testFirstAfterDequeue: First was not updated properly after dequeue", "customer " + firstCustomerIndex, queue.last.c.name);
    }

    @Test(timeout = 5000)
    public void testLastBasic() {
        CustomerQueue queue = new CustomerQueue();
        for (int i = 1; i <= 30; i++) {
            String customerName = "customer " + i;
            queue.enqueue(new CustomerElement(new Customer(i, customerName)));
            assertEquals("CustomerQueue testLastBasic: Last does not point the last enqueued customer", customerName, queue.first.c.name);
        }
    }

    @Test(timeout = 5000)
    public void testLastAfterDequeue() {
        CustomerQueue queue = new CustomerQueue();
        String customerName = "";
        for (int i = 1; i <= 30; i++) {
            customerName = "customer " + i;
            queue.enqueue(new CustomerElement(new Customer(i, customerName)));
        }

        queue.dequeue();
        assertEquals("CustomerQueue testLastAfterDequeue: Last should not change after dequeue", customerName, queue.first.c.name);
    }

    @Test(timeout = 5000)
    public void testFIFO() {
        CustomerQueue queue = new CustomerQueue();
        Customer alice = new Customer(1, "alice");
        Customer bob = new Customer(2, "bob");
        Customer charlie = new Customer(3, "charlie");
        queue.enqueue(new CustomerElement(alice));
        queue.enqueue(new CustomerElement(bob));
        queue.enqueue(new CustomerElement(charlie));
        assertEquals("CustomerQueue testFIFO: The queue does not follow the First In First Out principle", true, equalCustomers(queue.dequeue().c, alice));
        assertEquals("CustomerQueue testFIFO: The queue does not follow the First In First Out principle", true, equalCustomers(queue.dequeue().c, bob));
        assertEquals("CustomerQueue testFIFO: The queue does not follow the First In First Out principle", true, equalCustomers(queue.dequeue().c, charlie));
    }

    @Test(timeout = 5000)
    public void testPeek() {
        CustomerQueue queue = new CustomerQueue();
        Customer alice = new Customer(1, "alice");
        Customer bob = new Customer(2, "bob");
        Customer charlie = new Customer(3, "charlie");
        queue.enqueue(new CustomerElement(alice));
        queue.enqueue(new CustomerElement(bob));
        queue.enqueue(new CustomerElement(charlie));

        Customer peeked = queue.peek().c;
        assertEquals("CustomerQueue testPeek: peek returned wrong element", true, equalCustomers(peeked, alice));

        Customer dequeued = queue.dequeue().c;
        assertEquals("CustomerQueue testPeek: peek should not change the queue", true, equalCustomers(peeked, dequeued));
    }

    @Test(timeout = 5000)
    public void testNextPrevBasic() {
        Customer a = new Customer(6, "a");
        Customer b = new Customer(6, "b");
        Customer c = new Customer(6, "c");
        CustomerQueue queue = new CustomerQueue();
        queue.enqueue(new CustomerElement(a));
        queue.enqueue(new CustomerElement(b));
        queue.enqueue(new CustomerElement(c));
        assertEquals("CustomerQueue testNextPrevBasic: next/prev of CustomerElement were not set correctly (1)", true, equalCustomers(queue.first.next.next.c, queue.last.c));
        assertEquals("CustomerQueue testNextPrevBasic: next/prev of CustomerElement were not set correctly (2)", true, equalCustomers(queue.last.prev.prev.c, c));
        assertEquals("CustomerQueue testNextPrevBasic: next/prev of CustomerElement were not set correctly (3)", true, equalCustomers(queue.first.next.prev.c, queue.first.c));
        assertEquals("CustomerQueue testNextPrevBasic: next/prev of CustomerElement were not set correctly (4)", true, equalCustomers(queue.last.prev.next.c, a));
    }

    @Test(timeout = 30000)
    public void testQueueLoad() {
        int N = 0;
        CustomerQueue queue = new CustomerQueue();
        try {
            while (N < 100 * 1000) {
                N++;
                queue.enqueue(new CustomerElement(new Customer(N, "customer " + N)));
            }

        } catch (Exception e) {
            fail("CustomerQueue testQueueLoad: Enqueue load test failed- the queue should support any amount of customers, yours crashed with " + N + " customers");
        }
    }


    //##############################
    //####  CustomerHeap Tests  ####
    //##############################

    @Test(timeout = 5000)
    public void testExampleCustomerHeapTest() {
        CustomerHeap heap = new CustomerHeap();
        Customer a = new Customer(10, "netta");
        Customer b = new Customer(2, "izhar");
        Customer c = new Customer(3, "dana");
        Customer d = new Customer(20, "liran");

        heap.insert(new CustomerElement(a));
        assertEquals("CustomerHeap testExampleCustomerHeapTest: example test provided with the code does not work", true, equalCustomers(heap.findMax().c, a));

        heap.insert(new CustomerElement(b));
        heap.insert(new CustomerElement(c));
        heap.insert(new CustomerElement(d));
        assertEquals("CustomerHeap testExampleCustomerHeapTest: example test provided with the code does not work", true, equalCustomers(heap.findMax().c, d));
        assertEquals("CustomerHeap testExampleCustomerHeapTest: example test provided with the code does not work", true, equalCustomers(heap.extractMax().c, d));
        assertEquals("CustomerHeap testExampleCustomerHeapTest: example test provided with the code does not work", true, equalCustomers(heap.extractMax().c, a));
        assertEquals("CustomerHeap testExampleCustomerHeapTest: example test provided with the code does not work", true, equalCustomers(heap.extractMax().c, c));
        assertEquals("CustomerHeap testExampleCustomerHeapTest: example test provided with the code does not work", true, equalCustomers(heap.extractMax().c, b));
    }

    @Test(timeout = 5000)
    public void testExtractMax() {
        CustomerHeap heap = new CustomerHeap();
        Customer alice = new Customer(1, "alice");
        heap.insert(new CustomerElement(alice));
        assertEquals("CustomerHeap testExtractMax: ExtractMax did not return the expected customer", true, equalCustomers(heap.extractMax().c, alice));
        assertEquals("CustomerHeap testExtractMax: size was not updated correctly", 0, heap.size());
    }

    @Test(timeout = 5000)
    public void testInsert() {
        CustomerHeap heap = new CustomerHeap();
        Customer alice = new Customer(1, "alice");
        heap.insert(new CustomerElement(alice));

        int i = 0;
        CustomerElement e = heap.heap[i++];
        while (i < heap.heap.length && e == null)
            e = heap.heap[i++];

        if (e == null)
            fail("CustomerHeap testInsert: Insert did not work as expected");

        assertEquals("CustomerHeap testInsert: Insert did not work as expected", true, equalCustomers(e.c, alice));
        assertEquals("CustomerHeap testExtractMax: size was not updated correctly", 1, heap.size());
    }

    @Test(timeout = 5000)
    public void testFindMax() {
        CustomerHeap heap = new CustomerHeap();
        Customer alice = new Customer(1, "alice");
        Customer bob = new Customer(2, "bob");
        Customer charlie = new Customer(3, "charlie");
        heap.insert(new CustomerElement(alice));
        heap.insert(new CustomerElement(bob));
        heap.insert(new CustomerElement(charlie));

        Customer maxCustomer = heap.findMax().c;
        assertEquals("CustomerHeap testFindMax: findMax returned wrong element", true, equalCustomers(maxCustomer, charlie));

        Customer dequeued = heap.extractMax().c;
        assertEquals("CustomerHeap testFindMax: findMax should not change the queue", true, equalCustomers(maxCustomer, dequeued));
    }

    @Test(timeout = 30000)
    public void testHeapLoad() {
        int N = 0;
        CustomerHeap heap = new CustomerHeap();
        try {
            while (N < 100 * 1000) {
                N++;
                heap.insert(new CustomerElement(new Customer(N, "customer " + N)));
            }

        } catch (Exception e) {
            fail("CustomerHeap testHeapLoad: Insert load test failed- the heap should support any amount of customers, yours crashed with " + N + " customers");
        }
    }

    @Test(timeout = 5000)
    public void testHeapIndex() {
        CustomerHeap heap = new CustomerHeap();
        Customer alice = new Customer(1, "alice");
        Customer bob = new Customer(2, "bob");
        Customer charlie = new Customer(3, "charlie");
        CustomerElement aliceCe = new CustomerElement(alice);
        CustomerElement bobCe = new CustomerElement(bob);
        CustomerElement charlieCe = new CustomerElement(charlie);
        heap.insert(aliceCe);
        heap.insert(bobCe);
        heap.insert(charlieCe);
        try {
            assertEquals("CustomerHeap testHeapIndex: heap index was not set correctly", true, equalCustomers(heap.heap[aliceCe.heapIndex].c, aliceCe.c));
            assertEquals("CustomerHeap testHeapIndex: heap index was not set correctly", true, equalCustomers(heap.heap[bobCe.heapIndex].c, bobCe.c));
            assertEquals("CustomerHeap testHeapIndex: heap index was not set correctly", true, equalCustomers(heap.heap[charlieCe.heapIndex].c, charlieCe.c));
        } catch (Exception e) {
            fail("CustomerHeap testHeapIndex: Error: " + e.getMessage());
        }

    }

    @Test(timeout = 5000)
    public void testInitialSize() {
        CustomerHeap heap = new CustomerHeap();
        assertEquals("CustomerHeap testInitialSize: heap should contain 10 elements initially", 10, heap.heap.length);
    }

    @Test(timeout = 5000)
    public void testSizeAfterRemove() {
        CustomerHeap heap = new CustomerHeap();
        Customer alice = new Customer(1, "alice");
        Customer bob = new Customer(2, "bob");
        Customer charlie = new Customer(3, "charlie");
        heap.insert(new CustomerElement(alice));
        heap.insert(new CustomerElement(bob));
        heap.insert(new CustomerElement(charlie));
        heap.remove(2);
        assertEquals("CustomerHeap: testSizeAfterRemove: size was not updated after remove operation", 2, heap.size());
        heap.remove(1);
        assertEquals("CustomerHeap: testSizeAfterRemove: size was not updated after remove operation", 1, heap.size());
        heap.remove(1);
        assertEquals("CustomerHeap: testSizeAfterRemove: size was not updated after remove operation", 0, heap.size());

    }

    @Test(timeout = 5000)
    public void testRemoveIllegalIndex() {
        CustomerHeap heap = new CustomerHeap();
        Customer alice = new Customer(1, "alice");
        Customer bob = new Customer(2, "bob");
        Customer charlie = new Customer(3, "charlie");
        heap.insert(new CustomerElement(alice));
        heap.insert(new CustomerElement(bob));
        heap.insert(new CustomerElement(charlie));
        try {
            heap.remove(-1);
            heap.remove(5);
            heap.remove(15);
            assertEquals("CustomerHeap: testRemoveIllegalIndex: size was changed even though no item was removed", 3, heap.size());

        } catch (Exception e) {
            fail("CustomerHeap testRemoveIllegalIndex: The program Should not crash when trying to remove item at illegal index: " + e.getMessage());
        }
    }

    //#################################
    //####  EuroVisionQueue Tests  ####
    //#################################

    @Test(timeout = 5000)
    public void testExampleEuroVisionQueueTest() {
        /*
         * A basic test to check your class.
    	 * Expected outcome:
    	 * 	customer: Liran GreyShirt, priority: 20
    	 *	customer: Netta Barzilay, priority: 10
    	 *	customer: Izhar Ashdot, priority: 2
    	 *	customer: Alan Turing, priority: 100
		 *  customer: Dana International, priority: 3
	     */
        EuroVisionQueue q = new EuroVisionQueue();
        Customer a = new Customer(10, "netta barzilay");
        Customer b = new Customer(2, "izhar ashdot");
        Customer c = new Customer(3, "dana international");
        Customer d = new Customer(20, "liran greyshirt");
        Customer e = new Customer(100, "alan turing");


        q.addCustomer(a);
        q.addCustomer(b);
        q.addCustomer(c);
        q.addCustomer(d);
        assertEquals("EuroVisionQueue: testExampleEuroVisionQueueTest: Example test failed, see expected outcome inside EuroVisionQueue' main"
                , true, equalCustomers(q.servePriorityCustomer(), d));
        assertEquals("EuroVisionQueue: testExampleEuroVisionQueueTest: Example test failed, see expected outcome inside EuroVisionQueue' main"
                , true, equalCustomers(q.servePriorityCustomer(), a));
        q.addCustomer(e);
        assertEquals("EuroVisionQueue: testExampleEuroVisionQueueTest: Example test failed, see expected outcome inside EuroVisionQueue' main"
                , true, equalCustomers(q.serveRegularCustomer(), b));
        assertEquals("EuroVisionQueue: testExampleEuroVisionQueueTest: Example test failed, see expected outcome inside EuroVisionQueue' main"
                , true, equalCustomers(q.servePriorityCustomer(), e));
        assertEquals("EuroVisionQueue: testExampleEuroVisionQueueTest: Example test failed, see expected outcome inside EuroVisionQueue' main"
                , true, equalCustomers(q.serveRegularCustomer(), c));

        assertEquals("EuroVisionQueue: testExampleEuroVisionQueueTest: Example test failed, see expected outcome inside EuroVisionQueue' main"
                , true, a == a);
        assertEquals("EuroVisionQueue: testExampleEuroVisionQueueTest: Example test failed, see expected outcome inside EuroVisionQueue' main"
                , false, a == b);
    }

    @Test(timeout = 5000)
    public void testAddOneCustomer() {
        EuroVisionQueue q = new EuroVisionQueue();
        Customer a = new Customer(10, "a");
        q.addCustomer(a);
        assertEquals("EuroVisionQueue: testAddOneCustomer: The customer was not added to the heap"
                , true, equalCustomers(q.heap.findMax().c, a));
        assertEquals("EuroVisionQueue: testAddOneCustomer: The heap size was not updated"
                , 1, q.heap.size());
        assertEquals("EuroVisionQueue: testAddOneCustomer: The customer was not added to the queue correctly- peek"
                , true, equalCustomers(q.q.peek().c, a));
        assertEquals("EuroVisionQueue: testAddOneCustomer: The customer was not added to the queue correctly- last"
                , true, equalCustomers(q.q.last.c, a));
        assertEquals("EuroVisionQueue: testAddOneCustomer: The customer was not added to the queue correctly- first"
                , true, equalCustomers(q.q.first.c, a));
        assertEquals("EuroVisionQueue: testAddOneCustomer: The customer was not added to the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.q.peek().heapIndex].c, a));
    }

    @Test(timeout = 5000)
    public void testAddthreeCustomers() {
        EuroVisionQueue q = new EuroVisionQueue();
        Customer a = new Customer(10, "a");
        Customer b = new Customer(14, "b");
        Customer c = new Customer(12, "c");
        q.addCustomer(a);
        q.addCustomer(b);
        q.addCustomer(c);
        assertEquals("EuroVisionQueue: testAddthreeCustomers: The customer was not added to the heap"
                , true, equalCustomers(q.heap.findMax().c, b));
        assertEquals("EuroVisionQueue: testAddthreeCustomers: The heap size was not updated"
                , 3, q.heap.size());
        assertEquals("EuroVisionQueue: testAddthreeCustomers: The customer was not added to the queue correctly- peek"
                , true, equalCustomers(q.q.peek().c, a));
        assertEquals("EuroVisionQueue: testAddthreeCustomers: The customer was not added to the queue correctly- last"
                , true, equalCustomers(q.q.first.c, c));
        assertEquals("EuroVisionQueue: testAddthreeCustomers: The customer was not added to the queue correctly- first"
                , true, equalCustomers(q.q.last.c, a));
        assertEquals("EuroVisionQueue: testAddthreeCustomers: The customer was not added to the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.q.peek().heapIndex].c, a));
        assertEquals("EuroVisionQueue: testAddthreeCustomers: The customer was not added to the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.q.first.heapIndex].c, c));
        assertEquals("EuroVisionQueue: testAddthreeCustomers: The customer was not added to the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.heap.findMax().heapIndex].c, b));
    }

    //serve a regular customer, which also happens to be the most prioritized customer
//    @Test(timeout = 5000)
//    public void testServeRegularCustomer() {
//        EuroVisionQueue q = new EuroVisionQueue();
//        Customer a = new Customer(10, "a");
//        Customer b = new Customer(14, "b");
//        Customer c = new Customer(16, "c");
//        q.addCustomer(a);
//        q.addCustomer(b);
//        q.addCustomer(c);
//        q.serveRegularCustomer();//a is served
//        assertEquals("EuroVisionQueue: testServeRegularCustomer: FindMax returned wrong customer"
//                , true, equalCustomers(q.heap.findMax().c, c));
//        assertEquals("EuroVisionQueue: testServeRegularCustomer: The heap size was not updated"
//                , 2, q.heap.size());
//        assertEquals("EuroVisionQueue: testServeRegularCustomer: The customer was not removed from the queue correctly- peek"
//                , true, equalCustomers(q.q.peek().c, b));
//        assertEquals("EuroVisionQueue: testServeRegularCustomer: The customer was not removed from the queue correctly- last"
//                , true, equalCustomers(q.q.first.c, c));
//        assertEquals("EuroVisionQueue: testServeRegularCustomer: The customer was not removed from the queue correctly- first"
//                , true, equalCustomers(q.q.last.c, b));
//        assertEquals("EuroVisionQueue: testServeRegularCustomer: The customer was not removed from the queue correctly- heapIndex"
//                , true, equalCustomers(q.heap.heap[q.q.peek().heapIndex].c, b));
//        assertEquals("EuroVisionQueue: testServeRegularCustomer: The customer was not removed from the queue correctly- heapIndex"
//                , true, equalCustomers(q.heap.heap[q.q.last.heapIndex].c, c));
//    }

    @Test(timeout = 5000)
    public void testServePriorityCustomerMiddle() {
        EuroVisionQueue q = new EuroVisionQueue();
        Customer a = new Customer(10, "a");
        Customer b = new Customer(14, "b");
        Customer c = new Customer(12, "c");
        q.addCustomer(a);
        q.addCustomer(b);
        q.addCustomer(c);
        q.servePriorityCustomer();//b is served
        assertEquals("EuroVisionQueue: testServePriorityCustomerMiddle: FindMax returned wrong customer"
                , true, equalCustomers(q.heap.findMax().c, c));
        assertEquals("EuroVisionQueue: testServePriorityCustomerMiddle: The heap size was not updated"
                , 2, q.heap.size());
        assertEquals("EuroVisionQueue: testServePriorityCustomerMiddle: The customer was not removed from the queue correctly- peek"
                , true, equalCustomers(q.q.peek().c, a));
        assertEquals("EuroVisionQueue: testServePriorityCustomerMiddle: The customer was not removed from the queue correctly- last"
                , true, equalCustomers(q.q.first.c, c));
        assertEquals("EuroVisionQueue: testServePriorityCustomerMiddle: The customer was not removed from the queue correctly- first"
                , true, equalCustomers(q.q.last.c, a));
        assertEquals("EuroVisionQueue: testServePriorityCustomerMiddle: The customer was not removed from the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.q.peek().heapIndex].c, a));
        assertEquals("EuroVisionQueue: testServePriorityCustomerMiddle: The customer was not removed from the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.q.last.heapIndex].c, c));
    }

    @Test(timeout = 5000)
    public void testServePriorityCustomerLast() {
        EuroVisionQueue q = new EuroVisionQueue();
        Customer a = new Customer(10, "a");
        Customer b = new Customer(14, "b");
        Customer c = new Customer(16, "c");
        q.addCustomer(a);
        q.addCustomer(b);
        q.addCustomer(c);
        q.servePriorityCustomer();//c is served
        assertEquals("EuroVisionQueue: testServePriorityCustomerLast: FindMax returned wrong customer"
                , true, equalCustomers(q.heap.findMax().c, b));
        assertEquals("EuroVisionQueue: testServePriorityCustomerLast: The heap size was not updated"
                , 2, q.heap.size());
        assertEquals("EuroVisionQueue: testServePriorityCustomerLast: The customer was not removed from the queue correctly- peek"
                , true, equalCustomers(q.q.peek().c, a));
        assertEquals("EuroVisionQueue: testServePriorityCustomerLast: The customer was not removed from the queue correctly- last was not updated"
                , true, equalCustomers(q.q.first.c, b));
        assertEquals("EuroVisionQueue: testServePriorityCustomerLast: The customer was not removed from the queue correctly- first"
                , true, equalCustomers(q.q.last.c, a));
        assertEquals("EuroVisionQueue: testServePriorityCustomerLast: The customer was not removed from the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.q.peek().heapIndex].c, a));
        assertEquals("EuroVisionQueue: testServePriorityCustomerLast: The customer was not removed from the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.q.first.heapIndex].c, b));
    }

    //serve a regular customer, which also happens to be the most prioritized customer
    @Test(timeout = 5000)
    public void testServeRegularCustomerAlsoPriority() {
        EuroVisionQueue q = new EuroVisionQueue();
        Customer a = new Customer(15, "a");
        Customer b = new Customer(14, "b");
        Customer c = new Customer(12, "c");
        q.addCustomer(a);
        q.addCustomer(b);
        q.addCustomer(c);
        q.serveRegularCustomer();//a is served
        assertEquals("EuroVisionQueue: testServeRegularCustomerAlsoPriority: FindMax returned wrong customer"
                , true, equalCustomers(q.heap.findMax().c, b));
        assertEquals("EuroVisionQueue: testServeRegularCustomerAlsoPriority: The heap size was not updated"
                , 2, q.heap.size());
        assertEquals("EuroVisionQueue: testServeRegularCustomerAlsoPriority: The customer was not removed from the queue correctly- peek"
                , true, equalCustomers(q.q.peek().c, b));
        assertEquals("EuroVisionQueue: testServeRegularCustomerAlsoPriority: The customer was not removed from the queue correctly- last"
                , true, equalCustomers(q.q.first.c, c));
        assertEquals("EuroVisionQueue: testServeRegularCustomerAlsoPriority: The customer was not removed from the queue correctly- first"
                , true, equalCustomers(q.q.last.c, b));
        assertEquals("EuroVisionQueue: testServeRegularCustomerAlsoPriority: The customer was not removed from the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.q.peek().heapIndex].c, b));
        assertEquals("EuroVisionQueue: testServeRegularCustomerAlsoPriority: The customer was not removed from the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.q.first.heapIndex].c, c));
    }

    //serve a priority customer, which also happens to be the first regular customer
    @Test(timeout = 5000)
    public void testServePriorityCustomerAlsoFirst() {
        EuroVisionQueue q = new EuroVisionQueue();
        Customer a = new Customer(15, "a");
        Customer b = new Customer(14, "b");
        Customer c = new Customer(12, "c");
        q.addCustomer(a);
        q.addCustomer(b);
        q.addCustomer(c);
        q.servePriorityCustomer();//a is served
        assertEquals("EuroVisionQueue: testServePriorityCustomerAlsoFirst: FindMax returned wrong customer"
                , true, equalCustomers(q.heap.findMax().c, b));
        assertEquals("EuroVisionQueue: testServePriorityCustomerAlsoFirst: The heap size was not updated"
                , 2, q.heap.size());
        assertEquals("EuroVisionQueue: testServePriorityCustomerAlsoFirst: The customer was not removed from the queue correctly- peek"
                , true, equalCustomers(q.q.peek().c, b));
        assertEquals("EuroVisionQueue: testServePriorityCustomerAlsoFirst: The customer was not removed from the queue correctly- last"
                , true, equalCustomers(q.q.first.c, c));
        assertEquals("EuroVisionQueue: testServePriorityCustomerAlsoFirst: The customer was not removed from the queue correctly- first"
                , true, equalCustomers(q.q.last.c, b));
        assertEquals("EuroVisionQueue: testServePriorityCustomerAlsoFirst: The customer was not removed from the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.q.peek().heapIndex].c, b));
        assertEquals("EuroVisionQueue: testServePriorityCustomerAlsoFirst: The customer was not removed from the queue correctly- heapIndex"
                , true, equalCustomers(q.heap.heap[q.q.first.heapIndex].c, c));
    }

    @Test(timeout = 5000)
    public void testNextPrevRemovingMiddleNode() {
        Customer a = new Customer(6, "A");
        Customer b = new Customer(10, "B");
        Customer c = new Customer(6, "C");
        EuroVisionQueue queue = new EuroVisionQueue();
        queue.addCustomer(a);
        queue.addCustomer(b);
        queue.addCustomer(c);
        queue.servePriorityCustomer();//serve b, which is in the middle of the queue
        assertEquals("EuroVisionQueue testNextPrevBasic: next/prev of CustomerElement were not set correctly (1)", true, equalCustomers(queue.q.first.next.c, queue.q.last.c));
        assertEquals("EuroVisionQueue testNextPrevBasic: next/prev of CustomerElement were not set correctly (2)", true, equalCustomers(queue.q.last.prev.c, c));
        assertEquals("EuroVisionQueue testNextPrevBasic: next/prev of CustomerElement were not set correctly (3)", true, equalCustomers(queue.q.first.next.prev.c, queue.q.first.c));
        assertEquals("EuroVisionQueue testNextPrevBasic: next/prev of CustomerElement were not set correctly (4)", true, equalCustomers(queue.q.last.prev.next.c, a));
        assertEquals("EuroVisionQueue testNextPrevBasic: next/prev of CustomerElement were not set correctly (5)", true, equalCustomers(queue.q.last.c, a));
        assertEquals("EuroVisionQueue testNextPrevBasic: next/prev of CustomerElement were not set correctly (6)", true, equalCustomers(queue.q.last.prev.c, queue.q.first.c));
    }

    @Test(timeout = 60000)
    public void testEuroVisionQueueFlow6() {
        testEuroVisionQueueFlow(6);
    }

    @Test(timeout = 60000)
    public void testEuroVisionQueueFlow20() {
        testEuroVisionQueueFlow(20);
    }

    @Test(timeout = 60000)
    public void testEuroVisionQueueFlow40() {
        testEuroVisionQueueFlow(40);
    }


    @Test(timeout = 60000)
    public void testEuroVisionQueueLoad() {
        int N = 0;
        EuroVisionQueue q = new EuroVisionQueue();
        try {
            while (N < 100 * 1000) {
                N++;
                q.addCustomer((new Customer(N, "customer " + N)));
            }

        } catch (Exception e) {
            fail("EuroVisionQueue testEuroVisionQueueLoad: Add load test failed- the EuroVisionQueue should support any amount of customers, yours crashed with " + N + " customers");
        }
    }

    private void testEuroVisionQueueFlow(int n) {
        int N = n;
        EuroVisionQueue q = new EuroVisionQueue();
        List<Customer> outside_queue = getCustomers(N);
        List<Customer> inside_queue = new ArrayList<Customer>();
        Customer c;
        for (int i = 0; i < N / 2; i++) {
            c = outside_queue.remove(0);
            inside_queue.add(c);
            q.addCustomer(c);
        }

        int curRand;
        //make some random operations
        for (int i = 0; i < N * 5; i++) {
            curRand = getRandom(10);
            if (curRand <= 3) {
                if (inside_queue.size() > 0) {
                    c = q.serveRegularCustomer();
                    outside_queue.add(c);
                    inside_queue.remove(c);
                }
            }

            if (curRand >= 3 && curRand <= 5) {
                if (inside_queue.size() > 0) {
                    c = q.servePriorityCustomer();
                    outside_queue.add(c);
                    inside_queue.remove(c);
                }
            }

            if (curRand >= 6) {
                if (outside_queue.size() > 0) {
                    c = outside_queue.remove(0);
                    inside_queue.add(c);
                    q.addCustomer(c);
                }
            }
        }

        //make sure things seem fine
        assertEquals("EuroVisionQueue: testEuroVisionQueueFlow1: The heap size is not correct"
                , inside_queue.size(), q.heap.size());

        for (Customer cust : inside_queue) {
            assertEquals("EuroVisionQueue: testEuroVisionQueueFlow1: A customer that is in the EuroVisionQueue does not appear in the CustomerQueue"
                    , true, isInCustomerQueue(cust, q.q));
            assertEquals("EuroVisionQueue: testEuroVisionQueueFlow1: A customer that is in the EuroVisionQueue does not appear in the CustomerHeap"
                    , true, isInCustomerHeap(cust, q.heap));
        }

        assertEquals("EuroVisionQueue: testEuroVisionQueueFlow1: HeapIndex is not correct"
                , true, isHeapIndexCorrect(q.heap));
    }

    private boolean isInCustomerQueue(Customer cust, CustomerQueue q) {
        CustomerElement ce = q.first;
        while (ce != null) {
            if (equalCustomers(ce.c, cust))
                return true;
            ce = ce.next;
        }

        return false;
    }

    private boolean isInCustomerHeap(Customer cust, CustomerHeap heap) {
        for (int i = 0; i < heap.size(); i++) {
            if (heap.heap[i] != null && equalCustomers(heap.heap[i].c, cust))
                return true;
        }

        return false;
    }

    private boolean isHeapIndexCorrect(CustomerHeap heap) {
        for (int i = 0; i < heap.size(); i++) {
            if (heap.heap[i].heapIndex != i)
                return false;
        }

        return true;
    }

    private List<Customer> getCustomers(int n) {
        List<Customer> customers = new ArrayList<Customer>();
        for (int i = 1; i <= n; i++)
            customers.add(new Customer(i, "customer " + i));
        return customers;
    }

    private int getRandom(int max) {
        return ThreadLocalRandom.current().nextInt(1, max + 1);
    }

}
