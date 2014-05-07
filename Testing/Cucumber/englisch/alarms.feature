
Feature: Alarms
  In order to not miss a course
  in the rapla calendar, the user
  can set alarms to be alerted at
  the specified time
  
  Scenario: Set alarm time
    Given I am on the screen 'Alarme'
      And I have clicked the button to set a new alarm
      And I have set the date
      And I have set the time
    When I submit
    Then this new alarm time is saved
  
  Scenario: Sound alarm
    Given an alarm is set
      And this alarm is activated
    When that alarm time is reached
    Then an alarm sound is played