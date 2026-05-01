package pizzeria.controller;

import pizzeria.model.EstadoPedido;
import pizzeria.model.Menu;
import pizzeria.model.MovimientoCaja;
import pizzeria.model.Inventario;
import pizzeria.model.Insumo;
import pizzeria.model.Reserva;
import pizzeria.model.Venta;
import pizzeria.model.Producto;
import pizzeria.model.Combo;
import pizzeria.model.MetodoPago;
import pizzeria.model.DetalleVenta;
import pizzeria.view.Consola;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestorVenta {

    private static final String ARCHIVO_VENTAS = "resources/data/ventas.txt";

    private ArrayList<Venta> listaVenta;
    private Menu menu;
    private Venta ventaActual;
    private Inventario inventario;
    private GestorFinanzas gestorFinanzas;
    private GestorReserva gestorReserva;
    private GestorCocina gestorCocina;

    public GestorVenta(Menu menu, Inventario inventario, GestorFinanzas gestorFinanzas,
                       GestorReserva gestorReserva, GestorCocina gestorCocina) {
        listaVenta = new ArrayList<>();
        this.menu = menu;
        this.inventario = inventario;
        this.gestorFinanzas = gestorFinanzas;
        this.gestorReserva = gestorReserva;
        this.gestorCocina = gestorCocina;
    }

    public ArrayList<Venta> getListaVenta() {
        return listaVenta;
    }

    public Venta getVentaActual() {
        return ventaActual;
    }

    public void menuVentas(int idCajero) {
        int opcion;
        ArrayList<Producto> productos = menu.getProductos();

        do {
            Consola.titulo("GESTIÓN DE VENTAS");
            System.out.println(" 1. Nuevo pedido");
            System.out.println(" 2. Ver historial de ventas");
            System.out.println(" 3. Buscar venta por ID");
            System.out.println(" 4. Cancelar venta pagada");
            System.out.println(" 0. Volver");
            Consola.separador();

            opcion = Consola.leerEnteroRango("Seleccione una opción: ", 0, 4);

            switch (opcion) {
                case 1 -> menuNuevoPedido(idCajero, productos);
                case 2 -> verHistorial();
                case 3 -> buscarVentaMenu();
                case 4 -> cancelarVentaPagadaMenu();
                case 0 -> System.out.println(" Volviendo al menú principal...");
            }
        } while (opcion != 0);
    }

    private void menuNuevoPedido(int idCajero, ArrayList<Producto> productos) {
        crearVenta(idCajero);
        Consola.titulo("NUEVO PEDIDO #" + ventaActual.getId());

        int opcion;
        do {
            ventaActual.calcularTotal();

            System.out.printf("%n Total actual: Bs.%.2f%n", ventaActual.getTotal());
            Consola.separador();
            System.out.println(" 1. Agregar producto");
            System.out.println(" 2. Agregar combo");
            System.out.println(" 3. Quitar producto");
            System.out.println(" 4. Ver items actuales");
            System.out.println(" 5. Cobrar y definir tipo de pedido");
            System.out.println(" 0. Cancelar armado del pedido");
            Consola.separador();

            opcion = Consola.leerEnteroRango("Seleccione una opción: ", 0, 5);

            switch (opcion) {
                case 1 -> menuAgregarItem(productos);
                case 2 -> menuAgregarCombo();
                case 3 -> menuQuitarItem();
                case 4 -> verItemsActuales();
                case 5 -> {
                    if (ventaActual.getItems().isEmpty()) {
                        System.out.println(" No hay productos en el pedido.");
                    } else {
                        boolean finalizado = menuCobrarYDefinirDestino();
                        if (finalizado) {
                            opcion = 0;
                        }
                    }
                }
                case 0 -> {
                    if (Consola.confirmar("¿Está seguro de cancelar el pedido actual?")) {
                        cancelarArmadoPedido();
                    }
                }
            }
        } while (opcion != 0);
    }

    private void menuAgregarItem(ArrayList<Producto> productos) {
        Consola.titulo("AGREGAR PRODUCTO");

        for (Producto p : productos) {
            System.out.printf("  [%2d] %-22s Bs.%.2f%n",
                    p.getID(), p.getNombre(), p.getPrecio());
        }

        Consola.separador();
        Integer idProd = Consola.leerEnteroCancelable("ID del producto");
        if (idProd == null) {
            System.out.println(" Registro de producto cancelado.");
            return;
        }

        if (idProd <= 0) {
            System.out.println(" ID inválido.");
            Consola.pausar();
            return;
        }

        Producto prod = menu.buscarProducto(idProd);

        if (prod == null) {
            System.out.println(" Producto no encontrado.");
            Consola.pausar();
            return;
        }

        Integer cant = Consola.leerEnteroCancelable("Cantidad");
        if (cant == null) {
            System.out.println(" Registro de producto cancelado.");
            return;
        }

        if (cant <= 0) {
            System.out.println(" La cantidad debe ser mayor a 0.");
            Consola.pausar();
            return;
        }

        agregarItem(prod, cant);

        System.out.printf(" Agregado: %dx %s%n", cant, prod.getNombre());
        Consola.pausar();
    }

    private void menuAgregarCombo() {
        ArrayList<Combo> combos = menu.getCombos();

        if (combos.isEmpty()) {
            System.out.println(" No hay combos disponibles.");
            Consola.pausar();
            return;
        }

        Consola.titulo("AGREGAR COMBO");

        for (Combo c : combos) {
            System.out.printf("  [%2d] Combo #%d  —  Bs.%.2f%n",
                    c.getNroCombo(), c.getNroCombo(), c.getPrecio());
            for (Producto p : c.getCombo()) {
                System.out.printf("        • %s%n", p.getNombre());
            }
        }

        Consola.separador();

        Integer nro = Consola.leerEnteroCancelable("Nro. de combo");
        if (nro == null) {
            System.out.println(" Registro de combo cancelado.");
            return;
        }

        if (nro <= 0) {
            System.out.println(" Número de combo inválido.");
            return;
        }

        Combo combo = menu.buscarCombo(nro);

        if (combo == null) {
            System.out.println(" Combo " + nro + " no encontrado.");
            Consola.pausar();
            return;
        }

        Integer cant = Consola.leerEnteroCancelable("Cantidad de combos");
        if (cant == null) {
            System.out.println(" Registro de combo cancelado.");
            return;
        }

        if (cant <= 0) {
            System.out.println(" La cantidad debe ser mayor a 0.");
            Consola.pausar();
            return;
        }

        ArrayList<Producto> prods = combo.getCombo();
        double precioTotal = 0;

        for (Producto p : prods) {
            precioTotal += p.getPrecio();
        }

        for (Producto p : prods) {
            double proporcion;
            if (precioTotal > 0) {
                proporcion = p.getPrecio() / precioTotal;
            } else {
                proporcion = 1.0 / prods.size();
            }

            double precioAjustado = combo.getPrecio() * proporcion;

            Producto pAjustado = new Producto(p.getID(), p.getNombre(), p.getDescripcion(), precioAjustado);
            pAjustado.getIngredientes().addAll(p.getIngredientes());

            agregarItem(pAjustado, cant);
        }

        System.out.printf(" Combo #%d agregado x%d — Bs.%.2f c/u%n", nro, cant, combo.getPrecio());
        Consola.pausar();
    }


    private void menuQuitarItem() {
        if (ventaActual.getItems().isEmpty()) {
            System.out.println(" No hay productos en el pedido.");
            return;
        }

        Consola.titulo("QUITAR PRODUCTO");
        mostrarItemsSinPausa();

        Integer index = Consola.leerEnteroCancelable(
                "Índice del producto a quitar (1 - " + ventaActual.getItems().size() + ")");

        if (index == null) {
            System.out.println(" Acción cancelada.");
            return;
        }

        if (index < 1 || index > ventaActual.getItems().size()) {
            System.out.println(" Índice inválido.");
            Consola.pausar();
            return;
        }

        String nombre = ventaActual.getItems().get(index - 1).getProducto().getNombre();

        if (Consola.confirmar("¿Está seguro de quitar este producto?")) {
            quitarItem(index - 1);
            System.out.println(" Eliminado: " + nombre);
        } else {
            System.out.println(" No se eliminó ningún producto.");
        }

        Consola.pausar();
    }

    private void verItemsActuales() {
        Consola.titulo("ITEMS DEL PEDIDO ACTUAL");
        if (ventaActual.getItems().isEmpty()) {
            System.out.println(" (sin productos)");
        } else {
            for (int i = 0; i < ventaActual.getItems().size(); i++) {
                System.out.printf(" [%d] %s%n", i + 1, ventaActual.getItems().get(i));
            }

            Consola.separador();
            System.out.printf(" Total: Bs.%.2f%n", ventaActual.getTotal());
        }
        Consola.pausar();
    }

    private void mostrarItemsSinPausa() {
        if (ventaActual.getItems().isEmpty()) {
            System.out.println(" (sin productos)");
            return;
        }

        for (int i = 0; i < ventaActual.getItems().size(); i++) {
            System.out.printf(" [%d] %s%n", i + 1, ventaActual.getItems().get(i));
        }

        Consola.separador();
        System.out.printf(" Total: Bs.%.2f%n", ventaActual.getTotal());
    }
  
    private boolean menuCobrarYDefinirDestino() {
        Consola.titulo("COBRO DEL PEDIDO");

        ventaActual.calcularTotal();

        if (!hayStockSuficienteParaPedido(ventaActual.getItems())) {
            System.out.println(" No hay stock suficiente para confirmar este pedido.");
            System.out.println(" Revise el inventario o modifique el pedido.");
            Consola.pausar();
            return false;
        }

        System.out.println(ventaActual);

        String nombreCliente = Consola.leerTextoOpcionalCancelable("Nombre del cliente");
        if (nombreCliente == null) {
            System.out.println(" Cobro cancelado.");
            return false;
        }
        ventaActual.setNombreCliente(nombreCliente);

        System.out.println(" TIPO DE ATENCIÓN DEL PEDIDO:");
        System.out.println("  1. Atender ahora (venta inmediata)");
        System.out.println("  2. Registrar como reserva");
        System.out.println("  0. Cancelar cobro");
        int tipoPedido = Consola.leerEnteroRango("Seleccione una opción: ", 0, 2);

        if (tipoPedido == 0) {
            if (Consola.confirmar("¿Está seguro de cancelar el cobro?")) {
                System.out.println(" Cobro cancelado.");
                return false;
            }
        }

        MetodoPago[] metodos = MetodoPago.values();
        System.out.println(" MÉTODO DE PAGO:");
        for (int i = 0; i < metodos.length; i++) {
            System.out.printf("  [%d] %s%n", i + 1, metodos[i].getNombre());
        }
        System.out.println("  [0] Cancelar cobro");

        int opPago = Consola.leerEnteroRango("Seleccione método: ", 0, metodos.length);

        if (opPago == 0) {
            if (Consola.confirmar("¿Está seguro de cancelar el cobro?")) {
                System.out.println(" Cobro cancelado.");
                return false;
            } else {
                return menuCobrarYDefinirDestino();
            }
        }

        MetodoPago metodo = metodos[opPago - 1];

        double montoPagado = ventaActual.getTotal();
        if (metodo == MetodoPago.EFECTIVO) {
            while (true) {
                Double monto = Consola.leerDoubleCancelable(
                        String.format("Total: Bs.%.2f  —  Monto recibido: Bs.", ventaActual.getTotal()));

                if (monto == null) {
                    System.out.println(" Cobro cancelado.");
                    return false;
                }

                if (monto < ventaActual.getTotal()) {
                    System.out.println(" Monto insuficiente, intente de nuevo.");
                } else {
                    montoPagado = monto;
                    break;
                }
            }
        }

        if (tipoPedido == 1) {
            finalizarVentaInmediata(metodo, montoPagado);
        } else {
            registrarReservaPagada(metodo, montoPagado);
        }

        return true;
    }

    private void finalizarVentaInmediata(MetodoPago metodo, double montoPagado) {
        if (ventaActual == null) {
            return;
        }

        ventaActual.setMetodoPago(metodo);
        ventaActual.calcularTotal();
        ventaActual.calcularCambio(montoPagado);
        ventaActual.setEstado(EstadoPedido.PENDIENTE);

        Venta ventaFinalizada = ventaActual;
        listaVenta.add(ventaFinalizada);

        registrarCobro(ventaFinalizada.getTotal(), metodo, ventaFinalizada.getCambio(),
                "Venta inmediata #" + ventaFinalizada.getId());

        gestorCocina.agregarVentaACocina(ventaFinalizada);

        guardarArchivo();
        generarFactura(ventaFinalizada);
        ventaActual = null;
    }
   
    private void registrarReservaPagada(MetodoPago metodo, double montoPagado) {
        if (ventaActual == null) {
            return;
        }

        String nombreReserva = ventaActual.getNombreCliente();

        if (nombreReserva == null || nombreReserva.isBlank()) {
            nombreReserva = Consola.leerTextoCancelable("Nombre del cliente para la reserva");
            if (nombreReserva == null) {
                System.out.println(" Registro de reserva cancelado.");
                return;
            }
        }

        String telefono = Consola.leerTextoCancelable("Teléfono");
        if (telefono == null) {
            System.out.println(" Registro de reserva cancelado.");
            return;
        }

        LocalDateTime fechaReserva = LocalDateTime.now();

        ventaActual.setMetodoPago(metodo);
        ventaActual.calcularTotal();
        ventaActual.calcularCambio(montoPagado);

        List<DetalleVenta> copiaPedido = copiarItems(ventaActual.getItems());
        Reserva reserva = gestorReserva.nuevaReserva(nombreReserva, telefono, fechaReserva, copiaPedido);

        registrarCobro(reserva.calcularTotal(), metodo, ventaActual.getCambio(),
                "Reserva #" + reserva.getId());

        gestorReserva.guardarArchivo("resources/data/reservas.txt");
        generarConfirmacionReserva(reserva);

        ventaActual = null;
    }

    private void registrarCobro(double total, MetodoPago metodo, double cambio, String descripcion) {
        if (gestorFinanzas != null) {
            gestorFinanzas.registrarIngreso(
                    total,
                    MovimientoCaja.CAT_VENTA,
                    descripcion
            );

            if (metodo == MetodoPago.EFECTIVO && cambio > 0) {
                gestorFinanzas.registrarEgreso(
                        cambio,
                        MovimientoCaja.CAT_OTRO,
                        "Cambio entregado de " + descripcion
                );
            }
        }
    }

    private void verHistorial() {
        Consola.titulo("HISTORIAL DE VENTAS");
        if (listaVenta.isEmpty()) {
            System.out.println(" No hay ventas registradas.");
        } else {
            for (Venta v : listaVenta) {
                String cliente = (v.getNombreCliente() == null || v.getNombreCliente().isBlank())
                        ? "Sin nombre"
                        : v.getNombreCliente();

                System.out.printf(" #%-4d | %s | Cliente: %-15s | Bs.%7.2f | %-20s | [%s]%n",
                        v.getId(),
                        v.getFecha().format(Venta.FORMATO_FECHA),
                        cliente,
                        v.getTotal(),
                        v.getMetodoPago(),
                        v.getEstado());
            }
        }
        Consola.pausar();
    }

    private void buscarVentaMenu() {
        Consola.titulo("BUSCAR VENTA");

        Integer id = Consola.leerEnteroCancelable("ID de venta");
        if (id == null) {
            System.out.println(" Búsqueda cancelada.");
            return;
        }

        Venta v = buscarVentaPorId(id);

        if (v == null) {
            System.out.println(" Venta no encontrada.");
        } else {
            System.out.println(v);
        }

        Consola.pausar();
    }

    // Permite cancelar una venta pagada antes de ser entregada y registra reembolso
    private void cancelarVentaPagadaMenu() {
        Consola.titulo("CANCELAR VENTA PAGADA");

        Integer id = Consola.leerEnteroCancelable("ID de venta");
        if (id == null) {
            System.out.println(" Acción cancelada.");
            return;
        }

        Venta venta = buscarVentaPorId(id);
        if (venta == null) {
            System.out.println(" No existe una venta con ese ID.");
            Consola.pausar();
            return;
        }

        System.out.println(venta);

        if (!Consola.confirmar("¿Está seguro de cancelar esta venta pagada?")) {
            System.out.println(" No se realizó ninguna cancelación.");
            Consola.pausar();
            return;
        }

        boolean cancelada = cancelarVentaPagada(id);

        if (cancelada) {
            System.out.println(" Venta cancelada correctamente y reembolso registrado.");
        } else {
            System.out.println(" No se pudo cancelar la venta. Puede no existir o ya estar entregada/cancelada.");
        }

        Consola.pausar();
    }

    public void crearVenta(int idCajero) {
        int nuevoId;
        if (listaVenta.isEmpty()) {
            nuevoId = 1;
        } else {
            nuevoId = listaVenta.get(listaVenta.size() - 1).getId() + 1;
        }

        ventaActual = new Venta(nuevoId, idCajero);
    }

    public void agregarItem(Producto p, int cantidad) {
        if (ventaActual == null) {
            return;
        }

        for (DetalleVenta d : ventaActual.getItems()) {
            if (d.getProducto().getID() == p.getID()) {
                d.setCantidad(d.getCantidad() + cantidad);
                ventaActual.calcularTotal();
                return;
            }
        }

        ventaActual.getItems().add(new DetalleVenta(p, cantidad));
        ventaActual.calcularTotal();
    }

    public void quitarItem(int index) {
        if (ventaActual == null) {
            return;
        }

        ArrayList<DetalleVenta> items = ventaActual.getItems();

        if (index >= 0 && index < items.size()) {
            items.remove(index);
            ventaActual.calcularTotal();
        }
    }

    public void cancelarArmadoPedido() {
        if (ventaActual != null) {
            System.out.println(" Pedido en armado cancelado.");
            ventaActual = null;
        }
    }

    public boolean cancelarVentaPagada(int idVenta) {
        Venta venta = buscarVentaPorId(idVenta);

        if (venta == null) {
            return false;
        }

        if (venta.getEstado() == EstadoPedido.ENTREGADO ||
            venta.getEstado() == EstadoPedido.CANCELADO) {
            return false;
        }

        gestorCocina.cancelarPedidoPorVenta(idVenta);
        venta.setEstado(EstadoPedido.CANCELADO);

        if (gestorFinanzas != null) {
            gestorFinanzas.registrarEgreso(
                    venta.getTotal(),
                    MovimientoCaja.CAT_OTRO,
                    "Reembolso venta #" + venta.getId()
            );
        }

        guardarArchivo();
        return true;
    }

    // Genera la factura de una venta inmediata
    public void generarFactura(Venta v) {
        Consola.titulo("FACTURA");
        System.out.println(v);
        System.out.println(" Gracias por su preferencia — Pizzería");
        Consola.separador();
    }

    // Genera una confirmación simple de reserva pagada
    public void generarConfirmacionReserva(Reserva r) {
        Consola.titulo("CONFIRMACIÓN DE RESERVA");
        System.out.println(r);
        System.out.println(" Reserva registrada y pagada correctamente.");
        Consola.separador();
    }

    // Busca una venta por su ID dentro del historial
    private Venta buscarVentaPorId(int id) {
        for (Venta v : listaVenta) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    // Valida si el stock actual alcanza para cubrir todo el pedido antes de aceptarlo
    private boolean hayStockSuficienteParaPedido(List<DetalleVenta> items) {
        HashMap<Integer, Double> requeridoPorInsumo = new HashMap<>();

        for (DetalleVenta detalle : items) {
            Producto productoCompleto = menu.buscarProducto(detalle.getProducto().getID());

            if (productoCompleto == null) {
                productoCompleto = detalle.getProducto();
            }

            for (int idInsumo : productoCompleto.getIngredientes()) {
                Insumo insumo = inventario.buscarId(idInsumo);

                if (insumo == null) {
                    System.out.println(" Falta configurar un insumo del producto: " + productoCompleto.getNombre());
                    return false;
                }

                double requerido = insumo.getCantidadPorPizza() * detalle.getCantidad();

                if (requeridoPorInsumo.containsKey(idInsumo)) {
                    requerido += requeridoPorInsumo.get(idInsumo);
                }

                requeridoPorInsumo.put(idInsumo, requerido);
            }
        }

        boolean suficiente = true;

        for (Integer idInsumo : requeridoPorInsumo.keySet()) {
            Insumo insumo = inventario.buscarId(idInsumo);
            double requerido = requeridoPorInsumo.get(idInsumo);

            if (insumo.getStockActual() < requerido) {
                suficiente = false;
                System.out.printf(" Stock insuficiente para %s. Disponible: %.3f | Requerido: %.3f%n",
                        insumo.getNombre(), insumo.getStockActual(), requerido);
            }
        }

        return suficiente;
    }

    public void guardarArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_VENTAS))) {
            bw.write("# Sistema Pizzería — Ventas");
            bw.newLine();
            bw.write("# Formato: id|fecha|idCajero|metodoPago|total|cambio|estado|nombreCliente|items...");
            bw.newLine();

            for (Venta v : listaVenta) {
                bw.write(v.escribirTexto());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println(" Error al guardar ventas: " + e.getMessage());
        }
    }

    public void cargarArchivo() {
        listaVenta = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_VENTAS))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }

                Venta v = parsearVenta(linea);

                if (v != null) {
                    listaVenta.add(v);
                }
            }
        } catch (IOException e) {
            // Si no existe el archivo todavía, no se considera error crítico
        }
    }

    // Reconstruye una venta desde una línea de texto del archivo
    private Venta parsearVenta(String linea) {
        try {
            String[] partes = linea.split("\\|", 9);

            if (partes.length < 8) {
                return null;
            }

            int id = Integer.parseInt(partes[0].trim());
            LocalDateTime fecha = LocalDateTime.parse(partes[1].trim(), Venta.FORMATO_FECHA);
            int idCajero = Integer.parseInt(partes[2].trim());
            MetodoPago metodo = MetodoPago.valueOf(partes[3].trim());
            double total = Double.parseDouble(partes[4].trim().replace(",", "."));
            double cambio = Double.parseDouble(partes[5].trim().replace(",", "."));
            EstadoPedido estado = EstadoPedido.valueOf(partes[6].trim());

            Venta v = new Venta(id, idCajero);
            v.setFecha(fecha);
            v.setMetodoPago(metodo);
            v.setTotal(total);
            v.setCambio(cambio);
            v.setEstado(estado);

            String itemsRaw = "";

            if (partes.length == 8) {            
                itemsRaw = partes[7].trim();
                v.setNombreCliente("");
            } else {
                v.setNombreCliente(partes[7].trim());
                itemsRaw = partes[8].trim();
            }

            if (!itemsRaw.isEmpty()) {
                String[] itemsPartes = itemsRaw.split(";");

                for (String itemTxt : itemsPartes) {
                    String[] d = itemTxt.split("~");
                    if (d.length < 4) {
                        continue;
                    }

                    int idProd = Integer.parseInt(d[0].trim());
                    String nombre = d[1].trim();
                    double precio = Double.parseDouble(d[2].trim().replace(",", "."));
                    int cant = Integer.parseInt(d[3].trim());

                    Producto prodMenu = menu.buscarProducto(idProd);
                    Producto prod;

                    if (prodMenu != null) {
                        prod = prodMenu;
                    } else {
                        prod = new Producto(idProd, nombre, "", precio);
                    }

                    v.getItems().add(new DetalleVenta(prod, cant));
                }
            }

            return v;
        } catch (Exception e) {
            System.out.println(" [ERROR] parsearVenta: " + e.getMessage() + " → " + linea);
            return null;
        }
    }

    private List<DetalleVenta> copiarItems(ArrayList<DetalleVenta> origen) {
        List<DetalleVenta> copia = new ArrayList<>();

        for (DetalleVenta d : origen) {
            Producto p = d.getProducto();
            Producto copiaProducto = new Producto(p.getID(), p.getNombre(), p.getDescripcion(), p.getPrecio());
            copiaProducto.getIngredientes().addAll(p.getIngredientes());

            copia.add(new DetalleVenta(copiaProducto, d.getCantidad()));
        }

        return copia;
    }
}