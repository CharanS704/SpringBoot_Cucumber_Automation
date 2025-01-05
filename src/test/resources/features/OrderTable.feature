Feature: Validate the ItemDetails database is up and able to retrieve data

@DBValidation1
Scenario Outline: Validate the quantity for an item from the database against the expected stock count
Given User establish connectivity with the database and prepares query
Then User fetch records for the item "<item>" which are left with booked status as "<status>"
And User validate the quantity for the records against the expected stock quantity <Expected Qty>
Examples:
|item       |status |Expected Qty|
|ZARA COAT 3|No     |2           |


@DBValidation2
Scenario Outline: Validate the quantity and status for the item is as expected
Given User establish connectivity with the database and prepares query to fetch all the records from database
Then User fetch the records from the database with status as "<status>"
And User validate the items in list "<items>" are present in the database with booked status "<status>"
Examples:
|status |items                                              |
|Yes     |ZARA COAT 3,Red Hat,ADIDAS ORIGINAL,Cello ball Pen|



