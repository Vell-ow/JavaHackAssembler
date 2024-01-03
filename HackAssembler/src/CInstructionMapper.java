import java.util.*;

public class CInstructionMapper {
  private HashMap<String, String> compCodes = new HashMap<>();
  private HashMap<String, String> destCodes = new HashMap<>();
  private HashMap<String, String> jumpCodes = new HashMap<>();
  // In our constructor, initialize all hashMaps
  public CInstructionMapper()
  {
    // Put key/value pair into hashMaps
    compCodes.put("0", "0101010");
    compCodes.put("1", "0111111");
    compCodes.put("-1", "0111010");
    compCodes.put("D", "0001100");
    compCodes.put("A", "0110000");
    compCodes.put("!D", "0001101");
    compCodes.put("!A", "0110001");
    compCodes.put("-D", "0001111");
    compCodes.put("-A", "0110011");
    compCodes.put("D+1", "0011111");
    compCodes.put("1+D", "0011111");
    compCodes.put("A+1", "0110111");
    compCodes.put("1+A", "0110111");
    compCodes.put("D-1", "0001110");
    compCodes.put("A-1", "0110010");
    compCodes.put("D+A", "0000010");
    compCodes.put("A+D", "0000010");
    compCodes.put("D-A", "0010011");
    compCodes.put("A-D", "0000111");
    compCodes.put("D&A", "0000000");
    compCodes.put("A&D", "0000000");
    compCodes.put("D|A", "0010101");
    compCodes.put("A|D", "0010101");
    // Start of a=1 codes
    compCodes.put("M", "1110000");
    compCodes.put("!M", "1110001");
    compCodes.put("-M", "1110011");
    compCodes.put("M+1", "1110111");
    compCodes.put("1+M", "1110111");
    compCodes.put("M-1", "1110010");
    compCodes.put("D+M", "1000010");
    compCodes.put("D-M", "1010011");
    compCodes.put("M-D", "1000111");
    compCodes.put("D&M", "1000000");
    compCodes.put("M&D", "1000000");
    compCodes.put("D|M", "1010101");
    compCodes.put("M|D", "1010101");
    // Start of Dest codes
    destCodes.put(null, "000");
    destCodes.put("M", "001");
    destCodes.put("D", "010");
    destCodes.put("MD", "011");
    destCodes.put("A", "100");
    destCodes.put("AM", "101");
    destCodes.put("AD", "110");
    destCodes.put("AMD", "111");
    // Start of Jump codes
    jumpCodes.put(null, "000");
    jumpCodes.put("JGT", "001");
    jumpCodes.put("JEQ", "010");
    jumpCodes.put("JGE", "011");
    jumpCodes.put("JLT", "100");
    jumpCodes.put("JNE", "101");
    jumpCodes.put("JLE", "110");
    jumpCodes.put("JMP", "111");
  }

  public String comp(String mnemonic)
  {
    return compCodes.get(mnemonic);
  }
  
  public String dest(String mnemonic)
  {
    return destCodes.get(mnemonic);
  }

  public String jump(String mnemonic)
  {
    return jumpCodes.get(mnemonic);
  }  
}