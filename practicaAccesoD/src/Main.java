import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //datos de biblioteca, se generar 5 libros aleatorios, se pueden añadir más ,eliminar y editar
        //fichero con todos los libros de la biblioteca,fichero con liros prestados + quien lo presta y lista de persoonas a las que no prestar libros, si se mete ese nombre no se permitira que se preste
        //XML de todos los libros en la biblioteca

        llenar();

        menu();
    }

    private static void llenar() throws IOException {
        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");
        RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "r");


        long tamanyo = raf.length();
        //si no hay datos se crean automaticamente
        if(tamanyo == 0){

            String libros ;
            String estado ="disponible";



            StringBuilder buff ;
            StringBuilder buff2;


            for (int j = 0; j < 5; j++) {
                libros = "Libros "+j;
                buff = new StringBuilder(libros);
                buff2 = new StringBuilder(estado);


                buff.setLength(20);
                buff2.setLength(20);



                fichero.writeInt(j+1);//id automatico
                fichero.writeChars(buff.toString());//nombre
                fichero.writeChars(buff2.toString());//estado



            }

        }

    }


    private static void menu() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean avance = false;

        int opcion =0;

        do {
            System.out.println("Bienvenido a la biblioteca ¿que vas a hacer hoy?\n 1.-registrar nuevo libro\n2.-dar de baja un libro\n3.-modificar libro\n4.-tomar prestado libro\n5.-Lista de todos los libros" +
                    "\n6.-lista de betados\n7.-lista libros prestados\n8.-devolver libro\n9.-Fin!");
            try {
                opcion = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Debes meter un número");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (opcion>0 && opcion<=9){
                avance= true;
            }else {
                System.out.println("Mete un valor valido");
            }
        } while (!avance);
        if (opcion == 1){
            crear(reader);
            menu();
        }
        if(opcion ==2){
            eliminar(reader);
        }
        if(opcion ==3){
            editar();
        }
        if(opcion==4){
            prestar(reader);
        }
        if(opcion ==5){
            TodosLibros();
            menu();
        }
        if(opcion==6){
            listaNegra();
        }
        if(opcion==7){
            librosPrestados();
        }
        if(opcion==8){
            devolver();
        }
        if (opcion==9){
            System.out.println("Saliste con existo ;)");
        }
    }

    private static void devolver() {

    }

    private static void librosPrestados() {
    }

    private static void listaNegra() {
    }

    private static void TodosLibros() throws IOException {

        RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "r");

        char[] apellidos = new char[20];
        char a;
        char b;
        char[] estado = new char[20];
        int pos=0,id;


        try {
            while(true){
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = raf.readChar();
                    apellidos[i] =a;


                }
                for (int i = 0; i < estado.length; i++) {
                    b=raf.readChar();
                    estado[i]=b;
                }

                String apellidos2 = new String(apellidos);
                String estado2 = new String(estado);


                if (id < 0){
                    System.out.println("ese número no sirve");

                }else{

                        System.out.println("ID: "+id+" nombre: "+apellidos2.trim()+" estado: "+estado2.trim());




                }

                if(raf.getFilePointer()== raf.length()){
                    break;
                }else{
                    pos += 84;
                }


            }
        } catch (
                IOException e) {
            System.out.println("Ya se han mostrado todos los libros");
        }

    }//funciona, muestra la lista de todos los libros

    private static void prestar(BufferedReader reader) throws IOException {
        int idB=0;
        String estadoN= "alquilado";

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
        int pos=0,id;
         //buscar el id especifico
        try {
            while(true){
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = raf.readChar();
                    apellidos[i] =a;


                }


                String apellidos2 = new String(apellidos);


                //cuando se encuentra se hace la modificacion
                if(id == idB){
                    StringBuffer buff2;

                    buff2 = new StringBuffer(estadoN);
                    buff2.setLength(20);

                    fichero.writeInt( id);
                    fichero.writeChars(apellidos2);
                    fichero.writeChars(buff2.toString());



                }


                if(raf.getFilePointer()== raf.length()){
                    break;
                }else{
                    pos += 64;
                }


            }
        } catch (
                IOException e) {
            System.out.println("se presto libro");
        }


    }//funciona, marca el libro seleccionado como prestado

    private static void editar() {
    }

    private static void eliminar(BufferedReader reader) throws IOException {

        int idB=0;
        String estadoN= "descontinuado";

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
        int pos=0,id;
        //buscar el id especifico
        try {
            while(true){
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = raf.readChar();
                    apellidos[i] =a;


                }


                String apellidos2 = new String(apellidos);


                //cuando se encuentra se hace la modificacion
                if(id == idB){
                    System.out.println("dentro");
                    StringBuffer buff2;

                    buff2 = new StringBuffer(estadoN);
                    buff2.setLength(20);

                    fichero.writeInt( id);
                    fichero.writeChars(apellidos2);
                    fichero.writeChars(buff2.toString());



                }


                if(raf.getFilePointer()== raf.length()){
                    break;
                }else{
                    pos += 84;
                }


            }
        } catch (
                IOException e) {
            System.out.println("se dio de baja un libro, ya no se podra alquilar");
        }
    }

    private static void crear(BufferedReader reader) throws IOException {

        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");
        RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "r");

        //posicionarnos al final de los datos
        int pos =0;
        int id =0;


        //coger el ultimo id
        try {
            while(true){
                raf.seek(pos);
                id = raf.readInt();


                if(raf.getFilePointer()== raf.length()){
                    break;
                }else{
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

        } while (nombre.length()<2);


        String disponibilidad="disponible";


        //insertar nuevos datos
        long ids = fichero.length();
        fichero.seek(ids);//volverse a posicionar al final

        StringBuffer buff3;
        StringBuffer buff4;

        buff3 = new StringBuffer(nombre);
        buff3.setLength(20);

        buff4 = new StringBuffer(disponibilidad);
        buff4.setLength(20);

        fichero.writeInt( id+1);
        fichero.writeChars(buff3.toString());
        fichero.writeChars(buff4.toString());


        fichero.close();
        raf.close();

        System.out.println("Libro registrado con exito");
        menu();// vuelve a mostrar el menu



    }//funciona, se crea un nuevo registro de libro
}