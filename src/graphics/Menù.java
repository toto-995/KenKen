package graphics;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import game_grid.GridFactory;
import logic.GridFacade;

public class Menù {
	private GridFactory grid;
	private int dimensione1;
	private Object[] possibleValues = { '3','4','5','6','7','8','9'};

	public Menù(GridFactory gridF) {
		this.grid= gridF;
		ImageIcon icon = new ImageIcon("src/img/kenken.jpg");
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		Object inputO = JOptionPane.showInputDialog(null, "Scegliere la dimensione della griglia", "Dimensione",
				JOptionPane.INFORMATION_MESSAGE,icon, possibleValues, possibleValues[0]);
		if (inputO != null) {
			Character input = (Character) inputO;
			dimensione1 = (int) Character.getNumericValue(input);
			GridFacade facade = new GridFacade(grid, dimensione1);
		}
		
	}
}
