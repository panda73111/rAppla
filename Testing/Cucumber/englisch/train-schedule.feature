
Feature: Train schedule
  The user can request the train schedule
  to a specified date, time and station
  
  Scenario: Show train schedule
    Given I am on the screen 'Bahn'
      And I have set a departure
      And I have set a destination
      And I have set a time
    When I click the button 'Suchen'
    Then the relevant train lines and departure times are listed