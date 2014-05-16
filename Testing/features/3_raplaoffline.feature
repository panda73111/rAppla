Feature: Viewing Events feature

  Scenario: As a user I see the tabs
	Then I wait	
	Then I see "Tag"	
	Then I see "Woche"
	Then I wait	
	Then I wait
	
  Scenario: As a user I see the rapla offline
    Then I wait	
	Then I see "Software Engineering"	 
	Then I press "Einstellungen"	 
	Then I go back	 
	Then I see "Software Engineering"	
	Then I press "Tag"	
	Then I press "Woche"	
	Then I see "Software Engineering"	
	Then I wait	
	Then I wait
	
  Scenario: As a user I can update the rapla
  	Then I wait
	Then I see "Software Engineering"	
	Then I press "Aktualisieren"	
	Then I wait	
	Then I see "Software Engineering"	
	Then I wait	
	Then I wait	