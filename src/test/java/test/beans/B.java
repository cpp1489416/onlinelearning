package test.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Stack;

@Component
public class B {
    @Autowired
    private A a;

    public B(A a) {
        System.out.println("b constructed");
    }

    public static class Node {
        int val;
        Node left;
        Node right;
    }

    public static void visit(Node node) {
        System.out.print(node.val + " " );
    }

}
