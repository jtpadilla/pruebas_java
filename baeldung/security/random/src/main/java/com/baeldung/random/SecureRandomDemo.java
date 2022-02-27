package com.baeldung.random;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.DoubleStream;

public interface SecureRandomDemo {

    static void generateSecureRandomValues() throws NoSuchAlgorithmException {

        SecureRandom sr = SecureRandomDemo.getSecureRandomForAlgorithm(null);

        int randomInt = sr.nextInt();
        long randomLong = sr.nextLong();
        float randomFloat = sr.nextFloat();
        double randomDouble = sr.nextDouble();
        boolean randomBoolean = sr.nextBoolean();

        IntStream randomIntStream = sr.ints();
        LongStream randomLongStream = sr.longs();
        DoubleStream randomDoubleStream = sr.doubles();

        byte[] values = new byte[124];
        sr.nextBytes(values);
    }

    static SecureRandom getSecureRandomForAlgorithm(String algorithm) throws NoSuchAlgorithmException {
        if (algorithm == null || algorithm.isEmpty()) {
            return new SecureRandom();
        }

        return SecureRandom.getInstance(algorithm);
    }

}
