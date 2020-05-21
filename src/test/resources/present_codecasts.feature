Feature: present code casts
  Present codecasts correspond with specific user

  Scenario: User who doesn't have the license cannot see any code casts
    Given codecasts
      | title | publicationDate |
      | A     | 01/01/2020      |
      | B     | 01/02/2020      |
      | C     | 01/03/2020      |
    Given no codecast
    Given user "U"
    When the user "U" logged in
    Then cannot see any codecasts

  Scenario: Present viewable codecasts
    Given codecasts
      | title | publicationDate |
      | A     | 01/02/2020      |
      | B     | 01/03/2020      |
      | C     | 01/01/2020      |
    Given user "U"
    When the user "U" logged in
    When license for user "U" able to view "A"
    Then the user "U" can see codecasts in chronological order
      | title | picture | description | viewable | downloadable |
      | C     | C       | C           | false    | false        |
      | A     | A       | A           | true     | false        |
      | B     | B       | B           | false    | false        |

