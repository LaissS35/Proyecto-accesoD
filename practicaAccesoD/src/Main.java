import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //datos de biblioteca, se generar 5 libros aleatorios, se pueden añadir más ,eliminar y editar
        //fichero con todos los libros de la biblioteca,fichero con nombre de usuarios con libros prestados y lista de personas a las que no prestar libros, si se mete ese nombre no se permitira que se preste
        //XML de todos los libros en la biblioteca

        llenar();

        menu();
    }

    private static void llenar() throws IOException {
        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");
        RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "r");


        long tamanyo = raf.length();
        //si no hay datos se crean automaticamente
        if (tamanyo == 0) {

            String libros;
            String estado = "disponible";


            StringBuilder buff;
            StringBuilder buff2;


            for (int j = 0; j < 5; j++) {
                libros = "Libros " + j;
                buff = new StringBuilder(libros);
                buff2 = new StringBuilder(estado);


                buff.setLength(20);
                buff2.setLength(20);


                fichero.writeInt(j + 1);//id automatico
                fichero.writeChars(buff.toString());//nombre
                fichero.writeChars(buff2.toString());//estado


            }

        }

    }//si no hay datos se generan 5


    private static void menu() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean avance = false;

        int opcion = 0;

        do {
            System.out.println("Bienvenido a la biblioteca ¿que vas a hacer hoy?\n 1.-registrar nuevo libro\n2.-dar de baja un libro\n3.-modificar libro\n4.-tomar prestado libro\n5.-Lista de todos los libros" +
                    "\n6.-lista de betados\n7.-lista libros prestados\n8.-devolver libro\n9.-Lista Clientes\n10.-añadir cliente\n11.-Eliminar cliente\n12.-Salir!");
            try {
                opcion = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Debes meter un número");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (opcion > 0 && opcion <= 12) {
                avance = true;
            } else {
                System.out.println("Mete un valor valido");
            }
        } while (!avance);
        if (opcion == 1) {
            crear(reader);
            menu();
        }
        if (opcion == 2) {
            eliminar(reader);
            menu();
        }
        if (opcion == 3) {
            editar(reader);
            menu();
        }
        if (opcion == 4) {
            prestar(reader);
            menu();
        }
        if (opcion == 5) {
            //eliminarL(reader);
            TodosLibros();
            menu();
        }
        if (opcion == 6) {
            listaNegra(reader);
            menu();
        }
        if (opcion == 7) {
            librosPrestados();
            menu();
        }
        if (opcion == 8) {
            devolver();
            menu();
        }
        if (opcion == 9) {
            ListaClientes();
            menu();
        }
        if (opcion == 10) {
            Acliente(reader);
            menu();
        }
        if (opcion == 11) {
            EliCliente(reader);
            menu();
        }
        if (opcion == 12) {
            System.out.println("Saliste ;)");
        }
    }

    //FUNCIONES CLIENTES id,nombre,Nlibros Max->3
    private static void ListaClientes() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(".//Clientes.dat", "rw");

        if (raf.length() == 0) {
            System.out.println("no hay clientes registrados aun");
        } else {

            char[] nombres = new char[20];
            char a;
            int LibrosM;
            int pos = 0, id;


            try {
                while (true) {
                    raf.seek(pos);
                    id = raf.readInt();
                    for (int i = 0; i < nombres.length; i++) {
                        a = raf.readChar();
                        nombres[i] = a;

                    }
                    LibrosM = raf.readInt();

                    String nombres2 = new String(nombres);


                    if (id != 0) {
                        System.out.println("ID: " + id + " nombre: " + nombres2.trim() + " Nº libros prestados: " + LibrosM);

                    }

                    if (raf.getFilePointer() == raf.length()) {
                        break;
                    } else {
                        pos += 48;//diferente cantidad
                    }


                }
            } catch (
                    IOException e) {
                System.out.println("Ya se han mostrado todos los clientes");
            }
        }

    } //probar

    private static void Acliente(BufferedReader reader) throws IOException {
        int libros = 0;


        //añadir datos
        RandomAccessFile fichero = new RandomAccessFile(".//Clientes.dat", "rw");
        RandomAccessFile raf = new RandomAccessFile(".//Clientes.dat", "r");

        long tamanyo = raf.length();



        if (tamanyo == 0) {
            String nombre;
            do {
                System.out.println("escribe el nombre del nuevo cliente");
                nombre = reader.readLine();

            } while (nombre.length() < 2);

            //insertar nuevos datos
            long ids = fichero.length();
            fichero.seek(ids);//volverse a posicionar al final

            StringBuffer buff3;


            buff3 = new StringBuffer(nombre);
            buff3.setLength(20);


            fichero.writeInt(1);
            fichero.writeChars(buff3.toString());
            fichero.writeInt(libros);


            fichero.close();
            raf.close();

            System.out.println("Cliente registrado con exito");

        } else {
            //posicionarnos al final de los datos
            int pos = 0;
            int id = 0;
            //coger el ultimo id
            try {
                while (true) {
                    raf.seek(pos);
                    id = raf.readInt();


                    if (raf.getFilePointer() == raf.length()) {
                        break;
                    } else {
                        pos += 48;//valor correcto para cliente
                    }


                }
            } catch (
                    IOException e) {

            }

            //recoger nuevos datos


            String nombre;
            do {
                System.out.println("escribe el nombre del nuevo cliente");
                nombre = reader.readLine();

            } while (nombre.length() < 2);

            //insertar nuevos datos
            long ids = fichero.length();
            fichero.seek(ids);//volverse a posicionar al final

            StringBuffer buff3;


            buff3 = new StringBuffer(nombre);
            buff3.setLength(20);


            fichero.writeInt(id + 1);
            fichero.writeChars(buff3.toString());
            fichero.writeInt(libros);


            fichero.close();
            raf.close();

            System.out.println("Cliente registrado con exito");


        }

    } //probar
     private  static void EliCliente (BufferedReader reader) throws IOException {
         System.out.println("mira el id:");
         ListaClientes();

         System.out.println("escribe el id del cliente que quieres eliminar");
         int numero = Integer.parseInt(reader.readLine());

         if (existe(numero)) {

             RandomAccessFile raf = new RandomAccessFile(".//Clientes.dat", "rw");
             RandomAccessFile ficheroTemporal = new RandomAccessFile(".//Clientes2.dat", "rw");

             File ficheroBorrar = new File(".//Clientes.dat");


             char[] apellidos = new char[20];
             char a;
             char b;
             char[] estado = new char[20];
             int Libros;
             int pos = 0, id = 0;
             StringBuilder buff1;
             StringBuilder buff2;

             //escrbir en el fichero temporal los valores que se quieren
             try {
                 while (true) {
                     raf.seek(pos);
                     id = raf.readInt();
                     for (int i = 0; i < apellidos.length; i++) {
                         a = raf.readChar();
                         apellidos[i] = a;


                     }
                    Libros = raf.readInt();

                     String apellidos2 = new String(apellidos);
                     String estado2 = new String(estado);


                     if (id == numero) {
                         System.out.println("se lo salta");


                     } else {
                         buff1 = new StringBuilder(apellidos2);
                         buff2 = new StringBuilder(estado2);


                         buff1.setLength(20);
                         buff2.setLength(20);


                         ficheroTemporal.seek(pos);

                         ficheroTemporal.writeInt(id);
                         ficheroTemporal.writeChars(buff1.toString());
                         ficheroTemporal.writeInt(Libros);

                     }

                     if (raf.getFilePointer() == raf.length()) {
                         break;
                     } else {
                         pos += 48;
                     }


                 }
             } catch (
                     IOException e) {
                 System.out.println("Ya se termino");
             }


             ficheroBorrar.delete();//borra el fichero original

             //reescribir el fichero original
             RandomAccessFile fichero2 = new RandomAccessFile(".//Clientes2.dat", "rw");
             RandomAccessFile ficheroD = new RandomAccessFile(".//Clientes.dat", "rw");

             pos = 0;

             char[] nombres = new char[20];
             char aa;

             int Libros2;


             try {
                 while (true) {
                     fichero2.seek(pos);
                     id = fichero2.readInt();
                     for (int i = 0; i < nombres.length; i++) {
                         aa = fichero2.readChar();
                         nombres[i] = aa;


                     }

                     String nombres2 = new String(nombres);
                     Libros2=fichero2.readInt();

                     buff1 = new StringBuilder(nombres2);
                     buff1.setLength(20);

                     System.out.println("entra");

                     ficheroD.seek(pos);

                     ficheroD.writeInt(id);
                     ficheroD.writeChars(buff1.toString());
                     ficheroD.writeInt(Libros2);


                     if (fichero2.getFilePointer() == fichero2.length()) {
                         break;
                     } else {
                         pos += 48;
                     }


                 }
             } catch (
                     IOException e) {
                 System.out.println("Se termino de guardar los valores correctors");
             }
             System.out.println("lista final:");
             ListaClientes();

             //se borra el fichero sobrante
             File limpiar = new File(".//Clientes2.dat");
             limpiar.delete();


         } else {
             System.out.println("no existe");
         }

     }//probar

    //FUNCIONES MENU
    private static void devolver() {

    }

    private static void librosPrestados() {
    }


    private static boolean existe(int numero) throws FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(".//Clientes.dat", "r");

        int pos = 0, id;


        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();


                if (id == numero) {
                    return true;

                }

                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    pos += 48;
                }


            }
        } catch (
                IOException e) {
            System.out.println("Ya se busco el cliente");
        }


        return false;
    }//existe id cliente

    //MENU LISTA NEGRA
    private static void listaNegra(BufferedReader reader) throws IOException {
        System.out.println("¿que quieres hacer?:\n1.-ver lista\n2.-añadir a la lista\n3.-eliminar de la lista\n4.-volver al menú");
        int opcion = Integer.parseInt(reader.readLine());
        if (opcion == 1) {
            leerLista();
        }
        if (opcion == 2) {
            nuevoLN(reader);

        }
        if (opcion == 3) {
            expulsar(reader);
        }
        if (opcion == 4) {
            menu();
        }

    }

    private static void nuevoLN(BufferedReader reader) throws IOException {
        System.out.println("lista de clientes:");
        ListaClientes();


        int nombre;
        do {
            System.out.println("escribe el id");
            nombre = Integer.parseInt(reader.readLine());

            if (existe(nombre)) {
                System.out.println("causas:");
                String causa = reader.readLine();

                RandomAccessFile fichero = new RandomAccessFile(".//ListaNegra.dat", "rw");


                StringBuilder buff;
                StringBuilder buff2;

                buff = new StringBuilder(nombre);//remodelar, para que encuentre el nombre en ese ID especifico
                buff2 = new StringBuilder(causa);


                buff.setLength(20);
                buff2.setLength(20);

                fichero.writeChars(buff.toString());//nombre
                fichero.writeChars(buff2.toString());//causa

                System.out.println("usuario vetado con exito");

                //eliminar el usuario de la lista original
            } else {
                System.out.println("este cliente no existe");
            }

        } while (!existe(nombre));


    }//falta borrar cliente de lista orginal

    private static void expulsar(BufferedReader reader) throws IOException {
        RandomAccessFile fichero = new RandomAccessFile(".//ListaNegra.dat", "rw");
        RandomAccessFile raf = new RandomAccessFile(".//ListaNegra.dat", "r");

        //posicionarnos al final de los datos
        int pos = 0;
        int id = 0;


        //coger el ultimo id
        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();


                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    pos += 84;
                }


            }
        } catch (
                IOException e) {

        }

        //recoger nuevos datos

        String nombre;
        do {
            System.out.println("Escribe el nombre del cliente que quieres expulsar");
            nombre = reader.readLine();
            //comparar con lista de personas ya registradas

        } while (nombre.length() < 3);

        String motivo;

        do {
            System.out.println("Da el motivo por el cual esta expulsado(minimo 10 caracteres)");
            motivo = reader.readLine();
        } while (motivo.length() < 10);


        //insertar nuevos datos
        long ids = fichero.length();
        fichero.seek(ids);//volverse a posicionar al final

        StringBuffer buff3;
        StringBuffer buff4;

        buff3 = new StringBuffer(nombre);
        buff3.setLength(20);

        buff4 = new StringBuffer(motivo);
        buff4.setLength(20);

        fichero.writeInt(id + 1);
        fichero.writeChars(buff3.toString());
        fichero.writeChars(buff4.toString());


        fichero.close();
        raf.close();

    }//probar, falta eliminar de la lista

    private static void leerLista() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(".//ListaNegra.dat", "rw");
        long tamanyo = raf.length();

        if( tamanyo>0){
        char[] apellidos = new char[20];
        char a;
        char b;
        char[] estado = new char[20];
        int pos = 0, id;


        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = raf.readChar();
                    apellidos[i] = a;


                }
                for (int i = 0; i < estado.length; i++) {
                    b = raf.readChar();
                    estado[i] = b;
                }

                String apellidos2 = new String(apellidos);
                String estado2 = new String(estado);


                if (id != 0) {
                    System.out.println("ID: " + id + " nombre: " + apellidos2.trim() + " motivo: " + estado2.trim());

                }

                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    pos += 84;//creo que este esta bien
                }


            }
        } catch (
                IOException e) {
            System.out.println("Ya se han mostrado todos los expulsados");
        }
        }else{
            System.out.println("la lista esta vacia");
        }

    }//probar


    //MENU NORMAL

    private static void TodosLibros() throws IOException {

        RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "r");

        char[] apellidos = new char[20];
        char a;
        char b;
        char[] estado = new char[20];
        int pos = 0, id;


        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = raf.readChar();
                    apellidos[i] = a;


                }
                for (int i = 0; i < estado.length; i++) {
                    b = raf.readChar();
                    estado[i] = b;
                }

                String apellidos2 = new String(apellidos);
                String estado2 = new String(estado);


                if (id != 0) {
                    System.out.println("ID: " + id + " nombre: " + apellidos2.trim() + " estado: " + estado2.trim());

                }

                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    pos += 84;
                }


            }
        } catch (
                IOException e) {
            System.out.println("Ya se han mostrado todos los libros");
        }

    }//funciona, muestra la lista de todos los libros

    private static void TodosLibros2() throws FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(".//Libros2.dat", "rw");

        char[] apellidos = new char[20];
        char a;
        char b;
        char[] estado = new char[20];
        int pos = 0, id;


        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = raf.readChar();
                    apellidos[i] = a;


                }
                for (int i = 0; i < estado.length; i++) {
                    b = raf.readChar();
                    estado[i] = b;
                }

                String apellidos2 = new String(apellidos);
                String estado2 = new String(estado);


                if (id < 0) {
                    System.out.println("ese número no sirve");

                } else {

                    System.out.println("ID: " + id + " nombre: " + apellidos2.trim() + " estado: " + estado2.trim());


                }

                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    pos += 84;
                }


            }
        } catch (
                IOException e) {
            System.out.println("Ya se han mostrado todos los libros");
        }

    }

    private static void LibrosPresta() throws FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "r");

        char[] apellidos = new char[20];
        char a;
        char b;
        char[] estado = new char[20];
        int pos = 0, id;


        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = raf.readChar();
                    apellidos[i] = a;


                }
                for (int i = 0; i < estado.length; i++) {
                    b = raf.readChar();
                    estado[i] = b;
                }

                String apellidos2 = new String(apellidos);
                String estado2 = new String(estado);

                if (!estado2.trim().equals("descontinuado") && !estado2.trim().equals("alquilado")) {
                    System.out.println("ID: " + id + " nombre: " + apellidos2.trim() + " estado: " + estado2.trim());
                }


                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    pos += 84;
                }


            }
        } catch (
                IOException e) {
            System.out.println("Ya se han mostrado todos los libros");
        }

    }//solo mostrara al usuario los libros disponibles para alquilar

    private static void prestar(BufferedReader reader) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "r");
        int idB = 0;
        boolean seguir = false;
        String estadoN = "alquilado";

        int poss = 0;
        int idd = 0;


        //coger el ultimo id
        try {
            while (true) {
                raf.seek(poss);
                idd = raf.readInt();


                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    poss += 84;
                }


            }
        } catch (
                IOException e) {

        }

        do {
            System.out.println("Elige el ID del libro que quieras alquilar");
            LibrosPresta();

            try {
                idB = Integer.parseInt(reader.readLine());

                if (idB > 0 && idB <= idd) {
                    System.out.println("entra");
                    seguir = true;
                } else {
                    System.out.println("este ID no sirve");
                }

            } catch (NumberFormatException e) {
                System.out.println("mete un valor númerico");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } while (!seguir);

        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");


        char[] apellidos = new char[20];
        char a;
        int pos = 0, id;
        //buscar el id especifico
        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = raf.readChar();
                    apellidos[i] = a;


                }

                String apellidos2 = new String(apellidos);


                //cuando se encuentra se hace la modificacion
                if (id == idB) {
                    fichero.seek(pos);//ojo con el indice del fichero en el que se quiere editar, el de raf seek no aplica
                    StringBuffer buff2;

                    buff2 = new StringBuffer(estadoN);
                    buff2.setLength(20);

                    fichero.writeInt(id);
                    fichero.writeChars(apellidos2);
                    fichero.writeChars(buff2.toString());


                }


                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    pos += 84;
                }


            }
        } catch (
                IOException e) {
            System.out.println("se presto libro");
        }


    }//funciona, marca el libro seleccionado como prestado

    private static void editar(BufferedReader reader) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "r");
        int idB = 0;
        boolean seguir = false;

        int poss = 0;
        int idd = 0;


        //coger el ultimo id
        try {
            while (true) {
                raf.seek(poss);
                idd = raf.readInt();


                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    poss += 84;
                }


            }
        } catch (
                IOException e) {

        }


        System.out.println("Elige el ID del libro que quieras editar");
        TodosLibros();

        do {
            try {
                idB = Integer.parseInt(reader.readLine());
                if (idB > 0 && idB <= idd) {
                    seguir = true;


                } else {
                    System.out.println("este número no sirve");

                }

            } catch (NumberFormatException e) {
                System.out.println("debes meter un valor númerico");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (!seguir);

        seguir = false;


        do {
            System.out.println("Escribe su nuevo nombre");
            String nombre = reader.readLine();
            if (nombre.length() < 2) {
                System.out.println("escribe un nombre de minimo 2 caracteres");
            } else {
                seguir = true;
            }
        } while (!seguir);

        seguir = false;


        String estadoN;
        do {
            System.out.println("Escribe su nuevo estado (disponible,alquilado,descontinuado");
            estadoN = reader.readLine();
            if (estadoN != "disponible" && estadoN != "alquilado" && estadoN != "descontinuado") {
                System.out.println("esto no sirve, intentalo de nuevo");
            } else {
                seguir = true;
            }
        } while (!seguir);


        //editar libro seleccionado


        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");


        char[] apellidos = new char[20];
        char a;
        int pos = 0, id;
        //buscar el id especifico
        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = raf.readChar();
                    apellidos[i] = a;


                }

                String apellidos2 = new String(apellidos);


                //cuando se encuentra se hace la modificacion
                if (id == idB) {
                    fichero.seek(pos);//ojo con el indice del fichero en el que se quiere editar, el de raf seek no aplica
                    StringBuffer buff2;

                    buff2 = new StringBuffer(estadoN);
                    buff2.setLength(20);

                    fichero.writeInt(id);
                    fichero.writeChars(apellidos2);
                    fichero.writeChars(buff2.toString());


                }


                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    pos += 84;
                }


            }
        } catch (
                IOException e) {
            System.out.println("se edito el libro");
        }
    }//probar

    private static void eliminar(BufferedReader reader) throws IOException {

        int idB = 0;
        String estadoN = "descontinuado";

        System.out.println("Elige el ID del libro que quieras alquilar");
        TodosLibros();

        try {
            idB = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("debe meter un valor númerico");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "r");
        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");


        char[] apellidos = new char[20];
        char a;
        int pos = 0, id;
        //buscar el id especifico
        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = raf.readChar();
                    apellidos[i] = a;


                }


                String apellidos2 = new String(apellidos);


                //cuando se encuentra se hace la modificacion
                if (id == idB) {
                    fichero.seek(pos);
                    StringBuffer buff2;

                    buff2 = new StringBuffer(estadoN);
                    buff2.setLength(20);

                    fichero.writeInt(id);
                    fichero.writeChars(apellidos2);
                    fichero.writeChars(buff2.toString());


                }


                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    pos += 84;
                }


            }
        } catch (
                IOException e) {
            System.out.println("se dio de baja un libro, ya no se podra alquilar");
        }
    }//marca como descontinuado un libro, asi no permite que se pueda prestar, pero permanece en la base de datos

    private static void crear(BufferedReader reader) throws IOException {

        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");
        RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "r");

        //posicionarnos al final de los datos
        int pos = 0;
        int id = 0;


        //coger el ultimo id
        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();


                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    pos += 84;
                }


            }
        } catch (
                IOException e) {

        }

        //recoger nuevos datos

        String nombre;
        do {
            System.out.println("Escribe el nombre del libro");
            nombre = reader.readLine();

        } while (nombre.length() < 2);


        String disponibilidad = "disponible";


        //insertar nuevos datos
        long ids = fichero.length();
        fichero.seek(ids);//volverse a posicionar al final

        StringBuffer buff3;
        StringBuffer buff4;

        buff3 = new StringBuffer(nombre);
        buff3.setLength(20);

        buff4 = new StringBuffer(disponibilidad);
        buff4.setLength(20);

        fichero.writeInt(id + 1);
        fichero.writeChars(buff3.toString());
        fichero.writeChars(buff4.toString());


        fichero.close();
        raf.close();

        System.out.println("Libro registrado con exito");
        menu();// vuelve a mostrar el menu


    }//funciona, se crea un nuevo registro de libro

    private static void eliminarL(BufferedReader reader) throws IOException {
        System.out.println("mira el id:");
        TodosLibros();

        System.out.println("escribe el id del cliente que quieres eliminar");
        int numero = Integer.parseInt(reader.readLine());

        if (existe(numero)) {

            RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "rw");
            RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");

            RandomAccessFile ficheroTemporal = new RandomAccessFile(".//Libros2.dat", "rw");

            File ficheroBorrar = new File(".//Libros.dat");


            char[] apellidos = new char[20];
            char a;
            char b;
            char[] estado = new char[20];
            int pos = 0, id = 0;
            StringBuilder buff1;
            StringBuilder buff2;

            //escrbir en el fichero temporal los valores que se quieren
            try {
                while (true) {
                    raf.seek(pos);
                    id = raf.readInt();
                    for (int i = 0; i < apellidos.length; i++) {
                        a = raf.readChar();
                        apellidos[i] = a;


                    }
                    for (int i = 0; i < estado.length; i++) {
                        b = raf.readChar();
                        estado[i] = b;
                    }

                    String apellidos2 = new String(apellidos);
                    String estado2 = new String(estado);


                    if (id == numero) {
                        System.out.println("se lo salta");


                    } else {
                        buff1 = new StringBuilder(apellidos2);
                        buff2 = new StringBuilder(estado2);


                        buff1.setLength(20);
                        buff2.setLength(20);


                        ficheroTemporal.seek(pos);

                        ficheroTemporal.writeInt(id);
                        ficheroTemporal.writeChars(buff1.toString());
                        ficheroTemporal.writeChars(buff2.toString());

                    }

                    if (raf.getFilePointer() == raf.length()) {
                        break;
                    } else {
                        pos += 84;
                    }


                }
            } catch (
                    IOException e) {
                System.out.println("Ya se termino");
            }

            System.out.println("nueva lista de libros");
            TodosLibros2();

            ficheroBorrar.delete();//borra el fichero original

            //reescribir el fichero original
            RandomAccessFile fichero2 = new RandomAccessFile(".//Libros2.dat", "rw");
            RandomAccessFile ficheroD = new RandomAccessFile(".//Libros.dat", "rw");

            pos = 0;

            char[] nombres = new char[20];
            char aa;
            char bb;
            char[] es = new char[20];

            try {
                while (true) {
                    fichero2.seek(pos);
                    id = fichero2.readInt();
                    for (int i = 0; i < nombres.length; i++) {
                        aa = fichero2.readChar();
                        nombres[i] = aa;


                    }
                    for (int i = 0; i < es.length; i++) {
                        bb = fichero2.readChar();
                        es[i] = bb;
                    }

                    String nombres2 = new String(nombres);
                    String esstado = new String(es);


                    buff1 = new StringBuilder(nombres2);
                    buff2 = new StringBuilder(esstado);


                    buff1.setLength(20);
                    buff2.setLength(20);
                    System.out.println("entra");

                    ficheroD.seek(pos);

                    ficheroD.writeInt(id);
                    ficheroD.writeChars(buff1.toString());
                    ficheroD.writeChars(buff2.toString());


                    if (fichero2.getFilePointer() == fichero2.length()) {
                        break;
                    } else {
                        pos += 84;
                    }


                }
            } catch (
                    IOException e) {
                System.out.println("Se termino de guardar los valores correctors");
            }
            System.out.println("lista final:");
            TodosLibros();

            //se borra el fichero sobrante
            File limpiar = new File(".//Libros2.dat");
            limpiar.delete();


        } else {
            System.out.println("no existe");
        }


    } //actualizar para clientes
}