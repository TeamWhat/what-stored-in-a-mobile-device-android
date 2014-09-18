Feature: Change information
  This feature allows the user to change his personal information in the app.
  
  Scenario: Changing gender
    Given I press the menu key
    Then I press "Settings" button
    Then I select "Male" from "settingsUserGenderListPreference"
    Then I select "Female" from "settingsUserGenderListPreference"
    Then I should see "Female"
    Then I should not see "Male"
    
  Scenario: Inputting age
    Given I press the menu key
    Then I press "Settings" button
    Then I select "Over 35" from "settingsUserAgeListPreference"
    Then I select "Under 18" from "settingsUserAgeListPreference"
    Then I should not see "Over 35"
    Then I should see "Under 18"
    
  Scenario: Inputting frequency
    Given I press the menu key
    Then I press "Settings" button
    Then I toggle checkbox number 0
    Then I select "Weekly" from "settingsDataSendingFrequencyListPreference"
    Then I select "Daily" from "settingsDataSendingFrequencyListPreference"
    Then I should not see "Weekly"
    Then I should not see "Monthly"
    Then I should see "Daily"
    
