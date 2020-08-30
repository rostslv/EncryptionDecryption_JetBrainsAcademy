# EncryptionDecryption_JetBrainsAcademy
Program to encrypt and decrypt text with Unicode and Caesar methods (check README how to use)  
There is console app. You shoUld use something like "cmd" or another console. To run the program, go to the directory where the Main.java file is located.  
eg. -cd /d "...."
Then you can use arguments:  
-mode "enc/dec" - Choose Encryption or Decryption. If no argument is given the default will be "enc".
-key "0...999999" - Choose shift. If no argument is given the default will be 0.
-in "toDecrypt.txt" - Source text from file, should be in the package com.skorbr.testandlearn  
-out "file.txt" - The result will be written to this file, if it does not exist, then it will be created. If no argument is given output will be in console line.
-data "Hello Java!" - Source text from this line. If no argument is given the default will be empty line. If given -data and -in input will from -data.
-alg "shift/unicode" - Choose of encryption algorithm. If no argument is given the default will be "shift".
  
