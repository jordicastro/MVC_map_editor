import javax.swing.JFrame; // JFrame is part of the javax.swing namespace (FOLDER)
import java.awt.Toolkit;
// think of code as if it were a bunch of classes. objects are instantiation (new) of classes. 
// method: functions that are inside the class.
public class Game extends JFrame // Game(JFrame): all the Jframe methods are imported into Game. the JFrame class has window property functions
{
	Model model;
	Controller controller;
	View view;

	public Game() // constructor
	{
		// Instantiate the three main objects
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		

		// Set some window properties
		this.setTitle("Map Editor!");
		this.setSize(900, 900);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.addKeyListener(controller); // pay attention to the keyboard
	}

	public static final String[] Things = {
		"chair",
		"lamp",
		"mushroom",
		"outhouse",
		"pillar",
		"pond",
		"rock",
		"statue",
		"tree",
		"turtle",
	};

	public static void main(String[] args) // static means not in an object
	{
		Game g = new Game(); // g object is an instantiation of a Game class
		g.run(); 
	}
	
	public void run()
	{
		// Main loop
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for a brief moment
			try
			{
				Thread.sleep(25);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
