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
//        String[] cmd = new String[]{"/bin/sh", "-c", "sudo sh -c \"echo '10.1.1.1 legitlab.letv.cn' >> /etc/hosts\" "};
//        exec(cmd);
//        String[] cmd1 = new String[]{"/bin/sh", "-c","sudo sh -c \"touch /etc/temp\";sudo sh -c \"sed '$ d' /etc/hosts > /etc/temp\";sudo sh -c \"cat /etc/temp > /etc/hosts\""};
//        exec(cmd1, baseDir);

        String[] cmd1 = new String[]{"/bin/sh", "-c"," ping legitlab.letv.cn -c 3"};
        String result = exec(cmd1, baseDir);
        System.out.println(result.contains("Request timeout"));
    }

}
