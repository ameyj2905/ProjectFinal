package algorithms;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Sorting {
    /**
     * Merge two ArrayLists efficiently, in ascending order. Does not modify either argument.
     * @param a The first list.
     * @param b The second list.
     * @return A new sorted ArrayList containing elements from both lists.
     */
    public static ArrayList<Integer> merge(ArrayList<Integer> a, ArrayList<Integer> b) {
        ArrayList<Integer> result = new ArrayList<>();

        /* YOUR CODE HERE */
        int l=0,r=0;


        while(l<a.size() &&r<b.size())
        {
            if(a.get(l).compareTo(b.get(r))<=0)
            {
                result.add(a.get(l++));
            }
            else
            {
                result.add(b.get(r++));
            }
        }
        while(l<a.size())
        {
            result.add(a.get(l++));
        }
        while(r<b.size())
        {
            result.add(b.get(r++));
        }

        return result;

    }

    public static ArrayList<Integer> mergeSort(ArrayList<Integer> list) {
        /* YOUR CODE HERE */
        if(list.size()<=1)
        {
            return list;
        }
        int mid=list.size()/2;
        ArrayList<Integer> left=new ArrayList<>();
        ArrayList<Integer> right=new ArrayList<>();
        ArrayList<Integer> output=new ArrayList<>();


        for(int i=0;i<list.size();i++)
        {
            if(i<mid)
            {
                left.add(list.get(i));
            }
            else
            {
                right.add(list.get(i));
            }
        }
        left=mergeSort(left);
        right=mergeSort(right);
        output=merge(left,right);


        return output;


    }
}
