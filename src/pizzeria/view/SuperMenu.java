package pizzeria.view;

import pizzeria.model.Menu;
import pizzeria.model.Combo;
import pizzeria.util.ArchivoMenu;
import pizzeria.model.TipoProducto;

import pizzeria.model.Producto;
import java.util.ArrayList;

public class SuperMenu{

    private final Menu       menu;
    private final ArchivoMenu archivoMenu;

    public SuperMenu(Menu menu, ArchivoMenu archivoMenu){
        this.menu        = menu;
        this.archivoMenu = archivoMenu;
    }


    public void mostrar(){
        boolean enMenu = true;
        while(enMenu){
            Consola.titulo("MENÚ Y COMBOS");
            System.out.println(" 1. Ver menú");
            System.out.println(" 2. Agregar producto");
            System.out.println(" 3. Agregar ingrediente a un producto");
            System.out.println(" 4. Eliminar producto");
            System.out.println(" 5. Crear combo");
            System.out.println(" 6. Eliminar combo");
            System.out.println(" 7. Ver productos");
            System.out.println(" 8. Ver Combos");
            System.out.println(" 0. Volver al menú principal");
            Consola.separador();

            int opcion = Consola.leerEnteroRango("Seleccione una opción: ", 0, 8);

            switch(opcion){
                case 1 -> verMenu();
                case 2 -> agregarProducto();
                case 3 -> agregarIngrediente();
                case 4 -> eliminarProducto();
                case 5 -> agregarCombo();
                case 6 -> eliminarCombo();
                case 7 -> verProductos();
                case 8 -> verCombos();
                case 0 -> enMenu = false;
            }
        }
    }

    private void verMenu(){
        Consola.titulo("MENÚ COMPLETO");
        System.out.println(menu.mostrarMenu());
        Consola.pausar();
    }
    
    private void verProductos(){
        Consola.titulo("PRODUCTOS REGISTRADOS");
        System.out.println(menu.mostrarProductos());
        Consola.pausar();
    }

    private void verCombos(){
        Consola.titulo("COMBOS REGISTRADOS");
        System.out.println(menu.mostrarCombos());
        Consola.pausar();
    }
    
    private void agregarProducto(){
        Consola.titulo("AGREGAR PRODUCTO");

        String nombre      = Consola.leerTexto("Nombre del producto: ");
        String descripcion = Consola.leerTexto("Descripción del producto: ");
        double precio      = leerPrecio("Precio del producto (Bs.): ");
        
        System.out.println(" Tipo de producto:");
        System.out.println(" 1. " + TipoProducto.PRODUCTO.getNombre());
        System.out.println(" 2. " + TipoProducto.REFRESCO.getNombre());
        int opTipo = Consola.leerEnteroRango("Seleccione el tipo: ", 1, 2);
        TipoProducto tipo = (opTipo == 2) ? TipoProducto.REFRESCO : TipoProducto.PRODUCTO;

        menu.agregarProducto(nombre, descripcion, precio, tipo);
        System.out.println(" Producto agregado correctamente.");
        archivoMenu.guardarProductos(menu.getProductos());
        Consola.pausar();
        
        
    }

    private void agregarIngrediente(){
        Consola.titulo("AGREGAR INGREDIENTE A PRODUCTO");

        if(menu.getProductos().isEmpty()){
            System.out.println(" No hay productos registrados.");
            Consola.pausar();
            return;
        }

        System.out.println(menu.mostrarMenu());
        int idProducto = Consola.leerEntero("ID del producto: ");

        Producto prod = menu.buscarProducto(idProducto);
        if(prod == null){
            System.out.printf(" No existe un producto con ID %d.%n", idProducto);
            Consola.pausar();
            return;
        }

        int idIngrediente = Consola.leerEntero("ID del ingrediente (insumo): ");
        if(idIngrediente <= 0){
            System.out.println(" ID de ingrediente inválido.");
            Consola.pausar();
            return;
        }

        prod.agregarIngrediente(idIngrediente);
        System.out.printf(" Ingrediente %d agregado al producto '%s'.%n",
                idIngrediente, prod.getNombre());
        Consola.pausar();
    }

    private void eliminarProducto(){
        Consola.titulo("ELIMINAR PRODUCTO");

        if(menu.getProductos().isEmpty()){
            System.out.println(" No hay productos registrados.");
            Consola.pausar();
            return;
        }

        System.out.println(menu.mostrarProductos());
        
        int idEliminar = Consola.leerEntero("ID del producto a eliminar (0 = cancelar): ");

        if(idEliminar == 0){
            return;    
        }

        if(idEliminar < 0){
            System.out.println(" ID inválido.");
            Consola.pausar();
            return;
        }

        Producto prod = menu.buscarProducto(idEliminar);
        if(prod == null){
            System.out.printf(" No existe un producto con ID %d.%n", idEliminar);
            Consola.pausar();
            return;
        }

        System.out.printf(" Producto a eliminar: %s%n", prod.getNombre());
        if(!Consola.confirmar("¿Confirmar eliminación?")){
            System.out.println(" Operación cancelada.");
            Consola.pausar();
            return;
        }

        menu.eliminarProducto(idEliminar);
        System.out.println(" Producto eliminado.");
        archivoMenu.guardarProductos(menu.getProductos());
        Consola.pausar();
    }


    private void agregarCombo(){
        Consola.titulo("CREAR COMBO");

        if(menu.getProductos().isEmpty()){
            System.out.println(" No hay productos para armar un combo.");
            Consola.pausar();
            return;
        }

        ArrayList<Producto> productosCombo = new ArrayList<>();

        boolean seleccionando = true;
        while(seleccionando){

            System.out.println();
            System.out.println(" Productos disponibles:");
            System.out.println(menu.mostrarProductos());

            if(!productosCombo.isEmpty()){
                System.out.println(" Productos ya en el combo:");
                for(Producto p : productosCombo){
                    System.out.printf("   + [%d] %s%n", p.getID(), p.getNombre());
                }
            }

            System.out.println(" (0 para terminar la selección)");
            Consola.separador();

            int id = Consola.leerEntero("ID del producto a agregar: ");

            if(id == 0){
                if(productosCombo.isEmpty()){
                    System.out.println(" El combo no tiene ningún producto.");
                    if(Consola.confirmar("¿Desea cancelar la creación del combo?")){
                        System.out.println(" Creación de combo cancelada.");
                        Consola.pausar();
                        return;
                    }
                    continue;
                }
                System.out.println();
                System.out.println(" Productos seleccionados para el combo:");
                double precioSugerido = 0;
                for(Producto p : productosCombo){
                    System.out.printf("   + %-22s  Bs. %.2f%n", p.getNombre(), p.getPrecio());
                    precioSugerido += p.getPrecio();
                }
                System.out.printf(" Precio sugerido (suma): Bs. %.2f%n", precioSugerido);

                if(Consola.confirmar("¿Confirmar creación del combo?")){
                    seleccionando = false;
                } else {
                    System.out.println(" Siga agregando productos o presione 0 para cancelar.");
                }
                continue;
            }
            if(id < 0){
                System.out.println(" ID inválido. Ingrese un número positivo.");
                continue;
            }            
            Producto prod = menu.buscarProducto(id);
            if(prod == null){
                System.out.printf(" No existe un producto con ID %d.%n", id);
                continue;
            }
            boolean yaEsta = false;
            for(Producto p : productosCombo){
                if(p.getID() == prod.getID()){
                    yaEsta = true;
                    break;
                }
            }

            if(yaEsta){
                System.out.printf(" '%s' ya está en el combo.%n", prod.getNombre());
                continue;
            }

            productosCombo.add(prod);
            System.out.printf(" '%s' agregado al combo.%n", prod.getNombre());
        }
        double precioSumaProductos = 0;
        for (Producto p : productosCombo){
            precioSumaProductos += p.getPrecio();
        }

        System.out.printf("%n Precio sugerido (suma de productos): Bs. %.2f%n", precioSumaProductos);
        double precioFinal;

        if(Consola.confirmar("¿Usar ese precio? (N para ingresar precio especial)")){
            precioFinal = precioSumaProductos;
        }else{
            precioFinal = leerPrecio("Precio especial del combo (Bs.): ");
        }
        
        menu.agregarCombo(productosCombo, precioFinal);

        Combo nuevo = menu.getCombos().get(menu.getCombos().size() - 1);
        System.out.println();
        System.out.println(" Combo creado exitosamente:");
        System.out.println(nuevo.verCombo());
        archivoMenu.guardarCombos(menu.getCombos());
        Consola.pausar();
    }

    private void eliminarCombo(){
        Consola.titulo("ELIMINAR COMBO");

        if(menu.getCombos().isEmpty()){
            System.out.println(" No hay combos registrados.");
            Consola.pausar();
            return;
        }

        System.out.println(" Combos disponibles:");
        for(Combo c : menu.getCombos()){
            System.out.printf("  [%d] %d producto(s)  —  Bs. %.2f%n",
                    c.getNroCombo(), c.getCombo().size(), c.getPrecio());
        }
        Consola.separador();

        int idCombo = Consola.leerEntero("Nro. de combo a eliminar (0 = cancelar): ");
        if(idCombo == 0){
            return;    
        }

        if(idCombo < 0){
            System.out.println(" Número de combo inválido.");
            Consola.pausar();
            return;
        }

        Combo combo = menu.buscarCombo(idCombo);
        if(combo == null){
            System.out.printf(" No existe el combo #%d.%n", idCombo);
            Consola.pausar();
            return;
        }

        System.out.println(" Combo a eliminar:");
        System.out.println(combo.verCombo());

        if(!Consola.confirmar("¿Confirmar eliminación?")){
            System.out.println(" Operación cancelada.");
            Consola.pausar();
            return;
        }

        menu.eliminarCombo(idCombo);
        System.out.println(" Combo eliminado.");
        archivoMenu.guardarProductos(menu.getProductos());
        Consola.pausar();
    }

    private void guardarArchivo(){
        archivoMenu.guardarProductos(menu.getProductos());
        archivoMenu.guardarCombos(menu.getCombos());
        System.out.println(" Productos y combos guardados en archivo.");
        Consola.pausar();
    }

    private double leerPrecio(String mensaje){
        while(true){
            double valor = Consola.leerDouble(mensaje);
            if(valor > 0){
                return valor;    
            }
            System.out.println(" El precio debe ser mayor que cero.");
        }
    }
}
