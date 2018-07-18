package com.letv.mas.client.config;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by David.Liu on 2018/7/5.
 */
public class CommandHandler {
    private static Logger logger = LoggerFactory.getLogger(CommandHandler.class);

    public static String exec(String[] command, String currentDir) {
        String result = "";
        try {
            final ProcessBuilder pb = new ProcessBuilder(command);
            if (StringUtils.isNotEmpty(currentDir)) {
                pb.directory(new File(currentDir)); // change current directory
            }
            Process pro = pb.start();

            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result = result + line + "\n";
            }

            input.close();
            output.close();
            pro.destroy();
        } catch (IOException ex) {
            logger.error("command exec error: {}", ex.getMessage(), ex);
        }

        logger.info("runtime exec result：" + result);
        return result;
    }

    public static String exec(String[] command) {
        return exec(command, null);
    }

    public static void main(String[] args) throws InterruptedException {
        String baseDir = System.getProperty("user.dir") + "/client/src/test/java/com/letv/mas/client/config/";
        String[] cmd = new String[]{"/bin/sh", "-c", "rm -rf " + baseDir + "config"};
        exec(cmd, baseDir);
    }

}
