Feature: Student feature

  Scenario: Verify that student data is created
    Given the body
    When creating the student data
    Then student data is created

    Scenario: Verify that student data is displayed
      Given the request
      Then getting student data

   Scenario:Verify that student data is updated
     Given creating student data
     When updating email field in student data
     Then student data is updated

     Scenario: Verify that student data is deleted
       Given creating student
       When deleting student
       Then student is deleted

   Scenario: Verify that email field should not be null
     Given the student data
     When creating student without email
     Then getting error message

  Scenario: Verify that age field should not be null
    Given the student data without age
    When creating student without age
    Then getting error message age is required

  Scenario: Verify that id field should not be null
    Given the student data without id
    When creating student without id
    Then getting Internal server error message

  Scenario: Verify that name field should not be null
    Given the student data without name
    When creating student without name
    Then getting error message name is required

    Scenario: Verify that error message is thrown when invalid endpoint is given
      Given the student
      When creating students with invalid endpoint
      Then getting bad request error message

      Scenario: Verify that specific student data is displayed
        Given the request with specific student Id
        Then specific student data is displayed
