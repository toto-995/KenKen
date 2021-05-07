package test;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import game_grid.GameGrid;
import game_grid.Grid;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GridTest {
	static Grid grid = new GameGrid(5);
	
	@Test(expected = IllegalArgumentException.class)
	public void setNegativeVal() {
		grid.setVal(-5, 0, 0);
	}
	
	@Test
	public void setPositiveVal() {
		grid.setVal(5, 0, 0);
		Assert.assertTrue(grid.getVal(0, 0) == 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNegativeCol() {
		grid.setVal(5, 1, -1);
	}
	
	@Test
	public void setPositiveCol() {
		grid.setVal(4, 4, 4);
		Assert.assertTrue(grid.getVal(4, 4) == 4);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNegativeRow() {
		grid.setVal(5, -9, 0);
	}
	
	@Test
	public void setPositiveRow() {
		grid.setVal(3, 2, 0);
		Assert.assertTrue(grid.getVal(2, 0) == 3);
	}
	
	@Test
	public void testRC() {
		grid.setVal(1, 0, 0);
		Assert.assertTrue(grid.verificaRC(1, new Point(0,3))== false);
	}
	
	@Test
	public void testBlock() {
		List<Point> l =new LinkedList<Point>(); 
		l.add(new Point(0,0));
		l.add(new Point(0,1));
		grid.addBlock(l, 2, 6, '+');
		Assert.assertTrue(grid.verificaBlock(5, new Point(0,1))==false);
	}
}
