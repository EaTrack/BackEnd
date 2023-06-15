package com.eatrack.helper;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class CodigoHelper {

    private static volatile SecureRandom numberGenerator = null;
    private static final long MSB = 0x8000000000000000L;

    public String unique(){
        SecureRandom ng = numberGenerator;

        if (ng ==null){
            numberGenerator = ng = new SecureRandom();
        }

        return Long.toHexString(MSB | ng.nextLong()) + Long.toHexString(MSB | ng.nextLong());
    }
}
