package logic;

import java.awt.Point;
import java.util.List;
import java.util.ListIterator;
import javax.swing.JOptionPane;
import backtracking.KenKen;
import game_grid.Block;
import game_grid.Grid;
import game_grid.GridFactory;
import game_grid.Memento;
import graphics.KenKenGui;

public class GridFacade implements Facade {
	private GridFactory gridF;
	private Grid grid;
	private int dimensione;
	private KenKenGui gui;
	KenKen kenKen;
	private List<Grid> soluzioni;
	private ListIterator<Grid> iterator;
	private int solVis = 0;
	private int nSol;
	private boolean next = true;

	public GridFacade(GridFactory gf, int dimension) {
		this.gridF = gf;
		this.grid = gridF.createGame(dimension);
		this.dimensione = dimension;
		gui = new KenKenGui(this, dimensione);
	}

	@Override
	public void solve() {
		nSol = 0;
		if (!gui.getNSol().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "Inserire un numero di soluzioni nella casella a destra!");
			return;
		}
		nSol = Integer.parseInt(gui.getNSol());
		if (nSol <= 0) {
			JOptionPane.showMessageDialog(null, "Inserire un numero positivo!");
			return;
		}
		kenKen = new KenKen(dimensione, nSol, grid);
		kenKen.risolvi();

		soluzioni = kenKen.getSoluzioni(); /// non dovrei prenderli con il get ma deve essere il kenken ad avere facade
											/// e passarli

		if (soluzioni.isEmpty()) {
			JOptionPane.showMessageDialog(gui.getFrame(), "Nessuna soluzione trovata!", "Attention!",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(gui.getFrame(), "Trovate " + soluzioni.size() + " soluzioni", "Attention!",
					JOptionPane.INFORMATION_MESSAGE);
			gui.getGridGui().getAdd().setEnabled(false);
			gui.getGridGui().getBack().setEnabled(false);
			gui.getGridGui().getForward().setEnabled(false);
			gui.getGridGui().getSolve().setEnabled(false);
			iterator = soluzioni.listIterator();
			viewNSol();
		}

	}

	@Override
	public void viewNSol() {
		solVis++;
		if (iterator.hasNext() && solVis < soluzioni.size())
			gui.getNext().setEnabled(true);
		else
			gui.getNext().setEnabled(false);
		if (solVis > 1)
			gui.getPrevious().setEnabled(true);
		if (!next) {
			iterator.next();
			gui.getGridGui().setGrid(iterator.next());
			gui.getN_max_sol().setText(String.valueOf(solVis));
		} else {
			gui.getGridGui().setGrid(iterator.next());
			gui.getN_max_sol().setText(String.valueOf(solVis));
		}
		next = true; // ricordo che l'ultimo premuto è il next

	}

	 @Override
	public void viewPrevSol() {
		solVis--;
		if (iterator.hasPrevious() && solVis > 1)
			gui.getPrevious().setEnabled(true);
		else
			gui.getPrevious().setEnabled(false);
		gui.getNext().setEnabled(true);
		if (next) {
			iterator.previous();
			gui.getGridGui().setGrid(iterator.previous());
		} else
			gui.getGridGui().setGrid(iterator.previous());
		next = false; // ricordo che l'ultimo premuto è il previous
	}

	@Override
	public void addBlock(List<Point> l, int id, int operando, char operatore) {
		grid.addBlock(l, id, operando, operatore);
	}

	@Override
	public Memento createMemento() {
		return grid.createMemento();
	}

	@Override
	public Memento firstMemento() {
		return grid.firstMemento();
	}

	@Override
	public void restoreMemento(Memento m) {
		grid.restoreMemento(m);
		;
	}

	@Override
	public List<Block> getList() {
		return grid.getBlockList();
	}

}
