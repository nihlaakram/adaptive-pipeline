package lk.ac.iit.visual;


import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ac.iit.core.analyser.data.AnalyserReport;


/**
 * Sample application that shows examples of the different layout panes
 * provided by the JavaFX layout API.
 * The resulting UI is for demonstration purposes only and is not interactive.
 */


public class MainFX extends Application {

    TableView<AnalyserReport> table;


    @Override
    public void start(Stage stage) {

        BorderPane border = new BorderPane();

        HBox hbox = addHBox();
        border.setTop(hbox);
        border.setLeft(addSpace());

        border.setRight(addGridPane());

        border.setCenter(addAnimation());

        BorderPane border1 = new BorderPane();
        border1.setCenter(addVBox());
        border.setBottom(border1);

        Scene scene = new Scene(border);
        stage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();


        stage.setWidth(1500);
        stage.setHeight(900);
        stage.setTitle("JAutoPipe");
        stage.show();
    }

    private Node addAnimation() {

        Polygon rectangle1 = new Polygon();
        Polygon rectangle2 = new Polygon();

        //lines
        Polygon line1 = new Polygon();
        Polygon line2 = new Polygon();

        //Adding coordinates to the hexagon
        rectangle1.getPoints().addAll(new Double[]{
                200.0, 50.0,
                300.0, 50.0,
                300.0, 100.0,
                200.0, 100.0
        });
        rectangle2.getPoints().addAll(new Double[]{
                500.0, 50.0,
                600.0, 50.0,
                600.0, 100.0,
                500.0, 100.0
        });
        //Adding coordinates to the hexagon
        line1.getPoints().addAll(new Double[]{
                50.0, 73.0,
                200.0, 73.0,
                200.0, 77.0,
                50.0, 77.0
        });
        line2.getPoints().addAll(new Double[]{
                50.0, 73.0,
                400.0, 173.0,
                400.0, 177.0,
                50.0, 77.0
        });
        //Setting the fill color for the hexagon

        rectangle2.setFill(Color.BLUE);
        line1.setFill(Color.BLACK);
        line2.setFill(Color.BLACK);
        //Creating a Group object
        Group root = new Group(rectangle1, rectangle2, line1, line2);

        //transition

        FillTransition ft = new FillTransition(Duration.millis(3000), rectangle1, Color.BLUE, Color.RED);
        ft.setCycleCount(4);
        ft.setAutoReverse(true);

        ft.play();

        return root;
    }

    private HBox addHBox() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #ffffff;");

        Text text = new Text("JPipe: Performance Analysis Report");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 18));


        hbox.getChildren().add(text);
        return hbox;
    }


    /*
     * Creates a VBox with a list of links for the left region
     */
    private VBox addVBox() {

        TableColumn<AnalyserReport, String> nameColumn = new TableColumn<>("BeforeScaling");
        nameColumn.setMinWidth(200);
        // nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Price column
        TableColumn<AnalyserReport, Double> priceColumn = new TableColumn<>("After Scaling");
        priceColumn.setMinWidth(200);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("tps"));

        //Quantity column
        TableColumn<AnalyserReport, String> quantityColumn = new TableColumn<>("Improvement Percentage");
        quantityColumn.setMinWidth(200);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("avgLatency"));

        table = new TableView<>();
        table.setItems(getPerformance());
        table.getColumns().addAll(nameColumn, priceColumn, quantityColumn);
        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: #ffffff;");
        vBox.getChildren().addAll(table);

        return vBox;
    }

    private VBox addSpace() {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10)); // Set all sides to 10
        vbox.setSpacing(8);
        vbox.setStyle("-fx-background-color: #ffffff;");

        return vbox;
    }


    public ObservableList<AnalyserReport> getPerformance() {
        ObservableList<AnalyserReport> analysedData = FXCollections.observableArrayList();
        analysedData.add(new AnalyserReport(859.00, 20));
        analysedData.add(new AnalyserReport(859.00, 20));
        ;
        //System.out.println(products.size());
        return analysedData;
    }


    private GridPane addGridPane() {

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        // Category in column 2, row 1
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("No. of requests");
        yAxis.setLabel("TPS");
        //creating the chart
        final LineChart<String, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("TPS Monitoring");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("TPS");
        //populating the series with data
        series.getData().add(new XYChart.Data("0", 0));
        series.getData().add(new XYChart.Data("X", 23));
        series.getData().add(new XYChart.Data("X+1", 1023));
        series.getData().add(new XYChart.Data("Y", 1023));
        grid.add(lineChart, 0, 0);
        lineChart.getData().add(series);

        // Title in column 3, row 1
        final CategoryAxis xAxis1 = new CategoryAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("No. of requests");
        yAxis1.setLabel("TPS");
        //creating the chart
        final LineChart<String, Number> lineChart1 =
                new LineChart<>(xAxis1, yAxis1);

        lineChart1.setTitle("TPS Monitoring");
        //defining a series
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("TPS");
        //populating the series with data
        series1.getData().add(new XYChart.Data("0", 0));
        series1.getData().add(new XYChart.Data("X", 500));
        series1.getData().add(new XYChart.Data("X+1", 1023));
        series1.getData().add(new XYChart.Data("Y", 1023));

        lineChart1.getData().add(series1);

        grid.add(lineChart1, 0, 1);
        grid.setStyle("-fx-background-color: #ffffff;");

        return grid;
    }


}



