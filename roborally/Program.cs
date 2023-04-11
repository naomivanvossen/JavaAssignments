using System; 
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

public enum Facing{
        North,
        East,
        South,
        West
}

class Robot{
    private int xPosition = 1;
    private int yPosition = 1;
    public Facing direction;
    public Boolean canIRun;
    List<string> commands; 

    public Robot(int newXPosition, int newYPosition, string newDirection){
        xPosition = newXPosition;
        yPosition = newYPosition;
        direction = ( Facing ) Enum.Parse( typeof( Facing ), newDirection );
        canIRun = false;
        commands = new List<string>();
    }

    int getXPosition(){
        return this.xPosition;
    }

    int getYPosition(){
        return this.yPosition;
    }

    Facing getDirection(){
        return this.direction;
    }

    Boolean getCanIRun(){
        return this.canIRun;
    }

    void addToCommands(string myFunction){
        this.commands.Add(myFunction);
    }

    public void turnRight(){
        if(canIRun){
            turnRightExecute();
        }
        else{
            addToCommands("turnRight");
        }
    }

    void turnRightExecute(){
        if( (Array.IndexOf(Enum.GetValues(this.direction.GetType()), this.direction)) < Enum.GetNames(typeof(Facing)).Length -1 ){
            this.direction = (Facing)(Array.IndexOf(Enum.GetValues(this.direction.GetType()), this.direction) + 1);
        }
        else{
            this.direction = (Facing)(0);
        }
    }

    public void turnLeft(){
        if(canIRun){
            turnLeftExecute();
        }
        else{
            addToCommands("turnLeft");
        }
    }

    public void turnLeftExecute(){
        if( (Array.IndexOf(Enum.GetValues(this.direction.GetType()), this.direction)) > 0 ){
            this.direction = (Facing)(Array.IndexOf(Enum.GetValues(this.direction.GetType()), this.direction) -1 );
        }
        else{
            this.direction = (Facing)(Enum.GetNames(typeof(Facing)).Length -1);
        }
    }

    public void goForward(){
        if(canIRun){
            goForwardExecute();
        }
        else{
            addToCommands("goForward");
        }
    }

    public void goForwardExecute(){
        switch (this.direction){
        case Facing.North:
            this.yPosition ++; 
            break;
        case Facing.East:
            this.xPosition ++;
            break;
        case Facing.South:
            this.yPosition --;
            break;
        case Facing.West:
            this.xPosition --;
            break;
        }
    }

     public void goForward(int speed){
        if(canIRun){
            goForwardExecute(speed);
        }
        else{
            addToCommands(speed.ToString());
        }
    }

    public void goForwardExecute(int speed){
        if(speed > 0){
            this.goForwardExecute();
            goForwardExecute(-- speed);
        }
    }

    public void goBackwards(){
        if(canIRun){
            goBackwardsExecute();
        }
        else{
            addToCommands("goBackwards");
        }
    }

    public void goBackwardsExecute(){
        switch (this.direction){
        case Facing.North:
            this.yPosition --; 
            break;
        case Facing.East:
            this.xPosition --;
            break;
        case Facing.South:
            this.yPosition ++;
            break;
        case Facing.West:
            this.xPosition ++;
            break;
        }
    }

    public void execute(){
        this.switchCanIRun();
        for(int command = 0; command < commands.Count; command++){
            switch (commands[command]) {
            case "turnRight":
                Console.WriteLine("This robot turns to the right.");
                this.turnRight();
                break;
            case "turnLeft":
                Console.WriteLine("This robot turns to the left.");
                this.turnLeft();
                break;
            case "goForward":
                Console.WriteLine("This robot does one step forward.");
                this.goForward();
                break;
            case "goBackwards":
                Console.WriteLine("This robot does one step backwards.");
                this.goBackwards();
                break;
            case "1":
                Console.WriteLine("This robot does one step forward.");
                this.goForward(1);
                break;
            case "2":
                Console.WriteLine("This robot does two steps forward.");
                this.goForward(2);
                break;
            case "3":
                Console.WriteLine("This robot does three steps forward.");
                this.goForward(3);
                break;
            }
            this.printState();
        }
        clearCommands();
        this.switchCanIRun();
    }

    void switchCanIRun(){
        this.canIRun = !this.getCanIRun();
    }

    void clearCommands(){
        this.commands.Clear();
    }

    public void printState(){
        Console.WriteLine("The robot has position: (" + this.getXPosition() + "," + this.getYPosition() + "), with facing: "+ this.getDirection() + ".");
    }
}

class Roborally{
    static void Main(string[] args){
        Robot myFirstRobot = new Robot(0, 1, "North");
        Robot mySecondRobot = new Robot(1, 0, "West");

        myFirstRobot.printState();
        mySecondRobot.printState();

        myFirstRobot.turnLeft();
        myFirstRobot.goForward(3);
        Console.WriteLine("Execution!!!");
        myFirstRobot.execute();

        myFirstRobot.turnLeft();
        myFirstRobot.turnRight();
        myFirstRobot.turnRight();
        myFirstRobot.goBackwards();
        myFirstRobot.goForward(2);
        Console.WriteLine("Execution 2!!!");
        myFirstRobot.execute();

    }
}