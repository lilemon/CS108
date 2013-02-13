import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class WebFrame extends JFrame {

	private DefaultTableModel model;
	private JTable table;
	private JButton single;
	private JButton concurrent;
	private JTextField threads;
	private JLabel running;
	private JLabel completed;
	private JLabel elapsed;
	private JProgressBar progress;
	private JButton stop;
	private final String file;

	private List<WebWorker> workers;
	private List<Pair<String, String>> data;
	private Launcher launcher;
	private int fetched;
	private Object lock;
	private CountDownLatch latch;
	private boolean interrupted;
	private long begin;

	private static Semaphore sema;

	private WebFrame() {
		super("WebLoader");
		final WebFrame frame = this;

		sema = null;
		workers = Collections.synchronizedList(new ArrayList<WebWorker>());
		data = Collections
				.synchronizedList(new ArrayList<Pair<String, String>>());
		fetched = 0;
		lock = new Object();

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		model = new DefaultTableModel(new String[] { "url", "status" }, 0);
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setShowGrid(false);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(600, 350));
		scrollpane.setAlignmentX(LEFT_ALIGNMENT);
		add(scrollpane);

		single = new JButton("Single Thread Fetch");
		single.setAlignmentX(LEFT_ALIGNMENT);
		single.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clearTable();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							stop.setEnabled(true);
							concurrent.setEnabled(false);
							single.setEnabled(false);
						}
					});
					launcher = new Launcher(1);
					launcher.start();
				} catch (Exception ignored) {

				}
			}
		});

		concurrent = new JButton("Concurrent Fetch");
		concurrent.setAlignmentX(LEFT_ALIGNMENT);
		concurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clearTable();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							stop.setEnabled(true);
							concurrent.setEnabled(false);
							single.setEnabled(false);
						}
					});
					int num = Integer.parseInt(threads.getText());
					if (num <= 0) {
						throw (new Exception("non-positive"));
					}
					launcher = new Launcher(num);
					launcher.start();
				} catch (Exception ignored) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							stop.setEnabled(false);
							concurrent.setEnabled(true);
							single.setEnabled(true);
						}
					});
					JOptionPane.showMessageDialog(frame,
							"Please use a positive integer",
							"Invalid Number of Threads",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		threads = new JTextField(20);
		threads.setMaximumSize(new Dimension(40, 20));
		threads.setAlignmentX(LEFT_ALIGNMENT);

		running = new JLabel("Running: ");
		running.setAlignmentX(LEFT_ALIGNMENT);

		completed = new JLabel("Completed: ");
		completed.setAlignmentX(LEFT_ALIGNMENT);

		elapsed = new JLabel("Elapsed: ");
		elapsed.setAlignmentX(LEFT_ALIGNMENT);

		progress = new JProgressBar();
		progress.setAlignmentX(LEFT_ALIGNMENT);
		progress.setValue(0);
		progress.setMaximum(data.size());

		stop = new JButton("Stop");
		stop.setAlignmentX(LEFT_ALIGNMENT);
		stop.setEnabled(false);
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							stop.setEnabled(false);
							concurrent.setEnabled(true);
							single.setEnabled(true);
						}
					});
					interrupted = true;
					launcher.interrupt();
				} catch (Exception ignored) {
				}
			}
		});

		add(single);
		add(concurrent);
		add(threads);
		add(running);
		add(completed);
		add(elapsed);
		add(progress);
		add(stop);

		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[] options = { "links.txt", "links2.txt" };
		int n = JOptionPane.showOptionDialog(frame,
				"Which file do you want to load?", "Select a file",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[1]);
		if (n == JOptionPane.YES_OPTION) {
			file = "links.txt";
		} else {
			file = "links2.txt";
		}

		try {
			BufferedReader io = new BufferedReader(new FileReader(file));
			String line = io.readLine();
			while (line != null) {
				data.add(new Pair<String, String>(line, ""));
				line = io.readLine();
			}
			update();
			io.close();
		} catch (Exception ignored) {
			System.err.println("File loading error occured");
		}
	}

	public void clearTable() {
		fetched = 0;
		interrupted = false;
		for (Pair<String, String> pair : data) {
			pair.setR("");
		}
		update();
	}

	public void fetchWeb(int num) {
	}

	public void update() {
		model.getDataVector().removeAllElements();
		for (Pair<String, String> ent : data) {
			model.addRow(new String[] { ent.getL(), ent.getR() });
		}
		model.fireTableDataChanged();
	}

	public class Launcher extends Thread {

		public Launcher(int num) {
			sema = new Semaphore(num);
			latch = new CountDownLatch(data.size());
		}

		public void run() {

			try {
				begin = System.currentTimeMillis();
				for (int i = 0; i < data.size(); i++) {
					if (!Thread.currentThread().isInterrupted()) {
						;
						WebWorker worker = new WebWorker(i);
						worker.start();
					}
				}
				latch.await();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						stop.setEnabled(false);
						concurrent.setEnabled(true);
						single.setEnabled(true);
					}
				});
			} catch (InterruptedException ignored) {
				for (WebWorker worker : workers) {
					worker.interrupt();
				}
			}
		}
	}
	
	public class WebWorker extends Thread {
		private int index;

		// This is the core web/download i/o code...
		public WebWorker(int index) {
			this.index = index;
		}
		
		public void updateLabels() {
			running.setText("Running: " + workers.size());
			completed.setText("Completed: " + fetched);
			elapsed.setText("Elapsed: " + (System.currentTimeMillis() - begin) / 1000.0);
		}

		public void run() {

			final int i = index;
			InputStream input = null;
			String urlString = data.get(index).getL();

			try {
				sema.acquire();
				if (interrupted) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							data.get(i).setR("Did not start");
							update();
						}
					});
					sema.release();
					return;
				}
				workers.add(this);
				updateLabels();
				long start = System.currentTimeMillis();
				StringBuilder contents = null;
				int size = 0;

				URL url = new URL(urlString);
				URLConnection connection = url.openConnection();

				// Set connect() to throw an IOException
				// if connection does not succeed in this many msecs.
				connection.setConnectTimeout(5000);

				connection.connect();
				input = connection.getInputStream();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(input));

				char[] array = new char[1000];
				int len;
				contents = new StringBuilder(1000);
				while ((len = reader.read(array, 0, array.length)) > 0) {
					contents.append(array, 0, len);
					size = size + len;
					Thread.sleep(100);
				}

				// Successful download if we get here
				String timestamp = new SimpleDateFormat("hh:mm:ss")
						.format(Calendar.getInstance().getTime()) + "  ";
				String lapse = (System.currentTimeMillis() - start) + "ms  ";
				final String right = timestamp + lapse + size + " bytes ";

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						data.get(i).setR(right);
						update();
					}
				});
				workers.remove(this);
				updateLabels();
				sema.release();
			}
			// Otherwise control jumps to a catch...
			catch (MalformedURLException ignored) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						data.get(i).setR("err");
						update();
					}
				});
				workers.remove(this);
				sema.release();
			} catch (InterruptedException exception) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						data.get(i).setR("Interrupted");
						update();
					}
				});
				workers.remove(this);
				sema.release();
			} catch (IOException ignored) {
				workers.remove(this);
				sema.release();
			}
			// "finally" clause, to close the input stream
			// in any case
			finally {
				try {
					if (input != null)
						input.close();
				} catch (IOException ignored) {
				}
				if (!interrupted) {
					synchronized (lock) {
						fetched++;
						progress.setValue(fetched);
						progress.setString(fetched + "/" + data.size());
						progress.setStringPainted(true);
					}
				}
				latch.countDown();
			}
		};

	}

	public static void main(String[] args) {
		WebFrame frame = new WebFrame();
	}

	/**
	 * pair class found on StackOverFlow to help storing data
	 */

	public class Pair<L, R> {
		private L l;
		private R r;

		public Pair(L l, R r) {
			this.l = l;
			this.r = r;
		}

		public L getL() {
			return l;
		}

		public R getR() {
			return r;
		}

		public void setL(L l) {
			this.l = l;
		}

		public void setR(R r) {
			this.r = r;
		}
	}

}
