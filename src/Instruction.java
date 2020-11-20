public class Instruction implements Opcode {

    public String mnemonic = "";
    public int opCode;

    public Instruction(String inst){getInstruction(inst);}
/*
    public Instruction(Opcode opcde, Operand oprnd){
        this.mnemonic = opcde;
        this.operand = oprnd;
    }
*/
    public Instruction(){}

    public void getInstruction(String inst){
      for(int i = 0; i<inherentMnemonics.length; i++){
          if(inherentMnemonics[i].contains(inst)){
              this.mnemonic = inherentMnemonics[i];
              this.opCode = inherentOpcodes[i];
          }
      }
    }

    public void parseMnemonic(String inst){
        String mnemonic_temp = "";
        for (int i = 0; i < inst.length(); i++){
            mnemonic_temp += inst.charAt(i);
            if(inst.charAt(i) == ' ' || inst.charAt(i) == '.'){break;}
        }
        getInstruction(mnemonic_temp);
    }

    public void printInstruction(){
        System.out.print(String.format("\n0x%02X",opCode) + " " + mnemonic);
    }
}

