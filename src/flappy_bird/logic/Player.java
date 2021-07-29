package flappy_bird.logic;

// ---------- Imports ----------

// ---------- ------- ----------

public class Player {

	// ---------- Constants ----------

	// ---------- --------- ----------
	// ---------- Variables ----------
	private double height;
	private double vVelocity;
	// ---------- --------- ----------

	public Player() {
		height = 205;
		vVelocity = 0;
	}

	public double getHeight() {
		return height;
	}

	public double getvVelocity() {
		return vVelocity;
	}

	public void setHeight(final double height) {
		this.height = height;
	}

	public void setvVelocity(final double vVelocity) {
		this.vVelocity = vVelocity;
	}
}
