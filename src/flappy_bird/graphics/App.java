package flappy_bird.graphics;

// ---------- Imports ----------
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
// ---------- ------- ----------

public class App extends Application
{
	
	// ---------- Constants ----------
	private final int WIDTH = 1000;
	private final int HEIGHT = 600;
	// ---------- --------- ----------
	// ---------- Variables ----------
	private Scene scene;
	private Canvas canvas;
	
	private Graphics graphics;
	private Timeline timeline;
	// ---------- --------- ----------
	
	@Override
	public void start(Stage window) throws Exception{
		
		canvas = new Canvas(WIDTH, HEIGHT);
		VBox box = new VBox();
		box.getChildren().add(canvas);
		
		graphics = new Graphics(canvas);
		graphics.draw();
		graphics.firstStart();
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame frame = new KeyFrame(new Duration(graphics.getFpsDelay()), new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event)
			{
				graphics.gameTick();
				if (!graphics.isGameRunning()){
					//timeline.stop();
				}
			}
		});
		timeline.getKeyFrames().add(frame);
		
		scene = new Scene(box);
			
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER){
				if (!graphics.isGameRunning()){
					
					graphics.newGame();
					timeline.playFromStart();
					
				}
			}
			else if (e.getCode() == KeyCode.SPACE) {
				if (graphics.isGameRunning()){
					
					graphics.getWorld().jump();
		
				}
			}
		});
		
		window.setScene(scene);
		window.setTitle("Flying Chicken");
		window.getIcons().add(new Image("resources/bird_up.png"));
		window.setResizable(false);
		window.sizeToScene();
		window.show();
		window.setOnCloseRequest(e -> {
			graphics.end();
		});
		
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
}
