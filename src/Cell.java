public class Cell {
    int x, y;
    int bombsNear;
    boolean isBomb;
    CellState state;

    Cell() {
        x = -1;
        y = -1;
        isBomb = false;
        CellState state = CellState.closed;
    }

    Cell(int _x, int _y, boolean _isBomb) {
        x = _x;
        y = _y;
        isBomb = _isBomb;
        CellState state = CellState.closed;
    }

    boolean Mark() {
        if (state != CellState.opened) {
            state = CellState.marked;
            return true;
        }
        return false;
    }

    boolean Open() {
        if (state != CellState.opened) {
            state = CellState.opened;
            return true;
        } else
            return false;

    }
}
