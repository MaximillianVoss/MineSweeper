import java.util.ArrayList;
import java.util.List;

public class Field {
    public Cell[][] cells;
    int size;
    boolean failed;

    Field(int _size, int bombs) {
        failed = false;
        size = _size;
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = ((int) Math.ceil(Math.random() * 100)) % 2;
                if (value == 1 && bombs > 0) {
                    cells[i][j] = new Cell(i, j, true);
                    bombs--;
                } else
                    cells[i][j] = new Cell(i, j, false);
                cells[i][j].state = CellState.closed;
            }
        }
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                cells[i][j].bombsNear = GetBombsCount(cells[i][j]);
            }
    }

    boolean IsCorrect(int value) {
        if (value > -1 && value < size)
            return true;
        return false;
    }

    List<Cell> GetNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int[] points = (cell.y % 2 != 0) ? new int[]{
                0, -1,
                1, -1,
                -1, 0,
                1, 0,
                0, 1,
                1, 1
        } : new int[]{
                -1, -1,
                0, -1,
                -1, 0,
                1, 0,
                -1, 1,
                0, 1
        };
        for (int i = 0; i < points.length; i++) {
            int dx = points[i], dy = points[++i];
            int newX = cell.x + dx, newY = cell.y + dy;

            if (IsCorrect(newX) && IsCorrect(newY))
                neighbors.add(cells[(int) newX][(int) newY]);
        }
        return neighbors;
    }

    int GetBombsCount(Cell cell) {
        int counter = 0;
        List<Cell> neighbours = GetNeighbors(cell);
        for (int i = 0; i < neighbours.size(); i++)
            if (neighbours.get(i).isBomb)
                counter++;
        return counter;
    }

    public void Select(Cell cell) {
        if (cell.state == CellState.closed) {
            cell.state = CellState.opened;
            if (cell.isBomb)
                failed = true;
            cells[cell.x][cell.y] = cell;
            List<Cell> list = GetNeighbors(cell);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).bombsNear == 0) {
                    Select(list.get(i));
                } else if (!list.get(i).isBomb) {
                    cells[list.get(i).x][list.get(i).y].state = CellState.opened;
                }
            }
        }
    }

    public void Check() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                if (cells[i][j].isBomb && cells[i][j].state == CellState.opened) {
                    failed = true;
                    break;
                }
            }
    }
}
