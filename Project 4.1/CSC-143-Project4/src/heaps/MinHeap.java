package heaps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Min-heap implementation where the smallest element is the root of the heap.
 * The heap should be stored in an ArrayList<T> container, with a null element
 * at index 0, and the root element starting at index 1. Use the indexing
 * formulas discussed in class to retrieve and assign child nodes.
 *
 * @param <T> The generic type stored in the heap. Must be a Comparable type.
 *           For more information on the Comparable interface, please read:
 *           https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Comparable.html
 */
public class MinHeap<T extends Comparable<T>> {
    public ArrayList<T> container = new ArrayList<>();
    /* YOUR CODE HERE */
    private int size;
    private static final int FRONT=1;





    public MinHeap() {
        container.add(0, null);
    }

    public MinHeap(Collection<T> collection) {
        container.add(0,null);

    }

    private int getParentIndex(int childIndex) {
        return childIndex / 2;
    }

    private int getLeftChildIndex(int parentIndex) {
        return parentIndex * 2;
    }

    private int getRightChildIndex(int parentIndex) {
        return getLeftChildIndex(parentIndex) + 1;
    }

    private void swap(int left, int right) {
        T leftValue = container.get(left);
        container.set(left, container.get(right));
        container.set(right, leftValue);
    }


    /**
     * Inserts a value into the heap, "bubbling up" to the correct position.
     * @param value The value to be inserted.
     */
    public void insert(T value) {
        /* YOUR CODE HERE */

        //use the bubble up function
        container.add(value);
        if(size()>1)
        {
            int index=size();

            while(value.compareTo(container.get(getParentIndex(index)))<0)
            {
                swap(index,getParentIndex(index));
                index=getParentIndex(index);
                if(index==1){break;}
            }
        }

    }



    public T peek() throws NoSuchElementException{
        /* YOUR CODE HERE */
        if(size()==0)
        {
            throw new NoSuchElementException();
        }
        return container.get(1);

    }


    public T remove() {
        /* YOUR CODE HERE */
        if(size()==0)
        {
            throw new NoSuchElementException();
        }
        if(size()==1)
        {
            T value=container.get(1);
            container.remove(1);
            return value;
        }
        else
        {
            swap(1,size());
            T value=container.get(size());
            container.remove(size());
            bubbledown(1);
            return value;
        }
    }
    public void bubbledown(int pos)
    {
        int left=getLeftChildIndex(pos);
        int right=getRightChildIndex(pos);
        int index;
        if(right>size())
        {
            if(left>size())
            {
                return;
            }
            else
            {
                index=left;
            }
        }
        else
        {
            if(container.get(left).compareTo(container.get(right))<=0)
            {
                index=left;
            }
            else
            {
                index=right;
            }
        }

        if(container.get(pos).compareTo(container.get(index))>0)
        {
            swap(index,pos);
            bubbledown(index);
        }

    }

    public int size() {
        /* YOUR CODE HERE */
        return container.size()-1;
    }
}
