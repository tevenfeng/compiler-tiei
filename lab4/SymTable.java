
import java.util.*;

/**
 * A class for Symbol Table
 */
public class SymTable {

    private LinkedList<HashMap<String,SymInfo>> listHashMap;

    /**
     * Create a Symbol Table with one empty scope
     */
    public SymTable() {
        this.listHashMap = new LinkedList<HashMap<String,SymInfo>>();
        this.listHashMap.addFirst(new HashMap<String, SymInfo>());
    }

    /**
     * Add a declaration (i.e. a pair [name,sym]) in the inner scope
     */
    public void addDecl(String name, SymInfo sym) throws DuplicateSymException, EmptySymTableException {
        if(this.listHashMap.size()==0){
            throw new EmptySymTableException();
        }else if(name.equals("")||name==null||sym==null){
            throw new NullPointerException();
        }else if(this.listHashMap.getFirst().containsKey(name)){
            throw new DuplicateSymException();
        }else{
            this.listHashMap.getFirst().put(name, sym);
        }
    }

    /**
     * Add a new inner scope
     */
    public void addScope() {
        this.listHashMap.addFirst(new HashMap<String, SymInfo>());
    }

    /**
     * Lookup for 'name' in the inner scope
     */
    public SymInfo lookupLocal(String name) throws EmptySymTableException {
        if(this.listHashMap.size()==0){
            throw new EmptySymTableException();
        }else if(this.listHashMap.getFirst().containsKey(name)){
            return this.listHashMap.getFirst().get(name);
        }else{
            return null;
        }
    }

    /**
     * Lookup for 'name' sequentially in all scopes from inner to outer
     */
    public SymInfo lookupGlobal(String name)  throws EmptySymTableException {
        if(this.listHashMap.size()==0){
            throw new EmptySymTableException();
        }else{
            for (HashMap<String, SymInfo> hashMap : this.listHashMap) {
                if(hashMap.containsKey(name)){
                    return hashMap.get(name);
                }
            }
            return null;
        }
    }

    /**
     * Remove the inner scope
     */
    public void removeScope() throws EmptySymTableException {
        if(this.listHashMap.size()==0){
            throw new EmptySymTableException();
        }else{
            this.listHashMap.removeFirst();
        }
    }

    /**
     * Print the Symbol Table on System.out
     */
    public void print() {
        System.out.print("\nSym Table\n");
        for (HashMap<String, SymInfo> hashMap : this.listHashMap) {
            System.out.print("{");

            Iterator iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String)entry.getKey();
                SymInfo val = (SymInfo)entry.getValue();
                if(iter.hasNext()){
                    System.out.print(String.format("%s=%s, ", key, hashMap.get(key).toString()));
                }else{
                    System.out.print(String.format("%s=%s", key, hashMap.get(key).toString()));
                }
            }

            System.out.print("}\n");
        }
    }
}
