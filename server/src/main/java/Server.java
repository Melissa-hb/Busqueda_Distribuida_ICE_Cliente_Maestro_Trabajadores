import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.zeroc.Ice.*;

public class Server {
    public static void main(String[] args) {
	
	try(Communicator communicator = Util.initialize(args, "properties.cfg")){

	    ObjectAdapter adapter = communicator.createObjectAdapter("services"); 

	    CoordinadorI publisher = new CoordinadorI(); 

	    adapter.add(publisher, Util.stringToIdentity("Coordinador"));
	    adapter.activate();

	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
	    String msj = ""; 

	    System.out.println("Puede andar un mensaje al cualquier worker conectado con el formato NombreWorker::Mensaje");
	    while ((msj = reader.readLine()) != null) {
		if(!msj.contains("::")){
		    System.out.println("Formato incorrecto. Debe ser NombreWorker::Mensaje");
		    continue; 
		}
		String[] command = msj.split("::"); 
		publisher.notifyWorker(command[0], command[1]);
	    	
	    }
	    reader.close();
	    communicator.waitForShutdown(); 
	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }
}