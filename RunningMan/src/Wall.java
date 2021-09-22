import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
public class Wall extends Pane{
	public Rectangle wallPiece;
	public double x;
	public double y;
	public double width;
	public double height;
	
	public Wall(double x, double y,double width, double height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		wallPiece = new Rectangle(x,y,width,height);
		wallPiece.setFill(Color.TRANSPARENT);
		this.getChildren().add(wallPiece);
	}
	
	public Bounds getBounds() {
	Bounds bound =  new BoundingBox(x,  y, wallPiece.getWidth()-60, wallPiece.getHeight()-60);
	return bound; 
	}
	
	public Bounds getBoundsForEnemy() {
	Bounds bound =  new BoundingBox(x,  y, wallPiece.getWidth()-50, wallPiece.getHeight()+20);
	return bound; 
	}
}
