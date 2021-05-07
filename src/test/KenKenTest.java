package test;

import java.awt.Point;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.Assert;
import backtracking.KenKen;
import game_grid.Block;
import game_grid.GameGridFactory;
import game_grid.Grid;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KenKenTest {
	static Grid grid;

	@BeforeClass
	public static void creaGrid() {
		grid = new GameGridFactory().createGame(5);
		Point p;
		Block b1 = new Block(1);
		b1.setOperando(14);
		b1.setOperatore('+');
		b1.add(grid.getBox(p = new Point(0, 0)));
		b1.add(grid.getBox(p = new Point(1, 0)));
		b1.add(grid.getBox(p = new Point(0, 1)));
		b1.add(grid.getBox(p = new Point(1, 1)));
		grid.getBox(p = new Point(0, 0)).setBlock(b1);
		grid.getBox(p = new Point(1, 0)).setBlock(b1);
		grid.getBox(p = new Point(0, 1)).setBlock(b1);
		grid.getBox(p = new Point(1, 1)).setBlock(b1);
		grid.getBlockList().add(b1);

		Block b2 = new Block(2);
		b2.setOperando(3);
		b2.setOperatore('-');
		b2.add(grid.getBox(p = new Point(1, 2)));
		b2.add(grid.getBox(p = new Point(0, 2)));
		grid.getBox(p = new Point(1, 2)).setBlock(b2);
		grid.getBox(p = new Point(0, 2)).setBlock(b2);
		grid.getBlockList().add(b2);

		Block b3 = new Block(3);
		b3.setOperando(4);
		b3.setOperatore('/');
		b3.add(grid.getBox(p = new Point(0, 3)));
		b3.add(grid.getBox(p = new Point(0, 4)));
		grid.getBox(p = new Point(0, 3)).setBlock(b3);
		grid.getBox(p = new Point(0, 4)).setBlock(b3);
		grid.getBlockList().add(b3);

		Block b4 = new Block(4);
		b4.setOperando(2);
		b4.setOperatore('-');
		b4.add(grid.getBox(p = new Point(1, 3)));
		b4.add(grid.getBox(p = new Point(1, 4)));
		grid.getBox(p = new Point(1, 3)).setBlock(b4);
		grid.getBox(p = new Point(1, 4)).setBlock(b4);
		grid.getBlockList().add(b4);

		Block b5 = new Block(5);
		b5.setOperando(2);
		b5.setOperatore('-');
		b5.add(grid.getBox(p = new Point(2, 0)));
		b5.add(grid.getBox(p = new Point(2, 1)));
		grid.getBox(p = new Point(2, 0)).setBlock(b5);
		grid.getBox(p = new Point(2, 1)).setBlock(b5);
		grid.getBlockList().add(b5);

		Block b6 = new Block(6);
		b6.setOperando(40);
		b6.setOperatore('*');
		b6.add(grid.getBox(p = new Point(2, 2)));
		b6.add(grid.getBox(p = new Point(3, 2)));
		b6.add(grid.getBox(p = new Point(3, 3)));
		b6.add(grid.getBox(p = new Point(4, 3)));
		grid.getBox(p = new Point(2, 2)).setBlock(b6);
		grid.getBox(p = new Point(3, 2)).setBlock(b6);
		grid.getBox(p = new Point(3, 3)).setBlock(b6);
		grid.getBox(p = new Point(4, 3)).setBlock(b6);
		grid.getBlockList().add(b6);

		Block b7 = new Block(7);
		b7.setOperando(9);
		b7.setOperatore('+');
		b7.add(grid.getBox(p = new Point(2, 3)));
		b7.add(grid.getBox(p = new Point(2, 4)));
		b7.add(grid.getBox(p = new Point(3, 4)));
		grid.getBox(p = new Point(2, 3)).setBlock(b7);
		grid.getBox(p = new Point(2, 4)).setBlock(b7);
		grid.getBox(p = new Point(3, 4)).setBlock(b7);
		grid.getBlockList().add(b7);

		Block b8 = new Block(8);
		b8.setOperando(2);
		b8.setOperatore('/');
		b8.add(grid.getBox(p = new Point(3, 0)));
		b8.add(grid.getBox(p = new Point(4, 0)));
		grid.getBox(p = new Point(3, 0)).setBlock(b8);
		grid.getBox(p = new Point(4, 0)).setBlock(b8);
		grid.getBlockList().add(b8);
		Block b9 = new Block(9);
		b9.setOperando(12);
		b9.setOperatore('*');
		b9.add(grid.getBox(p = new Point(3, 1)));
		b9.add(grid.getBox(p = new Point(4, 1)));
		b9.add(grid.getBox(p = new Point(4, 2)));
		grid.getBox(p = new Point(3, 1)).setBlock(b9);
		grid.getBox(p = new Point(4, 1)).setBlock(b9);
		grid.getBox(p = new Point(4, 2)).setBlock(b9);
		grid.getBlockList().add(b9);

		Block b10 = new Block(10);
		b10.setOperando(5);
		b10.setOperatore('+');
		b10.add(grid.getBox(p = new Point(4, 4)));
		grid.getBox(p = new Point(4, 4)).setBlock(b10);
		grid.getBlockList().add(b10);
	}

	// Risolvibili
	@Test
	public void verificaTrue() {
		KenKen ken = new KenKen(5, 5, grid);
		ken.risolvi();
		for (Grid gr : ken.getSoluzioni())
			Assert.assertTrue(gr.test(grid.getBlockList()));
	}

	// NonRisolvibili
	@Test
	public void verificoFalse() {
		grid.getBlock(new Point(4, 4)).setOperando(4);
		KenKen ken = new KenKen(5, 5, grid);
		ken.risolvi();
		Assert.assertTrue(ken.getSoluzioni().size()==0);
	}

}
