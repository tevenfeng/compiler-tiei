import java.util.*;

/**
 * The SymInfo class defines a symbol-table entry. 
 * Each SymInfo contains a type (a Type).
 */
public class SymInfo {
    private Type type;
    
    public SymInfo(Type type) {
        this.type = type;
    }
    
    public Type getType() {
        return type;
    }
    
    public String toString() {
        return type.toString();
    }
}

/**
 * The FnInfo class is a subclass of the SymInfo class just for functions.
 * The returnType field holds the return type and there are fields to hold
 * information about the parameters.
 */
class FnInfo extends SymInfo {
    
    private Type type;
    private int numparams;
    private LinkedList<Type> paramTypes;

    public FnInfo(Type type, int numparams) {
        this.type = type;
        this.numparams = numparams;
    }

    public void addFormals(List<Type> L) {
        this.paramTypes = L;
    }
    
    public Type getReturnType() {
        return this.type;
    }

    public int getNumParams() {
        return this.numparams;
    }

    public List<Type> getParamTypes() {
        return this.paramTypes;
    }

    public String toString() {
        return "";
    }
}

/**
 * The StructInfo class is a subclass of the SymInfo class just for variables 
 * declared to be a struct type. 
 */
class StructInfo extends SymInfo {

    private IdNode structType;
    
    public StructInfo(IdNode id) {
        this.structType = id;
    }

    public IdNode getStructType() {
        return this.structType;
    }    
}

/**
 * The StructDefInfo class is a subclass of the SymInfo class just for the 
 * definition of a struct type. 
 * Each StructDefInfo contains a symbol table to hold information about its 
 * fields.
 */
class StructDefInfo extends SymInfo {
    
    private SymTable structSymTable;

    public StructDefInfo(SymTable table) {
        this.structSymTable = table;
    }

    public SymTable getSymTable() {
        return this.structSymTable;
    }
}
