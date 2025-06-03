import java.util.ArrayList;
import java.util.List;

import com.zeroc.Ice.Current; 

public class WorkerI implements Demo.Worker {
    @Override
    public int[] encontrarPerfectos(int start, int end, Current current) {
        List<Integer> perfectos = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (esPerfecto(i)) {
                perfectos.add(i);
            }
        }
        return perfectos.stream().mapToInt(i -> i).toArray();
    }
    private boolean esPerfecto(int n) {
        int suma = 1;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) suma += i;
        }
        return n > 1 && suma == n;
    }

}