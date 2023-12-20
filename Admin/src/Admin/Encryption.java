package Admin;

import static java.lang.System.console;
import java.util.ArrayList;

public class Encryption {
   
    public static String Crypt(String T, String K){
        
        String EncryptHexa = "";
        
        int keyItr = 0;
        
        for(int i = 0; i < T.length(); i++){
            
            int temp = T.charAt(i) ^ K.charAt(keyItr);
            
            EncryptHexa += String.format("%05x", (byte)temp);
            keyItr++;
            
            if(keyItr >= K.length()){
                
                keyItr = 0;
            }
        }
        
        return EncryptHexa;
    }
   
    
   
   
   
}
