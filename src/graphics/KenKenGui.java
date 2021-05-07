package graphics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import logic.Facade;

public class KenKenGui {
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension frameSize = new Dimension(screenSize.width - 400, screenSize.height - 200);
	private int dimensione;
	private int weight, height;
	private JTextField n_max_sol;
	private Facade facade;
	private JFrame frame;
	private GridGui gridGui;
	private JTextField nSol;
	private JButton next;
	private JButton previous;

	public KenKenGui(Facade facade, int dimensione) {
		this.dimensione = dimensione;
		this.facade = facade;
		this.weight = 900;
		this.height = 700;
		frame = new JFrame("KenKen");
		frame.setIconImage(new ImageIcon("src/img/kenken.jpg").getImage());
		frame.setResizable(false);
		frame.setSize(weight, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width / 2) - (frame.getWidth() / 2),
				(screenSize.height / 2) - (frame.getHeight() / 2));
		frame.setLayout(null);

		gridGui = new GridGui(dimensione, frame, this.facade);
		frame.getContentPane().add(gridGui);
		BottoniListener listener = new BottoniListener();

		JPanel curren_sol = new JPanel();
		curren_sol.setLayout(new BorderLayout());
		curren_sol.setSize(new Dimension(200, 75));
		curren_sol.setLocation(675, 300);
		JLabel label = new JLabel("Soluzione attuale: ");
		label.setPreferredSize(new Dimension(200, 25));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Arial", Font.PLAIN, 15));
		curren_sol.add(label, BorderLayout.PAGE_START);

		BottoniListener bl = new BottoniListener();

		previous = new JButton();
		previous.setEnabled(false);
		previous.addActionListener(bl);
		previous.setPreferredSize(new Dimension(70, 60));
		ImageIcon icon = new ImageIcon("src/img/f2.jpg");
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(70, 55, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		previous.setBorderPainted(false);
		previous.setIcon(icon);
		curren_sol.add(previous, BorderLayout.LINE_START);

		n_max_sol = new JTextField("1", 2);
		n_max_sol.setEditable(false);
		n_max_sol.setHorizontalAlignment(JLabel.CENTER);
		n_max_sol.setFont(new Font("Arial", Font.PLAIN, 20));
		n_max_sol.setBackground(null);
		n_max_sol.setBorder(null);
		previous.setPreferredSize(new Dimension(70, 60));
		curren_sol.add(n_max_sol, BorderLayout.CENTER);

		next = new JButton();
		next.setEnabled(false);

		next.addActionListener(bl);
		next.setPreferredSize(new Dimension(70, 60));
		ImageIcon icon2 = new ImageIcon("src/img/f.jpg");
		Image image2 = icon2.getImage();
		Image newimg2 = image2.getScaledInstance(70, 55, java.awt.Image.SCALE_SMOOTH);
		icon2 = new ImageIcon(newimg2);
		next.setBorderPainted(false);
		next.setIcon(icon2);
		curren_sol.add(next, BorderLayout.LINE_END);

		frame.getContentPane().add(curren_sol);

		JPanel sol_found = new JPanel();
		sol_found.setLayout(new BorderLayout());
		sol_found.setSize(new Dimension(200, 60));
		sol_found.setLocation(675, 140);
		JLabel label2 = new JLabel("Inserire il numero di soluzioni:");
		label2.setPreferredSize(new Dimension(200, 25));
		label2.setHorizontalAlignment(JLabel.CENTER);
		label2.setFont(new Font("Arial", Font.PLAIN, 15));
		sol_found.add(label2, BorderLayout.PAGE_START);
		nSol = new JTextField(null, 2);
		nSol.setHorizontalAlignment(JLabel.CENTER);
		nSol.setFont(new Font("Arial", Font.PLAIN, 20));
		sol_found.add(nSol, BorderLayout.CENTER);
		frame.getContentPane().add(sol_found);

		frame.setVisible(true);
	}

	public JTextField getN_max_sol() {
		return n_max_sol;
	}

	public void setN_max_sol(JTextField n_max_sol) {
		this.n_max_sol = n_max_sol;
	}

	private class BottoniListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent aE) {
			if (aE.getSource() == next) {
				facade.viewNSol();
			}

			else if (aE.getSource() == previous)
				facade.viewPrevSol();
		}

	}

	public Component getFrame() {
		return this.frame;
	}

	public String getNSol() {
		return nSol.getText();
	}

	public JButton getNext() {
		return next;
	}

	public void setNext(JButton next) {
		this.next = next;
	}

	public JButton getPrevious() {
		return previous;
	}

	public void setPrevious(JButton previous) {
		this.previous = previous;
	}

	public GridGui getGridGui() {
		return gridGui;
	}

}
