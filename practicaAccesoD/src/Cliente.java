public class Cliente {
    int id;
    String nombre;
    int libroPrestado;//maximo 3 libros a la vez

   //constructor

    public Cliente(int id, String nombre, int libroPrestado) {
        this.id = id;
        this.nombre = nombre;
        this.libroPrestado = libroPrestado;
    }


    //get y set

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLibroPrestado() {
        return libroPrestado;
    }

    public void setLibroPrestado(int libroPrestado) {
        this.libroPrestado = libroPrestado;
    }
}
