package lk.ac.iit.visual;

        import java.util.HashMap;
        import java.util.Map;
        import javafx.application.Application;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.geometry.Insets;
        import javafx.scene.Group;
        import javafx.scene.Scene;
        import javafx.scene.control.Label;
        import javafx.scene.control.TableCell;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.MapValueFactory;
        import javafx.scene.control.cell.TextFieldTableCell;
        import javafx.scene.layout.StackPane;
        import javafx.scene.layout.VBox;
        import javafx.scene.text.Font;
        import javafx.stage.Stage;
        import javafx.util.Callback;
        import javafx.util.StringConverter;

        class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        TableView table = new TableView();
        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Email");

        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        emailCol.setVisible(false);

        StackPane root = new StackPane();
        root.getChildren().add(table);
        primaryStage.setScene(new Scene(root, 200, 250));
        primaryStage.show();
    }
}