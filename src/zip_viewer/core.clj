(ns zip-viewer.core
  (:require [lanterna.screen :as screen]
            clojure.string)
  (:use [zip-viewer.zip-helper :refer [get-zip-map]]
        [clojure.java.io :refer [file]])
  (:gen-class))

(def main-screen (screen/get-screen :text))
(def vertical-move {\j 1 \k -1})

(defn check-vertical-move
  [saved-positions add total]
  (let [n (+ (peek saved-positions) add)]
    (if (<= 0 n (dec total))
      (conj (pop saved-positions) n)
      saved-positions)))

(defn get-map-keys
  [zip-map path]
  (filterv #(not= :meta %) (keys (get-in zip-map path))))

(defn make-entries-sheet
  [map-keys position]
  (update map-keys position #(str ">> " %)))

(defn make-meta-path
  [path map-keys position]
  (conj path (get map-keys position) :meta))

(defn -main
  [& args]
  (cond
    (empty? args)                       (println "Usage: <program name> <path to zipfile>")
    (not (.exists (file (first args)))) (println "File doesn't exist!")
    :else
    (loop [zip-map         (get-zip-map (first args))
           path            []
           saved-positions [0]]
      (screen/start main-screen)
      (let [screen-size   (screen/get-size main-screen)
            map-keys      (get-map-keys zip-map path)
            entries-sheet (make-entries-sheet map-keys (peek saved-positions))
            meta-sheet    (get-in zip-map (make-meta-path path map-keys
                                                          (peek saved-positions)))]
        (screen/clear main-screen)
        (screen/put-string main-screen 0 0 (str "/" (clojure.string/join "/" path)))
        (screen/put-sheet main-screen 0 1 entries-sheet)
        (screen/put-sheet main-screen (int (/ (screen-size 0) 2)) 1 meta-sheet)
        (screen/redraw main-screen)

        (let [key (screen/get-key-blocking main-screen)]
          (case key
            (\j \k) (recur zip-map path (check-vertical-move saved-positions
                                                             (vertical-move key)
                                                             (count map-keys)))
            \h      (if (empty? path)
                      (recur zip-map path saved-positions)
                      (recur zip-map (pop path) (pop saved-positions)))
            \l      (let [new-path (conj path (map-keys (peek saved-positions)))]
                      (if (zero? (count (get-map-keys zip-map new-path)))
                        (recur zip-map path saved-positions)
                        (recur zip-map new-path (conj saved-positions 0))))
            \q      (screen/stop main-screen)))))))
