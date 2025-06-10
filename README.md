# Búsqueda Distribuida de Números Perfectos

Este proyecto consiste en la implementación de un sistema distribuido usando ICE (Internet Communications Engine) para la búsqueda de números perfectos dentro de un rango definido por el usuario. El sistema está compuesto por tres tipos de componentes: Cliente, Maestro y Trabajadores. El cliente solicita la búsqueda, el maestro coordina la distribución del trabajo y los trabajadores realizan el cálculo en paralelo.

El sistema permite elegir entre una ejecución sincrónica o asincrónica y evaluar su impacto en el rendimiento. Los resultados muestran cómo el uso de asincronismo e hilos mejora significativamente los tiempos de ejecución y la escalabilidad.

## Integrantes

- Santiago Grajales
- Valentina Tobar
- Simon Reyes
- Melissa Hurtado

---

## Instrucciones de Ejecución

### 1. Requisitos Previos

- Java 8 o superior  
- ICE (versión recomendada: 3.7.4)  
- Gradle  
- Compilador `slice2java` incluido en ICE  

En caso de no tener la version de ICE recomendada puede dirigirse al documento de `build.gradle` busque el apartado de `subprojects` donde encontrara el `slice`

```java
    slice {
        java {
            files = [file("../App.ice")] 
        }
    }
```

Dentro de slice pero fuera de java coloca `iceHome = file('coloca aqui la ruta de tu ICE')` de la siguiente manera

```java
    slice {
        java {
            files = [file("../App.ice")] 
        }
        iceHome = file('coloca aqui la ruta de tu ICE')
    }
```

En caso de estar en una IDE asegúrate de configurar gradle con la versión que tienes.

Tambien, asegúrate de que las variables de entorno estén correctamente configuradas (`ICE_HOME`, `PATH` con acceso a `slice2java`, etc.)

### 2. Construir el proyecto con Gradle

En la raíz del proyecto, ejecuta:

```bash
./gradlew build
```

Esto compilará todos los módulos del proyecto.

Asegúrate de tener ICE correctamente instalado y configurado en tu sistema.

### 3. Ejecución de las clases

Para la ejecución de las clases hay que tener en cuenta el order de la conexion entre las clases. Primero se debera ejecutar el server que estara esperando que algun worker se conocte a el o que el cliente le mande una petición.

```bash
# Ejecutar el server
java -jar server/build/libs/server.jar
```

Donde para verificar la conexión futura entre worker y server, desde el server se puede mandar un mensaje a worker en el formato que establece el programa.

```bash
Puede andar un mensaje al cualquier worker conectado con el formato NombreWorker::Mensaje
```

Despues se debera ejecutar al menos un worker con la siguiente linea de comando.

```bash
# Ejecutar el worker
java -jar worker/build/libs/worker.jar
```

Donde debera escribir su nombre para realizar la conexion con el servidor.

```bash
Ingrese el nombre del trabajador: 
```

Por ultimo se debera ejecutar el cliente con la linea de comando.

```bash
# Ejecutar el cliente
java -jar client/build/libs/client.jar
```

### 4. Parámetros del Cliente

Al correr el cliente, el usuario deberá ingresar por consola los siguientes parámetros:

- **Rango de búsqueda**: número entero que define el rango superior del intervalo. El rango siempre comienza en 0.
- **Cantidad de trabajadores**: número de procesos trabajadores que deseas usar.
- **Tipo de ejecución**: elegir si la ejecución será sincrónica o asincrónica.

**Ejemplo de entrada**:

```bash
Ingrese el rango superior: 1000000  
Ingrese el número de trabajadores: 4  
De que forma desea que trabajen los trabajadores? (1: Sincronica, 2: Asincronica): 2
```

---

## Ejecución Distribuida en Múltiples Computadores

Si deseas ejecutar los componentes del sistema (Cliente, Maestro, Trabajadores) en diferentes computadores de una red local, debes seguir los siguientes pasos:

### Configuración de los archivos `.properties`

Cada módulo (`client`, `server` y `worker`) tiene su archivo de configuración ubicado en `src/main/resources/config.properties` (o el nombre que hayas usado). Asegúrate de lo siguiente:

- **Cliente**: Configura la dirección IP y el puerto del Maestro.

```properties
ClientCallback.Endpoints = default -p <PUERTO_DE_CLIENT> -h <IP_DE_CLIENT>
coordinador.proxy = Coordinador:default -p <PUERTO_DE_SERVER> -h <IP_DE_SERVER>
```

- **Server**: Configura la dirección IP y el puerto del Maestro.

```properties
services.Endpoints=default -p <PUERTO_DE_SERVER> -h <IP_DE_SERVER>
```

- **Worker**: Configura la dirección IP y el puerto del Maestro.

```properties
worker.Endpoints = default -p <PUERTO_DE_WORKER> -h <IP_DE_WORKER>
coordinador.proxy = Coordinador:default -p <PUERTO_DE_SERVER> -h <IP_DE_SERVER>
```

### Recomendaciones de red

- Todos los equipos deben estar conectados a la **misma red local** o red VPN.
- Verifica que cada máquina pueda hacer `ping` a las demás.
- Usa direcciones IP locales (por ejemplo: `192.168.1.10`) o nombres de host resolubles.

---

## Resultados Experimentales y Análisis

El sistema fue probado con múltiples combinaciones de rango y número de trabajadores, tanto en ejecución sincrónica como asincrónica. Se observó que:

- A mayor número de trabajadores, el tiempo de ejecución disminuye significativamente.
- La ejecución asincrónica ofrece mejores tiempos de respuesta y mayor eficiencia en comparación con la sincrónica.
- El uso de hilos dentro del maestro permitió manejar múltiples respuestas en paralelo, sin bloquear la ejecución general.

Puedes consultar las tablas de resultados y los gráficos en el informe adjunto [`Informe.pdf`](docs/Informe.pdf) en la carpeta [`docs/`](docs/).
Además, en la misma carpeta [`docs/`](docs/). encontrarás el archivo [`Evidencias.pdf`](docs/Evidencias.pdf), que contiene las pruebas experimentales realizadas, junto con las condiciones detalladas que se explican en el informe.

---

## Posibles Mejoras

- **Patrones de diseño**: la implementación podría beneficiarse del uso de patrones como *Factory Method* para instanciar trabajadores o *Observer* para notificar al cliente de forma más estructurada.
- **Persistencia de resultados**: guardar los números perfectos encontrados en archivos o bases de datos para su posterior análisis.
- **Interfaz gráfica**: una UI permitiría mejorar la interacción del usuario con el sistema.
- **Escalabilidad en red real**: adaptar la solución para ejecutarse en diferentes máquinas conectadas por red para simular un entorno distribuido más realista.

---

## Licencia

Este proyecto fue desarrollado con fines académicos. Todos los derechos reservados por sus autores.
