package logic;

import java.awt.Point;
import java.util.List;

import game_grid.Block;
import game_grid.Memento;

public interface Facade {
	public void solve();
	
	public void viewNSol();				
										
	public void viewPrevSol();			
	
	public void addBlock(List<Point> l, int id, int operando, char operatore);

	List<Block> getList();

	Memento createMemento();

	void restoreMemento(Memento m);

	Memento firstMemento();

}
