Feature: Settings feature

  Scenario: As a user I can change the value of a
	Then I wait
    
	Then I enter text "https://rapla.dhbw-karlsruhe.de/rapla?page=calendar&user=vollmer&file=tinf12b3" into field with id "123456"
	
	Then I press "OK"
	
	Then I wait
  
	Then I press "Einstellungen"
	 
	Then I press "Push-Benachrichtigungen"
	 
	Then I go back
	 
	Then I press "Einstellungen"
	 
	Then I see "Push-Benachrichtigungen"
	Then I see "Nur im Wifi aktualisieren"
	Then I see "Aktualisierungsintervall"
	 
	Then I press "Aktualisierungsintervall"
	 
	Then I press "Abbrechen"
	 
	Then I go back 