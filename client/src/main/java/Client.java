
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.zeroc.Ice.*;

import Demo.*; 

public class Client {
    public static void main(String[] args) {
    	try(Communicator communicator = Util.initialize(args, "properties.cfg")) {

            Suscriber suscriber = new SuscriberI(); 

            ObjectAdapter adapter = communicator.createObjectAdapter("Suscriber"); 

            ObjectPrx proxys = adapter.add(suscriber, Util.stringToIdentity("NN"));

            adapter.activate(); 

            SuscriberPrx suscriberPrx = SuscriberPrx.checkedCast(proxys);

            PublisherPrx publisher = PublisherPrx.checkedCast(communicator.propertyToProxy("publisher.proxy")); 

            if(publisher == null){
                throw new Error("Bat Ice Proxy"); 
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
            String name = reader.readLine(); 

            publisher.addSuscriber(name, suscriberPrx);
            communicator.waitForShutdown();
    	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }
}