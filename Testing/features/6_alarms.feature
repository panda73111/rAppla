Feature: Alarms feature

  Scenario: As a valid user I can add alarms to events
  
	Then I wait
  
	Then I see "Software Engineering"
	
	Then I press the "Software Engineering" Button
	
	Then I press "Wecker"
	
	Then I press "Hinzufügen"
	
	Then I wait
	
	Then I press "Speichern"
	
	Then I see "Wecker gestellt"
	
	