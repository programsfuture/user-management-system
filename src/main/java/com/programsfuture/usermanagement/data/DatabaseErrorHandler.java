package com.programsfuture.usermanagement.data;

import java.sql.SQLException;

public final class DatabaseErrorHandler {

    private DatabaseErrorHandler() {
    }

    public static String getUserMessage(Throwable throwable) {

        SQLException sqlException = findSQLException(throwable);

        if (sqlException != null) {

            int errorCode = sqlException.getErrorCode();
            String sqlState = sqlException.getSQLState();
            String technicalMessage = sqlException.getMessage();

            String userMessage;

            if (errorCode == 2601 || errorCode == 2627) {

                userMessage =
                        "این مقدار قبلاً ثبت شده است و امکان ثبت مورد تکراری وجود ندارد.";

            } else if (errorCode == 547) {

                userMessage =
                        "این مورد در بخش دیگری استفاده شده است و امکان حذف یا تغییر آن وجود ندارد.";

            } else {

                userMessage =
                        "در انجام عملیات پایگاه داده خطایی رخ داد.";
            }

            return userMessage
                    + "\n\nکد خطا: " + errorCode
                    + "\nSQL State: " + sqlState
                    + "\n\nجزئیات فنی:\n" + technicalMessage;
        }

        String message = throwable == null
                ? null
                : throwable.getMessage();

        if (message != null && !message.isBlank()) {
            return "خطا:\n" + message;
        }

        return "خطای نامشخصی رخ داده است.";
    }

    private static SQLException findSQLException(Throwable throwable) {

        Throwable current = throwable;

        while (current != null) {

            if (current instanceof SQLException sqlException) {
                return sqlException;
            }

            current = current.getCause();
        }

        return null;
    }
}


//
//این مقدار قبلاً ثبت شده است و امکان ثبت مورد تکراری وجود ندارد.
//
//کد خطا: 2627
//SQL State: 23000
//
//جزئیات فنی:
//Violation of PRIMARY KEY constraint ...
//
//
//این مورد در بخش دیگری استفاده شده است و امکان حذف یا تغییر آن وجود ندارد.
//
//کد خطا: 547
//SQL State: 23000
//
//جزئیات فنی:
//The DELETE statement conflicted with the REFERENCE constraint ...