package com.custmngt.customer_management.constants;
import java.util.regex.Pattern;

public class MessageConstants {



    private MessageConstants() {}
    public static final String CUSTOMER_CREATED = "Customer created successfully";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found for : ";
    public static final String CUSTOMER_UPDATED = "Customer updated successfully";

    public static final String INVALID_UUID = "Invalid UUID format";
    public static final String INTERNAL_SERVER_ERROR = "Something went wrong, please try again later";

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


}
