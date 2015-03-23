package SecurityProtokol;

public class Decrypt 
{
	 char[][] key ={{'Q','W','E','R','T'},
                    {'M','Y','U','I','O'},
                    {'P','A','S','D','F'},
	                {'G','H','J','K','L'},
	                {'X','C','V','B','N'}};
	 
	 final int N = 5;
	 final int arrayLimit = N-1;
	 final int arrayStart = 0;
	 private StringBuilder decryptedText;
	 
	 public Decrypt(String text) {
		 if(text != null && !text.equals(""))
		  encrypt(new StringBuilder(text.toUpperCase()));
	 }
	 
	private void caseOneTwoThree(StringBuilder encrypted, char a, char b) // checks all other cases 1,2,3
	   throws IndexOutOfBoundsException { 
	    	int rowA = arrayStart, rowB = arrayStart;
	    	int columnA = arrayStart, columnB = arrayStart;
	    	
	    	if(a == 'Z') // if the letter a is 'Z'
	    	{
	    		encrypted.append("Z");
	    		encrypted.append(b);
	    	}
	    	if(b == 'Z') // if the letter b is 'Z'
	    	{
	    		encrypted.append(a);
	    		encrypted.append("Z");
	    	}
	    	if(a != 'Z' && b != 'Z')
	    	{
	    	   for(int i = 0;i<N;i++)
	             for(int j = 0;j<N;j++)
	             {
	              if(key[i][j]== a)
	              {
	            	  rowA = i;   // save row index
	            	  columnA = j;   // save column index
	              }
	              if(key[i][j]== b)
	              {
	            	  rowB = i;   // save row index
	            	  columnB = j;  // save column index
	              }
	            }	
	     	  if( rowA ==  rowB)   // case 3 
	    	   {
	    		if(columnA == arrayStart)
	    			encrypted.append(key[rowA][arrayLimit]);
	    		else
	    			encrypted.append(key[rowA][columnA-1]);
	    	    if(columnB == arrayStart)
	        	    encrypted.append(key[rowB][arrayLimit]);
	        	else
	        		encrypted.append(key[rowB][columnB-1]);
	    	   }
	    	  if(columnA == columnB) // case 2
	    	  {
	    		if(rowA == arrayLimit)
	    			encrypted.append(key[arrayStart][columnA]);
	    		else
	    			encrypted.append(key[rowA+1][columnA]);
	    	    if(rowB == arrayLimit)
	    	    	encrypted.append(key[arrayStart][columnA]);
	        	else
	        		encrypted.append(key[rowB+1][columnA]);
	    	   }
	    	  if((rowA != rowB)&&(columnA != columnB)) //case 1
	    	   {
	    		encrypted.append(key[rowA][columnB]); 
	    		encrypted.append(key[rowB][columnA]);
	    	   }
	    	}
	    }

  private void caseFour(StringBuilder encrypted,char a)  // if a == b
     throws IndexOutOfBoundsException {  
	   if(a == 'Z')  // if the letters a and b are 'Z'
   		 encrypted.append("ZZ");
	   else
	   {
		   for(int i = 0;i<N;i++)
             for(int j = 0;j<N;j++)
              if(key[i][j]==a)
      	      {
                if(i == arrayStart)
                {
            	   encrypted.append(key[arrayLimit][j]);
            	   encrypted.append(key[arrayLimit][j]);
                }
      	        else
      	        {
      	    	   encrypted.append(key[i-1][j]);
      	    	   encrypted.append(key[i-1][j]);
      	        }
      	      }
	   }
   } 
   
   private void encrypt(StringBuilder textToEncrypt) // encrypt text
	{
		try {
		      decryptedText = new  StringBuilder(); // array for encrypted text
              char a, b;

          for(int index = 0;index <textToEncrypt.length();index += 2)
            {
                  a = textToEncrypt.charAt(index); 
                  b = textToEncrypt.charAt(index+1);

                if(a == b) // if letters a and b are equal
                  caseFour(decryptedText, a);
                else
                  caseOneTwoThree(decryptedText, a, b); // checks all other cases 1,2,3
            } 
        //  System.out.println(" Encrypted text is: " + encryptedText);
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println(" There was an unexpected error!");
		}
		
	  }
   
   public String getEncryptedText() {
	   return decryptedText.toString();  
   }
}
