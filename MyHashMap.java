
// Custom implementation of a HashMap data structure using open addressing with quadratic probing.
public class MyHashMap<K,V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    private Entry<K,V>[] table;
    private int size;
    private int capacity;


    //Represents a key-value pair stored in hashMap
    private static class Entry<K,V>{
        K key;
        V value;
        boolean active; // Flag to indicate if the entry is currently active
        Entry(K key, V value){
            this.key = key;
            this.value = value;
            this.active = true;
        }
    }

    public MyHashMap(){
        this.capacity = INITIAL_CAPACITY;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    private int hash(Object key){
        return Math.abs(key.hashCode()%capacity);

    }

    public void put(K key, V value){
        double loadFactor = (double) size/capacity;
        if(loadFactor>=LOAD_FACTOR_THRESHOLD){ //checks whether loadFactor exceeds the threshold
            rehash();
        }

        int index = hash(key);
        int step = 1;

        //quadratic probing to find an empty or inactive slot
        while(table[index] != null && table[index].active){
            if(table[index].key.equals(key)){
                return; //If the key is already in map, returns
            }
            index = (index+(step*step))%capacity; //With quadratic probing, looking for an available slot
            step++;
        }
        table[index] = new Entry<>(key,value);
        size++;
    }

    public V get(K key){
        int index = hash(key);
        int step = 1;

        //Quadratic Probing to find the key
        while(table[index]!=null){
            if(table[index].key.equals(key)&&table[index].active){
                return table[index].value;
            }
            index = (index+(step*step))%capacity;
            step++;
        }
        return null; // Key not found
    }

    public V remove(K key) {
        int index = hash(key);
        int step = 1;

        // Quadratic probing to find the key
        while (table[index] != null) {
            if (table[index].key.equals(key) && table[index].active) {
                table[index].active = false;  // Marked as inactive instead of deleted
                size--;
                return table[index].value;
            }
            index = (index + (step*step)) % capacity;  // Move to the next slot
            step++;
        }

        return null;  // Key not found
    }

    private void rehash() {
        int newCapacity = getNextPrime(capacity * 2); //Capacity is set to bigger prime number
        Entry<K, V>[] oldTable = table;

        table = new Entry[newCapacity];
        capacity = newCapacity;
        size = 0;

        // Rehashing all existing active entries into the new table
        for (Entry<K, V> entry : oldTable) {
            if (entry != null && entry.active) {
                put(entry.key, entry.value);
            }
        }
    }
    private int getNextPrime(int n) {
        while (!isPrime(n)) {
            n++;
        }
        return n;
    }

    // Checks if a number is prime
    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }

        return true;
    }

    public boolean containsKey(K key) {
        int index = hash(key);
        int step = 1;

        while (table[index] != null) {
            if (table[index].key.equals(key) && table[index].active) {
                return true;
            }
            index = (index + (step*step)) % capacity;  // Quadratic probing to the next slot
            step++;
        }

        return false;  // Key not found
    }

}
