import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
	JButton loadB;
	JButton saveB;
	BufferedImage turtle_image;
	BufferedImage images[];
	Model model;
	int randImageIndex;
	int currKindIndex;
	BufferedImage selectedImage; 
	static int scrollX, scrollY; // needed to expand the view.
	int time;

	View(Controller c, Model m)
	{
		// Make a button

		loadB = new JButton("Load");
		loadB.addActionListener(c);
		saveB = new JButton("Save");
		saveB.addActionListener(c);
		this.add(loadB);
		this.add(saveB);
		this.setFocusable(true); //required to detect Key Events in Controller.java!
		currKindIndex = 0;
		

		loadB.setFocusable(false);
		saveB.setFocusable(false);
		// Link up to other objects
		c.setView(this);
		model = m;

		// Send mouse events to the controller
		this.addMouseListener(c);
		this.addMouseMotionListener(c);
		this.addKeyListener(c);

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

		time++; // counts the number of times the update method has been called
		//Jumper.updateTime(time); 
		// Clear the background
		g.setColor(new Color(64, 255, 128)); // green background.
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// Draw the image so that its bottom center is at (x,y)
		int w = this.turtle_image.getWidth();
		int h = this.turtle_image.getHeight();
		g.drawImage(this.turtle_image, model.turtle_x - w / 2, model.turtle_y - h, null);


		

		// paige est ici!!!!!! paige est tres super!!!


		for (Thing thing: model.things) // loop through the arraylist and print the things to the screen
		{
			if (thing instanceof Jumper) 
			{
				((Jumper) thing).updateTime(time); // Update time for each Jumper
			}
			int type = thing.getKind();
			BufferedImage thingImage = images[type];
			int thingW = thingImage.getWidth();
			int thingH = thingImage.getHeight();
			Point p = thing.getPos(time);
			g.drawImage(thingImage,p.x - thingW / 2 - scrollX, p.y - thingH / 2 - scrollY, null);

		}
		// purple box is drawn last so it is at the top of the image. i.e., objects do not overlap the purple box.

		// Selector, including the purple box and the current selection
			// purple box: scroll positions do not subtract, because we want the purple box to remain in the upper left corner of the screen at all times.
		g.setColor(new Color (238,130,238));
		g.fillRect(0, 0, 200, 200);

		BufferedImage selectedImage = images[currKindIndex];
		int selectedImageW = selectedImage.getWidth();
		int selectedImageH = selectedImage.getHeight();
		int selectedImageX = (200 - selectedImageW) / 2; // Centered horizontally
		int selectedImageY = (200 - selectedImageH) / 2; // Centered vertically
		g.drawImage(selectedImage, selectedImageX, selectedImageY, null); // the selected image in the purple box.

		
	}
	
	void removeButton()
	{
		//this.remove(this.b1);
		//this.repaint();
	}

	void updateSelectedImage()
	{
		currKindIndex++;
		if (currKindIndex >= images.length)
			currKindIndex =0;
		selectedImage = images[currKindIndex];
	}
	public BufferedImage getSelectedImage()
	{
		return selectedImage;
	}
	public int getSelectedImageIndex()
	{
		return currKindIndex;
	}
	
	public BufferedImage getDefaultImage()
	{
		return images[0];
	}

	public int getScrollX()
	{
		return scrollX;
	}

	public int getScrollY()
	{
		return scrollY;
	}
	
	public int getTime()
	{
		return time;
	}
}
