import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.io.IOException;
import java.io.FileWriter;

class Controller implements ActionListener, MouseListener, KeyListener, MouseMotionListener
{
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;

	Controller(Model m)
	{
		model = m;
		view = new View(this, model);
		view.addKeyListener(this);
		System.out.println(view);
	}

	void setView(View v)
	{
		System.out.println("view set");
		view = v;
		System.out.println(view);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == view.loadB) // checks if the load button was clicked.
		{
			//load map: load the previously saved map from the JSON DOM - map.json.
			model.loadMapFromJSON("map.json");
			
		}
		else if (e.getSource() == view.saveB) // checks if the save button was clicked.
		{
			// save map - call the marshall class to store the exact x, y positions and kinds of every 'thing' in the map.
			onSaveButtonClick();
		}

		System.out.println("inside action performed" + e);

	}
	
	public void mousePressed(MouseEvent e)
	{
		int mouseX = e.getX() + View.scrollX; // offset the scroll to calibrate after scroll movement.;
		int mouseY = e.getY() + View.scrollY;

		System.out.println("inside mouse pressed" + e);

		model.setDestination(mouseX, mouseY);
		BufferedImage selectedImage = view.getDefaultImage();
		int kind = view.getSelectedImageIndex();

		// event: click is inside purple box
		if ( (mouseX >= View.scrollX && mouseX <= View.scrollX + 200) && (mouseY >= View.scrollY && mouseY <= View.scrollY + 200))
		{
			view.updateSelectedImage();
		}
		else if (e.getButton() == 1) // event: left click anywhere outside the purple box
		{

			if (selectedImage != null)
			{
				Thing newThing = Thing.createThing(mouseX, mouseY, kind);
				model.things.add(newThing);
			}

		}
		else if (e.getButton() == 3) // event: right button is pressed, remove closest object to click coordinates in Things ArrayList
		{
			double minDistance = Double.MAX_VALUE; // maxes value a double can store: guaranteed to be larger than any thing's distance in the Things ArrayList
			int closestIndex = -1;
			double distance = -1;

			for(int i = 0; i < model.things.size(); i++)
			{
				Thing thing = model.things.get(i);
				distance = Math.sqrt( ( Math.pow( (mouseX - thing.getX()), 2) + Math.pow( (mouseY - thing.getY()) ,2) ));

				if (distance < minDistance)
				{
					minDistance = distance;
					closestIndex = i;
				}
			}

			if (closestIndex != -1)
			{
				model.things.remove(closestIndex);
			}
		}
		// view.repaint();
	}

	public void mouseReleased(MouseEvent e) 
	{	}
	
	public void mouseEntered(MouseEvent e) 
	{	}
	
	public void mouseExited(MouseEvent e) 
	{	}
	
	public void mouseClicked(MouseEvent e) 
	{	}
	
	public void keyPressed(KeyEvent e) // adjusting scrollX and scrollY based on if arrow keys or 'asdf' keys are pressed.
	{

		System.out.println("key pressed event\n");
		switch(e.getKeyCode())
		{
	
			case KeyEvent.VK_UP: 
			case KeyEvent.VK_W: 
				keyUp = true; 
				System.out.println("up arrow/w has been pressed... adjusting scrollY: " + View.scrollY);
				View.scrollY -= Model.speed;
				System.out.println("scrollY has been adjusted to scrollY:" + View.scrollY);
				break;

			case KeyEvent.VK_LEFT: 
			case KeyEvent.VK_A: 
				keyLeft = true; 
				View.scrollX -= Model.speed;
				break;

			case KeyEvent.VK_DOWN: 
			case KeyEvent.VK_S:
				keyDown = true; 
				View.scrollY += Model.speed;
				break;

			case KeyEvent.VK_RIGHT: // Arrow key functionality.
			case KeyEvent.VK_D:    // ASDF functionality
				keyRight = true; 
				View.scrollX += Model.speed;
				break;
		}
	}

	public void keyReleased(KeyEvent e) // reset the values of scrollX and scrollY when stop moving (button is released).
	{
		switch(e.getKeyCode())
		{

			case KeyEvent.VK_UP: 
			case KeyEvent.VK_W:
				System.out.print("Up or W has been UNPRESSED. updating scollY from " + View.scrollY);
				keyUp = false; 
				//View.scrollY = 0;
				System.out.println(" to " + View.scrollY);
				break;

			case KeyEvent.VK_LEFT: 
			case KeyEvent.VK_A:
				keyLeft = false; 
				//View.scrollX = 0;
				break;

			case KeyEvent.VK_DOWN: 
			case KeyEvent.VK_S:
				keyDown = false; 
				//View.scrollY = 0;
				break;

			case KeyEvent.VK_RIGHT: 
			case KeyEvent.VK_D:
				keyRight = false; 
				//View.scrollX = 0;
				break;

			case KeyEvent.VK_ESCAPE:
				System.exit(0);
		}
		char c = Character.toLowerCase(e.getKeyChar());
		if(c == 'q')
			System.exit(0);
        if(c == 'r')
            model.reset();
	}

	public void keyTyped(KeyEvent e)
	{

	}

	void update()
	{
		if(keyRight) {
            model.dest_x += Model.speed;
			System.out.println("key right pressed");
		}
		if(keyLeft) 
    		model.dest_x -= Model.speed;
		if(keyDown) 
            model.dest_y += Model.speed;
		if(keyUp)
            model.dest_y -= Model.speed;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("THE MOUSE MOVED ! ! !");
	}

	/// PA 2:

		// handle save button
	public void onSaveButtonClick()
	{
		try
		{
			FileWriter writer = new FileWriter("map.json");
			writer.write(this.model.marshall().toString());
			writer.close();
			System.out.println("Model saved to map.json");
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
