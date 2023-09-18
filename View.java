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
	BufferedImage images[];
	Model model;
	int currKindIndex;
	BufferedImage selectedImage; 
	static int scrollX, scrollY; // scroll values passed into controller to scroll based on keyboard input
	int time;

	View(Controller c, Model m)
	{
		// Make load and save buttons
		loadB = new JButton("Load");
		loadB.addActionListener(c);
		saveB = new JButton("Save");
		saveB.addActionListener(c);
		this.add(loadB);
		this.add(saveB);
		this.setFocusable(true); //required to detect Key Events in Controller.java!
		currKindIndex = 0;
		

		loadB.setFocusable(false); // these are FALSE
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

		// Green background
		g.setColor(new Color(64, 255, 128)); 
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// paige est ici!!!!!! paige est tres super!!!


		for (Thing thing: model.things) // loop through the arraylist and print the things to the screen
		{
			if (thing instanceof Jumper)  // if the thing is of child type "Jumper", update time
			{
				((Jumper) thing).updateTime(time); // Update time for each Jumper
			}
			int type = thing.getKind();
			BufferedImage thingImage = images[type];
			int thingW = thingImage.getWidth();
			int thingH = thingImage.getHeight();
			Point p = thing.getPos(time); // getPos of thing p. this will either run the getPos default function or the getPos overloaded JUMPER function (based on the whether it is a type THING or type JUMPER)
			g.drawImage(thingImage,p.x - thingW / 2 - scrollX, p.y - thingH / 2 - scrollY, null);

		}


		
		// selector, including the purple box and the current selection
			// purple box: scroll positions do not subtract, because we want the purple box to remain in the upper left corner of the screen at all times.
				// purple box is drawn last so it is at the top of the image. i.e., objects do not overlap the purple box.
		g.setColor(new Color (238,130,238));
		g.fillRect(0, 0, 200, 200);
		// selected image on top of the purple box
		BufferedImage selectedImage = images[currKindIndex];
		int selectedImageW = selectedImage.getWidth();
		int selectedImageH = selectedImage.getHeight();
		int selectedImageX = (200 - selectedImageW) / 2; // centered horizontally
		int selectedImageY = (200 - selectedImageH) / 2; // centered vertically
		g.drawImage(selectedImage, selectedImageX, selectedImageY, null); // the selected image in the purple box.

		// instructions for graders
		g.setColor(Color.MAGENTA);
		g.drawString("Scroll functionality with WASD and Arrow Keys!", 300, 60);
		
	}
	
	void removeButton()
	{
		//this.remove(this.b1);
		//this.repaint();
	}

	void updateSelectedImage() // iterates through the images in chronological order: 0, 1, 2,..., 9, 0, 1, 2...
	{
		currKindIndex++;
		if (currKindIndex >= images.length) //resets to zero after 9 (the largest index)
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