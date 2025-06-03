
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.zeroc.Ice.*;

import Demo.*;

public class Client {
    public static void main(String[] args) {
    	try(Communicator communicator = Util.initialize(args, "properties.cfg")) {

            ClientI client = new ClientI(); 

            ObjectAdapter adapter = communicator.createObjectAdapter("ClientCallbackAdapter"); 

            ObjectPrx proxys = adapter.add(client, Util.stringToIdentity("NN"));

            adapter.activate(); 

            ClientCallbackPrx clienteCallbackPrx = ClientCallbackPrx.checkedCast(proxys);
            adapter.activate();

            CoordinadorPrx coordinador = CoordinadorPrx.checkedCast(communicator.propertyToProxy("coordinador.proxy"));

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
            // String name = reader.readLine(); 

            
            communicator.waitForShutdown();
            
            System.out.println("Ingrese el número de inicio :");
            int start = 1;
            System.out.println("Ingrese el número de fin :");
            int end = reader.read();
            System.out.println("Ingrese el número de trabajadores :");
            int numWorkers = reader.read();
            if (coordinador == null) {
                System.err.println("No se pudo obtener proxy del coordinador.");
                return;
            }
            coordinador.buscarPerfectos(start, end, numWorkers, clienteCallbackPrx);



            communicator.waitForShutdown();
    	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }
}
// public class Cliente {
//     public static void main(String[] args) {
//         try (Communicator communicator = Util.initialize(args, "config.properties")) {

//             ObjectAdapter adapter = communicator.createObjectAdapter("CallbackAdapter");
//             ClienteCallbackI callback = new ClienteCallbackI();
//             ObjectPrx proxy = adapter.add(callback, Util.stringToIdentity("clienteCallback"));
//             ClienteCallbackPrx callbackPrx = ClienteCallbackPrx.checkedCast(proxy);
//             adapter.activate();

//             CoordinadorPrx coordinador = CoordinadorPrx.checkedCast(
//                 communicator.propertyToProxy("coordinador.proxy"));

//             if (coordinador == null) {
//                 System.err.println("No se pudo obtener proxy del coordinador.");
//                 return;
//             }

//             int start = 1, end = 10000, numWorkers = 3;
//             coordinador.buscarPerfectos(start, end, numWorkers, callbackPrx);

//             communicator.waitForShutdown();
//         }
//     }
// }
