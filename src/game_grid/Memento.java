package game_grid;

import java.util.List;

public interface Memento {

	Box[][] getGridMemento();

	void setGridMemento(Box[][] b);

	void setMemento(Box[][] b, List<Block> list);

}
