create PROCEDURE ADD_LIBRARY_FUND(NAME VARCHAR, AUTHOR VARCHAR, ISBN VARCHAR,
                                  YEAR VARCHAR, DESCRIPTION VARCHAR,
                                  RENTAL NUMBER, STATUS NUMBER,
                                  BOOK_TYPE NUMBER, HOLDER NUMBER) IS
    CurDate TIMESTAMP := CURRENT_TIMESTAMP;
    CurID NUMBER(10) := LIBRARY_FUND_SEQ.NEXTVAL;
BEGIN
    INSERT INTO LIBRARY_FUND_NAME
        (ID, VALUE, CHANGE_TIME)
    VALUES (CurID, NAME, CurDate);
    INSERT INTO LIBRARY_FUND_AUTHOR
        (ID, VALUE, CHANGE_TIME)
    VALUES (CurID, AUTHOR, CurDate);
    INSERT INTO LIBRARY_FUND_ISBN
        (ID, VALUE, CHANGE_TIME)
    VALUES (CurID, ISBN, CurDate);
    INSERT INTO LIBRARY_FUND_YEAR
        (ID, VALUE, CHANGE_TIME)
    VALUES (CurID, YEAR, CurDate);
    INSERT INTO LIBRARY_FUND_DESCRIPTION
        (ID, VALUE, CHANGE_TIME)
    VALUES (CurID, DESCRIPTION, CurDate);
    INSERT INTO LIBRARY_FUND_RENTAL
        (ID, VALUE, CHANGE_TIME)
    VALUES (CurID, RENTAL, CurDate);
    INSERT INTO LIBRARY_FUND_STATUS
        (ID, VALUE, CHANGE_TIME)
    VALUES (CurID, STATUS, CurDate);
    INSERT INTO LIBRARY_FUND_BOOK_TYPE
        (ID, VALUE, CHANGE_TIME)
    VALUES (CurID, BOOK_TYPE, CurDate);
    INSERT INTO LIBRARY_FUND_HOLDER
        (ID, VALUE, CHANGE_TIME)
    VALUES (CurID, HOLDER, CurDate);
END;
/
