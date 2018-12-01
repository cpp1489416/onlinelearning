package test.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@DependsOn("b")
public class A {
    @Autowired
    B b;

    public A() {
        System.out.println("system construct a");
    }

    @PostConstruct
    public void init() {
        System.out.println(" a post construct, b is " + b);
    }

}
