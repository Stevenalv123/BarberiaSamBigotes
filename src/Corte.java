public class Corte extends Cliente {
    public String nombreBarbero;
    public String fechaCorte;
    public double costoCorte;

    public Corte(String nombre, int edad, int numCortes, String nombreBarbero, String fechaCorte, double costoCorte) {
        super(nombre, edad, numCortes);
        this.nombreBarbero = nombreBarbero;
        this.fechaCorte = fechaCorte;
        this.costoCorte = costoCorte;
    }

    public void calcularCostoCorte() {
        if (numCortes == 7) {
            costoCorte = 0;
            System.out.println("El total a pagar es de: " + costoCorte);
        } else if (edad > 14 && numCortes != 7) {
            costoCorte = 150;
            System.out.println("El total a pagar es de: " + costoCorte);
        } else {
            costoCorte = 140;
            System.out.println("El total a pagar es de: " + costoCorte);
        }
    }

    public void aumentarNumCortes() {
        if (numCortes < 7) {
            numCortes++;
        } else {
            numCortes = 0;
        }
    }

    public void mostrarCorte() {
        System.out.println("");
        System.out.println("Nombre del cliente: " + nombre);
        System.out.println("Atendido por: " + nombreBarbero);
        System.out.println("Fecha: " + fechaCorte);
        System.out.println("Costo del corte: C$" + costoCorte);
        System.out.println("==================================================");
    }

    @Override
    public String toString() {
        return super.toString() + "," + nombreBarbero + "," + fechaCorte + "," + costoCorte;
    }

    public static Corte fromString(String str) {
        String[] parts = str.split(",");
        try {
            String nombre = parts[0];
            int edad = Integer.parseInt(parts[1]);
            int numCortes = Integer.parseInt(parts[2]);
            String nombreBarbero = parts[3];
            String fechaCorte = parts[4];
            double costoCorte = Double.parseDouble(parts[5]);
            return new Corte(nombre, edad, numCortes, nombreBarbero, fechaCorte, costoCorte);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al parsear un número de la cadena de entrada: " + str, e);
        }
    }
}
