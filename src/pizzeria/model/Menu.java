package pizzeria.model;

import pizzeria.model.Combo;
import pizzeria.util.ArchivoMenu;
import pizzeria.model.Producto;
import java.util.ArrayList;
import pizzeria.model.Combo;
import pizzeria.model.Producto;

public class Menu{
    private ArrayList <Producto> productos;
    private ArrayList <Combo> combos;
    private int IdProducto = 1;
    private int NroCombo = 1;
    private ArchivoMenu archivoMenu;
    
    public Menu(ArrayList <Producto> pizzas, ArrayList <Combo> combos ){
        this.productos = pizzas;
        this.combos = combos;
    }
    
    public Menu(){
        productos = new ArrayList<>();
        combos = new ArrayList<>();
    }
    
    public void agregarProducto(String nombre, String descripcion, double precio) {
        int maxId = 0;
        for(Producto p : productos){
            if(p.getID() > maxId){
                maxId = p.getID();
            }
        }
        Producto p = new Producto(maxId + 1, nombre, descripcion, precio);
        productos.add(p);
    }
    
    public boolean eliminarProducto(int id){
        boolean res = false;
        for(int i = 0; i < productos.size(); i++){
            if(productos.get(i).getID() == id){
                productos.remove(i);
                for(Combo c : combos){
                    c.eliminarProducto(id);
                }
                res = true;
                break;
            }
        }
        return res;
    }
    
    public Producto buscarProducto(int id){
        for(Producto p : productos){
            if(p.getID() == id){
                return p;
            }
        }
        return null;
    }
    
     public ArrayList<Producto> getProductos(){
        return productos;
    }
    
    public boolean agregarIngredienteAProducto(int idProducto, int idIngrediente){
        Producto p = buscarProducto(idProducto);
        if(p != null){
            p.agregarIngrediente(idIngrediente);
            return true;
        }
        return false;
    }
    
    public void agregarCombo(ArrayList<Producto> productosCombo, double precio){
        Combo c = new Combo(precio, productosCombo);
        combos.add(c);
    }
    
    public boolean eliminarCombo(int nro){
        boolean res = false;
        for(int i = 0; i < combos.size(); i++){
            if(combos.get(i).getNroCombo() == nro){
                combos.remove(i);
                res = true;
                break;
            }
        }
        return res;
    }
    
    public Combo buscarCombo(int nro){
        for(Combo c : combos){
            if(c.getNroCombo() == nro){
                return c;
            }
        }
        return null;
    }
    
    public ArrayList<Combo> getCombos(){
        return combos;
    }
    
    public String mostrarMenu(){
        String res = "===============PRODUCTOS===============" + "\n";
        for(Producto p : productos){
            res+= p.verProducto()+"\n";
        }
        res+=  "=================COMBOS=================: \n";
        for(Combo c : combos){
            res+= c.verCombo()+"\n";
        }
        return res;
    }
    
    public String mostrarProductos(){
        String res = "";
        for(Producto p : productos){
            res+= p.verProducto()+"\n";
        }
        return res;
    }
    
    public String mostrarCombos(){
        String res = "";
        for(Combo c : combos){
            res+= c.verCombo()+"\n";
        }
        return res;
    }
    
    
}