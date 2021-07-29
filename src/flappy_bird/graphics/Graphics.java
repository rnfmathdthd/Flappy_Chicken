package flappy_bird.graphics;

// ---------- Imports ----------
import flappy_bird.logic.GameStats;
import flappy_bird.logic.World;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

import java.time.Duration;
import java.util.ArrayList;
// ---------- ------- ----------

public class Graphics{
	
	// ---------- Constants ----------
	
	// ---------- --------- ----------
	// ---------- Variables ----------
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	
	private World world;
	private boolean gameRunning;
	static double positionX;
	static double positionY;
	static boolean myevent = true;
	private int gameoverimgSizeX = 400;
	private int gameoverimgSizeY = 200;
	// ---------- --------- ----------
	
	public Graphics(final Canvas canvas){
		this.canvas = canvas;
		this.graphicsContext = canvas.getGraphicsContext2D();
		
		gameRunning = false;
		world = new World(canvas.getWidth(), canvas.getHeight());
	}
	
	public void draw() {
		Image background = new Image("resources/background1.png");
		graphicsContext.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
		
		double offset = world.getXOffset();
		ArrayList<Double> pipeHeights = world.getPipes();
		for (int i = 0; i < pipeHeights.size(); i++) {
			drawPipesAtLoc(offset + i * GameStats.PIPE_INTERVAL, pipeHeights.get(i));
		}
		graphicsContext.drawImage(new Image("resources/Score.png"), 0, 20, 70, 25);
		setScore();
		if (!world.gameoverByWall && !world.gameoverByScreen) {
			graphicsContext.drawImage(new Image("resources/bird_" + (world.isGoingDown() ? "down" : "up") + ".png"),
					GameStats.PLAYER_OFFSET, world.getPlayerHeight(), GameStats.PLAYER_WIDTH, GameStats.PLAYER_HEIGHT);
			positionX = GameStats.PLAYER_OFFSET;
			positionY = world.getPlayerHeight();
		} else {
			if (offset - GameStats.PLAYER_WIDTH > 0 && myevent && world.gameoverByScreen) {
				graphicsContext.drawImage(new Image("resources/bird_" + (world.isGoingDown() ? "down" : "up") + ".png"),
						offset - GameStats.PLAYER_WIDTH, -500, GameStats.PLAYER_WIDTH, GameStats.PLAYER_HEIGHT);
				gameoverimg();
			} else if (offset - GameStats.PLAYER_WIDTH > 0 && myevent && world.gameoverByWall) {
				graphicsContext.drawImage(new Image("resources/bird_" + (world.isGoingDown() ? "down" : "up") + ".png"),
						offset - GameStats.PLAYER_WIDTH, positionY, GameStats.PLAYER_WIDTH, GameStats.PLAYER_HEIGHT);
				gameoverimg();
			} else {
				myevent = false;
				graphicsContext.drawImage(new Image("resources/bird_" + (world.isGoingDown() ? "down" : "up") + ".png"),
						-500, positionY, GameStats.PLAYER_WIDTH, GameStats.PLAYER_HEIGHT);
				gameoverimg();
			}
		}
	}
	public void setScore() {
		String scoreimg = Integer.toString(world.score);
		for(int i=0; i<scoreimg.length(); i++) {
			switch(scoreimg.charAt(i)) {
			case'1':
				graphicsContext.drawImage(new Image("resources/1.png"), i*25+100, 20, 20, 25);
				break;
			case'2':
				graphicsContext.drawImage(new Image("resources/2.png"), i*25+100, 20, 20, 25);
				break;
			case'3':
				graphicsContext.drawImage(new Image("resources/3.png"), i*25+100, 20, 20, 25);
				break;
			case'4':
				graphicsContext.drawImage(new Image("resources/4.png"), i*25+100, 20, 20, 25);
				break;
			case'5':
				graphicsContext.drawImage(new Image("resources/5.png"), i*25+100, 20, 20, 25);
				break;
			case'6':
				graphicsContext.drawImage(new Image("resources/6.png"), i*25+100, 20, 20, 25);
				break;
			case'7':
				graphicsContext.drawImage(new Image("resources/7.png"), i*25+100, 20, 20, 25);
				break;
			case'8':
				graphicsContext.drawImage(new Image("resources/8.png"), i*25+100, 20, 20, 25);
				break;
			case'9':
				graphicsContext.drawImage(new Image("resources/9.png"), i*25+100, 20, 20, 25);
				break;
			case'0':
				graphicsContext.drawImage(new Image("resources/0.png"), i*25+100, 20, 20, 25);
				break;
			}
		}
	}
	public void gameoverimg() {
		graphicsContext.drawImage(new Image("resources/gameover.png"), 
				(canvas.getWidth()/2)-(gameoverimgSizeX/2), (canvas.getHeight()/2)-(gameoverimgSizeY/2), gameoverimgSizeX, gameoverimgSizeY);
		
		graphicsContext.drawImage(new Image("resources/restart.png"), 
				(canvas.getWidth()/2)-(gameoverimgSizeX/2) + 87, (canvas.getHeight()/2)-(gameoverimgSizeY/2) + 250, 250, 100);
	}
	
	public World getWorld(){
		return world;
	}
	
	public void firstStart() {
		graphicsContext.drawImage(new Image("resources/start.png"), 
				
				(canvas.getWidth()/2)-(gameoverimgSizeX/2) + 87, (canvas.getHeight()/2)-(gameoverimgSizeY/2) + 250, 250, 100);
	}
	
	public void gameTick(){
		world.gameTick();
		draw();
		
		gameRunning = !world.isPlayerDead();
	}
	
	public void drawPipesAtLoc(final double xOffset, final double yOffset){
		graphicsContext.drawImage(new Image("resources/pipe_up.png"), 
				xOffset, yOffset + GameStats.PIPE_SEPARATION, GameStats.PIPE_WIDTH, canvas.getHeight());
		
		graphicsContext.drawImage(new Image("resources/pipe_down.png"), 
				xOffset, yOffset - canvas.getHeight(), GameStats.PIPE_WIDTH, canvas.getHeight());
	}
	
	public void newGame(){
		world.restart();
		world.gameoverByScreen = false;
		world.gameoverByWall = false;
		myevent = true;
		
		gameRunning = true;
	}
	public void end(){
		gameRunning = false;
	}
	
	public boolean isGameRunning(){
		return gameRunning;
	}
	
	public int getFpsDelay(){
		return 1000 / GameStats.FPS;
	}
	
}
