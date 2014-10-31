Feature: Fill information
  This feature allows the user to input his personal information to the app.

  Scenario: Inputting gender
    Given I press the menu key
    Then I press "Settings"
    Then I press "Gender"
    Then I press "Male"
    Then I should see "Male"
    Then I should not see "Cancel"
    
  Scenario: Inputting age
    Given I press the menu key
    Then I press "Settings"
    Then I press "Age"
    Then I press "Over 35"
    Then I should see "Over 35"
    Then I should not see "Under 18"
    
  Scenario: Inputting frequency
    Given I press the menu key
    Then I press "Settings"
    Then I press "Send anonymous data for research periodically"
    Then I press "Data sending frequency"
    Then I press "Weekly"
    Then I should see "Weekly"
    Then I should not see "Monthly"
    Then I should not see "Daily"
    
  Scenario: Inputting country
    Given I press the menu key
    Then I press "Settings"
    Then I press "Country"
    Then I press "Antigua and Barbuda"
    Then I should see "Antigua and Barbuda"
    Then I should not see "Cancel"
    
  Scenario: Information persists after switching screens
    Given I press the menu key
    Then I press "Settings"
    Then I press "Gender"
    Then I press "Female"
    Then I go back
    Then I should see "Show device data"
    Then I press the menu key
    Then I press "Settings"
    Then I should see "Female"
    
