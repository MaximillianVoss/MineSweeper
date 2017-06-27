import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    Field field;
    Pane _root;
    Stage _stage;
    int size = 80;
    int w = 800 + size / 2, h = 650, count = 10, bombs = 10;

    public void UpdateField(Field field) {
        Scene scene = new Scene(DrawField(field, w, h));
        _stage.setScene(scene);
        _stage.show();
        _stage.setTitle("Minesweeper");
        _stage.getIcons().add(new Image("cell.png"));
        _stage.setResizable(true);
    }

    private Parent DrawField(Field field, int width, int height) {
        Pane root = new Pane();

        for (int i = 0; i < field.size; i++) {
            for (int j = 0; j < field.size; j++) {
                CellBtn tile = new CellBtn(field.cells[i][j], size, this);
                root.getChildren().add(tile);
            }
        }
        root.setPrefSize(width, height);

        if (field.failed == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("БАБАХ");
            alert.setHeaderText(null);
            alert.setContentText("ПОТРАЧЕНО!");
            alert.showAndWait();
        }

        return root;
    }

    private Parent CreateField(int width, int height, int size) {
        field = new Field(size, bombs);
        Pane root = (Pane) DrawField(field, width, height);
        _root = root;
        root.setPrefSize(width, height);
        return root;
    }

    private void CreateStage(Stage stage) {
        Scene scene = new Scene(CreateField(w, h, count));
        _stage = stage;
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Minesweeper");
        stage.getIcons().add(new Image("cell.png"));
        stage.setResizable(true);
    }

    public void start(Stage stage) throws Exception {
        CreateStage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
