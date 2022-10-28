import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;

public class Libro {



    //FUNCIONES LIBRO
    public static void TodosLibros() throws IOException {

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(".//Libros.dat", "r");
        } catch (FileNotFoundException e) {
            System.out.println("error con el fichero");
        }

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

    }//MUESTRA TODOS LOS LIBROS, NO IMPORTA SU ESTADO
    public static void LibrosPresta() throws FileNotFoundException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(".//Libros.dat", "r");
        } catch (FileNotFoundException e) {
            System.out.println("error con el fichero");
        }

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

                if (!estado2.trim().equals("discontinuado") && !estado2.trim().equals("alquilado")) {
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

    }//MUESTRA LOS LIBROS EN ESTADO DISPONIBLE

    public static void prestar(BufferedReader reader) throws IOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(".//Libros.dat", "r");
        } catch (FileNotFoundException e) {
            System.out.println("error con el fichero");
        }
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
            Libro.LibrosPresta();

            try {
                idB = Integer.parseInt(reader.readLine());

                if (idB > 0 && idB <= idd) {
                    //si el libro esta en estado alquilado no debe permitir que se alquile, de entrada no se ve
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

        seguir = false;

        //ver que el usuario no tenga ya 3 libros alquilados, y que exista
        RandomAccessFile raf2 = new RandomAccessFile(".//Clientes.dat", "rw");

        do {
            System.out.println("escribe el ID de tu usuario para proceder a alquilar el libro");
            int numero = Integer.parseInt(reader.readLine());
            if (Cliente.existe(numero)) {
                //ver que no exceda el limite
                int pos = 0;
                int id;
                int Mlibros;

                char[] nombre = new char[20];
                char n;


                try {
                    while (true) {
                        raf2.seek(pos);
                        id = raf2.readInt();
                        for (int i = 0; i < nombre.length; i++) {
                            n = raf2.readChar();
                            nombre[i] = n;

                        }
                        Mlibros = raf2.readInt();


                        if (id == numero) {
                            if (Mlibros == 3) {
                                System.out.println("ya no se puede alquilar más libros");
                                Main.menu();
                            } else {
                                //añadir un numero a libros alquilados

                                String name = new String(nombre);

                                StringBuffer names = new StringBuffer(name);
                                names.setLength(20);
                                raf2.seek(pos);

                                raf2.writeInt(id);
                                raf2.writeChars(names.toString());
                                raf2.writeInt(Mlibros + 1);
                                seguir = true;
                                break;//sale  y edita el libro para que ponga alquilado
                            }

                        }


                        if (raf2.getFilePointer() == raf2.length()) {
                            break;
                        } else {
                            pos += 48;
                        }


                    }
                } catch (IOException e) {


                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                } catch (TransformerException e) {
                    throw new RuntimeException(e);
                }


            } else {
                System.out.println("este usuario no existe");
            }

        } while (!seguir);


        //alquilar el libro

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


    }//CAMBIA LOS LIBROS DE ESTADO Y SUMA UN VALOR AL ALQUILER DEL CLIENTE

    public static void editar(BufferedReader reader) throws IOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(".//Libros.dat", "r");
        } catch (FileNotFoundException e) {
            System.out.println("error con el fichero");
        }
        int idB = 0;
        boolean seguir = false;//sirve para controlar el bucle

        int poss = 0;
        int idd = 0;

        //coger el ultimo id asi se puede saber si el id que elije el usuario esta dentro del rango
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
        Libro.TodosLibros();

        do {
            try {
                idB = Integer.parseInt(reader.readLine());
                if (idB > 0 && idB <= idd) {
                    seguir = true;//si el id esta en un rango valido se seguira adelante


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

        String nombre;
        do {
            System.out.println("Escribe su nuevo nombre");
            nombre = reader.readLine();
            if (nombre.length() < 2) {
                System.out.println("escribe un nombre de minimo 2 caracteres");
            } else {
                seguir = true;//si el nombre cumple los requisitos de caracter seguira adelante
            }
        } while (!seguir);

        seguir = false;

        String estadoN;
        do {
            System.out.println("Escribe su nuevo estado (disponible,alquilado,descontinuado)");
            estadoN = reader.readLine().toLowerCase();

            if (estadoN.equals("disponible") || estadoN.equals("alquilado") || estadoN.equals("discontinuado")) {
                seguir = true;//si el estado escrito es valido seguira adelante

            } else {
                System.out.println("esto no sirve, intentalo de nuevo");
            }

        } while (!seguir);


        //editar libro seleccionado
        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");

        char[] nombres = new char[20];
        char a;
        int pos = 0, id;
        //buscar el id especifico
        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < nombres.length; i++) {
                    a = raf.readChar();
                    nombres[i] = a;


                }

                //cuando se encuentra se hace la modificacion
                if (id == idB) {
                    fichero.seek(pos);//ojo con el indice del fichero en el que se quiere editar, el de raf seek no aplica
                    StringBuffer buff2;
                    StringBuffer buff = new StringBuffer(nombre);
                    buff.setLength(20);//si no se ponen se juntaran todos los valores

                    buff2 = new StringBuffer(estadoN);
                    buff2.setLength(20);

                    fichero.writeInt(id);
                    fichero.writeChars(buff.toString());
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
    }// MODIFICA EL ESTADO Y NOMBRE DEL LIBRO

    public static void eliminar(BufferedReader reader) throws IOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(".//Libros.dat", "r");
        } catch (FileNotFoundException e) {
            System.out.println("error con el fichero");
        }
        boolean seguir = false;

        int idB = 0;//id que da el usuario
        String estadoN = "discontinuado";//valor que se le pasara al libro para marcarlo como descontinuado

        int idd = 0;
        int poss = 0;


        //coger el ultimo id asi se puede saber si el id que elije el usuario esta dentro del rango
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

        System.out.println("Elige el ID del libro que quieras discontinuar");
        Libro.TodosLibros();

        //hasta que no meta el id correcto no saldra
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

                    System.out.println("se dio de baja un libro, ya no se podra alquilar");


                }


                if (raf.getFilePointer() == raf.length()) {
                    break;
                } else {
                    pos += 84;
                }


            }
        } catch (
                IOException e) {

        }
    }//MARCA COMO DESCONTINUADO UN LIBRO

    public static void crear(BufferedReader reader) throws IOException, ParserConfigurationException, TransformerException {

        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(".//Libros.dat", "r");
        } catch (FileNotFoundException e) {
            System.out.println("error con el fichero");
        }

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
        } catch (IOException e) {}

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
        Main.menu();// vuelve a mostrar el menu


    }//SE CREA UN LIBRO

    public static void devolver(BufferedReader reader) throws IOException {
        boolean fin = false;

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(".//Libros.dat", "r");
        } catch (FileNotFoundException e) {
            System.out.println("error con el fichero");
        }

        int idB = 0;
        boolean seguir = false;
        String estadoN = "disponible";


        boolean proceder = false;

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
            System.out.println("Elige el ID del libro que quieras devolver(0 para salir)");
            librosPrestados();

            try {
                idB = Integer.parseInt(reader.readLine());

                //si no hay libros para devolver o no se quiere seguir
                if (idB ==0){
                    fin = true;
                    seguir = true;
                }

                if (idB > 0 && idB <= idd) {
                    //si el libro esta en estado alquilado no debe permitir que se alquile, de entrada no se ve

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


        if (!fin) {
            seguir = false;

            //ver que el usuario no tenga ya 3 libros alquilados, y que exista
            RandomAccessFile raf2 = new RandomAccessFile(".//Clientes.dat", "rw");

            do {
                System.out.println("escribe el ID de tu usuario para proceder a devolver el libro");
                int numero = Integer.parseInt(reader.readLine());
                if (Cliente.existe(numero)) {
                    //ver que no exceda el limite
                    int pos = 0;
                    int id;
                    int Mlibros;

                    char[] nombre = new char[20];
                    char n;


                    try {
                        while (true) {
                            raf2.seek(pos);
                            id = raf2.readInt();
                            for (int i = 0; i < nombre.length; i++) {
                                n = raf2.readChar();
                                nombre[i] = n;

                            }
                            Mlibros = raf2.readInt();


                            if (id == numero) {
                                if (Mlibros == 0) {
                                    System.out.println("Este usuario no tiene ningun libro alquilado");
                                    break;
                                } else {
                                    //quitar un número

                                    String name = new String(nombre);

                                    StringBuffer names = new StringBuffer(name);
                                    names.setLength(20);
                                    raf2.seek(pos);

                                    raf2.writeInt(id);
                                    raf2.writeChars(names.toString());
                                    raf2.writeInt(Mlibros - 1);


                                    proceder= true;

                                    break;//sale  y edita el libro para que ponga alquilado}

                                }

                            }
                            if (raf2.getFilePointer() == raf2.length()) {
                                break;
                            } else {
                                pos += 48;
                            }


                        }


                        if(proceder){
                            //alquilar el libro

                            RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");


                            char[] apellidos = new char[20];
                            char a;
                            pos = 0;
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
                                        System.out.println("se devolvio el libro libro");

                                        seguir= true;
                                        break;


                                    }


                                    if (raf.getFilePointer() == raf.length()) {
                                        seguir=true;
                                        break;
                                    } else {
                                        pos += 84;
                                    }


                                }
                            } catch (
                                    IOException e) {

                            }

                        } else {
                            System.out.println("este usuario no existe");
                            seguir=true;
                        }

                    }catch (IOException e) {

                    }


                }
            }while (!seguir);
        }
    }//EDITA EL ESTADO DE UN LIBRO Y RESTA UN ALQUILER AL USUARIO

    public static void librosPrestados() throws FileNotFoundException {

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(".//Libros.dat", "r");
        } catch (FileNotFoundException e) {
            System.out.println("error con el fichero");
        }

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

                if (!estado2.trim().equals("discontinuado") && !estado2.trim().equals("disponible")) {
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
            System.out.println("Ya se han mostrado todos los libros alquilados");
        }


    }//MUESTRA LOS LIBROS ALQUILADOS




}
