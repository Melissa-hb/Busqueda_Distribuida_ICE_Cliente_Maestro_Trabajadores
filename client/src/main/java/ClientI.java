import com.zeroc.Ice.Current; 

public class ClientI implements Demo.ClientCallback {
    @Override
    public void recibirResultado(int[] resultados, double tiempo, Current current) {
        System.out.println("\n===== RESULTADO DE LA BUSQUEDA =====");

        if (resultados == null || resultados.length == 0) {
            System.out.println("No se encontraron numeros perfectos en el rango especificado.");
        } else {
            System.out.println("Numeros perfectos encontrados:");
            for (int n : resultados) {
                System.out.println("- " + n);
            }
        }
        System.out.println("Tiempo total: " + tiempo + " segundos");
    }
}