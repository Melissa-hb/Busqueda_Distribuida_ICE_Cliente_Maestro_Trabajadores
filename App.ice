module Demo {
    sequence<int> IntSeq;

    interface ClientCallback {
        void recibirResultado(IntSeq resultados, double tiempo);
        
    }

    interface Worker {
        IntSeq encontrarPerfectos(int start, int end);
    }

    interface Coordinador {
        void buscarPerfectos(int start, int end, int numWorkers, ClientCallback* callback);
        void buscarAsyncPerfectos(int start, int end, int numWorkers, ClientCallback* callback);
        void addWorker(string nombre, Worker* worker);
    }
}
