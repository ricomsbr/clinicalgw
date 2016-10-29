package br.com.ackta.clinicalgw.session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 *
 *
 */

@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableHazelcastHttpSession
@Configuration
public class SpringSessionConfig {

	@Value("${cluster.group.name}")
	private String sessionGroupName;

	@Bean(destroyMethod = "shutdown")
	public HazelcastInstance hazelcastInstance() {
		final Config config = new Config();
		GroupConfig groupConfig = config.getGroupConfig();
		groupConfig.setName(sessionGroupName);
		NetworkConfig networkConfig = config.getNetworkConfig();
		networkConfig.setPort(62001);
		JoinConfig join = networkConfig.getJoin();
		join.getMulticastConfig().setMulticastGroup("225.1.1.1");
		// join.getMulticastConfig().setEnabled(false);
		// final TcpIpConfig tcpIpConfig = join.getTcpIpConfig();
		// tcpIpConfig.setEnabled(true);
		// tcpIpConfig.addMember("localhost:62002,localhost:62003");
		final SerializerConfig serializerConfig = new SerializerConfig().setTypeClass(Object.class)
				.setImplementation(new DefaultStreamSerializer());
		config.getSerializationConfig().addSerializerConfig(serializerConfig);
		return Hazelcast.newHazelcastInstance(config);
	}

}
