package game_grid;

import java.awt.Point;
import java.util.List;

public interface Grid {
	void setVal(int val, int r, int c);

	int getVal(int r, int c);

	void setDimension(int d);

	int getDimension();

	Block getBlock(Point p);

	void addBlock(List<Point> l, int id, int operando, char operatore);

	Box getBox(Point p);

	Box[][] getGrid();

	boolean verificaBlock(Integer scelta, Point puntoDiScelta);

	boolean verificaRC(Integer scelta, Point puntoDiScelta);

	void setGrid(Box[][] b);

	List<Block> getBlockList();

	Memento createMemento();

	void restoreMemento(Memento m);

	Memento firstMemento();

	boolean test(List<Block> l);

}
