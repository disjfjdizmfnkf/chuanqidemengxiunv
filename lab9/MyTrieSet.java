import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private class Node{
        private boolean iskey;
        private HashMap<Character, Node> map;

        Node(boolean b){    //构造函数，初始化
            this.iskey = b;
            map = new HashMap<>();   //上面申明过hashmap类型，<>里面省略了
        }
    }

    private Node root;

    public MyTrieSet() {
        root = new Node(false);
    }  //使用mytrieset时初始化的

    @Override
    public void clear() {
        root = new Node(false);
    }

    /**return the node if it exist*/
    private Node get(String key){
        Node curr = root;
        for (int i = 0; i < key.length(); i++){
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)){
                return null;
            }
            curr = curr.map.get(c);
        }
        return curr;
    }

    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            throw new IllegalArgumentException();
        }
        Node n = get(key);

        return n != null && n.iskey;
    }


    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(false));
            }
            curr = curr.map.get(c);
        }
        curr.iskey = true;
    }

    private void collect(String s, List<String> x, Node start) {
        if (start == null) {
            return;
        }
        if (start.iskey) {
            x.add(s);
        }
        for (char c : start.map.keySet()) {      //遍历所有子项
            collect(s + c, x, start.map.get(c));
        }
    }
    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> keys = new ArrayList<>();
        Node start = get(prefix);
        collect(prefix, keys, start);
        return keys;
    }

    @Override
    public String longestPrefixOf(String key) {
        int n = key.length();
        StringBuilder sb = new StringBuilder();
        Node curr = root;
        for(int i = 0; i < n; i++){
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)){
                break;
            }
            curr = curr.map.get(c);
            sb.append(c);
        }
        return sb.toString();
    }
}
