package pizzeria.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Venta {
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private int id;
    private LocalDateTime fecha;
    private ArrayList<DetalleVenta> items;
    private double total;
    private int idCajero;
    private MetodoPago metodoPago;
    private double cambio;
    private EstadoPedido estado;
    private LocalDateTime horaEntrega;
    private String nombreCliente;

    public Venta(int id, int idCajero) {
        this.id = id;
        this.idCajero = idCajero;
        this.fecha = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.cambio = 0.0;
        this.estado = EstadoPedido.PENDIENTE;
        this.metodoPago = MetodoPago.EFECTIVO;
        this.nombreCliente = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }


    public ArrayList<DetalleVenta> getItems() {
        return items;
    }

    public void setItems(ArrayList<DetalleVenta> items) {
        this.items = items;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }


    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }


    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }


    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }


    public LocalDateTime getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(LocalDateTime horaEntrega) {
        this.horaEntrega = horaEntrega;
    }


    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        if (nombreCliente == null) {
            this.nombreCliente = "";
        } else {
            this.nombreCliente = nombreCliente.trim();
        }
    }

    // Recalcula el total de la venta sumando los subtotales de todos los items
    public void calcularTotal() {
        total = 0.0;
        for (DetalleVenta d : items) {
            total += d.getSubTotal();
        }
    }

    // Calcula el cambio del cliente en caso de pago en efectivo
    public double calcularCambio(double montoPagado) {
        calcularTotal();
        cambio = montoPagado - total;
        if (cambio < 0) {
            cambio = -1;
        }
        return cambio;
    }

    // Convierte la venta a texto para guardarla en archivo
    public String escribirTexto() {
        String texto = id + "|"
                + fecha.format(FORMATO_FECHA) + "|"
                + idCajero + "|"
                + metodoPago.name() + "|"
                + String.format("%.2f", total).replace(",", ".") + "|"
                + String.format("%.2f", cambio).replace(",", ".") + "|"
                + estado.name() + "|"
                + nombreCliente.replace("|", "/") + "|";

        for (int i = 0; i < items.size(); i++) {
            texto += items.get(i).escribirTexto();
            if (i < items.size() - 1) {
                texto += ";";
            }
        }

        return texto;
    }

    // Devuelve una representación visual completa de la venta
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(" Venta #%-4d | %s | Cajero: %d | %s%n",
                id, fecha.format(FORMATO_FECHA), idCajero, metodoPago));

        if (nombreCliente != null && !nombreCliente.isBlank()) {
            sb.append(String.format(" Cliente: %s%n", nombreCliente));
        }

        sb.append(String.format(" Estado: %-20s%n", estado));
        sb.append(" " + "-".repeat(58) + "\n");
        for (DetalleVenta d : items) {
            sb.append(d.toString()).append("\n");
        }
        sb.append(" " + "-".repeat(58) + "\n");
        sb.append(String.format(" %-38s TOTAL: Bs.%7.2f%n", "", total));
        if (cambio > 0) {
            sb.append(String.format(" %-38s CAMBIO: Bs.%6.2f%n", "", cambio));
        }
        return sb.toString();
    }
}