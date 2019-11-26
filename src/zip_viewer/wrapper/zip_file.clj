(ns zip-viewer.wrapper.zip-file
  (:import java.util.zip.ZipFile))

(defn open-reading
  "Opens a ZIP file for reading."
  [name]
  (new ZipFile name))

(defn close
  "Closes the ZIP file."
  [zip]
  (.close zip))

(defn entries
  "Returns an enumeration of the ZIP file entries."
  [zip]
  (.entries zip))

(defn get-entry
  "Returns the ZIP file entry for the specified name, or null if not found."
  [zip name]
  (.getEntry zip name))

(defn get-name
  "Returns the path name of the ZIP file."
  [zip]
  (.getName zip))

(defn size
  "Returns the number of entries in the ZIP file."
  [zip]
  (.size zip))

