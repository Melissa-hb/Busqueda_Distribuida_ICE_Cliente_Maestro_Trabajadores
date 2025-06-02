module Demo {
    interface Suscriber {
        void onUpdate(string s);
    }

    interface Publisher {
        void addSuscriber(string name, Suscriber* o);
        void removeSuscriber(string name);
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
