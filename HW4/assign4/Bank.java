import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Bank {

	private final static int NUM_ACCOUNT = 20;
	private final static int FUND = 1000;
	private final static int QUEUE_SIZE = 100;

	private static BlockingQueue<Transaction> queue;
	private static List<Account> accounts;
	static CyclicBarrier barrier;
	private static Transaction nullTrans = new Transaction(-1, 0, 0);

	static class Worker extends Thread {
		public void run() {
			try {
				Transaction current = queue.take();
				while (!current.equals(nullTrans)) {
					process(current);
					current = queue.take();
				}
				if (current.equals(nullTrans)) {
					queue.put(current);
				}
				try {
					barrier.await();
				} catch (InterruptedException ignore) {
				} catch (BrokenBarrierException ignore) {
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void process(Transaction current) {
			int from = current.getFrom();
			int to = current.getTo();

			if (from == to) {
				accounts.get(from).selfTrans(current);
			} else {
				accounts.get(from).withdraw(current);
				accounts.get(to).deposit(current);
			}
		}
	}

	private static void setup(String[] args) {
		accounts = new ArrayList<Account>();
		for (int i = 0; i < NUM_ACCOUNT; i++) {
			accounts.add(new Account(i, FUND));
		}

		queue = new ArrayBlockingQueue<Transaction>(QUEUE_SIZE);

		String filename = args[0];
		int num_worker = 0;
		try {
			num_worker = Integer.parseInt(args[1]);
		} catch (Exception e) {
			System.err.println("Usage: java Bank [input_file] [num_thread]");
			return;
		}
		for (int i = 0; i < num_worker; i++) {
			Worker w = new Worker();
			w.start();
		}
		barrier = new CyclicBarrier(num_worker + 1);
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line = in.readLine();
			while (line != null) {
				String[] input = line.split(" ");
				Transaction tran = new Transaction(Integer.parseInt(input[0]),
						Integer.parseInt(input[1]), Integer.parseInt(input[2]));
				queue.put(tran);
				line = in.readLine();
			}
			in.close();
			queue.put(nullTrans);
		} catch (FileNotFoundException ef) {
			barrier = new CyclicBarrier(1);
			ef.printStackTrace();
			System.err.println("File not found, exiting...");
			return;
		} catch (Exception e) {
			barrier = new CyclicBarrier(1);
			e.printStackTrace();
			System.err.println("Can't read file, exiting...");
			return;
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java Bank [input_file] [num_thread]");
		} else {
			setup(args);
			try {
				barrier.await();
			} catch (InterruptedException ignored) {
			} catch (BrokenBarrierException ignored) {
			}
			for (Account account : accounts) {
				System.out.println(account);
			}
		}
	}

}
