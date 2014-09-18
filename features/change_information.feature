Feature: Change information
  This feature allows the user to change his personal information in the app.
  
  Scenario: Changing gender
    Given I press the menu key
    Then I press "Settings"
    Then I press "Gender"
    Then I press "Male"
    Then I press "Gender"
    Then I press "Female"
    Then I should see "Female"
    Then I should not see "Cancel"
    
  Scenario: Changing age
    Given I press the menu key
    Then I press "Settings"
    Then I press "Age"
    Then I press "Over 35"
    Then I press "Age"
    Then I press "Under 18"
    Then I should not see "Over 35"
    Then I should see "Under 18"
    
  Scenario: Cannot change frequency if periodic sending not checked
    Given I press the menu key
    Then I press "Settings"
    Then I press "Data sending frequency"
    Then I should not see "Cancel"
    
  Scenario: Changing frequency
    Given I press the menu key
    Then I press "Settings"
    Then I press "Send anonymous data for research periodically"
    Then I press "Data sending frequency"
    Then I press "Weekly"
    Then I should see "Weekly"
    Then I should not see "Monthly"
    Then I should not see "Daily"
    Then I should not see "Cancel"
    
