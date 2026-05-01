package pizzeria.model;

import pizzeria.model.Insumo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedWriter;

public class Inventario{

    private static final String ARCHIVO = "resources/data/Insumos.txt";

    private ArrayList<Insumo> insumos    = new ArrayList<>();
    private int proximoId  = 1;

    public Insumo agregar(String nombre, String unidad, double stockActual, double stockMinimo, double precioCompra, double cantidadPorPizza){

        if(existeNombre(nombre)){
            return null; 
        }

        Insumo nuevo = new Insumo(
            proximoId++, nombre, unidad,
            stockActual, stockMinimo, precioCompra, cantidadPorPizza
        );
        insumos.add(nuevo);
        return nuevo;
    }

    public void agregarDesdeArchivo(Insumo ins){
        insumos.add(ins);
        if(ins.getId() >= proximoId){
            proximoId = ins.getId() + 1;
        }
    }

    public Insumo buscarId(int id){
        for(Insumo ins : insumos){
            if (ins.getId() == id){
                return ins;    
            }
        }
        return null;
    }

    public boolean existeNombre(String nombre){
        for(Insumo ins : insumos){
            if(ins.getNombre().equalsIgnoreCase(nombre.trim())){
                return true;    
            }
        }
        return false;
    }

    public ArrayList<Insumo> getInsumos(){
        return insumos;
    }

    
    public boolean modificarNombre(int id, String nuevoNombre){
        Insumo ins = buscarId(id);
        if (ins == null){
            return false;    
        }
        for(Insumo otro : insumos){
            if(otro.getId() != id && otro.getNombre().equalsIgnoreCase(nuevoNombre.trim())){
                return false;
            }
        }
        ins.setNombre(nuevoNombre.trim());
        return true;
    }

    public boolean modificarUnidad(int id, String nuevaUnidad){
        Insumo ins = buscarId(id);
        if (ins == null) return false;
        ins.setUnidad(nuevaUnidad);
        return true;
    }

    public boolean modificarStockMinimo(int id, double nuevoMin){
        Insumo ins = buscarId(id);
        if(ins == null){
            return false;
        }
        ins.setStockMinimo(nuevoMin);
        return true;
    }

    public boolean modificarPrecio(int id, double nuevoPrecio){
        Insumo ins = buscarId(id);
        if (ins == null){
            return false;
        }
        ins.setPrecioCompra(nuevoPrecio);
        return true;
    }

    public boolean modificarUsoPorPizza(int id, double nuevaCant){
        Insumo ins = buscarId(id);
        if (ins == null){
            return false;
        }
        ins.setCantidadPorPizza(nuevaCant);
        return true;
    }

    public void actualizarStock(int id, double cantidad, boolean sumar){
        Insumo ins = buscarId(id);
        if(ins == null){
            System.out.println(" [!] Insumo con ID " + id + " no encontrado.");
            return;
        }
        if(sumar){
            ins.setStockActual(ins.getStockActual() + cantidad);
        }else{
            if(ins.getStockActual() >= cantidad){
                ins.setStockActual(ins.getStockActual() - cantidad);
            }else{
                System.out.printf(" [!] Stock insuficiente para '%s' " +
                        "(disponible: %.3f, solicitado: %.3f).%n",
                        ins.getNombre(), ins.getStockActual(), cantidad);
            }
        }
    }

    public void descontarStock(int id, double cantidad){
        actualizarStock(id, cantidad, false);
    }

    public boolean eliminarInsumo(int id){
        Insumo ins = buscarId(id);
        if (ins == null){
            return false;
        }
        insumos.remove(ins);
        return true;
    }

    public void verTodo(){
        if(insumos.isEmpty()){
            System.out.println(" No hay insumos registrados.");
            return;
        }
        System.out.println();
        System.out.println(" " + "─".repeat(95));
        System.out.printf(" %-4s  %-18s  %-8s  %7s  %7s  %8s  %9s%n",
                "ID", "NOMBRE", "UNIDAD", "STOCK", "MIN", "PRECIO", "USO/PIZZA");
        System.out.println(" " + "─".repeat(95));
        for(Insumo ins : insumos){
            String alerta = ins.stockBajo() ? " ⚠" : "";
            System.out.printf(" %-4d  %-18s  %-8s  %7.3f  %7.3f  Bs.%6.2f  %9.3f%s%n",
                    ins.getId(), ins.getNombre(), ins.getUnidad(),
                    ins.getStockActual(), ins.getStockMinimo(),
                    ins.getPrecioCompra(), ins.getCantidadPorPizza(), alerta);
        }
        System.out.println(" " + "─".repeat(95));
        System.out.printf(" Total: %d insumo(s)%n", insumos.size());
    }

    public int verStockBajo(){
        List<Insumo> enAlerta = insumos.stream()
                .filter(Insumo::stockBajo)
                .collect(Collectors.toList());
        if(enAlerta.isEmpty()){
            System.out.println(" Todo el stock está sobre el mínimo requerido.");
            return 0;
        }

        System.out.println();
        System.out.println(" ┌─── INSUMOS CON STOCK BAJO ───────────────────────────────────────┐");
        for(Insumo ins : enAlerta){
            System.out.printf(" │  [%d] %-18s  Stock: %6.3f  /  Mín: %6.3f  %-5s │%n",
                    ins.getId(), ins.getNombre(),
                    ins.getStockActual(), ins.getStockMinimo(), ins.getUnidad());
        }
        System.out.println(" └───────────────────────────────────────────────────────────────────┘");
        return enAlerta.size();
    }

    public boolean alertaStockBajo(){
        long cantidad = insumos.stream().filter(Insumo::stockBajo).count();
        if(cantidad > 0){
            System.out.printf(" [!] ALERTA: %d insumo(s) con stock por debajo del mínimo.%n",
                    cantidad);
            insumos.stream()
                    .filter(Insumo::stockBajo)
                    .forEach(ins -> System.out.printf(
                            "     · %-18s  stock: %.3f  mín: %.3f%n",
                            ins.getNombre(), ins.getStockActual(), ins.getStockMinimo()));
            return true;
        }
        return false;
    }
    
    public void guardarArchivo(){
        guardarArchivo(ARCHIVO);
    }

    public void guardarArchivo(String nombreArchivo){
        try(BufferedWriter pw = new BufferedWriter(new FileWriter(nombreArchivo))){
            pw.write("# Sistema Pizzeria — Inventario de insumos");
            pw.newLine();
            pw.write("# Formato: id,nombre,unidad,stockActual,stockMinimo,precioCompra,cantPorPizza");
            pw.newLine();        
            for(Insumo ins : insumos){
                pw.write(ins.escribirTexto());
                pw.newLine();
            }
            System.out.printf(" [INFO] %d insumo(s) guardado(s) en %s%n",
                    insumos.size(), nombreArchivo);
        }catch(IOException e){
            System.out.println(" [ERROR] No se pudo guardar " + nombreArchivo +
                    ": " + e.getMessage());
        }
    }

    public void cargarArchivo(){
        cargarArchivo(ARCHIVO);
    }

    public void cargarArchivo(String nombreArchivo){
        insumos.clear();
        proximoId = 1;

        try(BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))){
            String linea;
            int numLinea = 0;
            while((linea = br.readLine()) != null){
                numLinea++;
                linea = linea.trim();
                if(linea.isEmpty() || linea.startsWith("#")){
                    continue;    
                }

                String[] p = linea.split(",");
                if(p.length != 7){
                    System.out.printf(" [ADVERTENCIA] Línea %d ignorada (campos incorrectos): %s%n",
                            numLinea, linea);
                    continue;
                }
                
                try{
                    int    id           = Integer.parseInt(p[0].trim());
                    String nombre       = p[1].trim().replace(",",".");
                    String unidad       = p[2].trim().replace(",",".");
                    double stockActual  = Double.parseDouble(p[3].trim().replace(",","."));
                    double stockMinimo  = Double.parseDouble(p[4].trim().replace(",","."));
                    double precio       = Double.parseDouble(p[5].trim().replace(",","."));
                    double cantPorPizza = Double.parseDouble(p[6].trim().replace(",","."));

                    agregarDesdeArchivo(new Insumo(id, nombre, unidad,
                            stockActual, stockMinimo, precio, cantPorPizza));
                }catch(NumberFormatException e){
                    System.out.printf(" [ADVERTENCIA] Línea %d ignorada (número inválido)%n",
                            numLinea);
                }
            }
            System.out.printf(" [INFO] %d insumo(s) cargado(s) desde %s%n",
                    insumos.size(), nombreArchivo);
        }catch(IOException e){
            System.out.println(" [INFO] No se encontró " + nombreArchivo +
                    ". Se iniciará con inventario vacío.");
        }
    }
}