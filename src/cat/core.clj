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
(def max-move 4)
;(defn space-vec
;  [prefix num-spaces]
;  (vec (map #(str prefix %) (range 1 (+ num-spaces 1))))
;  )
(defn space-vec
  ([end]
  (map #(keyword (str %)) (vec (range 1 (+ end 1))))
   )
  ([start end]
   (map #(keyword (str %)) (vec (range start (+ end 1))))
   )
  )
(defn edge-details 
  [g start end] 
    (uber/attr g (last (alg/edges-in-path (alg/shortest-path g start end))) :direction)
  )


(def spaces [
             [[:1 :2] :left]
             [[:1 :3] :up]
             [[:3 :4] :up]
             [[:2 :5] :up]
             [(space-vec 5 13) :up]
             [(space-vec 14 18) :up] 
             [(space-vec 18 24) :left] 
             [[:18 :25] :right]
             [(space-vec 25 30) :up]
             [(space-vec 30 36) :right]
             [[:28 :37] :right]
             [[:37 :38] :right]
             [[:29 :39] :right]
             [[:39 :40] :right]
             [[:31 :39] :down]
             [[:37 :39] :up]
             [[:32 :40] :down]
             [[:38 :40] :up]
             [[:24 :37] :left]
             [[:24 :38] :left]
             [[:23 :53] :down]
             [(space-vec 40 46) :left]
             [(space-vec 46 48) :down]
             [(space-vec 48 51) :right]
             [[:43 :52] :down]
             [[:43 :53] :up]
             [[:44 :53] :up]
             [(space-vec 54 60) :right]
             [(space-vec 60 62) :down]
             [(space-vec 62 65) :left]
             [[:65 :66] :up]
             [[:56 :66] :down]
             [[:15 :40] :left]
             [[:15 :54] :right]
             [[:55 :66] :right]
             [[:60 :67] :up]
             [[:60 :68] :up]
             [[:38 :69] :down]
             [[:38 :70] :right]
             [(space-vec 70 73) :right]
             [(space-vec 73 75) :down]
             [(space-vec 75 78) :left]
             [[:70 :79] :down]
             [[:78 :79] :up]
             ])

;; Helper functions
(defn print-2n
  [s]
  (print (str s "\n\n"))
  )
(defn print-pr
  [s]
  (print (str s "\n> "))
  )
(defn opp-dir
  [d]
  (cond
    (= d :up) :down
    (= d :down) :up
    (= d :left) :right
    (= d :right) :left
    :else nil
    )
  )

;; World functions
(defn add-exit-edges
  [edge-list exit-edges]
     (uber/add-edges* edge-list exit-edges)
  )
(defn remove-exit-edges
  [edge-list exit-edges]
     (uber/remove-edges* edge-list exit-edges)
  )
(defn open-exits?
  [exit-list]
  (vec (mapcat #(:loc (val %)) (filter #(:open? (val %)) exit-list)))
  )
(defn locked-exits?
  [exit-list]
  (vec (mapcat #(:loc (val %)) (filter #(not (:open? (val %))) exit-list)))
  )
(defn move-edges
  [exit-list]
   (let [main-edges (apply uber/graph (mapcat #(map vec (partition 2 1 %)) spaces))
         open-exits (open-exits? exit-list)
         ]
     (add-exit-edges main-edges open-exits)
     ) 
  )
(defn move-dir-edges
  [exit-list]
   (let [space-edges (mapcat (fn [e] (mapv #(into [] (flatten [% {:direction (last e)}])) (partition 2 1 (first e)))) spaces)
         rev-edges (mapv #(into [] (flatten [(reverse (drop-last %)) {:direction (opp-dir (:direction (last %)))}])) space-edges)
         main-edges (apply uber/multidigraph (into [] (concat space-edges rev-edges)))
         open-exits (open-exits? exit-list)
         ]
     (add-exit-edges main-edges open-exits)
     ) 
  )
(defn random-space
  []
  (rand-nth (into () (remove #(> 14 (Integer. (name %))) (set (flatten (map first spaces))))))
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
(defn is-treasure?
  [world-state loc]
  (let [treasure-list (:treasure-list world-state)]
    (get treasure-list loc)
    )
  )

;; World objects
(defrecord Game-state [phone-list exit-list treasure-list character-list neighbor-list move-edges combat? turn])
(defrecord Phone [loc able?])
(defrecord Exit [loc open?])

;; Characters objects
(defrecord Caretaker [message loc face hidden treasure arms legs mouth muffled? susp? aware?])
(defrecord Cat [message loc face hidden treasure arms legs mouth muffled?])
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
(defn other-character
  [character]
  (cond
    (= character :cat) :caretaker
    (= character :caretaker) :cat)
  )
(defn change-character
  [world-state]
  (assoc world-state :turn (other-character (:turn world-state)))
  )
(defn can-move?
  [move-edges start end]
  (let [path (alg/shortest-path move-edges start end)] 
    (if (and path (<= (:cost path) max-move))
      (:cost path)
      nil
      ))
  )
(defn can-see?
  [see-edges player opponent]
  (let [ploc (:loc player)
        oloc (:loc opponent)
        pdir (:face player)
        odir (:face opponent)
        path (alg/shortest-path see-edges ploc oloc)
        edges (alg/edges-in-path path)]
    (cond 
      (nil? path) nil
      (= (uber/attr see-edges (first edges) :direction) (opp-dir pdir)) nil
      (every? #(= (uber/attr see-edges % :direction) pdir) edges) {:see-loc oloc :see-dir odir}
      (and (< (:cost path) max-move) (every? #(= (uber/attr see-edges % :direction) (uber/attr see-edges (first see-edges) :direction)) edges)) {:see-loc oloc :see-dir odir}
      :else nil
          )
    )
  )
(defn make-noise
  ([move-num]
   (println "You moved " move-num " and made " (max (- (+ 1 (rand-int 6)) (- 4 move-num)) 0) " noise!")
  )
  ([move-num start end]
   (println "You moved " move-num "from space " (name start) "to space " (name end) " and made " (max (- (+ 1 (rand-int 6)) (- 4 move-num)) 0) " noise!") 
   )
  )
(defn move
  [world-state character-keyword end]
  (let [start-loc (get-in world-state [:character-list character-keyword :loc])
        move-num (can-move? (:move-edges world-state) start-loc end)]
    (if move-num
      (do 
        (make-noise move-num start-loc end) 
        (change-character (update-in world-state [:character-list character-keyword] assoc :loc end :face (edge-details (:move-edges world-state) start-loc end)))
          )
      (do (print-2n "You cannot move there.")
          world-state
          )))
  )

;; Object functions
(defn make-phone
  []
  (Phone. (random-space) true)
  )
(defn make-exits
  []
  (hash-map :front (Exit. [[:4 :14 {:direction :up}] [:14 :4 {:direction :down}]] (rand-open)) :side (Exit. [[:10 :39 {:direction :right}] [:39 :10 {:direction :left}]] (rand-open)))
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
  (hash-map :caretaker (Caretaker. caretaker-message (random-space) (random-dir) false 0 0 0 0 false false false) :cat (Cat. cat-message :1 :up false 0 0 0 0 false))
  )

;; Game functions
(defn get-player
  [world-state]
  (get-in world-state [:character-list (:turn world-state)])
  )
(defn available-exit
  [world-state loc]
  (first (filter #(contains? (set (drop-last (first (:loc (val %))))) loc) (:exit-list world-state)))
  )
(defn check-door
  [world-state player]
  (let [loc (:loc player)
        available-exit (available-exit world-state loc)
        ]
    (if (nil? available-exit)
      (println "There are no doors near you.")
      (let [open? (:open? (val available-exit))]
        (if open?
          (println "The door in front of you is unlocked.")
          (println "The door in front of you is locked.")
          )
        )
      )
    )
  )
(defn unlock-exit
  [world-state exit]
  (let [unlock-state (assoc-in world-state [:exit-list (key exit) :open?] true)]
  (assoc unlock-state :move-edges (add-exit-edges (:move-edges unlock-state) (open-exits? (:exit-list unlock-state)))))
  )
(defn lock-exit
  [world-state exit]
  (let [lock-state (assoc-in world-state [:exit-list (key exit) :open?] false)]
  (do (assoc lock-state :move-edges (remove-exit-edges (:move-edges lock-state) (locked-exits? (:exit-list lock-state))))
      (println "The door in front of you is now locked.")
      )) 
  )
(defn unlock-door
  [world-state player]
  (let [loc (:loc player)
        available-exit (available-exit world-state loc)
        ]
    (if (= (:turn world-state) :caretaker)
      (if (nil? available-exit)
        (do (println "There are no doors near you.")
            world-state
            )
        (let [open? (:open? available-exit)]
          (if open?
            (do (println "The door in front of you is already unlocked!")
                world-state
                )
            (do (println "The door in front of you is now unlocked.")
                (change-character (unlock-exit world-state available-exit))
                )
            )
          )
        )
      (do (println "This isn't your house!")
          world-state
          )
      )
    )
  )
(defn pick-lock
  [world-state player]
  (let [loc (:loc player)
        available-exit (available-exit world-state loc)
        ]
    (if (= (:turn world-state) :cat)
      (if (nil? available-exit)
        (do (println "There are no doors near you.")
            world-state
            )
        (let [open? (:open? available-exit)]
          (if open?
            (do (println "The door in front of you is already unlocked!")
                world-state
                )
            (do (println "The door in front of you is now unlocked.")
                (change-character (unlock-exit world-state available-exit))
                )
            )
          )
        )
      (do (println "You don't know how to do that.")
          world-state
          )
      )
    )
  )
(defn lock-door
  [world-state player]
  (let [loc (:loc player)
        available-exit (available-exit world-state loc)
        ]
    (if (= (:turn world-state) :caretaker)
      (if (nil? available-exit)
        (do (println "There are no doors near you.")
            world-state
            )
        (let [open? (:open? available-exit)]
          (if open?
            (do (println "The door in front of you is now locked.")
                (change-character (lock-exit world-state available-exit))
                )
            (do (println "The door in front of you is already locked!")
                world-state
                )
            )
          )
        )
      (do (println "Are you sure you want to do that?")
          world-state
          )
      )
    )
  )
(defn remove-treasure-from-list
  [treasure-list loc]
  (dissoc treasure-list loc)
  )
(defn pickup-treasure
  [world-state player]
  (if (is-treasure? world-state (:loc player))
    (let [loc (:loc player)
          treasure-amt (is-treasure? world-state loc)
          player-amt (get-in world-state [:character-list (:turn world-state) :treasure])
          ]
      (change-character 
        (assoc-in (assoc world-state :treasure-list (remove-treasure-from-list (:treasure-list world-state) loc)) [:character-list (:turn world-state) :treasure] (+ player-amt treasure-amt)))   
;(println player-amt)
      )
    world-state
    )
  )
(defn print-message
  [world-state active-character]
  (let [player (get-in world-state [:character-list active-character])
        opponent (get-in world-state [:character-list (other-character active-character)])]
  (do (println (str (:message player)))
      (cond (can-see? (:move-edges world-state) player opponent) (println "You see someone at " (:loc opponent) " facing " (:face opponent)))
      (println "\nYou are at location " (name (:loc player)) " facing " (name (:face player)) ".")
      (cond (:susp? player) (println "You are suspicious."))
      (cond (:aware player) (println "You are aware of the Cat."))
      (cond (is-treasure? world-state (:loc player))
        (println "There is " (is-treasure? world-state (:loc player)) " treasure at your location.")
        )
      (cond (> (:treasure player) 0) (println "You have " (:treasure player) "treasure."))
      (cond (:muffled? player) (println "You are muffled."))
      (cond (> 0 (:arms player)) (println "Your arms are subdued by " (:arms player) "."))
      (cond (> 0 (:legs player)) (println "Your legs are subdued by " (:legs player) "."))
      (cond (> 0 (:mouth player)) (println "Your mouth is subdued by " (:mouth player) "."))
      (print-pr "What would you like to do?")
      ))
  )
(defn play
  [world-state]
   (let [active-character (:turn world-state)
         opponent (other-character active-character)]
     (if (and (= active-character :caretaker) 
              (= (get-in world-state [:character-list active-character :aware?]) false) 
              (can-see? (:move-edges world-state) (get-in world-state [:character-list active-character]) (get-in world-state [:character-list opponent])))
       (play (assoc-in world-state [:character-list active-character :aware?] true))
       (do 
         (print-message world-state active-character)
         (flush)
         (let [input (read-line)]
           (cond 
             (or (= input "quit") (= input "q")) nil
             (= input "move") (do (print-pr "Where would you like to move?")
                                  (flush)
                                  (play (move world-state active-character (keyword (read-line))))
                                  ) 
             (= input "check door") (do (check-door world-state (get-player world-state))
                                        (play world-state)
                                        )
             (= input "pick lock") (play (pick-lock world-state (get-player world-state)))
             (= input "unlock door") (play (unlock-door world-state (get-player world-state)))
             (= input "lock door") (play (lock-door world-state (available-exit world-state (get-in world-state [:character-list active-character :loc]))))
             (= input "pick up treasure") (play (pickup-treasure world-state (get-player world-state)))                        
             :else (do (println "Invalid selection.") (play world-state))
             )))))
   )
(defn start
  []
  (let [exit-list (make-exits)
        initial-state (Game-state. (repeat 2 (make-phone)) exit-list (make-treasure) (make-characters) (repeat 3 (make-neighbor)) (move-dir-edges exit-list) false :cat)]
    (play initial-state)
    )
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (start))
