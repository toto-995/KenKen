package game_grid;

public class GameGridFactory implements GridFactory{

	@Override
	public Grid createGame(int d) {
		return new GameGrid(d);
	}

}
