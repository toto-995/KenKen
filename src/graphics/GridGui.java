package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import game_grid.Block;
import game_grid.Box;
import game_grid.Grid;
import game_grid.Memento;
import logic.Facade;

public class GridGui extends JPanel {
	private int dimensione;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Facade facade;
	private List<Point> lista;
	private JFrame frame;
	private ButtonGui button[][];
	private JButton add;
	private JButton forward;
	private JButton back;
	private JButton solve;
	private int panelWidth = 500;
	private int panelHeight = 500;
	private int contBlock; // lo incremento quando premo sul button di add
	private Object[] possibleValues = { '+', '-', '*', '/' };
	private char inputC;
	private JPanel solvePanel;
	private JPanel sud;
	private Memento currentMem;
	private Stack<Memento> undo;
	private Stack<Memento> redo;
	private Queue<Memento> redoFifo;
	private Border defaultBorder;

	public GridGui(int d, JFrame f, Facade facade) {
		undo = new Stack<Memento>();
		redo = new Stack<Memento>();
		redoFifo = new LinkedList<Memento>();
		contBlock = 0;
		this.frame = f;
		this.lista = new LinkedList<Point>();
		this.dimensione = d;
		this.facade = facade;
		this.setLayout(new GridLayout(d, d));
		this.setSize(panelWidth, panelHeight);
		this.setLocation(150, 0);
		creaGriglia();
	}

	public void creaGriglia() {
		button = new ButtonGui[dimensione][dimensione];
		BoxListener boxl = new BoxListener();
		JButtonListener jbl = new JButtonListener();
		for (int i = 0; i < dimensione; i++)
			for (int j = 0; j < dimensione; j++) {
				button[i][j] = new ButtonGui();
				button[i][j].setLayout(new BorderLayout());
				button[i][j].setRiga(i);
				button[i][j].setColonna(j);
				button[i][j].addActionListener(boxl);
				this.add(button[i][j]);
			}
		defaultBorder = button[0][0].getBorder();
		sud = new JPanel();
		sud.setLayout(new GridLayout(1, 3));
		sud.setSize(new Dimension(240, 50));
		sud.setLocation((this.getWidth() / 2) - (sud.getWidth() / 2) + 150, 510);

		add = new JButton("ADD");
		add.addActionListener(jbl);
		forward = new JButton();
		forward.addActionListener(jbl);
		ImageIcon icon1 = new ImageIcon("src/img/forward.jpg");
		Image image1 = icon1.getImage();
		Image newimg1 = image1.getScaledInstance(80, 50, java.awt.Image.SCALE_SMOOTH);
		icon1 = new ImageIcon(newimg1);
		forward.setBorderPainted(false);
		forward.setIcon(icon1);
		forward.setEnabled(false);

		back = new JButton();
		back.addActionListener(jbl);
		ImageIcon icon2 = new ImageIcon("src/img/back.jpg");
		Image image2 = icon2.getImage();
		Image newimg2 = image2.getScaledInstance(80, 50, java.awt.Image.SCALE_SMOOTH);
		icon2 = new ImageIcon(newimg2);
		back.setBorderPainted(false);
		back.setIcon(icon2);
		back.setEnabled(false);

		sud.add(back);
		sud.add(add);
		sud.add(forward);
		frame.getContentPane().add(sud);

		solvePanel = new JPanel();
		solvePanel.setSize(new Dimension(150, 50));
		solvePanel.setLayout(null);
		solvePanel.setBackground(Color.BLACK);
		solvePanel.setLayout(new GridLayout(1, 1));
		solve = new JButton("SOLVE");
		solve.addActionListener(jbl);
		solvePanel.add(solve);
		frame.getContentPane().add(solvePanel);
		solvePanel.setLocation((this.getWidth() / 2) - (solvePanel.getWidth() / 2) + 150, 580);
		solve.setEnabled(false);
		currentMem = facade.firstMemento();
	}

	private class BoxListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			ButtonGui pressed = (ButtonGui) ev.getSource();
			if (!pressed.getStop()) {
				if (!pressed.getSelect()) {
					pressed.setBackground(Color.LIGHT_GRAY);
					pressed.setSelect(true);
					lista.add(new Point(pressed.getRiga(), pressed.getColonna()));
				} else {
					pressed.setBackground(null);
					pressed.setSelect(false);
					lista.remove(new Point(pressed.getRiga(), pressed.getColonna()));
				}
			}
		}
	}

	private int contAdiacenti(Point p, int[][] mat) {
		int cont = 0;
		if (p.x>0 && mat[p.x - 1][ p.y]==1)
			cont++;
		if (p.x < dimensione-1 && mat[p.x + 1][p.y]==1)
			cont++;
		if (p.y < dimensione-1 && mat[p.x][p.y+1]==1)
			cont++;
		if (p.y > 0 && mat[p.x][p.y-1]==1)
			cont++;
		return cont;
	}
	private int contAdiacentiFalse(Point p, int[][] mat) {
		int cont = 0;
		if (p.x>0 && mat[p.x - 1][ p.y]==2)
			cont++;
		if (p.x < dimensione-1 && mat[p.x + 1][p.y]==2)
			cont++;
		if (p.y < dimensione-1 && mat[p.x][p.y+1]==2)
			cont++;
		if (p.y > 0 && mat[p.x][p.y-1]==2)
			cont++;
		return cont;
	}

	private boolean okBlock(List<Point> lista) {
		if (lista.size() == 1) return true;
		int[][] mat = new int[dimensione][dimensione];
		for(int i = 0; i<dimensione; i++)
			for(int j = 0; j<dimensione; j++) {
				if(lista.contains(new Point(i,j)))
					mat[i][j] = 1;
				else
					mat[i][j] = 0;
			}
		boolean ok = true;
		boolean error = false;
		for(int i = 0; i<dimensione; i++)
			for(int j = 0; j<dimensione; j++) {
				if(mat[i][j]==1 && ok==true) {
					if(contAdiacenti(new Point(i, j), mat)>0)
						mat[i][j] = 2;
					else {
						mat[i][j] = 0;
						ok = false;
					}
				}
				if(mat[i][j]==1 && ok == false && contAdiacentiFalse(new Point(i, j), mat)>0) {
					ok = true;
					error=false;
				}
				if(mat[i][j]==1 && ok==false) {
					error = true;
				}
			}
		if (error) {
			JOptionPane.showMessageDialog(frame, "Celle selezionate non adiacenti!", "Attention!",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	private class JButtonListener implements ActionListener {
		private int inputN;
		private int lastListSize;

		@Override
		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == add) {
				if(lista.size()<1) {
					JOptionPane.showMessageDialog(null, "Errore! Nessuna casella selezionata!!!");
					return;
				}
				if (okBlock(lista)) {
					int controll = 0;
					String input;
					int a = 0;
					do {
						input = JOptionPane.showInputDialog("Inserire il numero");
						try {
							a = Integer.parseInt(input);
							controll = 1;
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Errore! inserire un NUMERO!");
							controll = -1;
						}
						if(a<=0 && controll >-1) {
							JOptionPane.showMessageDialog(null, "Errore! inserire un numero POSITIVO!");
							controll = -1;
						}
					} while (controll != 1 && input != null && a <= 0);
					if (input != null) {
						inputN = Integer.parseInt(input);
						Object inputO = JOptionPane.showInputDialog(null, "Scegliere l'operatore", "Operatore",
								JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
						if (inputO != null) {
							inputC = (char) inputO;
							lastListSize = lista.size();
							contBlock += 1;
							facade.addBlock(lista, contBlock, inputN, inputC);

							if (redo.size() > 0) {
								List<Memento> copia = new LinkedList<Memento>();
								copia.addAll(redo);
								List<Memento> copia2 = new LinkedList<>();
								copia2.addAll(redo);
								for (Memento m : copia)
									redoFifo.offer(redo.pop());
								for (Memento m : copia2)
									undo.push(redoFifo.poll());
							}
							undo.push(currentMem);
							currentMem = facade.createMemento();
							drawBlock(facade.getList());
						}
					}
				}
			} else if (ev.getSource() == solve) {
				facade.solve();
			} else if (ev.getSource() == back) {
				redo.push(currentMem);
				Memento m = undo.pop();
				facade.restoreMemento(m);
				contBlock = facade.getList().size();
				currentMem = m;
				drawBlock(facade.getList());
			} else if (ev.getSource() == forward) {
				undo.push(currentMem);
				Memento m = redo.pop();
				facade.restoreMemento(m);
				contBlock = facade.getList().size();
				currentMem = m;
				drawBlock(facade.getList());
			}
		}// actionPerformed

	}// JButtonListener

	public void drawBlock(List<Block> list) {
		clear();
		for (Block bl : list) {
			addLabel(bl.getList(), bl.getOperando(), bl.getOperatore());
			block(bl.getList());
		}
		if (undo.size() > 0)
			back.setEnabled(true);
		else
			back.setEnabled(false);

		if (redo.size() > 0)
			forward.setEnabled(true);
		else
			forward.setEnabled(false);

		if (boxPressed() == dimensione * dimensione) {
			add.setEnabled(false);
			solve.setEnabled(true);
		} else {
			add.setEnabled(true);
			solve.setEnabled(false);
		}
		lista.clear();
		frame.repaint();
	}

	private int boxPressed() {
		int cont = 0;

		for (int i = 0; i < dimensione; i++)
			for (int j = 0; j < dimensione; j++)
				if (button[i][j].getSelect())
					cont += 1;
		return cont;
	}

	private void clear() {
		for (int i = 0; i < dimensione; i++)
			for (int j = 0; j < dimensione; j++) {
				button[i][j].removeAll();
				button[i][j].setBorder(this.defaultBorder);
				button[i][j].setStop(false);
				button[i][j].setSelect(false);
			}
	}

	// inseriamo operando e operatore nel block
	private void addLabel(List<Box> list, int operando, char operatore) {
		JLabel l = new JLabel(String.valueOf(operando) + "" + operatore);
		l.setBackground(Color.BLACK);
		l.setSize(10, 5);
		int[][] mat = new int[dimensione][dimensione];
		boolean ok = false;
		for (Box b : list)
			mat[b.getRiga()][b.getColonna()] = 1;
		for (int i = 0; i < dimensione; i++) {
			if (!ok) {
				for (int j = 0; j < dimensione; j++)
					if (mat[i][j] == 1) {
						button[i][j].add(l, BorderLayout.PAGE_START);
						ok = true;
						break;
					}
			}
		}
	}

	private void block(List<Box> list) {
		List<Point> lista = new LinkedList<Point>();
		for (Box b : list)
			lista.add(new Point(b.getRiga(), b.getColonna()));
		for (Point p : lista) {
			button[p.x][p.y].setStop(true);
			button[p.x][p.y].setSelect(true);
			int l = 1, r = 1, u = 1, d = 1;
			int color = 3;
			if (!lista.contains(new Point(p.x - 1, p.y)))
				u = color;

			if (!lista.contains(new Point(p.x + 1, p.y)))
				d = color;

			if (!lista.contains(new Point(p.x, p.y + 1)))
				r = color;

			if (!lista.contains(new Point(p.x, p.y - 1)))
				l = color;
			button[p.x][p.y].setBorder(BorderFactory.createMatteBorder(u, l, d, r, Color.BLACK));
			button[p.x][p.y].setBackground(null);
		}

	}

	public void setGrid(Grid g) {
		for (int i = 0; i < dimensione; i++)
			for (int j = 0; j < dimensione; j++) {
				button[i][j].setText(String.valueOf(g.getVal(i, j)));
			}
		frame.revalidate();
	}

	public JButton getAdd() {
		return add;
	}

	public void setAdd(JButton add) {
		this.add = add;
	}

	public JButton getForward() {
		return forward;
	}

	public void setForward(JButton forward) {
		this.forward = forward;
	}

	public JButton getBack() {
		return back;
	}

	public void setBack(JButton back) {
		this.back = back;
	}

	public JButton getSolve() {
		return solve;
	}

	public void setSolve(JButton solve) {
		this.solve = solve;
	}

}
