package pizzeria.model;

import java.util.ArrayList;

public class Producto{
    private static int contadorId = 1;
    
    private int ID;
    private String nombre;
    private String descripcion;
    private double precio;
    private ArrayList <Integer> ingredientes;
    
    public Producto(String nombre, String descripcion, double precio){
        this.ID = contadorId++;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.ingredientes = new ArrayList<>();
    }
    
    public Producto(int ID, String nombre, String descripcion, double precio){
        this.ID = ID;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.ingredientes = new ArrayList<>();
        
        if(ID >= contadorId){
            contadorId = ID + 1;
        }
    }
    
    public static void ajustarContador(int maxIdCargado){
        if(maxIdCargado >= contadorId){
            contadorId = maxIdCargado + 1;
        }
    }
    
    public static int getContadorId(){
        return contadorId;
    }
    
    public static void resetearContador(){
        contadorId = 1;
    }
    
    public int getID(){
        return ID;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public double getPrecio(){
        return precio;
    }
    
    public ArrayList<Integer> getIngredientes(){
        return ingredientes;
    }
    
    public void setID(int id){
        this.ID = id;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setDescripcion(String descrip){
        descripcion = descrip;
    }
    
    public void setPrecio(double precio){
        this.precio = precio;
    }
    
    public void agregarIngrediente(int i){
        ingredientes.add(i); 
    }
    
    public void eliminarIngrediente(int id){
        for(int i = 0; i < ingredientes.size(); i++){
            if(ingredientes.get(i) == id){
                ingredientes.remove(i);
                break;
            }
        }
    }
    
    public String verProducto(){
        String res = "ID: "+ ID + " -- " + nombre + "\n" + descripcion + "\n" + precio + " Bs.\n";        
        return res;
    }
}