Feature: Event Infos feature

  Scenario: As a user I can see the EventActivity
  
	Then I wait
	
	Then I see "Software Engineering"
	
	Then I press the "Software Engineering" Button
	
	Then I see "Infos"
	
	Then I see "Notizen"
	
	Then I see "Wecker"
  
  Scenario: As a valid user I can see details about events
  
	Then I wait
  
	Then I wait
  
	Then I see "Software Engineering"
	
	Then I press the "Software Engineering" Button
	
	Then I see "Begin"
	
	Then I see "8:30"
    	
	Then I see "Titel"
	
	Then I see "Ende"
	
	Then I see "Datum"
    
	Then I see "Ressourcen"