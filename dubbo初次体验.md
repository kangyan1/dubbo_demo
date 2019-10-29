# dubbo使用
[本人git地址](https://github.com/kangyan1/dubbo_demo.git)  
[参考链接](https://blog.csdn.net/qq_22152261/article/details/80300597)
### 1.docker安装zookeeper
docker run --privileged=true -d --name zookeeper --publish 2181:2181  -d zookeeper:latest  
如果没有，会先下载，然后再运行。  
doker ps  查看是否运行成功  
之后在idea中下载插件zookeeper,然后在setting中设置zookeeper的配置信息。
### 2.zookeeper管理界面
[下载链接](https://www.csdn.net/link/?target_url=https%3A%2F%2Fcodeload.github.com%2Fapache%2Fincubator-dubbo-ops%2Fzip%2Fmaster&id=80300597&token=9b4035150a1e8a02160c008b1deba6b3)
运行项目后，使用浏览器打开http://127.0.0.1:8080，提示输入账号密码，账号和密码默认均为root，登陆
### 3.提供者
- pom.xml
```
<properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <dubbo-spring-boot>1.0.0</dubbo-spring-boot>
        <log4j.version>1.2.17</log4j.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>io.dubbo.springboot</groupId>
            <artifactId>spring-boot-starter-dubbo</artifactId>
            <version>${dubbo-spring-boot}</version>
        </dependency>

<!--        <dependency>-->
<!--            <groupId>com.alibaba.spring.boot</groupId>-->
<!--            <artifactId>dubbo-spring-boot-starter</artifactId>-->
<!--            <version>2.0.0</version>-->
<!--         </dependency>-->

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>${log4j.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```
- application.properties
```
    #web服务端口，由于我们不需要使用web服务，所以将其设为随机端口
    server.port=-1
    #dubbo服务端口，我们无需知道dubbo服务运行在哪个端口，故也将其设为随机端口
    dubbo.protocol.port = -1
    #dubbo服务名称
    spring.dubbo.application.name = dubbo-provider
    #dubbo服务所在包路径
    spring.dubbo.scan  = cn.hzr0523.service
    #注册中心地址
    spring.dubbo.registry.address=zookeeper://127.0.0.1:2181
    #设置服务的日志输出级别为debug级
    logging.level.com.czh.dubbo.provider.service=debug
```
- service
```
    public interface ProviderService {
        String send();
    }
```
- impl（service使用的是dubbo的，不是spring的）
```
    import cn.hzr0523.service.ProviderService;
    import com.alibaba.dubbo.config.annotation.Service;

    /**
    * description
    *
    * @author yan.kang@hand-china.com
    * @date 2019/10/24 10:57
    */
    @Service
    public class ProviderServiceImpl implements ProviderService {
        @Override
        public String send() {
            return "hello,you are success";
        }
    }
```

### 4.消费者
- pom.xml
```
<properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.alibaba.boot/dubbo-spring-boot-project -->
        <dependency>
            <groupId>com.alibaba.boot</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>0.1.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.101tec/zkclient -->
        <dependency>
            <groupId>com.101tec</groupId>
            <artifactId>zkclient</artifactId>
            <version>0.10</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.47</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```
- application.properties
```
    #web服务端口
    server.port=9000
    #消费者名称
    dubbo.application.name=dubbo-consumer
    #需要使用到提供者服务的包路径
    dubbo.scan.base-packages=cn.hzr0523.service
    #注册中心地址
    dubbo.registry.address=zookeeper://127.0.0.1:2181
```
- contoller
```
@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @RequestMapping("/hello")
    public String hello(){
        consumerService.receive();
        return "null";
    }
}
```

- service(写了两个service，有一个和远程调用框架名称相同)
```
public interface ConsumerService {
    void receive();
}

public interface ProviderService {
    String send();
}
```

- serviceImpl（service和Reference注解都是使用dubbo的）
```
    import cn.hzr0523.service.ConsumerService;
    import cn.hzr0523.service.ProviderService;
    import com.alibaba.dubbo.config.annotation.Reference;
    import com.alibaba.dubbo.config.annotation.Service;
    import org.springframework.stereotype.Component;

    /**
    * description
    *
    * @author yan.kang@hand-china.com
    * @date 2019/10/24 11:08
    */
    @Service
    @Component
    public class ConsumerServiceImpl implements ConsumerService {
        @Reference
        private ProviderService providerService;

        @Override
        public void receive() {
            providerService.send();
        }
    }
```
5.最后
首先启动admin来监控zookeeper，之后启动提供者（确保提供者启动再启动消费者），最后我们调用消费者的接口(http://localhost:9000/hello).