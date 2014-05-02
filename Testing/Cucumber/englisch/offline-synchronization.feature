
Feature: Offline Synchronization
  The user can set the synchronization interval
  at which the online version of rapla will be copied
  and saved locally
  
  Scenario: Synchronize offline calendar
    Given rapla is online
    When the synchronization interval has expired
    Then rapla is saved locally