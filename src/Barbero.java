public abstract class Barbero {

    public String nombreBarbero;
    public String turno;
    private double salario;
    private int numCortes;
    private double bonoPorCorte;

    public Barbero(String nombreBarbero, String turno) {
        this.nombreBarbero = nombreBarbero;
        this.turno = turno;
        this.salario = 0; 
        this.numCortes = 0; 
        this.bonoPorCorte=0;
    }
    public double getSalario() {
            return salario;
        }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getNumCortes() {
        return numCortes;
    }

    public void setNumCortes(int numCortes) {
        this.numCortes = numCortes;
    }

    public double getBonoporCorte(){
        return bonoPorCorte;
    }

    public void setBonoporCorte(double bonoPorCorte){
        this.bonoPorCorte=bonoPorCorte;
    }

    public abstract void calcularBono(double gananciaDia);
    public abstract void mostrarInformacion();

    public void incrementarCortes() {
        numCortes++;
    }
    

    
   
}
