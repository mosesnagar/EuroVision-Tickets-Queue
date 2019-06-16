package eurovision;

public class testerEVQ {
    public static void main (String[] args){
        /*
         * A test to check your class.
         * Expected outcome:
         * customer: Avner Halevy, priority: 100
         * customer: Avner Halevi, priority: 100
         * customer: Udi Boker, priority: 87
         * customer: Udi Bokeri, priority: 87
         * The EuroVisionQueue is empty
         * null
         * The EuroVisionQueue is empty
         * null
         * customer: Udi Boker, priority: 87
         * customer: Shlomo Kalish, priority: 120
         * customer: Udi Bokeri, priority: 87
         * customer: Sharon Gal, priority: 120
         * customer: Avner Halevi, priority: 100
         * customer: Avner Halevy, priority: 100
         * The EuroVisionQueue is empty
         * null
         * The EuroVisionQueue is empty
         * null
         */
        
        EuroVisionQueue q = new EuroVisionQueue();
        Customer a = new Customer(87, "Udi Boker");
        Customer b = new Customer(87, "Udi Bokeri");
        Customer c = new Customer(100, "Avner Halevi");
        Customer d = new Customer(100, "Avner Halevy");
        Customer e = new Customer(120, "Sharon Gal");
        Customer f = new Customer(120, "Shlomo Kalish");

        q.addCustomer(a);
        q.addCustomer(b);
        q.addCustomer(c);
        q.addCustomer(d);
        // Should be Avner Halevy
        System.out.println(q.servePriorityCustomer());
        // Should be Avner Halevi
        System.out.println(q.servePriorityCustomer());
        // Should be Udi Boker
        System.out.println(q.serveRegularCustomer());
        // Should be Udi Bokeri
        System.out.println(q.servePriorityCustomer());
        // Should be null\"the list is empty"
        System.out.println(q.serveRegularCustomer());
        // Should be null\"the list is empty"
        System.out.println(q.servePriorityCustomer());
        q.addCustomer(a);
        q.addCustomer(b);
        q.addCustomer(c);
        q.addCustomer(d);
        q.addCustomer(e);
        q.addCustomer(f);
        // Should be Udi Boker
        System.out.println(q.serveRegularCustomer());
        // Should be Shlomo Kalish
        System.out.println(q.servePriorityCustomer());
        // Should be Udi Bokeri
        System.out.println(q.serveRegularCustomer());
        // Should be Sharon Gal
        System.out.println(q.servePriorityCustomer());
        // Should be Avner Halevi
        System.out.println(q.serveRegularCustomer());
        // Should be Avner Halevy
        System.out.println(q.servePriorityCustomer());
        // Should be null\"the list is empty"
        System.out.println(q.servePriorityCustomer());
        // Should be null\"the list is empty"
        System.out.println(q.serveRegularCustomer());
    }
}
