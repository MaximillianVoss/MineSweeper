import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import static java.lang.Math.sqrt;

public class CellBtn extends StackPane {
    TempData t;

    Cell cell;
    Color opened = Color.GREEN, closed = Color.GRAY, marked = Color.ORANGE, bomb = Color.RED;
    Main main;

    CellBtn(Cell _cell, int size, Main _main) {
        main = _main;
        cell = _cell;
        double sideSize = size / sqrt(3);
        Polygon btn = new Polygon
                (
                        0, (size - sideSize) / 2,
                        size / 2, 0,
                        size, (size - sideSize) / 2,
                        size, (size - sideSize) / 2 + sideSize,
                        size / 2, size,
                        0, (size - sideSize) / 2 + sideSize
                );
        btn.setStroke(Color.LIGHTGRAY);
        if (cell.isBomb && cell.state == CellState.opened)
            btn.setFill(bomb);
        else
            btn.setFill(GetColor(cell.state));

        Text text = new Text();
        text.setFont(Font.font(18));
        text.setFill(Color.BLACK);

        String pos = "";
        //String pos = " "+Integer.toString(cell.x) +" "+ Integer.toString(cell.y) + " " + Integer.toString(cell.neghbours) ;
        //text.setText(pos);

        if (cell.state == CellState.opened) {
            if (cell.isBomb)
                text.setText("Bomb");
            else if (cell.bombsNear > 0)
                text.setText(Integer.toString(cell.bombsNear) + pos);
            else
                text.setText("" + pos);
            text.setVisible(true);
        }

        getChildren().addAll(btn, text);

        double curX = (cell.y % 2 != 0) ? cell.x + 0.5 : cell.x;

        setTranslateX(curX * size);
        setTranslateY(cell.y * 0.8 * size);

        setOnMousePressed(e -> {
            Open(e.isPrimaryButtonDown(), e.isSecondaryButtonDown());
            main.field.Select(cell);
            main.UpdateField(main.field);
        });
    }

    Color GetColor(CellState state) {
        switch (state) {
            case opened:
                return opened;
            case closed:
                return closed;
            case marked:
                return marked;
            default:
                return closed;
        }
    }

    Color GetColor(boolean isBomb) {
        if (isBomb)
            return bomb;
        else
            return opened;
    }

    void Open(boolean primary, boolean secondary) {
        if (primary) //&& cell.Open())
            //cell.state = CellState.opened;
            if (secondary)
                cell.state = CellState.marked;
        main.field.cells[cell.x][cell.y] = cell;
    }

}
