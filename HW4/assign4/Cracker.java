import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!"
			.toCharArray();
	private static MessageDigest md;
	public static int LEN = CHARS.length;
	private static Object mdLock = new Object();
	private static List<String> results;

	/*
	 * Given a byte[] array, produces a hex String, such as "234a6f". with 2
	 * chars for each byte in the array. (provided code)
	 */

	public static void init() {
		md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Can't load MessageDigest class");
			e.printStackTrace();
		}
	}

	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff; // remove higher bits, sign
			if (val < 16)
				buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}

	/*
	 * Given a string of hex byte values such as "24a26f", creates a byte[]
	 * array of those values, one byte value -128..127 for each 2 chars.
	 * (provided code)
	 */
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			result[i / 2] = (byte) Integer
					.parseInt(hex.substring(i, i + 2), 16);
		}
		return result;
	}

	public static String encode(String s) {
		synchronized (mdLock) {
			if (md != null) {
				byte[] b = s.getBytes();
				md.update(b);
				String sha = hexToString(md.digest());
				return sha;
			}
		}
		return "";
	}

	static CountDownLatch latch;

	public static void decode(String[] args) {
		results = Collections.synchronizedList(new ArrayList<String>());
		String code = args[0];
		int max_len = 0;
		int num_t = 0;
		try {
			max_len = Integer.parseInt(args[1]);
			num_t = Integer.parseInt(args[2]);
		} catch (Exception e) {
			System.err.println("Parsing Error");
			return;
		}
		latch = new CountDownLatch(num_t);
		int each = (LEN - 1) / num_t;
		for (int i = 0; i < num_t - 1; i++) {
			Solver sol = new Solver(i * each, (i + 1) * each, max_len, code);
			sol.start();
		}
		Solver sol = new Solver((num_t - 1) * each, LEN, max_len, code);
		sol.start();
	}

	static class Solver extends Thread {
		private int start;
		private int end;
		private int len;
		private String code;
		private boolean solved;

		public Solver(int start, int end, int len, String code) {
			this.start = start;
			this.end = end;
			this.len = len;
			this.code = code;
			solved = false;
		}

		public void run() {
			Thread running = Thread.currentThread();
			if (len < 1) {
				System.err.println("Length has to be at least 1");
				return;
			}
			for (int i = start; i < end; i++) {
				char curr = CHARS[i];
				solve("" + curr, len - 1);
			}
			// if (!solved) {
			// System.out.println(running.getName() + " finds nothing");
			// }
			latch.countDown();
		}

		private void solve(String curr, int len) {
			if (!solved) {
				String test = encode(curr);
				if (test.equals(code)) {
					// System.out.println(curr);
					results.add(curr);
					solved = true;
					return;
				} else if (len < 1) {
					return;
				} else {
					for (char ch : CHARS) {
						solve(curr + ch, len - 1);
					}
				}
			}
		}
	}

	// possible test values:
	// a 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8
	// fm adeb6f2a18fe33af368d91b09587b68e3abcb9a7
	// a! 34800e15707fae815d7c90d49de44aca97e2d759
	// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

	public static void main(String[] args) {
		init();
		String ret;
		if (args.length == 1) {
			ret = encode(args[0]);
			if (ret.isEmpty()) {
				System.out.println("Can't encode");
			} else {
				System.out.println(ret);
			}
		} else if (args.length == 3) {
			decode(args);
			try {
				latch.await();
			} catch (InterruptedException ignored) {
			}
			if (results.isEmpty()) {
				System.out.println("No matching result found!");
			} else {
				for (String result : results)
					System.out.println(result);
			}
			System.out.println("All done!");
		} else {
			System.err
					.println("Usage: java Cracker [toEncode] | java Cracker [toDecode] [maxWordLength] [numThreads]");
		}
	}

}
