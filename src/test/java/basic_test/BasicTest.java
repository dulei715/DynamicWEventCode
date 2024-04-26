package basic_test;

import cn.edu.dll.io.print.MyPrint;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

public class BasicTest {
    @Test
    public void fun1() {
        System.out.println("Hello Test!");
    }

    @Test
    public void fun2() {
        String value = "abcde";
        char element = value.charAt(2);
        System.out.println(element);
    }

    @Test
    public void fun3() {
        Deque<Double> queue = new LinkedList<>();
        queue.addLast(3.3);
        queue.addLast(5.2);
        queue.addLast(7.1);
        queue.addLast(4.8);

        MyPrint.showCollection(queue, "; ");
        Double tempElement;
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            tempElement = queue.poll();
            System.out.println(tempElement);
            MyPrint.showCollection(queue, "; ");
        }

    }

}
