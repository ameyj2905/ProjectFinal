package hashing;

import lib.Pair;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * HashMap implementation using hashing w/ linked list buckets.
 * Please refer to the official HashMap Java 11 documentation
 * for an explanation on the behavior of each of the methods below.
 *
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/HashMap.html
 *
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class HashMap<K, V> implements Iterable<Pair<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    // Normally this would be private, but we'll make it public
    // for testing purposes
    public LinkedList<Pair<K, V>>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMap() {
        /* DO NOT MODIFY */
        table = (LinkedList<Pair<K, V>>[]) Array.newInstance(LinkedList.class, DEFAULT_CAPACITY);
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity) {
        /* DO NOT MODIFY */
        table = (LinkedList<Pair<K, V>>[]) Array.newInstance(LinkedList.class, initialCapacity);
        size = 0;
    }

    public int getSlot(K key) {
        /* DO NOT MODIFY */
        return key == null ? 0 : (key.hashCode() % table.length);
        /*
            Explanation: null always has a hashCode of zero and will always be in index 0;
                         all non-null keys will have a table entry corresponding to their hashCode
                         that wraps around using the modulo (%) operator to prevent overflow
                         (but not collisions).

                         For example, given a table size of 10,
                         a hashCode of 6 results in a slot of 6, and
                         a hashCode of 16 also results in a slot of 6.
         */
    }

    public V put(K key, V value) {
        int slot = getSlot(key);
        V oldval=null;
        if (table[slot] == null) {
            table[slot] = new LinkedList<Pair<K, V>>();
        }
        LinkedList<Pair<K,V>> bucket=table[slot];
        Pair<K,V> pair=new Pair<K,V>(key,value);
        boolean found=false;

        /*
            Use the .set(value) method on the ListIterator to do
            an O(1) replacement of a value.
         */
        ListIterator<Pair<K, V>> i = bucket.listIterator();
        /* YOUR CODE HERE */
        while(i.hasNext())
        {
            Pair<K,V> ipair=i.next();
            if((ipair.left == null && key == null)  || ipair.left.equals(key))
            {
                oldval=ipair.right;
                i.set(pair);
                found=true;

            }

        }
        if(!found)
        {
            table[slot].add(pair);
            size++;
            if((float)size / table.length > LOAD_FACTOR) {
                expand();
            }
        }


        return oldval;
    }

    public V get(K key) {
        int slot = getSlot(key);
        if (table[slot] == null) {
            return null;
        }
        /* YOUR CODE HERE */
        for(Pair<K,V> child: table[slot])
        {
            if((child.left == null && key == null) || child.left.equals(key))
            {
                return child.right;
            }
        }

        return null;
    }

    public V remove(K key) {
        int slot = getSlot(key);
        if (table[slot] == null) {
            return null;
        }

        /*
            Use the remove() method supplied by ListIterator
            to do an O(1) remove on the list bucket.
         */

        ListIterator<Pair<K, V>> i = table[slot].listIterator();

        while(i.hasNext())
        {
            Pair<K, V> child = i.next();
                if(child.left==key)
                {
                    V val=child.right;
                    i.remove();
                    size--;
                    return val;
                }
        }


        /* YOUR CODE HERE */
        return null;
    }

    public int size() {
        /* DO NOT MODIFY */
        return size;
    }


    @Override
    public Iterator<Pair<K, V>> iterator() {
        /* DO NOT MODIFY */
        return new HashMapIterator(this);
    }

    private class HashMapIterator implements Iterator<Pair<K, V>> {
        HashMap<K, V> hashMap;

        /* YOUR CODE HERE */

        int bucketIndex = 0;
        Iterator<Pair<K, V>> currentIterator;

        void nextBucket() {
            while(bucketIndex < hashMap.table.length && (hashMap.table[bucketIndex] == null || hashMap.table[bucketIndex].size() == 0)) {
                bucketIndex++;
            }

            if(bucketIndex == hashMap.table.length) {
                currentIterator = null;
            } else {
                currentIterator = hashMap.table[bucketIndex].iterator();
            }
        }

        HashMapIterator(HashMap<K, V> hashMap) {
            this.hashMap = hashMap;

            /* YOUR CODE HERE */

            bucketIndex = 0;
            currentIterator = null;

            nextBucket();
        }

        @Override
        public boolean hasNext() {
            /*
                hasNext should be worst case O(n), not O(n^2)
                Hint: Use an Iterator to retrieve individual bucket values
                instead of .get(index), which is O(n) on its own
             */

            /* YOUR CODE HERE */

            if(currentIterator == null) {
                return false;
            }

            if(currentIterator.hasNext()) return true;

            int tempVar = bucketIndex + 1;

            while(tempVar < hashMap.table.length &&  (hashMap.table[tempVar] == null || hashMap.table[tempVar].size() == 0)) {
                tempVar++;
            }

            if(tempVar >= hashMap.table.length) return false;
            else return true;

        }

        @Override
        public Pair<K, V> next() {
            /* YOUR CODE HERE */

            if(!hasNext()) {
                currentIterator = null;
                throw new NoSuchElementException();
            }

            if(currentIterator.hasNext()) {
                return currentIterator.next();
            } else {
                bucketIndex++;
                nextBucket();

                if(currentIterator != null) {
                    return currentIterator.next();
                }
            }

            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            /* YOUR CODE HERE */

            if(currentIterator == null) throw new IllegalStateException();

            this.hashMap.size--;
            try {
                currentIterator.remove();
            } catch (IllegalStateException e) {
                this.hashMap.size++;
                throw new IllegalStateException();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        /* DO NOT MODIFY */
        LinkedList<Pair<K, V>>[] newTable = (LinkedList<Pair<K, V>>[]) Array.newInstance(LinkedList.class, table.length * 2);

        /* YOUR CODE HERE */

        LinkedList<Pair<K, V>>[] oldTable = this.table;
        this.table = newTable;

        this.size = 0;

        for(int i = 0; i < oldTable.length; i++) {
            LinkedList<Pair<K, V>> bucket = oldTable[i];
            if(bucket == null) continue;

            for(Pair<K, V> pair : bucket) {
                this.put(pair.left, pair.right);
            }
        }

    }

}
