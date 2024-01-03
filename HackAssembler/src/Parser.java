import java.util.*;
import java.io.*;

public class Parser
{
  private Command commandType;

  private Scanner inputFile;
  private int lineNumber;
  private String rawLine, cleanLine, symbol, destMnemonic, compMnemonic, jumpMnemonic;

  public Parser(String infileName)
  {
    try
    {
      inputFile = new Scanner(new FileInputStream(infileName));
    }
    catch(IOException e)
    {
      System.out.println("Error opening file at Parser in Assembler. Terminating.");
      System.exit(0);
    }
  }

  /**
  * Reads the next command from the input and 
  * makes it the current command. Should be called 
  * only if hasMoreCommands is true.
  * Initially there is no current command.
  */
  public void advance()
  {
    if(hasMoreCommands())
    {
      rawLine = inputFile.nextLine();
      lineNumber++;
      cleanLine(rawLine);
      parseCommandType();
    }
  }

  // return cleanLine?
  public String getCleanLine()
  {
    return cleanLine;
  }

  // return ?_COMMAND ?
  public Command getCommandType()
  {
    return commandType;
  }

  // return compMnemonic?
  public String getCompMnemonic()
  {
    return compMnemonic;
  }

  // return DestMnemonic?
  public String getDestMnemonic()
  {
    return destMnemonic;
  }

  // return JumpMnemonic?
  public String getJumpMnemonic()
  {
    return jumpMnemonic;
  }  

  // return lineNumber?
  public int getLineNumber()
  {
    return lineNumber;
  }

  // return rawLine?
  public String getRawLine()
  {
    return rawLine;
  }

  // return symbol?
  public String getSymbol()
  {
    return symbol;
  }

  // how to determine if has more commands?
  public boolean hasMoreCommands()
  {
    return inputFile.hasNextLine();
  }

  // what do here?
  public void parse()
  {
    if(commandType == Command.A_COMMAND || commandType == Command.L_COMMAND)
    {
      parseSymbol();
    }
    else if(commandType == Command.C_COMMAND)
    {
      parseDest();
      parseComp();
      parseJump();
    }
  }
  
  private void parseCommandType()
  {
    System.out.println("Note at Parser line 113");
    //
    // NOTE: program compiles up until line 123: 
    // if(cleanLine.startsWith("@"))
    //
    // 1. grab the line in the asm file
    // 2. check to see if that line begins with a certain   
    // character
    // 3. assign the command type instance variable to the
    // matching command type of that character
    if(cleanLine.startsWith("@"))
    {
      commandType = Command.A_COMMAND;
    }
    else if(cleanLine == null || cleanLine.isEmpty())
    {
      commandType = Command.NO_COMMAND;
    }
    else if(cleanLine.startsWith("(") && cleanLine.endsWith(")"))
    {
      commandType = Command.L_COMMAND;
    }
    else
    {
      commandType = Command.C_COMMAND;
    }
  }  

  // what do here?
  public void parseComp()
  {
    int eqIndex = cleanLine.indexOf('=');
    int scIndex = cleanLine.indexOf(';');
    if(eqIndex != 1)
    {
      cleanLine = cleanLine.substring(eqIndex);
    }
    if(scIndex != 1)
    {
      cleanLine = cleanLine.substring(0, scIndex);
    }
    compMnemonic = cleanLine;
  }  

  // what do here?
  public void parseDest()
  {
    int index = cleanLine.indexOf('='); 
    destMnemonic = cleanLine.substring(0, index);
  }  

  // what do here?
  public void parseJump()
  {
    int index = cleanLine.indexOf(';');
    jumpMnemonic = cleanLine.substring(index + 1);
  }

  // what do here?
  public void parseSymbol()
  {
    if(commandType == Command.A_COMMAND)
    {
    symbol = cleanLine.substring(1);
    }
    else
    {
    symbol = cleanLine.substring(1, (cleanLine.length() - 1));
    }
  }  

  private String cleanLine(String rawLine)
  {
    String cleanLine = rawLine.replaceAll("\t", "").replaceAll(" ", "").trim().toUpperCase();
    int index = rawLine.indexOf("/");
    if(index != -1)
    {
      return cleanLine.substring(0, index);
    }
    else
    {
      return cleanLine;
    }
  }

  private Command parseCommandType(String cleanLine)
  {
    int atsym = cleanLine.charAt(1);
    if(atsym >= 0)
    {
      return Command.A_COMMAND;
    }
    else if(cleanLine.contains("="))
    {
      return Command.C_COMMAND;
    }
    else if(cleanLine.contains("()"))
    {
      return Command.L_COMMAND;
    }
    else
    {
      return Command.NO_COMMAND;
    }
  }

  public boolean validName(String symbol)
  {
    char scapegoatChar= symbol.charAt(0);
    final String checkOne = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_.$:";
    if(checkOne.indexOf(scapegoatChar) == -1)
    {
    return false;
    }
    final String checkTwo = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_.$:0123456789";
    for(int i = 1; i < symbol.length(); i++)
    {
      if(checkTwo.indexOf(symbol.charAt(i)) == -1)
      {
      return false;
      }
    }
    return true;
  }
}