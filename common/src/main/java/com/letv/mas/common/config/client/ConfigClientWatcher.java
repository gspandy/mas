package com.letv.mas.common.config.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.util.StringUtils.hasText;

/**
 * Custom client configuration watcher, only refresh when version has changed.
 *
 * Created by David.Liu on 2018/7/2.
 */
public class ConfigClientWatcher implements Closeable, EnvironmentAware {

    private final Logger log = LoggerFactory.getLogger(ConfigClientWatcher.class);

    private final String CONFIG_CLIENT_VERSION = "config.client.version";
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final ContextRefresher refresher;
    private final ConfigServicePropertySourceLocator locator;
    private Environment environment;
    private volatile String oldVersion;

    public ConfigClientWatcher(ContextRefresher refresher, ConfigServicePropertySourceLocator locator) {
        this.refresher = refresher;
        this.locator = locator;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void start() {
        this.running.compareAndSet(false, true);
    }

    @Scheduled(initialDelayString = "${spring.cloud.config.watcher.initialDelay:120000}", fixedDelayString = "${spring.cloud.config.watcher.delay:300000}")
    public void watchConfigServer() {
        if (this.running.get()) {
            String newVersion = getPropertyValue(CONFIG_CLIENT_VERSION);
            if (null == newVersion) {
                log.info("[thread: {}] Config client version is null", Thread.currentThread().getName());
                return;
            }

            oldVersion = this.environment.getProperty(CONFIG_CLIENT_VERSION);
            log.info("[thread: {}] Config client old version:{}, new version:{}, version changed:{}", Thread.currentThread().getName(), oldVersion, newVersion, versionChanged(oldVersion, newVersion));

            // only refresh if version has changed
            if (versionChanged(oldVersion, newVersion)) {
                oldVersion = newVersion;
                Set<String> refreshKeys = this.refresher.refresh();
                log.info("[thread: {}] Remote repo configuration has been updated. Keys refreshed [{}]", Thread.currentThread().getName(), refreshKeys);
            }
        }
    }

    private String getPropertyValue(String name) {
        PropertySource<?> propertySource = getPropertySource();
        if (propertySource instanceof CompositePropertySource) {
           return (String) propertySource.getProperty(name);
        }
        return null;
    }

    private PropertySource<?> getPropertySource() {
        return locator.locate(this.environment);
    }

    boolean versionChanged(String oldVersion, String newVersion) {
        return (!hasText(oldVersion) && hasText(newVersion))
                || (hasText(oldVersion) && !oldVersion.equals(newVersion));
    }

    @Override
    public void close() {
        this.running.compareAndSet(true, false);
    }
}
