import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    static String nombreBarberia = "SamBigotes";
    static String telefonoBarberia = "7603-1567";
    static String direcBarberia = "Del semaforo del Rigoberto Lopez Perez, 1C al sur, Costado Oeste";
    static Scanner sc = new Scanner(System.in);
    static int edadCliente, numCortesCliente = 0;
    static ArrayList<Cliente> listaClientes = new ArrayList<>();
    static ArrayList<Barbero> listaBarberos = new ArrayList<>();
    static ArrayList<Corte> listaCortes = new ArrayList<>();
    static String fechaCorte, nomCliente;
    static double gananciaDia;
    static LocalDate hoy = LocalDate.now();
    static DateTimeFormatter formatofecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        // Verificar y crear los archivos si no existen
        verificarYCrearArchivo("barberos.txt");
        verificarYCrearArchivo("clientes.txt");
        verificarYCrearArchivo("cortes.txt");

        // Leer registros existentes
        LeerBarbero();
        LeerCliente();
        LeerRegistro();

        Barberia barberia = new Barberia(nombreBarberia, direcBarberia, telefonoBarberia, gananciaDia);
        barberia.MostrarInfo();

        // Inicializar barberos si la lista está vacía
        if (listaBarberos.isEmpty()) {
            inicializarBarberos();
        }

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n<----------Menu---------->");
            System.out.println("1. Registrar corte");
            System.out.println("2. Mostrar registro");
            System.out.println("3. Salarios");
            System.out.println("4. Salir");
            System.out.print("Ingrese una opcion: ");
            int opt = sc.nextInt();
            sc.nextLine(); //Limpiar el buffer
            switch (opt) {
                case 1:
                    RegistrarCorte();
                    break;
                case 2:
                    MostrarRegistro();
                    break;
                case 3:
                    mostrarSalarios();
                    break;
                case 4:
                    System.out.println("Cerrando programa....");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opcion invalida......");
                    break;
            }
        }
    }

    private static void verificarYCrearArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Error al crear el archivo " + nombreArchivo + ": " + e.getMessage());
            }
        }
    }

    private static void inicializarBarberos() {
        BarberoTurnoRegular barberoTurnoRegular1 = new BarberoTurnoRegular("Ronald");
        listaBarberos.add(barberoTurnoRegular1);
        BarberoTurnoRegular barberoTurnoRegular2 = new BarberoTurnoRegular("Jose");
        listaBarberos.add(barberoTurnoRegular2);
        BarberoTurnoRegular barberoTurnoRegular3 = new BarberoTurnoRegular("Bryan");
        listaBarberos.add(barberoTurnoRegular3);
        BarberoTurnoRegular barberoTurnoRegular4 = new BarberoTurnoRegular("Ricardo");
        listaBarberos.add(barberoTurnoRegular4);
        BarberoTurnoRegular barberoTurnoRegular5 = new BarberoTurnoRegular("Marvin");
        listaBarberos.add(barberoTurnoRegular5);
        BarberoTurnoIrregular barberoTurnoIrregular1 = new BarberoTurnoIrregular("Mario");
        listaBarberos.add(barberoTurnoIrregular1);
        BarberoTurnoIrregular barberoTurnoIrregular2 = new BarberoTurnoIrregular("Luis");
        listaBarberos.add(barberoTurnoIrregular2);
        BarberoTurnoIrregular barberoTurnoIrregular3 = new BarberoTurnoIrregular("Joshua");
        listaBarberos.add(barberoTurnoIrregular3);
        BarberoTurnoIrregular barberoTurnoIrregular4 = new BarberoTurnoIrregular("Steven");
        listaBarberos.add(barberoTurnoIrregular4);
        BarberoTurnoIrregular barberoTurnoIrregular5 = new BarberoTurnoIrregular("Cristian");
        listaBarberos.add(barberoTurnoIrregular5);

        for (Barbero barbero : listaBarberos) {
            EscribirBarbero(barbero);
        }
    }

    public static void RegistrarCorte() {
        System.out.print("Ingrese el nombre del cliente: ");
        nomCliente = sc.nextLine();
        Cliente clienteExistente = null;
        
        // Verificar si el cliente ya existe en la lista
        for (Cliente cliente : listaClientes) {
            if (nomCliente.equalsIgnoreCase(cliente.nombre)) {
                clienteExistente = cliente;
                break;
            }
        }
    
        if (clienteExistente != null) {
            // Si el cliente existe, procedemos con el registro del corte
            boolean continuar = true;
            while (continuar) {
                System.out.print("Ingrese el nombre del barbero: ");
                String nomBarbero = sc.nextLine();
                
                // Buscar el barbero en la lista de barberos
                for (Barbero barbero : listaBarberos) {
                    if (nomBarbero.equalsIgnoreCase(barbero.nombreBarbero)) {
                        String fechaCorte = hoy.format(formatofecha);
                        
                        // Crear el objeto Corte utilizando los datos del cliente existente
                        Corte corte = new Corte(nomCliente, clienteExistente.edad, clienteExistente.numCortes, nomBarbero, fechaCorte, 0);
                        corte.calcularCostoCorte();
                        listaCortes.add(corte);
                        corte.aumentarNumCortes();
                        clienteExistente.numCortes = corte.numCortes;
                        barbero.incrementarCortes();
                        gananciaDia = SumarVentas();
                        barbero.calcularBono(gananciaDia);
                        actualizarBarbero(barbero); // Actualizar el barbero en el archivo
                        EscribirRegistro(corte); // Guardar el registro del corte en el archivo
                        continuar = false;
                        break;
                    }
                }
            }
        } else {
            // Si el cliente no existe, solicitamos la edad y procedemos con el registro del cliente y corte
            System.out.print("Ingrese la edad del cliente: ");
            try {
                edadCliente = sc.nextInt();
                sc.nextLine(); // Limpiar el buffer del scanner
            } catch (Exception e) {
                System.out.println("Error: Edad inválida.");
                sc.nextLine();
                return;
            }
    
            boolean continuar = true;
            while (continuar) {
                System.out.print("Ingrese el nombre del barbero: ");
                String nomBarbero = sc.nextLine();
                
                // Buscar el barbero en la lista de barberos
                for (Barbero barbero : listaBarberos) {
                    if (nomBarbero.equalsIgnoreCase(barbero.nombreBarbero)) {
                        String fechaCorte = hoy.format(formatofecha);
                        
                        // Crear el objeto Corte y Cliente nuevos
                        Corte corte = new Corte(nomCliente, edadCliente, 0, nomBarbero, fechaCorte, 0);
                        corte.calcularCostoCorte();
                        listaCortes.add(corte);
                        corte.aumentarNumCortes();
                        Cliente nuevoCliente = new Cliente(nomCliente, edadCliente, corte.numCortes);
                        listaClientes.add(nuevoCliente);
                        barbero.incrementarCortes();
                        gananciaDia = SumarVentas();
                        barbero.calcularBono(gananciaDia);
                        actualizarBarbero(barbero); // Actualizar el barbero en el archivo
                        EscribirCliente(nuevoCliente); // Guardar el nuevo cliente en el archivo
                        EscribirRegistro(corte); // Guardar el registro del corte en el archivo
                        continuar = false;
                        break;
                    }
                }
            }
        }
    }

    public static void MostrarRegistro() {
        for (Corte cort : listaCortes) {
            cort.mostrarCorte();
        }
        System.out.println("La venta total del dia es de: C$" + SumarVentas());
    }

    public static double SumarVentas() {
        gananciaDia = 0;
        for (Corte cort : listaCortes) {
            gananciaDia += cort.costoCorte;
        }
        return gananciaDia;
    }

    public static void mostrarSalarios() {
        for (Barbero barbero : listaBarberos) {
            barbero.mostrarInformacion();
        }
    }

    public static void EscribirRegistro(Corte corte) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("cortes.txt", true))) {
            bw.write(corte.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al escribir el registro: " + e.getMessage());
        }
    }
    

    public static void LeerRegistro() {
        try (BufferedReader br = new BufferedReader(new FileReader("cortes.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Corte corte = Corte.fromString(linea);
                listaCortes.add(corte);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el registro: " + e.getMessage());
        }
    }

    public static void EscribirCliente(Cliente cliente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("clientes.txt", true))) {
            bw.write(cliente.toString());
            bw.newLine();
        } catch (IOException e) {
            
        }
    }

    public static void LeerCliente() {
        try (BufferedReader br = new BufferedReader(new FileReader("clientes.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Cliente cliente = Cliente.fromString(linea);
                listaClientes.add(cliente);
            }
        } catch (IOException e) {
            System.out.println("Error al leer los clientes: " + e.getMessage());
        }
    }

    public static void EscribirBarbero(Barbero barbero) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("barberos.txt", true))) {
            bw.write(barbero.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al escribir el barbero: " + e.getMessage());
        }
    }
    

    public static void LeerBarbero() {
        try (BufferedReader br = new BufferedReader(new FileReader("barberos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Barbero barbero = null;
                if (linea.contains("Regular")) {
                    barbero = BarberoTurnoRegular.fromString(linea);
                } else if (linea.contains("Irregular")) {
                    barbero = BarberoTurnoIrregular.fromString(linea);
                }
                if (barbero != null) {
                    listaBarberos.add(barbero);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer los barberos: " + e.getMessage());
        }
    }
    

    public static void actualizarBarbero(Barbero barberoActualizado) {
        ArrayList<Barbero> nuevaListaBarberos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("barberos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Barbero barbero = null;
                if (linea.contains("Regular")) {
                    barbero = BarberoTurnoRegular.fromString(linea);
                } else if (linea.contains("Irregular")) {
                    barbero = BarberoTurnoIrregular.fromString(linea);
                }
                if (barbero != null) {
                    if (barbero.nombreBarbero.equalsIgnoreCase(barberoActualizado.nombreBarbero)) {
                        nuevaListaBarberos.add(barberoActualizado);
                    } else {
                        nuevaListaBarberos.add(barbero);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer los barberos: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("barberos.txt", false))) {
            for (Barbero barbero : nuevaListaBarberos) {
                bw.write(barbero.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar el barbero: " + e.getMessage());
        }
    }
}

