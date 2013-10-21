(ns colliery.test.models.user
  (:use clojure.test)
  (:require [colliery.test.db :as testdb]
            [colliery.models.entities :as ents]
            [colliery.models.user :as u]))

(def user-names #{"Tom" "Dick" "Harry"})

(defn insert-users [t]
  (doseq [n user-names] (u/create {:name n}))
  (t))

(use-fixtures :once testdb/setup-db testdb/teardown-db)

(use-fixtures :each insert-users (testdb/remove-ents ents/users))

(deftest ^:db all
  (let [users (u/all)]
    (is (not (empty? (u/all))))
    (is (= 1 (count (filter #{"Tom"} (map :name users)))))))

(deftest ^:db fetch-user
  (let [tom (u/fetch-user {:name "Tom"})]
    (is (= "Tom" (:name tom)))
    (is (empty? (:histories tom)))))
