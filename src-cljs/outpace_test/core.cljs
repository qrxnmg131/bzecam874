(ns outpace-test.core
  (:require
    [clojure.string :refer [blank? split split-lines]]
    [outpace-test.util :refer [js-log log]]))

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
;; Convert Pipes and Underscores to Numbers
;;------------------------------------------------------------------------------

(defn- str->number [s]
  (get numbers s "?"))

(defn- lines->numbers
  "Converts an account number from pipes/underscores to a string of numbers.
   '?' if the number is not recognized."
  [lines]
  (let [row-1 (partition-all 3 (first lines))
        row-2 (partition-all 3 (second lines))
        row-3 (partition-all 3 (nth lines 2))]
    (->> (map concat row-1 row-2 row-3)
         (map #(apply str %))
         (map str->number)
         (apply str))))

;;------------------------------------------------------------------------------
;; Validate Account Numbers
;;------------------------------------------------------------------------------

(def account-number-length 9)

(defn- valid-account-number-format?
  "Is the account number in a valid format?"
  [s]
  (and (string? s)
       (= (count s) account-number-length)
       (= -1 (.indexOf s "?"))))

(defn- valid-account-number-checksum?
  "Does the account number have a valid checksum?
   NOTE: this function assumes an account number in a valid format"
  [s]
  (let [sum (->> (split s "")
                 (map int)
                 reverse
                 (map-indexed #(* (inc %1) %2))
                 (apply +))]
    (zero? (mod sum 11))))

(defn- flag-invalid-accounts
  "Flag accounts that have an invalid format or checksum."
  [n]
  (cond
    (not (valid-account-number-format? n))
      (str n " ILL")
    (not (valid-account-number-checksum? n))
      (str n " ERR")
    :else n))

;;------------------------------------------------------------------------------
;; Parse File
;;------------------------------------------------------------------------------

(def expected-line-length 27)

;; my text editor removes trailing whitespace by default
;; this function pads spaces to the right of each line
(defn- ensure-line-length [line]
  (let [len (count line)]
    (if-not (= len expected-line-length)
      (str line (apply str (repeat (- expected-line-length len) " ")))
      line)))

(defn- parse-accounts
  "Returns a sequence of account numbers."
  [file-contents]
  (->> file-contents
    ;; split on new lines
    split-lines

    ;; make sure all the lines have the same length
    (map ensure-line-length)

    ;; split every 4 lines
    (partition-all 4)

    ;; join lines so that each account number is a string
    (map lines->numbers)

    ;; flag account numbers for invalid numbers and checksums
    (map flag-invalid-accounts)))

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
        (let [file-contents (.readFileSync fs filename read-file-opts)
              account-numbers (parse-accounts file-contents)]
          (doall (map js-log account-numbers))))))

(set! *main-cli-fn* -main)