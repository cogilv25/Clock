package clock;

import java.util.Calendar;

/**
 * Implementation of a PriorityQueue to track the alarms and order them by time
 * until expiration.
 * 
 * @author Calum Lindsay
 */
public class PriorityQueue
{
    
    /**
     * Where the data is actually stored.
     */
    private Alarm[] storage;

    /**
     * The size of the storage array.
     */
    private int capacity;

    /**
     * The index of the last item stored.
     *
     * This is equal to the item count minus one.
     */
    private int tailIndex;

    /**
     * Create a new empty queue of the given size.
     *
     * @param size
     */
    public PriorityQueue(int size)
    {
        storage = new Alarm[size];
        capacity = size;
        tailIndex = -1;
    }
    
    /**
     * Get the Alarm stored at the head of the queue.
     * 
     * @return The alarm at the head of the queue.
     * @throws QueueUnderflowException 
     */
    public Alarm head() throws QueueUnderflowException
    {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            return storage[0];
        }
    }

    /**
     * Add an Alarm to the queue.
     * 
     * @param alarmTime The expiry time of the new alarm.
     * @param message The message to display to the user when the alarm expires.
     */
    public void add(Calendar alarmTime, String message)
    {
        tailIndex = tailIndex + 1;
        
        /* Expand the array if neccessary */
        if (tailIndex == capacity)
        {
            capacity *= 2;
            Alarm[] temp = new Alarm[capacity];
            System.arraycopy(storage, 0, temp, 0, tailIndex);
            storage = temp;
        }
        
        /* Scan backwards looking for insertion point */
        int i = tailIndex;
            while (i > 0 && storage[i - 1].getAlarmTimeInMillis() > alarmTime.getTimeInMillis())
        {
            storage[i] = storage[i - 1];
            i = i - 1;
        }
        storage[i] = new Alarm(message, alarmTime);
    }
    
    /**
     * Get the Alarm stored at the provided index in the queue.
     * 
     * @param index The index of the Alarm that will be returned.
     * @return The Alarm at the index requested.
     * @throws ArrayIndexOutOfBoundsException 
     */
    public Alarm get(int index) throws ArrayIndexOutOfBoundsException
    {
        if(index < 0 || index > tailIndex)
            throw new ArrayIndexOutOfBoundsException();
        
        return storage[index];
    }
    
    /**
     * Get the number of Alarms stored in the queue.
     * 
     * @return The number of Alarms stored in the queue.
     */
    public int getCount()
    {
        return tailIndex+1;
    }
    
    /**
     * Remove the Alarm stored at the index provided from the queue.
     * 
     * @param index The index of the Alarm to be removed.
     * @throws QueueUnderflowException
     * @throws ArrayIndexOutOfBoundsException 
     */
    public void remove(int index) throws QueueUnderflowException, ArrayIndexOutOfBoundsException
    {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else if (tailIndex == 0 && index == 0)
        {
            storage[0] = null;
            tailIndex--;
        }else if(index <= tailIndex){
            for (int i = index; i < tailIndex; i++) {
                storage[i] = storage[i + 1];
            }
            tailIndex--;
        }
        else
            throw new ArrayIndexOutOfBoundsException();
    }

    /**
     * Get whether the queue is empty.
     * 
     * @return True if the queue is empty
     */
    public boolean isEmpty()
    {
        return tailIndex < 0;
    }

    /**
     * Get a String representation of the queue.
     * 
     * @return a String representing the queue.
     */
    @Override
    public String toString()
    {
        String result = "[";
        for (int i = 0; i <= tailIndex; i++) {
            if (i > 0) {
                result = result + ", ";
            }
            result = result + storage[i];
        }
        result = result + "]";
        return result;
    }
}
