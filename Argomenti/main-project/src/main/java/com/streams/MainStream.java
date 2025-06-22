package com.streams;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MainStream {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();

        int tot = 1000000;
        Stream.iterate(1, n -> n<=tot, n -> n+2)
                .forEach(n -> words.add(n+". Hello world"));

        System.out.println(words.size()+" elementi"+"\nEstratto:");
        words.stream().limit(10).forEach(System.out::println);
        System.out.println("...\n");

        /* Test prestazioni */
        Predicate<String> predicate = w -> w.contains("1");
        long start, end;

        // 1. Stream sequenziale
        start = System.currentTimeMillis();
        long c1 = words.stream().filter(predicate).count();
        end = System.currentTimeMillis();
        System.out.println("Stream sequenziale: " + c1 + ", tempo = " + (end - start) + " ms");

        // 2. Stream parallelo
        start = System.currentTimeMillis();
        long c2 = words.parallelStream().filter(predicate).count();
        end = System.currentTimeMillis();
        System.out.println("Stream parallelo: " + c2 + ", tempo = " + (end - start) + " ms");

        // 3. ForEach con AtomicLong
        AtomicLong c3 = new AtomicLong(0);
        start = System.currentTimeMillis();
        words.forEach(w -> { if (predicate.test(w)) c3.incrementAndGet(); });
        end = System.currentTimeMillis();
        System.out.println("forEach + AtomicLong: " + c3.get() + ", tempo = " + (end - start) + " ms");

        // 4. For-each classico
        long c4 = 0;
        start = System.currentTimeMillis();
        for (String word : words) {
            if (predicate.test(word)) c4++;
        }
        end = System.currentTimeMillis();
        System.out.println("For-each: " + c4 + ", tempo = " + (end - start) + " ms");

        // 5. Iterator while
        long c5 = 0;
        start = System.currentTimeMillis();
        Iterator<String> i = words.iterator();
        while (i.hasNext()) {
            if (predicate.test(i.next())) c5++;
        }
        end = System.currentTimeMillis();
        System.out.println("Iterator: " + c5 + ", tempo = " + (end - start) + " ms");
    }
}
