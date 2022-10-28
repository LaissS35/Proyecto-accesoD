import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException {

        llenar();

        menu();
    }


    //------FUNCIONES------
    public static void menu() throws IOException, ParserConfigurationException, TransformerException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean avance = false;

        int opcion = 0;

        do {
            System.out.println("Bienvenido a la biblioteca ¿que vas a hacer hoy?\n1.-registrar nuevo libro\n2.-dar de baja un libro\n3.-modificar libro\n4.-tomar prestado libro\n5.-Lista de todos los libros" +
                    "\n6.-lista de expulsados\n7.-lista libros prestados\n8.-devolver libro\n9.-Lista Clientes\n10.-añadir cliente\n11.-Eliminar cliente\n12.-Crear XML\n13.-Saliste!");
            try {
                opcion = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Debes meter un número");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (opcion > 0 && opcion <= 13) {
                avance = true;
            } else {
                System.out.println("Mete un valor valido");
            }
        } while (!avance);

        switch (opcion) {
            case 1 -> Libro.crear(reader);
            case 2 -> {
                Libro.eliminar(reader);
                menu();
            }
            case 3 -> {
                Libro.editar(reader);
                menu();
            }
            case 4 -> {
                Libro.prestar(reader);
                menu();
            }
            case 5 -> {
                Libro.TodosLibros();
                menu();
            }
            case 6 -> listaNegra(reader);
            case 7 -> {
                Libro.librosPrestados();
                menu();
            }
            case 8 -> {
                Libro.devolver(reader);
                menu();
            }
            case 9 -> {
                Cliente.ListaClientes();
                menu();
            }
            case 10 -> {
                Cliente.Acliente(reader);
                menu();
            }
            case 11 -> {
                Cliente.EliCliente(reader);
                menu();
            }
            case 12 -> {
                crearXMLlibro();
                crearXMLcliente();
                menu();
            }
            case 13 -> System.out.println("Saliste ;)");
            default -> System.out.println("esa opción no existe...?");
        }

    }//menu principal del usuario

    //MENU LISTA NEGRA
    private static void listaNegra(BufferedReader reader) throws IOException {
        boolean pasar = false;//CUANDO SEA TRUE DEJARA DE PEDIR UN VALOR CORRECTO AL USUARIO
        int opcion;
        do {
            try {

                System.out.println("¿que quieres hacer?:\n1.-ver lista\n2.-añadir a la lista\n3.-volver al menú");
                opcion = Integer.parseInt(reader.readLine());

                if (opcion > 0 && opcion <= 3) {
                    pasar = true; //SE VERIFICA QUE SIRVE
                } else {
                    System.out.println("mete un valor valido");
                }

                switch (opcion) {
                    case 1 -> {
                        leerLista();
                        listaNegra(reader);
                    }
                    case 2 -> {
                        nuevoLN(reader);
                        listaNegra(reader);
                    }
                    case 3 -> menu();
                    default -> System.out.println("esta opcion no existe...?");
                }

            } catch (NumberFormatException e) {
                System.out.println("mete un valor númerico");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }
        } while (!pasar);


    }//MENU LISTA NEGRA

    private static void nuevoLN(BufferedReader reader) throws IOException {
        System.out.println("lista de clientes:");
        Cliente.ListaClientes();


        int nombre;
        do {
            System.out.println("escribe el id");
            nombre = Integer.parseInt(reader.readLine());//id

            if (Cliente.existe(nombre)) {
                System.out.println("causas:");
                String causa = reader.readLine();


                //coger nombre
                RandomAccessFile ficheroC = new RandomAccessFile(".//Clientes.dat", "rw");

                char[] nombres = new char[20];
                char a;
                int pos = 0, id;


                try {
                    while (true) {
                        ficheroC.seek(pos);
                        id = ficheroC.readInt();
                        for (int i = 0; i < nombres.length; i++) {
                            a = ficheroC.readChar();
                            nombres[i] = a;

                        }


                        String nombres2 = new String(nombres);


                        if (id == nombre) {

                            RandomAccessFile fichero = new RandomAccessFile(".//ListaNegra.dat", "rw");


                            StringBuilder buff;
                            StringBuilder buff2;

                            buff = new StringBuilder(nombres2);//remodelar, para que encuentre el nombre en ese ID especifico

                            buff2 = new StringBuilder(causa);


                            buff.setLength(20);
                            buff2.setLength(20);

                            fichero.writeInt(nombre);
                            fichero.writeChars(buff.toString());//nombre
                            fichero.writeChars(buff2.toString());//causa

                            System.out.println("usuario vetado con exito");
                            break;

                        }

                        if (ficheroC.getFilePointer() == ficheroC.length()) {
                            break;
                        } else {
                            pos += 48;//para cliente
                        }


                    }


                } catch (IOException e) {
                }

                //eliminar el usuario de la lista original


            } else {
                System.out.println("este cliente no existe");
            }

        } while (!Cliente.existe(nombre));

        Cliente.eliminarC(nombre);

    }//GUARDA CLIENTE EXPULSADO Y LO ELIMINA DEL FICHERO ORIGINAL

    private static void leerLista() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(".//ListaNegra.dat", "rw");
        long tamanyo = raf.length();

        if (tamanyo > 0) {
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

                    System.out.println("ID: " + id + " nombre: " + apellidos2.trim() + " motivo: " + estado2.trim());


                    if (raf.getFilePointer() == raf.length()) {
                        break;
                    } else {
                        pos += 84;
                    }


                }
            } catch (
                    IOException e) {
                System.out.println("Ya se han mostrado todos los expulsados");
            }
        } else {
            System.out.println("la lista esta vacia");
        }

    }//MOSTRAR TODOS LOS EXPULSADOS

    //CREAR XML [CLIENTE / LIBRO]
    private static void crearXMLcliente() throws FileNotFoundException, ParserConfigurationException, TransformerException {
        RandomAccessFile fichero = new RandomAccessFile(".//Clientes.dat", "rw");
        // DOM

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, "Clientes", null);
        document.setXmlVersion("1.0");


        Element raiz = document.createElement("Cliente"); //nodo empleado
        document.getDocumentElement().appendChild(raiz); //lo añade a la raíz del documento


        //añadir los datos para el xml

        int pos = 0, id;
        char[] nombres = new char[20];
        char a;

        try {
            while (true) {
                fichero.seek(pos);
                id = fichero.readInt();
                for (int i = 0; i < nombres.length; i++) {
                    a = fichero.readChar();
                    nombres[i] = a;

                }
                String nombre = new String(nombres);


                //se guardan los clientes registrados en la biblioteca

                CrearElemento("ID", String.valueOf(id), raiz, document);
                CrearElemento("Nombre", nombre.trim(), raiz, document);


                if (fichero.getFilePointer() == fichero.length()) {
                    break;
                } else {
                    pos += 48;
                }


            }
        } catch (IOException e) {
        }


        //creacion fichero XML

        Source source = new DOMSource(document);
        // Se crea el resultado en el fichero Clientes.xml
        Result result = new StreamResult(new java.io.File("Clientes.xml"));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        transformer.transform(source, result);

        Result console = new StreamResult(System.out);
        transformer.transform(source, console);
    }

    private static void crearXMLlibro() throws ParserConfigurationException, FileNotFoundException, TransformerException {
        //DOM
        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, "Libros", null);
        document.setXmlVersion("1.0");

        //Crea y añade el nodo empleado al documento
        Element raiz = document.createElement("Libro"); //nodo empleado
        document.getDocumentElement().appendChild(raiz); //lo añade a la raíz del documento

        int pos = 0, id;
        char[] apellidos = new char[20];
        char a;

        //Coger datps de los ficheros
        try {
            while (true) {
                fichero.seek(pos);
                id = fichero.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = fichero.readChar();
                    apellidos[i] = a;

                }
                String apellidos2 = new String(apellidos);

                //se guardan los libros que la biblioteca virtual tiene
                CrearElemento("ID", String.valueOf(id), raiz, document);
                CrearElemento("Nombre", apellidos2.trim(), raiz, document);

                if (fichero.getFilePointer() == fichero.length()) {
                    break;
                } else {
                    pos += 84;
                }

            }
        } catch (IOException e) {
        }

        //creacion fichero XML

        //Se crea la fuente XML a partir del documento
        Source source = new DOMSource(document);
        // Se crea el resultado en el fichero Empleados.xml
        Result result = new StreamResult(new java.io.File("Libros.xml"));
        // Se obtiene un TransformerFactory
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        // Se realiza la transformación de documento a fichero
        transformer.transform(source, result);
        // Para mostrar el documento por pantalla podemos especificar como resultado el canal  system.out
        Result console = new StreamResult(System.out);
        transformer.transform(source, console);
    }

    static void CrearElemento(String datos, String valor, Element raiz, Document document) {
        Element elem = document.createElement(datos); //creamos hijo
        Text text = document.createTextNode(valor); //damos valor
        raiz.appendChild(elem); //pegamos el elemento hijo a la raiz
        elem.appendChild(text); //pegamos el valor
    }

    //LLENAR BIBLIOTECA CON 5 LIBROS
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

    }//si no hay datos se generan 5 libros


}
