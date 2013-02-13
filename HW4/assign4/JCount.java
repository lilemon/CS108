import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class JCount extends JPanel {
	
	private static JFrame frame;
	private JTextField textfield;
	private JLabel label;
	private JButton start;
	private JButton stop;
	private Counter counter;
	
	private final static int TWIDTH = 10;
	private final static int VGAP = 40;
	private final static int ITR = 10000;
	private final static int NAP = 100;

	public JCount() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final JCount jc = this;
		
		textfield = new JTextField(TWIDTH);
		label = new JLabel("0");
		
		start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				label.setText("0");
				counter = new Counter(jc);
				counter.start();
			}
		});
		
		stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					counter.interrupt();
					counter = null;
				} catch (Exception ignored) {
				}
			}
		});
		
		add(textfield);
		add(label);
		add(start);
		add(stop);
		add(Box.createRigidArea(new Dimension(0,VGAP)));
		
		setVisible(true);
	}
	
	private class Counter extends Thread {
		JCount jc;

		public Counter(JCount jc) {
			this.jc = jc;
		}

		@Override
		public void run() {
			int max = 0;
			try {
				max = Integer.parseInt(jc.textfield.getText());
			} catch (Exception ignored) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						jc.label.setText("Please try a valid int");
					}
				});
				return;
			}
			for (int i = 0; i < max; i++) {
				if (i % ITR == 0) {
					try {
						Thread.sleep(NAP);
					} catch (InterruptedException ex) {
						return;
					}
					if (isInterrupted())
						return;
					final String text = i + "";
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							jc.label.setText(text);
						}
					});
				}
			}
			if ((max - 1) % ITR != 0) {
				try {
					Thread.sleep(NAP);
				} catch (InterruptedException ignored) {
				}
				final String text = max + "";
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						jc.label.setText(text);
					}
				});
			}	
		}
	}
	
	
	
	public static void main(String[] args) {
		frame  = new JFrame("JCounters");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		JCount jc1 = new JCount();
		JCount jc2 = new JCount();
		JCount jc3 = new JCount();
		JCount jc4 = new JCount();
		
		frame.add(jc1);
		frame.add(jc2);
		frame.add(jc3);
		frame.add(jc4);
		frame.pack();
		frame.setVisible(true);
	}
}
