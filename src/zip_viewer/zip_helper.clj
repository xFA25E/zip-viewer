(ns zip-viewer.zip-helper
  (:require [zip-viewer.wrapper.zip-entry :as zip-entry]
            [zip-viewer.wrapper.zip-file :as zip-file]))

;; {"dir" {:meta {:id "blah" :hash "blah" :dir true}
;;         "dir1" {:meta {:id "blah" :hash "blah" :dir true}
;;                 "file1" {:meta {:info "info"}}}
;;         "file" {:meta {:info "info"}}}}

(defn get-meta-array
  [entry]
  [(str "Name: " (zip-entry/get-name entry))
   (str "Type: " ({true "directory" false "file"} (zip-entry/directory? entry)))
   (str "Size: " (zip-entry/get-size entry))
   (str "Compressed size: " (zip-entry/get-compressed-size entry))
   (str "Time: " (zip-entry/get-time entry))
   (str "Hash code: " (zip-entry/hash-code entry))
   (str "Comment: " (zip-entry/get-comment entry))
   (str "Method: " (zip-entry/get-compression-method entry))
   (str "Extra: " (.toString (zip-entry/get-extra entry)))])

(defn zip->map
  [zip]
  (loop [[entry & entries] (enumeration-seq (zip-file/entries zip))
         result            (sorted-map)]
    (let [path       (clojure.string/split (zip-entry/get-name entry) #"/")
          map-path   (conj path :meta)
          new-result (assoc-in result map-path (get-meta-array entry))]
      (if (empty? entries)
        new-result
        (recur entries new-result)))))

(defn get-zip-map
  [name]
  (let [zip    (zip-file/open-reading name)
        result (zip->map zip)]
    (zip-file/close zip)
    result))
