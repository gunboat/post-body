(ns post-body.core.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [post-body.core.views :as views]
            [ring.middleware.json :refer [wrap-json-params wrap-json-body wrap-json-response]]))

(defroutes app-routes
           (GET "/" [] "Hello World")
           (POST "/json" params (views/handle-post-body params))
           (route/not-found "Not Found"))

(def app
  (-> (wrap-defaults app-routes api-defaults)
      (wrap-json-body {:keywords? true})
      (wrap-json-response)))
