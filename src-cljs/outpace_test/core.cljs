(ns outpace-test.core
  (:require
    [outpace-test.util :refer [js-log log]]))

(enable-console-print!)

;;------------------------------------------------------------------------------
;; Node libraries
;;------------------------------------------------------------------------------

(def fs (js/require "fs"))

;;------------------------------------------------------------------------------
;; Parse
;;------------------------------------------------------------------------------

;; TODO: write me

;;------------------------------------------------------------------------------
;; Main
;;------------------------------------------------------------------------------

(defn -main [& args]
  (log args)
  )

(set! *main-cli-fn* -main)