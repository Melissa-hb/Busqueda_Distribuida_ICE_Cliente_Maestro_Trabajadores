import com.zeroc.Ice.Current; 

public class ClientI implements Demo.ClientCallback {
    @Override
    public void recibirResultado(int[] resultados, double tiempo, Current current) {
        System.out.println("Números perfectos encontrados:");
        for (int n : resultados) {
            System.out.println(n);
        }
        System.out.println("Tiempo total: " + tiempo + " segundos");
    }
}