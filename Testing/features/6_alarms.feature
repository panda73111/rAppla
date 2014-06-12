Feature: Alarms feature

  Scenario: As a valid user I can add alarms to events
  
	Then I wait
  
  
	Then I enter text "https://rapla.dhbw-karlsruhe.de/rapla?page=calendar&user=vollmer&file=tinf12b3" into field with id "123456"
	
	Then I press "OK"
	
	Then I wait
  
	Then I see "Mathematik"
	
	Then I press the "Mathematik" Button
	
	Then I press "Wecker"
	
	Then I press "Hinzufügen"
	
	Then I wait
	
	Then I press "Speichern"
	
	Then I see "Wecker gestellt"
	
	