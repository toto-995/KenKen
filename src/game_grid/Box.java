package game_grid;

public class Box implements Cloneable {
	private int val;
	private Block block;
	private int riga;
	private int colonna;

	public Box() {
		block = null;
	}

	public Box(int riga, int colonna) {
		this.riga = riga;
		this.colonna = colonna;
	}

	@Override
	public Object clone() {
		try {
			Box b = (Box) super.clone();
			b.block = new Block();
			return b;
		} catch (CloneNotSupportedException e) {
			return null;
		}
		
	}

	public Box clone(Block block2) {
		try {
			Box b = (Box) super.clone();
			b.block = block2;
			return b;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public int getRiga() {
		return riga;
	}

	public void setRiga(int riga) {
		this.riga = riga;
	}

	public int getColonna() {
		return colonna;
	}

	public void setColonna(int colonna) {
		this.colonna = colonna;
	}

	public int getVal() {
		return this.val;
	}

	public void setVal(int v) {
		this.val = v;
	}

	public void setBlock(Block b) {
		this.block = b;
	}

	public void setBlockNull() {
		this.block = null;
	}

	public Block getBlock() {
		return this.block;
	}

}
