(ns outpace-test.core
  (:require
    [clojure.string :refer [split-lines]]
    [outpace-test.util :refer [js-log log]]))

(enable-console-print!)

;;------------------------------------------------------------------------------
;; Node libraries
;;------------------------------------------------------------------------------

(def fs (js/require "fs"))

;;------------------------------------------------------------------------------
;; Number Formats
;;------------------------------------------------------------------------------

(def numbers {
  (str "   "
       "  |"
       "  |") 1

  (str " _ "
       " _|"
       "|_ ") 2

  (str " _ "
       " _|"
       " _|") 3

  (str "   "
       "|_|"
       "  |") 4

  (str " _ "
       "|_ "
       " _|") 5

  (str " _ "
       "|_ "
       "|_|") 6

  (str " _ "
       "  |"
       "  |") 7

  (str " _ "
       "|_|"
       "|_|") 8

  (str " _ "
       "|_|"
       " _|") 9

  (str " _ "
       "| |"
       "|_|") 0 })

;;------------------------------------------------------------------------------
;; Parse
;;------------------------------------------------------------------------------

(defn- parse-file [file-contents]
  ;;(log (split-lines file-contents))
  )

;;------------------------------------------------------------------------------
;; Main
;;------------------------------------------------------------------------------

(def read-file-opts (js-obj "encoding" "utf-8"))

(defn -main [& args]
  (let [filename (first args)]
    (cond
      (not filename)
        (js-log "Please pass a filename as the first argument to this program.\nExiting...")
      (not (.existsSync fs filename))
        (js-log (str "Could not find file: " filename "\nExiting..."))
      :else
        (parse-file (.readFileSync fs filename read-file-opts)))))

(set! *main-cli-fn* -main)