package Vista;

import Controlador.ControladorAsistencia;
import Modelo.Asistencia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VistaAsistencia extends javax.swing.JFrame {
    
    private ControladorAsistencia controladorAsistencia;
    private int idUsuarioActual;
    private String nombreUsuario;
    
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblFecha;
    private JLabel lblHora;
    private JButton btnMarcarEntrada;
    private JButton btnMarcarSalida;
    private JButton btnVerEstado;
    private JButton btnVerHistorial;
    private JButton btnCerrarSesion;
    private JTable tablaAsistencias;
    private JScrollPane scrollPane;
    private JLabel lblEstado;
    
    public VistaAsistencia(int idUsuario, String nombreUsuario) {
        this.idUsuarioActual = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.controladorAsistencia = new ControladorAsistencia();
        
        initComponents();
        actualizarInterfaz();
        cargarHistorialAsistencias();
    }
    
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Control de Asistencia");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        lblTitulo = new JLabel("CONTROL DE ASISTENCIA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        panelSuperior.add(lblTitulo, gbc);
        
        lblUsuario = new JLabel("Usuario: " + nombreUsuario);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        panelSuperior.add(lblUsuario, gbc);
        
        lblFecha = new JLabel("Fecha: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1; gbc.gridy = 1;
        panelSuperior.add(lblFecha, gbc);
        
        lblHora = new JLabel("Hora: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        lblHora.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 2; gbc.gridy = 1;
        panelSuperior.add(lblHora, gbc);
        
        lblEstado = new JLabel("Estado: Verificando...");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
        lblEstado.setForeground(Color.BLUE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 0, 20, 0);
        panelSuperior.add(lblEstado, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        btnMarcarEntrada = new JButton("MARCAR ENTRADA");
        btnMarcarEntrada.setFont(new Font("Arial", Font.BOLD, 12));
        btnMarcarEntrada.setBackground(Color.GREEN);
        btnMarcarEntrada.setForeground(Color.WHITE);
        btnMarcarEntrada.setPreferredSize(new Dimension(150, 40));
        btnMarcarEntrada.addActionListener(e -> marcarEntrada());
        panelBotones.add(btnMarcarEntrada);
        
        btnMarcarSalida = new JButton("MARCAR SALIDA");
        btnMarcarSalida.setFont(new Font("Arial", Font.BOLD, 12));
        btnMarcarSalida.setBackground(Color.RED);
        btnMarcarSalida.setForeground(Color.WHITE);
        btnMarcarSalida.setPreferredSize(new Dimension(150, 40));
        btnMarcarSalida.addActionListener(e -> marcarSalida());
        panelBotones.add(btnMarcarSalida);
        
        btnVerEstado = new JButton("VER ESTADO");
        btnVerEstado.setFont(new Font("Arial", Font.BOLD, 12));
        btnVerEstado.setBackground(Color.BLUE);
        btnVerEstado.setForeground(Color.WHITE);
        btnVerEstado.setPreferredSize(new Dimension(120, 40));
        btnVerEstado.addActionListener(e -> verEstado());
        panelBotones.add(btnVerEstado);
        
        btnCerrarSesion = new JButton("CERRAR SESION");
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrarSesion.setBackground(Color.RED);
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setPreferredSize(new Dimension(150, 40));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        panelBotones.add(btnCerrarSesion);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        panelSuperior.add(panelBotones, gbc);
        
        // Tabla de historial
        String[] columnas = {"Fecha", "Hora Entrada", "Hora Salida", "Duracion", "Estado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaAsistencias = new JTable(modeloTabla);
        
        tablaAsistencias.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaAsistencias.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaAsistencias.setRowHeight(30);
        tablaAsistencias.setSelectionBackground(Color.LIGHT_GRAY);
        tablaAsistencias.setGridColor(Color.GRAY);
        tablaAsistencias.setShowGrid(true);
        tablaAsistencias.setShowHorizontalLines(true);
        tablaAsistencias.setShowVerticalLines(true);
        
        tablaAsistencias.getColumnModel().getColumn(0).setPreferredWidth(100); // Fecha
        tablaAsistencias.getColumnModel().getColumn(1).setPreferredWidth(100); // Hora Entrada
        tablaAsistencias.getColumnModel().getColumn(2).setPreferredWidth(100); // Hora Salida
        tablaAsistencias.getColumnModel().getColumn(3).setPreferredWidth(80);  // Duracion
        tablaAsistencias.getColumnModel().getColumn(4).setPreferredWidth(100); // Estado
        
        tablaAsistencias.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, 
                int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
        
        scrollPane = new JScrollPane(tablaAsistencias);
        scrollPane.setPreferredSize(new Dimension(750, 250));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Historial de Asistencias"));
        scrollPane.setBackground(Color.WHITE);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 0, 0, 0);
        panelSuperior.add(scrollPane, gbc);
        
        panelPrincipal.add(panelSuperior, BorderLayout.CENTER);
        add(panelPrincipal);
        
        // Asegurar que la tabla sea visible
        tablaAsistencias.setVisible(true);
        scrollPane.setVisible(true);
        this.setVisible(true);
        
        // Timer para actualizar la hora cada segundo
        Timer timer = new Timer(1000, e -> actualizarHora());
        timer.start();
    }
    
    private void actualizarInterfaz() {
        String estado = controladorAsistencia.obtenerEstadoAsistencia(idUsuarioActual);
        
        switch (estado) {
            case "PENDIENTE_ENTRADA":
                lblEstado.setText("Estado: Pendiente de entrada");
                lblEstado.setForeground(Color.ORANGE);
                btnMarcarEntrada.setEnabled(true);
                btnMarcarSalida.setEnabled(false);
                break;
            case "PENDIENTE_SALIDA":
                lblEstado.setText("Estado: Pendiente de salida");
                lblEstado.setForeground(Color.BLUE);
                btnMarcarEntrada.setEnabled(false);
                btnMarcarSalida.setEnabled(true);
                break;
            case "COMPLETO":
                lblEstado.setText("Estado: Asistencia completa");
                lblEstado.setForeground(Color.GREEN);
                btnMarcarEntrada.setEnabled(false);
                btnMarcarSalida.setEnabled(false);
                break;
            default:
                lblEstado.setText("Estado: Error");
                lblEstado.setForeground(Color.RED);
                btnMarcarEntrada.setEnabled(false);
                btnMarcarSalida.setEnabled(false);
                break;
        }
    }
    
    private void actualizarHora() {
        lblHora.setText("Hora: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
    
    private void marcarEntrada() {
        String resultado = controladorAsistencia.marcarEntrada(idUsuarioActual);
        JOptionPane.showMessageDialog(this, resultado, "Marcar Entrada", JOptionPane.INFORMATION_MESSAGE);
        actualizarInterfaz();
        cargarHistorialAsistencias();
    }
    
    private void marcarSalida() {
        String resultado = controladorAsistencia.marcarSalida(idUsuarioActual);
        JOptionPane.showMessageDialog(this, resultado, "Marcar Salida", JOptionPane.INFORMATION_MESSAGE);
        actualizarInterfaz();
        cargarHistorialAsistencias();
    }
    
    private void verEstado() {
        controladorAsistencia.mostrarEstadoAsistencia(idUsuarioActual);
        actualizarInterfaz();
    }
    
    private void cargarHistorialAsistencias() {
        List<Asistencia> asistencias = controladorAsistencia.obtenerAsistenciasUsuario(idUsuarioActual);
        
        // Crear matriz como en VistaAdmin
        String matriz[][] = new String[asistencias.size()][5];
        
        for (int i = 0; i < asistencias.size(); i++) {
            Asistencia asistencia = asistencias.get(i);
    
            matriz[i][0] = asistencia.getFecha();
            matriz[i][1] = asistencia.getHoraEntrada() != null ? asistencia.getHoraEntrada() : "N/A";
            matriz[i][2] = asistencia.getHoraSalida() != null ? asistencia.getHoraSalida() : "N/A";
            matriz[i][3] = asistencia.getTiempoTrabajado();
            matriz[i][4] = asistencia.getEstado();
        }
        
        // Usar setModel exactamente como en VistaAdmin
        tablaAsistencias.setModel(new javax.swing.table.DefaultTableModel(matriz, new String[] { "Fecha", "Hora Entrada", "Hora Salida", "Duracion", "Estado" }));
        
        // Forzar actualización visual - patrón de VistaAdmin
        tablaAsistencias.repaint();
        tablaAsistencias.revalidate();
        scrollPane.repaint();
        scrollPane.revalidate();
        this.repaint();
        this.revalidate();
        
        // Asegurar que la tabla sea visible
        tablaAsistencias.setVisible(true);
        scrollPane.setVisible(true);
        
        System.out.println("DEBUG: Tabla actualizada con matriz - Filas: " + matriz.length);
    }
    
    private void cerrarSesion() {
        // Cerrar sesión y volver a la ventana de login
        this.dispose();
        // Aquí podrías agregar lógica adicional para cerrar sesión si es necesario
        // Por ejemplo, limpiar datos de sesión, etc.
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Crear instancia con datos de prueba para testing
                new VistaAsistencia(1, "Usuario Prueba").setVisible(true);
            }
        });
    }
}
