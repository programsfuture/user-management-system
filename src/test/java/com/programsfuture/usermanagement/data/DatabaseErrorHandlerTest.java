package com.programsfuture.usermanagement.data;

import com.programsfuture.usermanagement.data.DatabaseErrorHandler;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class DatabaseErrorHandlerTest {

    @Test
    void shouldHandleDuplicateError2601() {

        SQLException exception = new SQLException(
                "Duplicate value",
                "23000",
                2601
        );

        String message = DatabaseErrorHandler.getUserMessage(exception);

        assertEquals(
                "این مقدار قبلاً ثبت شده است و امکان ثبت مورد تکراری وجود ندارد."
                        + "\n\nکد خطا: 2601"
                        + "\nSQL State: 23000"
                        + "\n\nجزئیات فنی:\nDuplicate value",
                message
        );
    }

    @Test
    void shouldHandleDuplicateError2627() {

        SQLException exception = new SQLException(
                "Primary key violation",
                "23000",
                2627
        );

        String message = DatabaseErrorHandler.getUserMessage(exception);

        assertEquals(
                "این مقدار قبلاً ثبت شده است و امکان ثبت مورد تکراری وجود ندارد."
                        + "\n\nکد خطا: 2627"
                        + "\nSQL State: 23000"
                        + "\n\nجزئیات فنی:\nPrimary key violation",
                message
        );
    }

    @Test
    void shouldHandleForeignKeyError547() {

        SQLException exception = new SQLException(
                "Reference constraint violation",
                "23000",
                547
        );

        String message = DatabaseErrorHandler.getUserMessage(exception);

        assertEquals(
                "این مورد در بخش دیگری استفاده شده است و امکان حذف یا تغییر آن وجود ندارد."
                        + "\n\nکد خطا: 547"
                        + "\nSQL State: 23000"
                        + "\n\nجزئیات فنی:\nReference constraint violation",
                message
        );
    }

    @Test
    void shouldHandleGenericSqlError() {

        SQLException exception = new SQLException(
                "Connection failed",
                "08001",
                100
        );

        String message = DatabaseErrorHandler.getUserMessage(exception);

        assertEquals(
                "در انجام عملیات پایگاه داده خطایی رخ داد."
                        + "\n\nکد خطا: 100"
                        + "\nSQL State: 08001"
                        + "\n\nجزئیات فنی:\nConnection failed",
                message
        );
    }

    @Test
    void shouldFindSQLExceptionInsideCauseChain() {

        SQLException sqlException = new SQLException(
                "Nested database error",
                "23000",
                547
        );

        RuntimeException wrapper =
                new RuntimeException("Wrapper", sqlException);

        String message =
                DatabaseErrorHandler.getUserMessage(wrapper);

        assertEquals(
                "این مورد در بخش دیگری استفاده شده است و امکان حذف یا تغییر آن وجود ندارد."
                        + "\n\nکد خطا: 547"
                        + "\nSQL State: 23000"
                        + "\n\nجزئیات فنی:\nNested database error",
                message
        );
    }

    @Test
    void shouldHandleNullThrowable() {

        String message =
                DatabaseErrorHandler.getUserMessage(null);

        assertEquals(
                "خطای نامشخصی رخ داده است.",
                message
        );
    }

    @Test
    void shouldHandleGenericExceptionMessage() {

        RuntimeException exception =
                new RuntimeException("خطای آزمایشی");

        String message =
                DatabaseErrorHandler.getUserMessage(exception);

        assertEquals(
                "خطا:\nخطای آزمایشی",
                message
        );
    }
}