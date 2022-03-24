package lab5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * TODO class declaration
 * We want to accept any type which is comparable to itself
 *
 * @param <T>
 */
public class Heap<T extends Comparable<T>> {
    private ArrayList<T> container;

    public Heap() {
        container = new ArrayList<>();
    }

    /**
     * @return If size is 0, throw {@link IllegalStateException}.
     * Otherwise, return the first element of {@link this#container}
     */
    public T peek() {
        //TODO
        
    	// if size is 0
    	if (container.size() == 0)
    		throw new IllegalStateException();
    	
    	// else return first element
    	else
    		return container.get(0);
    }

    /**
     *
     * @return If size is 0, throw {@link IllegalStateException}. Otherwise, temporarily save the first element.
     * Afterwards, set the first position to the last element, and remove the last element.
     * Call {@link this#heapifyDown()}, then return the original first element
     */
    public T poll() {
        //TODO
        
    	// if size is 0
    	if (container.size() == 0)
    		throw new IllegalStateException();
    	
    	else {
    		// temporary first element
    		T tempFirstElement = container.get(0);
    		
    		// first position to carry the last element
    		container.set(0, container.get(size() - 1));
    		
    		// remove the last element
    		container.remove(size() - 1);
    		
    		heapifyDown();
    		
    		// return original first element
    		return tempFirstElement;
    	}
    }

    private void heapifyDown() {
        int pos = 0;
        while (hasLeft(pos)) {
            int smallerChildIndex = getLeftIndex(pos);
            if (hasRight(pos) && container.get(getRightIndex(pos)).compareTo(container.get(getLeftIndex(pos))) < 0) {
                smallerChildIndex = getRightIndex(pos);
            }
            if (container.get(pos).compareTo(container.get(smallerChildIndex)) < 0) {
                break;
            } else {
                swap(pos, smallerChildIndex);
            }
            pos = smallerChildIndex;
        }
    }

    /**
     * Add the object into {@link this#container}, then call {@link this#heapifyUp()}
     *
     * @param obj the object to add
     */
    public void add(T obj) {
        //TODO
    	
    	// add object
    	container.add(obj);
    	
    	heapifyUp();
    }

    public void addAll(Collection<T> list) {
        list.forEach(this::add);
    }

    /**
     * While the last element has a parent and is smaller than its parent, swap the two elements. Then, check again
     * with the new parent until there's either no parent or we're larger than our parent.
     */
    private void heapifyUp() {
        // TODO
    	
    	int currentIndex = size() - 1;
    	int parentIndex = -1;
    	
    	do {
    		// update parentIndex
    		if (hasParent(currentIndex)) {
    			parentIndex = getParentIndex(currentIndex);
    		}
    	
    		// if meet swap condition
    		if (parentIndex != -1 && container.get(currentIndex).compareTo(container.get(parentIndex)) < 0)
    			swap(currentIndex, parentIndex);
    		
    		// update currentIndex after swap
    		currentIndex = parentIndex;
    		
    		// update parentIndex after swap
    		if (hasParent(currentIndex)) {
    			parentIndex = getParentIndex(currentIndex);
    		}
    		
    	// check again
    	} while (hasParent(currentIndex) && container.get(currentIndex).compareTo(container.get(parentIndex)) < 0);
    }

    public int size() {
        return container.size();
    }

    private int getParentIndex(int i) {
        return (i - 1) / 2;
    }

    private int getLeftIndex(int i) {
        return 2 * i + 1;
    }

    private int getRightIndex(int i) {
        return 2 * i + 2;
    }

    private boolean hasParent(int i) {
        return getParentIndex(i) >= 0;
    }

    private boolean hasLeft(int i) {
        return getLeftIndex(i) < container.size();
    }

    private boolean hasRight(int i) {
        return getRightIndex(i) < container.size();
    }

    private void swap(int i1, int i2) {
        Collections.swap(container, i1, i2);
    }
}
