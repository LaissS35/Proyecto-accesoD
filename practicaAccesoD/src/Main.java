import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //datos de biblioteca, se generar 5 libros aleatorios, se pueden añadir más ,eliminar y editar
        //fichero con todos los libros de la biblioteca,fichero con liros prestados + quien lo presta y lista de persoonas a las que no prestar libros, si se mete ese nombre no se permitira que se preste
        //XML de todos los libros en la biblioteca

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean avance = false;


        int opcion =0;
        do {
            System.out.println("Bienvenido a la biblioteca ¿que vas a hacer hoy?\n 1.-registrar nuevo libro\n2.-eliminar libro\n3.-modificar libro\n4.-tomar prestado libro\n5.-Lista de todos los libros" +
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

        menu(opcion,reader);
    }


    private static void menu(int opcion,BufferedReader reader) throws IOException {
        if (opcion == 1){
            crear(reader);
        }
        if(opcion ==2){
            eliminar();
        }
        if(opcion ==3){
            editar();
        }
        if(opcion==4){
            prestar();
        }
        if(opcion ==5){
            TodosLibros();
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

    private static void TodosLibros() {
    }

    private static void prestar() {
    }

    private static void editar() {
    }

    private static void eliminar() {
    }

    private static void crear(BufferedReader reader) throws IOException {

        RandomAccessFile fichero = new RandomAccessFile(".//Libros.dat", "rw");
        RandomAccessFile raf = new RandomAccessFile(".//Libros.dat", "r");

        //posicionarnos al final de los datos
        int pos =0;
        int id =0;

        long tamanyo = raf.length();

        if(tamanyo == 0){
            System.out.println("entra");
            String libros ;
            String estado ="disponible";



            StringBuffer buff ;
            StringBuffer buff2;


            for (int j = 0; j < 5; j++) {
                libros = "Libros "+j;
                buff = new StringBuffer(libros);
                buff2 = new StringBuffer(estado);


                buff.setLength(20);
                buff2.setLength(20);



                fichero.writeInt(j+1);//id automatico
                fichero.writeChars(buff.toString());//nombre
                fichero.writeChars(buff2.toString());//estado



            }

        }
        //leer

        char[] apellidos = new char[20];
        char a;
        char b;
        char[] estado = new char[20];
        double salarios;





        try {
            while(true){
                raf.seek(pos);
                id = raf.readInt();
                for (int i = 0; i < apellidos.length; i++) {
                    a = raf.readChar();
                    apellidos[i] =a;

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
            System.out.println("fin");
        }

/*
            raf.seek(tamanyo-80);
             id = raf.readInt();





        fichero.close();
        raf.close();
*/

       // raf.seek(tamanyo);

       // System.out.println("numero de id nuevo: "+id);
        /*

        //recoger nuevos datos

        System.out.println("Escribe el nombre del nuevo empleado");

        String nombre = reader.readLine();

        System.out.println("Número de departamento");
        int departamentoN = Integer.parseInt(reader.readLine());

        System.out.println("Salario del nuevo empleado");
        double sala = Double.parseDouble(reader.readLine());

        //insertar nuevos datos

        StringBuffer buff2;

        buff2 = new StringBuffer(nombre);
        buff2.setLength(20);

        fichero.writeInt( id+1);
        fichero.writeChars(buff2.toString());
        fichero.writeInt(departamentoN);
        fichero.writeDouble(sala);*/


    }
}