(ns outpace-test.util)

;;------------------------------------------------------------------------------
;; Util Functions
;;------------------------------------------------------------------------------

(defn log
  "Log a Clojure thing."
  [thing]
  (.log js/console (pr-str thing)))

(defn js-log
  "Log a JavaScript thing."
  [& js-things]
  (apply (.-log js/console) js-things))
