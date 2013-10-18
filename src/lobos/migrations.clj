(ns lobos.migrations
  (:refer-clojure :exclude [alter drop bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema)
        colliery.lobos.helpers))

(defmigration add-users-table
  (up [] (create 
           (tbl :users
              (varchar :name 100 :unique)
              (check :name (> (length :name) 1)))))
  (down [] (drop (table :users))))

(defmigration add-histories-table
  (up [] (let [history-tbl #(-> (tbl %)
                                (varchar :title 200)
                                (integer :revision :not-null (default 1))
                                (belongs-to :users :user)) 
               main-table (history-tbl :histories)
               not-those-on-id #(if (= [:id] (get-in %1 [%2 :columns])) (dissoc %1 %2) %1)
               vers-table (-> (history-tbl :history-versions)
                              (update-in [:columns :id] (fn [_ c] c) ;; Remove pk reference
                                         (column* :id (data-type "uuid" [] {}) #{:not-null}))
                              (update-in [:constraints] ;; remove constraints that apply to id
                                         #(reduce not-those-on-id % (keys %))))]
           (create main-table)
           (create vers-table)))
  (down [] 
        (drop (table :history-versions))
        (drop (table :histories))))

(defmigration add-steps-table
  (up [] (create (tbl :steps
              (varchar :mimetype 200)
              (uuid :uuid)
              (text :data)
              (varchar :uri 500)
              (belongs-to :steps :preceding-step)))
          (create (indirection-tbl :steps :histories)))
  (down [] 
          (drop (indirection-tbl :steps :histories))
          (drop (table :steps))))

(defmigration add-roles-table
  (up [] (create (table :roles
                        (uuid-key)
                        (varchar :name 50 :unique)))
         (create (indirection-tbl :users :roles)))
  (down []
        (drop (indirection-tbl :users :roles))
        (drop (table :roles))))

(defmigration add-intermine-accounts
  (up [] (create (tbl :intermine-accounts
                      (varchar :token 100)
                      (varchar :uri 200)
                      (timestamp :last_accessed)
                      (belongs-to :users))))
  (down [] (drop (table :intermine-accounts))))

           
