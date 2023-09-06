import java.util.ArrayList;
import java.util.Random;

class Model
{
	int dest_x;
	int dest_y;
	int turtle_x;
	int turtle_y;
	Random random = new Random();

	static int speed = 10;
	ArrayList<Thing> things;

	Model()
	{
		this.things = new ArrayList<Thing>();
		this.dest_x = 0;
		this.dest_y = 0;

		// Add Thing object to things ArrayList
			/* 
			this.things.add(new Thing(dest_x, dest_y, 0)); // clicker destination
			this.things.add(new Thing(300, 300, 1)); // chair location
			this.things.add(new Thing(500, 500, 2)); // lamp 
			this.things.add(new Thing(500, 800, 3)); // lettuce
			this.things.add(new Thing(500, 800, 4)); // mushroom
			this.things.add(new Thing(500, 800, 5)); // outhouse
			this.things.add(new Thing(500, 800, 6)); // pillar
			this.things.add(new Thing(500, 800, 7)); // pond
			this.things.add(new Thing(500, 800, 8)); // robot
			this.things.add(new Thing(500, 800, 9)); // rock
			this.things.add(new Thing(500, 800, 10)); // statue
			this.things.add(new Thing(500, 800, 11)); // tree
			*/

		// for (int i = 0; i < 10; i++) // random thing placement
		// {
		// 	int randX = random.nextInt(900) + 200; // 200 -> 989
		// 	int randY = random.nextInt(900) + 200;
		// 	this.things.add(new Thing(randX, randY, i));
		// }


	}

	public void update()
	{
		// if(this.turtle_x < this.dest_x)
        //     this.turtle_x += Math.min(speed, dest_x - turtle_x);
		// else if(this.turtle_x > this.dest_x)
        //     this.turtle_x -= Math.max(speed, dest_x - turtle_x);
		// if(this.turtle_y < this.dest_y)
        //     this.turtle_y += Math.min(speed, dest_y - turtle_y);
		// else if(this.turtle_y > this.dest_y)
        //     this.turtle_y -= Math.max(speed, dest_y - turtle_y);
	}

    public void reset()
    {
        turtle_x = 200;
        turtle_y = 200;
        dest_x = turtle_x;
        dest_y = turtle_y;
    }

	public void setDestination(int x, int y)
	{
		this.dest_x = x;
		this.dest_y = y;
	}

	public void addThing(int x, int y, int kind)
	{
		things.add(new Thing(x, y, kind));
	}
	public void removeThing(int x, int y)
	{
		for (Thing thing : things)
		{
			if (thing.x == x && thing.y == y)
			{
				things.remove(thing);
			} 
		}
	}


}
class Thing
{
	public int x;
	public int y;
	public int kind;

	Thing(int x, int y, int kind)
	{
		this.x = x;
		this.y = y;
		this.kind = kind;
	}
}
