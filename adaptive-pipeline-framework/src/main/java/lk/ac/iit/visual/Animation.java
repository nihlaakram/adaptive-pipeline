//package lk.ac.iit.visual;
//
//import javafx.animation.FillTransition;
//import javafx.animation.RotateTransition;
//import javafx.application.Application;
//import static javafx.application.Application.launch;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Polygon;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//public class Animation extends Application {
//    @Override
//    public void start(Stage stage) {
//        //Creating a hexagon
//        Polygon rectangle1 = new Polygon();
//        Polygon rectangle2 = new Polygon();
//
//        //lines
//        Polygon line1 = new Polygon();
//        Polygon line2 = new Polygon();
//
//        //Adding coordinates to the hexagon
//        rectangle1.getPoints().addAll(new Double[]{
//                200.0, 50.0,
//                300.0, 50.0,
//                300.0, 100.0,
//                200.0, 100.0
//        });
//        rectangle2.getPoints().addAll(new Double[]{
//                500.0, 50.0,
//                600.0, 50.0,
//                600.0, 100.0,
//                500.0, 100.0
//        });
//        //Adding coordinates to the hexagon
//        line1.getPoints().addAll(new Double[]{
//                50.0, 73.0,
//                200.0, 73.0,
//                200.0, 77.0,
//                50.0, 77.0
//        });
//        line2.getPoints().addAll(new Double[]{
//                50.0, 73.0,
//                400.0, 173.0,
//                400.0, 177.0,
//                50.0, 77.0
//        });
//        //Setting the fill color for the hexagon
//
//        rectangle2.setFill(Color.BLUE);
//        line1.setFill(Color.BLACK);
//        line2.setFill(Color.BLACK);
//        //Creating a Group object
//        Group root = new Group(rectangle1, rectangle2, line1, line2);
//
//        //transition
//
//        FillTransition ft = new FillTransition(Duration.millis(3000), rectangle1, Color.BLUE, Color.RED);
//        ft.setCycleCount(4);
//        ft.setAutoReverse(true);
//
//        ft.play();
//        //Creating a scene object
//        Scene scene = new Scene(root, 900, 300);
//
//        //Setting title to the Stage
//        stage.setTitle("Rotate transition example ");
//
//        //Adding scene to the stage
//        stage.setScene(scene);
//
//        //Displaying the contents of the stage
//        stage.show();
//    }
//    public static void main(String args[]){
//        launch(args);
//    }
//}
