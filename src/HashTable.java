import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Course: COMP 2071
 * Assignment: Lab 5
 * Group #: 11
 * Group members: Tom Plano, Cliff Anderson, Will Lawrence, Artur Janowiec
 * Due date: 4/6/16
 */
public class HashTable
{
    //amount of items in the HashTable
    private int currentSize = 0;

    //Used array space
    private int usedArraySpace = 0;

    //internal array to hold Nodes
    private Node[] nodes;

    public HashTable(int initialSize)
    {
        this.nodes = new Node[initialSize];
    }

    /**
     * Add a new entry to the HashTable. If the entry already exists, replace the value
     * @param s the String
     * @param i the value
     */
    public void put(String s, AtomicInteger i)
    {
        /*
        if s exists in any node replace the value
        else add new string/ int pair
         */
        int index = this.getStringHashCode(s)%this.nodes.length;
        Node node = this.nodes[index];

        if (node == null){
            //Make a new node
            this.nodes[index]=new Node(s,i);
            this.usedArraySpace++;
            this.currentSize++;
        }
        else{
             while (node.getNext() != null){
                 node = node.getNext();
             }

            //when next=null
            Node newNode =new Node(s,i);
            node.setNext(newNode);
            this.currentSize++;
        }

        //Check to see if resizing is needed
        //Used array space must not exceed 75% of the array length
        if(usedArraySpace >= (int) (this.nodes.length * 0.75))
        {
            //Must resize array

        }
    }

    /**
     * Check whether the table contains a key
     * @param s The key to check for
     * @return True if exists, False is not
     */
    public boolean containsKey(String s)
    {
        try
        {
            return this.get(s) != null;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * Get the value given a key
     * @param s The key
     * @return The value
     */
    public AtomicInteger get(String s) throws NoSuchElementException
    {
        //First check and see if there is a node at index hashcode(s)
        int index = this.getStringHashCode(s) % this.nodes.length;
        if(this.nodes[index] == null)
        {
            return null;
        }

        //Search through this chain of nodes looking for the string s
        Node node = this.nodes[index];
        while(node != null)
        {
            if(node.getString().equals(s))
            {
                //Found it
                return node.getInteger();
            }
            else
            {
                //Go to next node
                node = node.getNext();
            }
        }

        //We never found it
        throw new NoSuchElementException("Could not find integer for string: " + s);
    }

    /**
     * Get the current (filled) size of the table
     * @return The current filled size
     */
    public int size()
    {
        return this.currentSize;
    }

    /**
     * Get the hashcode of a string
     * @param s The string
     * @return The hashcode
     */
    private int getStringHashCode(String s)
    {
        int hash = 0;

        for(Character c : s.toCharArray())
        {
            hash += Character.getNumericValue(c);
        }

        return hash;
    }
}

class Node
{
    private String string;
    private AtomicInteger integer;
    private Node next;

    public Node(String string, AtomicInteger integer)
    {
        this.string = string;
        this.integer = integer;
    }

    public String getString()
    {
        return this.string;
    }

    public AtomicInteger getInteger()
    {
        return this.integer;
    }

    public Node getNext()
    {
        return this.next;
    }

    public void setNext(Node next)
    {
        this.next = next;
    }
}
