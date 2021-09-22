import java.io.File;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.media.AudioClip;
import java.util.ArrayList;
import java.util.Random;


public class Main extends Application  {
	//Application window width and height
	public static int WIDTH = 1600;
	public static int HEIGHT = 1000;
	private Group root;
	
	//keyboard booleans
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private boolean motion;
	
	
	//Adding player
	private Player player = new Player((WIDTH-300)/2,(HEIGHT-300)/2);
	
	//Adding enemy
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private int enemyIndex = 1;

	
	//Adding Walls
	private Wall wallLeft = new Wall(0,0,210,HEIGHT);
	private Wall wallRight = new Wall(WIDTH-210,0,210,HEIGHT);
	private Wall wallTop = new Wall(0,0,WIDTH,70);
	private Wall wallBottom = new Wall(0,HEIGHT,WIDTH,70);
	
	
	//Music/Sound Objects
	private Media song = new Media(this.getClass().getResource("Background.mp3").toExternalForm());
	private MediaPlayer backgroundMusic = new MediaPlayer(song);
	
	private AudioClip running = new AudioClip(this.getClass().getResource("running.wav").toExternalForm());
	
	//Adding score
	private int score;
	private int highScore = 0;
	private Text scoreDisplay = new Text(1010,130,"Score: "+score);
	private Text highScoreDisplay = new Text(1010,180,"High Score: "+score);
	
	//Random object
	private Random rand = new Random();
	
	public static void main(String[] args) {
		//launch GUI menu and start method
		launch(args);
	}
	
	public void run() {
		
		//adding background
		Image backgroundImage = new Image("Background.jpg",WIDTH,WIDTH,true,true);
		ImageView background = new ImageView();
		background.setImage(backgroundImage);
		add(background);
		
		//adding score & highscore
		scoreDisplay.setFont(Font.font("impact", 60));
		scoreDisplay.setFill(Color.ANTIQUEWHITE);
		add(scoreDisplay);
		
		highScoreDisplay.setFont(Font.font("impact", 30));
		highScoreDisplay.setFill(Color.ANTIQUEWHITE);
		add(highScoreDisplay);
		
		//adding walls to stage
		add(wallTop);
		add(wallBottom);
		add(wallLeft);
		add(wallRight);
		//add graphics and shapes here
		add(player);
		enemies.add(new Enemy(1200,750,6,6));
		add(enemies.get(0));
		
		//adding music
		backgroundMusic.setVolume(0.3);
		backgroundMusic.play();
		
		//creating animation
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		 //Animation frame rate
		timeline.setRate(60);
		
		root.setOnKeyPressed(keyPressed);
		root.setOnKeyReleased(keyReleased);
		
		
	}
	
	//Event Handlers
	//KEYBOARD
	EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>()
	{
		public void handle(KeyEvent event) {
			// TODO Auto-generated method stub
			if(event.getCode()==KeyCode.DOWN)
			{
				down = true;
				motion = true;
			}
			if(event.getCode()==KeyCode.UP)
			{
				up = true;
				motion = true;
			}
			if(event.getCode()==KeyCode.LEFT)
			{
				left = true;
				motion = true;
			}
			if(event.getCode()==KeyCode.RIGHT)
			{
				right = true;
				motion = true;
			}
			if(!running.isPlaying())
			{
				running.play();
			}
		}
		};
	
		EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>()
		{
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if(event.getCode()==KeyCode.DOWN)
				{
					down = false;
					motion = false;
				}
				if(event.getCode()==KeyCode.UP)
				{
					up = false;
					motion = false;
				}
				if(event.getCode()==KeyCode.LEFT)
				{
					left = false;
					motion = false;
				}
				if(event.getCode()==KeyCode.RIGHT)
				{
					right = false;
					motion = false;
				}
			}
			};
			

	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
		
		//Keeping score
		if(motion)
		{
			score++;
		}
		
		if(running.isPlaying() && Math.abs(player.xVelocity) < 1 && Math.abs(player.yVelocity) < 1)
		{
			running.stop();
		}
		


		
//----------------	PLAYER ----------------
		//Player Acceleration
		if(right && player.xVelocity<10)
		{
			player.xVelocity+=0.4;
		}

		if(left && player.xVelocity>-10)
		{
			player.xVelocity-=0.4;
		}
		if(up && player.yVelocity>-10)
		{
			player.yVelocity-=0.4;
		}
		if(down && player.yVelocity<10)
		{
			player.yVelocity+=0.4;
		}
		
		//Player Deceleration
		if(player.xVelocity>0&&!right)
		{
			player.xVelocity-=player.xAcceleration;
		}
		if(player.xVelocity<0&&!left)
		{
			player.xVelocity+=player.xAcceleration;
		}
		if(player.yVelocity>0&&!up)
		{
			player.yVelocity-=player.yAcceleration;
		}
		if(player.yVelocity<0&&!down)
		{
			player.yVelocity+=player.yAcceleration;
		}
		
		//Prevent character drifting due to the nature of utilizing doubles
		if(!motion && Math.abs(player.xVelocity) < 0.20) {
			player.xVelocity = 0;
		}
		if(!motion && Math.abs(player.yVelocity) < 0.20) {
			player.yVelocity = 0;
		}
		
		//Wall collision
		if(wallTop.getBounds().intersects(player.getBounds()))
		{
			player.yVelocity = 3;
		}
		if(wallBottom.getBounds().intersects(player.getBounds()))
		{
			player.yVelocity = -3;
		}
		if(wallLeft.getBounds().intersects(player.getBounds()))
		{
			player.xVelocity = 3;
		}
		if(wallRight.getBounds().intersects(player.getBounds()))
		{
			player.xVelocity = -3;
		}

		player.setX(player.x+player.xVelocity);
		player.setY(player.y+player.yVelocity);
		
		scoreDisplay.setText("Score: "+score);;
		
//---------------- ENEMY ----------------
		
		//updates enemy position & detects collision
		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i).update(player, wallTop, wallBottom, wallLeft, wallRight))
			{
				root.getChildren().removeAll(enemies);
				enemies.removeAll(enemies);
				enemyIndex = 0;
				
				enemies.add(new Enemy(300+rand.nextInt(900),200+rand.nextInt(550),randomizeSign()*(3+rand.nextInt(5)),randomizeSign()*(3+rand.nextInt(5))));
				add(enemies.get(enemyIndex));
				enemyIndex++;
				
				if(score > highScore)
				{
					highScore = score;
					
				}
				highScoreDisplay.setText("Highscore: "+highScore);
				score = 0;
			}
		}
		
		if(score>0 && score%200==0)
		{	
			//enemies.add(new Enemy(300+rand.nextInt(900),200+rand.nextInt(550),randomizeSign()*(3+rand.nextInt(5)),randomizeSign()*(3+rand.nextInt(5))));
			enemies.add(new Enemy(enemies.get(enemyIndex-1).x,enemies.get(enemyIndex-1).y,randomizeSign()*(3+rand.nextInt(5)),randomizeSign()*(3+rand.nextInt(5))));
			add(enemies.get(enemyIndex));
			enemyIndex++;
			score++;
		}
		
	}));
	
	
	
	public void add(Node node) {
		root.getChildren().add(node);
	}
	
	public void remove(Node node) {
		root.getChildren().remove(node);
	}
	
	public int randomizeSign()
	{
		return 1-(rand.nextInt(2)*2);
	}
	
	public void start(Stage stage) throws Exception {
		//When an application is launched, an initial stage is created and
		//passed to this method
		stage.setTitle("Running Man");
		//Creates a scene class that contains all content for a Scene
		//Scene is set to a root
		root = new Group();
		Scene scene = new Scene(root);
		//Scene is passed to the stage
		stage.setScene(scene);
		//Canvas object allows you to draw shapes and images
		Canvas canvas = new Canvas(WIDTH,HEIGHT);
		
		//root method to add canvas to Scene
		root.getChildren().add(canvas);
		stage.show();
	    root.requestFocus();
		
		//method to add custom elements to stage
		run();
	}
}