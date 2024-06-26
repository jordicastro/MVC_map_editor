import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;
import java.nio.file.Files;
import java.nio.file.Paths;
// import org.json.JSONArray; used if JSON library is installed. similarly, an alternate version of loadMapFromJSON() is used.
// import org.json.JSONObject;

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



	}

	public void update() // could be used to update objects motions towards clicks //if(this.turtle_x < this.dest_x) //this.turtle_x += Math.min(speed, dest_x - turtle_x);
	{

	}

    public void reset() // reset the values of objects / destinations in motion  bturtle_x = 200; turtle_y = 200; dest_x = turtle_x; dest_y = turtle_y;
    {
		
    }

	public void setDestination(int x, int y) //this.dest_x = x; this.dest_y = y;
	{

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

	public Json marshall() // marshalls every monument (thing) in things ArrayList and takes note of its attributes (x,y,kind) and stores it neatly in a DOM JSON file
	{ 
		// called in onSaveButtonClick() in View.java!
		Json map = Json.newObject();
		Json list_of_things = Json.newList(); // a list of type JSON

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
			// read the contents of the JSON file into one big String
			String jsonString = new String(Files.readAllBytes(Paths.get(filePath))); 

			// parse the String as a JSON object -> JSON DOM
			Json loadedJson = Json.parse(jsonString);
			// access the "things" <array> field of the parsed Json object
			Json thingsArray = loadedJson.get("things"); // LOOK AT THE "things" FIELD OF THE LOADED JSON DOM!
			/*
			 * { "things": <-- lookin at him
			 * 		[ {"kind": 0, "x": 100, "y": 100}, {...}, ...] <-- extract contents of 'things' field.
			 * }
			 * 
			 */
			// clear things list in JSON
			things.clear(); // basically clearing the map and adding the loaded map in the for loop

			//for loop to iterate through each thing in the things section of the JSON object, GETTING / extracting kind, x, and y attributes.
			for (int i = 0; i < thingsArray.size(); i++) // UNMARSHALL DOM into ArrayList of things
			{
				Json thingJson = thingsArray.get(i); // one custom Json thing at a time
				int kind = Integer.parseInt(thingJson.getString("kind")); // getString is a custom Json method that gets the String in the "thing" -> "kind" subfield. there is no getInt, so Integer.parseInt is required to parse the string into an int.
				int x = Integer.parseInt(thingJson.getString("x")); 
				int y = Integer.parseInt(thingJson.getString("y")); 

					// Debug Statment:
						// System.out.println("Loaded Thing - Kind: " + kind + ", X: " + x + ", Y: " + y);
				//  polymorphism: is it a JUMPER or a thing (turtle or not)? This call will find out:
				Thing newThing = Thing.createThing(x, y, kind);
				
				things.add(newThing);
			}
			//reset scrollX and scrollY to 0 after loading the map to make sure map is centered upon reloading
			View.scrollX = 0;
			View.scrollY = 0;
			System.out.println("loading the map!");
			// could add more debug for loop things.get(i).getKind(), getX, getY
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

	public Point getPos(int t) // DEFAULT getPos method
	{
		return new Point(this.x, this.y);
	}

	public void update() // default update behavior for Thing (does nothing)
	{
		
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

	Jumper(int x, int y, int kind)
	{
		super(x, y, kind);
		
		
	}
	
	// time inherited from Thing
	@Override
	public Point getPos(int t) // OVERRIDED default getPos() Thing method - JUMPS
	{
		return new Point(this.x, this.y - (int)Math.max(0., 50 * Math.sin(((double)t) / 5)));
	}

}
