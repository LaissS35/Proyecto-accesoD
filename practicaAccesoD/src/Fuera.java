public class Fuera {
    int id;
    String nombre;
    String causa;
    //constructor

    public Fuera(int id, String nombre, String causa) {
        this.id = id;
        this.nombre = nombre;
        this.causa = causa;
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

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }
}
