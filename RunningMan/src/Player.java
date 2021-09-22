import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
public class Player extends Pane{
	
	//image view character
	Image playerImage;
	private ImageView player = new ImageView();
	public static double DEFAULT_SPEED = 0;
	public double x;
	public double y;
	public double xVelocity = DEFAULT_SPEED;
	public double yVelocity = DEFAULT_SPEED;
	public double xAcceleration = 0.19;
	public double yAcceleration = 0.19;
	
	public Player(double x,double y)
	{
		playerImage = new Image("Player.gif",300,300,true,true);
		player.setImage(playerImage);
		this.getChildren().add(player);
		setX(x);
		setY(y);
	}
	
	public void setX(double x){
		this.x = x;
		player.setX(x);
	}
	
	public void setY(double y){
		this.y = y;
		player.setY(y);
	}
	
	//to get the relative bounds of object
	public Bounds getBounds() {
	Bounds bound =  new BoundingBox(x,  y, playerImage.getWidth()-90, playerImage.getHeight()-50);
	return bound; 
	}
	
	public Bounds hurtBox()
	{
		Bounds bound =  new BoundingBox(x,  y, playerImage.getWidth()-170, playerImage.getHeight()-140);
		return bound;
	}

}
