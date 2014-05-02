
Feature: Notes
  The user can add notes to rapla events
  
  Scenario: Attach note to event
    Given I am on the screen 'Notizen'
      And I have written a note
    When I click the button 'Speichern'
    Then that note is saved