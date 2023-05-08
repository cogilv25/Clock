package clock;

public class Clock {
    
    public static void main(String[] args)
    {
        Model model = new Model();
        View view = new View();
        
        Controller controller = new Controller(model, view);
        
        controller.begin();
    }
}
