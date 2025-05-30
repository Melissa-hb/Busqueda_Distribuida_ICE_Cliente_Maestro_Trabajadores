import com.zeroc.Ice.*;
import Demo.*;

public class Client {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args, "properties.cfg")) {

            ObjectAdapter adapter = communicator.createObjectAdapter("Subscriber");

            Demo.Subscriber subscriberImpl = new SubscriberI();
            ObjectPrx proxy = adapter.add(subscriberImpl, Util.stringToIdentity("NN"));
            adapter.activate();

            SubscriberPrx subscriberPrx = SubscriberPrx.checkedCast(proxy);

            PublisherPrx publisher = PublisherPrx.checkedCast(
                communicator.propertyToProxy("publisher.proxy"));

            if (publisher == null) {
                System.err.println("Invalid proxy");
                return;
            }

            publisher.addSubscriber("NN", subscriberPrx);
        }
    }
}
