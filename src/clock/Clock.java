package clock;

/**
 * Provides the entry point for the program. Creates Model View and Controller
 * instances, then passes control to the controller.
 * 
 * @author Calum Lindsay
 */
public class Clock {
    
    /**
     * Entry point for the program.
     * 
     * @param args Command line arguments provided to the program.
     */
    public static void main(String[] args)
    {
        Model model = new Model();
        View view = new View();
        
        Controller controller = new Controller(model, view);
        
        controller.begin();
    }
}
