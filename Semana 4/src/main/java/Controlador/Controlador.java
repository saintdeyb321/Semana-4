/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Estudiante;
import Modelo.EstudianteArray;
import Vista.IRegistro;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author USER 17
 */
public class Controlador {
    private EstudianteArray gestor;
    private IRegistro vista;

    public Controlador(IRegistro vista, EstudianteArray gestor) {
        this.vista = vista;
        this.gestor = gestor;
        
        

        // Añadir DocumentListener al campo de búsqueda
        this.vista.getTxtBuscador().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarPorNombre();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarPorNombre();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscarPorNombre();
            }
        });
    }
    
    public void guardarEstudiante() {
    // Validar campos
    if (vista.getTxtCodigo().getText().isEmpty() || vista.getTxtNombre().getText().isEmpty() || 
        vista.getTxtApellido().getText().isEmpty() || vista.getCboFacultad().getSelectedItem() == null || 
        vista.getCboProvincia().getSelectedItem() == null || vista.getCalendario().getDate() == null) {
        JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
        return;
    }

    Estudiante estudiante = new Estudiante();
    estudiante.setCodigo(vista.getTxtCodigo().getText());
    estudiante.setNombre(vista.getTxtNombre().getText());
    estudiante.setApellidos(vista.getTxtApellido().getText());
    estudiante.setFacultad(vista.getCboFacultad().getSelectedItem().toString());
    estudiante.setProvincia(vista.getCboProvincia().getSelectedItem().toString());

    // Obtener fecha de nacimiento y calcular la edad
    Date fechaNacimiento = vista.getCalendario().getDate();
    int edad = Modelo.EstudianteArray.calcularEdad(fechaNacimiento);  // Calcular la edad en función de la fecha de nacimiento
    estudiante.setEdad(edad);  // Asignar la edad al estudiante

    gestor.agregarEstudiante(estudiante, fechaNacimiento);

    // Guardar el estudiante en el archivo .txt incluyendo la edad
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Estudiante.txt", true))) {
        writer.write(estudiante.getCodigo() + "," + estudiante.getNombre() + "," + estudiante.getApellidos() + "," 
                + estudiante.getFacultad() + "," + estudiante.getProvincia() + "," + estudiante.getEdad());
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
    }
    JOptionPane.showMessageDialog(null, "Los datos se guardaron con exito :D.");
    gestor.limpiar(vista.getTxtCodigo(), vista.getTxtNombre(), vista.getTxtApellido(), vista.getCboFacultad(), vista.getCboProvincia(), vista.getCalendario());
    gestor.actualizarTabla();
}

    
public void cargarEstudiantesDesdeArchivo() {
    try (BufferedReader reader = new BufferedReader(new FileReader("Estudiante.txt"))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split(","); // Asegúrate de que los datos están en el formato correcto
            if (datos.length == 6) { // Asegurarse de que haya 6 elementos antes de continuar
                Estudiante estudiante = new Estudiante();
                estudiante.setCodigo(datos[0]); // Código
                estudiante.setNombre(datos[1]); // Nombre
                estudiante.setApellidos(datos[2]); // Apellidos
                estudiante.setFacultad(datos[3]); // Facultad
                estudiante.setProvincia(datos[4]); // Provincia

                // Asignar directamente la edad leída desde el archivo
                int edad = Integer.parseInt(datos[5].trim()); // Convertir de String a int, asegurando que no haya espacios
                estudiante.setEdad(edad); // Establecer la edad del estudiante

                // Agregar el estudiante
                gestor.agregarEstudiante(estudiante, new Date());  // La fecha no se necesita aquí
            }
        }
            JOptionPane.showMessageDialog(null, "Se cargaron datos correctamente :D.");

        gestor.actualizarTabla();  // Actualizar la tabla después de cargar los datos
    } catch (IOException e) {
        e.printStackTrace();
    } catch (NumberFormatException e) {
        System.out.println("Error de formato de número al convertir la edad.");
        e.printStackTrace();
    }
}




    public void eliminarEstudiante() {
        String codigo = vista.getTxtCodigo().getText();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese el código del estudiante a eliminar.");
            return;
        }

        gestor.eliminarEstudiante(codigo);
        gestor.actualizarTabla();
    }

    public void buscarPorNombre() {
        String nombreBuscado = vista.getTxtBuscador().getText();
        gestor.buscarPorNombre(nombreBuscado); // Actualiza la tabla según el texto buscado
    }

    public void eliminarTodosEstudiantes() {
        gestor.eliminarTodosEstudiantes();
    }

    public void verificarLista() {
        gestor.verificarListaVacia();
    }
}