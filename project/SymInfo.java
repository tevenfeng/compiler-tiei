import java.util.*;

/**
 * The Sym class defines a symbol-table entry. 
 * Each Sym contains a type (a Type).
 */
public class SymInfo {
    private Type type;
    private int offset;
    private boolean isGlobalVariable = true;
    
    public SymInfo(Type type) {
        this.type = type;
        this.offset = -1;
    }
    
    public SymInfo(Type type, int offset) {
        this.type = type;
        this.offset = offset;
    }
    
    
    public Type getType() {
        return type;
    }
    
    public String toString() {
        return type.toString();
    }
    
    //Offset set/get
    public int getOffset(){
        return this.offset;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }
    
    //isGlobalVariable set/get
    public void setGlobal(boolean isGlobalVariable){
        this.isGlobalVariable = isGlobalVariable;
    }

    public boolean isGlobal(){
        return this.isGlobalVariable;
    }
}

/**
 * The FnSym class is a subclass of the Sym class just for functions.
 * The returnType field holds the return type and there are fields to hold
 * information about the parameters.
 */
class FnInfo extends SymInfo {
    // new fields
    private Type returnType;
    private int numParams;
    private List<Type> paramTypes;
    
    public FnInfo(Type type, int numparams) {
        super(new FnType());
        returnType = type;
        numParams = numparams;
    }

    public void addFormals(List<Type> L) {
        paramTypes = L;
    }
    
    public Type getReturnType() {
        return returnType;
    }

    public int getNumParams() {
        return numParams;
    }

    public List<Type> getParamTypes() {
        return paramTypes;
    }

    public String toString() {
        // make list of formals
        String str = "";
        boolean notfirst = false;
        for (Type type : paramTypes) {
            if (notfirst)
                str += ",";
            else
                notfirst = true;
            str += type.toString();
        }

        str += "->" + returnType.toString();
        return str;
    }
}

/**
 * The StructSym class is a subclass of the Sym class just for variables 
 * declared to be a struct type. 
 * Each StructSym contains a symbol table to hold information about its 
 * fields.
 */
class StructInfo extends SymInfo {
    // new fields
    private IdNode structType;  // name of the struct type
    
    public StructInfo(IdNode id) {
        super(new StructType(id));
        structType = id;
    }

    public IdNode getStructType() {
        return structType;
    }    
}

/**
 * The StructDefSym class is a subclass of the Sym class just for the 
 * definition of a struct type. 
 * Each StructDefSym contains a symbol table to hold information about its 
 * fields.
 */
class StructDefInfo extends SymInfo {
    // new fields
    private SymTable symTab;
    
    public StructDefInfo(SymTable table) {
        super(new StructDefType());
        symTab = table;
    }

    public SymTable getSymTable() {
        return symTab;
    }
}
