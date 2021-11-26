package com.kodakro.jiki.hazelcast;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;

@Configuration
public class HazelcastCacheConfig {
	@Bean
    public Config hazelcastConfig(){
       return new Config().setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig()
                		.setName("usersCache")
                		.setTimeToLiveSeconds(2000)
                		.setEvictionConfig(new EvictionConfig()
                                .setSize(300)
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                                .setEvictionPolicy(EvictionPolicy.LRU)
                        ))
                ;
    }
}