Feature: Notes feature

  Scenario: As a valid user I can add notes to events
  
	Then I wait
  
	Then I see "Software Engineering"
	
	Then I press the "Software Engineering" Button
	
	Then I press "Notizen"
	
	Then I enter text "cucumbertest" into field with id "editText1"
	
	Then I press "Speichern"
	
	Then I go back
	
	Then I wait
  
	Then I see "Software Engineering"
	
	Then I press the "Software Engineering" Button
	
	Then I press "Notizen"
	
	Then I see "cucumbertest"
	