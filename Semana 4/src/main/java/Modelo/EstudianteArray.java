/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER 17
 */
public class EstudianteArray {
    private ArrayList<Estudiante> listaEstudiante;
    private DefaultTableModel modelo;

    // Constructor que acepta DefaultTableModel
    public EstudianteArray(DefaultTableModel modelo) {
        this.listaEstudiante = new ArrayList<>();
        this.modelo = modelo;
    }

    public void agregarEstudiante(Estudiante estudiante, Date fecha) {
    listaEstudiante.add(estudiante);
    // Aquí también deberías asegurarte de que el modelo se esté actualizando correctamente
    modelo.addRow(new Object[]{
        estudiante.getCodigo(),
        estudiante.getNombre(),
        estudiante.getApellidos(),
        estudiante.getFacultad(),
        estudiante.getEdad(), // Asegúrate de que este valor no esté siendo modificado
        estudiante.getProvincia()
    });
}



    // Método para calcular la edad en función de la fecha de nacimiento
    public static int calcularEdad(Date fechaNacimiento) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(fechaNacimiento);
    int añoNacimiento = cal.get(Calendar.YEAR);
    int añoActual = Calendar.getInstance().get(Calendar.YEAR);
    return añoActual - añoNacimiento;
}

    public void actualizarTabla() {
    modelo.setRowCount(0); // Limpiar la tabla existente

    for (Estudiante estudiante : listaEstudiante) {
        modelo.addRow(new Object[]{
            estudiante.getCodigo(),
            estudiante.getNombre(),
            estudiante.getApellidos(),
            estudiante.getFacultad(),
            estudiante.getEdad(), // Asegúrate de que aquí estás obteniendo el valor correcto
            estudiante.getProvincia()
        });
    }
}

    // Método para limpiar los campos del formulario
    public void limpiar(javax.swing.JTextField txtCodigo, javax.swing.JTextField txtNombre,
                                  javax.swing.JTextField txtApellido, javax.swing.JComboBox<String> cboFacultad,
                                  javax.swing.JComboBox<String> cboProvincia, com.toedter.calendar.JCalendar calendario) {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        cboFacultad.setSelectedIndex(-1);
        cboProvincia.setSelectedIndex(-1);
        calendario.setDate(null);
    }
    
    public void eliminarEstudiante(String codigo) {
        // Eliminar el estudiante de la lista
        listaEstudiante.removeIf(e -> e.getCodigo().equals(codigo));
    }


    
   public void buscarPorNombre(String nombre) {
    // Limpiar la tabla actual
    modelo.setRowCount(0);

    // Filtrar la lista por nombre y agregar los resultados a la tabla
    for (Estudiante e : listaEstudiante) {
        if (e.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
            modelo.addRow(new Object[]{e.getCodigo(), e.getNombre(), e.getApellidos(), e.getFacultad(), e.getEdad(), e.getProvincia()});
        }
    }
}
   
   public void eliminarTodosEstudiantes() {
    System.out.println("Gestor: eliminarTodosEstudiantes llamado");
    listaEstudiante.clear();
    JOptionPane.showMessageDialog(null, "Todos los estudiantes han sido eliminados.");
    actualizarTabla();
}
   
   public void verificarListaVacia() {
    if (listaEstudiante.isEmpty()) {
        JOptionPane.showMessageDialog(null, "La lista de estudiantes está vacía.");
    } else {
        JOptionPane.showMessageDialog(null, "La lista de estudiantes tiene " + listaEstudiante.size() + " estudiante(s).");
    }
}
    
    public ArrayList<Estudiante> getListaEstudiantes() {
        return listaEstudiante;
    }
}