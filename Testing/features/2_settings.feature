Feature: Settings feature

  Scenario: As a user I can change the value of a
    Then I press "Einstellungen"
	 
	Then I press "Mit GMail synchronisieren"
	 
	Then I go back
	 
	Then I press "Einstellungen"
	 
	Then I see "Push-Benachrichtigungen"
	Then I see "Nur im Wifi aktualisieren"
	Then I see "Aktualisierungsintervall"
	 
	Then I press "Push-Benachrichtigungen"
	 
	Then I press "Aktualisierungsintervall"
	 
	Then I press "Abbrechen"
	 
	Then I go back 