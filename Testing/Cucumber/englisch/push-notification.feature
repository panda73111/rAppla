
Feature: Push notifications
  The user can activate push notifications
  to be notified when changes to the online
  version of rapla are made
  
  Scenario: Show push notification
    Given I have activated push notifications
    When a change of the online verion of rapla is detected
    Then a push notification is triggered