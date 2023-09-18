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
		// debug
			//System.out.println(view);
	}

	void setView(View v)
	{
		view = v;
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == view.loadB) // checks if the load button was clicked.
		{
			//load map: calls loadMapFromJSON to load the previously saved map from the JSON DOM - map.json.
			model.loadMapFromJSON("map.json");
			
		}
		else if (e.getSource() == view.saveB) // checks if the save button was clicked.
		{
			// save map: call the marshall class to store the exact x, y positions and kinds of every 'thing' in the map.
			onSaveButtonClick(); // method in THIS file ~line 230
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		int mouseX = e.getX() + View.scrollX; // offset the scroll to calibrate after scroll movement.;
		int mouseY = e.getY() + View.scrollY; // CAPITAL 'V' View is used here to refer to the STATIC scroll values (meaning the values are identical no matter the instance of View objects)

		model.setDestination(mouseX, mouseY); // setting the destination coordinates for the object based on the mouse click + adjusted scroll offset! (marking an X on the map where the object will soon go)
		BufferedImage selectedImage = view.getDefaultImage();
		int kind = view.getSelectedImageIndex();

		if ( (mouseX >= View.scrollX && mouseX <= View.scrollX + 200) && (mouseY >= View.scrollY && mouseY <= View.scrollY + 200)) // event: click is inside purple box
		{
			view.updateSelectedImage();
		}
		else if (e.getButton() == 1) // event: left click anywhere outside the purple box
		{
			if (selectedImage != null)
			{
				Thing newThing = Thing.createThing(mouseX, mouseY, kind); // createThing DETERMINES whether the object is type jumper or not and calls the corresponding default or overloaded function accordingly

				model.things.add(newThing); // append the things ArrayList with the new obj with adjusted x, y, and kind attributes
			}

		}
		else if (e.getButton() == 3) // event: right button is pressed, remove closest object to click coordinates in Things ArrayList
		{
			double minDistance = Double.MAX_VALUE; // maxes value a double can store: guaranteed to be larger than any thing's distance in the Things ArrayList
			int closestIndex = -1;
			double distance = -1;

			for(int i = 0; i < model.things.size(); i++) // does the distance formula on every obj in things AL to find and delete obj with shortest path to RIGHT CLICK
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

		switch(e.getKeyCode()) // add print statements throughout here to debug
		{
	
			case KeyEvent.VK_UP: // Arrow key functionality.
			case KeyEvent.VK_W:  // ASDF functionality
				keyUp = true; 
				View.scrollY -= Model.speed;
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

			case KeyEvent.VK_RIGHT: 
			case KeyEvent.VK_D:
				keyRight = true; 
				View.scrollX += Model.speed;
				break;
		}
	}

	public void keyReleased(KeyEvent e) 
	{
		switch(e.getKeyCode()) // more debug statements, if necessary
		{

			case KeyEvent.VK_UP: 
			case KeyEvent.VK_W:
				keyUp = false; 
				break;

			case KeyEvent.VK_LEFT: 
			case KeyEvent.VK_A:
				keyLeft = false; 
				break;

			case KeyEvent.VK_DOWN: 
			case KeyEvent.VK_S:
				keyDown = false; 
				break;

			case KeyEvent.VK_RIGHT: 
			case KeyEvent.VK_D:
				keyRight = false; 
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
		if(keyRight) // more debugging statements possible
            model.dest_x += Model.speed;
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
	
	public void onSaveButtonClick() // handle save button click
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