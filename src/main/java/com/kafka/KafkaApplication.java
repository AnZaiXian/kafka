package com.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);

		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.36.129:22:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		String topic = "lxw1234.com";
		File file = new File("/Users/jiaozhiguang/software/logstash-tutorial.log");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			 String tempString = null;
			 int line = 1;
			 while ((tempString = reader.readLine()) != null) {
			 producer.send(new ProducerRecord<String, String>(topic,line + "---" + tempString));
			 System.out.println("Success send [" + line + "] message ..");
			 line++;
			 }
			 reader.close();
			 System.out.println("Total send [" + line + "] messages ..");
			 } catch (Exception e) {
			 e.printStackTrace();
			 } finally {
			 if (reader != null) {
			try {
			 reader.close();
			} catch (IOException e1) {}
			 }
			}
			producer.close();

	    }
}
