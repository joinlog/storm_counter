package org.apache.storm.TopologyMain;

import org.apache.storm.topology.TopologyBuilder;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.tuple.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	public static void main(String[] args) throws InterruptedException {
        
        //Topology definition
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader",new WordReader());
		builder.setBolt("word-normalizer", new WordNormalizer())
			.shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new WordCounter(),1)
			.fieldsGrouping("word-normalizer", new Fields("word"));
		LOG.info("Getting-Started-Toplogie2");
		LOG.debug("Getting-Started-Toplogie2");
        //Configuration
		Config conf = new Config();
		conf.put("wordsFile", args[0]);
		conf.setDebug(false);
        //Topology run
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		LocalCluster cluster = new LocalCluster();
		LOG.info("submitTopology start");
		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
		LOG.info("submitTopology end");
		Thread.sleep(100000);
		cluster.shutdown();
	}
}
