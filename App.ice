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
    sequence<int> arrayNumbers;
    interface Worker{
    arrayNumbers encontrarPerfectos(int star, int finish);
    }

    interface Coordinador{
    arrayNumbers crearRangosWorkers(int finish, int numberWorkers);
    }
}
