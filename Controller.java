import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.Math;

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
	}

	void setView(View v)
	{
		view = v;
	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println("Hey! I said never push that button! This incident will be reported! (j/k)");
		view.removeButton();
	}
	
	public void mousePressed(MouseEvent e)
	{
		int mouseX = e.getX();
		int mouseY = e.getY();
		model.setDestination(mouseX, mouseY);
		BufferedImage selectedImage = view.getDefaultImage();
		int kind = view.getSelectedImageIndex();

		// event: click is inside purple box
		if ( (mouseX >= 0 && mouseX <= 200) && (mouseY >= 0 && mouseY <=200))
		{
			view.updateSelectedImage();
		}
		else if (e.getButton() == 1) // event: left click anywhere outside the purple box
		{

			if (selectedImage != null)
			{
				Thing newThing = new Thing(mouseX, mouseY, kind);
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
				distance = Math.sqrt( ( Math.pow( (mouseX - thing.x), 2) + Math.pow( (mouseY - thing.y) ,2) ));

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
		view.repaint();
	}

	public void mouseReleased(MouseEvent e) 
	{	}
	
	public void mouseEntered(MouseEvent e) 
	{	}
	
	public void mouseExited(MouseEvent e) 
	{	}
	
	public void mouseClicked(MouseEvent e) 
	{	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: 
				keyRight = true; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = true; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = true; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = true; 
				break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: 
				keyRight = false; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = false; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = false; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = false; 
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
	{	}

	void update()
	{
		if(keyRight) 
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
}
