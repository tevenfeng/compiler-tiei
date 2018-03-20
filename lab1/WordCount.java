
import java.util.*;

/**
 * A class for the WordCount data structure.
 * A WordCount object is a map which pairs a word (string)
 * with a list of information (Info)
 */
public class WordCount {

    private Map<String, Vector<Info>> wordMap;

    /**
     * Builds an empty WordCount
     */
    public WordCount() {
        this.wordMap = new TreeMap<String, Vector<Info>>();
    }

    /**
     * Adds the given 'info' in the list of
     * Infos of the given word 'word'
     */
    public void add(String word, Info info) {
        if (this.wordMap.containsKey(word)) {
            Vector<Info> wordInfos = this.wordMap.get(word);
            wordInfos.addElement(info);
            this.wordMap.put(word, wordInfos);
        } else {
            Vector<Info> wordInfos = new Vector<>();
            wordInfos.addElement(info);
            this.wordMap.put(word, wordInfos);
        }
    }

    /**
     * Returns an iterator over the informations of
     * the given word 'word'. If 'word' has no information
     * returns null
     */
    public Iterator<Info> getListIterator(String word) {
        if (this.wordMap.containsKey(word)) {
            return this.wordMap.get(word).iterator();
        } else {
            return null;
        }
    }

    /**
     * Displays the WordCount on System.out
     */
    public void display() {
        this.wordMap = this.sortMapByKey(this.wordMap);

        for (String word : this.wordMap.keySet()) {
            System.out.printf("%14s (%d):", word, this.wordMap.get(word).size());
            for (Info info : this.wordMap.get(word)) {
                System.out.printf(" %s", info.toString());
            }
            System.out.print("\n");
        }
    }

    private Map<String, Vector<Info>> sortMapByKey(Map<String, Vector<Info>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Vector<Info>> sortMap = new TreeMap<String, Vector<Info>>(
                new MapKeyComparator()
        );

        sortMap.putAll(map);

        return sortMap;
    }
}

class MapKeyComparator implements Comparator<String>{

    @Override
    public int compare(String str1, String str2) {

        return str1.compareTo(str2);
    }
}
