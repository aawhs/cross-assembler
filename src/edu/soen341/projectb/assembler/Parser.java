package edu.soen341.projectb.assembler;

import edu.soen341.projectb.binary.*;
import edu.soen341.projectb.nodes.*;
import edu.soen341.projectb.options.*;
import edu.soen341.projectb.file.ISourceFile;
import edu.soen341.projectb.file.SourceFile;
import edu.soen341.projectb.reportable._Error;
import edu.soen341.projectb.reportable.ErrorReporter;
import edu.soen341.projectb.reportable.IReportable;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;

public class Parser implements IParser {
    public Parser(Environment env) throws IOException {
        this.lexer = env.getLexer();
        this.sourceFile = env.getSourceFile();
        this.errorReporter = env.getErrorReporter();
        this.table = env.getTable();
        this.keywordTable = env.getKeywordTable();
        this.opCodes = lexer.getOpCodes();
        this.options = env.getOptions();
        for(int i = 0; i<line.length ; i++){
            line[i]="";
            linetokens[i]=Tokens.ILLEGAL_CHAR;
        }

        nextToken(); // prime
        parse();


        address = 0;
    }


    // Record the error: <t> expected, found <token> at <token>.position
    
    protected void expect(Tokens t) throws IOException {
        if (t != token) {
            String expected = lexer.getTokenName(t);
            errorReporter.record( _Error.create( expected+" expected", lexer.getPosition()));
            errorReporter.getException();
            System.out.println(expected);
            nextToken();
        }
    }

     
    /*protected void expect(String t) {
        errorReporter.record( _Error.create(t+" expected", lexer.getPosition()) );
    }
    protected void error(String t) {
        errorReporter.record( _Error.create(t, lexer.getPosition()) );
    }

    private class SyntaxError extends Exception {}
    */


    // -------------------------------------------------------------------
    // An assembly unit is zero or more line statement(s).
    //
    // AssemblyUnit = { LineStmt } EOF .
    // -------------------------------------------------------------------
    private void printLabel(FileWriter fr, File file) throws IOException{
        if (options.isEnabled() &&
        options.isRequired() &&
        options.getClass().getSimpleName().equals("ListingOption")) {
    System.out.println("Listing File : " + file.getAbsolutePath());
    fr.write(String.format("%1s%10s%15s%10s%20s%20s\n",
            "Line", "Address", "Machine Code", "Label", "Assembly Code", "Comment") + "\n");
        }else if (options.getClass().getSimpleName().equals("VerboseOption")&&options.isEnabled() && options.isRequired()) {
    
        System.out.print(String.format("%1s%10s%15s%10s%20s%20s\n",
                "Line", "Address", "Machine Code", "Label", "Assembly Code", "Comment") + "\n");
    } else {
        options.printUsage();
    }


    };

    public LinkedQueue parse() throws IOException {
        System.out.println("Parsing an AssemblyUnit...");



        seq = new LineStmtSeq();
        GenerateBinary binary = new GenerateBinary(seq);
        LineStmt lineStmt ;


        int count = 0;
        File file = new File("S1Test1.lst");
        FileWriter fr = new FileWriter(file);

            printLabel(fr,file);
        System.out.print(String.format("%1s%10s%15s%10s%20s%20s\n",
                "Line", "Address", "Machine Code", "Label", "Assembly Code", "Comment") + "\n");
            while (!token.equals(Tokens.EOF)) {
                if(!token.equals(Tokens.EOL)){
                    if(!keywordTable.isEmpty()){

                        String s = keywordTable.poll().toString();
                        if(curlinepos == lexer.getPosition().getLinePos()){
                            if(token.equals(Tokens.LABEL)){
                                line [0] = s;
                                linetokens[0]= token;

                            }else if(token.equals(Tokens.INHERENT) ||
                                     token.equals(Tokens.RELATIVE) ||
                                     token.equals(Tokens.IMMEDIATE)||
                                     token.equals(Tokens.DIRECTIVE)){
                                line[1] = s;
                                linetokens[1]= token;

                            }else if(token.equals(Tokens.NUMBER)){

                                line[2] = s;
                                linetokens[2]= token;

                            }else if(token.equals(Tokens.COMMENT)){

                                line[3] = s;
                                linetokens[3]= token;
                            }
                        }else{
                            lineStmt = parseLineStmt();
                            seq.add(lineStmt);
                            String[] inst = seq.pop().getInstruction().printInstruction();

                            System.out.print(String.format("%02d\t   %#04X\t\t %4s\t\t\t\t\t\t  %-4s",
                                    lexer.getPosition().getLinePos(), address, inst[0], inst[1]) + "\n");
                            for(int i = 0; i<line.length ; i++){
                                line[i]="";
                                linetokens[i]=Tokens.ILLEGAL_CHAR;
                            }
                            curlinepos++;


                        }
                        /*
                        lineStmt = parseLineStmt(s);
                        seq.add(lineStmt);
                        String[] inst = seq.pop().getInstruction().printInstruction();
                        binary.getInstructions().add(inst);


                        if (options.isEnabled() && options.isRequired() &&
                                options.getClass().getSimpleName() == "ListingOption") {
                            fr.write(String.format("%02d\t   %#04X\t\t %4s\t\t\t\t\t\t  %-4s",
                                    lexer.getPosition().getLinePos(), address, inst[0], inst[1]) + "\n");
                        } else if (options.getClass().getSimpleName() == "VerboseOption") {
                            if (options.isEnabled() && options.isRequired()) {
                                System.out.print(String.format("%02d\t   %#04X\t\t %4s\t\t\t\t\t\t  %-4s",
                                        lexer.getPosition().getLinePos(), address, inst[0], inst[1]) + "\n");
                            } else {
                                options.printUsage();
                            }
                        }

                         */


                        count++;
                        address++;
                        nextToken();
                    }
                } else{
                    nextToken();
                }
            }

            System.out.print("Assembly Unit (Mnemonics) processed and stored in Nodes");
            if (!options.isEnabled()) {
                System.out.print(" ,to create a listing file or verbose use options : '-l' or '-v' respectively");
            }
            fr.flush();
            fr.close();
            binary.init();
            binary.printBinary();
            binary.writeBinary();
            binary.printText();

            return new TranslationUnit(seq);

    }

        private Instruction parseInherent (Instruction inst, String line) throws IOException {
            inst.parseMnemonic(line);
            return inst;
        }
  
        private Instruction parseImmediate (Instruction inst, String line) throws IOException{
            inst.parseMnemonic(line);
            return inst;
        }
        
        private Instruction parseRelative (Instruction inst, String line) throws IOException{
            inst.parseMnemonic(line);
            return inst;
        }
        // -------------------------------------------------------------------
        // A line statement:
        //   - could be empty (only a EOL);
        //   - could have a single comment start at BOL or after a label, label/inst, or label/dir;
        //   - could have a label only, etc.
        //
        // LineStatement = [Label] [Instruction | Directive ] [Comment] EOL .
        //
        public LineStmt parseLineStmt () throws IOException {
            Label label = null;
            Instruction inst = new Instruction();
            Directive directive = null;
            Comment comment = null;


            //parseLabel(); TODO: parse the label in a line statement - create a function to translate label
            //parseComment(); TODO: parse the comment in a line statement - create a function to translate the comment

            /**
             * Inputs: Instruction, String
             * Output: void, saves mnemonic and respective opcode in object variables
             */
            if(linetokens[0].equals(Tokens.LABEL)){
                label = new Label(line[0]);
            }
            if(linetokens[1].equals(Tokens.INHERENT)){
                inst = parseInherent(inst, line[1]);
            }
            if(linetokens[1].equals(Tokens.IMMEDIATE)){
                inst = parseImmediate(inst, line[1]);
            }
            if(linetokens[1].equals(Tokens.RELATIVE)){
                inst = parseRelative(inst, line[1]);
            }
            if (linetokens[1].equals(Tokens.DIRECTIVE)){
                directive = parseDirective(line[1]);
            }
            if(linetokens[2].equals(Tokens.NUMBER)){
                if(!inst.mnemonic.isEmpty()) {
                    if(inst.mnemonic.contains("lda")){
                        if (lexer.getToken() == Tokens.MINUS){
                            errorReporter.record( _Error.create("error: address can not be signed", lexer.getPosition()));
                        }
                        else {inst.operand.address = Integer.parseInt(line[2]);}
                    }
                    else if(inst.mnemonic.contains("ldc") && inst.mnemonic.contains("ldv")){
                        inst.operand.offset = Integer.parseInt(line[2]);
                    }
                    //inst.operand.address = Integer.parseInt(line[2]);
                }
            }
            if(linetokens[3].equals(Tokens.COMMENT)){
                comment = new Comment(line[3]);
            }

            return new LineStmt(label, inst, comment);
        }

    private Directive parseDirective(String line) {
        return new Directive();
    }

    protected void nextToken () throws IOException {
            token = lexer.getToken();
        }

        public LineStmtSeq getSeq () {
        return seq;
    }


        private Tokens token;
        private int curlinepos = 1;
        private int address;
        private ILexer lexer;
        private ISourceFile sourceFile;
        private IReportable errorReporter;
        private ISymbolTable table;
        private Queue keywordTable;
        private ISymbolTable opCodes;
        private IOption options;
        private LineStmtSeq seq;
        String [] line = new String[4]; //Label,instruction or directive ,operand,comment
        Tokens[] linetokens = new Tokens[4];

}

