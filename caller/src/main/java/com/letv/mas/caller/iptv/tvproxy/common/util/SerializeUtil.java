package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class SerializeUtil {

    private static Logger logger = Logger.getLogger(SerializeUtil.class);

    public static byte[] serialize(Object object) throws Exception {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            baos.close();
            oos.close();
            return bytes;
        } catch (Exception e) {
            logger.error("serialize object[" + object + "] error", e);
            throw new Exception("serialize  error" + e.getMessage());
        }
    }

    public static Object unserialize(byte[] bytes) throws Exception {
        if (bytes == null) {
            return null;
        }
        Object object = null;
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            object = ois.readObject();
            bais.close();
            ois.close();
            return object;
        } catch (Exception e) {
            logger.error("unserialize  error", e);
            throw new Exception("unserialize  error" + e.getMessage());
        }
    }

}
