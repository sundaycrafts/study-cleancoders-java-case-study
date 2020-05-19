Feature: present code casts
  Present codecasts correspond with specific user

  Scenario: User who doesn't have the license cannot see any code casts
    Given User "U"
    Given some codecasts are saved
      | title | publicationDate |
      | A     | 01/01/2020    |
      | B     | 01/02/2020    |
      | C     | 01/03/2020    |
    When the user "U" logged in
    Then cannot see any codecasts

