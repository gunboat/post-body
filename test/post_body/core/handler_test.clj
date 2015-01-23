(ns post-body.core.handler-test
  (:require [ring.mock.request :as mock]
            [post-body.core.handler :as controller]
            [midje.sweet :refer [facts fact]]
            [clojure.pprint :refer [pprint]]
            [ring.util.response :as response]
            [ring.middleware.json :as middleware]))

(facts
  (fact
    "facts about the index page"
    (let [response (controller/app (mock/request :get "/"))]
      (:status response) => 200
      (:body response) => "Hello World"))

  (fact
    "facts about invalid routes"
    (let [response (controller/app (mock/request :get "/invalid"))]
      (:status response) => 404))

  (fact
    "facts about mock requests and such"
    (let [request (-> (mock/request :post "/json")
                      (mock/content-type "application/json")
                      (mock/body "[{\"name\": \"Steve\"}, {\"name\": \"Austin\"}]")
                      )
          handler (middleware/wrap-json-body identity {:keywords? true})
          received (handler request)
          response (controller/app request)]

      (fact
        "facts about the actual response"
        (:status response) => 200
        (response/get-header response "content-type") => "text/plain")

      (fact
        "facts about the received request"
        (response/get-header received "content-type") => "application/json"
        (:json-params received) => nil
        (:params received) => nil
        (:body received) => [{:name "Steve"} {:name "Austin"}]
        ))))