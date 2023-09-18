import java.util.ArrayList;
import java.util.Random;
// import java.util.List;
import java.awt.Point;
import java.nio.file.Files;
import java.nio.file.Paths;
// import org.json.JSONArray; used if JSON library is installed. similarly, an alternate version of loadMapFromJSON() is used.
// import org.json.JSONObject;

class Model
{
	// View view; // REFERENCE to original view: scrollX, scrollY, and time values will be updated as the view progresses (I think)
	int dest_x;
	int dest_y;
	int turtle_x;
	int turtle_y;
	Random random = new Random();

	static int speed = 10;
	ArrayList<Thing> things;

	Model() // <------------- pass in view to use scrollX, scrollY, and time (time is used in jumper class)
	{
		this.things = new ArrayList<Thing>();
		this.dest_x = 0;
		this.dest_y = 0;



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
			thingJson.add("kind", Integer.toString(t.getKind())); // integer to string so that loadMap from JSON is able to run
			thingJson.add("x", Integer.toString(t.getX())); // more over, Json.getString() gets string kind, x, and y.
			thingJson.add("y", Integer.toString(t.getY()));

			list_of_things.add(thingJson);
		}
		map.add("things", list_of_things);
		return map;
	}
	public void loadMapFromJSON(String filePath) // LOAD map / UNMARSHALL
	{	
		try
		{
			//int scrollX = view.getScrollX();
			//int scrollY = view.getScrollY();
			// read the contents of the JSON file into one big String
			String jsonString = new String(Files.readAllBytes(Paths.get(filePath))); 

			// parse the String as a JSON object -> JSON DOM
			Json loadedJson = Json.parse(jsonString);
			// access the "things" <array> field of the parsed Json object
			Json thingsArray = loadedJson.get("things"); 

			// clear things list in JSON
			things.clear(); // basically clearing the map and adding the loaded map in the for loop

			//for loop to iterate through each thing in the things section of the JSON object, GETTING / extracting kind, x, and y attributes.
			for (int i = 0; i < thingsArray.size(); i++) // UNMARSHALL DOM into ArrayList of things
			{
				Json thingJson = thingsArray.get(i);
				int kind = Integer.parseInt(thingJson.getString("kind"));
				int x = Integer.parseInt(thingJson.getString("x")); // adjust for scrollX and scrollY : - view.getScrollX()
				int y = Integer.parseInt(thingJson.getString("y")); // - view.getScrollY();

				
				System.out.println("Loaded Thing - Kind: " + kind + ", X: " + x + ", Y: " + y);
				// polymorphism: is it a JUMPER or a thing (turtle or not)? This call will find out:
				Thing newThing = Thing.createThing(x, y, kind);
				
				things.add(newThing);
			}
			//reset scrollX and scrollY to 0 after loading the map to make sure map is centered
			// View.scrollX = 0;
			// View.scrollY = 0;
			System.out.println("loading the map!");

			for (int i = 0; i < things.size(); i++)
			{
				System.out.println("KIND: " + things.get(i).getKind());
				System.out.println("X: " + things.get(i).getX());
				System.out.println("Y: " + things.get(i).getY());
				System.out.println("\n");
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
	protected int x; // 'protected' KEYWORD, like 'private', prevents other functions from accessing without a getter function, BUT children CAN access these protected attributes.
	protected int y;
	protected int kind;
	Thing() // default constructor
	{

	}

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

	public Point getPos(int t)
	{
		return new Point(this.x, this.y);
	}

	public static Thing createThing(int x, int y, int kind)
	{
		if (9 == kind)
		{
			return new Jumper(x, y, kind);
			
		}
		else
		{
			return new Thing(x, y, kind);
		}
	}

}

class Jumper extends Thing
{
	private int time; // time

	Jumper(int x, int y, int kind)
	{
		this.x = x;
		this.y = y;
		this.kind = kind;
		this.time = 0;
		
		
	}
	
	public void updateTime(int time)
	{
		this.time = time;
	}

	

	@Override
	public Point getPos(int t)
	{
		// t = view.getTime();
		return new Point(this.x, this.y - (int)Math.max(0., 50 * Math.sin(((double)this.time) / 5)));
	}

}
