

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or  
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.  
import java.io.*;  
  
public class Main {  
  
    public static void main(String[] args) throws IOException {  
        BufferedReader br = new BufferedReader(new FileReader("datos.txt"));  
        String linea;  
        while ((linea = br.readLine()) != null) {  
            System.out.println(linea);  
        }        br.close();  
    }}