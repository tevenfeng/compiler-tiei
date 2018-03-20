import java.io.*;
import java.util.*;

import org.w3c.dom.Node;

// **********************************************************************
// The ASTnode class defines the nodes of the abstract-syntax tree that
// represents a C-- program.
//
// Internal nodes of the tree contain pointers to children, organized
// either in a list (for nodes that may have a variable number of 
// children) or as a fixed set of fields.
//
// The nodes for literals and ids contain line and character number
// information; for string literals and identifiers, they also contain a
// string; for integer literals, they also contain an integer value.
//
// Here are all the different kinds of AST nodes and what kinds of children
// they have.  All of these kinds of AST nodes are subclasses of "ASTnode".
// Indentation indicates further subclassing:
//
//     Subclass            Kids
//     --------            ----
//     ProgramNode         DeclListNode
//     DeclListNode        linked list of DeclNode
//     DeclNode:
//       VarDeclNode       TypeNode, IdNode, int
//       FnDeclNode        TypeNode, IdNode, FormalsListNode, FnBodyNode
//       FormalDeclNode    TypeNode, IdNode
//       StructDeclNode    IdNode, DeclListNode
//
//     FormalsListNode     linked list of FormalDeclNode
//     FnBodyNode          DeclListNode, StmtListNode
//     StmtListNode        linked list of StmtNode
//     ExpListNode         linked list of ExpNode
//
//     TypeNode:
//       IntNode           -- none --
//       BoolNode          -- none --
//       VoidNode          -- none --
//       StructNode        IdNode
//
//     StmtNode:
//       AssignStmtNode      AssignNode
//       PostIncStmtNode     ExpNode
//       PostDecStmtNode     ExpNode
//       ReadStmtNode        ExpNode
//       WriteStmtNode       ExpNode
//       IfStmtNode          ExpNode, DeclListNode, StmtListNode
//       IfElseStmtNode      ExpNode, DeclListNode, StmtListNode,
//                                    DeclListNode, StmtListNode
//       WhileStmtNode       ExpNode, DeclListNode, StmtListNode
//       CallStmtNode        CallExpNode
//       ReturnStmtNode      ExpNode
//
//     ExpNode:
//       IntLitNode          -- none --
//       StrLitNode          -- none --
//       TrueNode            -- none --
//       FalseNode           -- none --
//       IdNode              -- none --
//       DotAccessNode       ExpNode, IdNode
//       AssignNode          ExpNode, ExpNode
//       CallExpNode         IdNode, ExpListNode
//       UnaryExpNode        ExpNode
//         UnaryMinusNode
//         NotNode
//       BinaryExpNode       ExpNode ExpNode
//         PlusNode     
//         MinusNode
//         TimesNode
//         DivideNode
//         AndNode
//         OrNode
//         EqualsNode
//         NotEqualsNode
//         LessNode
//         GreaterNode
//         LessEqNode
//         GreaterEqNode
//
// Here are the different kinds of AST nodes again, organized according to
// whether they are leaves, internal nodes with linked lists of kids, or
// internal nodes with a fixed number of kids:
//
// (1) Leaf nodes:
//        IntNode,   BoolNode,  VoidNode,  IntLitNode,  StrLitNode,
//        TrueNode,  FalseNode, IdNode
//
// (2) Internal nodes with (possibly empty) linked lists of children:
//        DeclListNode, FormalsListNode, StmtListNode, ExpListNode
//
// (3) Internal nodes with fixed numbers of kids:
//        ProgramNode,     VarDeclNode,     FnDeclNode,     FormalDeclNode,
//        StructDeclNode,  FnBodyNode,      StructNode,     AssignStmtNode,
//        PostIncStmtNode, PostDecStmtNode, ReadStmtNode,   WriteStmtNode   
//        IfStmtNode,      IfElseStmtNode,  WhileStmtNode,  CallStmtNode
//        ReturnStmtNode,  DotAccessNode,   AssignExpNode,  CallExpNode,
//        UnaryExpNode,    BinaryExpNode,   UnaryMinusNode, NotNode,
//        PlusNode,        MinusNode,       TimesNode,      DivideNode,
//        AndNode,         OrNode,          EqualsNode,     NotEqualsNode,
//        LessNode,        GreaterNode,     LessEqNode,     GreaterEqNode
//
// **********************************************************************

// **********************************************************************
// ASTnode class (base class for all other kinds of nodes)
// **********************************************************************

abstract class ASTnode {
    
    // every subclass must provide an unparse operation
    public void unparse(PrintWriter p, int indent) {
    }

    // this method can be used by the unparse methods to do indenting
    protected void doIndent(PrintWriter p, int indent) {
        for (int k=0; k<indent; k++) p.print(" ");
    }
}

// **********************************************************************
// ProgramNode,  DeclListNode, FormalsListNode, FnBodyNode,
// StmtListNode, ExpListNode
// **********************************************************************

class ProgramNode extends ASTnode {
    public DeclListNode declListNode;

    public ProgramNode(DeclListNode declListNode){
        this.declListNode = declListNode;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.declListNode.unparse(p, indent);
    }
}

class DeclListNode extends ASTnode {
    public LinkedList<DeclNode> declNodeList;

    public DeclListNode(LinkedList<DeclNode> declNodeList){
        this.declNodeList = declNodeList;
    }

    public void unparse(PrintWriter p, int indent){
        for (DeclNode node : this.declNodeList) {
            node.unparse(p, indent);
        }
    }
}

class FormalsListNode extends ASTnode {
    public LinkedList<FormalDeclNode> formalDeclList;

    public FormalsListNode(){
        this.formalDeclList = new LinkedList<FormalDeclNode>();
    }

    public FormalsListNode(LinkedList<FormalDeclNode> formalDeclList){
        this.formalDeclList = formalDeclList;
    }

    public void unparse(PrintWriter p, int indent){
        for (FormalDeclNode node : this.formalDeclList) {
            if(node==this.formalDeclList.getLast()){
                node.unparse(p, 0);
            }else{
                node.unparse(p, 0);
                p.write(", ");
            }
        }
    }
}

class FnBodyNode extends ASTnode {
    public LinkedList<VarDeclNode> varDeclList;
    public LinkedList<StmtNode> stmtList;

    public FnBodyNode(LinkedList<VarDeclNode> varDeclList, LinkedList<StmtNode> stmtList){
        this.varDeclList = varDeclList;
        this.stmtList = stmtList;
    }

    public void unparse(PrintWriter p, int indent){
        p.write("{\n");
        for (VarDeclNode node : this.varDeclList) {
            node.unparse(p, indent + 4);
        }
        for (StmtNode node : this.stmtList) {
            node.unparse(p, indent + 4);
        }
        p.write("}\n");
    }
}

class StmtListNode extends ASTnode {
    public LinkedList<StmtNode> stmtList;

    public StmtListNode(LinkedList<StmtNode> stmtList){
        this.stmtList = stmtList;
    }

    public void unparse(PrintWriter p, int indent){
        for (StmtNode node : this.stmtList) {
            node.unparse(p, indent+2);
        }
    }
}

class ExpListNode extends ASTnode {
    public LinkedList<ExpNode> expList;

    public ExpListNode(LinkedList<ExpNode> expList){
        this.expList = expList;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        for (ExpNode node : this.expList) {
            if(node==this.expList.getLast()){
                node.unparse(p, 0);
            }else{
                node.unparse(p, 0);
                p.write(", ");
            }
        }
    }
}

// **********************************************************************
// DeclNode and its subclasses
// **********************************************************************

abstract class DeclNode extends ASTnode {
}

class VarDeclNode extends DeclNode {
    public TypeNode type;
    public IdNode id;
    public int isStruct;

    public VarDeclNode(TypeNode type, IdNode id, int isStruct){
        this.type = type;
        this.id = id;
        this.isStruct = isStruct;
    }

    public void unparse(PrintWriter p, int indent){
        //doIndent(p, indent);
        this.type.unparse(p, indent);
        p.write(" ");
        this.id.unparse(p, indent);
        p.write(";\n");
    }
    
    ///// DO NOT CHANGE THIS PART /////
    private int mySize;  // use value NOT_STRUCT if this is not a struct type
    public static int NOT_STRUCT = -1;
}

class FnDeclNode extends DeclNode {
    public TypeNode type;
    public IdNode id;
    public FormalsListNode formals;
    public FnBodyNode body;

    public FnDeclNode(TypeNode type, IdNode id, FormalsListNode formals, FnBodyNode body){
        this.type = type;
        this.id = id;
        this.formals = formals;
        this.body = body;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.type.unparse(p, 0);
        p.write(" ");
        this.id.unparse(p, 0);
        p.write("(");
        this.formals.unparse(p, 0);
        p.write(") ");
        this.body.unparse(p, 0);
    }
}

class FormalDeclNode extends DeclNode {
    public TypeNode type;
    public IdNode id;

    public FormalDeclNode(TypeNode type, IdNode id){
        this.type = type;
        this.id = id;
    }

    public void unparse(PrintWriter p, int indent){
        this.type.unparse(p, 0);
        p.write(" ");
        this.id.unparse(p, 0);
    }
}

class StructDeclNode extends DeclNode {
    public IdNode id;
    public LinkedList<VarDeclNode> varDeclList;

    public StructDeclNode(IdNode id, LinkedList<VarDeclNode> varDeclList){
        this.id = id;
        this.varDeclList = varDeclList;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("struct ");
        id.unparse(p, 0);
        p.write(" {\n");
        for (VarDeclNode node : this.varDeclList) {
            node.unparse(p, indent + 4);
        }
        p.write("};\n");
    }
}

// **********************************************************************
// TypeNode and its Subclasses
// **********************************************************************

abstract class TypeNode extends ASTnode {
}

class IntNode extends TypeNode {
    public IntNode(){

    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("int");
    }
}

class BoolNode extends TypeNode {
    public BoolNode(){
        
    }
        
    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("bool");
    }
}

class VoidNode extends TypeNode {
    public VoidNode(){
        
    }
        
    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("void");
    }
}

class StructNode extends TypeNode {
    public IdNode type;

    public StructNode(IdNode type){
        this.type = type;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("struct ");
        this.type.unparse(p, 0);
    }
}

// **********************************************************************
// StmtNode and its subclasses
// **********************************************************************

abstract class StmtNode extends ASTnode {
}

class AssignStmtNode extends StmtNode {
    public AssignNode assign;

    public AssignStmtNode(AssignNode assign){
        this.assign = assign;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.assign.unparse(p, 0);
    }
}

class PostIncStmtNode extends StmtNode {
    public ExpNode exp;

    public PostIncStmtNode(ExpNode exp){
        this.exp = exp;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.exp.unparse(p, 0);
        p.write("++;\n");
    }
}

class PostDecStmtNode extends StmtNode {
    public ExpNode exp;

    public PostDecStmtNode(ExpNode exp){
        this.exp = exp;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.exp.unparse(p, 0);
        p.write(" --;\n");
    }
}

class ReadStmtNode extends StmtNode {
    public ExpNode exp;

    public ReadStmtNode(ExpNode exp){
        this.exp = exp;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("cin >> ");
        this.exp.unparse(p, 0);
        p.write(";\n");
    }
}

class WriteStmtNode extends StmtNode {
    public ExpNode exp;
    
    public WriteStmtNode(ExpNode exp){
        this.exp = exp;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("cout << ");
        this.exp.unparse(p, 0);
        p.write(";\n");
    }
}

class IfStmtNode extends StmtNode {
    public ExpNode exp;
    public DeclListNode declListNode;
    public StmtListNode stmts;

    public IfStmtNode(ExpNode exp, DeclListNode declListNode, StmtListNode stmts){
        this.exp = exp;
        this.declListNode = declListNode;
        this.stmts = stmts;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("if (");
        this.exp.unparse(p, 0);
        p.write(") {\n");
        this.declListNode.unparse(p, indent+2);
        this.stmts.unparse(p, indent+2);
        p.write("}\n");
    }
}

class IfElseStmtNode extends StmtNode {
    public ExpNode exp;
    public DeclListNode v1;
    public StmtListNode s1;
    public DeclListNode v2;
    public StmtListNode s2;

    public IfElseStmtNode(ExpNode exp, DeclListNode v1, StmtListNode s1, DeclListNode v2, StmtListNode s2){
        this.exp = exp;
        this.s1 = s1;
        this.v1 = v1;
        this.s2 = s2;
        this.v2 = v2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("if (");
        this.exp.unparse(p, 0);
        p.write(") {\n");
        this.v1.unparse(p, indent+2);
        this.s1.unparse(p, indent+2);
        doIndent(p, indent);
        p.write("} else {\n");
        this.v2.unparse(p, indent+2);
        this.s2.unparse(p, indent+2);
        doIndent(p, indent);
        p.write("}\n");
    }
}

class WhileStmtNode extends StmtNode {
    public ExpNode exp;
    public DeclListNode v;
    public StmtListNode s;

    public WhileStmtNode(ExpNode exp, DeclListNode v, StmtListNode s){
        this.exp = exp;
        this.s = s;
        this.v = v;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("while(");
        this.exp.unparse(p, 0);
        p.write(") {\n");
        this.v.unparse(p, indent+2);
        this.s.unparse(p, indent+2);
        doIndent(p, indent);
        p.write("}\n");
    }
}

class CallStmtNode extends StmtNode {
    public CallExpNode callExpNode;

    public CallStmtNode(CallExpNode callExpNode){
        this.callExpNode = callExpNode;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.callExpNode.unparse(p, 0);
    }
}

class ReturnStmtNode extends StmtNode {
    public ExpNode exp;

    public ReturnStmtNode(){
        this.exp = null;
    }

    public ReturnStmtNode(ExpNode exp){
        this.exp = exp;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        if(this.exp!=null){
            p.write("return ");
            this.exp.unparse(p, 0);
            p.write(";\n");
        }else{
            p.write("return;\n");
        }
    }
}

// **********************************************************************
// ExpNode and its subclasses
// **********************************************************************

abstract class ExpNode extends ASTnode {
}

class IntLitNode extends ExpNode {
    public int linenum;
    public int charnum;
    public int intVal;

    public IntLitNode(int linenum, int charnum, int intVal){
        this.linenum = linenum;
        this.charnum = charnum;
        this.intVal = intVal;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write(String.format("%d", this.intVal));
    }
}

class StringLitNode extends ExpNode {
    public int linenum;
    public int charnum;
    public String strVal;
    
    public StringLitNode(int linenum, int charnum, String strVal){
        this.linenum = linenum;
        this.charnum = charnum;
        this.strVal = strVal;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write(this.strVal);
    }
}

class TrueNode extends ExpNode {
    public int linenum;
    public int charnum;
    
    public TrueNode(int linenum, int charnum){
        this.linenum = linenum;
        this.charnum = charnum;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("true");
    }
}

class FalseNode extends ExpNode {
    public int linenum;
    public int charnum;
    
    public FalseNode(int linenum, int charnum){
        this.linenum = linenum;
        this.charnum = charnum;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("false");
    }
}

class IdNode extends ExpNode {
    public String idVal;
    public int linenum;
    public int charnum;

    public IdNode(int linenum, int charnum, String idVal){
        this.linenum = linenum;
        this.charnum = charnum;
        this.idVal = idVal;
    }

    public void unparse(PrintWriter p, int indent){
        p.write(this.idVal);
    }
}

class DotAccessExpNode extends ExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public DotAccessExpNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(".");
        this.e2.unparse(p, 0);
    }
}

class AssignNode extends ExpNode {
    public ExpNode id;
    public ExpNode value;

    public AssignNode(ExpNode id, ExpNode value){
        this.id = id;
        this.value = value;
    }

    public void unparse(PrintWriter p, int indent){
        this.id.unparse(p, 0);
        p.write(" = ");
        this.value.unparse(p, 0);
        p.write(";\n");
    }
}

class CallExpNode extends ExpNode {
    public IdNode id;
    public ExpListNode expListNode;

    public CallExpNode(IdNode id, ExpListNode expListNode){
        this.id = id;
        this.expListNode = expListNode;
    }

    public CallExpNode(IdNode id){
        this.id = id;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.id.unparse(p, 0);
        p.write("(");
        this.expListNode.unparse(p, 0);
        p.write(")");
    }
}

abstract class UnaryExpNode extends ExpNode {
    // TO COMPLETE
}

abstract class BinaryExpNode extends ExpNode {
    // TO COMPLETE
}

// **********************************************************************
// Subclasses of UnaryExpNode
// **********************************************************************

class UnaryMinusNode extends UnaryExpNode {
    public ExpNode exp;

    public UnaryMinusNode(ExpNode exp){
        this.exp = exp;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("-");
        this.exp.unparse(p, 0);
    }
}

class NotNode extends UnaryExpNode {
    public ExpNode exp;

    public NotNode(ExpNode exp){
        this.exp = exp;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        p.write("!(");
        this.exp.unparse(p, 0);
        p.write(")");
    }
}

// **********************************************************************
// Subclasses of BinaryExpNode
// **********************************************************************

class PlusNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public PlusNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" + ");
        this.e2.unparse(p, 0);
    }
}

class MinusNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public MinusNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" - ");
        this.e2.unparse(p, 0);
    }
}

class TimesNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public TimesNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" * ");
        this.e2.unparse(p, 0);
    }
}

class DivideNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public DivideNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" / ");
        this.e2.unparse(p, 0);
    }
}

class AndNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public AndNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" && ");
        this.e2.unparse(p, 0);
    }
}

class OrNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public OrNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" || ");
        this.e2.unparse(p, 0);
    }
}

class EqualsNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public EqualsNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" == ");
        this.e2.unparse(p, 0);
    }
}

class NotEqualsNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public NotEqualsNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" ！= ");
        this.e2.unparse(p, 0);
    }
}

class LessNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public LessNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" < ");
        this.e2.unparse(p, 0);
    }
}

class GreaterNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public GreaterNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" > ");
        this.e2.unparse(p, 0);
    }
}

class LessEqNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public LessEqNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" <= ");
        this.e2.unparse(p, 0);
    }
}

class GreaterEqNode extends BinaryExpNode {
    public ExpNode e1;
    public ExpNode e2;

    public GreaterEqNode(ExpNode e1, ExpNode e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public void unparse(PrintWriter p, int indent){
        doIndent(p, indent);
        this.e1.unparse(p, 0);
        p.write(" >= ");
        this.e2.unparse(p, 0);
    }
}
