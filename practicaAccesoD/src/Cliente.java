import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
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

    } //funciona

    public static void Acliente(BufferedReader reader) throws IOException {
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

    } //funciona

    public static void EliCliente(BufferedReader reader) throws IOException {
        System.out.println("mira el id:");
        ListaClientes();

        System.out.println("escribe el id del cliente que quieres eliminar");
        int numero = Integer.parseInt(reader.readLine());

        if (Libro.existe(numero)) {

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


                    if (id == numero) {
                        System.out.println("se lo salta");


                    } else {
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


            //eliminar clientes funciona, se le pasa el id


        } else {
            System.out.println("no existe");
        }

    }//eliminar correctamente el cliente

    public static void eliminarC(int numero) throws IOException {


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


                if (id == numero) {
                    System.out.println("se lo salta");


                } else {
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


    }//eliminar clientes funciona, se le pasa el id
}
