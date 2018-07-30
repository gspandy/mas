package com.letv.mas.common.ribbon;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class MetadataWeightedRule extends ZoneAvoidanceRule {
    private static final String WEIGHT_KEY = "weight";
    private static final Random RANDOM = new Random();
    private static final Integer UNDEFINED_WEIGHT = 1;
    private static final Integer ZERO_WEIGHT = 0;

    @Override
    public Server choose(Object key) {
        List<Server> eligibleServers = this.getPredicate().getEligibleServers(this.getLoadBalancer().getAllServers(), key);
        if (CollectionUtils.isEmpty(eligibleServers)) {
            return null;
        }
        if (eligibleServers.size() == 1) {
            return eligibleServers.get(0);
        }
        WeightedCollection<Server> serverCollection = new WeightedCollection<Server>(RANDOM);
        for (Server server : eligibleServers) {
            serverCollection.add(fetchServerWeight(server), server);
        }
        if (!serverCollection.isEmpty()) {
            return serverCollection.next();
        } else {
            return null;
        }
    }

    private static Integer fetchServerWeight(Server server) {
        String weightStr = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata().get(WEIGHT_KEY);
        return weightStr == null ? UNDEFINED_WEIGHT : Integer.valueOf(weightStr);
    }

    class WeightedCollection<E> {
        private final TreeMap<Integer, E> map = new TreeMap<Integer, E>();
        private final Random random;
        private int total = 0;

        WeightedCollection(Random random) {
            this.random = random;
        }

        void add(Integer weight, E entity) {
            if (weight > ZERO_WEIGHT) {
                total += weight;
                map.put(total, entity);
            }
        }

        E next() {
            int rnd = random.nextInt(total);
            return map.higherEntry(rnd).getValue();
        }

        boolean isEmpty() {
            return map.isEmpty();
        }
    }

}
