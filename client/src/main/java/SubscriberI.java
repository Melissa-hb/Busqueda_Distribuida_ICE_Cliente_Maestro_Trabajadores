import com.zeroc.Ice.Current;

public class SubscriberI implements Demo.Subscriber {
    @Override
    public void onUpdate(String message, Current current) {
        System.out.println("Received message: " + message);
    }

    
}
