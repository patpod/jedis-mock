package com.github.fppt.jedismock;

import com.github.fppt.jedismock.exception.WrongValueTypeException;

import java.io.*;

/**
 * Created by Xiaolu on 2015/4/21.
 */
public class Utils {

    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Slice serializeObject(Object o){
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(o);
            Slice encode = Slice.create(bo.toByteArray());
            oo.close();
            bo.close();
            return encode;
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> T deserializeObject(Slice data){
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(data.data());
            ObjectInputStream oi = new ObjectInputStream(bi);
            T ret = (T) oi.readObject();
            oi.close();
            bi.close();
            return ret;
        } catch (IOException | ClassNotFoundException e){
            throw new WrongValueTypeException("WRONGTYPE Key is not a valid HyperLogLog string value.");
        }
    }

    public static long convertToLong(String value){
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new WrongValueTypeException("ERR value is not an integer or out of range");
        }
    }

    public static byte convertToByte(String value){
        try {
            byte bit = Byte.parseByte(value);
            if (bit != 0 && bit != 1) {
                throw new NumberFormatException();
            }
            return bit;
        } catch (NumberFormatException e) {
            throw new WrongValueTypeException("ERR bit is not an integer or out of range");
        }
    }

    public static int convertToNonNegativeInteger(String value){
        try {
            int pos = Integer.parseInt(value);
            if(pos < 0) throw new NumberFormatException("Int less than 0");
            return pos;
        } catch (NumberFormatException e) {
            throw new WrongValueTypeException("ERR bit offset is not an integer or out of range");
        }
    }

    public static int convertToInteger(String value){
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new WrongValueTypeException("ERR bit offset is not an integer or out of range");
        }
    }
}
