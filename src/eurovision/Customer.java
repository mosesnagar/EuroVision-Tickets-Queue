package eurovision;

public class Customer {
	int priority;
	String name;
	
	/**
	 * A standard constructor for the customer class
	 * 
	 * @param priority
	 * @param name
	 */
	
	public Customer(int priority, String name){
		this.name = name;
		this.priority = priority;
	}

	/**
	 * Compares this customer to another customer.
	 * This customer is considerd smller than the other customer if and only if
	 * The priority of this customer is smaller than the other customer or the priorities are equal 
	 * and the name of this customer is smaller in the lexicorapgic ordering than the name of the other customer.
	 * 
	 * If this customer is smaller returns a negative number. If this customer is bigger return a positive number.
	 * If the customers are equal return 0.
	 * 
	 * 
	 * @param other
	 * @return a negative/positive or zero number of this customer is smaller/greater or equal to other
	 */
	public int compareTo(Customer other) {
		int priority = this.priority - other.priority;
		if (priority == 0) {
			priority = this.name.compareToIgnoreCase(other.name);
		}
		return priority;
	}
	
	
	public String toString(){
		return "customer: " + this.name + ", priority: " + this.priority;
	}
}

