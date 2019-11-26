(ns zip-viewer.wrapper.zip-entry
  (:import java.util.zip.ZipEntry))

(defn get-comment
  [zip-entry]
  (.getComment zip-entry))

(defn get-compressed-size
  "Returns the size of the compressed entry data, or -1 if not known."
  [zip-entry]
  (.getCompressedSize zip-entry))

(defn get-extra
  "Returns the extra field data for the entry, or null if none."
  [zip-entry]
  (.getExtra zip-entry))

(defn get-compression-method
  "Returns the compression method of the entry, or -1 if not specified."
  [zip-entry]
  (.getMethod zip-entry))

(defn get-name
  "Returns the name of the entry."
  [zip-entry]
  (.getName zip-entry))

(defn get-size
  "Returns the uncompressed size of the entry data, or -1 if not known."
  [zip-entry]
  (.getSize zip-entry))

(defn get-time
  "Returns the modification time of the entry, or -1 if not specified."
  [zip-entry]
  (.getTime zip-entry))

(defn hash-code
  "Returns the hash code value for this entry."
  [zip-entry]
  (.hashCode zip-entry))

(defn directory?
  "Returns true if this is a directory entry."
  [zip-entry]
  (.isDirectory zip-entry))

(defn zip-entry->string
  "Returns a string representation of the ZIP entry."
  [zip-entry]
  (.toString zip-entry))
