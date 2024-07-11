package basic_test;

import cn.edu.dll.basic.NumberUtil;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll._config.ParameterUtils;
import ecnu.dll.dataset.real.datasetB.spetial_tools.CheckInStringTool;
import ecnu.dll.utils.filters.TxtFilter;
import org.junit.Test;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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


    @Test
    public void fun4() {
        HashSet<Integer> set = new HashSet<>();
        set.add(2);
        set.add(5);
        set.add(6);
        MyPrint.showCollection(set, "; ");

        Iterator<Integer> setIterator = set.iterator();
        Integer tempElement;
        while (setIterator.hasNext()) {
            tempElement = setIterator.next();
            if (tempElement.equals(5)) {
                setIterator.remove();
            }
        }
        MyPrint.showCollection(set, "; ");

    }

    @Test
    public void fun5() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(2);
        linkedList.add(5);
        linkedList.add(7);
        Iterator<Integer> iterator = linkedList.descendingIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void fun6() {
        Deque<Integer> queue = new LinkedList<>();
        queue.add(2);
        queue.add(5);
        queue.add(7);
        Iterator<Integer> iterator = queue.descendingIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void fun7() {
        Instant now = Instant.now();
//        System.out.println(now);
        ZoneId zoneId = ZoneId.of("Asia/Shanghai"); // 定义时区
        ZonedDateTime zonedDateTime = now.atZone(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = zonedDateTime.format(formatter);
        System.out.println("UTC Time: " + now);
        System.out.println("Time in Shanghai: " + formattedTime);

    }

    @Test
    public void fun8() {
        // Tue, 3 Jun 2008 11:05:30 GMT
//        String dataString = "Tue Apr 03 18:00:06 +0000 2012";
        String dataString = "Tue, 3 Apr 2012 18:00:06 GMT";
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dataString, formatter);
        System.out.println(zonedDateTime);
    }
    @Test
    public void fun9() {
        // Tue, 3 Jun 2008 11:05:30 GMT
        String dataString = "Tue Apr 03 18:00:06 +0000 2012";
//        String dataString = "Tue, 3 Apr 2012 18:00:06 GMT";
        String newDataString = CheckInStringTool.toRFC1132(dataString);
        System.out.println(newDataString);
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(newDataString, formatter);
        System.out.println(zonedDateTime);

    }
    @Test
    public void fun10() {
        // Tue, 3 Jun 2008 11:05:30 GMT
        String dataString = "Tue Apr 03 18:00:06 +0000 2012";
//        String newDataString = CheckInStringTool.toRFC1132(dataString);
        System.out.println(dataString);
        String dateTime = CheckInStringTool.toZonedDateTime(dataString);
        System.out.println(dateTime);

    }
    @Test
    public void fun11() {
        // Tue, 3 Jun 2008 11:05:30 GMT
        String dataString = "Tue Apr 03 18:00:06 +0000 2012";
//        String newDataString = CheckInStringTool.toRFC1132(dataString);
        System.out.println(dataString);
        long dateTime = CheckInStringTool.toTimestamp(dataString);
        System.out.println(dateTime);
        Date date = new Date(dateTime);
        System.out.println(date);

    }

    @Test
    public void fun12() {
        System.out.println(Constant.projectPath);
    }

    @Test
    public void fun13() {
        String data = String.format("haha: %08d", 23);
        System.out.println(data);
    }

    @Test
    public void fun14() {
//        Date dateA = new Date(1198944000000L);
//        Date dateB = new Date(1199030399000L);
        Date dateA = new Date(1201930244000L);
        Date dateB = new Date(1202463559000L);
        System.out.println(dateA);
        System.out.println(dateB);
    }

    @Test
    public void fun15() {
        Long dataA = 1202463559000L;
        Long dataB = 1201930244000L;
        System.out.println((dataA-dataB)/1000/60);
    }

    @Test
    public void fun16() {
        String path = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, "test");
        File file = new File(path);
        File[] files = file.listFiles(new TxtFilter());
        System.out.println(files.length);
    }





























}
