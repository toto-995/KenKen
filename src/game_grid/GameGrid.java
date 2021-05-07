package game_grid;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class GameGrid implements Grid {
	private int dimension;
	private Box[][] grid;
	private List<Block> blockList;

	public GameGrid(int d) {
		blockList = new LinkedList<Block>();
		this.dimension = d;
		this.grid = new Box[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				grid[i][j] = new Box(i, j);
			}
		}
	}// GameGrid

	// InnerClass
	public class GridMemento implements Memento {

		private Box[][] box;
		private List<Block> blockList;

		public GridMemento(int dimensione) {
			this.box = new Box[dimensione][dimensione];
			this.blockList = new LinkedList<Block>();
		}

		@Override
		public Box[][] getGridMemento() {
			return this.box;
		}

		public List<Block> getList() {
			return this.blockList;
		}

		@Override
		public void setGridMemento(Box[][] b) {
			for (int i = 0; i < b.length; i++)
				for (int j = 0; j < b.length; j++) {
					box[i][j] = (Box) b[i][j].clone();
				}
		}

		@Override
		public void setMemento(Box[][] b, List<Block> list) {
			for (Block block : list) {
				blockList.add((Block) block.clone());
			}
			for (int i = 0; i < b.length; i++)
				for (int j = 0; j < b.length; j++)
					if (!serchBox(b, i, j))
						box[i][j] = (Box) b[i][j].clone();
		}

		private boolean serchBox(Box[][] b, int i, int j) {
			for (Block block : blockList)
				for (Box bx : block.getList())
					if (bx.getRiga() == i && bx.getColonna() == j) {
						box[i][j] = bx;
						return true;
					}
			return false;
		}

	}
	// Fine InnerClass

	@Override
	public boolean verificaBlock(Integer scelta, Point puntoDiScelta) {

		Block block = getBlock(puntoDiScelta);
		List<Box> l = block.getList();
		int tot = scelta;
		switch (block.getOperatore()) {
		case '+':
			for (Box i : l)
				tot += i.getVal();
			if (tot > block.getOperando())
				return false;
			else if (block.getAssegnati() + 1 == l.size() && tot != block.getOperando())
				return false;
			break;
		case '*':
			for (Box i : l) {
				if (i.getVal() == 0)
					tot = tot * 1;
				else
					tot *= i.getVal();
			}
			if (tot > block.getOperando())
				return false;
			else if (block.getAssegnati() + 1 == l.size() && tot != block.getOperando())
				return false;
			break;
		case '-':
			for (Box i : l) {
				if (tot < i.getVal())
					tot = i.getVal() - tot;
				else {
					tot -= i.getVal();
				}
			} // for
			if (block.getAssegnati() + 1 == l.size() && tot != block.getOperando()) {
				return false;
			}
			break;
		case '/':
			double t = (double) tot;
			for (Box i : l) {
				double v = (double) i.getVal();
				if (i.getVal() == 0) {
					t = t / 1;
				} else if (t < i.getVal()) {
					t = v / t;
				} else {
					t /= v;
				}
			} // for
			if (block.getAssegnati() + 1 == l.size() && t != (double) block.getOperando()) {
				return false;
			}
			break;
		}// switch
		return true;
	}// verificaOp

	@Override
	public boolean verificaRC(Integer scelta, Point puntoDiScelta) {
		for (int i = 0; i < this.dimension; i++) {
			if (getVal(puntoDiScelta.x, i) == scelta && puntoDiScelta.y != i)
				return false;
			if (getVal(i, puntoDiScelta.y) == scelta && puntoDiScelta.x != i)
				return false;

		}
		return true;
	}

	@Override
	public void setVal(int val, int r, int c) {
		controlla(val, r, c);
		this.grid[r][c].setVal(val);
	}

	private void controlla(int val, int r, int c) {
		if(r>=dimension || r<0 || c>=dimension || c<0 || val<0 || val > dimension)
			throw new IllegalArgumentException();
		
	}

	@Override
	public int getVal(int r, int c) {
		return grid[r][c].getVal();
	}

	@Override
	public void setDimension(int d) {
		this.dimension = d;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public Block getBlock(Point p) {
		return grid[p.x][p.y].getBlock();
	}

	@Override
	public void addBlock(List<Point> l, int id, int operando, char operatore) {
		Block b = new Block(id);
		b.setOperando(operando);
		b.setOperatore(operatore);
		for (Point i : l) {
			grid[i.x][i.y].setBlock(b);
			b.add(grid[i.x][i.y]);
		}
		blockList.add(b);
	}

	@Override
	public List<Block> getBlockList() {
		return blockList;
	}

	@Override
	public Box getBox(Point p) {
		return grid[p.x][p.y];
	}

	@Override
	public Box[][] getGrid() {
		return this.grid;
	}

	@Override
	public Memento firstMemento() {
		Memento m = new GridMemento(dimension);
		m.setGridMemento(this.grid);
		return m;
	}

	@Override
	public Memento createMemento() {
		Memento m = new GridMemento(dimension);
		m.setMemento(this.grid, this.blockList);
		return m;
	}

	@Override
	public void setGrid(Box[][] b) {
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = null;
				if (b[i][j] != null)
					grid[i][j] = b[i][j];
			}
	}

	@Override
	public void restoreMemento(Memento m) {
		if (!(m instanceof GridMemento))
			throw new IllegalArgumentException();

		GridMemento gm = (GridMemento) m;
		this.setGrid(gm.getGridMemento());
		this.setBlockList(gm.getList());
	}

	public void setBlockList(List<Block> blockL) {
		blockList.clear();
		for (Block b : blockL) {
			this.blockList.add(b);
		}
	}

	@Override
	public boolean test(List<Block> list) {
		for(Block b : list)
			for(Box box : b.getList())
				grid[box.getRiga()][box.getColonna()].setBlock(b);
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				List<Integer> riga = new LinkedList<Integer>();
				List<Integer> colonna = new LinkedList<Integer>();
				for (int k = 0; k < dimension; k++) {
					if (k != j)
						riga.add(grid[i][k].getVal());
					if (k != i)
						colonna.add(grid[k][j].getVal());
				}
				if (riga.contains(grid[i][j].getVal()) || colonna.contains(grid[i][j].getVal()))
					return false;
				if(!verificaB(grid[i][j].getVal(), new Point(i,j)))
					return false;
			}
		}
		return true;
	}

	private boolean verificaB(Integer scelta, Point puntoDiScelta) {
		Block block = getBlock(puntoDiScelta);
		List<Box> l = block.getList();
		int tot = 0;
		switch (block.getOperatore()) {
		case '+':
			for (Box i : l)
				tot += grid[i.getRiga()][i.getColonna()].getVal();
			if (tot > block.getOperando())
				return false;
			else if (block.getAssegnati() + 1 == l.size() && tot != block.getOperando())
				return false;
			break;
		case '*':
			tot = 1;
			for (Box i : l) {
				if (grid[i.getRiga()][i.getColonna()].getVal() == 0)
					tot = tot * 1;
				else
					tot *= grid[i.getRiga()][i.getColonna()].getVal();
			}
			if (tot > block.getOperando())
				return false;
			else if (block.getAssegnati() + 1 == l.size() && tot != block.getOperando())
				return false;
			break;
		case '-':
			for (Box i : l) {
				if (tot < grid[i.getRiga()][i.getColonna()].getVal())
					tot = grid[i.getRiga()][i.getColonna()].getVal() - tot;
				else {
					tot -= grid[i.getRiga()][i.getColonna()].getVal();
				}
			} // for
			if (block.getAssegnati() + 1 == l.size() && tot != block.getOperando()) {
				return false;
			}
			break;
		case '/':
			tot = 1;
			double t = (double) tot;
			for (Box i : l) {
				double v = (double) grid[i.getRiga()][i.getColonna()].getVal();
				if (grid[i.getRiga()][i.getColonna()].getVal() == 0) {
					t = t / 1;
				} else if (t < grid[i.getRiga()][i.getColonna()].getVal()) {
					t = v / t;
				} else {
					t /= v;
				}
			} // for
			if (block.getAssegnati() + 1 == l.size() && t != (double) block.getOperando()) {
				return false;
			}
			if((int)t==block.getOperando())
				return true;
			break;
		}// switch
		if(tot == block.getOperando())
			return true;
		return false;
	}// verificaOp

}
