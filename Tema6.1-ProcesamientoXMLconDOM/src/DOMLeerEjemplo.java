import javax.xml.parsers.*; // Importa clases para parsear XML.
import org.w3c.dom.*;       // Importa clases para manipular el DOM de XML.
import java.io.File;        // Importa la clase File para manejar archivos.

public class DOMLeerEjemplo {
    public static void main(String[] args) {
        try {
            // Crea un objeto File que representa el archivo XML a leer.
            File archivo = new File("datos/empleados.xml");
            // Obtiene una instancia de DocumentBuilderFactory.
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // Crea un DocumentBuilder para parsear el XML.
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Parsea el archivo XML y obtiene el documento DOM.
            Document doc = db.parse(archivo);

            // Obtiene una lista de nodos con la etiqueta "empleado".
            NodeList lista = doc.getElementsByTagName("empleado");

            // Itera sobre cada nodo "empleado" encontrado.
            for (int i = 0; i < lista.getLength(); i++) {
                // Obtiene el elemento "empleado" actual.
                Element emp = (Element) lista.item(i);
                // Obtiene el atributo "id" del empleado.
                String id = emp.getAttribute("id");
                // Obtiene el texto del primer nodo "nombre" hijo.
                String nombre = emp.getElementsByTagName("nombre").item(0).getTextContent();
                // Obtiene el texto del primer nodo "puesto" hijo.
                String puesto = emp.getElementsByTagName("puesto").item(0).getTextContent();
                // Muestra los datos del empleado por consola.
                System.out.println("ID: " + id + " | Nombre: " + nombre + " | Puesto: " + puesto);
            }

        } catch (Exception e) {
            // Si ocurre un error, muestra el mensaje por consola.
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}
