package backtracking;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import game_grid.GameGrid;
import game_grid.Grid;

public class KenKen extends Problema<Point, Integer> {
	private final int N;
	private Grid gameGrid;
	private List<Grid> soluzioni;

	public KenKen(int N, int n_max_sol, Grid g) {
		super(n_max_sol);
		this.N = N;
		this.gameGrid = g;
		this.gameGrid.setDimension(N);
		this.soluzioni = new LinkedList<Grid>();
	}

	@Override
	protected Point primoPuntoDiScelta() {
		Point p = new Point(0, 0);
		return p;
	}

	@Override
	protected Point prossimoPuntoDiScelta(Point ps) {
		Point p = new Point();
		if (ps.y < N - 1) {
			p.x = ps.x;
			p.y = ps.y + 1;
		} else {
			p.x = ps.x + 1;
			p.y = 0;
		}
		return p;
	}

	@Override
	protected Point ultimoPuntoDiScelta() {
		int d = this.gameGrid.getDimension() - 1;
		Point p = new Point(d, d);
		return p;
	}

	@Override
	protected Integer primaScelta(Point ps) {
		return 1;
	}

	@Override
	protected Integer prossimaScelta(Integer s) {
		return s + 1;
	}

	@Override
	protected Integer ultimaScelta(Point ps) {
		return N;
	}

	@Override
	protected boolean assegnabile(Integer scelta, Point puntoDiScelta) {
		return gameGrid.verificaBlock(scelta, puntoDiScelta) && gameGrid.verificaRC(scelta, puntoDiScelta);
	}

	@Override
	protected void assegna(Integer scelta, Point puntoDiScelta) {
		gameGrid.setVal(scelta, puntoDiScelta.x, puntoDiScelta.y);
		gameGrid.getBlock(puntoDiScelta).addAssegnati();
	}

	@Override
	protected void deassegna(Integer scelta, Point puntoDiScelta) {
		gameGrid.setVal(0, puntoDiScelta.x, puntoDiScelta.y);
		gameGrid.getBlock(puntoDiScelta).removeAssegnati();
	}

	@Override
	protected Point precedentePuntoDiScelta(Point puntoDiScelta) {
		Point p = new Point();
		p = puntoDiScelta;
		if (p.y == 0) {
			p.x -= 1;
			p.y = N - 1;
		} else {
			p.y -= 1;
		}
		return p;
	}

	@Override
	protected Integer ultimaSceltaAssegnataA(Point puntoDiScelta) {
		return gameGrid.getVal(puntoDiScelta.x, puntoDiScelta.y);
	}

	public List<Grid> getSoluzioni() {
		return soluzioni;
	}
	
	@Override
	protected void scriviSoluzione(int nr_sol) {
		Grid soluzione = new GameGrid(N);
		for (int i = 0; i < gameGrid.getDimension(); i++) 
			for (int j = 0; j < gameGrid.getDimension(); j++) {
				soluzione.setVal(gameGrid.getVal(i, j), i, j);
			}
		soluzioni.add(soluzione);

	}

}
