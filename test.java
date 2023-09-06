// Random pero esto es lo que me preguntaron para la entrevista técnica de jb hunt:

class Main {
  public static void main(String[] args) {
    
    //Task: "Create a string array and make 4 string variables: cats, dogs, horse, and donkey"
    String[] animals = {"cats", "dogs", "horse", "donkey"};
  
    //Task: "Create a loop that only prints the variables that end in the character 's' "
    for (int i = 0; i < animals.length; i++)
    {
        if (animals[i].charAt(animals[i].length()-1) == 's')
        {
            System.out.println(animals[i]);
        }
    }
    
    
    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    
    //Task: "Reverse the contents of the array"
    String[] temp = new String[4];
    int j = 0;
    for (int i = animals.length-1; i>=0; i--)
    {
        temp[j] = animals[i];
        j++;
    }
    
    //Task: "Print out original array vs new reversed array"
    
    //original
    for (int i = 0; i < animals.length; i++)
    {
        System.out.println(animals[i]);
    }
    
    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    
    //reversed
    for (int i = 0; i < temp.length; i++)
    {
        System.out.println(temp[i]);
    }
    
  }
}