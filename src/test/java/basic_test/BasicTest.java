package basic_test;

import org.junit.Test;

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

}
