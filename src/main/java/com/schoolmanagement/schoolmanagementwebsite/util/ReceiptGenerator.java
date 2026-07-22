package com.schoolmanagement.schoolmanagementwebsite.util;

public class ReceiptGenerator {

    public static String generate(Long id) {

        return String.format("RCPT%06d", id);

    }

}