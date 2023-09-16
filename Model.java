import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

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
			if (thing.getX() == x && thing.getY() == y)
			{
				things.remove(thing);
			} 
		}
	}

	public Json marshall() // marshalls the every monument (thing) in things ArrayList and takes note of its attributes (x,y,kind) and stores it neatly in a DOM JSON file
	{
		Json map = Json.newObject();
		Json list_of_things = Json.newList();

		// Loop through the list of things -> add them to JSON array
		for (Thing t : this.things)
		{
			Json thingJson = Json.newObject();
			thingJson.add("kind", t.getKind());
			thingJson.add("x", t.getX());
			thingJson.add("y", t.getY());

			list_of_things.add(thingJson);
		}
		map.add("things", list_of_things);
		return map;
	}
	public void loadMapFromJSON(String filePath) // LOAD map
	{	
		try
		{
			// read the contents of the JSON file using readAllBytes and .get(filePath)
			// parse the String as a JSON object
			String jsonString = new String(Files.readAllBytes(Paths.get(filePath))); 
			JSONObject json = new JSONObject(jsonString);
			JSONArray thingsArray = json.getJSONArray("things"); // GET things array from JSON and clear things list.

			// clear things list in JSON
			things.clear(); // basically clearing the map and adding the loaded map in the for loop

			//for loop to iterate through each thing in the things section of the JSON object, GETTING / extracting kind, x, and y attributes.
			for (int i = 0; i < thingsArray.length(); i++)
			{
				JSONObject thingJson = thingsArray.getJSONObject(i);
				int kind = thingJson.getInt("kind");
				int x = thingJson.getInt("x");
				int y = thingJson.getInt("y");

				things.add(new Thing(x, y, kind));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

class Thing
{
	public int x;
	public int y;
	public int kind;

	Thing(int x, int y, int kind) // overloaded constructor
	{
		this.x = x;
		this.y = y;
		this.kind = kind;
	}

	//getters
	public int getKind()
	{
		return kind;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

}
