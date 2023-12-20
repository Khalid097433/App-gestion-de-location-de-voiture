package Client;

public class Decryption {
    
     public static String Decrypt(String T, String K){

   
         String HexToDeci = "";
         
         for(int i = 0; i < T.length()-1; i += 5){
             
             String output = T.substring(i, (i + 5));
             int decimal = Integer.parseInt(output, 16);
             HexToDeci += (char)decimal;
         }
         
         String decryptText = "";
         
          int keyItr = 0;
        
        for(int i = 0; i < HexToDeci.length(); i++){
            
            int temp = HexToDeci.charAt(i) ^ K.charAt(keyItr);
            
            decryptText += (char)temp;
            keyItr++;
            
            if(keyItr >= K.length()){
                
                keyItr = 0;
            }
        }
         
         return decryptText;
         
   
}
    
    
    
    
}
   
