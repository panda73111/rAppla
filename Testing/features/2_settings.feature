Feature: Settings feature

  Scenario: As a user I can change the value of a
    Then I press "Einstellungen"
	 
	Then I press "Mit GMail synchronisieren"
	 
	Then I go back
	 
	Then I press "Einstellungen"
	 
	Then I see "Mit GMail synchronisieren"
	 
	Then I press "Rapla offline Bereitstellen"
	 
	Then I press "Aktualisierungsintervall"
	 
	Then I press "Abbrechen"
	 
	Then I go back 