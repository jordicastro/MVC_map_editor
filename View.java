import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;
import java.util.Random;

class View extends JPanel
{
	JButton loadB;
	JButton saveB;
	BufferedImage turtle_image;
	BufferedImage images[];
	Model model;
	int randImageIndex;
	BufferedImage selectedImage; 
	int scrollX, scrollY;

	View(Controller c, Model m)
	{
		// Make a button

		loadB = new JButton("Load");
		loadB.addActionListener(c);
		saveB = new JButton("Save");
		saveB.addActionListener(c);
		this.add(loadB);
		this.add(saveB);

		// Link up to other objects
		c.setView(this);
		model = m;

		// Send mouse events to the controller
		this.addMouseListener(c);
		this.addMouseMotionListener(c);

		// Load the images from the Game.Things array
		images = new BufferedImage[Game.Things.length];
		for (int i = 0; i < images.length; i++)
		{
			String imageName = "images/" + Game.Things[i] + ".png";
			
			images[i] = loadImages(imageName);
		}

		try
		{
			this.turtle_image = ImageIO.read(new File("images/turtle.png"));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	private BufferedImage loadImages(String imageName)
	{
		try
		{
			return ImageIO.read(new File(imageName));
		} catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
			return null;
		}
	}

	public void paintComponent(Graphics g)
	{
		// Clear the background
		g.setColor(new Color(64, 255, 128));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// Draw the image so that its bottom center is at (x,y)
		int w = this.turtle_image.getWidth();
		int h = this.turtle_image.getHeight();
		g.drawImage(this.turtle_image, model.turtle_x - w / 2, model.turtle_y - h, null);

		// Selector, including the purple box and the current selection
			// purple box
		g.setColor(new Color (238,130,238));
		g.fillRect(0, 0, 200, 200);

		BufferedImage selectedImage = images[randImageIndex];
		int selectedImageW = selectedImage.getWidth();
		int selectedImageH = selectedImage.getHeight();
		int selectedImageX = (200 - selectedImageW) / 2; // Centered horizontally
		int selectedImageY = (200 - selectedImageH) / 2; // Centered vertically
		g.drawImage(selectedImage, selectedImageX, selectedImageY, null);


		for (Thing thing: model.things) // loop through the arraylist and print the things to the screen
		{
			int type = thing.kind;
			BufferedImage thingImage = images[type];
			int thingW = thingImage.getWidth();
			int thingH = thingImage.getHeight();
			g.drawImage(thingImage,thing.x - thingW / 2, thing.y - thingH / 2, null);

		}
	}
	
	void removeButton()
	{
		//this.remove(this.b1);
		//this.repaint();
	}

	void updateSelectedImage()
	{
		Random random = new Random();
		randImageIndex = random.nextInt(images.length);
		selectedImage = images[randImageIndex];
	}
	public BufferedImage getSelectedImage()
	{
		return selectedImage;
	}
	public int getSelectedImageIndex()
	{
		return randImageIndex;
	}
	
	public BufferedImage getDefaultImage()
	{
		return images[0];
	}
}
