package de.isse.alumni.muenchner_kindl_buam;

import org.jooq.lambda.Seq;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        // Lombok features
        final Test test = Test.builder().value(42).build();
        System.out.println(test);
        
        // jOOL features, tests Java 8 language compat
        final Seq<Integer> nums = Seq.of(1, 2, 3);
        final Seq<Character> chars = Seq.of('a', 'b');
        nums.crossJoin(chars)
            .forEach(t -> System.out.println(t));
    }
}

@Builder
@ToString
class Test {
    @Getter
    private final int value;
}
