public class Barberia {
    public String nombre;
    public String ubicacion;
    public String telefono;
    private double ganancia;

    public Barberia(String nombre, String ubicacion, String telefono, double ganancia) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.telefono=telefono;
        this.ganancia=ganancia;
    }

    public double getGanancia(){
        return ganancia;
    }
    public void setGanancia(double ganancia){
        this.ganancia=ganancia;
    }

    public void MostrarInfo(){
        System.out.print("\nBarberia "+nombre);
        System.out.println("                                                                            Telefono: "+telefono);
        System.out.println("Direccion: "+ubicacion);
    }
    
    
}
