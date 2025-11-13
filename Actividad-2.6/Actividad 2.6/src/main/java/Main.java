import DAO.TransferenciaBancaria;

public class Main {
    public static void main(String[] args) {
        TransferenciaBancaria transferencia = new TransferenciaBancaria();
        transferencia.hacerTransferencia();
        transferencia.transcacionConLogsYSavepoint();
    }
}
