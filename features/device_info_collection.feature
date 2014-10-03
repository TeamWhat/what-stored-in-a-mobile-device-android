Feature: Device info collection
  This feature shows device info to the user.

  Scenario: I can see device info screen
    Given I press "Show device data"
    Then I should see "Brand:"
    Then I should see "Product:"
    Then I should see "Device:"
    Then I should see "Model:"
    Then I should see "Serial:"
    Then I should see "Date collected:"
