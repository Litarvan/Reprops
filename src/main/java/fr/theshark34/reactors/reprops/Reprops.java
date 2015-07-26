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
package fr.theshark34.reactors.reprops;

import fr.theshark34.reactors.rebyte.Rebyte;
import fr.theshark34.reactors.rebyte.RebyteMalformedBytesException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * The Reprops object
 *
 * <p>
 *    The Reprops object contains some methods to read,
 *    etc...
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public class Reprops {

    /**
     * The current RProps
     */
    private ArrayList<RProp> props = new ArrayList<RProp>();

    /**
     * Reprops :)
     */
    public Reprops() {
        // Registering the pre-defined RProp types
        RPropsManager.registerPredefinedRPropTypes();
    }

    /**
     * Return the prop of the given index in the props list
     *
     * @param index
     *            The index of the prop to get
     * @return The founded props
     */
    public RProp getProp(int index) {
        return props.get(index);
    }

    /**
     * Add a prop to the list
     *
     * @param prop
     *            The prop to add
     */
    public void addProp(RProp prop) {
        // Checking the prop
        if(prop == null)
            throw new IllegalArgumentException("prop == null");

        // Checking if the given prop type is registered
        if(RPropsManager.getIdentifierFromPropType(prop.getClass()) == null)
            throw new IllegalArgumentException("RProp '" + prop.getName() + "' is not registered");

        // Adding the prop
        props.add(prop);
    }

    /**
     * Return the prop with the given identifier
     *
     * @param identifier
     *            The identifier of the prop to get
     * @return The founded prop
     */
    public RProp getProp(String identifier) {
        for(RProp prop : props)
            if(prop.getIdentifier().equals(identifier))
                return prop;

        return null;
    }

    /**
     * Remove a prop with the given index from the list
     *
     * @param index
     *            The index of the prop to remove
     */
    public void removeProp(int index) {
        props.remove(index);
    }

    /**
     * Remove a prop from the list
     *
     * @param identifier
     *            The identifier of the prop to remove
     * @return True if the prop was removed, false if it wasn't founded
     */
    public boolean removeProp(String identifier) {
        for(int i = 0; i < props.size(); i++)
            if(props.get(i).getIdentifier().equals(identifier)) {
                props.remove(i);
                return true;
            }

        return false;
    }

    /**
     * Load some properties from a file
     *
     * @param file
     *            The file where to load the properties
     *
     * @throws IOException
     *            If it failed to read the file
     * @throws RebyteMalformedBytesException
     *            If the file bytes were malformed
     */
    public void loadFromFile(File file) throws IOException, RebyteMalformedBytesException {
        // Creating a new rebyte object
        Rebyte rebyte = new Rebyte(file);

        // Registering the reprops extension
        rebyte.loadExt(new RepropsRebyteExtension(this));

        // Then starting it
        rebyte.start();
    }

    /**
     * Copy the props from an other Reprops object
     *
     * @param reprops
     *            The Reprops object where to get the props to copy
     */
    public void copyAll(Reprops reprops) {
        for(RProp prop : reprops.getProps())
            this.addProp(prop);
    }

    /**
     * Write the props to a file
     *
     * @param file
     *            The file where to write the props
     * @throws IOException
     *            If it failed to write
     */
    public void writeToFile(File file) throws IOException {
        // Creating an output stream
        FileOutputStream outputStream = new FileOutputStream(file);

        // For each prop
        for(RProp prop : props) {
            // Writing the infos
            outputStream.write(RepropsCodes.PROP_START);
            outputStream.write(crypt(prop.getIdentifier()).getBytes());

            outputStream.write(RepropsCodes.PROP_TYPE_IDENTIFIER);
            outputStream.write(RPropsManager.getIdentifierFromPropType(prop.getClass()));

            // Getting the prop bytes
            byte[] propBytes = prop.write();

            // Writing the bytes length, then the bytes
            byte[] bs = fromLongToByteArray(propBytes.length);
            outputStream.write(bs);

            for(byte b : propBytes)
                outputStream.write(b);
        }

        // Closing the stream
        outputStream.close();
    }

    /**
     * Return the list of the props
     *
     * @return The list of props
     */
    public ArrayList<RProp> getProps() {
        return props;
    }

    /**
     * 'Crypt' a string
     *
     * @param string
     *            The string to crypt
     * @return The crypted string
     */
    public static String crypt(String string) {
        return new BASE64Encoder().encode(string.getBytes());
    }

    /**
     * Decrypt a string 'crypt' with the crypt method
     *
     * @param string
     *            The string to decrypt
     * @return The decrypted string (or the same string if it failed)
     */
    public static String decrypt(String string) {
        try {
            return new String(new BASE64Decoder().decodeBuffer(string));
        } catch (IOException e) {
            return string;
        }
    }

    /**
     * Converts a byte array to a long
     *
     * @param bytes
     *            The byte array to convert
     * @return The created long
     */
    public static long fromByteArrayToLong(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return bb.getLong();
    }

    /**
     * Convert a long to a byte array
     *
     * @param l
     *            The long to convert
     * @return The created byte array
     */
    public static byte[] fromLongToByteArray(long l) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        return bb.putLong(l).array();
    }

}
