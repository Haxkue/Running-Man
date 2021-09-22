import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
abstract public class Entity extends Pane{
	public double x;
	public double y;
	public double xVelocity;
	public double yVelocity;
	public double xAcceleration;
	public double yAcceleration;
	protected abstract void setX(double d);
	protected abstract void setY(double d);
}
