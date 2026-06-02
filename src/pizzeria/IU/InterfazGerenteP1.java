/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pizzeria.IU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author StarTech
 */
public class InterfazGerenteP1 extends javax.swing.JFrame {
    
    private String nombreUsuario;
    private String rolUsuario;
    private JButton btnActivo = null;
    private Image logoImagen;
    private Image fondoImagen;

    /**
     * Constructor con usuario y rol
     */
    public InterfazGerenteP1(String rol, String nombre) {
        initComponents();
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        this.rolUsuario = rol;
        this.nombreUsuario = nombre;
        cargarImagenes();
        configurarComponentes();
        configurarHover();
        activarBoton(btnInicio);
        verificarDeudasProximas();
    }
    
    /**
     * Carga las imágenes del logo y fondo
     */
    private void cargarImagenes() {
        // Cargar logo
        try {
            String rutaLogoPng = "resources/imagenes/LogoPizzeria.png";
            String rutaLogoJpg = "resources/imagenes/LogoPizzeria.jpg";
            File archivoLogoPng = new File(rutaLogoPng);
            File archivoLogoJpg = new File(rutaLogoJpg);

            if (archivoLogoPng.exists()) {
                ImageIcon logoIcon = new ImageIcon(archivoLogoPng.getAbsolutePath());
                logoImagen = logoIcon.getImage();
                System.out.println("Logo cargado desde: " + archivoLogoPng.getAbsolutePath());
            } else if (archivoLogoJpg.exists()) {
                ImageIcon logoIcon = new ImageIcon(archivoLogoJpg.getAbsolutePath());
                logoImagen = logoIcon.getImage();
                System.out.println("Logo cargado desde: " + archivoLogoJpg.getAbsolutePath());
            } else {
                System.out.println("No se encontró el logo");
                logoImagen = null;
            }
        } catch (Exception e) {
            System.out.println("Error cargando logo: " + e.getMessage());
            logoImagen = null;
        }
        
        // Cargar fondo para el panel derecho
        try {
            String rutaFondo = "resources/imagenes/FondoBlanco.png";
            File archivoFondo = new File(rutaFondo);
            if (archivoFondo.exists()) {
                ImageIcon fondoIcon = new ImageIcon(archivoFondo.getAbsolutePath());
                fondoImagen = fondoIcon.getImage();
                System.out.println("Fondo cargado desde: " + archivoFondo.getAbsolutePath());
            } else {
                fondoImagen = null;
            }
        } catch (Exception e) {
            System.out.println("Error cargando fondo: " + e.getMessage());
            fondoImagen = null;
        }
    }
    
    /**
     * Configura los componentes principales
     */
    private void configurarComponentes() {
        // Configurar tamaños de paneles
        Encabezado.setPreferredSize(new Dimension(1280, 100));
        BarraNav.setPreferredSize(new Dimension(280, 570));
        // PiePag.setPreferredSize(new Dimension(1280, 50)); // Ya no es necesario

        // Configurar el panel de interfaz con fondo
        Interfaz.setLayout(new BorderLayout());
        configurarPanelInterfaz();

        // Configurar cabecera con logo y título
        configurarCabecera();

        // Comentar o eliminar esta línea
        // configurarPiePagina();

        // Configurar botones de navegación
        configurarBotonesNavegacion();
    }
    
    /**
     * Configura el panel de interfaz con fondo blanco
     */
    private void configurarPanelInterfaz() {
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondoImagen != null) {
                    g.drawImage(fondoImagen, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelFondo.setLayout(new BorderLayout());
        
        // Limpiar y configurar Interfaz
        Interfaz.removeAll();
        Interfaz.setLayout(new BorderLayout());
        Interfaz.add(panelFondo, BorderLayout.CENTER);
        Interfaz.revalidate();
        Interfaz.repaint();
    }
    
    /**
     * Configura la cabecera con logo, título y rol
     */
    private void configurarCabecera() {
        Encabezado.removeAll();
        Encabezado.setLayout(new BorderLayout());
        Encabezado.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(217, 217, 217)));
        Encabezado.setBackground(Color.WHITE);
        
        // Panel izquierdo - Logo
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.X_AXIS));
        panelIzquierdo.setBorder(new EmptyBorder(10, 20, 10, 10));
        
        if (logoImagen != null) {
            Image logoEscalado = logoImagen.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(logoEscalado));
            panelIzquierdo.add(lblLogo);
            panelIzquierdo.add(Box.createRigidArea(new Dimension(15, 0)));
        }
        
        // Panel central - Títulos
        JPanel panelTitulos = new JPanel();
        panelTitulos.setOpaque(false);
        panelTitulos.setLayout(new BoxLayout(panelTitulos, BoxLayout.Y_AXIS));
        
        JLabel lblTitulo = new JLabel("LA CASA DEL SABOR");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(168, 27, 29));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("PIZZERÍA");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubtitulo.setForeground(new Color(74, 74, 74));
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelTitulos.add(lblTitulo);
        panelTitulos.add(Box.createRigidArea(new Dimension(0, 5)));
        panelTitulos.add(lblSubtitulo);
        
        // Panel derecho - Rol de usuario
        JPanel panelDerecho = new JPanel();
        panelDerecho.setOpaque(false);
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBorder(new EmptyBorder(10, 10, 10, 20));
        panelDerecho.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JLabel lblRol = new JLabel("ROL: " + (rolUsuario != null ? rolUsuario.toUpperCase() : "GERENTE"));
        lblRol.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblRol.setForeground(new Color(100, 100, 100));
        lblRol.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JLabel lblNombre = new JLabel(nombreUsuario != null ? nombreUsuario : "Gerente");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNombre.setForeground(new Color(168, 27, 29));
        lblNombre.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        panelDerecho.add(Box.createVerticalGlue());
        panelDerecho.add(lblRol);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 5)));
        panelDerecho.add(lblNombre);
        panelDerecho.add(Box.createVerticalGlue());
        
        // Agregar paneles a la cabecera
        Encabezado.add(panelIzquierdo, BorderLayout.WEST);
        Encabezado.add(panelTitulos, BorderLayout.CENTER);
        Encabezado.add(panelDerecho, BorderLayout.EAST);
        
        Encabezado.revalidate();
        Encabezado.repaint();
    }
    
    /**
     * Configura el pie de página
     
    private void configurarPiePagina() {
        PiePag.removeAll();
        PiePag.setLayout(new BorderLayout());
        PiePag.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(217, 217, 217)));
        PiePag.setBackground(Color.WHITE);
        
        JLabel lblEquipo = new JLabel("Equipo StarTech - Desarrollo de Software");
        lblEquipo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEquipo.setForeground(new Color(100, 100, 100));
        lblEquipo.setBorder(new EmptyBorder(0, 20, 0, 0));
        
        JLabel lblVersion = new JLabel("Versión: 1.0.0");
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblVersion.setForeground(new Color(100, 100, 100));
        lblVersion.setBorder(new EmptyBorder(0, 0, 0, 20));
        
        PiePag.add(lblEquipo, BorderLayout.WEST);
        PiePag.add(lblVersion, BorderLayout.EAST);
        
        PiePag.revalidate();
        PiePag.repaint();
    }
    
    /**
     * Configura los botones de navegación
     */
    private void configurarBotonesNavegacion() {
        JButton[] botones = {btnInicio, btnUsuarios, btnFinanzas, btnMenu, btnInvetario, btnReportes, btnCerrar};
        
        for (JButton boton : botones) {
            boton.setBackground(Color.WHITE);
            boton.setForeground(new Color(60, 60, 60));
            boton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            boton.setHorizontalAlignment(SwingConstants.LEFT);
            boton.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 15));
            boton.setFocusPainted(false);
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            boton.setMaximumSize(new Dimension(280, 50));
            boton.setMinimumSize(new Dimension(280, 50));
            boton.setPreferredSize(new Dimension(280, 50));
        }
    }
    
    /**
     * Configura el efecto hover para los botones
     */
    private void configurarHover() {
        JButton[] botones = {btnInicio, btnUsuarios, btnFinanzas,
                            btnMenu, btnInvetario, btnReportes, btnCerrar};
        
        for (JButton b : botones) {
            b.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (b != btnActivo) {
                        b.setBackground(new Color(245, 245, 245));
                        b.setForeground(new Color(168, 27, 29));
                    }
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (b != btnActivo) {
                        b.setBackground(Color.WHITE);
                        b.setForeground(new Color(60, 60, 60));
                    }
                }
            });
        }
    }
    
    /**
     * Activa visualmente un botón
     */
    private void activarBoton(JButton boton) {
        if (btnActivo != null) {
            btnActivo.setBackground(Color.WHITE);
            btnActivo.setForeground(new Color(60, 60, 60));
            btnActivo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        }
        
        boton.setBackground(new Color(168, 27, 29));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnActivo = boton;
    }
    
    /**
     * Carga un panel en el área de interfaz
     */
    private void cargarPanel(JPanel panel) {
        // Limpiar el panel Interfaz
        Interfaz.removeAll();

        // Configurar layout
        Interfaz.setLayout(new java.awt.BorderLayout());

        // Hacer que el panel se expanda
        panel.setOpaque(false);  // Importante: para ver el fondo
        Interfaz.add(panel, java.awt.BorderLayout.CENTER);

        // Forzar actualización
        Interfaz.revalidate();
        Interfaz.repaint();
    }
    
    /**
    * Verifica deudas próximas a vencer (menos de 7 días) y muestra advertencia
    */
    private void verificarDeudasProximas() {
       try {
           pizzeria.controller.GestorFinanzas gestorFinanzas = new pizzeria.controller.GestorFinanzas();
           gestorFinanzas.cargarArchivos();

           LocalDate hoy = LocalDate.now();
           LocalDate limite = hoy.plusDays(7);

           List<pizzeria.model.Deuda> deudasPendientes = gestorFinanzas.getDeudasPendientes();
           List<pizzeria.model.Deuda> deudasProximas = new ArrayList<>();

           for (pizzeria.model.Deuda d : deudasPendientes) {
               LocalDate fechaVencimiento = d.getFechaCompromiso().toLocalDate();
               if (!fechaVencimiento.isBefore(hoy) && !fechaVencimiento.isAfter(limite)) {
                   deudasProximas.add(d);
               }
           }

           if (!deudasProximas.isEmpty()) {
               StringBuilder mensaje = new StringBuilder();
               mensaje.append(" ATENCIÓN: Tiene deudas por vencer en los próximos 7 días \n\n");

               double totalProximas = 0;
               for (pizzeria.model.Deuda d : deudasProximas) {
                   LocalDate fechaVencimiento = d.getFechaCompromiso().toLocalDate();
                   long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(hoy, fechaVencimiento);
                   mensaje.append(String.format("• ID: %d | %s | Bs. %.2f | Vence en %d día%s | %s\n",
                       d.getId(),
                       d.getTipo(),
                       d.getMontoTotal(),
                       diasRestantes,
                       diasRestantes == 1 ? "" : "s",
                       d.getDescripcion()));
                   totalProximas += d.getMontoTotal();
               }

               mensaje.append("\n").append("═".repeat(50)).append("\n");
               mensaje.append(String.format("TOTAL DEUDAS PRÓXIMAS: Bs. %.2f\n", totalProximas));
               mensaje.append("\nPor favor, revise el módulo de Finanzas para gestionar los pagos.");

               JOptionPane.showMessageDialog(this,
                   mensaje.toString(),
                   "Deudas por Vencer",
                   JOptionPane.WARNING_MESSAGE);
           }
       } catch (Exception e) {
           System.out.println("Error al verificar deudas próximas: " + e.getMessage());
       }
   }
    
    /**
     * Limpia el área de interfaz (muestra solo el fondo)
     */
    private void limpiarPanel() {
        JPanel panelFondo = (JPanel) Interfaz.getComponent(0);
        panelFondo.removeAll();
        panelFondo.revalidate();
        panelFondo.repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Interfaz = new javax.swing.JPanel();
        PiePag = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BarraNav = new javax.swing.JPanel();
        btnInicio = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnFinanzas = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        btnInvetario = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        Encabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Rol = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1280, 720));

        Interfaz.setBackground(new java.awt.Color(255, 255, 255));
        Interfaz.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Interfaz.setLayout(new java.awt.BorderLayout());

        PiePag.setBackground(new java.awt.Color(255, 255, 255));
        PiePag.setPreferredSize(new java.awt.Dimension(1280, 50));
        PiePag.setLayout(new java.awt.BorderLayout());

        jLabel5.setText("Logo");
        PiePag.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(74, 74, 74));
        jLabel2.setText("Powered by StarTech");
        PiePag.add(jLabel2, java.awt.BorderLayout.CENTER);

        BarraNav.setBackground(new java.awt.Color(255, 255, 255));

        btnInicio.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnInicio.setText("Inicio");
        btnInicio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnInicio.addActionListener(this::btnInicioActionPerformed);

        btnUsuarios.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnUsuarios.setText("Gestión de Usuarios");
        btnUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnUsuarios.addActionListener(this::btnUsuariosActionPerformed);

        btnFinanzas.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnFinanzas.setText("Finanzas");
        btnFinanzas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFinanzas.addActionListener(this::btnFinanzasActionPerformed);

        btnMenu.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnMenu.setText("Gestión Menú");
        btnMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMenu.addActionListener(this::btnMenuActionPerformed);

        btnInvetario.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnInvetario.setText("Inventario");
        btnInvetario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnInvetario.addActionListener(this::btnInvetarioActionPerformed);

        btnReportes.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnReportes.setText("Reportes");
        btnReportes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReportes.addActionListener(this::btnReportesActionPerformed);

        btnCerrar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnCerrar.setText("Cerrar Sesión");
        btnCerrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCerrar.addActionListener(this::btnCerrarActionPerformed);

        javax.swing.GroupLayout BarraNavLayout = new javax.swing.GroupLayout(BarraNav);
        BarraNav.setLayout(BarraNavLayout);
        BarraNavLayout.setHorizontalGroup(
            BarraNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarraNavLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BarraNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFinanzas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvetario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReportes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        BarraNavLayout.setVerticalGroup(
            BarraNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarraNavLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFinanzas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInvetario, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Encabezado.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 35)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(168, 27, 29));
        jLabel1.setText("LA CASA DEL SABOR");

        jLabel3.setText("LOGO");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(74, 74, 74));
        jLabel4.setText("PIZZERIA");

        Rol.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Rol.setText("Rol: Usuario");

        javax.swing.GroupLayout EncabezadoLayout = new javax.swing.GroupLayout(Encabezado);
        Encabezado.setLayout(EncabezadoLayout);
        EncabezadoLayout.setHorizontalGroup(
            EncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EncabezadoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 798, Short.MAX_VALUE)
                .addComponent(Rol, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        EncabezadoLayout.setVerticalGroup(
            EncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EncabezadoLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(EncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EncabezadoLayout.createSequentialGroup()
                        .addGroup(EncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Rol, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Encabezado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(BarraNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Interfaz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PiePag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(Encabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BarraNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Interfaz, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PiePag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        activarBoton(btnInicio);
        limpiarPanel();
        
        JLabel lblBienvenida = new JLabel("Bienvenido al Panel de Gerente", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBienvenida.setForeground(new Color(168, 27, 29));
        JPanel panelBienvenida = new JPanel(new BorderLayout());
        panelBienvenida.setOpaque(false);
        panelBienvenida.add(lblBienvenida, BorderLayout.CENTER);
        cargarPanel(panelBienvenida);
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        
        activarBoton(btnUsuarios);

        // Crear el panel de gestión de usuarios
        GestionUsuariosGUI panelUsuarios = new GestionUsuariosGUI();

        // Cargarlo en el área central (Interfaz)
        cargarPanel(panelUsuarios);
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnFinanzasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinanzasActionPerformed
        activarBoton(btnFinanzas);
        GestionFinanzasGUI panelFinanzas = new GestionFinanzasGUI();
        cargarPanel(panelFinanzas);;
    }//GEN-LAST:event_btnFinanzasActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        activarBoton(btnMenu);
        
        JLabel lblMensaje = new JLabel("Gestión de Menú - Próximamente", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblMensaje.setForeground(new Color(168, 27, 29));
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(lblMensaje, BorderLayout.CENTER);
        cargarPanel(panel);
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnInvetarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvetarioActionPerformed
        new InventarioPantallaInicial().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnInvetarioActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        activarBoton(btnReportes);
        ReportesGUI panelReportes = new ReportesGUI();
        cargarPanel(panelReportes);
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(
            this, "¿Desea cerrar sesión?", "Cerrar Sesión",
            JOptionPane.YES_NO_OPTION
        );
        if (respuesta == JOptionPane.YES_OPTION) {
            this.dispose();
            new PantallaInicial().setVisible(true);
        }
    }//GEN-LAST:event_btnCerrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        java.awt.EventQueue.invokeLater(() -> new InterfazGerenteP1("GERENTE", "Administrador").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarraNav;
    private javax.swing.JPanel Encabezado;
    private javax.swing.JPanel Interfaz;
    private javax.swing.JPanel PiePag;
    private javax.swing.JLabel Rol;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnFinanzas;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnInvetario;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
