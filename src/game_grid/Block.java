package game_grid;

import java.util.LinkedList;
import java.util.List;

public class Block implements Cloneable {
	private int id;
	private List<Box> list;
	private int operando;
	private char operatore;
	private int assegnati;
	private boolean stopId;

	public Block(int id) {
		this.id = id;
		this.list = new LinkedList<Box>();
		this.assegnati = 0;
		this.operatore = '.';
		this.stopId = false;
	}

	public Block() {

	}

	public boolean isStopId() {
		return stopId;
	}

	public void setStopId(boolean stopId) {
		this.stopId = stopId;
	}

	@Override
	public Object clone() {
		try {
			Block b = (Block) super.clone();
			b.list = new LinkedList<Box>();
			for (Box box : list) {
				Box bx = new Box();
				bx = (Box) box.clone(b);
				b.getList().add(bx);
			}

			return b;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public int getID() {
		return id;
	}

	public void setID(int i) {
		this.id = i;
	}

	public void add(Box box) {
		this.list.add(box);
	}

	public void remove(Box box) {
		this.list.remove(box);
	}

	public List<Box> getList() {
		return list;
	}

	public void setListNull() {
		this.list = new LinkedList<Box>();
	}

	public void setOperando(int i) {
		this.operando = i;
	}

	public int getOperando() {
		return this.operando;
	}

	public void setOperatore(char c) {
		this.operatore = c;
	}

	public char getOperatore() {
		return this.operatore;
	}

	public void addAssegnati() {
		assegnati += 1;
	}

	public void removeAssegnati() {
		assegnati -= 1;
	}

	public int getAssegnati() {
		return assegnati;
	}

}
