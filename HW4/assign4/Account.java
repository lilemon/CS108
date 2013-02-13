public class Account implements Comparable{
	
	private int id;
	private int balance;
	private int numTrans;
	private Object lock;
	
	public Account(int id, int balance) {
		this.id = id;
		this.balance = balance;
		this.numTrans = 0;
		lock = new Object();
	}
	
	public void deposit(Transaction trans) {
		synchronized(lock) {
			balance = balance + trans.getAmount();
			numTrans++;
		}
	}
	
	public void withdraw(Transaction trans) {
		synchronized(lock) {
			balance = balance - trans.getAmount();
			numTrans++;
		}
	}
	
	public void selfTrans(Transaction trans) {
		synchronized(lock) {
			numTrans++;
		}
	}
	
	public int getId () {
		return id;
	}
	
	public String toString() {
		return ("acct: " + id + " bal: " + balance + " trans: " + numTrans);
	}

	@Override
	public int compareTo(Object arg0) {
		Account that = (Account) arg0;
		return this.getId() - that.getId();
	}
	
	
}
 