Feature: User feature

  Scenario: Verify that User is created
    Given the user details
    When creating the user
    Then the user is created

    Scenario: Verify that User list is displayed
      Given the request body
      Then getting users list

   Scenario: Verify that User data is updated
     Given creating a user
     When updating marks field in the user
     Then the user is updated

     Scenario: Verify that User data is deleted
       Given creating a user data
       When deleting the user
       Then the user is deleted

   Scenario: Verify that name cannot be null
     Given the user data without name
     When creating the user without name
     Then getting error message Name is required

   Scenario: Verify that address cannot be null
     Given the user data without address
     When creating the user without address
     Then getting error message Address is required

  Scenario: Verify that error is thrown when endpoint is invalid
    Given the user data
    When updating the user with invalid endpoint
    Then getting error message method not allowed

    Scenario: Verify that multiple users are created
      Given multiple users
      When creating multiple users
      Then multiple users are created

      Scenario: Verify that specific user is displayed
        Given the request with specific userId
        Then specific user is displayed
