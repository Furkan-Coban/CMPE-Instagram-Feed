public class MyHashSet<E> {
    private static final Object PRESENT = new Object(); // Placeholder value
    private MyHashMap<E, Object> map; //By using a placeholder value, hashMap is converted to a hashSet

    public MyHashSet() {
        map = new MyHashMap<>();
    }

    public boolean add(E element) {
        if (!map.containsKey(element)) {
            map.put(element, PRESENT);
            return true; // Element is added
        }
        return false; // Element was already in the set
    }

    // Removes an element from the set
    public boolean remove(E element) {
        return map.remove(element) != null;
    }

    // Checks if the set contains an element
    public boolean contains(E element) {
        return map.containsKey(element);
    }
}
