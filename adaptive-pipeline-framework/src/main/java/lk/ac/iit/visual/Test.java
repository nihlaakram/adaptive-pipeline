package lk.ac.iit.visual;

import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ac.iit.core.analyser.data.AnalyserReport;
import lk.ac.iit.visual.data.ExternalReader;
import lk.ac.iit.visual.data.PerformanceInstance;
import lk.ac.iit.visual.data.PerformanceLogger;

public class Test extends Application {

    TableView<AnalyserReport> table;


    @Override
    public void start(Stage stage) {

        ExternalReader reader = new ExternalReader().getPerformanceNumbers();
        BorderPane border = new BorderPane();

        //top title
        border.setTop(addHeader());

        //side margin on left
        border.setLeft(addSpace());

        //add performance table
        border.setRight(addReport(reader));

        //add animation component
        border.setCenter(addAnimation1(reader));

        //graphs of the two tps and latency variation
        border.setBottom(addGraphs(reader));

        Scene scene = new Scene(border);
        stage.setScene(scene);


        stage.setWidth(1500);
        stage.setHeight(900);
        stage.setTitle("JAutoPipe");
        stage.show();

    }

    private Node addAnimation1(ExternalReader reader) {

        //initial system
        Text text = new Text("Before Scaling");
        text.setX(200);
        text.setY(25);
        Polygon rectangle1 = new Polygon();
        Polygon rectangle2 = new Polygon();
        Polygon line1 = new Polygon();
        Polygon line2 = new Polygon();
        Polygon line3 = new Polygon();

        rectangle1.getPoints().addAll(new Double[]{
                200.0, 55.0,
                300.0, 55.0,
                300.0, 95.0,
                200.0, 95.0
        });
        rectangle2.getPoints().addAll(new Double[]{
                400.0, 55.0,
                500.0, 55.0,
                500.0, 95.0,
                400.0, 95.0
        });
        line1.getPoints().addAll(new Double[]{
                50.0, 73.0,
                200.0, 73.0,
                200.0, 77.0,
                50.0, 77.0
        });
        line2.getPoints().addAll(new Double[]{
                300.0, 73.0,
                400.0, 73.0,
                400.0, 77.0,
                300.0, 77.0
        });
        line3.getPoints().addAll(new Double[]{
                500.0, 73.0,
                650.0, 73.0,
                650.0, 77.0,
                500.0, 77.0
        });

        rectangle2.setFill(Color.BLUE);
        line1.setFill(Color.BLACK);
        line2.setFill(Color.BLACK);
        line3.setFill(Color.BLACK);

        //final system
        Text text1 = new Text("After Scaling");
        text1.setX(200);
        text1.setY(130);
        Polygon frectangle1 = new Polygon();
        Polygon frectangle2 = new Polygon();
        Polygon frectangle3 = new Polygon();
        Polygon fline1 = new Polygon();
        Polygon fline2 = new Polygon();
        Polygon fline3 = new Polygon();
        Polygon fline4 = new Polygon();
        Polygon fline5 = new Polygon();

        frectangle1.getPoints().addAll(new Double[]{
                200.0, 155.0,
                300.0, 155.0,
                300.0, 195.0,
                200.0, 195.0
        });
        frectangle2.getPoints().addAll(new Double[]{
                400.0, 155.0,
                500.0, 155.0,
                500.0, 195.0,
                400.0, 195.0
        });
        frectangle3.getPoints().addAll(new Double[]{
                200.0, 255.0,
                300.0, 255.0,
                300.0, 295.0,
                200.0, 295.0
        });
        fline1.getPoints().addAll(new Double[]{
                50.0, 173.0,
                200.0, 173.0,
                200.0, 177.0,
                50.0, 177.0
        });
        fline2.getPoints().addAll(new Double[]{
                300.0, 173.0,
                400.0, 173.0,
                400.0, 177.0,
                300.0, 177.0
        });
        fline3.getPoints().addAll(new Double[]{
                500.0, 173.0,
                650.0, 173.0,
                650.0, 177.0,
                500.0, 177.0
        });
        fline4.getPoints().addAll(new Double[]{
                150.0, 173.0,
                200.0, 272.0,
                200.0, 278.0,
                150.0, 177.0
        });
        fline5.getPoints().addAll(new Double[]{
                300.0, 272.0,
                350.0, 173.0,
                350.0, 177.0,
                300.0, 278.0
        });

        frectangle2.setFill(Color.BLUE);
        frectangle1.setFill(Color.BLUE);
        frectangle3.setFill(Color.BLUE);
        fline1.setFill(Color.BLACK);
        fline2.setFill(Color.BLACK);
        fline3.setFill(Color.BLACK);
        fline4.setFill(Color.BLACK);
        fline5.setFill(Color.BLACK);



        //Creating a Group object
        Group root = new Group(text, rectangle1, rectangle2, line1, line2, line3,
                text1, frectangle1,frectangle2, frectangle3, fline1, fline2, fline3,
                fline4, fline5);

        //transition
        FillTransition ft = new FillTransition(Duration.millis(2000), rectangle1, Color.BLUE, Color.RED);
        ft.setCycleCount(10);
        ft.setAutoReverse(false);
        ft.play();


        root.setStyle("-fx-background-color: #ffffff;");
        return root;
    }

    private Node addAnimation(ExternalReader reader) {

        Polygon rectangle1 = new Polygon();
        Polygon rectangle2 = new Polygon();
        Polygon rectangle3 = new Polygon();

        //lines
        Polygon line1 = new Polygon();
        Polygon line2 = new Polygon();
        Polygon line3 = new Polygon();
        Polygon line4 = new Polygon();
        Polygon line5 = new Polygon();
        Polygon line6 = new Polygon();


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
        rectangle3.getPoints().addAll(new Double[]{
                200.0, 150.0,
                300.0, 150.0,
                300.0, 200.0,
                200.0, 200.0
        });

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


        rectangle2.setFill(Color.BLUE);
        rectangle1.setFill(Color.BLUE);
        rectangle3.setFill(Color.WHITE);
        line1.setFill(Color.BLACK);
        line2.setFill(Color.BLACK);
        //Creating a Group object
        Group root = new Group(rectangle1, rectangle2, line1, line2, rectangle3);

        //transition

        FillTransition ft = new FillTransition(Duration.millis(3000), rectangle1, Color.BLUE, Color.RED);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);



        FillTransition ft1 = new FillTransition(Duration.millis(3000), rectangle3, Color.WHITE, Color.BLUE);
        ft1.setCycleCount(1);
        ft1.setAutoReverse(false);

        ft1.play();

        SequentialTransition sq = new SequentialTransition(ft, ft1);
        sq.play();

        root.setStyle("-fx-background-color: #ffffff;");
        return root;
    }

    private VBox addHeader() {

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15, 12, 15, 12));
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: #ffffff;");

        Text text = new Text("Performance Analysis Report");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Text text1 = new Text("JAutoPipe: Auto-Scaling Framework for " +
                "Pipeline Architecture");
        text1.setFont(Font.font("Arial", FontWeight.LIGHT, 14));


        vBox.getChildren().add(0, text);
        vBox.getChildren().add(1, text1);

        return vBox;
    }



    private VBox addReport(ExternalReader reader) {

        //manipulate data
        PerformanceLogger logger = reader.getPerformanceLogger();
        double befTps = getMinValue(logger.getBefore().get(0).getPerfNumbers()[1],
                logger.getBefore().get(1).getPerfNumbers()[1]);
        double afTps = getMinValue(logger.getAfter().get(0).getPerfNumbers()[1],
                logger.getAfter().get(1).getPerfNumbers()[1]);

        double befLat = logger.getBefore().get(0).getPerfNumbers()[0]+
                logger.getBefore().get(1).getPerfNumbers()[0];
        double  afLat = logger.getAfter().get(0).getPerfNumbers()[0]+
                logger.getAfter().get(1).getPerfNumbers()[0];


        double befRat = (befTps/befLat);
        double afRat = (afTps/afLat);

        double tpsPerc= ((afTps-befTps)/befTps)*100;
        double latPerc = ((befLat-afLat)/befLat)*100;
        double ratPerc = ((afRat-befRat)/befRat)*100;

        Text title = new Text("Performance Impact");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Text text = new Text("\n\nNet TPS Before Scaling : " +Math.round(befTps)+ " req/sec");
        Text text1 = new Text("Net TPS After Scaling : " +Math.round(afTps)+ " req/sec\n--");

        Text text2 = new Text("\nNet Latency Before Scaling : " +Math.round(befLat)+ " ms");
        Text text3 = new Text("Net Latency After Scaling : " +Math.round(afLat)+ " ms\n--");

        Text text4 = new Text("\nEffect on TPS : " +Math.round(tpsPerc)+ "%");

        Text text5 = new Text("Effect on Latency : " +Math.round(latPerc)+ "%");

        Text text6 = new Text("Effect on overall performance : " +Math.round(ratPerc)+ "%");



        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15, 12, 15, 12));
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: #ffffff;");
        vBox.getChildren().addAll(title);
        vBox.getChildren().addAll(text);
        vBox.getChildren().addAll(text1);
        vBox.getChildren().addAll(text2);
        vBox.getChildren().addAll(text3);
        vBox.getChildren().addAll(text4);
        vBox.getChildren().addAll(text5);
        vBox.getChildren().addAll(text6);

        return vBox;
    }

    private VBox addSpace() {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setSpacing(8);
        vbox.setStyle("-fx-background-color: #ffffff;");

        return vbox;
    }




    private GridPane addGraphs(ExternalReader reader) {

        //manipulate necessary data
        PerformanceLogger logger = reader.getPerformanceLogger();
        double befTps = getMinValue(logger.getBefore().get(0).getPerfNumbers()[1],
                logger.getBefore().get(1).getPerfNumbers()[1]);
        double afTps = getMinValue(logger.getAfter().get(0).getPerfNumbers()[1],
                logger.getAfter().get(1).getPerfNumbers()[1]);

        double befLat = logger.getBefore().get(0).getPerfNumbers()[0]+
                logger.getBefore().get(1).getPerfNumbers()[0];
        double  afLat = logger.getAfter().get(0).getPerfNumbers()[0]+
                logger.getAfter().get(1).getPerfNumbers()[0];

        //visualize
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));


        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("No. of requests");
        yAxis.setLabel("TPS (req/sec)");
        //creating the chart
        final LineChart<String,Number> lineChart =
                new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("TPS Monitoring");


        XYChart.Series series = new XYChart.Series();
        series.setName("TPS in log base 10");

        series.getData().add(new XYChart.Data("0", 0));
        series.getData().add(new XYChart.Data("X", Math.log10(befTps)));
        series.getData().add(new XYChart.Data("X+1", Math.log10(afTps)));
        series.getData().add(new XYChart.Data("Y", Math.log10(afTps)));

        lineChart.getData().add(series);
        lineChart.setMaxWidth(700);
        lineChart.setMaxHeight(300);
        grid.add(lineChart, 0, 0);


        //----------------------------------------

        final CategoryAxis xAxis1 = new CategoryAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("No. of requests");
        yAxis1.setLabel("Latency (ms)");

        final LineChart<String,Number> lineChart1 =
                new LineChart<>(xAxis1,yAxis1);

        lineChart1.setTitle("Latency Monitoring");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Latency");

        series1.getData().add(new XYChart.Data("0", 0));
        series1.getData().add(new XYChart.Data("X", befLat));
        series1.getData().add(new XYChart.Data("X+1", afLat));
        series1.getData().add(new XYChart.Data("Y", afLat));

        grid.add(lineChart1, 1, 0);
        lineChart1.getData().add(series1);
        lineChart1.setMaxWidth(700);
        lineChart1.setMaxHeight(300);
        grid.setStyle("-fx-background-color: #ffffff;");

        return grid;
    }

    private double getMinValue(double t1, double t2) {
        if (t1>t2){
            return t2;
        }
        return t1;
    }


}



