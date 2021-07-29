package flappy_bird.hitbox;

public class HitBox
{
	
	private final double xMin;
	private final double yMin;
	private final double xMax;
	private final double yMax;
	
	public HitBox(final double x, final double y, final double w, final double h)
	{
		this.xMin = x;
		this.yMin = y;
		this.xMax = x + w;
		this.yMax = y + h;
	}
	
	public boolean collision(final HitBox otherHitBox)
	{
		boolean xContained = false;
		boolean yContained = false;
		
		if ((xMin >= otherHitBox.xMin && xMin <= otherHitBox.xMax) || (xMax >= otherHitBox.xMin && xMax <= otherHitBox.xMax))
		{
			xContained = true;
		}
		if ((yMin >= otherHitBox.yMin && yMin <= otherHitBox.yMax) || (yMax >= otherHitBox.yMin && yMax <= otherHitBox.yMax))
		{
			yContained = true;
		}
		
		return xContained && yContained;
	}
	
}
