package com.letv.mas.config;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * Created by David.Liu on 2018/6/25.
 */
@RunWith(SpringRunner.class)
public class ConfigServerBaseTests {

    protected static Logger log = LoggerFactory.getLogger(ConfigServerBaseTests.class);

    protected static final String LETV_MAS_CLIENT = "letv-mas-client";

    protected static final String LETV_MAS_CONFIG = "letv-mas-config";

    protected static final String LOCAL_REPOS = "/letv/app/mas/config/repos";

    protected static final String USERNAME = "config";

    protected static final String PASSWORD = "config2018";

    @Autowired
    protected TestRestTemplate restTemplate;

    protected static String baseUrl;

    @LocalServerPort
    protected int localPort;

    @Before
    public void bootstrap() {
        baseUrl = String.format("http://localhost:%d", localPort);
    }

    /**
     * 清理本地仓库
     */
    @BeforeClass
    public static void removeLocalRepo() {
        File localRepo = new File(LOCAL_REPOS);
        if (localRepo.exists()) {
            deleteDir(localRepo);
            log.info("delete local repos directory for config server");
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
