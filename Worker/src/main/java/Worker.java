import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.zeroc.Ice.*; 
import Demo.*;
public class Worker {
    public static void main(String[] args) {
    	try(Communicator communicator = Util.initialize(args, "properties.cfg")) {

            WorkerI worker = new WorkerI();

            ObjectAdapter adapter = communicator.createObjectAdapter("worker"); 

            ObjectPrx proxys = adapter.add(worker, Util.stringToIdentity("Worker"));

            adapter.activate(); 

            WorkerPrx workerPrx = WorkerPrx.checkedCast(proxys);

            CoordinadorPrx coordinador = CoordinadorPrx.checkedCast(communicator.propertyToProxy("coordinador.proxy")); 

            if(coordinador == null){
                throw new Error("Bat Ice Proxy"); 
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
            System.out.println("Ingrese el nombre del trabajador: ");
            String name = reader.readLine(); 

            coordinador.addWorker(name, workerPrx);
            communicator.waitForShutdown();
    	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }
}