(ns user
  (:require [portal.api :as portal]))

(comment
  ;; Portal helpers
  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  ;; Open Portal window with solarized-dark theme
  (portal/open {:portal.colors/theme :portal.colors/solarized-dark})

  ;; Open Portal window with solarized-light theme
  (portal/open {:portal.colors/theme :portal.colors/solarized-light})

  ;; add portal as a tap target (add-tap)
  (portal/tap)

  ;; send data to the portal inspector window (or any other data you wish to send)
  (tap> {:accounts [{:name "jen" :email "jen@jen.com"} {:name "sara" :email "sara@sara.com"}]})

  ;; Clear all values in the portal inspector window
  (portal/clear)

  ;; Close the portal window
  (portal/close)

  (tap> 1)

  ,)
