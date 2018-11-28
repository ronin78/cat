(ns cat.core
  (:gen-class)
  (:require [clojure.set :as s]
            [ubergraph.core :as uber]
            [ubergraph.alg :as alg]
            )
  )

;; Constants
(def treasure 40)
(def car-treasure 10)
(def num-spaces 100)
(def max-move 4)

;; World functions
(defn move-edges
  []
   (apply uber/graph (mapcat #(map vec (partition 2 1 %)) spaces)) 
  )
(defn random-space
  []
  (rand-nth (flatten spaces))
  )
(defn random-dir
  []
  (rand-nth [:up :down :left :right])
  )
(defn rand-open
  []
  (if (= (rand-int 2) 1)
    true
    false
    )
  )

;; World objects
(defrecord Game-state [phone-list exit-list treasure-list character-list neighbor-list move-edges combat? turn])
(defrecord Phone [loc able?])
(defrecord Exit [loc open?])
(defn space-vec
  [prefix num-spaces]
  (vec (map #(str prefix %) (range 1 (+ num-spaces 1))))
  )
(def spaces [
             (vec (map #(str "S" %) [1 2]))
             (vec (map #(str "P" %) [1 2]))
             (vec (map #(str "SD" %) (range 1 10)))
             (vec (map #(str "BD" %) (range 1 10)))
             (vec (map #(str "H" %) (range 1 30)))
             (vec (map #(str "D" %) (range 1 14)))
             (vec (map #(str "DB" %) (range 1 13)))
             (vec (map #(str "DR" %) (range 1 3)))
             (space-vec "SF" 3)
             (space-vec "K" 27)
             (space-vec "L" 1)
             (space-vec "O" 10)
             ["S2" "SD1"]
             ["S1" "P1"]
             ["H1" "P2"]
             ["SF3" "SD6"]
             ["H13" "K1"]
             ["H2" "D1"]
             ["H4" "DB1"]
             ["DB6" "DR1"]
             ["DB6" "DR2"]
             ["H16" "K6"]
             ["H19" "O1"]
             ["H19" "L1"]
             ])

;; Characters objects
(defrecord Caretaker [message loc face hidden treasure arms legs mouth susp? aware?])
(defrecord Cat [message loc face hidden treasure arms legs mouth])
(defrecord Neighbor [alert-count investigating?])
(def general-movelist ["move"])
(def door-movelist ["check door" "open door" "close door" "lock door"])
(def closet-movelist ["look in closet"])
(def garage-movelist ["open garage door"])
(def treasure-movelist ["pick up treasure" "put down treasure"])
(def subdued-movelist ["resist"])
(def taunt-movelist ["taunt"])
(def cat-sabotage ["sabotage"])
(def cat-ambush ["ambush"])
(def cat-charm ["charm"])

;; Character functions
(defn can-move?
  [move-edges start end]
  (let [path (alg/shortest-path move-edges start end)] 
    (if (and path (<= (:cost path) max-move))
      (:cost path)
      nil
      ))
  )
(defn move
  [world-state character-keyword end]
    (if (can-move? (:move-edges world-state) (get-in world-state [:character-list character-keyword :loc]) end)
      (assoc-in world-state [:character-list character-keyword :loc] end)
      (print "Invalid move."))
  )


;; Object functions
(defn make-phone
  []
  (Phone. (random-space) true)
  )
(defn make-exits
  []
  (list (Exit. 1 (rand-open)) (Exit. 50 (rand-open)))
  )
(defn make-neighbor
  []
  (Neighbor. 0 false)
  )
(defn make-treasure
  []
  (apply merge-with + (take treasure (repeatedly #(hash-map (random-space) 1))))
  )
(def cat-message "You are the Cat, the most notorious cat burglar in the Southern United States. You appear, appropriate and abscond -- leaving bereft owners wriggling in your wake, if need be. You’ve recently opened shop in Decatur, GA. Having hit three houses this week, you’ve already identified your next victim...")
(def caretaker-message "You are the Caretaker. Your best friend is taking a long vacation and has asked you to housesit while he is away. You amuse yourself by working a little, playing a little, lounging a little. One of the neighbors mentioned to you yesterday that several houses in the area have recently been robbed. You hope that the burglar will steer clear of your friend’s house...but if they don’t, you’re not giving up your friend’s valuables without a fight.")
(defn make-characters
  []
  (hash-map :caretaker (Caretaker. caretaker-message (random-space) (random-dir) false 0 0 0 0 false false) :cat (Cat. cat-message "S1" :up false 0 0 0 0))
  )

;; Game functions
(defn print-message
  [player]
  (do (print (str (:message player) "\n\nYou are at location " (:loc player) "."))
      (print (str "\n> "))
      )
  )
(defn play
  [world-state]
   (let [active-character (:turn world-state)]
   (do 
     (print-message (get-in world-state [:character-list (:turn world-state)] world-state))
     (flush)
     (let [input (read-line)]
       (cond 
         (or (= input "quit") (= input "q")) nil
         (= input "move") (do (print "Where would you like to move?\n> ")
                                (flush)
                                (play (move world-state active-character (read-line)))
                                ) 
                            
         :else (do (print "Invalid selection.") (play world-state))
         ))))
  )
(defn start
  []
  (let [initial-state (Game-state. (repeat 2 (make-phone)) (make-exits) (make-treasure) (make-characters) (repeat 3 (make-neighbor)) move-edges false :cat)]
    (play initial-state)
    )
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (start))
