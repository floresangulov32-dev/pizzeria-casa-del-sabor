package pizzeria.IU;

import pizzeria.controller.GestorCocina;
import pizzeria.model.PedidoCocina;
import javax.swing.*;
import java.awt.*;

public class GestorCocinaGUI extends javax.swing.JFrame {

    // Colores del equipo
    private static final Color VINO        = new Color(168, 27, 29);
    private static final Color NEGRO       = new Color(0, 0, 0);
    private static final Color GRIS_CARBON = new Color(74, 74, 74);
    private static final Color BLANCO      = new Color(255, 255, 255);
    private static final Color GRIS_CLARO  = new Color(217, 217, 217);

    // Referencia al gestor
    private GestorCocina gestorCocina;

    // Paneles
    private JPanel pnlEncabezado;
    private JPanel pnlContenido;
    private JPanel pnlPiePag;
    private JPanel pnlPendientes;
    private JPanel pnlEnPreparacion;
    private JPanel pnlListos;

    // Listas
    private DefaultListModel<String> modeloPendientes;
    private DefaultListModel<String> modeloEnPrep;
    private DefaultListModel<String> modeloListos;
    private JList<String> listaPendientes;
    private JList<String> listaEnPrep;
    private JList<String> listaListos;

    // Botones
    private JButton btnTomarPedido;
    private JButton btnMarcarListo;
    private JButton btnMarcarEntregado;
    private JButton btnActualizar;
    private JButton btnCerrar;

    // Label rol
    private JLabel lblRol;

    public GestorCocinaGUI(GestorCocina gestorCocina) {
        this.gestorCocina = gestorCocina;
        initComponentes();
        actualizarListas();
    }

    public GestorCocinaGUI() {
        this.gestorCocina = null;
        initComponentes();
    }

    private void initComponentes() {
        setTitle("Gestor de Cocina - La Casa del Sabor");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        construirEncabezado();
        construirContenido();
        construirPiePagina();
    }

    private void construirEncabezado() {
        pnlEncabezado = new JPanel(new BorderLayout());
        pnlEncabezado.setBackground(VINO);
        pnlEncabezado.setPreferredSize(new Dimension(1280, 100));
        pnlEncabezado.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));

        JLabel lblTitulo = new JLabel("LA CASA DEL SABOR");
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 35));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSub = new JLabel("PIZZERÍA");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblSub.setForeground(GRIS_CLARO);

        JPanel pnlTexto = new JPanel(new GridLayout(2, 1));
        pnlTexto.setBackground(VINO);
        pnlTexto.add(lblTitulo);
        pnlTexto.add(lblSub);

        lblRol = new JLabel("Rol: Cocina  ");
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblRol.setForeground(BLANCO);

        pnlEncabezado.add(pnlTexto, BorderLayout.CENTER);
        pnlEncabezado.add(lblRol, BorderLayout.EAST);
        pnlEncabezado.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(pnlEncabezado, BorderLayout.NORTH);
    }

    private void construirContenido() {
        pnlContenido = new JPanel(new GridLayout(1, 3, 10, 0));
        pnlContenido.setBackground(BLANCO);
        pnlContenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Columna 1: Pendientes
        modeloPendientes = new DefaultListModel<>();
        listaPendientes = new JList<>(modeloPendientes);
        estilizarLista(listaPendientes);
        btnTomarPedido = crearBoton("▶ Tomar Siguiente Pedido", VINO);
        btnTomarPedido.addActionListener(e -> tomarPedido());
        pnlPendientes = construirColumna(
            "📋 PENDIENTES", listaPendientes, btnTomarPedido, new Color(255, 243, 243));
        pnlContenido.add(pnlPendientes);

        // Columna 2: En Preparación
        modeloEnPrep = new DefaultListModel<>();
        listaEnPrep = new JList<>(modeloEnPrep);
        estilizarLista(listaEnPrep);
        btnMarcarListo = crearBoton("✔ Marcar como Listo", GRIS_CARBON);
        btnMarcarListo.addActionListener(e -> marcarListo());
        pnlEnPreparacion = construirColumna(
            "🔥 EN PREPARACIÓN", listaEnPrep, btnMarcarListo, new Color(255, 251, 230));
        pnlContenido.add(pnlEnPreparacion);

        // Columna 3: Listos
        modeloListos = new DefaultListModel<>();
        listaListos = new JList<>(modeloListos);
        estilizarLista(listaListos);
        btnMarcarEntregado = crearBoton("✅ Marcar como Entregado", new Color(34, 139, 34));
        btnMarcarEntregado.addActionListener(e -> marcarEntregado());
        pnlListos = construirColumna(
            "✅ LISTOS", listaListos, btnMarcarEntregado, new Color(230, 255, 234));
        pnlContenido.add(pnlListos);

        add(pnlContenido, BorderLayout.CENTER);
    }

    private JPanel construirColumna(String titulo, JList<String> lista,
                                     JButton boton, Color colorFondo) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(colorFondo);
        panel.setBorder(BorderFactory.createLineBorder(GRIS_CLARO, 1));

        JLabel lbl = new JLabel(titulo, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(NEGRO);
        lbl.setOpaque(true);
        lbl.setBackground(GRIS_CLARO);
        lbl.setPreferredSize(new Dimension(0, 40));

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JPanel pnlBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBoton.setBackground(colorFondo);
        pnlBoton.add(boton);

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(pnlBoton, BorderLayout.SOUTH);

        return panel;
    }

    private void construirPiePagina() {
        pnlPiePag = new JPanel(new BorderLayout());
        pnlPiePag.setBackground(NEGRO);
        pnlPiePag.setPreferredSize(new Dimension(1280, 47));
        pnlPiePag.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));

        JLabel lblPower = new JLabel("  Powered by StarTech");
        lblPower.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblPower.setForeground(BLANCO);

        btnActualizar = crearBoton("🔄 Actualizar", GRIS_CARBON);
        btnActualizar.setPreferredSize(new Dimension(130, 35));
        btnActualizar.addActionListener(e -> actualizarListas());

        btnCerrar = crearBoton("Cerrar", NEGRO);
        btnCerrar.setForeground(BLANCO);
        btnCerrar.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));
        btnCerrar.setPreferredSize(new Dimension(100, 35));
        btnCerrar.addActionListener(e -> dispose());

        JPanel pnlBotonesPie = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlBotonesPie.setBackground(NEGRO);
        pnlBotonesPie.add(btnActualizar);
        pnlBotonesPie.add(btnCerrar);

        pnlPiePag.add(lblPower, BorderLayout.WEST);
        pnlPiePag.add(pnlBotonesPie, BorderLayout.EAST);

        add(pnlPiePag, BorderLayout.SOUTH);
    }

    private void tomarPedido() {
        if (gestorCocina == null) {
            JOptionPane.showMessageDialog(this, "Sin conexión al gestor.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        PedidoCocina pedido = gestorCocina.tomarSiguientePedido();
        if (pedido == null) {
            JOptionPane.showMessageDialog(this, "No hay pedidos pendientes.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Pedido #" + pedido.getIdPedidoCocina() + " tomado.\nCliente: " + pedido.getNombreCliente(),
                "Pedido tomado", JOptionPane.INFORMATION_MESSAGE);
        }
        actualizarListas();
    }

    private void marcarListo() {
        if (gestorCocina == null) return;
        String seleccion = listaEnPrep.getSelectedValue();
        if (seleccion == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = extraerIdDeCadena(seleccion);
        boolean ok = gestorCocina.marcarComoListo(id);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Pedido #" + id + " marcado como LISTO.", "Listo", JOptionPane.INFORMATION_MESSAGE);
        }
        actualizarListas();
    }

    private void marcarEntregado() {
        if (gestorCocina == null) return;
        String seleccion = listaListos.getSelectedValue();
        if (seleccion == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = extraerIdDeCadena(seleccion);
        boolean ok = gestorCocina.marcarComoEntregado(id);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Pedido #" + id + " marcado como ENTREGADO.", "Entregado", JOptionPane.INFORMATION_MESSAGE);
        }
        actualizarListas();
    }

    public void actualizarListas() {
        modeloPendientes.clear();
        modeloEnPrep.clear();
        modeloListos.clear();
        if (gestorCocina == null) return;
        for (PedidoCocina p : gestorCocina.getColaPendientes()) {
            modeloPendientes.addElement(formatearPedido(p));
        }
        for (PedidoCocina p : gestorCocina.getEnPreparacion()) {
            modeloEnPrep.addElement(formatearPedido(p));
        }
        for (PedidoCocina p : gestorCocina.getListos()) {
            modeloListos.addElement(formatearPedido(p));
        }
    }

    private String formatearPedido(PedidoCocina p) {
        return String.format("[#%d] %s | %s | Bs.%.2f",
            p.getIdPedidoCocina(),
            p.getNombreCliente(),
            p.getTipoOrigen(),
            p.calcularTotal());
    }

    private int extraerIdDeCadena(String texto) {
        try {
            return Integer.parseInt(texto.substring(2, texto.indexOf("]")));
        } catch (Exception e) {
            return -1;
        }
    }

    private void estilizarLista(JList<String> lista) {
        lista.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lista.setBackground(BLANCO);
        lista.setForeground(NEGRO);
        lista.setSelectionBackground(VINO);
        lista.setSelectionForeground(BLANCO);
        lista.setFixedCellHeight(45);
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(colorFondo);
        btn.setForeground(BLANCO);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(220, 40));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        return btn;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GestorCocinaGUI().setVisible(true));
    }
}