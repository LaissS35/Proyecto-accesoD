
import java.io.*;

public class Cliente {


    //FUNCIONES CLIENTE
    public static void ListaClientes() throws IOException {
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


                    if (id != 0) {//AL ELIMINAR UN CLIENTE QUEDA UN ID 0 SIN DATOS, SE SALTARAN EN CASO DE SER PRESENTES
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

    } //VER TODOS LOS CLIENTES

    public static void Acliente(BufferedReader reader) throws IOException {
        int libros = 0;//NUMERO DE LIBROS ALQUILADOS SIEMPRE SERA 0 AL REGISTRAR UN NUEVO CLIENTE

        RandomAccessFile fichero = new RandomAccessFile(".//Clientes.dat", "rw");
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(".//Clientes.dat", "r");
        } catch (FileNotFoundException e) {
            System.out.println("error con el fichero");
        }

        long tamanyo = raf.length();


        if (tamanyo == 0) {//EN CASO NO NO HABER CLIENTES REGISTRADOS NO COGEMOS NINGUN ID FINAL
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
            } catch (IOException e) {}

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

    } //AÑADIR CLIENTE

    public static void EliCliente(BufferedReader reader) throws IOException {
        System.out.println("mira el id:");
        ListaClientes();

        System.out.println("escribe el id del cliente que quieres eliminar");
        int numero = Integer.parseInt(reader.readLine());

        if (existe(numero)) {

            //AQUI ESTAN 2 FICHEROS, EL ORGINAL Y EL TEMPORAL 'CLIENTES2'.
            RandomAccessFile raf = new RandomAccessFile(".//Clientes.dat", "rw");
            RandomAccessFile ficheroTemporal = new RandomAccessFile(".//Clientes2.dat", "rw");

            File ficheroBorrar = new File(".//Clientes.dat");


            char[] nombres = new char[20];
            char a;
            int Libro;

            int pos = 0, id = 0;
            StringBuilder buff1;


            //escrIbir en el fichero temporal los valores que se quieren
            try {
                while (true) {
                    raf.seek(pos);
                    id = raf.readInt();
                    for (int i = 0; i < nombres.length; i++) {
                        a = raf.readChar();
                        nombres[i] = a;

                    }
                    Libro = raf.readInt();


                    String apellidos2 = new String(nombres);


                    if (id != numero) {//SE ESCRIBIRAN TODOS LOS CLIENTES QUE NO TENGAN EL ID QUE QUEREMOS ELIMINAR EN EL FICHERO TEMPORAL
                        buff1 = new StringBuilder(apellidos2);


                        buff1.setLength(20);


                        ficheroTemporal.seek(pos);

                        ficheroTemporal.writeInt(id);
                        ficheroTemporal.writeChars(buff1.toString());
                        ficheroTemporal.writeInt(Libro);

                    }

                    if (raf.getFilePointer() == raf.length()) {
                        break;
                    } else {
                        pos += 48;
                    }


                }
            } catch (
                    IOException e) {
                System.out.println("Ya se termino de escribir en el fichero temporal");
            }


            ficheroBorrar.delete();//borra el fichero original

            //reescribir el fichero original
            RandomAccessFile fichero2 = new RandomAccessFile(".//Clientes2.dat", "rw");
            RandomAccessFile ficheroD = new RandomAccessFile(".//Clientes.dat", "rw");

            pos = 0;

            char[] nombre = new char[20];
            char aa;
            int Libro2;

            try {
                while (true) {
                    fichero2.seek(pos);
                    id = fichero2.readInt();
                    for (int i = 0; i < nombre.length; i++) {
                        aa = fichero2.readChar();
                        nombre[i] = aa;


                    }
                    Libro2 = fichero2.readInt();


                    String nombres2 = new String(nombre);


                    buff1 = new StringBuilder(nombres2);
                    buff1.setLength(20);


                    ficheroD.seek(pos);

                    ficheroD.writeInt(id);
                    ficheroD.writeChars(buff1.toString());
                    ficheroD.writeInt(Libro2);


                    if (fichero2.getFilePointer() == fichero2.length()) {
                        break;
                    } else {
                        pos += 48;
                    }


                }
            } catch (
                    IOException e) {
                System.out.println("Se termino de guardar los valores correctos");
            }


            //se borra el fichero temporal
            File limpiar = new File(".//Clientes2.dat");
            limpiar.delete();



        } else {
            //SI EL ID SELECCIONADO NO EXISTE SE TERMINARA
            System.out.println("no existe");
        }

    }//ELIMINAR CLIENTE

    public static void eliminarC(int numero) throws IOException {

        //LLAMADO POR LA LISTA NEGRA PARA ELIMINAR EL CLIENTE QUE SE QUIERA METER EN ELLA

        RandomAccessFile raf = new RandomAccessFile(".//Clientes.dat", "rw");
        RandomAccessFile ficheroTemporal = new RandomAccessFile(".//Clientes2.dat", "rw");

        File ficheroBorrar = new File(".//Clientes.dat");


        char[] nombres = new char[20];
        char a;
        int Libro;

        int pos = 0, id = 0;
        StringBuilder buff1;


        //escrbir en el fichero temporal los valores que se quieren
        try {
            while (true) {
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < nombres.length; i++) {
                    a = raf.readChar();
                    nombres[i] = a;

                }
                Libro = raf.readInt();


                String apellidos2 = new String(nombres);


                if (id != numero) {
                    buff1 = new StringBuilder(apellidos2);


                    buff1.setLength(20);


                    ficheroTemporal.seek(pos);

                    ficheroTemporal.writeInt(id);
                    ficheroTemporal.writeChars(buff1.toString());
                    ficheroTemporal.writeInt(Libro);


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

        char[] nombre = new char[20];
        char aa;
        int Libro2;

        try {
            while (true) {
                fichero2.seek(pos);
                id = fichero2.readInt();
                for (int i = 0; i < nombre.length; i++) {
                    aa = fichero2.readChar();
                    nombre[i] = aa;


                }
                Libro2 = fichero2.readInt();


                String nombres2 = new String(nombre);


                buff1 = new StringBuilder(nombres2);
                buff1.setLength(20);


                ficheroD.seek(pos);

                ficheroD.writeInt(id);
                ficheroD.writeChars(buff1.toString());
                ficheroD.writeInt(Libro2);


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


        //se borra el fichero sobrante
        File limpiar = new File(".//Clientes2.dat");
        limpiar.delete();


    }//SE USA EN LA LISTA NEGRA

    static boolean existe(int numero) throws FileNotFoundException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(".//Clientes.dat", "r");
        } catch (FileNotFoundException e) {
            System.out.println("error con el fichero");
        }

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

}
