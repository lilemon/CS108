package tetris;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tetris.Brain.Move;

public class JBrainTetris extends JTetris {
	
	private Brain brain;
	private JCheckBox brainMode;
	private JCheckBox animatedFalling;
	private JSlider adversary;
	private Move move;
	private Random random;
	
	JBrainTetris(int pixels) {
		super(pixels);
		brain = new DefaultBrain();
		move = null;
		random = new Random();
	}
	
	public Piece pickNextPiece() {
		int ran = random.nextInt(100);
		if (ran >= adversary.getValue()) {
			return super.pickNextPiece();
		} else {
			return adversaryPiece();
		}
	}
	
	private Piece adversaryPiece() {
		Piece ret = pieces[0];
		double worstScore = 0;
		for (Piece piece:pieces) {
			move = brain.bestMove(board, piece, HEIGHT, move);
			if (move.score > worstScore) {
				worstScore = move.score;
				ret = move.piece;
			}
		}
		return ret;
	}
	
	/**
	 * Called to change the position of the current piece. Each key press calls
	 * this once with the verbs LEFT RIGHT ROTATE DROP for the user moves, and
	 * the timer calls it with the verb DOWN to move the piece down one square.
	 * 
	 * Before this is called, the piece is at some location in the board. This
	 * advances the piece to be at its next location.
	 * 
	 * Overriden by the brain when it plays.
	 */
	public void tick(int verb) {
		if (brainMode.isSelected() && verb == DOWN && currentY < HEIGHT) {
			board.undo();
			move = brain.bestMove(board, currentPiece, HEIGHT, move);
			board.undo();
//			if (move != null && currentX == move.x && currentPiece.equals(move.piece) && !animatedFalling.isSelected()) {
//				tick(DROP);
//			}
			if (!currentPiece.equals(move.piece))
				tick(ROTATE);
			if (currentX > move.x)
				tick(LEFT);
			if (currentX < move.x)
				tick(RIGHT);
		}
		super.tick(verb);
	}

	/**
	 * Creates the panel of UI controls -- controls wired up to call methods on
	 * the JTetris. This code is very repetitive. Insert brain control at the end
	 */
	public JComponent createControlPanel() {
		JPanel panel = (JPanel) super.createControlPanel();
		
		// Brain button
		brainMode = new JCheckBox("Brain active");
		panel.add(brainMode);
		
		// Animated falling
//		animatedFalling = new JCheckBox("Animated Falling");
//		panel.add(animatedFalling);
//		animatedFalling.setSelected(true);
		
		// Adversary
		JPanel little = new JPanel();
		little.add(new JLabel("Adversary:"));
		adversary = new JSlider(0, 100, 0);
		adversary.setPreferredSize(new Dimension(100, 15));
		little.add(adversary);
		panel.add(little);
		return panel;
	}
	
	/**
	 * Creates a frame with a JBrainTetris.
	 */
	public static void main(String[] args) {
		// Set GUI Look And Feel Boilerplate.
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
		}

		JBrainTetris tetris = new JBrainTetris(16);
		JFrame frame = JBrainTetris.createFrame(tetris);
		frame.setVisible(true);
	}
}
