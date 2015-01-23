(ns post-body.core.views
  (:require [ring.util.response :refer [response content-type]]
            [clojure.pprint :refer [pprint]]))

(defn handle-post-body
  "accept the posted JSON object"
  [req]
  (-> (response (with-out-str (pprint req)))
      (content-type "text/plain"))
  )
