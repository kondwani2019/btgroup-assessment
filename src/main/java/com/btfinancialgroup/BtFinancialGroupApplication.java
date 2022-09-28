package com.btfinancialgroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.btfinancialgroup"})
public class BtFinancialGroupApplication {
	
    public static void main( String[] args ) {
        SpringApplication.run(BtFinancialGroupApplication.class, args);
    }
}
