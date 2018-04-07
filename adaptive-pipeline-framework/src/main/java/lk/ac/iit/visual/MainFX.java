package lk.ac.iit.visual;


import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ac.iit.core.analyser.Analyser;
import lk.ac.iit.core.analyser.data.AnalysedData;
import lk.ac.iit.core.analyser.data.AnalyserReport;

import static com.sun.org.apache.xml.internal.serializer.Version.getProduct;


/**
 * Sample application that shows examples of the different layout panes
 * provided by the JavaFX layout API.
 * The resulting UI is for demonstration purposes only and is not interactive.
 */


public class MainFX extends Application {

    TableView<AnalyserReport> table;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(MainFX.class, args);
    }

    @Override
    public void start(Stage stage) {

        BorderPane border = new BorderPane();

        HBox hbox = addHBox();
        border.setTop(hbox);
       border.setLeft(addSpace());


// To see only the grid in the center, uncomment the following statement
// comment out the setCenter() call farther down


// Choose either a TilePane or FlowPane for right region and comment out the
// one you aren't using
        border.setRight(addGridPane());
//        border.setRight(addTilePane());

// To see only the grid in the center, comment out the following statement
// If both setCenter() calls are executed, the anchor pane from the second
// call replaces the grid from the first call
//        border.setCenter(addAnchorPane(addGridPane()));
        border.setCenter(addVBox());

        Scene scene = new Scene(border);
        stage.setScene(scene);
        stage.setTitle("Layout Sample");
        stage.show();
    }

    private HBox addHBox() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);   // Gap between nodes
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



    public ObservableList<AnalyserReport> getPerformance(){
        ObservableList<AnalyserReport> analysedData = FXCollections.observableArrayList();
        analysedData.add(new AnalyserReport(859.00, 20));
        analysedData.add(new AnalyserReport(859.00, 20));;
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
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<String,Number> lineChart =
                new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        series.getData().add(new XYChart.Data("0", 0));
        series.getData().add(new XYChart.Data("X", 23));
        series.getData().add(new XYChart.Data("X+1", 1023));
        series.getData().add(new XYChart.Data<>("Y", 1023));
        grid.add(lineChart, 0, 0);
        lineChart.getData().add(series);

        // Title in column 3, row 1

        final NumberAxis xAxis1 = new NumberAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> lineChart1 =
                new LineChart<Number,Number>(xAxis1,yAxis1);

        lineChart1.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("My portfolio");
        //populating the series with data
        series1.getData().add(new XYChart.Data(1, 23));
        series1.getData().add(new XYChart.Data(2, 14));
        series1.getData().add(new XYChart.Data(3, 15));
        series1.getData().add(new XYChart.Data(4, 24));
        series1.getData().add(new XYChart.Data(5, 34));
        series1.getData().add(new XYChart.Data(6, 36));
        series1.getData().add(new XYChart.Data(7, 22));
        series1.getData().add(new XYChart.Data(8, 45));
        series1.getData().add(new XYChart.Data(9, 43));
        series1.getData().add(new XYChart.Data(10, 17));
        series1.getData().add(new XYChart.Data(11, 29));
        series1.getData().add(new XYChart.Data(12, 25));
        grid.add(lineChart1, 0, 1);
        grid.setStyle("-fx-background-color: #ffffff;");
        lineChart1.getData().add(series1);

//        // Subtitle in columns 2-3, row 2
//        Text chartSubtitle = new Text("Goods and Services");
//        grid.add(chartSubtitle, 1, 1, 2, 1);
//
//
//        // Left label in column 1 (bottom), row 3
//        Text goodsPercent = new Text("Goods\n80%");
//        GridPane.setValignment(goodsPercent, VPos.BOTTOM);
//        grid.add(goodsPercent, 1, 0);
//
//        // Chart in columns 2-3, row 3
//        grid.add(new TextField("Chart image"), 1, 2, 2, 1);
//
//        // Right label in column 4 (top), row 3
//        Text servicesPercent = new Text("Services\n20%");
//        GridPane.setValignment(servicesPercent, VPos.TOP);
//        grid.add(servicesPercent, 3, 2);

//        grid.setGridLinesVisible(true);
        return grid;
    }

    /*
     * Creates a horizontal flow pane with eight icons in four rows
     */
    private FlowPane addFlowPane() {

        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(5, 0, 5, 0));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setPrefWrapLength(170); // preferred width allows for two columns
        flow.setStyle("-fx-background-color: DAE6F3;");

        ImageView pages[] = new ImageView[8];
        for (int i=0; i<8; i++) {
            TextField textField = new TextField("123");
            flow.getChildren().add(textField);
        }

        return flow;
    }

    /*
     * Creates a horizontal (default) tile pane with eight icons in four rows
     */
    private TilePane addTilePane() {

        TilePane tile = new TilePane();
        tile.setPadding(new Insets(5, 0, 5, 0));
        tile.setVgap(4);
        tile.setHgap(4);
        tile.setPrefColumns(2);
        tile.setStyle("-fx-background-color: DAE6F3;");

        ImageView pages[] = new ImageView[8];
        for (int i=0; i<8; i++) {
            pages[i] = new ImageView(
                    new Image(MainFX.class.getResourceAsStream(
                            "graphics/chart_"+(i+1)+".png")));
            tile.getChildren().add(pages[i]);
        }

        return tile;
    }

    /*
     * Creates an anchor pane using the provided grid and an HBox with buttons
     *
     * @param grid Grid to anchor to the top of the anchor pane
     */
    private AnchorPane addAnchorPane(GridPane grid) {

        AnchorPane anchorpane = new AnchorPane();

        Button buttonSave = new Button("Save");
        Button buttonCancel = new Button("Cancel");

        HBox hb = new HBox();
        hb.setPadding(new Insets(0, 10, 10, 10));
        hb.setSpacing(10);
        hb.getChildren().addAll(buttonSave, buttonCancel);

        anchorpane.getChildren().addAll(grid,hb);
        // Anchor buttons to bottom right, anchor grid to top
        AnchorPane.setBottomAnchor(hb, 8.0);
        AnchorPane.setRightAnchor(hb, 5.0);
        AnchorPane.setTopAnchor(grid, 10.0);

        return anchorpane;
    }
}



