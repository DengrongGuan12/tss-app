package cn.superid.tss.config;

/**
 * Created by zp.
 */

import cn.superid.id_client.IdClient;
import cn.superid.tss.util.DStatement;
import com.alibaba.druid.pool.DruidDataSource;
import org.exemodel.cache.ICache;
import org.exemodel.cache.impl.RedisTemplate;
import org.exemodel.session.impl.JdbcSessionFactory;
import org.exemodel.transation.spring.TransactionManager;
import org.exemodel.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig implements CommandLineRunner {
    @Bean
    @ConfigurationProperties(prefix = "druid.datasource")
    public DataSource DataSource() {
        return new DruidDataSource();
    }

    @Bean
    public JedisPoolConfig JedisPoolConfig(
            @Value("${redis.pool.min-idle}") int minIdle,
            @Value("${redis.pool.max-idle}") int maxIdle,
            @Value("${redis.pool.max-wait}") int maxWaitMillis,
            @Value("${redis.pool.block-when-exhausted}") boolean blockWhenExhausted,
            @Value("${redis.pool.max-total}") int maxTotal) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setMaxTotal(maxTotal);
        config.setBlockWhenExhausted(blockWhenExhausted);
        return config;
    }

    @Bean
    public ICache Cache(
            @Qualifier("JedisPoolConfig") JedisPoolConfig config,
            @Value("${redis.host}") String host,
            @Value("${redis.port}") int port,
            @Value("${redis.password}") String password,
            @Value("${redis.timeout}") int timeout,
            @Value("${redis.database}") int database,
            @Value("${redis.ssl}") boolean ssl) {
        if(StringUtil.isEmpty(password)){
            password = null;
        }
        return new RedisTemplate(config, host, port, timeout, password, database, ssl);
    }



    @Bean
    public JdbcSessionFactory JdbcSessionFactory(
            @Qualifier("DataSource") DataSource dataSource,
            @Qualifier("Cache") ICache cache) {
        return new JdbcSessionFactory(dataSource, cache);
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("JdbcSessionFactory")JdbcSessionFactory jdbcSessionFactory) {
        TransactionManager transactionManager = new TransactionManager();
        transactionManager.setSessionFactory(jdbcSessionFactory);
        return transactionManager;
    }

    @Autowired
    private IdClient idClient;
    @Override
    public void run(String... strings) throws Exception {
        DStatement.setIdClient(idClient);
    }


}

