import java.util.HashMap;
import com.zeroc.Ice.Current;
import Demo.SuscriberPrx;
//import Demo.PublisherPrx;

public class CoordinadorI implements Perfectos.Coordinador{

    private HashMap<String, SuscriberPrx> suscribers; 

    public CoordinadorI(){
	    suscribers = new HashMap<>(); 
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

    public void notifySuscriber(String name, String msg){
        SuscriberPrx suscriber = suscribers.get(name);
        suscriber.onUpdate(msg);
    }
}