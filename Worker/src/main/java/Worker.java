import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.zeroc.Ice.*;
import Demo.*;

public class Worker {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args, "properties.cfg")) {

            WorkerI worker = new WorkerI();

            ObjectAdapter adapter = communicator.createObjectAdapter("worker");

            ObjectPrx proxys = adapter.add(worker, Util.stringToIdentity("Worker12"));

            adapter.activate();

            WorkerPrx workerPrx = WorkerPrx.checkedCast(proxys);

            CoordinadorPrx coordinador = null;
            try {
                coordinador = CoordinadorPrx.checkedCast(communicator.propertyToProxy("coordinador.proxy"));
                if (coordinador == null) {
                    throw new RuntimeException("No se pudo obtener el proxy del coordinador");
                }
            } catch (java.lang.Exception e) {
                System.err.println("Error al obtener o castear el proxy del coordinador: " + e.getMessage());
                e.printStackTrace();
                return; // Detener ejecución si el coordinador no está disponible
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Ingrese el nombre del trabajador: ");
                String name = reader.readLine();

                coordinador.addWorker(name, workerPrx);
                System.out.println("Trabajador registrado correctamente en el coordinador.");

            } catch (IOException e) {
                System.err.println("Error al leer entrada del usuario: " + e.getMessage());
            } catch (LocalException e) {
                System.err.println("Error de comunicacion con el coordinador: " + e.getMessage());
                e.printStackTrace();
            } catch (java.lang.Exception e) {
                System.err.println("Error inesperado durante el registro del worker: " + e.getMessage());
                e.printStackTrace();
            }

            communicator.waitForShutdown();

        } catch (LocalException e) {
            System.err.println("Error ICE durante la inicializacion o no se ha iniciado el server : " + e.getMessage());
            e.printStackTrace();
        } catch (java.lang.Exception e) {
            System.err.println("Error inesperado al iniciar Worker: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
