import DAO.ConsultasDAO;

public class Main {
    public static void main(String[] args) {
        ConsultasDAO consultasDAO = new ConsultasDAO();
        consultasDAO.mostrarEmpleados();
        consultasDAO.mostrarMetaDataCOnResulSet();
        consultasDAO.mostrarMetaDataConDatabaseMetaData();
    }
}
