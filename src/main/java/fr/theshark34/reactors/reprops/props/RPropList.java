/*
 * Copyright 2015 TheShark34
 *
 * This file is part of Reprops.

 * Reprops is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Reprops is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Reprops.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.theshark34.reactors.reprops.props;

import fr.theshark34.reactors.rebyte.Rebyte;
import fr.theshark34.reactors.rebyte.RebyteMalformedBytesException;
import fr.theshark34.reactors.reprops.RProp;
import fr.theshark34.reactors.reprops.RPropsManager;
import fr.theshark34.reactors.reprops.Reprops;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * The List RProp
 *
 * <p>
 *    A simple list of RProp :)
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public class RPropList extends RProp {

    /**
     * The List identifier
     */
    public static final byte IDENTIFIER = 3;

    /**
     * The start list byte
     */
    public static final byte LIST_START = 1;

    /**
     * The end list byte
     */
    public static final byte LIST_END = 2;

    /**
     * The entry start byte
     */
    public static final byte ENTRY_START = 3;

    /**
     * The list to save
     */
    private List<RProp> list;

    /**
     * Constructor with identifier only, reserved for Reprops
     * to use it.
     *
     * @param identifier
     *            The identifier of the prop
     */
    private RPropList(String identifier) {
        super(identifier);

        list = new ArrayList<RProp>();
    }

    /**
     * A RProp List prop
     *
     * @param identifier
     *            The identifier of the prop
     * @param list
     *            The list of RProp
     */
    public RPropList(String identifier, List<RProp> list) {
        super(identifier);

        this.list = list;
    }

    @Override
    public String getName() {
        return "String";
    }

    @Override
    public byte[] write() {
        // Creating the list
        ArrayList<Byte> bytes = new ArrayList<Byte>();

        // Starting
        bytes.add(LIST_START);

        // For each entry
        for(RProp prop : list) {
            // Starting the entry
            bytes.add(ENTRY_START);

            // Adding its identifier
            bytes.add(RPropsManager.getIdentifierFromPropType(prop.getClass()));

            // Getting its content
            byte[] content = prop.write();

            // Writing its size
            byte[] size = Reprops.fromLongToByteArray((long) content.length);
            for(byte b : size)
                bytes.add(b);

            // Writing the content
            for(byte b : content)
                bytes.add(b);
        }

        // Ending
        bytes.add(LIST_END);

        // Converting to an array
        byte[] finalBytes = new byte[bytes.size()];
        for(int i = 0; i < finalBytes.length; i++)
            finalBytes[i] = bytes.get(i);

        return finalBytes;
    }

    @Override
    public void read(byte[] bytes) throws RebyteMalformedBytesException {
        // Checking the list
        if(bytes.length < 2)
            throw new RebyteMalformedBytesException("Malformed list ! It only has " + bytes.length + " bytes !");
        else if (bytes.length == 2) {
            this.list = new ArrayList<RProp>();
            return;
        } else if (bytes[0] != LIST_START)
            throw new RebyteMalformedBytesException("A list need to start with a LIST_START byte (" + LIST_START + ")");

        // Starting Rebyte
        Rebyte rebyte = new Rebyte(bytes);

        // Skipping the next byte (LIST_START)
        rebyte.waitFor(1);

        // Reading the list
        byte[] list = rebyte.readFor(bytes.length - 1);

        // Resetting rebyte
        rebyte.setBytes(list);
        rebyte.setReadingIndex(0);

        // The number of entries
        int entryNumber = 0;

        // Reading each entry
        byte nextByte = rebyte.getNext();
        while(nextByte == ENTRY_START) {
            // Incrementing the number of entry
            entryNumber++;

            // Getting the prop identifier
            byte propTypeIdentifier = rebyte.getNext();

            // Getting the prop class
            Class<? extends RProp> propClass = RPropsManager.getPropTypeFromIdentifier(propTypeIdentifier);
            if(propClass == null)
                throw new RebyteMalformedBytesException("Unknown prop type read : " + propTypeIdentifier);

            // Getting the entry size
            long entrySize = Reprops.fromByteArrayToLong(rebyte.readFor(8));

            // Reading the entry
            byte[] entry = rebyte.readFor(entrySize);

            RProp entryProp;
            // Instantiating the corresponding RProp class with the prop identifier
            try {
                // Getting all constructors of it
                Constructor<?> constructor = null;
                for(Constructor<?> c : propClass.getDeclaredConstructors())
                    // If it's a constructor with one string argument
                    if(c.getParameterTypes().length == 1 && c.getParameterTypes()[0].equals(String.class))
                        // Getting it
                        constructor = c;

                // Checking the constructor
                if(constructor == null)
                    throw new IllegalStateException("Can't find the RProps string only constructor (identifier only constructor). Your RProp class need to have one (a private one is better)");

                // Setting it accessible, in the case of it is not private
                constructor.setAccessible(true);

                // Instantiating it
                entryProp = (RProp) constructor.newInstance(String.valueOf(entryNumber));

                // Initializing it
                entryProp.read(entry);

                // Adding it
                this.list.add(entryProp);
            } catch (IllegalAccessException e) {
                throw new RebyteMalformedBytesException("Can't init the prop : " + e);
            } catch (InstantiationException e) {
                throw new RebyteMalformedBytesException("Can't init the prop : " + e);
            } catch (InvocationTargetException e) {
                throw new RebyteMalformedBytesException("Can't init the prop : " + e);
            }

            // Getting the next byte
            nextByte = rebyte.getNext();
        }

        // Checking
        if(nextByte != LIST_END)
            throw new RebyteMalformedBytesException("A list need to end with a LIST_END byte (" + LIST_END + ")");
    }

    @Override
    public String toString() {
        // Print an output like [(RPropString) test, (RPropNumber) 2]
        String str = "[";

        for(int i = 0; i < list.size(); i++) {
            RProp prop = list.get(i);
            str += (i != 0 ? ", " : "") + "(" + prop.getClass().getSimpleName() + ") " + prop.toString();
        }

        str += "]";
        return str;
    }

    /**
     * Set the list of RProp
     *
     * @param list
     *            The new list to set
     */
    public void setList(List<RProp> list) {
        this.list = list;
    }

    /**
     * Return the list of RProp
     *
     * @return The RProp list
     */
    public List<RProp> getList() {
        return list;
    }
}
