public class Cliente {
    public String nombre;
    public int edad;
    public int numCortes;

    public Cliente(String nombre, int edad, int numCortes) {
        this.nombre = nombre;
        this.edad = edad;
        this.numCortes = numCortes;
    }

    public static Cliente fromString(String str) {
        String[] parts = str.split(",");
        String nombre = parts[0];
        int edad = Integer.parseInt(parts[1]);
        int numCortes = Integer.parseInt(parts[2]);
        return new Cliente(nombre, edad, numCortes);
    }

    @Override
    public String toString() {
        return nombre + "," + edad + "," + numCortes;
    }
}
