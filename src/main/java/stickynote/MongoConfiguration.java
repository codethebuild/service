package stickynote;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Mongo Configuration class.
 */
@Configuration
@ComponentScan
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Value("${mongodb.host}")
    private String host;

    @Value("${mongodb.portNumber}")
    private Integer port;

    @Value("${mongodb.databasename}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(host, port);
    }

    @Override
    protected String getMappingBasePackage() {
        return "stickynote/data/domain";
    }
}