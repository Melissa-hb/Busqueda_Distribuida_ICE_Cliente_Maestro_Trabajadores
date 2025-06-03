import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.zeroc.Ice.Current;
import Demo.*;
public class CoordinadorI implements Coordinador{

    private Map<String, WorkerPrx> workers;
    public CoordinadorI(){
	    workers = new HashMap<>(); 
    }

    /* 
    @Override
    public void addSuscriber(String name,  SuscriberPrx suscriber,  Current current) {
        System.out.println("New Suscriber has been added ");
        suscribers.put(name, suscriber); 
    	
    }

    @Override
    public void removeSuscriber(String name, Current current) {
        suscribers.remove(name); 
        System.out.println("Remove Suscriber: " + name);
    }*/
    @Override
    public void addWorker(String name, WorkerPrx worker, Current current) {
        System.out.println("New Worker has been added: " + name);
        workers.put(name, worker);
    }

    public int[] crearRangosWorkers(int finish, int numberWorkers, Current current) {
        int[] rangos = new int[numberWorkers + 1];

        int base = finish / numberWorkers;
        int resto = finish % numberWorkers;

        int actual = 0;
        for (int i = 0; i <= numberWorkers; i++) {
            rangos[i] = actual;
            if (i < numberWorkers) {
                int incremento = base;
                    if (i < resto) {
                        incremento += 1; // Esto es para cuando sobra algo en la division
                    }
                actual += incremento;
            }
        }
        return rangos;
    }
    @Override
    public void buscarPerfectos(int start, int end, int numWorkers, ClientCallbackPrx callback, Current current) {
        System.out.println("Iniciando búsqueda de perfectos...");

        // Obtener los rangos
        int[] rangos = crearRangosWorkers(end - start, numWorkers, current);

        List<Integer> resultados = new ArrayList<>();
        long inicio = System.currentTimeMillis();

        try {
            // Llamar a los workers registrados
            int i = 0;
            for (Map.Entry<String, WorkerPrx> entry : workers.entrySet()) {
                if (i >= numWorkers) break;

                int rangoInicio = rangos[i] + start;
                int rangoFin = rangos[i + 1] + start;

                System.out.println("Worker " + entry.getKey() + " -> " + rangoInicio + " a " + rangoFin);

                // Llamar remotamente a cada worker
                int[] parcial = entry.getValue().encontrarPerfectos(rangoInicio, rangoFin);
                for (int n : parcial) {
                    resultados.add(n);
                }

                i++;
            }

            // Tiempo total
            long fin = System.currentTimeMillis();
            double tiempo = (fin - inicio) / 1000.0;

            // Enviar resultados al cliente
            int[] finalResult = resultados.stream().mapToInt(n -> n).toArray();
            callback.recibirResultado(finalResult, tiempo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void buscarAsyncPerfectos(int start, int end, int numWorkers, ClientCallbackPrx callback, Current current) {
        System.out.println("Iniciando búsqueda de perfectos...");

        //Obtener los rangos
        int[] rangos = crearRangosWorkers(end - start, numWorkers, current);

        List<Integer> resultados = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threads = new ArrayList<>();
        long inicio = System.currentTimeMillis();

        try {
            // Llamar a los workers registrados en paralelo
            int i = 0;
            for (Map.Entry<String, WorkerPrx> entry : workers.entrySet()) {
                if (i >= numWorkers) break;

                int rangoInicio = rangos[i] + start;
                int rangoFin = rangos[i + 1] + start;

                WorkerPrx worker = entry.getValue();
                System.out.println("Worker " + entry.getKey() + " -> " + rangoInicio + " a " + rangoFin);

                Thread t = new Thread(() -> {
                    try {
                        int[] parcial = worker.encontrarPerfectos(rangoInicio, rangoFin);
                        for (int n : parcial) {
                            resultados.add(n);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                threads.add(t);
                t.start();
                i++;
            }

            // Esperar que todos los hilos terminen
            for (Thread t : threads) {
                t.join();
            }

            //  Medir tiempo
            long fin = System.currentTimeMillis();
            double tiempo = (fin - inicio) / 1000.0;

            // Enviar resultados al cliente
            int[] finalResult = resultados.stream().mapToInt(n -> n).toArray();
            callback.recibirResultado(finalResult, tiempo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void notifyWorker(String name, String msg){
        WorkerPrx worker = workers.get(name);
        worker.notify();
    }
}