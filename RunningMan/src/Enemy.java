
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import java.io.File;

public class Enemy extends Pane{
	//Enemy sound effects
	private AudioClip bounce = new AudioClip(this.getClass().getResource("bounce.wav").toExternalForm());
	private AudioClip squish = new AudioClip(this.getClass().getResource("squish.mp3").toExternalForm());
	private Image enemyImage;
	private ImageView enemy = new ImageView();
	public static double DEFAULT_SPEED = 7;
	public double x;
	public double y;
	public double xVelocity = DEFAULT_SPEED;
	public double yVelocity = DEFAULT_SPEED;
	
	public Enemy(double x, double y, double xVelocity, double yVelocity)
	{
		enemyImage = new Image("Enemy.gif",150,250,true,true);
		enemy.setImage(enemyImage);
		this.getChildren().add(enemy);
		setX(x);
		setY(y);
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		bounce.setVolume(0.4);
	}
	
	public void setX(double x){
		this.x = x;
		enemy.setX(x);
	}	
	public void setY(double y){
		this.y = y;
		enemy.setY(y);
	}
	
	//to get the relative bounds of object
	public Bounds getBounds() {
	Bounds bound = new BoundingBox(x, y, enemyImage.getWidth()-50, enemyImage.getHeight());
	return bound;
	}
	
	public Bounds hitBox()
	{
		Bounds bound = new BoundingBox(x, y, enemyImage.getWidth()-130, enemyImage.getHeight()-130);
		return bound;
	}
	
	//method to update enemy's position and detect collision
	public boolean update(Player player,Wall wallTop, Wall wallBottom, Wall wallLeft, Wall wallRight)
	{
		setX(x+xVelocity);
		setY(y+yVelocity);
		if(wallTop.getBoundsForEnemy().intersects(getBounds())||wallBottom.getBoundsForEnemy().intersects(getBounds()))
		{
			yVelocity*=-1;
			bounce.play();
		}
		if(wallLeft.getBoundsForEnemy().intersects(getBounds())||wallRight.getBoundsForEnemy().intersects(getBounds()))
		{
			xVelocity*=-1;
			bounce.play();
		}
		
		if(player.hurtBox().intersects(hitBox()))
		{
			squish.play();
			return true;
		}
		return false;
	}
}