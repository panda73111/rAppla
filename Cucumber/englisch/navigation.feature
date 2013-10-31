
Feature: App navigation

  Scenario: Navigate to the screen 'Woche'
    Given I am on the screen 'Tag'
      Or I am on the screen 'Bahn'
    When I click on the tab 'Woche'
    Then I get to the screen 'Woche'
  
  Scenario: Navigate to the screen 'Bahn'
    Given I am on the screen 'Woche'
      Or I am on the screen 'Tag'
    When I click on the tab 'Bahn'
    Then I get to the screen 'Bahn'
    
  Scenario: Navigate to the screen 'Tag'
    Given I am on the screen 'Woche'
      Or I am on the screen 'Bahn'
    When I click on the tab 'Tag'
    Then I get to the screen 'Tag'
  
  Scenario: Navigate to the screen 'Optionen'
    When I push the settings button
    Then I get to the screen 'Optionen'
  
  Scenario: Navigate to the screen 'Alarme'
    Given I am on the screen 'Informationen'
      Or I am on the screen 'Notizen'
    When I click on the tab 'Alarme'
    Then I get to the screen 'Alarme'
    
  Scenario: Navigate to the screen 'Informationen'
    Given I am on the screen 'Woche'
      Or I am on the screen 'Tag'
      Or I am on the screen 'Alarme'
      Or I am on the screen 'Notizen'
    When I click on the tab 'Informationen'
    Then I get to the screen 'Informationen'
  
  Scenario: Navigate to the screen 'Notizen'
    Given I am on the screen 'Informationen'
      Or I am on the screen 'Alarme'
    When I click on the tab 'Notizen'
    Then I get to the screen 'Notizen'
