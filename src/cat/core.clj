(ns cat.core
  (:gen-class)
  (:require [clojure.set :as s]
            [clojure.pprint :as pp]
            [ubergraph.core :as uber]
            [ubergraph.alg :as alg]
            )
  )

;; Constants
(def treasure 40)
(def car-treasure 10)
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
(defn edge-dir 
  [g start end] 
    (uber/attr g (last (alg/edges-in-path (alg/shortest-path g start end))) :direction)
  )
(defn edge-noise 
  ([g loc noise]
   {loc noise}
   )
  ([g start end noise] 
   (assoc (into {} (map #(hash-map (uber/src %) noise) (alg/edges-in-path (alg/shortest-path g start end)))) end noise)
   )
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
             [[:24 :84] :up]
             [[:24 :155] :left]
             [[:24 :156] :left]
             [[:23 :53] :down]
             [(space-vec 40 46) :left]
             [(space-vec 46 48) :down]
             [(space-vec 48 51) :right]
             [[:43 :52] :down]
             [[:51 :52] :up]
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
             [[:27 :95] :left]
             [(space-vec 80 84) :left]
             [(space-vec 84 87) :up]
             [(space-vec 87 91) :right]
             [[:80 :95] :up]
             [[:81 :94] :up]
             [[:90 :92] :down]
             [[:92 :94] :down]
             [[:91 :93] :down]
             [[:91 :96] :up]
             [[:93 :95] :down]
             [[:96 :97] :up]
             [(space-vec 97 103) :left]
             [(space-vec 103 105) :down]
             [[:105 :106] :right]
             [[:87 :106] :left]
             [(space-vec 155 157) :up]
             ])
(def view-spaces [
                  [[:43 :47] :left]
                  [[:43 :48] :down]
                  [[:43 :49] :down]
                  [[:43 :50] :down]
                  [[:44 :47] :left]
                  [[:44 :48] :down]
                  [[:44 :49] :down]
                  [[:44 :50] :down]
                  [[:44 :51] :down]
                  [[:44 :52] :right]
                  [[:45 :47] :left]
                  [[:45 :48] :down]
                  [[:45 :49] :down]
                  [[:45 :50] :down]
                  [[:45 :51] :down]
                  [[:45 :52] :right]
                  [[:46 :49] :down]
                  [[:46 :50] :down]
                  [[:46 :51] :right]
                  [[:46 :52] :right]
                  [[:56 :61] :right]
                  [[:56 :62] :right]
                  [[:56 :63] :down]
                  [[:56 :64] :down]
                  [[:57 :61] :right]
                  [[:57 :62] :down]
                  [[:57 :63] :down]
                  [[:57 :64] :down]
                  [[:57 :65] :down]
                  [[:57 :66] :left]
                  [[:58 :61] :right]
                  [[:58 :62] :down]
                  [[:58 :63] :down]
                  [[:58 :64] :down]
                  [[:58 :65] :down]
                  [[:58 :66] :left]
                  [[:59 :61] :right]
                  [[:59 :62] :down]
                  [[:59 :63] :down]
                  [[:59 :64] :down]
                  [[:59 :65] :down]
                  [[:59 :66] :left]
                  [[:60 :63] :down]
                  [[:60 :64] :down]
                  [[:60 :65] :left]
                  [[:60 :66] :left]
                  [[:70 :74] :right]
                  [[:70 :75] :right]
                  [[:70 :76] :down]
                  [[:70 :77] :down]
                  [[:71 :74] :right]
                  [[:71 :75] :down]
                  [[:71 :76] :down]
                  [[:71 :77] :down]
                  [[:71 :78] :down]
                  [[:71 :79] :left]
                  [[:72 :74] :right]
                  [[:72 :75] :down]
                  [[:72 :76] :down]
                  [[:72 :77] :down]
                  [[:72 :78] :down]
                  [[:72 :79] :left]
                  [[:73 :76] :down]
                  [[:73 :77] :down]
                  [[:73 :78] :left]
                  [[:73 :79] :left]
                  [[:81 :85] :left]
                  [[:81 :86] :left]
                  [[:81 :87] :left]
                  [[:81 :88] :up]
                  [[:81 :89] :up]
                  [[:82 :85] :left]
                  [[:82 :86] :left]
                  [[:82 :87] :up]
                  [[:82 :88] :up]
                  [[:82 :89] :up]
                  [[:82 :90] :up]
                  [[:82 :92] :right]
                  [[:82 :94] :right]
                  [[:83 :85] :left]
                  [[:83 :86] :left]
                  [[:83 :87] :up]
                  [[:83 :88] :up]
                  [[:83 :89] :up]
                  [[:83 :90] :up]
                  [[:83 :92] :right]
                  [[:83 :94] :right]
                  [[:87 :92] :right]
                  [[:87 :94] :right]
                  [[:87 :96] :right]
                  [[:87 :97] :right]
                  [[:87 :98] :right]
                  [[:87 :99] :up]
                  [[:87 :100] :up]
                  [[:87 :101] :up]
                  [[:87 :102] :up]
                  [[:87 :103] :up]
                  [[:87 :104] :left]
                  [[:88 :96] :right]
                  [[:88 :97] :right]
                  [[:88 :98] :up]
                  [[:88 :99] :up]
                  [[:88 :100] :up]
                  [[:88 :101] :up]
                  [[:88 :102] :up]
                  [[:88 :103] :left]
                  [[:88 :104] :left]
                  [[:89 :96] :right]
                  [[:89 :97] :up]
                  [[:89 :98] :up]
                  [[:89 :99] :up]
                  [[:89 :100] :up]  
                  [[:89 :101] :up]  
                  [[:89 :102] :left]  
                  [[:89 :103] :left]  
                  [[:89 :104] :left]  
                  [[:90 :96] :up]
                  [[:90 :97] :up]
                  [[:90 :98] :up]
                  [[:90 :99] :up]
                  [[:90 :100] :up]
                  [[:90 :101] :left]
                  [[:90 :102] :left]
                  [[:90 :103] :left]
                  [[:90 :104] :left]
                  [[:96 :106] :left]
                  [[:99 :106] :left]
                  [[:100 :106] :down]
                  [[:101 :106] :down]
                  [[:102 :106] :down]
                  [[:103 :106] :down]
                  [[:104 :106] :right]
                  ])
(def sound-spaces [
                 [[:14 :52] :left] 
                 [[:14 :66] :right] 
                 [[:14 :137] :right]
                 [[:16 :53] :left]
                 [[:16 :56] :right]
                 [[:17 :136] :right]
                 [[:18 :80] :up]
                 [[:19 :40] :up]
                 [[:19 :41] :up]
                 [[:19 :80] :up]
                 [[:19 :81] :up]
                 [[:20 :80] :up]
                 [[:20 :81] :up]
                 [[:21 :80] :up]
                 [[:21 :81] :up]
                 [[:21 :82] :up]
                 [[:22 :53] :down]
                 [[:22 :81] :up]
                 [[:22 :82] :up]
                 [[:22 :83] :up]
                 [[:23 :82] :up]
                 [[:23 :83] :up]
                 [[:23 :84] :up]
                 [[:21 :41] :down]
                 [[:21 :42] :down]
                 [[:22 :42] :down]
                 [[:22 :43] :down]
                 [[:23 :43] :down]
                 [[:23 :44] :down]
                 [[:24 :44] :down]
                 [[:24 :45] :down]
                 [[:24 :53] :down]
                 [[:24 :83] :up]
                 [[:24 :84] :up]
                 [[:24 :154] :down]
                 [[:25 :69] :right]
                 [[:26 :69] :right]
                 [[:27 :69] :right]
                 [[:25 :80] :left]
                 [[:26 :80] :left]
                 [[:26 :95] :left]
                 [[:27 :80] :left]
                 [[:27 :93] :left]
                 [[:27 :95] :left]
                 [[:28 :91] :left]
                 [[:28 :93] :left]
                 [[:28 :95] :left]
                 [[:29 :91] :left]
                 [[:29 :93] :left]
                 [[:29 :96] :left]
                 [[:30 :91] :left]
                 [[:30 :96] :left]
                 [[:30 :97] :left]
                 [[:32 :70] :down]
                 [[:33 :70] :down]
                 [[:34 :70] :down]
                 [[:34 :71] :down]
                 [[:34 :72] :down]
                 [[:35 :71] :down]
                 [[:35 :72] :down]
                 [[:35 :73] :down]
                 [[:36 :72] :down]
                 [[:36 :73] :down]
                 [[:37 :69] :down]
                 [[:40 :70] :down]
                 [[:56 :136] :up]
                 [[:57 :136] :up]
                 [[:59 :67] :up]
                 [[:60 :68] :up]
                 [[:62 :135] :down]
                 [[:65 :137] :down]
                 [[:66 :137] :down]
                 [[:67 :75] :up]
                 [[:67 :76] :up]
                 [[:67 :136] :left]
                 [[:68 :75] :up]
                 [[:68 :76] :up]
                 [[:69 :78] :right]
                 [[:69 :136] :down]
                 [[:77 :136] :down]
                 [[:78 :136] :down]
                 [[:84 :157] :left]
                 [[:85 :157] :left]
                 [[:135 :137] :left]
                 [[:154 :155] :up]
                  ])  
;; TODO Noise
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
(defn add-edges
  [edge-list new-edges]
     (uber/add-edges* edge-list new-edges)
  )
(defn remove-exit-edges
  [edge-list exit-edges]
     (uber/remove-edges* edge-list exit-edges)
  )
(defn find-exits
  [exit-list f]
  (vec (mapcat #(:loc (val %)) (filter #(f (val %)) exit-list)))
  )
(defn make-edge-with-reverse
  ([space-vec]
  (let [space-edges (mapcat (fn [e] (mapv #(into [] (flatten [% {:direction (last e) :weight 1}])) (partition 2 1 (first e)))) space-vec)
        rev-edges (mapv #(into [] (flatten [(reverse (drop-last %)) {:direction (opp-dir (:direction (last %))) :weight 1}])) space-edges)
        ]
    (into [] (concat space-edges rev-edges))))
  ([space-vec weight]
   (let [space-edges (mapcat (fn [e] (mapv #(into [] (flatten [% {:direction (last e) :weight weight}])) (partition 2 1 (first e)))) space-vec)
        rev-edges (mapv #(into [] (flatten [(reverse (drop-last %)) {:direction (opp-dir (:direction (last %))) :weight weight}])) space-edges)
        ]
    (into [] (concat space-edges rev-edges)))
   )
  )
(def main-edges (apply uber/multidigraph (make-edge-with-reverse spaces)))
(def view-edges-no-hidden (add-edges main-edges (make-edge-with-reverse view-spaces)))
(def sound-edges (add-edges view-edges-no-hidden (make-edge-with-reverse sound-spaces 2)))
(defn move-edges-with-exit
  [exit-list]
  (add-edges main-edges (find-exits exit-list :open?))
  )
(defn random-space
  []
  (rand-nth (into () (remove #(or (> 14 (Integer. (name %))) (<= 145 153 (Integer. (name %)))) (set (flatten (map first spaces))))))
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
(defn free-action
  [world-state]
  (assoc world-state :free-action? false)
  )
(defn is-treasure?
  [world-state loc]
  (let [treasure-list (:treasure-list world-state)]
    (get treasure-list loc)
    )
  )

;; World objects

;; Characters objects

;; Character functions
(def cat-message-first "You are the Cat, the most notorious cat burglar in the Southern United States. You appear, appropriate and abscond -- leaving bereft owners wriggling in your wake, if need be. You’ve recently opened shop in Decatur, GA. Having hit three houses this week, you’ve already identified your next victim...")
(def cat-message "You are the Cat.")
(def caretaker-message-first "You are the Caretaker. Your best friend is taking a long vacation and has asked you to housesit while he is away. You amuse yourself by working a little, playing a little, lounging a little. One of the neighbors mentioned to you yesterday that several houses in the area have recently been robbed. You hope that the burglar will steer clear of your friend’s house...but if they don’t, you’re not giving up your friend’s valuables without a fight.")
(def caretaker-message "You are the Caretaker.")
(defn make-characters
  []
  (hash-map :caretaker {:message caretaker-message-first
                        :loc (random-space)
                        :face (random-dir)
                        :hidden false
                        :treasure 0
                        :arms 0
                        :legs 0
                        :mouth 0
                        :muffled? false
                        :susp-count 0
                        :aware? false}
            :cat {:message cat-message-first
                        :loc :1
                        :face :up
                        :hidden false
                        :treasure 0
                        :arms 0
                        :legs 0
                        :mouth 0
                        :muffled? false})
  )
(defn other-character
  [character]
  (cond
    (= character :cat) :caretaker
    (= character :caretaker) :cat)
  )
(defn get-player
  [world-state]
  (get-in world-state [:character-list (:turn world-state)])
  )
(defn get-opponent
  [world-state]
  (get-in world-state [:character-list (other-character (:turn world-state))])
  )
(defn get-caretaker
  [world-state]
  (get-in world-state [:character-list :caretaker])
  )
(defn get-cat
  [world-state]
  (get-in world-state [:character-list :cat])
  )
(defn get-player-loc
  [world-state]
  (:loc (get-player world-state)) 
  )
(defn get-player-arms
  [world-state]
  (:arms (get-player world-state)) 
  )
(defn get-player-legs
  [world-state]
  (:legs (get-player world-state)) 
  )
(defn get-player-mouth
  [world-state]
  (:mouth (get-player world-state)) 
  )
(defn get-opponent-loc
  [world-state]
  (:loc (get-opponent world-state)) 
  )
(defn is-caretaker-aware?
  [world-state]
  (get-in world-state [:character-list :caretaker :aware])
  )
(defn make-caretaker-suspicious
  [world-state]
  (update-in world-state [:character-list :caretaker :susp-count] inc)
  )
(defn make-caretaker-aware
  [world-state]
  (assoc-in world-state [:character-list :caretaker :aware] true)
  )
(defn is-subdued?
  [player]
  (> (reduce + (vals (select-keys player [:arms :legs :mouth]))) 0)
  )
(defn change-character
  [world-state]
  (if (:free-action? world-state)
    (assoc world-state :turn (other-character (:turn world-state)))
    (assoc world-state :free-action? true)
    )
  )
(defn max-move
  [world-state]
  (cond (and (= (:turn world-state) :caretaker)
             (not (get-in world-state [:character-list :caretaker :aware]))
             ) 3
        (> (get-in world-state [:character-list (:turn world-state) :legs]) 0) 2
        (> (get-in world-state [:character-list (:turn world-state) :legs]) 6) 0
        (and (= (:turn world-state) :caretaker)
             (get-in world-state [:character-list :caretaker :aware]) 
             ) 5
        :else 4
        )
  )
(defn can-move?
  [world-state start end]
  (let [path (alg/shortest-path (:move-edges world-state) start end)] 
    (if (and path (<= (:cost path) (max-move world-state)))
      (:cost path)
      nil
      ))
  )
(defn is-hidden?
  [player]
  (:hidden player)
  )
(defn get-active-view-edges
  [opponent]
  (if (is-hidden? opponent)
    main-edges
    view-edges-no-hidden
    )
  )
(defn can-see?
  ([world-state see-edges]
  (let [player (get-player world-state)
        opponent (get-opponent world-state)
        ploc (:loc player)
        oloc (:loc opponent)
        pdir (:face player)
        odir (:face opponent)
        path (alg/shortest-path see-edges ploc oloc)
        edges (alg/edges-in-path path)]
    (cond 
      (nil? path) nil
      (= (uber/attr see-edges (first edges) :direction) (opp-dir pdir)) nil
      (or (every? #(= (uber/attr see-edges % :direction) pdir) edges)
          (and (< (:cost path) (max-move world-state)) (every? #(= (uber/attr see-edges % :direction) (uber/attr see-edges (first edges) :direction)) edges))) 
      {:see-loc oloc :see-dir odir}
      :else nil
          )
    ))
  ([world-state see-edges ploc oloc]
   (let [player (get-player world-state)
        opponent (get-opponent world-state)
        pdir (:face player)
        odir (:face opponent)
        path (alg/shortest-path see-edges ploc oloc)
        edges (alg/edges-in-path path)]
    (cond 
      (nil? path) nil
      (= (uber/attr see-edges (first edges) :direction) (opp-dir pdir)) nil
      (or (every? #(= (uber/attr see-edges % :direction) pdir) edges)
          (and (< (:cost path) (max-move world-state)) (every? #(= (uber/attr see-edges % :direction) (uber/attr see-edges (first edges) :direction)) edges))) 
      {:see-loc oloc :see-dir odir}
      :else nil
          )
    )
   )
  )
;; TODO Something wonky with the direction
(defn can-hear?
  ([world-state]
   (let [player (get-player world-state) 
         opponent (get-opponent world-state)
         p (alg/shortest-path sound-edges (:loc player) (:loc opponent))]
     (cond 
       (= (:loc player) (:loc opponent)) 0
       (and p (<= (:cost p) 12)) (:cost p)
       :else nil
       )
     )
   )
  ([sound-edges ploc nmap]
    (if-let [h (and nmap  (last (filter (fn [[k v]] (let [p (alg/shortest-path sound-edges ploc k)] (and p (<= (:cost p) v)))) nmap)))]
      (uber/attr sound-edges (first (alg/edges-in-path (alg/shortest-path sound-edges ploc (key h)))) :direction)
      nil
      ))
  )
(defn move-noise
  [world-state move-num]
  (cond 
    (and (= (:turn world-state) :caretaker) (not (is-caretaker-aware? world-state)) (not (is-subdued? (get-player world-state)))) (max (- (+ 1 (rand-int 6)) (- 4 (- move-num 1))) 0) 
    :else (max (- (+ 1 (rand-int 6)) (- 4 move-num)) 0)
    )
  )
(defn assoc-player
  [world-state pkey & rest]
   (apply (partial update-in world-state [:character-list pkey] assoc) rest) 
  )
(defn move
  [world-state character-keyword end]
  (let [player (get-player world-state)
        start-loc (:loc player)
        move-num (can-move? world-state start-loc end)]
    (if move-num
      (let [noise (move-noise world-state move-num)] 
        (change-character (assoc-player world-state character-keyword :loc end :face (edge-dir (:move-edges world-state) start-loc end) :noise-map (edge-noise sound-edges start-loc end noise) :add-message (str "On your last turn, you moved " move-num " from space " (name start-loc) " to space " (name end) " and made " noise " noise."))))
      (do (print-2n (str "You cannot move there."))
          world-state
          )))
  )
(defn yell
  [world-state]
  (let [player (get-player world-state)
        update-for-noise (fn [n] 
                           (change-character 
                             (assoc-player world-state :caretaker 
                                           :noise-map (edge-noise sound-edges (get-player-loc world-state) n)
                                           :add-message (str "On your last turn, you yelled for " n "noise!")  
                                           )))
        ]
  (cond 
    (= (:turn world-state) :cat) (do (println "What part of 'cat burglar' don't you understand?")
                                     world-state
                                     )
    (is-caretaker-aware? world-state) (cond 
                                        (:muffled? player) (do (println "The Cat's gloved hand muffles your yell completely.")
                                                                     world-state
                                                                     )
                                        (> (get-player-mouth world-state) 6) (do (println "You cannot yell when gagged.")
                                                                     world-state
                                                                     )
                                        (> (get-player-mouth world-state) 0) (let [noise (/ (+ 1 (rand-int 12)) 2)] 
                                                                   (do (println "It is hard to yell with " (:in-mouth-mat player) " in your mouth. You yell for " noise " noise!")
                                                                       (update-for-noise noise) 
                                                                       ))
                                        :else (let [noise (+ 1 (rand-int 12))] 
                                                                   (do (println "You yell for " noise " noise!")
                                                                       (update-for-noise noise)
                                                                       ))
                                        ) 
    
    :else (do (println "What's your problem?")
              world-state
              )
    ))
  )
(defn charm
  [world-state]
  (let [player (get-player world-state)
        charm-roll (+ (rand-int 6) 1)
        can-hear (can-hear? world-state)
        noise (if can-hear
                can-hear
                12
                )
        need-roll (if (> (get-player-mouth world-state) 0)
                    5
                    4
                    )
        update-for-noise (fn [n] 
                       (assoc-player world-state :cat :noise-map (edge-noise sound-edges (get-player-loc world-state) n)))
        ]
    (cond 
      (= (:turn world-state) :caretaker) (do (println "You're not that charming.")
                                             world-state
                                             )
      (:muffled? player) (do (println "You hum something unintelligible into the Caretaker's hand.")
                             world-state
                             )
      (> (get-player-mouth world-state) 6) (do (println "You cannot Charm while gagged.")
                                   world-state
                                   )
      (> (get-player-mouth world-state) 0) (do (println "It is hard to be charming with " (:in-mouth-mat player) " in your mouth." )
                                   (cond 
                                     (and (> charm-roll need-roll) can-hear) (do (println "Nonetheless, you manage to charm the Caretaker, making " noise " noise.")
                                                                                 (free-action (update-for-noise noise))
                                                                                 )
                                     can-hear (do (println "You failed to Charm the Caretaker, but made " noise " noise.")
                                                  (update-for-noise noise)
                                                  )
                                     :else (do (println "You failed to charm the Caretaker, but made 12 noise.")
                                               (update-for-noise noise)
                                               )
                                     ))
      :else (cond 
              (and (> charm-roll need-roll) can-hear) (do (println "You charm the Caretaker, making " noise " noise!")
                                                          (free-action (update-for-noise noise))
                                                          )
              can-hear (do (println "You failed to Charm the Caretaker, but made " noise " noise.")
                           (update-for-noise noise)
                           )
              :else (do (println "You failed to Charm the Caretaker, but made 12 noise.")
                        (update-for-noise noise)
                        )
              )

      )
    )
  )

;; Object functions
(defn make-phones
  []
  (hash-map :phone1 {:loc (random-space)
                     :able? true} 
            :phone2 {:loc (random-space)
                     :able? true})
  )
(defn find-phones
  [world-state]
  (pp/pprint (mapv #(:loc (val %)) (:phone-list world-state)))
  )
(defn make-exits
  []
  (hash-map :front {:loc [[:4 :14 {:direction :up}] [:14 :4 {:direction :down}]]
                    :open? (rand-open)} 
            :side {:loc [[:10 :157 {:direction :right}] [:157 :10 {:direction :left}]]
                   :open? (rand-open)}
            :side-closet {:loc [[:154 :155 {:direction :up}] [:155 :154 {:direction :down}]]
                          :open? (rand-open)} 
            :hall-closet {:loc [[:137 :14 {:direction :left}] [:14 :137 {:direction :right}]]
                          :open? (rand-open)}
            :bottom-bedroom-closet {:loc [[:62 :135 {:direction :up}] [:135 :62 {:direction :down}]]
                                    :open? (rand-open)} 
            :top-bedroom-closet {:loc [[:56 :136 {:direction :up}] [:136 :56 {:direction :down}]]
                                 :open? (rand-open)}
            )
  )
(defn make-neighbor
  []
  {:alert-count 0
   :investigating? false}
  )
(defn make-treasure
  []
  (apply merge-with + (take treasure (repeatedly #(hash-map (random-space) 1))))
  )

;; Game functions
(defn get-sub-mat
  ([b]
  (cond
    (= b :arms) :arm-mat
    (= b :legs) :leg-mat
    ))
  ([b new-subdue]
   (if (> new-subdue 6)
     :over-mouth-mat
     :in-mouth-mat
     )
   )
  )
(defn available-exit
  [world-state]
  (first (filter #(contains? (set (drop-last (first (:loc (val %))))) (get-player-loc world-state)) (:exit-list world-state)))
  )
(defn available-phone
  [world-state]
   (first (filter #(= (:loc (val %)) (get-player-loc world-state)) (:phone-list world-state))) 
  )
(defn check-door
  [world-state]
  (let [available-exit (available-exit world-state)]
    (if (nil? available-exit)
      (println "There are no doors near you.")
      (if (:open? (val available-exit))
        (println "The door in front of you is unlocked.")
        (println "The door in front of you is locked.")
        )
      )
    )
  )
(defn check-phone
  [world-state]
  (let [available-phone (available-phone world-state)]
    (if (nil? available-phone)
      (do (println "There is no phone here.")
          world-state
          )
      (if (:able? (val available-phone))
        (do (println "The phone here is working properly.")
            world-state
            )
        (do (println "The line is dead!")
            (if (is-caretaker-aware?)
              world-state
              (make-caretaker-suspicious world-state)
              )
            )
        )
      )
    )
  )

;; Message functions
(defn make-message
  ([character]
   (if (= character :cat)
     (str cat-message "\n")
     (str caretaker-message "\n")
     )
   )
  ([player add-message]
   (str (:message player) "\n\n" add-message)
   )
  )
(defn update-message
  [world-state message]
  (assoc-in world-state [:character-list (:turn world-state) :message] (make-message (get-player world-state) message))
  )
(defn update-add-message
  [world-state message]
  (assoc-in world-state [:character-list (:turn world-state) :add-message] message)
  )
(defn init-message
  [world-state]
  (assoc-in (assoc-in world-state [:character-list :caretaker :message] (str (get-in world-state [:character-list :caretaker :add-message]) "\n\n" (make-message :caretaker))) 
            [:character-list :cat :message] (str (get-in world-state [:character-list :cat :add-message]) "\n\n" (make-message :cat)))
  )
(defn loc-text
  [world-state]
  (let [player (get-in world-state [:character-list (:turn world-state)])]
    (update-message world-state (str "You are at location " (name (:loc player)) " facing " (name (:face player)) "."))
    )
  )
(defn see-text
  [world-state]
  (let [player (get-player world-state)
        opponent (get-opponent world-state)]
    (cond (can-see? world-state (get-active-view-edges opponent)) 
          (if (= (:turn world-state) :caretaker) 
            (make-caretaker-aware (update-message world-state (make-message player (str "You see the Cat at " (name (:loc opponent)) " facing " (name (:face opponent)) ".")))) 
            (update-message world-state (str "You see the Caretaker at " (name (:loc opponent)) " facing " (name (:face opponent)) "."))
            )
          :else world-state
          )
    )
  )
(defn hear-text
  [world-state]
  (let [player (get-player world-state)
        opponent (get-opponent world-state)]
    (if-let [hear-dir (can-hear? sound-edges (:loc player) (:noise-map opponent))] 
          (if (= (:turn world-state) :caretaker) 
            (make-caretaker-suspicious (update-message world-state (str "You hear something from " (name hear-dir) ".")))
            (update-message world-state (str "You hear the Caretaker from " (name hear-dir) "."))
            )
          world-state
          )
    )
  )
(defn susp-aware-text
  [world-state]
  (let [player (get-player world-state)]
      (cond (= (:turn world-state) :cat) world-state
            (:aware player) (update-message world-state (str "You are aware of the Cat.")) 
            (> (:susp-count player) 2) (make-caretaker-aware (update-message world-state (make-message player (str "Something is definitely wrong here. You are now Aware of the Cat.")))) 
            (> (:susp-count player) 0) (update-message world-state (make-message player (str "You are suspicious.")))
            :else world-state
            )
    )
  )
(defn your-treasure-text
  [world-state]
  (let [player (get-player world-state)]
      (if (> (:treasure player) 0) 
        (update-message world-state (str "You have " (:treasure player) " treasure."))
        world-state
        )
    )
  )
(defn treasure-here-text
  [world-state]
  (let [player (get-player world-state)
        loc (:loc player)
        tm (:treasure-map player)
        ]
    (cond 
      (and (is-treasure? world-state loc)
           (= (is-treasure? world-state loc) (get tm loc))
           )
      (update-message world-state (str "There is " (is-treasure? world-state loc) " treasure at your location.")) 
      (and (is-treasure? world-state loc)
           (not (contains? tm loc))
           )
      (assoc-player world-state (:turn world-state) :message (make-message player (str "There is " (is-treasure? world-state loc) " treasure at your location.")) [:treasure-map loc] (is-treasure? world-state loc)) 
      (or (and (is-treasure? world-state loc)
               (not= (is-treasure? world-state loc) (get (:treasure-map player) loc))
               )
          (and (not (is-treasure? world-state loc))
               (contains? tm loc)
               )    
          )
      (if (= (:turn world-state) :caretaker)
        (make-caretaker-suspicious (assoc-player world-state :caretaker :message (make-message player (str "There used to be " (loc tm)  " treasure at this location!")) [:treasure-map loc] (is-treasure? world-state loc)))
        (update-in world-state [:character-list (:turn world-state)] assoc :message (make-message player (str "There used to be " (loc tm)  " treasure at this location!")) [:treasure-map loc] (is-treasure? world-state loc))
        )
      :else world-state 
      )
    )
  )
(defn capability-text
  [world-state]
  (let [player (get-player world-state)]
    (update-message world-state (str 
                                  (if (:muffled? player)
                                    (str "You are muffled.\n")
                                    (str "")
                                    )
                                  (cond
                                    (> (:arms player) 6) (str "Your arms are bound with " (:arm-mat player) ". ")
                                    (> (:arms player) 0) (str "Your arms are hindered with " (:arm-mat player) ". ")
                                    :else (str "")
                                    )
                                  (cond
                                    (> (:legs player) 6) (str "Your legs are bound with " (:leg-mat player) ". ")
                                    (> (:legs player) 0) (str "Your legs are hindered with " (:leg-mat player) ". ")
                                    :else (str "")
                                    )
                                  (cond
                                    (> (:mouth player) 6) (str "You are gagged. " )
                                    (> (:mouth player) 0) (str "There is " (:in-mouth-mat player) " in your mouth. ")
                                    :else (str "")
                                    )
                                  ))
    )
  )
(defn build-move-list
  [world-state]
  (let [player-keyword (:turn world-state)
        player (get-player world-state)
        general-movelist " - move\n - turn\n - find phones\n"
        treasure-movelist (if (is-treasure? world-state (:loc player))
                            (str " - pick up treasure\n")
                            ""
                            )
        door-movelist (if (available-exit world-state)
                        (str " - check door\n - lock door\n" (if (= player-keyword :caretaker)
                                                               " - unlock door\n"
                                                               " - pick lock\n"
                                                               ))
                        ""
                        )
        phone-movelist (if (available-phone world-state)
                         (str " - check phone\n"
                              (if (= player-keyword :cat)
                                " - sabotage\n"
                                (if (:aware player)
                                  " - dial 911\n"
                                  " - fix\n"
                                  )
                                )
                              )
                         )
        subdued-list (if (is-subdued? player)
                       " - resist\n"
                       ""
                       )
        ]
    (str general-movelist treasure-movelist door-movelist phone-movelist subdued-list "\n")
    )
  )
(defn move-list-text
  [world-state]
  (update-message world-state (build-move-list world-state))
  )
(defn move-prompt-text
  [world-state]
  (update-message world-state "What would you like to do?")
  )
(defn update-and-print-message
  [world-state]
  (-> world-state
      (init-message)
      (loc-text)
      (your-treasure-text)
      (treasure-here-text)
      (see-text)
      (hear-text)
      (susp-aware-text)
      (capability-text)
      (move-list-text)
      (move-prompt-text)
      )
  )

;(defn sabotage
;  [world-state]
;  (let [available-phone (available-phone world-state)]
;    (if (nil? available-phone)
;      (do (println "There are no phones near you.")
;          world-state
;          )
;      (if (:able? (val available-phone))
;        (change-character (update-add-message  (assoc-in world-state [:phone-list (key phone) :able?] false) "This phone is now broken."))
;        (do (println "You've already sabotaged this phone!")
;            world-state
;            )
;        )
;      )
;    )
;  )
;(defn unlock-door
;  [world-state]
;  (let [available-exit (available-exit world-state)]
;    (if (nil? available-exit)
;      (do (println "There are no doors near you.")
;          world-state
;          )
;        (if (:open? (val available-exit))
;          (do (println "The door in front of you is already unlocked!")
;              world-state
;              )
;          (change-character (unlock-exit world-state available-exit))
;          )
;      )
;    )
;  )
;(defn lock-door
;  [world-state]
;  (let [available-exit (available-exit world-state)]
;    (if (nil? available-exit)
;      (do (println "There are no doors near you.")
;          world-state
;          )
;      (if (:open? (val available-exit))
;        (change-character (lock-exit world-state available-exit))
;        (do (println "The door in front of you is already locked!")
;            world-state
;            )
;        )
;      )
;    )
;  )
(defn unlock-exit
  [world-state exit]
  (let [unlock-state (assoc-in world-state [:exit-list (key exit) :open?] true)]
    (assoc unlock-state :move-edges (add-edges (:move-edges unlock-state) (find-exits (:exit-list unlock-state) :open?))))
  )
(defn lock-exit
  [world-state exit]
  (let [lock-state (assoc-in world-state [:exit-list (key exit) :open?] false)]
    (assoc lock-state :move-edges (remove-exit-edges (:move-edges lock-state) (find-exits (:exit-list lock-state) (complement :open?))))
      ) 
  )
;; TODO Have to adjust so that move edges get updated for lock and unlock
(defn act
  [world-state action-keyword]
  (let [sm (action-keyword  
             {:sabotage {:verb "sabotage this phone" :noun "phone" :act-num 1 :body :arms
                         :available (available-phone world-state) :test (complement :able?) :set-key :able? :desire-val false :counter :sabotage-count
                         :thing-list :phone-list :noa-text "This phone has already been sabotaged." :success-text "This phone is now sabotaged." :progress-text "You are sabotaging this phone. Number of tries remaining: "}
              :fix {:verb "fix" :noun "phone" :act-num 2 :body :arms 
                    :available (available-phone world-state) :test :able? :set-key :able? :desire-val true :counter :fix-count
                    :thing-list :phone-list :noa-text "This phone is already working." :success-text "This phone is now working." :progress-text "You are fixing this phone. Number of tries remaining: "}
              :lock-door {:verb "lock this door" :noun "door" :act-num 1 :body :arms
                          :available (available-exit world-state) :test (complement :open?) :set-key :open? :desire-val false :counter :lock-count 
                          :thing-list :exit-list :noa-text "This door is already locked." :success-text "This door is now locked." :progress-text "You are locking this door. Number of tries remaining: "}
              :unlock-door {:verb "unlock this door" :noun "door" :act-num 1 :body :arms
                          :available (available-exit world-state) :test :open? :set-key :open? :desire-val true :counter :unlock-count
                          :thing-list :exit-list :noa-text "This door is already unlocked." :success-text "This door is now unlocked." :progress-text "You are unlocking this door. Number of tries remaining: "}              
              :pick-lock  { :verb "pick this lock" :act-num 1 :body :arms
                          :available (available-exit world-state) :test :open? :set-key :open? :desire-val true :counter :picklock-count
                          :thing-list :exit-list :noa-text "This door is already unlocked." :success-text "This door is now unlocked." :progress-text "You are picking this lock. Number of tries remaining: "}                            
           })
        player (get-player world-state)
        change-world (fn [ws action-keyword]
                       (cond 
                         (or (= action-keyword :unlock-door) (= action-keyword :pick-lock)) (unlock-exit ws (:available sm))
                         (= action-keyword :lock-door) (lock-exit ws (:available sm))
                         :else (assoc-in ws [(:thing-list sm) (key (:available sm)) (:set-key sm)] (:desire-val sm))
                         )
                       )
        f (fn [act-need]
            (cond 
              (not (:available sm)) (do (println "There are no " (:noun sm) "s near you.")
                                        world-state
                                        )
              ((:test sm) (val (:available sm))) (do (println (:noa-text sm))
                                                     world-state
                                                     )
              :else (if (>= (+ 1 (get-in player [(:counter sm) (key (:available sm))] 0)) act-need)
                      (-> world-state
                          (change-world action-keyword)
                          (assoc-in [:character-list (:turn world-state) (:counter sm) (key (:available sm))] 0)
                          (update-add-message (:success-text sm))
                          (change-character)
                          )
                      (-> world-state
                          (update-in [:character-list (:turn world-state) (:counter sm) (key (:available sm))] (fnil inc 0))
                          (update-add-message (str (:progress-text sm) (- (:actnum sm) [(:counter sm) (key (:available sm))])))
                          (change-character)
                          )
                      )
              )
            )
        ]
    (cond
      (> ((:body sm) player) 6) (do (println (str "You cannot " (:verb sm) " right now."))
                                    world-state
                                    )
      (> ((:body sm) player) 0) (f (* 2 (:act-num sm)))
      :else (f (:act-num sm))
      )
    )
  )
(defn remove-treasure-from-list
  [treasure-list loc]
  (dissoc treasure-list loc)
  )
(defn pickup-treasure
  [world-state]
  (if (is-treasure? world-state (get-player-loc world-state))
    (let [loc (get-player-loc world-state)
          treasure-amt (is-treasure? world-state loc)
          player-amt (get-in world-state [:character-list (:turn world-state) :treasure])
          ]
      (change-character 
        (-> world-state
          (assoc :treasure-list (remove-treasure-from-list (:treasure-list world-state) loc))
          (assoc-in [:character-list (:turn world-state) :treasure] (+ player-amt treasure-amt))
          (update-in [:character-list (:turn world-state) :treasure-map] dissoc :loc)
          )
        )   
      )
    world-state
    )
  )
(defn end-game
  ([world-state]
   (let [cat-treasure (get-in world-state [:character-list :cat :treasure])
         care-treasure (get-in world-state [:character-list :caretaker :treasure])
         ]
     (cond 
       (> cat-treasure care-treasure) (str "The Cat wins with " cat-treasure "!")
       (> care-treasure cat-treasure) (str "The Caretaker wins with " cat-treasure "!")
       :else "It's a tie!"
       )
     ) 
   )
  ([world-state winner]
   (println (str "The " winner " wins!"))
   )
  )
(defn dial-911
  [world-state dial-need]
  (let [available-phone (available-phone world-state)
        dial-count (get-in world-state [:character-list :caretaker :dial-count (key (available-phone))] 0)
        ]
    (cond
      (not (is-caretaker-aware? world-state)) (do (println "What are you going to tell them?")
                                                  world-state
                                                  )
      (nil? available-phone) (do (println "There are no phones near you.")
                                 world-state
                                 )
      (not (:able? (val available-phone))) (do (println "The line is dead!")
                                               world-state
                                               )
      :else (if (>= dial-count dial-need)
              (end-game world-state "Caretaker")
              (-> world-state
                  (update-in [:character-list :caretaker :dial-count (key available-phone)] (fnil inc 0))
                  (update-add-message "You are dialing 911.")
                  (change-character)
                  )
              )
      )
    )
  )
(def bind-mats ["tape" "rope"])
(def in-mouth-mats ["a sock" "a rubber ball" "a handkerchief" "a stuffed animal"])
(def over-mouth-mats ["some tape" "a scarf"])
(defn subdue
  [world-state]
  (let [player (get-player world-state)
        opponent (get-opponent world-state)
        opponent-name (if (= (:turn world-state) :cat)
                        "Caretaker"
                        "Cat"
                        )
        input (do (println "What would you like to target?\n - arms\n - legs\n - mouth")
                  (flush)
                  (keyword (read-line))
                  )
        roll (fn [c] (if (> (:arms c) 0)
                       (+ (rand-int 6) 1)
                       (+ (rand-int 12) 1)
                       ))
        roll-diff (- (roll (get-player world-state)) (roll (get-opponent world-state)))
        old-subdue (input (get-opponent world-state))
        new-subdue (+ old-subdue roll-diff)
        bind-mat (rand-nth bind-mats)
        add-subdue (change-character (assoc-in world-state [:character-list (other-character (:turn world-state)) input] new-subdue))
        new-bind (change-character (update-in world-state [:character-list (other-character (:turn world-state))] assoc input new-subdue (get-sub-mat input) bind-mat))
        ] 
    (if (> roll-diff 0)
      (cond 
        (> (:arms player) 6) (do (println "You can't subdue anyone with your arms bound.")
                                 world-state
                                 ) 
        (or (= input :arms) (= input :legs)) (cond 
                                               (= old-subdue 12) (do (println "The " opponent-name "'s" (name input) " are already fully bound.")
                                                                     world-state
                                                                     )
                                               (> old-subdue 0) (do (println "You grab some " ((get-sub-mat input) opponent)" and further bind the " opponent-name "'s " (name input) " by " roll-diff ".")
                                                                    add-subdue
                                                                    )
                                               :else (do (println "You grab some " bind-mat " and bind the " opponent-name "'s " (name input) " by " roll-diff ".")
                                                         new-bind      
                                                         )
                                               )


        (= input :mouth) (cond 
                           (> old-subdue 11) (do (println "The " opponent-name " is already fully gagged.")
                                                 world-state
                                                 )
                           (> old-subdue 6) (do (println "You grab some more " (:over-mouth-mat opponent)" and reinforce the " opponent-name "'s gag by " roll-diff ".")
                                                add-subdue 
                                                )
                           (and (> old-subdue 0) (> new-subdue 6)) (let [om-mat (rand-nth over-mouth-mats)] 
                                                                     (do (println "You grab some " om-mat " and plaster it over the " opponent-name "'s mouth. They are gagged by an additional " roll-diff ".")
                                                                         (change-character (update-in world-state [:character-list (other-character (:turn world-state))] input new-subdue :over-mouth-mat om-mat)) 
                                                                         ))
                           (> old-subdue 0) (do (println "You shove the " (:in-mouth-mat opponent) " deeper into the " opponent-name "'s mouth. They are gagged by an additional " roll-diff ".")
                                                add-subdue
                                                )
                           :else (let [im-mat (rand-nth in-mouth-mats)] 
                                   (do (println "You grab " im-mat " and shove it in the " opponent-name "'s mouth, gagging them for " roll-diff ".")))
                           )
        :else (do (println "You can't target that.")
                  world-state
                  ))
      (do (println "The " opponent-name " escapes!")
          (change-character world-state)
          ))
    )
  )
(defn resist
  [world-state]
  (let [input (do (println "What would you like to resist?\n - arms\n - legs\n - mouth")
                  (flush)
                  (keyword (read-line))
                  )
        roll (fn [c] (cond 
                       (> (:arms c) 6) (int (Math/floor (/  (+ (rand-int 6) 1) 4)))
                       (> (:arms c) 0) (int (Math/floor (/  (+ (rand-int 6) 1) 2)))
                       :else (+ (rand-int 6) 1)
                       ))
        player (get-player world-state)
        old-subdue (input player)
        new-subdue (- old-subdue (roll player))
        reduce-subdue (change-character (assoc-in world-state [:character-list (:turn world-state) input] new-subdue))
        ]
    (cond
      (= input :mouth) (cond
                         (> new-subdue 6) (do (println "You loosen the " (:over-mouth-mat player) " over your mouth.")
                                              reduce-subdue
                                              )
                         (and (> old-subdue 6) (< new-subdue 7)) (do (println "You free your lips from the " (:over-mouth-mat player))
                                                                     (change-character (update-in world-state [:character-list (:turn world-state)] assoc :mouth new-subdue :over-mouth-mat nil))
                                                                     )
                         (> new-subdue 0) (do (println "You push against the " (:in-mouth-mat player) " with your tongue.")
                                              reduce-subdue
                                              )
                         :else (do (println "You spit out " (:in-mouth-mat player) "!")
                                   (change-character (update-in world-state [:character-list (:turn world-state)] assoc :mouth new-subdue :in-mouth-mat nil))
                                   )
                         )
      (or (= input :arms) (= input :legs)) (cond
                                             (= new-subdue 0) (do (println "You wriggle free of the " ((get-sub-mat input) player) "!")
                                                                  (change-character (update-in world-state [:character-list (:turn world-state)] assoc input new-subdue (get-sub-mat input) nil))
                                                                  )
                                             :else reduce-subdue
                                             )
      :else (do (println "You can't resist that.")
                world-state
                )
      )
    )
  )
(defn roll-for-initiative
  []
  (if (> (rand-int 1) 0)
    :cat
    :caretaker
    )
  )
;; TODO Hot coffee option
(defn combat
  [world-state]
  (let [current-player-name (:turn world-state)
        player (get-player world-state)
        opponent (get-opponent world-state)]
    (do (println (str (:message player) "\n\nYou are in combat!"))
        (let [input (read-line)
              opponent-name (if (= (:turn world-state) :cat)
                              "Caretaker"
                              "Cat"
                              )
              muffle (assoc-in world-state [:character-list (other-character (:turn world-state)):muffled?] true)
              unmuffle (assoc-in world-state [:character-list (:turn world-state):muffled?] false)
              ]
          (cond
            (or (= input "quit") (= input "q")) nil
            (= input "yell") (combat (change-character (yell world-state))) 
            (= input "charm") (combat (charm world-state))
            (= input "muffle") (cond
                                 (:muffled? opponent) (do (println "You are already doing that.")
                                                          (combat world-state)
                                                          )
                                 (< (:arms player) (:arms opponent)) (do (println "You cover the " opponent-name "'s mouth with your hand.")
                                                                         (combat (change-character (muffle world-state)))
                                                                         )
                                 :else (do (println "The " opponent-name " slaps your hand away.")
                                           (combat world-state)
                                           )
                                 )
            (= input "pry") (cond
                              (not (:muffled? player)) (do (println "You are not currently muffled.")
                                                           (combat world-state)
                                                           )
                              (< (:arms player) (:arms opponent)) (do (println "You pry the " opponent-name "'s hand off of your mouth.")
                                                                      (combat (change-character (unmuffle world-state)))
                                                                      )
                              :else (do (println "The " opponent-name " laughs as you attempt to free your mouth.")
                                        (combat world-state)
                                        )
                              )
            (= input "flee") (do (print-pr "Where would you like to move?")
                                (flush)
                                (update-in (move world-state (:turn world-state) (keyword (read-line))) [:character-list] assoc [:cat :muffled?] false [:caretaker :muffled?] false)
                                )
            (= input "subdue") (combat (subdue world-state))
            (= input "resist") (combat (resist world-state))
            :else (do (println "You cannot do that.")
                      (combat world-state)
                      )
            )
          )))
  )
(defn get-starting-loc
  [world-state pkey]
  (let [character (get-in world-state [:character-list pkey])]
    (if (:noise-map character)
      (first (:noise-map character))
      (:loc character)
      )
    )
  )
(defn play
  [initial-state]
    (if (= (get-player-loc initial-state) (get-opponent-loc initial-state)) 
      (let [initiative-state (assoc (make-caretaker-aware initial-state) :turn (roll-for-initiative))
            ambush-state (assoc (free-action initiative-state) :turn :cat)]
      (cond 
        (not (is-caretaker-aware? initial-state)) (play (combat ambush-state))
        (and 
          (> (+ (rand-int 6) 1) 4)
          (not (can-see? initial-state (get-active-view-edges (get-cat initial-state)) (:loc (get-caretaker initial-state)) (get-starting-loc initial-state :cat)))) (play (combat ambush-state))
        :else (play (combat initiative-state))
        ))
      (let [world-state (update-and-print-message initial-state)
            current-player-name (:turn world-state)
            player (get-player world-state)
            opponent (get-opponent world-state)]
        (do (print-pr (:message player))
          (flush)
          (let [input (read-line)]
            (cond 
              (or (= input "quit") (= input "q")) nil
              (= input "move") (do (print-pr "Where would you like to move?")
                                   (flush)
                                   (play (move world-state current-player-name (keyword (read-line))))
                                   ) 
              (= input "turn") (do (print-pr "In what direction would you like to turn?")
                                   (flush)
                                   (play (change-character (assoc-in world-state [:character-list current-player-name :face] (keyword (read-line)))))
                                   )
              (= input "check door") (do (check-door world-state)
                                         (play world-state)
                                         )
              (= input "pick lock") (if (= current-player-name :caretaker)
                                      (do (println "You don't know how to do that.")
                                          (play world-state)
                                          )
                                      (play (act world-state :pick-lock)))
              (= input "unlock door") (if (= current-player-name :cat)
                                        (do (println "This isn't your house!")
                                            (play world-state)
                                            )
                                        (play (act world-state :unlock-door)))
              (= input "lock door") (play (act world-state :lock-door))
              (= input "pick up treasure") (play (pickup-treasure world-state))                        
              (= input "show treasure map") (do (pp/pprint (get-in world-state [:character-list current-player-name :treasure-map]))
                                                (play world-state)
                                                )
              (= input "show noise map") (do (pp/pprint (get-in world-state [:character-list current-player-name :noise-map]))
                                             (play world-state)
                                             )
              (= input "charm") (play (charm world-state))
              (= input "yell")  (play (yell world-state))
              (= input "resist") (play (resist world-state))
              (= input "find phones") (do (find-phones world-state)
                                          (play world-state)
                                          )
              (= input "check phone") (play (check-phone world-state))
              (= input "sabotage") (if (= current-player-name :cat)
                                     (play (act world-state :sabotage))
                                     (do (println "Please don't wreck your friend's stuff.")
                                         (play world-state)
                                         )
                                     )
              (= input "fix") (if (= current-player-name :cat) 
                                (do (println "You don't fix things!")
                                    (play world-state)
                                    ) 
                                (play (act world-state :fix))
                                )
              (= input "dial 911") (cond 
                                     (= current-player-name :cat) (do (println "You don't want the cops here.")
                                                                      (play world-state)
                                                                      )
                                     (or (> (get-player-arms world-state) 6)
                                         (> (get-player-mouth world-state) 6)) (do (println "You cannot fix that right now.")
                                                                                   (play world-state)
                                                                                   )
                                     (and (> (get-player-arms world-state) 0) (> (get-player-mouth world-state) 0)) (play (dial-911 world-state 4))
                                     (or (> (get-player-arms world-state) 0) (> (get-player-mouth world-state) 0)) (play (dial-911 world-state 2))
                                     :else (play (dial-911 world-state 1))
                                     )
              :else (do (println "Invalid selection.") (play world-state))
              )))))
  )
(defn start
  []
  (let [exit-list (make-exits)
        initial-state {:phone-list (make-phones)
                       :exit-list exit-list
                       :treasure-list (make-treasure)
                       :character-list (make-characters)
                       :neighbor-list (repeat 3 (make-neighbor))
                       :move-edges (move-edges-with-exit exit-list)
                       :combat? false
                       :turn :cat
                       :free-action? true}]
      (do 
        (println (get-in initial-state [:character-list (:turn initial-state) :message]))
        (play initial-state))
    )
  )
(defn test-state
  []
  (let [exit-list (make-exits)]
    {:phone-list (make-phones)
                       :exit-list exit-list
                       :treasure-list (make-treasure)
                       :character-list (make-characters)
                       :neighbor-list (repeat 3 (make-neighbor))
                       :move-edges (move-edges-with-exit exit-list)
                       :combat? false
                       :turn :cat
                       :free-action? true} 
    )
  )
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (start))
