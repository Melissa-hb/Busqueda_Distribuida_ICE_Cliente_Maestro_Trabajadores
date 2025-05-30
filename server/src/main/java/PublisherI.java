
import java.util.HashMap;
import java.util.concurrent.Flow.Subscriber;

import com.zeroc.Ice.Current;
import Demo.SubscriberPrx;

public class PublisherI implements Demo.Publisher {
    private HashMap<String, SubscriberPrx> subscribers;

    public PublisherI() {
        subscribers = new HashMap<>();
    }

    @Override
    public void addSubscriber(String name, SubscriberPrx sub, Current current) {
        System.out.println("New subscriber added: " + name);
        subscribers.put(name, sub);
    }

    @Override
    public void removeSubscriber(String name, Current current) {
        subscribers.remove(name);
        System.out.println("Subscriber removed: " + name);
    }

    // Método adicional: notificar a todos los subs
    public void notifySuscriber(String name, String msg){
        Subscriber suscriber = subscribers.get(name);
        suscriber.onUpdate(msg);
    }
}
