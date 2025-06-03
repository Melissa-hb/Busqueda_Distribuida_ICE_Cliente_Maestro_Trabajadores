
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.zeroc.Ice.*;

import Demo.*;

public class Client {
    public static void main(String[] args) {
            System.out.println("Ejecutando Cliente...");
    	try(Communicator communicator = Util.initialize(args, "properties.cfg")) {

            ClientCallback client = new ClientI(); 

            ObjectAdapter adapter = communicator.createObjectAdapter("ClientCallback"); 

            ObjectPrx proxys = adapter.add(client, Util.stringToIdentity("ClientCallbackAdapter"));

            adapter.activate(); 

            ClientCallbackPrx clienteCallbackPrx = ClientCallbackPrx.checkedCast(proxys);
            adapter.activate();

            CoordinadorPrx coordinador = CoordinadorPrx.checkedCast(communicator.propertyToProxy("coordinador.proxy"));

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
            
            
            if (coordinador == null) {
                System.err.println("No se pudo obtener proxy del coordinador.");
                return;
            }
            int start = 1;
            System.out.println("Ingrese el número de fin :");
            int end = Integer.parseInt(reader.readLine());
            System.out.println("Ingrese el número de trabajadores :");
            int numWorkers = Integer.parseInt(reader.readLine());
            while (numWorkers <= 0) {
                System.out.println("El número de trabajadores debe ser mayor que 0. Intente nuevamente:");
                numWorkers = Integer.parseInt(reader.readLine());
            }
            System.out.println("De que forma desea que trabajen los trabajadores? (1: Sincronica, 2: Asincreonica)");
            int sinc = Integer.parseInt(reader.readLine());
            while (sinc != 1 && sinc != 2) {
                System.out.println("Opción inválida. Ingrese 1 para Sincronica o 2 para Asincrónica:");
                sinc = Integer.parseInt(reader.readLine());
            }
            if(sinc == 1){
                coordinador.buscarPerfectos(start, end, numWorkers, clienteCallbackPrx);
            } else {
                coordinador.buscarAsyncPerfectos(start, end, numWorkers, clienteCallbackPrx);
            }

            communicator.waitForShutdown();
    	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }
}