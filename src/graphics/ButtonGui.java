package graphics;
import javax.swing.JButton;

public class ButtonGui extends JButton {
	private int riga;
	private int colonna;
	private boolean selected = false;
	private boolean stop = false;

	public ButtonGui(String s) {
		super(s);
	}

	public ButtonGui() {
		super();
	}

	public boolean getSelect() {
		return selected;
	}

	public void setSelect(boolean selected) {
		this.selected = selected;
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

	public void setStop(boolean b) {
		stop  = b;
	}

	public boolean getStop() {
		return stop;
	}

}
