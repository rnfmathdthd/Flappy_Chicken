package flappy_bird.logic;

import flappy_bird.graphics.Graphics;
// ---------- Imports ----------
import flappy_bird.hitbox.HitBox;

import java.util.ArrayList;
import java.util.Random;
// ---------- ------- ----------

public class World {

	// ---------- Constants ----------
	private final double GRAVITY = 200;
	private final double JUMP_VELOCITY = 150;
	public double SCROLLING_SPEED = 125;
	private final double EDGE_BUFFER = 100;
	private final double TERMINAL_VELOCITY = 300;
	// ---------- --------- ----------
	// ---------- Variables ----------
	private final double screenWidth;
	private final double screenHeight;
	private ArrayList<Double> pipes;
	private Player player;

	private double xOffset;
	public boolean gameoverByScreen = false;
	public boolean gameoverByWall = false;
	public static int score = 0;
	// ---------- --------- ----------

	public World(final double screenWidth, final double screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		restart();
	}

	public void jump() {
		if (!isPlayerDead())
			player.setvVelocity(Math.max(player.getvVelocity() - JUMP_VELOCITY, -TERMINAL_VELOCITY));
	}

	public ArrayList<Double> getPipes() {
		return pipes;
	}

	public double getPlayerHeight() {
		return player.getHeight();
	}

	public boolean isGoingDown() {
		return player.getvVelocity() > 0;
	}

	public void restart() {
		player = new Player();
		score = 0;
		xOffset = screenWidth;

		pipes = new ArrayList<>();
		for (int i = 0; i < screenWidth / GameStats.PIPE_INTERVAL + 2; i++) {
			pipes.add(getRandomPipeHeight());
		}
	}

	public void gameTick() {
		// Moves the player
		player.setvVelocity(Math.min(player.getvVelocity() + GRAVITY / GameStats.FPS, TERMINAL_VELOCITY));
		player.setHeight(player.getHeight() + player.getvVelocity() / GameStats.FPS);

		if (xOffset < -GameStats.PIPE_WIDTH && !isPlayerDead()) {
			pipes.remove(0);
			score++;
			if(score % 3 == 0) {
				SCROLLING_SPEED += 25;
			}
			pipes.add(getRandomPipeHeight());
			xOffset += GameStats.PIPE_INTERVAL;
		}
		
		xOffset = xOffset - SCROLLING_SPEED / GameStats.FPS;

	}

	public boolean isPlayerDead() {

		ArrayList<HitBox> pipeBoxes = new ArrayList<>();
		HitBox playerBox = new HitBox(GameStats.PLAYER_OFFSET, player.getHeight(), GameStats.PLAYER_WIDTH,
				GameStats.PLAYER_HEIGHT);

		if (player.getHeight() <= 0 || player.getHeight() >= screenHeight - GameStats.PLAYER_HEIGHT) {
			gameoverByScreen = true;
			return true;
		}
		for (int i = 0; i < pipes.size(); i++) {
			pipeBoxes.add(new HitBox(xOffset + i * GameStats.PIPE_INTERVAL, 0, GameStats.PLAYER_WIDTH, pipes.get(i)));
			pipeBoxes.add(new HitBox(xOffset + i * GameStats.PIPE_INTERVAL, pipes.get(i) + GameStats.PIPE_SEPARATION,
					GameStats.PLAYER_WIDTH, screenHeight - pipes.get(i) - GameStats.PIPE_SEPARATION));
		}
		for (HitBox box : pipeBoxes) {
			if (playerBox.collision(box)) {
				gameoverByWall = true;
				return true;
			}
		}

		return false;
	}

	public double getXOffset() {
		return xOffset;
	}

	private double getRandomPipeHeight() {
		Random rand = new Random();
		return EDGE_BUFFER + (screenHeight - 2 * EDGE_BUFFER - GameStats.PIPE_SEPARATION) * rand.nextDouble();
	}
	

}
