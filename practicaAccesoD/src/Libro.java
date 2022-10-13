public class Libro {
    int id;
    String nombre;
    String estado;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    //constructor

    public Libro() {
    }

    public Libro(int id, String nombre, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }
}
