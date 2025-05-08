package com.mudigal.one.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Profile("default") //Chỉ kích hoạt cấu hình này khi profile đang chạy là default
@Configuration // Đây là lớp cấu hình Spring, được dùng để khai báo các bean
public class ReactiveMongoConfiguration
    extends AbstractReactiveMongoConfiguration {
  //ReactiveMongoTemplate là lớp giúp thao tác với MongoDB một cách reactive (phi đồng bộ).
  @Bean
  public ReactiveMongoTemplate mongoTemplate() {
    return new ReactiveMongoTemplate(
        mongoClient(), getDatabaseName()); // mongoClient(): Phương thức trả về đối tượng MongoClient
  } //getDatabaseName(): Trả về tên database mà ứng dụng sẽ sử dụng

  // MongoClient là lớp kết nối với MongoDB
  @Bean
  public MongoClient mongoClient() {
    return MongoClients.create();
    //MongoClients.create(): Khởi tạo một kết nối đến MongoDB bằng cấu hình mặc định, kết nối đến địa chỉ localhost:27017
  }

  @Override
  protected String getDatabaseName() {
    return "embeded_db";
    //Phương thức này trả về tên database mà ứng dụng sẽ kết nối
  }
}