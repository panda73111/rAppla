
Feature: Gmail Synchronization
  The user is able to apply rapla
  calender entries to Android's
  Google Calendar
  
  Scenario: Synchronize Gmail Calendar
    Given the gmail synchronization option is activated
    When the synchronization interval has expired
    Then synchronize rapla with Gmail Calendar