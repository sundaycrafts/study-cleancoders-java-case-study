Feature: present downloadable code casts

  Scenario: Present downloadable codecasts
    Given codecasts
      | title | publicationDate |
      | A     | 01/02/2020      |
      | B     | 01/03/2020      |
      | C     | 01/01/2020      |
    Given user "U"
    When the user "U" logged in
    When license for user "U" able to download "A"
    Then the user "U" can see codecasts in chronological order
      | title | picture | description | viewable | downloadable |
      | C     | C       | C           | -        | -            |
      | A     | A       | A           | +        | -            |
      | B     | B       | B           | -        | -            |

