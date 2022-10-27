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

    public static void menu() throws IOException, ParserConfigurationException, TransformerException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean avance = false;

        int opcion = 0;

        do {
            System.out.println("Bienvenido a la biblioteca ¿que vas a hacer hoy?\n 1.-registrar nuevo libro\n2.-dar de baja un libro\n3.-modificar libro\n4.-tomar prestado libro\n5.-Lista de todos los libros" +
                    "\n6.-lista de betados\n7.-lista libros prestados\n8.-devolver libro\n9.-Lista Clientes\n10.-añadir cliente\n11.-Eliminar cliente\n12.-Crear XML\n13.-Saliste!");
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
        if (opcion == 1) {
            Libro.crear(reader);
            menu();
        }
        if (opcion == 2) {
           Libro. eliminar(reader);
            menu();
        }
        if (opcion == 3) {
           Libro. editar(reader);
            menu();
        }
        if (opcion == 4) {
          Libro.  prestar(reader);
            menu();
        }
        if (opcion == 5) {
            Libro.TodosLibros();
            menu();
        }
        if (opcion == 6) {
            listaNegra(reader);

        }
        if (opcion == 7) {
            Libro.librosPrestados();
            menu();
        }
        if (opcion == 8) {
           Libro. devolver(reader);
            menu();
        }
        if (opcion == 9) {
           Cliente. ListaClientes();
            menu();
        }
        if (opcion == 10) {
           Cliente. Acliente(reader);
            menu();
        }
        if (opcion == 11) {
            Cliente.EliCliente(reader);
            menu();
        }
        if (opcion == 12) {
            System.out.println("Saliste ;)");
        }
        if(opcion ==13){
            crearXMLlibro();
            crearXMLcliente();
            menu();

        }
    }//menu principal del usuario


    //MENU LISTA NEGRA
    private static void listaNegra(BufferedReader reader) throws IOException {
        boolean pasar = false;
        int opcion;
        do {
            try {

                System.out.println("¿que quieres hacer?:\n1.-ver lista\n2.-añadir a la lista\n3.-volver al menú");
                opcion = Integer.parseInt(reader.readLine());

                if (opcion > 0 && opcion <= 3) {
                    pasar = true;
                } else {
                    System.out.println("mete un valor valido");
                }


                if (opcion == 1) {
                    leerLista();
                    listaNegra(reader);
                }
                if (opcion == 2) {
                    nuevoLN(reader);
                    listaNegra(reader);

                }
                if (opcion == 3) {
                    menu();
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


    }//fin?

    private static void nuevoLN(BufferedReader reader) throws IOException {
        System.out.println("lista de clientes:");
        Cliente.ListaClientes();


        int nombre;
        do {
            System.out.println("escribe el id");
            nombre = Integer.parseInt(reader.readLine());//id

            if (Libro.existe(nombre)) {
                System.out.println("causas:");
                String causa = reader.readLine();


                //coger nombre
                RandomAccessFile ficheroC = new RandomAccessFile(".//Clientes.dat", "rw");

                char[] nombres = new char[20];
                char a;
                int LibrosM;
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


                } catch (
                        IOException e) {

                }

                //eliminar el usuario de la lista original


            } else {
                System.out.println("este cliente no existe");
            }

        } while (!Libro.existe(nombre));

        Cliente.eliminarC(nombre);

    }//guarda el nuevo cliente y lo elimina de la lista original

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
        } else {
            System.out.println("la lista esta vacia");
        }

    }//funciona

    //CREAR XML
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

        int pos= 0,id;
        char[] nombres = new char[20];
        char a;



        try {
            while(true){
                fichero.seek(pos);
                id = fichero.readInt();
                for (int i = 0; i < nombres.length; i++) {
                    a = fichero.readChar();
                    nombres[i] =a;

                }


                String nombre = new String(nombres);


                if (id < 0){
                    System.out.println("ese número no sirve");

                }else{

                    //se guardan los clientes registrados en la biblioteca

                    CrearElemento("ID", String.valueOf(id), raiz, document);
                    CrearElemento("Nombre",nombre.trim(), raiz, document);


                }

                if(fichero.getFilePointer()== fichero.length()){
                    break;
                }else{
                    pos += 48;
                }


            }
        } catch (IOException e) {}



        //creacion fichero XML

        Source source = new DOMSource(document);
        // Se crea el resultado en el fichero Clientes.xml
        Result result = new StreamResult(new java.io.File("Clientes.xml"));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        transformer.transform(source, result);

        Result console= new StreamResult(System.out);
        transformer.transform(source, console);
    }

    private static void crearXMLlibro() throws ParserConfigurationException, FileNotFoundException, TransformerException {
        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");


        // DOM

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, "Libros", null);
        document.setXmlVersion("1.0");


        //Crea y añade el nodo empleado al documento
        Element raiz = document.createElement("Libro"); //nodo empleado
        document.getDocumentElement().appendChild(raiz); //lo añade a la raíz del documento


        //añadir los datos para el xml

        int pos= 0,id;
        char[] apellidos = new char[20];
        char a;

        char[] estados= new char[20];
        char est;


        try {
            while(true){
                fichero.seek(pos);
                id = fichero.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = fichero.readChar();
                    apellidos[i] =a;

                }
                for (int i = 0; i < estados.length; i++) {
                    est = fichero.readChar();
                    estados[i] = est;

                }

                String apellidos2 = new String(apellidos);
                String estado= new String ( estados);

                if (id < 0){
                    System.out.println("ese número no sirve");

                }else{

                   //se guardan los libros que la biblioteca virtual tiene

                    CrearElemento("ID", String.valueOf(id), raiz, document);
                    CrearElemento("Nombre",apellidos2.trim(), raiz, document);


                }

                if(fichero.getFilePointer()== fichero.length()){
                    break;
                }else{
                    pos += 84;
                }


            }
        } catch (
                IOException e) {
            System.out.println("fin");
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
        Result console= new StreamResult(System.out);
        transformer.transform(source, console);
    }//Crea XML de los libros

    static void CrearElemento(String datos, String valor, Element raiz, Document document) {
        Element elem = document.createElement(datos); //creamos hijo
        Text text = document.createTextNode(valor); //damos valor
        raiz.appendChild(elem); //pegamos el elemento hijo a la raiz
        elem.appendChild(text); //pegamos el valor
    }


}
