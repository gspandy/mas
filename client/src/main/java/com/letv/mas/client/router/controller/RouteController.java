package com.letv.mas.client.router.controller;

import com.letv.mas.client.router.model.EndpointInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.TreeMap;

@RestController
@RequestMapping(value = "/router")
public class RouteController {

    private static final Logger LOG = LoggerFactory.getLogger(RouteController.class);
    private static final Integer ZERO_WEIGHT = 0;
    private static final Random RANDOM = new Random();

    @Value("${eureka.instance.metadata-map.zone}")
    private String zone;
    @Value("${eureka.instance.instance-id}")
    private String instanceId;
    @Value("${eureka.client.region}")
    private String region;

    @RequestMapping(method = RequestMethod.GET, value = "/endpoint/info")
    public EndpointInfoResponse getEndpointInfo() {
        EndpointInfoResponse response = new EndpointInfoResponse();
        response.setInstanceId(instanceId);
        response.setRegion(region);
        response.setZone(zone);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/endpoint/fault")
    public ResponseEntity makeFaultWithProbability(@RequestParam(value = "p", required = true) Integer p) {
        WeightedCollection<HttpStatus> collection = new WeightedCollection<>(RANDOM);
        collection.add(p, HttpStatus.INTERNAL_SERVER_ERROR);
        collection.add(100 - p, HttpStatus.OK);
        HttpStatus status = collection.next();
        LOG.info("RSP-STATUS-{}", status);
        return ResponseEntity.status(status).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/endpoint/resource")
    public ResponseEntity<String> getResourceWithFixedDelay(@RequestParam(value = "fixed_delay_ms", required = false, defaultValue = "0") long fixed_delay_ms) throws InterruptedException {
        if (fixed_delay_ms > 0L) {
            Thread.sleep(fixed_delay_ms);
        }
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(fixed_delay_ms));
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
