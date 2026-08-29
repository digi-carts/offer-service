Feature: Offer component
  Scenario: list offers
    When I GET "/api/offers"
    Then the response status is 200
