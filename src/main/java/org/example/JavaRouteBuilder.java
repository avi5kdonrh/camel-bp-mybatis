package org.example;

import org.apache.camel.BeanInject;
import org.apache.camel.builder.RouteBuilder;

import java.util.UUID;

public class JavaRouteBuilder extends RouteBuilder {

    @BeanInject
    Util util;

    @Override
    public void configure() throws Exception {
        // Select example
         from("timer:foo1?period=10s")
                    .setHeader("id").constant(1L)
                    .to("mybatis:getPerson?statementType=SelectOne&inputHeader=id")
                    .process(exchange -> {
                        Person person = exchange.getIn().getBody(Person.class);
                        log.info(">> Article Title "+ person.getName());
                    }) .setAutoStartup("false");
        // Insert example
            from("timer:insert?period=10s")
                    .process(exchange -> {
                        Person person = new Person();
                        person.setName(UUID.randomUUID().toString());
                        person.setId(simple("${random(100000)}").evaluate(exchange,Integer.class).longValue());
                        person.setAge(simple("${random(100)}").evaluate(exchange,Integer.class).intValue());
                        exchange.getIn().setHeader("person", person);
                    })
                    .to("mybatis:setPerson?statementType=Insert&inputHeader=person")
                    .to("log:insertLog?showBody=true") .setAutoStartup("false");
        // Update Example
        from("timer:insert?repeatCount=1")
                .process(exchange -> {
                    Person person = new Person();
                    person.setName("Admin");
                    person.setId(3L);
                    person.setAge(simple("${random(100)}").evaluate(exchange,Integer.class).intValue());
                    exchange.getIn().setHeader("person", person);
                })
                .to("mybatis:updatePerson?statementType=Update&inputHeader=person")
                .to("log:updateLog?showBody=true")
                .setAutoStartup("false");

        // Consumer example

        from("mybatis:getAllPeople?delay=10000")
                 .process(exchange -> {
                     util.process(exchange.getIn().getBody(Person.class));
                 });
    }
}
