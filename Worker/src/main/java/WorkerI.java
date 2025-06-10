import java.util.ArrayList;
import java.util.List;

import com.zeroc.Ice.Current; 

public class WorkerI implements Demo.Worker {
    @Override
    public int[] encontrarPerfectos(int start, int end, Current current) {
        try{
            if (start >= end || start < 0 || end < 0) {
                throw new IllegalArgumentException("Rango inválido: start debe ser menor que end, y ambos no negativos.");
            }

        List<Integer> perfectos = new ArrayList<>();

        for (int i = start; i < end; i++) {
            if (esPerfecto(i)) {
                perfectos.add(i);
            }
        }
        return perfectos.stream().mapToInt(i -> i).toArray();

        } catch (IllegalArgumentException e) {
            // Error por entrada incorrecta
            System.err.println("Error de validación en WorkerI: " + e.getMessage());
            throw e;
        } catch (java.lang.Exception e) {
            // Error inesperado
            System.err.println("Error inesperado en WorkerI: " + e.getMessage());
            e.printStackTrace();
            // Lanzar una excepción para que el coordinador pueda manejarlo
            throw new RuntimeException("Error inesperado En worker I", e);
        }
        
    }
    private boolean esPerfecto(int n) {
        int suma = 1;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) suma += i;
        }
        return n > 1 && suma == n;
    }

}