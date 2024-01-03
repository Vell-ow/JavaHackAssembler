import java.util.*;

public class SymbolTable
{
  HashMap<String, Integer> symbolTable = new HashMap<>();
  public void SymbolTable()
  {
    symbolTable.put("R0", 0);
    symbolTable.put("R1", 1);
    symbolTable.put("R2", 2);
    symbolTable.put("R3", 3);
    symbolTable.put("R4", 4);
    symbolTable.put("R5", 5);
    symbolTable.put("R6", 6);
    symbolTable.put("R7", 7);
    symbolTable.put("R8", 8);
    symbolTable.put("R9", 9);
    symbolTable.put("R10", 10);
    symbolTable.put("R11", 11);
    symbolTable.put("R12", 12);
    symbolTable.put("R13", 13);
    symbolTable.put("R14", 14);
    symbolTable.put("R15", 15);
    symbolTable.put("SCREEN", 16384);
    symbolTable.put("KBD", 24576);
    symbolTable.put("SP", 0);
    symbolTable.put("LCC", 1);
    symbolTable.put("ARG", 2);
    symbolTable.put("THIS", 3);
    symbolTable.put("THAT", 4);
  }

  public boolean addEntry(String symbol, int address)
  {
    if(symbolTable.containsKey(symbol))
    {
      symbolTable.put(symbol, address);
      return true;
    }
    else
    {
      return false;
    }
  }

  public boolean contains(String symbol)
  {
    return symbolTable.containsKey(symbol);
  }

  public int getAddress(String symbol)
  {
    if(symbolTable.containsKey(symbol))
    {
      return symbolTable.get(symbol);
    }
    else
    {
      return -1;
    }
  }

  private boolean validName(String symbol)
  {
    // what does "rewrite using constants" mean?
    String checkOne = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_.$:";
    for(int i = 1; i < symbol.length(); i++)
    {
      if(checkOne.indexOf(symbol.charAt(i)) == -1)
      {
        return false;
      }
    }
    String checkTwo = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_.$:0123456789";
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