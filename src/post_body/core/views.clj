(ns post-body.core.views
  (:require [ring.util.response :refer [response content-type]]
            [clojure.pprint :refer [pprint]]
            [clojure.java.jdbc :as jdbc]))

(defn handle-post-body
  "accept the posted JSON object"
  [req]
  (let [my-db {:classname      "net.sourceforge.jtds.jdbc.Driver"
               :connection-uri "jdbc:jtds:sqlserver://atxcrtwmdb-q01.devid.local;user=testdev;password=testdev"}
        result (jdbc/query my-db ["select top 10 * from [Fresh_DB].[dbo].[PARSCORE]"])]
    (-> (response (with-out-str (pprint result)))
        (content-type "text/plain"))))
