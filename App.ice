module Demo {
    interface Subscriber {
        void onUpdate(string s);
    }

    interface Publisher {
        void addSubscriber(string name, Subscriber* o);
        void removeSubscriber(string name);
    }
}

module Perfectos{
    interface Worker{
        int[] encontrarPerfectos(int star, int finish);
    }

    interface Coordinador{
        int[] crearRangosWorkers(int finish, int numberWorkers);
    }
}
