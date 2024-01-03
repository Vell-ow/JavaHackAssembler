import java.io.*;
import java.lang.ClassNotFoundException;
import java.lang.Integer;
import java.util.*;

public class HackAssembler
{
  
  public static void main(String[] args) 
  {
    
    // String FILE_NAME = "C:\\Users\\Andorus' Gaming PC\\Desktop\\Logic Gates\\Test ASM Files\\Add.asm";
    String FILE_NAME = "Add1toN.asm", FILE_NAME_2 = "Add1toN.hack";
    // FILE_NAME_2 = "C:\\Users\\Andorus' Gaming PC\\Desktop\\Logic Gates\\Test ASM Files\\AddAssembled.asm";    
    SymbolTable st = new SymbolTable();
    // To read a binary file: 
    // ObjectInputStream/FileInputStream
    // C:\Users\Andorus' Gaming PC\Desktop\Logic Gates\Test ASM Files
    firstPass(FILE_NAME, st);
    secondPass(FILE_NAME, st, FILE_NAME_2);
  }

  public static String decimalToBinary(int number)
  {
    int noRem = number / 2;
    int rem = number % 2;
    String conversion = "";
    char temp;
    while(noRem != 0)
    {
      rem = noRem % 2;
      noRem = noRem / 2;
      if(noRem != 0)
      {
        conversion += (noRem / 2);
      }
    conversion += rem;
    }

    if(conversion.length() < 16)
    {
      for(int i = 0; i < (16 - conversion.length()); i++)
      {
        conversion += '0';
      }
    }
    for(int i = 0; i < (conversion.length() / 2); i++)
    {
      temp = conversion.charAt(i);
      // 1
      conversion = conversion.substring(0, i) + conversion.charAt(16 - i) + conversion.substring(i + 1);
      // 2
      conversion = conversion.substring(0, 16 - i) + temp + conversion.substring((16 - i) + 1);
      // 3    
    }
    return conversion;

  }


  /**
  *
  * WARNING: this was found online! don't let old man Celly 
  * catch you
  *
  * First pass - Parser obtains labels. sets their ROM address, * and adds data pair to SymbolTable. For each label 
  * declaration (LABEL) that appears in the source code, the 
  * pair (LABEL, romAddress) is added to the SymbolTable, where 
  * the ROM address will only count by A- and C-commands.
  *
  * Precondition: inputFileName corresponds to an existing .asm 
  * file, and symbolTable has been initialized with
  * pre-defined symbols.
  * Postcondition: symbolTable is populated with both 
  * pre-defined symbols and user-defined labels.
  *
  * @param inputFileName the name of the .asm input file
  * @param symbolTable a SymbolTable initialized with the HACK 
  * computer's pre-defined symbols
  *
  **/
  public static void firstPass(String assemblyFileName, SymbolTable symbolTable)
  {
    int romAddress = 0;
    Parser p = new Parser(assemblyFileName);
    String symbol;
    // Scanner k = new Scanner(System.in);  
    // System.out.println("Type in an ASM file name: ");
    // FILE_NAME = k.nextLine();

    while(p.hasMoreCommands())
    {
      p.advance();
      switch(p.getCommandType())
      {
        case L_COMMAND:
        symbol = p.getSymbol();
        if(symbolTable.contains(symbol))
        {
          System.err.println("This symbol, " + symbol + ", is already in the symbol table! No duplicates allowed... :( ");
          System.exit(0);
        }
        if(!symbolTable.addEntry(symbol, romAddress))
        {
          System.err.println("Error: label (" + symbol + ") has an invalid name.");
          System.exit(0);
        }
        break;
        case A_COMMAND:
        case C_COMMAND:
        romAddress++;
        break;        
      }
    }   
  }

  public static void handleError(String message)
  {
    message = "Malicious input detected. Terminating abruptly.";
    try
    {
      // put something here
      System.out.println("Trying...");
    }
    catch(InputMismatchException ime)
    {
      System.out.println(message);
      System.exit(0);
    }

  }

  /**
  *
  * WARNING: this was found online! don't let old man Celly 
  * catch you
  *
  * second
  * If the current line is a C-instruction, the comp, dest, and 
  * jump parts are received from the Parser, translatedthrough 
  * Code to binary, appended to "111" and written to the output 
  * file.
  * For an A-instruction, the symbol will be translated, 
  * appended to "0" and written to the output file. If the 
  * symbol can be converted to an unsigned 15-bit binary number, 
  * then its decimal value is translated through Code.
  * If the symbol exists in the SymbolTable, its address will be 
  * read and translated into binary. Likewise, new declared 
  * symbols will be added to the SymbolTable with the next 
  * available RAM address, and said address will be translated 
  * into binary for writing into the output file.
  * Labels and non-commands are ignored.
  *
  **/
  public static void secondPass(String assemblyFileName, SymbolTable symbolTable, String binaryFileName)
  {
    // create another parser
    // actually translate the command in this method
    Parser q = new Parser(assemblyFileName);
    CInstructionMapper cim = new CInstructionMapper();
    String instruction, locationSymbol;
    // RAS = number at which the Ram Address Starts
    int ras = 16;
    // create a print writer
    try
    {
      PrintWriter binaryFile = new PrintWriter(binaryFileName);
      while(q.hasMoreCommands())
      {
        q.advance();
        switch(q.getCommandType())
        {
          case A_COMMAND:
          int number = 0;
          locationSymbol = q.getSymbol();
          try
          {
            number = Integer.parseInt(locationSymbol);
          }
          catch(NumberFormatException n)
          {
            System.err.println("The number corresponding with the symbol at this location is not formatted properly. Throwing an exception... ;(");
            System.exit(0);
          }
          if(symbolTable.contains(locationSymbol))
          {
            number = symbolTable.getAddress(locationSymbol);
          }
          else
          {
            symbolTable.addEntry(locationSymbol, ras);
            number = ras++;
          }
          instruction = decimalToBinary(number);
          binaryFile.println(instruction);
          break;
          case C_COMMAND:
          StringBuilder cStringBuilder = new StringBuilder();
          cStringBuilder.append("111");
          q.parse();
          String c = q.getCompMnemonic();
          String d = q.getDestMnemonic();
          String j = q.getJumpMnemonic();
          String compToBinary = cim.comp(c), destToBinary = cim.dest(d), jumpToBinary = cim.jump(j);
          cStringBuilder.append(compToBinary);
          cStringBuilder.append(destToBinary);
          cStringBuilder.append(jumpToBinary);
          binaryFile.println(cStringBuilder.toString());
          
          break;        
        }
        binaryFile.close();
      }
    }
    catch(FileNotFoundException fnfEx)
    {
      System.err.println("The file was not found. Terminating to prevent fatal errors.");
      System.exit(0);
    }
  }
}